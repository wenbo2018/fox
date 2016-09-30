package com.dianping;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by shenwenbo on 16/8/25.
 */

public class TestClient {

        public static void main(String[] args) {
            ApplicationContext context= new ClassPathXmlApplicationContext("bean.xml");
            HelloService helloService=(HelloService)context.getBean("helloService");
            System.out.println(helloService.hello("shenwenbo"));
        }

}
