package com.fox.rpc.remoting.invoker.filter;

import com.fox.rpc.common.bean.InvokeResponse;
import com.fox.rpc.remoting.invoker.handler.Filter;
import com.fox.rpc.remoting.invoker.InvokeContext;
import com.fox.rpc.remoting.invoker.handler.ServiceInvocationHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by shenwenbo on 2017/4/1.
 */
public class MonitorFilter implements Filter {
    private static Logger LOGGER= LoggerFactory.getLogger(MonitorFilter.class);

    @Override
    public InvokeResponse invoke(ServiceInvocationHandler handler, InvokeContext invokeContext) throws Throwable {
        InvokeResponse invokeResponse=null;
        LOGGER.debug("enter monitor befor");
        invokeResponse=handler.invoke(invokeContext);
        LOGGER.debug("enter monitor after");
        return invokeResponse;
    }

}
