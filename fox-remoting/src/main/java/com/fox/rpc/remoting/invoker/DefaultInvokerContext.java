package com.fox.rpc.remoting.invoker;

import com.fox.rpc.remoting.invoker.api.Client;
import com.fox.rpc.remoting.invoker.config.InvokerConfig;

/**
 * Created by shenwenbo on 2017/4/1.
 */
public class DefaultInvokerContext implements InvokeContext {

    private InvokerConfig<?> invokerConfig;
    private String methodName;
    private Class<?>[] parameterTypes;
    private Object[] arguments;
    private Client client;

    @Override
    public InvokerConfig getInvokerConfig() {
        return this.invokerConfig;
    }

    @Override
    public String getMethodName() {
        return this.methodName;
    }

    @Override
    public Class<?>[] getParameterTypes() {
        return this.parameterTypes;
    }

    @Override
    public Object[] getArguments() {
        return this.arguments;
    }

    @Override
    public Client getClient() {
        return this.client;
    }

    @Override
    public void setClient(Client client) {
       this.client=client;
    }

    public DefaultInvokerContext(InvokerConfig<?> invokerConfig, String methodName, Class<?>[] parameterTypes, Object[] arguments) {
        this.invokerConfig = invokerConfig;
        this.methodName = methodName;
        this.parameterTypes = parameterTypes;
        this.arguments = arguments;
    }
}
