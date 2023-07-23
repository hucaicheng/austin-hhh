package com.austin.support.pipeline;

import com.austin.common.vo.BasicResultVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Author hucaicheng
 * @Date 2023/7/17 0017 21:15
 * @Description: 责任链上下文
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class ProcessContext<T extends ProcessModel>{

    /**
     * 标识责任链的code
     */
    private String code;

    /**
     * 储存责任链上下文数据的模型
     */
    private T processModel;

    /**
     * 责任链中断的标识
     */
    private Boolean needBreak;

    /**
     * 流程处理的结果
     */
    BasicResultVO response;

}
