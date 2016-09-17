package com.dianping;

import com.dianping.dto.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by shenwenbo on 16/8/25.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:bean.xml")
public class HelloServiceTest {

    @Resource
    private HelloService helloService;

    @Test
    public void test() {
        System.out.println(helloService.hello("shenwenbo"));
        System.out.println(helloService.hello("wngayuehua"));
        User user=new User();
        user.setPassword("123");
        user.setUsername("wenbo2018");
        System.out.println(helloService.changeUser(user).toString());
    }

}
