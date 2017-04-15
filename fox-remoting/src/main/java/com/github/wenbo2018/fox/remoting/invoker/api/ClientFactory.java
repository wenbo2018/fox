package com.github.wenbo2018.fox.remoting.invoker.api;

import com.github.wenbo2018.fox.remoting.common.ConnectInfo;

/**
 * Created by wenbo2018 on 2016/8/26.
 */
public interface ClientFactory {

    void init();

    Client createClient(ConnectInfo connectInfo);

    Client getClient(ConnectInfo connectInfo);

}
