package com.fox.rpc.remoting.invoker.cluster;

import com.fox.rpc.remoting.invoker.api.Client;
import com.fox.rpc.remoting.invoker.config.InvokerConfig;

import java.util.List;

/**
 * Created by shenwenbo on 2017/3/26.
 */
public interface LoadBalance {

    Client select(List<Client> clients, InvokerConfig invokerConfig);

}
