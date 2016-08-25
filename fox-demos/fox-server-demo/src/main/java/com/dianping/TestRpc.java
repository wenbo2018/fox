package com.dianping;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by shenwenbo on 16/8/23.
 */
public class TestRpc {
    public static void main(String[] args) {
        System.out.println("init");
        new ClassPathXmlApplicationContext("bean.xml");
    }
}
