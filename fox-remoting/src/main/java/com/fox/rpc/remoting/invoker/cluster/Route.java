package com.fox.rpc.remoting.invoker.cluster;

import com.fox.rpc.remoting.invoker.api.Client;
import com.fox.rpc.remoting.invoker.config.InvokerConfig;

import java.util.List;

/**
 * Created by shenwenbo on 2017/4/6.
 */
public interface Route {

    Client route(List<Client> clients, InvokerConfig invokerConfig);

}
