package com.fox.rpc.spring;

import com.fox.rpc.InvokerConfig;
import com.fox.rpc.ServiceFactory;
import com.fox.rpc.registry.ServiceDiscovery;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by shenwenbo on 16/7/21.
 */
public class ProxyBeanFactory {
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
        /***封装服务调用参数***/
        InvokerConfig invokerConfig=
                new InvokerConfig(this.objType,this.serviceName,this.serviceDiscovery);
        this.obj= ServiceFactory.getService(invokerConfig);
    }
}
