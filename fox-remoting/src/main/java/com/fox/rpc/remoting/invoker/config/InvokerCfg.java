package com.fox.rpc.remoting.invoker.config;

import com.fox.rpc.registry.RemotingServiceDiscovery;

/**
 * Created by shenwenbo on 16/8/6.
 * RPC 协议
 */
public class InvokerCfg<T> {


    private Class<T> serviceInterface;

    private String iface;

    private RemotingServiceDiscovery serviceDiscovery;

    private String serviceName;

    public InvokerCfg(Class<T> serviceInterface, String iface,String serviceName, RemotingServiceDiscovery serviceDiscovery) {
        this.serviceInterface = serviceInterface;
        this.iface = iface;
        this.serviceDiscovery = serviceDiscovery;
        this.serviceName=serviceName;
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
}
