package com.austin.handler.deduplication.limit;

import cn.hutool.core.util.IdUtil;
import com.austin.common.domain.TaskInfo;
import com.austin.handler.deduplication.DeduplicationParam;
import com.austin.handler.deduplication.service.AbstractDeduplicationService;
import com.austin.support.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @function 滑动窗口去重器（内容去重采用基于redis中zset的滑动窗口去重，可以做到严格控制单位时间内的频次。）
 *  限制几分钟内只能发送一次
 * @Author hcc
 */
@Service(value = "SlideWindowLimitService")
public class SlideWindowLimitService extends AbstractLimitService implements LimitService {

    @Autowired
    private RedisUtils redisUtils;

    private DefaultRedisScript<Long> redisScript;

    @PostConstruct
    public void init(){
        redisScript = new DefaultRedisScript<>();
        redisScript.setResultType(Long.class);
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("limit.lua")));
    }


    /**
     * 去重实现
     * @param service  去重器对象
     * @param taskInfo
     * @param param 去重参数
     * @return
     */
    @Override
    public Set<String> limitFilter(AbstractDeduplicationService service, TaskInfo taskInfo, DeduplicationParam param) {
        Set<String> result = new HashSet<>(taskInfo.getReceiver().size());
        long timeMillis = System.currentTimeMillis();
        for (String receiver : taskInfo.getReceiver()) {
            String key = deduplicationSingleKey(service, receiver, taskInfo);
            String scoreValue = String.valueOf(IdUtil.getSnowflake().nextId());
            String score = String.valueOf(timeMillis);
            if (redisUtils.execLimitLua(redisScript, Collections.singletonList(key), String.valueOf(param.getDeduplicationTime() * 1000), score, String.valueOf(param.getCountNum()), scoreValue)) {
                result.add(receiver);
            }
        }
        return result;
    }
}
