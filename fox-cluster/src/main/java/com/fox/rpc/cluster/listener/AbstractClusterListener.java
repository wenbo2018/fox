package com.fox.rpc.cluster.listener;

import com.fox.rpc.remoting.common.ConnectInfo;
import com.fox.rpc.remoting.invoker.api.Client;

/**
 * Created by shenwenbo on 2017/3/19.
 */
public class AbstractClusterListener implements ClusterListener{

    @Override
    public void addConnect(ConnectInfo connectInfo) {

    }

    @Override
    public void removeConnect(Client client) {

    }

}
