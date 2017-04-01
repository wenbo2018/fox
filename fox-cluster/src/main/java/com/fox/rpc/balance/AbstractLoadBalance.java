package com.fox.rpc.balance;

import com.fox.rpc.common.bean.InvokeRequest;
import com.fox.rpc.common.util.CollectionUtil;
import com.fox.rpc.remoting.invoker.api.Client;
import com.fox.rpc.remoting.invoker.config.InvokerConfig;

import java.util.List;

/**
 * Created by shenwenbo on 2017/3/26.
 */
public abstract class AbstractLoadBalance implements LoadBalance {

    protected abstract Client doSelect(List<Client> clients, InvokerConfig invokerConfig, InvokeRequest request);

    @Override
    public Client select(List<Client> clients, InvokerConfig invokerConfig, InvokeRequest request) {
        if (CollectionUtil.isEmpty(clients)) {
            return null;
        }
        if (clients.size()==1) {
            return clients.get(0);
        }
        Client loadBalanceClient = null;
        loadBalanceClient = doSelect(clients, invokerConfig,request);
        return loadBalanceClient;
    }
}
