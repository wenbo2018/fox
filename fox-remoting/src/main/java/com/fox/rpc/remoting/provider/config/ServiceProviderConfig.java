package com.fox.rpc.remoting.provider.config;


import com.fox.rpc.registry.RemotingServiceRegistry;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shenwenbo on 16/8/7.
 */
public class ServiceProviderConfig<T> {

    private Map<String, Object> handlerMap = new HashMap<String, Object>();

    private String servicePort;

    private String serviceAddress;

    private String serviceName;

    private RemotingServiceRegistry remotingServiceRegistry;

    public String getServicePort() {
        return servicePort;
    }

    public void setServicePort(String servicePort) {
        this.servicePort = servicePort;
    }

    public String getServiceAddress() {
        return serviceAddress;
    }

    public void setServiceAddress(String serviceAddress) {
        this.serviceAddress = serviceAddress;
    }

    public Map<String, Object> getHandlerMap() {
        return handlerMap;
    }

    public void setHandlerMap(Map<String, Object> handlerMap) {
        this.handlerMap = handlerMap;
    }

    public RemotingServiceRegistry getRemotingServiceRegistry() {
        return remotingServiceRegistry;
    }

    public void setRemotingServiceRegistry(RemotingServiceRegistry remotingServiceRegistry) {
        this.remotingServiceRegistry = remotingServiceRegistry;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
}
