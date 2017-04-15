package com.github.wenbo2018.fox.cluster.balance;

import com.github.wenbo2018.fox.remoting.invoker.api.Client;
import com.github.wenbo2018.fox.remoting.invoker.config.InvokerConfig;

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
