package com.dianping;

import com.dianping.dto.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shenwenbo on 16/8/25.
 */

public class TestClient {

        public static void main(String[] args) {
            ApplicationContext context= new ClassPathXmlApplicationContext("bean.xml");
            HelloService helloService=(HelloService)context.getBean("helloService");
            //System.out.println(helloService.hello("test","rpc"));
            List<User> users=new ArrayList<User>();
            for (int i=0;i<1;i++) {
                User user=new User();
                user.setPassword("123");
                user.setUsername("wenbo2018");
                users.add(helloService.changeUser(user));
            }
            for (User u:users)
            System.out.println(u.toString());
        }

}
