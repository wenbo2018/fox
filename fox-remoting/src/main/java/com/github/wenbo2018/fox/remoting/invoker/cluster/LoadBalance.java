package com.github.wenbo2018.fox.remoting.invoker.cluster;

import com.github.wenbo2018.fox.remoting.invoker.api.Client;
import com.github.wenbo2018.fox.remoting.invoker.config.InvokerConfig;

import java.util.List;

/**
 * Created by shenwenbo on 2017/3/26.
 */
public interface LoadBalance {

    Client select(List<Client> clients, InvokerConfig invokerConfig);

}
