package com.github.wenbo2018.fox.cluster;

import com.github.wenbo2018.fox.common.extension.ExtensionServiceLoader;
import com.github.wenbo2018.fox.remoting.invoker.config.InvokerConfig;
import com.github.wenbo2018.fox.remoting.invoker.cluster.LoadBalance;
import com.github.wenbo2018.fox.remoting.invoker.api.Client;
import com.github.wenbo2018.fox.remoting.invoker.cluster.Route;

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
                loadBalance= ExtensionServiceLoader.getExtensionLoader(LoadBalance.class)
                        .getExtension(resolveLoadBalance(loadBalanceType));
            } else {
                loadBalance= ExtensionServiceLoader.getExtensionLoader(LoadBalance.class)
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
