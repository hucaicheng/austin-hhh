package com.austin.web;

import com.alibaba.fastjson.JSON;
import com.austin.support.dao.MessageTemplateDao;
import com.austin.support.domain.MessageTemplate;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class testController {

    @Autowired
    private MessageTemplateDao messageTemplateDao;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @PostMapping("/test")
    public String test(){
        log.info("test接口被调用, 生成日志");
        System.out.println("sout打印对比");
        return "测试springBoot启动";
    }

    @RequestMapping("/database")
    public String testDatabase(){
        List<MessageTemplate> list = messageTemplateDao.findAllByIsDeletedEqualsOrderByUpdatedDesc(0, PageRequest.of(0,10));
        return JSON.toJSONString(list);
    }

    @RequestMapping(value = "/redis", method = RequestMethod.POST)
    public String testRedis(){
        stringRedisTemplate.opsForValue().set("value1","test");
        return stringRedisTemplate.opsForValue().get("value1");
    }

}
