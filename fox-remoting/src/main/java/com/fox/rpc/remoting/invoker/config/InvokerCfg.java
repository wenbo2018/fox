package com.fox.rpc.remoting.invoker.config;

import com.fox.rpc.registry.RemotingServiceDiscovery;

/**
 * Created by shenwenbo on 16/8/6.
 * RPC 协议
 */
public class InvokerCfg<T> {


    private Class<T> serviceInterface;

    private String url;

    private RemotingServiceDiscovery serviceDiscovery;


    public InvokerCfg(Class<T> serviceInterface, String url, RemotingServiceDiscovery serviceDiscovery) {
        this.serviceInterface = serviceInterface;
        this.url = url;
        this.serviceDiscovery = serviceDiscovery;
    }

    public RemotingServiceDiscovery getServiceDiscovery() {
        return serviceDiscovery;
    }

    public void setServiceDiscovery(RemotingServiceDiscovery serviceDiscovery) {
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
