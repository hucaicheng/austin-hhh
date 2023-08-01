package com.austin.handler.deduplication.build;

import cn.hutool.core.date.DateUtil;
import com.austin.common.domain.TaskInfo;
import com.austin.common.enums.AnchorState;
import com.austin.common.enums.DeduplicationType;
import com.austin.handler.deduplication.DeduplicationParam;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

/**
 * @function 频率去重参数构建
 * @Author hcc
 */
@Service
public class FrequencyDeduplicationBuilder extends AbstractDeduplicationBuilder implements Builder{

    public FrequencyDeduplicationBuilder() {
        // 设置去重类型为: 频次 去重
        deduplicationType = DeduplicationType.FREQUENCY.getCode();
    }

    @Override
    public DeduplicationParam build(String deduplication, TaskInfo taskInfo) {
        // 组装去重参数
        DeduplicationParam deduplicationParam = getParamsFromConfig(deduplicationType, deduplication, taskInfo);
        if (Objects.isNull(deduplicationParam)) {
            return null;
        }

        //(DateUtil.endOfDay(new Date()).getTime() - DateUtil.current()) / 1000 : 等到今天0点还剩的秒数
        deduplicationParam.setDeduplicationTime((DateUtil.endOfDay(new Date()).getTime() - DateUtil.current()) / 1000);
        deduplicationParam.setAnchorState(AnchorState.RULE_DEDUPLICATION);
        return deduplicationParam;
    }
}
