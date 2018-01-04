package com.github.wenbo2018.fox.remoting.provider.config;

import com.github.wenbo2018.fox.remoting.ServiceFactory;

/**
 * Created by shenwenbo on 2017/3/9.
 */
public class SingleServiceBean<T> {

    private String serviceInterface;

    private String serviceName;

    private ServerBean serverBean;

    private Object serviceImpl;


    public void init() throws Exception {
        if (serviceImpl == null) {
            throw new IllegalArgumentException("service not found:{}" + this);
        }
        ProviderConfig providerConfig = new ProviderConfig();
        providerConfig.setService(serviceImpl);
        providerConfig.setServiceName(serviceName);
        if (serverBean != null) {
            providerConfig.setServerConfig(serverBean.init());
        } else {
            providerConfig.setServerConfig(serverBean.getServerConfig());
        }
        ServiceFactory.addService(providerConfig);
    }


    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }


    public ServerBean getServerBean() {
        return serverBean;
    }

    public void setServerBean(ServerBean serverBean) {
        this.serverBean = serverBean;
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
