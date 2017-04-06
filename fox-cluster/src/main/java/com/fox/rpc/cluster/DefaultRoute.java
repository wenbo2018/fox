package com.fox.rpc.cluster;

import com.fox.rpc.remoting.invoker.cluster.LoadBalance;
import com.fox.rpc.common.extension.UserServiceLoader;
import com.fox.rpc.remoting.invoker.api.Client;
import com.fox.rpc.remoting.invoker.cluster.Route;
import com.fox.rpc.remoting.invoker.config.InvokerConfig;
import java.util.List;

/**
 * Created by shenwenbo on 2017/4/6.
 */
public class DefaultRoute implements Route {

    @Override
    public Client route(List<Client> clients, InvokerConfig invokerConfig) {
        LoadBalance loadBalance=null;
        if (clients != null && clients.size() > 0) {
            String loadBalanceType=invokerConfig.getLoadBalance();
            if (loadBalanceType!=null) {
                loadBalance= UserServiceLoader.getExtensionLoader(LoadBalance.class)
                        .getExtension(resolveLoadBalance(loadBalanceType));
            } else {
                loadBalance= UserServiceLoader.getExtensionLoader(LoadBalance.class)
                        .getExtension(ClusterConstants.DEFAULT_LOADBALANCE);
            }
        }
        return loadBalance.select(clients,invokerConfig);
    }

    private String resolveLoadBalance(String loadBalanceType){
        switch (loadBalanceType) {
            case ClusterConstants.RANDOM_LOADBALANCE_CONFIG:
                return ClusterConstants.RANDOM_LOADBALANCE;
            case ClusterConstants.LEASTACTIVELOADBALANCE_LOADBALANCE_CONFIG:
                return ClusterConstants.LEASTACTIVELOADBALANCE_LOADBALANCE;
            default:return ClusterConstants.DEFAULT_LOADBALANCE;
        }
    }

}
