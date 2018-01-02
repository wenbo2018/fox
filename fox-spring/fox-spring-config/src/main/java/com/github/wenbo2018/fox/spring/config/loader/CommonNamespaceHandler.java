
package com.github.wenbo2018.fox.spring.config.loader;

import com.github.wenbo2018.fox.common.extension.ExtensionServiceLoader;
import com.github.wenbo2018.fox.remoting.common.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

import java.util.List;
import java.util.Map;

public class CommonNamespaceHandler extends NamespaceHandlerSupport {

    private static Logger logger = LoggerFactory.getLogger(CommonNamespaceHandler.class);

    @Override
    public void init() {
        System.out.println(Constants.FOX_ICON);
        List<BeanDefinitionParserLoader> loaders = ExtensionServiceLoader.getExtensionList(BeanDefinitionParserLoader.class);
        if (loaders != null) {
            for (BeanDefinitionParserLoader loader : loaders) {
                Map<String, BeanDefinitionParser> parsers = loader.loadBeanDefinitionParsers();
                if (parsers != null) {
                    for (String key : parsers.keySet()) {
                        registerBeanDefinitionParser(key, parsers.get(key));
                    }
                }
            }
        }
    }

}