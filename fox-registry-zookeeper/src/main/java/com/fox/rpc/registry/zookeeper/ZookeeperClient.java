package com.fox.rpc.registry.zookeeper;

import org.I0Itec.zkclient.IZkStateListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;
import org.apache.log4j.Logger;
import org.apache.zookeeper.Watcher;

import java.util.List;

/**
 * Created by shenwenbo on 2016/10/17.
 */
public class ZookeeperClient {

    private static Logger LOGGER=Logger.getLogger(ZookeeperClient.class);

    private String address;

    private ZkClient zookeeperClient;

    public ZookeeperClient(String zkAddress) {
        this.address=zkAddress;
        newZkClient();
    }

    public void newZkClient() {

        ZkClient zookeeperClient = new ZkClient(address,10000,10000,new SerializableSerializer());
        zookeeperClient.subscribeStateChanges(new IZkStateListener() {
            @Override
            public void handleStateChanged(Watcher.Event.KeeperState state) throws Exception {

            }
            @Override
            public void handleNewSession() throws Exception {

            }
            @Override
            public void handleSessionEstablishmentError(Throwable error) throws Exception {

            }
        });
        this.zookeeperClient=zookeeperClient;
        LOGGER.debug("connect zookeeper success");
    }

    public ZkClient getZookeeperClient() {
        return zookeeperClient;
    }

    public List<String> get(String path) {
        if (!zookeeperClient.exists(path)) {
            throw new RuntimeException(String.format("can not find any service node on path: %s", path));
        }
        List<String> addressList = zookeeperClient.getChildren(path);
        return addressList;
    }

    public void create(String node,String value) {
        String addressNode = zookeeperClient.createEphemeralSequential(node, value);
        LOGGER.debug("create address node:"+addressNode);
    }

    public void creatrPersistentNode(String path) {
        if (!zookeeperClient.exists(path)) {
            zookeeperClient.createPersistent(path);
            LOGGER.debug("create registry node:"+path);
        }
    }

    public  boolean delete(String node) {
       return zookeeperClient.delete(node);
    }

}
