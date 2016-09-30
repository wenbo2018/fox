package com.fox.rpc.remoting.invoker.config;

/**
 * Created by shenwenbo on 2016/9/30.
 */
public class InvokerConfig<T> {

    //接口class对象
    private Class<T> interfaceClass;
    //接口包信息
    private String iface;
    //服务名称
    private String serviceName;

    public InvokerConfig(Class<T> interfaceClass, String iface,String serviceName) {
        this.interfaceClass = interfaceClass;
        this.iface = iface;
        this.serviceName=serviceName;
    }

    public Class<T> getInterfaceClass() {
        return interfaceClass;
    }

    public void setInterfaceClass(Class<T> interfaceClass) {
        this.interfaceClass = interfaceClass;
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
