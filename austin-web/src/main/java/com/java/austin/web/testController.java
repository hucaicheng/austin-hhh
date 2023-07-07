package com.java.austin.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class testController {

    @RequestMapping("/test")
    public String test(){
        log.info("test接口被调用, 生成日志");
        System.out.println("sout打印对比");
        return "测试springBoot启动";
    }
}
