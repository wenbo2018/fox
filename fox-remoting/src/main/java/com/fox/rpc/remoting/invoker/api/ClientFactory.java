package com.fox.rpc.remoting.invoker.api;

import com.fox.rpc.remoting.invoker.config.ConnectInfo;
import io.netty.channel.Channel;

/**
 * Created by wenbo2018 on 2016/8/26.
 */
public interface ClientFactory {

    void init();

    Client createClient(ConnectInfo connectInfo);

    Client getClient(ConnectInfo connectInfo);

}
