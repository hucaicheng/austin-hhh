package com.austin.handler.deduplication;

import com.austin.handler.deduplication.build.Builder;
import com.austin.handler.deduplication.service.DeduplicationService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @function 去重服务控制中心
 * @Author hcc
 */
@Service
public class DeduplicationHolder {

    /**
     * 存放所有组装去重参数的map, key是去重枚举的code
     */
    private Map<Integer,Builder> buildHolder = new HashMap<>(4);

    /**
     * 具体的去重接口
     */
    private Map<Integer, DeduplicationService> serviceHolder = new HashMap<>(4);

    public Builder selectBuild(Integer key){
        return buildHolder.get(key);
    }

    public void putBuild(Integer key,Builder builder){
        buildHolder.put(key,builder);
    }

    public DeduplicationService selectService(Integer key){
        return serviceHolder.get(key);
    }

    public void putService(Integer key, DeduplicationService deduplicationService){
        serviceHolder.put(key,deduplicationService);
    }

}
