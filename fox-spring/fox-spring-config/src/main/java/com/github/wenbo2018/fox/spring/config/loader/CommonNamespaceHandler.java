
package com.github.wenbo2018.fox.spring.config.loader;

import com.github.wenbo2018.fox.common.extension.UserServiceLoader;
import com.github.wenbo2018.fox.remoting.common.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

import java.util.List;
import java.util.Map;

public class CommonNamespaceHandler extends NamespaceHandlerSupport {

	private static Logger logger= LoggerFactory.getLogger(CommonNamespaceHandler.class);

	@Override
	public void init() {
		System.out.println("\n");
		System.out.println(Constants.FOX_ICON);
		System.out.println("\n");
		List<BeanDefinitionParserLoader> loaders = UserServiceLoader.getExtensionList(BeanDefinitionParserLoader.class);
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