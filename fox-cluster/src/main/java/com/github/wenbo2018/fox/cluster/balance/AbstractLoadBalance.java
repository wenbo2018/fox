package com.github.wenbo2018.fox.cluster.balance;

import com.github.wenbo2018.fox.common.util.CollectionUtil;
import com.github.wenbo2018.fox.remoting.invoker.config.InvokerConfig;
import com.github.wenbo2018.fox.remoting.invoker.api.Client;
import com.github.wenbo2018.fox.remoting.invoker.cluster.LoadBalance;

import java.util.List;

/**
 * Created by shenwenbo on 2017/3/26.
 */
public abstract class AbstractLoadBalance implements LoadBalance {

    protected abstract Client doSelect(List<Client> clients, InvokerConfig invokerConfig);

    @Override
    public Client select(List<Client> clients, InvokerConfig invokerConfig) {
        if (CollectionUtil.isEmpty(clients)) {
            return null;
        }
        if (clients.size()==1) {
            return clients.get(0);
        }
        Client loadBalanceClient = null;
        loadBalanceClient = doSelect(clients, invokerConfig);
        return loadBalanceClient;
    }
}
