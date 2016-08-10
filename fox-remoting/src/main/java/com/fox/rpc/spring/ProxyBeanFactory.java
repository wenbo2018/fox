package com.fox.rpc.spring;

import com.fox.rpc.common.util.ClassUtils;
import com.fox.rpc.registry.ServiceDiscovery;
import com.fox.rpc.remoting.ServiceFactory;
import com.fox.rpc.remoting.invoker.config.InvokerConfig;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.FactoryBean;

/**
 * Created by shenwenbo on 16/7/21.
 */
public class ProxyBeanFactory implements FactoryBean{
    /**
     * 服务名称
     */
    private String serviceName;
    /**
     * 服务接口名称
     */
    private String iface;
    /***
     * 服务代理对象;
     */
    private Object obj;

    /**
     * 接口类型
     */
    private Class<?>  objType;

    private ClassLoader classLoader;

    /**
     * 服务发现
     */
    private ServiceDiscovery serviceDiscovery;

    /**
     * Spring 初始化方法
     */
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
        this.obj= ServiceFactory.getService(invokerConfig);
    }

    @Override
    public Object getObject() throws Exception {
        return obj;
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
