package com.java.austin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AustinApplication {
    public static void main(String[] args) {

        /**
         * 如果需要启动Apollo动态配置
         * 1、启动Apollo
         * 2、 讲application.properties配置文件的austin，Apollo.enabled 改为true
         * 3. 下方的property替换真实的ip和port
         */
        SpringApplication.run(AustinApplication.class,args);
    }
}
