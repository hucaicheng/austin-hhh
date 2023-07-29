package com.austin.handler.receiver.kafka;

import cn.hutool.core.util.StrUtil;
import com.austin.handler.receiver.utils.GroupIdMappingUtils;
import com.austin.support.constans.MessageQueuePipeline;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.header.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListenerAnnotationBeanPostProcessor;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @Author hucaicheng
 * 消息过滤器 :消息过滤器可以让消息在抵达监听容器前被拦截，过滤器根据系统业务逻辑去筛选出需要的数据交由 KafkaListener 处理，不需要的消息则会过滤掉
 */
@Service
@ConditionalOnProperty(name = "austin.mq.pipeline", havingValue = MessageQueuePipeline.KAFKA)
@Slf4j
public class ReceiverStart {
    @Autowired
    private ApplicationContext context;
    @Autowired
    private ConsumerFactory consumerFactory;//监听器工厂

    /**
     * receiver的消费方法常量
     */
    private static final String RECEIVER_METHOD_NAME = "Receiver.consumer";

    /**
     * 获取得到所有的groupId
     */
    private static List<String> groupIds = GroupIdMappingUtils.getAllGroupIds();

    /**
     * 下标(用于迭代groupIds位置)
     */
    private static Integer index = 0;

    /**
     * 为每个渠道不同的消息类型 创建一个Receiver对象
     *
     * @PostConstruct: 是Java自带的注解，在方法上加该注解会在项目启动的时候执行该方法，
     *                  也可以理解为在spring容器初始化的时候执行该方法。
     */
    @PostConstruct
    public void init() {
        for (int i = 0; i < groupIds.size(); i++) {
            context.getBean(Receiver.class);
        }
    }

    /**
     * 给Receiver 配置一个切面
     * 给每个Receiver对象的consumer方法 @KafkaListener赋值相应的groupId
     *
     *
     */
    @Bean
    public static KafkaListenerAnnotationBeanPostProcessor.AnnotationEnhancer groupIdEnhancer() {
        return (attrs, element) -> {
            if (element instanceof Method) {
                String name = ((Method) element).getDeclaringClass().getSimpleName() + StrUtil.DOT + ((Method) element).getName();
                if (RECEIVER_METHOD_NAME.equals(name)) {
                    attrs.put("groupId", groupIds.get(index++));
                }
            }
            return attrs;
        };
    }


    /**
     * 针对tag消息过滤(多个人连接的是同一个kafka, 防止a用户发送的消息被b消费者消费, 通过header中添加的tagId进行区分)
     * producer 将tag写进header里
     *
     * @return true 消息将会被丢弃
     */
    @Bean
    public ConcurrentKafkaListenerContainerFactory filterContainerFactory( @Value("${austin.business.tagId.key}")String tagIdKey,
                                                                           @Value("${austin.business.tagId.value}")String tagIdValue){
        ConcurrentKafkaListenerContainerFactory factory = new ConcurrentKafkaListenerContainerFactory();
        factory.setConsumerFactory(consumerFactory);
        // 被过滤的消息将被丢弃
        factory.setAckDiscarded(true);
        // 设置记录筛选策略
        factory.setRecordFilterStrategy( consumerRecord -> {
            if (Optional.ofNullable(consumerRecord.value()).isPresent()){
                for (Header header : consumerRecord.headers()) {
                    if (header.key().equals(tagIdKey) && new String(header.value()).equals(new String(tagIdValue.getBytes(StandardCharsets.UTF_8)))) return false;
                }
            }
            return true;
        });

    return factory;
    }
}
