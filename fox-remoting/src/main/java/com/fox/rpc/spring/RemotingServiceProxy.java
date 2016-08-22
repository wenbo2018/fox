package com.fox.rpc.spring;

import com.fox.rpc.common.util.ClassUtils;
import com.fox.rpc.registry.RemotingServiceDiscovery;
import com.fox.rpc.remoting.ServiceFactory;
import com.fox.rpc.remoting.invoker.config.InvokerConfig;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.FactoryBean;

/**
 * Created by shenwenbo on 16/7/21.
 */
public class RemotingServiceProxy implements FactoryBean{

    private String serviceName;

    private String iface;

    private Object service;

    private Class<?>  objType;

    private ClassLoader classLoader;

    private RemotingServiceDiscovery serviceDiscovery;

    public void init() {
        if (StringUtils.isBlank(iface)) {
            throw new IllegalArgumentException("invalid interface:" + iface);
        }
        //获取接口类型
        try {
            this.objType = ClassUtils.loadClass(classLoader, this.iface.trim());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        /***封装服务调用协议***/
        InvokerConfig invokerConfig=
                new InvokerConfig(this.objType,this.serviceName,this.serviceDiscovery);
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
}
