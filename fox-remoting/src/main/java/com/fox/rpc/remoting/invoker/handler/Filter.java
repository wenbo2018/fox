package com.fox.rpc.remoting.invoker.handler;

import com.fox.rpc.common.bean.InvokeResponse;
import com.fox.rpc.remoting.invoker.InvokeContext;
import com.fox.rpc.remoting.invoker.handler.ServiceInvocationHandler;

/**
 * Created by shenwenbo on 2017/4/1.
 */
public interface Filter<T> {

    /****
     * invoke befor
     * invoke(InvocationHandler handler, T invokeContext) throws Throwable;
     * invoke after
     * @param handler
     * @param invokeContext
     * @return
     * @throws Throwable
     */
    InvokeResponse invoke(ServiceInvocationHandler handler, InvokeContext invokeContext) throws Throwable;

}
