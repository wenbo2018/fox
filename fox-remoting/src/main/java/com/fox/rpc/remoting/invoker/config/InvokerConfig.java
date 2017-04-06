package com.fox.rpc.remoting.invoker.config;

/**
 * Created by shenwenbo on 2016/9/30.
 */
public class InvokerConfig<T> {

    private Class<T> interfaceClass;
    private String iface;
    private String serviceName;
    private String serializer;
    private String serviceVersion;
    private String appkey;
    private String loadBalance;

    public InvokerConfig(Class<T> interfaceClass, String iface,String serviceName,String serializer) {
        this.interfaceClass = interfaceClass;
        this.iface = iface;
        this.serviceName=serviceName;
        this.serializer=serializer;
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

    public String getSerializer() {
        return serializer;
    }

    public void setSerializer(String serializer) {
        this.serializer = serializer;
    }

    public String getServiceVersion() {
        return serviceVersion;
    }

    public void setServiceVersion(String serviceVersion) {
        this.serviceVersion = serviceVersion;
    }

    public String getAppkey() {
        return appkey;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

    public String getLoadBalance() {
        return loadBalance;
    }

    public void setLoadBalance(String loadBalance) {
        this.loadBalance = loadBalance;
    }
}
