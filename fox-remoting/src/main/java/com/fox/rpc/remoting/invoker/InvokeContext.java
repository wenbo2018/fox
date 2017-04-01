package com.fox.rpc.remoting.invoker;

import com.fox.rpc.remoting.invoker.api.Client;
import com.fox.rpc.remoting.invoker.config.InvokerConfig;

import java.util.Map;

/**
 * Created by shenwenbo on 2017/4/1.
 */
public interface InvokeContext {

    InvokerConfig getInvokerConfig();

    String getMethodName();

    Class<?>[] getParameterTypes();

    Object[] getArguments();

    Client getClient();

    void setClient(Client client);
}
