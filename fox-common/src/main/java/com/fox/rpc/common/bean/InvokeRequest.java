package com.fox.rpc.common.bean;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 封装 RPC 请求
 */
public class InvokeRequest implements Serializable{

    private static final long serialVersionUID = 4242901101810773960L;
    private String requestId;
    private String interfaceName;
    private String serviceVersion;
    private String methodName;
    private Class<?>[] parameterTypes;
    private Object[] parameters;

    private String serialize;

    private String serviceName;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String className) {
        this.interfaceName = className;
    }

    public String getServiceVersion() {
        return serviceVersion;
    }

    public void setServiceVersion(String serviceVersion) {
        this.serviceVersion = serviceVersion;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getSerialize() {
        return serialize;
    }

    public void setSerialize(String serialize) {
        this.serialize = serialize;
    }

    @Override
    public String toString() {
        return "InvokeRequest{" +
                "requestId='" + requestId + '\'' +
                ", interfaceName='" + interfaceName + '\'' +
                ", serviceVersion='" + serviceVersion + '\'' +
                ", methodName='" + methodName + '\'' +
                ", parameterTypes=" + Arrays.toString(parameterTypes) +
                ", parameters=" + Arrays.toString(parameters) +
                ", serviceName='" + serviceName + '\'' +
                '}';
    }
}
