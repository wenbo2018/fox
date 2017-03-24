package com.fox.rpc.cluster;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by shenwenbo on 2017/3/19.
 */
public class ClusterManager {
    private static Logger LOGGER= LoggerFactory.getLogger(ClusterManager.class);
    private static ClusterManager instance=new ClusterManager();

    public static ClusterManager getInstance() {
        return instance;
    }

    private ClusterManager() {

    }
}
