package com.fox.rpc.remoting.provider.config;

/**
 * Created by shenwenbo on 16/8/7.
 */
public class ProviderConfig<T> {
    private Class<?> serviceInterface;
    private String url;
    private String version;
    private T service;
    private ServerConfig serverConfig = new ServerConfig();

    public Class<?> getServiceInterface() {
        return serviceInterface;
    }

    public void setServiceInterface(Class<?> serviceInterface) {
        this.serviceInterface = serviceInterface;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
