package com.github.wenbo2018.fox.demo.client;

import com.github.wenbo2018.fox.config.api.HelloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * Created by shenwenbo on 16/8/25.
 */

public class TestClient {


    private static final Logger logger = LoggerFactory.getLogger(TestClient.class);

        static  int i=100000;
        public static void main(String[] args) {

            ApplicationContext context= new ClassPathXmlApplicationContext("application.xml");
            final HelloService helloService=(HelloService)context.getBean("helloService");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (i>0) {
                        try {
                            System.err.println(helloService.incer());
                        i--;
                        logger.debug("thread info:{}", Thread.currentThread().getName());
                            Thread.sleep(2000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }

}
