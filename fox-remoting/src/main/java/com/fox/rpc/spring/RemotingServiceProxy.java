package com.fox.rpc.spring;

import com.fox.rpc.SpiServiceLoader;
import com.fox.rpc.common.util.ClassUtils;
import com.fox.rpc.registry.RemotingServiceDiscovery;
import com.fox.rpc.remoting.ServiceFactory;
import com.fox.rpc.remoting.invoker.config.InvokerCfg;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.FactoryBean;

/**
 * Created by shenwenbo on 16/7/21.
 */
public class RemotingServiceProxy implements FactoryBean{

    private static final org.apache.log4j.Logger LOGGER= org.apache.log4j.Logger.getLogger(RemotingServiceProxy.class);


    private String serviceName;

    private String iface;

    private Object service;

    private Class<?>  interfaceClass;

    private ClassLoader classLoader;

    private RemotingServiceDiscovery serviceDiscovery;

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
        this.serviceDiscovery= SpiServiceLoader.newExtension(RemotingServiceDiscovery.class);
        serviceDiscovery.init(this.zkAddress);
        InvokerCfg invokerConfig= new InvokerCfg(this.interfaceClass,this.iface,this.serviceName,this.serviceDiscovery);
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

    public RemotingServiceDiscovery getServiceDiscovery() {
        return serviceDiscovery;
    }

    public void setServiceDiscovery(RemotingServiceDiscovery serviceDiscovery) {
        this.serviceDiscovery = serviceDiscovery;
    }

    public String getZkAddress() {
        return zkAddress;
    }

    public void setZkAddress(String zkAddress) {
        this.zkAddress = zkAddress;
    }
}
