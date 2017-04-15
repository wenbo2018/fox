package com.github.wenbo2018.fox.remoting.invoker.filter;

import com.github.wenbo2018.fox.common.bean.InvokeResponse;
import com.github.wenbo2018.fox.common.util.StringUtil;
import com.github.wenbo2018.fox.remoting.exception.AuthorityException;
import com.github.wenbo2018.fox.remoting.invoker.InvokeContext;
import com.github.wenbo2018.fox.remoting.invoker.handler.ServiceInvocationHandler;
import com.github.wenbo2018.fox.remoting.enums.ReturnEnum;
import com.github.wenbo2018.fox.remoting.invoker.handler.Filter;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by shenwenbo on 2017/4/1.
 */
public class AuthorityFilter implements Filter {

    private static final ConcurrentHashMap<String, String> appkeys = new ConcurrentHashMap<>();

    private static final boolean APPKEY_ENABLE=false;

    @Override
    public InvokeResponse invoke(ServiceInvocationHandler handler, InvokeContext invokeContext) throws Throwable {
        InvokeResponse response = new InvokeResponse();
        if (APPKEY_ENABLE) {
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
        }
        return handler.invoke(invokeContext);
    }
}
