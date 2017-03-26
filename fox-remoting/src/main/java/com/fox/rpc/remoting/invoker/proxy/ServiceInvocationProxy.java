package com.fox.rpc.remoting.invoker.proxy;

import com.fox.rpc.common.bean.InvokeRequest;
import com.fox.rpc.common.bean.InvokeResponse;
import com.fox.rpc.remoting.invoker.RemoteServiceCall;
import com.fox.rpc.remoting.invoker.ServiceInvocationRepository;
import com.fox.rpc.remoting.invoker.ClientManager;
import com.fox.rpc.remoting.invoker.api.Client;
import com.fox.rpc.remoting.invoker.async.CallbackFuture;
import com.fox.rpc.remoting.invoker.async.RemoteInvocationBean;
import com.fox.rpc.remoting.invoker.config.InvokerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * Created by shenwenbo on 16/8/23.
 */
public class ServiceInvocationProxy<T> implements InvocationHandler {


    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceInvocationProxy.class);

    private String serviceAddress;

    private String serviceVersion = "";

    private Class<T> interfaceClass;

    private String serviceName;

    private String serializer;

    private InvokerConfig invokerConfig;

    public ServiceInvocationProxy(InvokerConfig config) {
        this.interfaceClass = config.getInterfaceClass();
        this.serviceName = config.getServiceName();
        this.serializer = config.getSerializer();
        this.invokerConfig = config;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        InvokeRequest request = createInvokeRequest(method, args);

        Client client = ClientManager.getInstance().getClient(invokerConfig);
        CallbackFuture callback = new CallbackFuture();
        InvokeResponse response=null;
        RemoteServiceCall.requestInvoke(request,callback,client);
        response = client.send(request, callback);
        if (response == null) {
            response = callback.get();
        }
        long time = System.currentTimeMillis();
        LOGGER.debug("time: {}ms", System.currentTimeMillis() - time);
        if (response == null) {
            throw new RuntimeException("response is null");
        }
        // 返回 RPC 响应结果
        if (response.hasException()) {
            throw response.getException();
        } else {
            return response.getResult();
        }
    }

    /**
     * 封装rpc调用请求
     *
     * @param method
     * @param args
     * @return
     */
    private InvokeRequest createInvokeRequest(Method method, Object[] args) {
        InvokeRequest request = new InvokeRequest();
        request.setRequestId(UUID.randomUUID().toString());
        request.setInterfaceName(method.getDeclaringClass().getName());
        request.setServiceVersion(serviceVersion);
        request.setMethodName(method.getName());
        request.setParameterTypes(method.getParameterTypes());
        request.setParameters(args);
        request.setServiceName(this.serviceName);
        request.setSerialize(this.serializer);
        request.setCreateMillisTime(System.currentTimeMillis());
        return request;
    }

}
