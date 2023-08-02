package com.austin.handler.deduplication.limit;

import cn.hutool.core.collection.CollUtil;
import com.austin.common.constant.CommonConstant;
import com.austin.common.domain.TaskInfo;
import com.austin.handler.deduplication.DeduplicationParam;
import com.austin.handler.deduplication.service.AbstractDeduplicationService;
import com.austin.support.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @function 每天限制给用户不能超过n次去重实现类
 * @Author hcc
 */
@Service(value = "SimpleLimitService")
public class SimpleLimitService extends AbstractLimitService{

    private static final String LIMIT_TAG = "SP_";

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 每天限制给用户不能超过n次去重
     * @param service  去重器对象
     * @param taskInfo
     * @param param 去重参数
     * @return 需要去重的receiver
     */
    @Override
    public Set<String> limitFilter(AbstractDeduplicationService service, TaskInfo taskInfo, DeduplicationParam param) {
        Set<String> filterReceiver = new HashSet<>(taskInfo.getReceiver().size());
        // 存储不用去重的key
        List<String> readyPutRedisReceiver = new ArrayList<>(taskInfo.getReceiver().size());
        // redis数据隔离
        List<String> keys = deduplicationSingleAllKey(service, taskInfo).stream().map(key -> LIMIT_TAG + key).collect(Collectors.toList());
        // 通过keys得到对应的value
        Map<String, String> values = redisUtils.mGet(keys);
        // 由于要统计receiver, 所以必须遍历receiver
        for (String receiver : taskInfo.getReceiver()) {
            String key =LIMIT_TAG + deduplicationSingleKey(service,receiver,taskInfo);
            String value = values.get(key);// 对应着key的次数
            if (Objects.nonNull(value) && Integer.parseInt(value) >= param.getCountNum()){
                filterReceiver.add(receiver);
            }else {
                readyPutRedisReceiver.add(key);
            }
        }
        // 给不满足条件的key更新或新增
        putInRedis(readyPutRedisReceiver, values, param.getDeduplicationTime());
        return filterReceiver;
    }

    /**
     *存入redis 实现去重
     * @param keys keys
     * @param values
     * @param deduplicationTime
     */
    private void putInRedis(List<String> keys, Map<String, String> values, Long deduplicationTime) {
        Map<String, String> keyValues = new HashMap<>(keys.size());
        for (String key : keys) {
            String value = values.get(key);
            if (Objects.nonNull(value)){
                // 存在就value加1
                keyValues.put(key, value+1);
            }else {
                // 不存在就将value设置为1
                keyValues.put(key, String.valueOf(CommonConstant.DEDUPLICATION_NEW_COUNT));
            }
        }
        if (CollUtil.isNotEmpty(keyValues)) {
            redisUtils.pipelineSetEx(keyValues, deduplicationTime);
        }
    }
}
