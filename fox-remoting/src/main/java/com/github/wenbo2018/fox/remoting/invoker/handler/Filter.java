package com.github.wenbo2018.fox.remoting.invoker.handler;

import com.github.wenbo2018.fox.common.bean.InvokeResponse;
import com.github.wenbo2018.fox.remoting.invoker.InvokeContext;

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
