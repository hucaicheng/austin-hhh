package com.austin.handler.deduplication.build;

import com.austin.common.domain.TaskInfo;
import com.austin.handler.deduplication.DeduplicationHolder;
import com.austin.handler.deduplication.DeduplicationParam;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.Objects;

/**
 * @function 当前抽象类存在的意义:
 *      1. 主要就是init()方法完成deduplicationHolder里面map的数据注入
 *      2. 由于两个子类组装参数的中间部分逻辑是一样的(getParamsFromConfig 方法), 就抽出来放到抽象类中
 *      3. 由于去重类型不一样, 但是参数是一样的, 所以组装参数的逻辑抽成一个方法, 入口还是在子类
 *      写法总结: 当中间部分代码逻辑是一样的时候, 就将方法抽出来,调用, 就不用写重复代码(AbstractDeduplicationBuilder)
 *      而如果是开头和结尾代码一样, 中间逻辑不一样, 那就将新增一个中间逻辑实现类, 然后注入不同的中间类来完成, 就可以去除重复代码(AbstractDeduplicationService)
 * @Author hcc
 */
public abstract class AbstractDeduplicationBuilder implements Builder{

    protected Integer deduplicationType;

    @Autowired
    private DeduplicationHolder deduplicationHolder;


    @PostConstruct
    public void init(){
        deduplicationHolder.putBuild(deduplicationType,this);
    }


    public DeduplicationParam getParamsFromConfig(Integer key, String deduplicationConfig, TaskInfo taskInfo){
        JSONObject jsonObject = JSONObject.parseObject(deduplicationConfig);
        if (Objects.isNull(jsonObject)){
            return null;
        }
        // 通过配置的deduplicationConfig 封装到为DeduplicationParam对象中
        DeduplicationParam deduplicationParam = JSONObject.parseObject(jsonObject.getString(DEDUPLICATION_CONFIG_PRE + key), DeduplicationParam.class);
        if (Objects.isNull(deduplicationParam)){
            return null;
        }
        deduplicationParam.setTaskInfo(taskInfo);
        return deduplicationParam;
    }
}