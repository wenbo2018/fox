package com.github.wenbo2018.fox.remoting.invoker.cluster;

import com.github.wenbo2018.fox.remoting.invoker.config.InvokerConfig;
import com.github.wenbo2018.fox.remoting.invoker.api.Client;

import java.util.List;

/**
 * Created by shenwenbo on 2017/4/6.
 */
public interface Route {

    Client route(List<Client> clients, InvokerConfig invokerConfig);

}
