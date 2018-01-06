
package com.github.wenbo2018.fox.spring.config;

import com.github.wenbo2018.fox.spring.config.loader.BeanDefinitionParserLoader;
import org.springframework.beans.factory.xml.BeanDefinitionParser;

import java.util.HashMap;
import java.util.Map;


public class InvokerBeanDefinitionParserLoader implements BeanDefinitionParserLoader {

    @Override
    public Map<String, BeanDefinitionParser> loadBeanDefinitionParsers() {
        Map<String, BeanDefinitionParser> parsers = new HashMap<String, BeanDefinitionParser>();
        parsers.put("reference", new ReferenceBeanDefinitionParser(ReferenceConfig.class, false));
        return parsers;
    }

}