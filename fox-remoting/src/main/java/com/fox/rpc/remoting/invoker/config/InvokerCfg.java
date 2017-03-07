package com.fox.rpc.remoting.invoker.config;

/**
 * Created by shenwenbo on 16/8/6.
 * RPC 协议
 */
public class InvokerCfg<T> {


    private Class<T> serviceInterface;

    private String iface;


    private String serviceName;

    public InvokerCfg(Class<T> serviceInterface, String iface,String serviceName) {
        this.serviceInterface = serviceInterface;
        this.iface = iface;
        this.serviceName=serviceName;
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
