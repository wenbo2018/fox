
package com.fox.rpc.remoting.invoker.config.spring;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

import java.util.concurrent.atomic.AtomicInteger;

public class ReferenceBeanDefinitionParser implements BeanDefinitionParser {


	private final Class<?> beanClass;

	private final boolean required;

	public static AtomicInteger idCounter = new AtomicInteger();

	public ReferenceBeanDefinitionParser(Class<?> beanClass, boolean required) {
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
			id = "pigeonRef_" + idCounter.incrementAndGet();
		}
		beanDefinition.setBeanClass(ReferenceProxy.class);
		beanDefinition.setInitMethodName("init");

		MutablePropertyValues properties = beanDefinition.getPropertyValues();
		if (element.hasAttribute("iface")) {
			properties.addPropertyValue("iface",element.getAttribute("iface"));
		}
		if (element.hasAttribute("serviceName")) {
			properties.addPropertyValue("serviceName",element.getAttribute("serviceName"));
		}
		if (element.hasAttribute("serializer")) {
			properties.addPropertyValue("serializer",element.getAttribute("serializer"));
		}
		parserContext.getRegistry().registerBeanDefinition(id, beanDefinition);

		return beanDefinition;
	}

}