
package com.fox.rpc.remoting.provider.config;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

import java.util.concurrent.atomic.AtomicInteger;

public class ServerBeanDefinitionParser implements BeanDefinitionParser {



	private static final Logger logger = LoggerFactory.getLogger(ServerBeanDefinitionParser.class);

	private final Class<?> beanClass;

	private final boolean required;

	public static AtomicInteger idCounter = new AtomicInteger();


	public ServerBeanDefinitionParser(Class<?> beanClass, boolean required) {
		this.beanClass = beanClass;
		this.required = required;
	}

	public BeanDefinition parse(Element element, ParserContext parserContext) {
		return parse(element, parserContext, beanClass, required);
	}

	private static BeanDefinition parse(Element element, ParserContext parserContext, Class<?> beanClass,
			boolean required) {
		RootBeanDefinition beanDefinition = new RootBeanDefinition();
		beanDefinition.setLazyInit(false);
		String id = element.getAttribute("id");
		if (StringUtils.isBlank(id)) {
			id = "pigeonServer_" + idCounter.incrementAndGet();
		}
		beanDefinition.setBeanClass(ServerBean.class);
		beanDefinition.setInitMethodName("init");
		MutablePropertyValues properties = beanDefinition.getPropertyValues();
		if (element.hasAttribute("port")) {
			properties.addPropertyValue("port",element.getAttribute("port"));
		}
		parserContext.getRegistry().registerBeanDefinition(id, beanDefinition);
		return beanDefinition;
	}
}