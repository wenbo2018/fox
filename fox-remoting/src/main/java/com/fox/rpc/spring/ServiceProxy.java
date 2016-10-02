package com.fox.rpc.spring;

import com.fox.rpc.common.common.Constants;
import com.fox.rpc.common.util.ClassUtils;
import com.fox.rpc.remoting.ServiceFactory;
import com.fox.rpc.remoting.invoker.config.InvokerConfig;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.FactoryBean;

/**
 * Created by shenwenbo on 16/7/21.
 */
public class ServiceProxy implements FactoryBean{

    private static final org.apache.log4j.Logger LOGGER= org.apache.log4j.Logger.getLogger(ServiceProxy.class);

    private String serviceName;

    private String serializer = Constants.PROTOSTUFF_SERIALIEE;

    private String iface;

    private Object service;

    private Class<?>  interfaceClass;

    private ClassLoader classLoader;


    private String zkAddress;

    public void init() {
        LOGGER.info("service init:"+serviceName);
        if (StringUtils.isBlank(iface)) {
            throw new IllegalArgumentException("invalid interface:" + iface);
        }
        try {
            this.interfaceClass = ClassUtils.loadClass(classLoader, this.iface.trim());
        } catch (ClassNotFoundException e) {
            LOGGER.error("not class found",e);
        }
        InvokerConfig invokerConfig= new InvokerConfig(this.interfaceClass,this.iface,this.serviceName,this.serializer);
        this.service= ServiceFactory.getService(invokerConfig);
    }

    @Override
    public Object getObject() throws Exception {
        return service;
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }

    @Override
    public boolean isSingleton() {
        return true;
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


    public String getZkAddress() {
        return zkAddress;
    }

    public void setZkAddress(String zkAddress) {
        this.zkAddress = zkAddress;
    }

    public String getSerializer() {
        return serializer;
    }

    public void setSerializer(String serializer) {
        this.serializer = serializer;
    }
}
