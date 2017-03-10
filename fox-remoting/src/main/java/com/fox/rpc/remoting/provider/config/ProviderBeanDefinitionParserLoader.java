/**
 * Dianping.com Inc.
 * Copyright (c) 2003-2013 All Rights Reserved.
 */
package com.fox.rpc.remoting.provider.config;

import com.fox.rpc.remoting.common.BeanDefinitionParserLoader;
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