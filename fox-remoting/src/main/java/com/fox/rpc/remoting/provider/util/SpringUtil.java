package com.fox.rpc.remoting.provider.util;

import com.fox.rpc.remoting.provider.config.ProviderConfig;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
/**
 * Created by shenwenbo on 2016/9/28.
 */
public class SpringUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    public static Object fetchSpringBean(ProviderConfig config) {
          return  applicationContext.getBean(config.getServiceName());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }
}
