
package com.fox.rpc.remoting.provider.config;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;
import java.util.concurrent.atomic.AtomicInteger;

public class ServiceBeanDefinitionParser implements BeanDefinitionParser {


	private static final Logger logger = LoggerFactory.getLogger(ServiceBeanDefinitionParser.class);

	private final Class<?> beanClass;

	private final boolean required;

	public static AtomicInteger idCounter = new AtomicInteger();

	public ServiceBeanDefinitionParser(Class<?> beanClass, boolean required) {
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
			id = "pigeonService_" + idCounter.incrementAndGet();
		}
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
			properties.addPropertyValue("serverBean", new RuntimeBeanReference(server));
		}
		String serviceName = null;
		if (element.hasAttribute("serviceName")) {
			serviceName = element.getAttribute("serviceName");
			properties.addPropertyValue("serviceName", serviceName);
		}
		parserContext.getRegistry().registerBeanDefinition(id, beanDefinition);
		return beanDefinition;
	}



}