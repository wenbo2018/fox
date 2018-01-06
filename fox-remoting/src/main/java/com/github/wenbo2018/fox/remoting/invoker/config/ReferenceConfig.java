package com.github.wenbo2018.fox.remoting.invoker.config;

import com.github.wenbo2018.fox.common.common.FoxConstants;
import com.github.wenbo2018.fox.common.util.ClassUtils;
import com.github.wenbo2018.fox.remoting.ServiceFactory;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by wenbo.shen on 2018/1/6.
 */
public class ReferenceConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReferenceConfig.class);

    private String serviceName;

    private String serializer = FoxConstants.PROTOSTUFF_SERIALIEE;

    private String iface;

    private Object service;

    private String timeout;

    private Class<?> interfaceClass;

    private ClassLoader classLoader;


    public void init() {
        if (StringUtils.isBlank(iface)) {
            throw new IllegalArgumentException("invalid interface:" + iface);
        }
        try {
            this.interfaceClass = ClassUtils.loadClass(classLoader, this.iface.trim());
        } catch (ClassNotFoundException e) {
            LOGGER.error("class:{} not found:{}", interfaceClass, e);
        }
        InvokerConfig invokerConfig = new InvokerConfig(
                this.interfaceClass,
                this.iface,
                this.serviceName,
                this.serializer,
                NumberUtils.toInt(timeout) > 0 ? NumberUtils.toInt(timeout) : FoxConstants.TIME_OUT);
        this.service = ServiceFactory.getService(invokerConfig);
    }

    public String getIface() {
        return iface;
    }

    public void setIface(String iface) {
        this.iface = iface;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Object getService() {
        return service;
    }

    public void setService(Object service) {
        this.service = service;
    }

    public Class<?> getInterfaceClass() {
        return interfaceClass;
    }

    public void setInterfaceClass(Class<?> interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    public ClassLoader getClassLoader() {
        return classLoader;
    }

    public void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public String getSerializer() {
        return serializer;
    }

    public void setSerializer(String serializer) {
        this.serializer = serializer;
    }

    public String getTimeout() {
        return timeout;
    }

    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }
}
