package com.austin.web;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;

import java.util.Date;

import static org.aspectj.bridge.Version.getTime;

/**
 * @function
 * @Author hcc
 */
public class ttt {
    public static void main(String[] args) {
        DateTime dateTime = DateUtil.endOfDay(new Date());
        long time = dateTime.getTime();
        long current = DateUtil.current();
        long current1 =  time- current;

        System.out.println(current1/1000);
    }
}
