
package com.github.wenbo2018.fox.spring.config.schema;

import com.github.wenbo2018.fox.remoting.provider.config.ServerBean;
import com.github.wenbo2018.fox.remoting.provider.config.SingleServiceBean;
import com.github.wenbo2018.fox.spring.config.ReferenceBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;


public class CommonNamespaceHandler extends NamespaceHandlerSupport {

    private static Logger logger = LoggerFactory.getLogger(CommonNamespaceHandler.class);

    @Override
    public void init() {
        registerBeanDefinitionParser("server", new FoxBeanDefinitionParser(ServerBean.class, false));
        registerBeanDefinitionParser("service", new FoxBeanDefinitionParser(SingleServiceBean.class, true));
        registerBeanDefinitionParser("reference", new FoxBeanDefinitionParser(ReferenceBean.class, false));
    }
}