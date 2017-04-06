package com.fox.rpc.cluster.balance;

import com.fox.rpc.remoting.invoker.api.Client;
import com.fox.rpc.remoting.invoker.config.InvokerConfig;

import java.util.List;
import java.util.Random;

/**
 * Created by shenwenbo on 2017/3/26.
 */
public class RandomLoadBalance extends AbstractLoadBalance {

    private final Random random = new Random();

    /**
     * 随机算法选择Client，暂时按照随机数选择；
     * @param invokerConfig
     * @return
     */
    @Override
    protected Client doSelect(List<Client> clients, InvokerConfig invokerConfig) {
        int total = clients.size();
        int offset = random.nextInt(total);
        return clients.get(offset);
    }
}
