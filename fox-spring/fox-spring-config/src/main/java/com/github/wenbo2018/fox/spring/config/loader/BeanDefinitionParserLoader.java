package com.github.wenbo2018.fox.spring.config.loader;

import org.springframework.beans.factory.xml.BeanDefinitionParser;

import java.util.Map;

public interface BeanDefinitionParserLoader {

    Map<String, BeanDefinitionParser> loadBeanDefinitionParsers();

}
