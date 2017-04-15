package com.github.wenbo2018.fox.demo.server;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by shenwenbo on 16/8/23.
 */
public class TestServer {
    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("bean.xml");
    }
}
