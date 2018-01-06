
package com.github.wenbo2018.fox.spring.config.schema;


import com.github.wenbo2018.fox.remoting.provider.config.ServerConfig;
import com.github.wenbo2018.fox.remoting.provider.config.SingleServiceBean;
import com.github.wenbo2018.fox.spring.config.ReferenceBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

import java.util.concurrent.atomic.AtomicInteger;

public class FoxBeanDefinitionParser implements BeanDefinitionParser {


    private final Class<?> beanClass;

    private final boolean required;

    public static AtomicInteger idCounter = new AtomicInteger();

    public FoxBeanDefinitionParser(Class<?> beanClass, boolean required) {
        this.beanClass = beanClass;
        this.required = required;
    }

    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        return parse(element, parserContext, beanClass, required);
    }

    private static BeanDefinition parse(Element element, ParserContext parserContext, Class<?> beanClass,
                                        boolean required) {
        RootBeanDefinition beanDefinition = new RootBeanDefinition();
        beanDefinition.setLazyInit(false);
        String id = element.getAttribute("id");
        if (StringUtils.isBlank(id)) {
            id = "foxRef_" + idCounter.incrementAndGet();
        }
        if (ReferenceBean.class.equals(beanClass)) {
            beanDefinition.setBeanClass(ReferenceBean.class);
            beanDefinition.setInitMethodName("init");
            MutablePropertyValues properties = beanDefinition.getPropertyValues();
            if (element.hasAttribute("iface")) {
                properties.addPropertyValue("iface", element.getAttribute("iface"));
            }
            if (element.hasAttribute("serviceName")) {
                properties.addPropertyValue("serviceName", element.getAttribute("serviceName"));
            }

            if (element.hasAttribute("timeout")) {
                properties.addPropertyValue("timeout", element.getAttribute("timeout"));
            }

            if (element.hasAttribute("serializer")) {
                properties.addPropertyValue("serializer", element.getAttribute("serializer"));
            }
        } else if (SingleServiceBean.class.equals(beanClass)) {
            beanDefinition.setBeanClass(SingleServiceBean.class);
            beanDefinition.setInitMethodName("init");
            MutablePropertyValues properties = beanDefinition.getPropertyValues();
            String ref = element.getAttribute("ref");
            if (!parserContext.getRegistry().containsBeanDefinition(ref)) {
                throw new IllegalStateException("service must have a reference to bean:" + ref);
            }
            properties.addPropertyValue("serviceImpl", new RuntimeBeanReference(ref));
            if (element.hasAttribute("server")) {
                String server = element.getAttribute("server");
                if (!parserContext.getRegistry().containsBeanDefinition(server)) {
                    throw new IllegalStateException("service must have a reference to bean:" + server);
                }
                properties.addPropertyValue("serverConfig", new RuntimeBeanReference(server));
            }
            String serviceName = null;
            if (element.hasAttribute("serviceName")) {
                serviceName = element.getAttribute("serviceName");
                properties.addPropertyValue("serviceName", serviceName);
            }
        } else if (ServerConfig.class.equals(beanClass)) {
            beanDefinition.setBeanClass(ServerConfig.class);
            beanDefinition.setInitMethodName("init");
            MutablePropertyValues properties = beanDefinition.getPropertyValues();
            if (element.hasAttribute("port")) {
                properties.addPropertyValue("port",element.getAttribute("port"));
            }
            if (element.hasAttribute("ip")) {
                properties.addPropertyValue("ip",element.getAttribute("ip"));
            }
            if (element.hasAttribute("registerPort")) {
                properties.addPropertyValue("registerPort",element.getAttribute("port"));
            }
        }


        parserContext.getRegistry().registerBeanDefinition(id, beanDefinition);

        return beanDefinition;
    }

}