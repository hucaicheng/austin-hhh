package com.austin.handler.deduplication.build;

import com.austin.common.domain.TaskInfo;
import com.austin.common.enums.AnchorState;
import com.austin.handler.deduplication.DeduplicationParam;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @function 内容去重参数构建
 * @Author hcc
 */
@Service
public class ContentDeduplicationBuilder extends AbstractDeduplicationBuilder implements Builder {

    public ContentDeduplicationBuilder(){
        deduplicationType = AnchorState.CONTENT_DEDUPLICATION.getCode();
    }

    @Override
    public DeduplicationParam build(String deduplication, TaskInfo taskInfo) {
        DeduplicationParam deduplicationParam = getParamsFromConfig(deduplicationType, deduplication, taskInfo);
        if (Objects.isNull(deduplicationParam)) {
            return null;
        }
        deduplicationParam.setAnchorState(AnchorState.CONTENT_DEDUPLICATION);
        return deduplicationParam;
    }
}
