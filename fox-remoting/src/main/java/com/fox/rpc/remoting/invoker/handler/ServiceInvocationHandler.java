package com.fox.rpc.remoting.invoker.handler;

import com.fox.rpc.common.bean.InvokeResponse;
import com.fox.rpc.remoting.invoker.InvokeContext;

/**
 * Created by shenwenbo on 2017/4/1.
 */
public interface ServiceInvocationHandler {

    InvokeResponse invoke(InvokeContext invokeContext) throws Throwable;

}
