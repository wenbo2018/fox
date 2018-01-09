package com.github.wenbo2018.fox.cluster.balance;

import com.github.wenbo2018.fox.remoting.invoker.config.InvokerConfig;
import com.github.wenbo2018.fox.remoting.invoker.api.Client;

import java.util.List;
import java.util.Random;

/**
 * Created by shenwenbo on 2017/3/26.
 */
public class RandomLoadBalance extends AbstractLoadBalance {

    private final Random random = new Random();

    @Override
    protected Client doSelect(List<Client> clients, InvokerConfig invokerConfig) {
        int total = clients.size();
        int offset = random.nextInt(total);
        return clients.get(offset);
    }
}
