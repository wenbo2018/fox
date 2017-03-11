package com.fox.rpc.registry.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.CuratorListener;
import org.apache.zookeeper.ZooKeeper;

/**
 * Created by shenwenbo on 2017/3/11.
 */
public class CuratorEventListener implements CuratorListener {

    @Override
    public void eventReceived(CuratorFramework curatorFramework, CuratorEvent curatorEvent) throws Exception {

    }
}
