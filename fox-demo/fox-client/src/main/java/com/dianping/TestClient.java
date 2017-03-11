package com.dianping;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * Created by shenwenbo on 16/8/25.
 */

public class TestClient {
        static  int i=100;

        public static void main(String[] args) {

            ApplicationContext context= new ClassPathXmlApplicationContext("bean.xml");
            final HelloService helloService=(HelloService)context.getBean("helloService");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (i>0) {
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
