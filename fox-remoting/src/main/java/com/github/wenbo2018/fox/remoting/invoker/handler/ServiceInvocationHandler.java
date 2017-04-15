package com.github.wenbo2018.fox.remoting.invoker.handler;

import com.github.wenbo2018.fox.common.bean.InvokeResponse;
import com.github.wenbo2018.fox.remoting.invoker.InvokeContext;

/**
 * Created by shenwenbo on 2017/4/1.
 */
public interface ServiceInvocationHandler {

    InvokeResponse invoke(InvokeContext invokeContext) throws Throwable;

}
