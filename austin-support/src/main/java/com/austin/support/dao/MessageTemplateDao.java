package com.austin.support.dao;

import com.austin.support.domain.MessageTemplate;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author hucaicheng
 * @Date 2023/7/8 0008 15:06
 * @Description: 消息模板
 */
public interface MessageTemplateDao extends JpaRepository<MessageTemplate,Long> {

    /**
     * 查询 列表(分页)
     * @param deleted 0:未删除 1:删除
     * @return
     */
    List<MessageTemplate> findAllByIsDeletedEqualsOrderByUpdatedDesc(Integer deleted, Pageable pageable);

    /**
     * 统计未删除的条数
     * @param deleted
     * @return
     */
    Long countByIsDeletedEquals(Integer deleted);
}
