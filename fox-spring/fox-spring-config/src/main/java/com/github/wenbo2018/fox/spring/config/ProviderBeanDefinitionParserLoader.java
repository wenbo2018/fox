
package com.github.wenbo2018.fox.spring.config;

import com.github.wenbo2018.fox.spring.config.loader.BeanDefinitionParserLoader;
import com.github.wenbo2018.fox.remoting.provider.config.ServerBean;
import com.github.wenbo2018.fox.remoting.provider.config.SingleServiceBean;
import org.springframework.beans.factory.xml.BeanDefinitionParser;

import java.util.HashMap;
import java.util.Map;


public class ProviderBeanDefinitionParserLoader implements BeanDefinitionParserLoader {

	@Override
	public Map<String, BeanDefinitionParser> loadBeanDefinitionParsers() {
		Map<String, BeanDefinitionParser> parsers = new HashMap<String, BeanDefinitionParser>();
		parsers.put("service", new ServiceBeanDefinitionParser(SingleServiceBean.class, true));
		parsers.put("server", new ServerBeanDefinitionParser(ServerBean.class, false));
		return parsers;
	}

}