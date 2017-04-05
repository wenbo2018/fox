package com.fox.rpc.remoting.invoker.filter;

import com.fox.rpc.common.bean.InvokeResponse;
import com.fox.rpc.common.util.StringUtil;
import com.fox.rpc.remoting.enums.ReturnEnum;
import com.fox.rpc.remoting.exception.AuthorityException;
import com.fox.rpc.remoting.invoker.handler.Filter;
import com.fox.rpc.remoting.invoker.InvokeContext;
import com.fox.rpc.remoting.invoker.handler.ServiceInvocationHandler;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by shenwenbo on 2017/4/1.
 */
public class AuthorityFilter implements Filter {

    private static final ConcurrentHashMap<String, String> appkeys = new ConcurrentHashMap<>();

    @Override
    public InvokeResponse invoke(ServiceInvocationHandler handler, InvokeContext invokeContext) throws Throwable {
        InvokeResponse response = new InvokeResponse();
        String appkey = invokeContext.getInvokerConfig().getAppkey();
        if (StringUtil.isEmpty(appkey)) {
            response.setException(new AuthorityException("The current key is null"));
            response.setReturnType(ReturnEnum.AUTHORITY_EXCEPTION.ordinal());
            return response;
        } else if (appkeys.get(invokeContext.getInvokerConfig().getServiceName()) == invokeContext.getInvokerConfig().getAppkey()) {
            response.setException(new AuthorityException("The current key does not have the authority to invoke the service"));
            response.setReturnType(ReturnEnum.AUTHORITY_EXCEPTION.ordinal());
            return response;
        }
        return handler.invoke(invokeContext);
    }
}
