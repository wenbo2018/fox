package com.github.wenbo2018.fox.remoting.invoker.filter;

import com.github.wenbo2018.fox.common.bean.InvokeResponse;
import com.github.wenbo2018.fox.remoting.invoker.InvokeContext;
import com.github.wenbo2018.fox.remoting.invoker.handler.Filter;
import com.github.wenbo2018.fox.remoting.invoker.handler.ServiceInvocationHandler;
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
