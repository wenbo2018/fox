
package com.fox.rpc.remoting.common;

import com.fox.rpc.common.extension.UserServiceLoader;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

import java.util.List;
import java.util.Map;

public class CommonNamespaceHandler extends NamespaceHandlerSupport {


	@Override
	public void init() {
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