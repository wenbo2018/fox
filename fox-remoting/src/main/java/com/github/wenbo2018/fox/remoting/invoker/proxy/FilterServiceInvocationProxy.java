package com.github.wenbo2018.fox.remoting.invoker.proxy;

import com.github.wenbo2018.fox.common.bean.InvokeResponse;
import com.github.wenbo2018.fox.remoting.enums.ReturnEnum;
import com.github.wenbo2018.fox.remoting.invoker.DefaultInvokerContext;
import com.github.wenbo2018.fox.remoting.invoker.config.InvokerConfig;
import com.github.wenbo2018.fox.remoting.invoker.handler.ServiceInvocationHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by shenwenbo on 2017/4/1.
 */
public class FilterServiceInvocationProxy implements InvocationHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(FilterServiceInvocationProxy.class);

    private InvokerConfig invokerConfig;
    private ServiceInvocationHandler handler;

    public FilterServiceInvocationProxy(InvokerConfig invokerConfig, ServiceInvocationHandler serviceInvocationHandler) {
        this.invokerConfig = invokerConfig;
        this.handler = serviceInvocationHandler;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String methodName = method.getName();
        Class<?>[] parameterTypes = method.getParameterTypes();
        if (method.getDeclaringClass() == Object.class) {
            return method.invoke(handler, args);
        }
        if ("toString".equals(methodName) && parameterTypes.length == 0) {
            return handler.toString();
        }
        if ("hashCode".equals(methodName) && parameterTypes.length == 0) {
            return handler.hashCode();
        }
        if ("equals".equals(methodName) && parameterTypes.length == 1) {
            return handler.equals(args[0]);
        }
        return invokeResult(handler.invoke(new DefaultInvokerContext(invokerConfig, methodName, parameterTypes, args)));
    }

    private Object invokeResult(InvokeResponse response) {
        if (response.getReturnType() == ReturnEnum.SERVICE.ordinal()) {
            LOGGER.debug("SERVICE result:{}",response.toString());
            return response.getResult();
        }
        if (response.getReturnType() == ReturnEnum.AUTHORITY_EXCEPTION.ordinal()) {
            LOGGER.debug("AUTHORITY_EXCEPTION result:{}",response.toString());
            LOGGER.error(response.getException().toString());
        }
        if (response.getReturnType() == ReturnEnum.TIMEOUT_EXCEPTION.ordinal()) {
            LOGGER.error(response.getException().toString());
        }
        return null;
    }
}
