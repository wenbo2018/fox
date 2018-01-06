package com.github.wenbo2018.fox.remoting.provider.config;

import com.github.wenbo2018.fox.remoting.ServiceFactory;

/**
 * Created by shenwenbo on 2017/3/9.
 */
public class SingleServiceBean<T> {

    private String serviceInterface;

    private String serviceName;

    private ServerConfig serverConfig;

    private Object serviceImpl;


    public void init() throws Exception {
        if (serviceImpl == null) {
            throw new IllegalArgumentException("service not found:{}" + this);
        }
        ProviderConfig providerConfig = new ProviderConfig();
        providerConfig.setService(serviceImpl);
        providerConfig.setServiceName(serviceName);
        if (serverConfig != null) {
            providerConfig.setServerConfig(serverConfig.init());
        } else {
            providerConfig.setServerConfig(serverConfig);
        }
        ServiceFactory.addService(providerConfig);
    }


    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }


    public ServerConfig getServerConfig() {
        return serverConfig;
    }

    public void setServerConfig(ServerConfig serverConfig) {
        this.serverConfig = serverConfig;
    }

    public Object getServiceImpl() {
        return serviceImpl;
    }

    public void setServiceImpl(Object serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    public String getServiceInterface() {
        return serviceInterface;
    }

    public void setServiceInterface(String serviceInterface) {
        this.serviceInterface = serviceInterface;
    }
}
