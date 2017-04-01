package com.fox.rpc.remoting.invoker.proxy;

import com.fox.rpc.common.bean.InvokeResponse;
import com.fox.rpc.common.extension.UserServiceLoader;
import com.fox.rpc.remoting.invoker.DefaultInvokerContext;
import com.fox.rpc.remoting.invoker.Filter;
import com.fox.rpc.remoting.invoker.InvokeContext;
import com.fox.rpc.remoting.invoker.config.InvokerConfig;
import com.fox.rpc.remoting.invoker.handler.ServiceInvocationHandler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by shenwenbo on 2017/4/1.
 */
public class FilterServiceInvocationProxy implements InvocationHandler {

    private InvokerConfig invokerConfig;
    private ServiceInvocationHandler handler;

    public FilterServiceInvocationProxy(InvokerConfig invokerConfig,ServiceInvocationHandler serviceInvocationHandler) {
        this.invokerConfig=invokerConfig;
        this.handler=serviceInvocationHandler;
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
       return handler.invoke(new DefaultInvokerContext(invokerConfig,methodName,parameterTypes,args)).getResult();
    }
}
