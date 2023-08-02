package com.austin.handler.deduplication;

import com.austin.common.domain.TaskInfo;
import com.austin.common.enums.DeduplicationType;
import com.austin.common.enums.EnumUtil;
import com.austin.handler.deduplication.service.DeduplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @function 去重功能入口
 *
 *
 * @Author hcc
 */
@Service
public class DeduplicationRuleService {

    @Autowired
    private DeduplicationHolder deduplicationHolder;

    /**
     * 消息去重入口
     * @param taskInfo
     */
    public void duplication(TaskInfo taskInfo){
        String deduplicationConfig = "{\"deduplication_30\":{\"num\":1,\"time\":300},\"deduplication_40\":{\"num\":5}}";

        List<Integer> codeList = EnumUtil.getCodeList(DeduplicationType.class);
        for (Integer code : codeList) {
            // 通过code来确定去重类型,然后组装参数
            DeduplicationParam build = deduplicationHolder.selectBuild(code).build(deduplicationConfig, taskInfo);

            if (!Objects.isNull(build)){
                // 调用(code)对应的去重接口
                deduplicationHolder.selectService(code).deduplication(build);
            }
        }

    }
}
