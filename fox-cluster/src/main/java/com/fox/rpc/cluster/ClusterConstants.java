package com.fox.rpc.cluster;

/**
 * Created by shenwenbo on 2017/4/6.
 */
public class ClusterConstants {

    public static final String DEFAULT_LOADBALANCE="com.fox.rpc.cluster.balance.RandomLoadBalance";

    public static final String RANDOM_LOADBALANCE="com.fox.rpc.cluster.balance.RandomLoadBalance";

    public static final String LEASTACTIVELOADBALANCE_LOADBALANCE="com.fox.rpc.cluster.balance.LeastActiveLoadBalance";

    public static final String RANDOM_LOADBALANCE_CONFIG="random";

    public static final String LEASTACTIVELOADBALANCE_LOADBALANCE_CONFIG="leastActive";

}
