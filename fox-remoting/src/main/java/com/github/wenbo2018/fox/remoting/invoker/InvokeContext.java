package com.github.wenbo2018.fox.remoting.invoker;

import com.github.wenbo2018.fox.remoting.invoker.api.Client;
import com.github.wenbo2018.fox.remoting.invoker.config.InvokerConfig;

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
