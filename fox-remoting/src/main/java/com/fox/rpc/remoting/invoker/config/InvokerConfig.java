package com.fox.rpc.remoting.invoker.config;

import com.fox.rpc.registry.ServiceDiscovery;

/**
 * Created by shenwenbo on 16/8/6.
 * 服务调用参数
 */
public class InvokerConfig<T> {


    private Class<T> serviceInterface;

    private String url;

    private ServiceDiscovery serviceDiscovery;


    public InvokerConfig(Class<T> serviceInterface, String url, ServiceDiscovery serviceDiscovery) {
        this.serviceInterface = serviceInterface;
        this.url = url;
        this.serviceDiscovery = serviceDiscovery;
    }

    public ServiceDiscovery getServiceDiscovery() {
        return serviceDiscovery;
    }

    public void setServiceDiscovery(ServiceDiscovery serviceDiscovery) {
        this.serviceDiscovery = serviceDiscovery;
    }

    public Class<T> getServiceInterface() {
        return serviceInterface;
    }

    public void setServiceInterface(Class<T> serviceInterface) {
        this.serviceInterface = serviceInterface;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
