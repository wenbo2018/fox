
package com.github.wenbo2018.fox.spring.config.schema;

import com.github.wenbo2018.fox.remoting.provider.config.ServerConfig;
import com.github.wenbo2018.fox.remoting.provider.config.ServiceConfig;
import com.github.wenbo2018.fox.spring.config.ReferenceBean;
import com.github.wenbo2018.fox.spring.config.RegistryConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;


public class CommonNamespaceHandler extends NamespaceHandlerSupport {

    private static Logger logger = LoggerFactory.getLogger(CommonNamespaceHandler.class);

    @Override
    public void init() {
        registerBeanDefinitionParser("register", new FoxBeanDefinitionParser(RegistryConfig.class, false));
        registerBeanDefinitionParser("server", new FoxBeanDefinitionParser(ServerConfig.class, false));
        registerBeanDefinitionParser("service", new FoxBeanDefinitionParser(ServiceConfig.class, true));
        registerBeanDefinitionParser("reference", new FoxBeanDefinitionParser(ReferenceBean.class, false));
    }
}