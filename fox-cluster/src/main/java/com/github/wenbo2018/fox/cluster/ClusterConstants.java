package com.github.wenbo2018.fox.cluster;

/**
 * Created by shenwenbo on 2017/4/6.
 */
public class ClusterConstants {

    public static final String DEFAULT_LOADBALANCE="com.github.wenbo2018.fox.cluster.balance.RandomLoadBalance";

    public static final String RANDOM_LOADBALANCE="com.github.wenbo2018.fox.cluster.balance.RandomLoadBalance";

    public static final String LEASTACTIVELOADBALANCE_LOADBALANCE="com.github.wenbo2018.fox.cluster.balance.LeastActiveLoadBalance";

    public static final String RANDOM_LOADBALANCE_CONFIG="random";

    public static final String LEASTACTIVELOADBALANCE_LOADBALANCE_CONFIG="leastActive";

}
