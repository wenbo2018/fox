package com.fox.rpc.remoting.common;

import org.springframework.beans.factory.xml.BeanDefinitionParser;

import java.util.Map;

public interface BeanDefinitionParserLoader {

    Map<String, BeanDefinitionParser> loadBeanDefinitionParsers();

}
