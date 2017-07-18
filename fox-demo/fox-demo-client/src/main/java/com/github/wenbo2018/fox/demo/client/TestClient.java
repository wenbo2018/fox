package com.github.wenbo2018.fox.demo.client;

import com.github.wenbo2018.fox.demo.api.HelloService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * Created by shenwenbo on 16/8/25.
 */

public class TestClient {

    static int i = 400;

    public static void main(String[] args) {

        ApplicationContext context = new ClassPathXmlApplicationContext("application.xml");
        final HelloService helloService = (HelloService) context.getBean("helloService");
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (i > 0) {
                    System.err.println(helloService.incer());
                    i--;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

}
