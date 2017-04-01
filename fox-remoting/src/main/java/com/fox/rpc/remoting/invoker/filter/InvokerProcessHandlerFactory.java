package com.fox.rpc.remoting.invoker.filter;

import com.fox.rpc.common.bean.InvokeResponse;
import com.fox.rpc.remoting.invoker.Filter;
import com.fox.rpc.remoting.invoker.InvokeContext;
import com.fox.rpc.remoting.invoker.config.InvokerConfig;
import com.fox.rpc.remoting.invoker.handler.ServiceInvocationHandler;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by shenwenbo on 2017/4/1.
 */
public class InvokerProcessHandlerFactory {

    private static List<Filter> bizProcessFilters = new LinkedList<Filter>();

    private static ServiceInvocationHandler bizInvocationHandler = null;

    private static volatile boolean isInitialized = false;

    public static void init() {
        if (!isInitialized) {
            registerBizProcessFilter(new MonitorFilter());
            registerBizProcessFilter(new RemoteCallInvokeFilter());
            bizInvocationHandler = createInvocationHandler(bizProcessFilters);
            isInitialized = true;
        }
    }

    public static ServiceInvocationHandler selectInvocationHandler() {
        return bizInvocationHandler;
    }

    @SuppressWarnings({ "rawtypes" })
    private static <V extends Filter> ServiceInvocationHandler createInvocationHandler(
            List<V> internalFilters) {
        ServiceInvocationHandler last = null;
        List<V> filterList = new ArrayList<V>();
        filterList.addAll(internalFilters);
        for (int i = filterList.size() - 1; i >= 0; i--) {
            final V filter = filterList.get(i);
            final ServiceInvocationHandler next = last;
            last = new ServiceInvocationHandler() {
                @Override
                public InvokeResponse invoke(InvokeContext invokeContext) throws Throwable {
                    InvokeResponse resp = filter.invoke(next, invokeContext);
                    return resp;
                }
            };
        }
        return last;
    }

    public static void registerBizProcessFilter(Filter filter) {
        bizProcessFilters.add(filter);
    }

    public static void clearClientFilters() {
        bizProcessFilters.clear();
    }
}
