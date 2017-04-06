package com.fox.rpc.cluster.balance;

import com.fox.rpc.remoting.invoker.api.Client;
import com.fox.rpc.remoting.invoker.config.InvokerConfig;

import java.util.List;

/**
 * Created by shenwenbo on 2017/4/6.
 */
public class LeastActiveLoadBalance extends AbstractLoadBalance {

    @Override
    protected Client doSelect(List<Client> clients, InvokerConfig invokerConfig) {
        return null;
    }
}
