
package com.fox.rpc.remoting.invoker.config.spring;

import com.fox.rpc.remoting.common.BeanDefinitionParserLoader;
import com.fox.rpc.spring.ServiceProxy;
import org.springframework.beans.factory.xml.BeanDefinitionParser;

import java.util.HashMap;
import java.util.Map;


public class InvokerBeanDefinitionParserLoader implements BeanDefinitionParserLoader {

    @Override
    public Map<String, BeanDefinitionParser> loadBeanDefinitionParsers() {
        Map<String, BeanDefinitionParser> parsers = new HashMap<String, BeanDefinitionParser>();
        parsers.put("invoker", new ReferenceBeanDefinitionParser(ServiceProxy.class, false));
        return parsers;
    }

}