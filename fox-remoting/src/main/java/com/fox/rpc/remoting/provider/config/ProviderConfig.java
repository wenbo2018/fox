package com.fox.rpc.remoting.provider.config;

/**
 * Created by shenwenbo on 2016/9/28.
 */
public class ProviderConfig<T> {

    private Class<?> serviceInterface;

    private String serviceName;

    private String version;

    private T service;

    private ServerConfig serverConfig;


    public ProviderConfig(T service) {
        this.setService(service);
    }

    public Class<?> getServiceInterface() {
        return serviceInterface;
    }

    public void setServiceInterface(Class<?> serviceInterface) {
        this.serviceInterface = serviceInterface;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public T getService() {
        return service;
    }

    public void setService(T service) {
        this.service = service;
    }

    public ServerConfig getServerConfig() {
        return serverConfig;
    }

    public void setServerConfig(ServerConfig serverConfig) {
        this.serverConfig = serverConfig;
    }
}
