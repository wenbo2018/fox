package com.fox.rpc.remoting.invoker.filter;

import com.fox.rpc.common.bean.InvokeResponse;
import com.fox.rpc.remoting.invoker.handler.Filter;
import com.fox.rpc.remoting.invoker.InvokeContext;
import com.fox.rpc.remoting.invoker.handler.ServiceInvocationHandler;

/**
 * Created by shenwenbo on 2017/4/1.
 */
public class MonitorFilter implements Filter {

    @Override
    public InvokeResponse invoke(ServiceInvocationHandler handler, InvokeContext invokeContext) throws Throwable {
        InvokeResponse invokeResponse=null;
        System.err.println("监控调用前");
        invokeResponse=handler.invoke(invokeContext);
        System.err.println("监控调用后"+invokeResponse.getResult());
        return invokeResponse;
    }

}
