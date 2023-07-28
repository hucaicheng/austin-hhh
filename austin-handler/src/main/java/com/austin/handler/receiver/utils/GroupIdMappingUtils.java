package com.austin.handler.receiver.utils;

import com.austin.common.domain.TaskInfo;
import com.austin.common.enums.ChannelType;
import com.austin.common.enums.EnumUtil;
import com.austin.common.enums.MessageType;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author hucaicheng
 * groupId 标识着每一个消费者组
 */
public class GroupIdMappingUtils {

    /**
     * 获取所有的groupId(对应一个消费者组)
     * @return
     */
    public static List<String> getAllGroupIds() {
        List<String> groupIds = new ArrayList<>();
        for (ChannelType channelType : ChannelType.values()) {
            for (MessageType messageType : MessageType.values()) {
                groupIds.add(channelType.getCodeEn() + "." + messageType.getCodeEn());
            }
        }
        return groupIds;
    }

    /**
     * 通过taskInfo得到groupId
     * @param taskInfo
     * @return
     */
    public static String getGroupIdByTaskInfo(TaskInfo taskInfo){
        String channelCodeEn = EnumUtil.getEnumByCode(taskInfo.getSendChannel(), ChannelType.class).getCodeEn();
        String msgCodeEn = EnumUtil.getEnumByCode(taskInfo.getMsgType(), MessageType.class).getCodeEn();
        return channelCodeEn + "." + msgCodeEn;
    }
}
