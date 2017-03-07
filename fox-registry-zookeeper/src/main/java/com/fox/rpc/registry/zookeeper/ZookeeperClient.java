package com.fox.rpc.registry.zookeeper;

import org.I0Itec.zkclient.IZkStateListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;
import org.apache.zookeeper.Watcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by shenwenbo on 2016/10/17.
 */
public class ZookeeperClient {

    private static Logger LOGGER= LoggerFactory.getLogger(ZookeeperClient.class);

    private String address;

    private ZkClient zookeeperClient;

    public ZookeeperClient(String zkAddress) {
        this.address=zkAddress;
        newZkClient();
    }

    /**
     * 初始化zk客户端
     */
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
        LOGGER.info("connect zookeeper success:{}",address);
    }

    public ZkClient getZookeeperClient() {
        return zookeeperClient;
    }

    /**
     * 获取根据path获取节点信息
     * @param path
     * @return
     */
    public List<String> get(String path) {
        if (!zookeeperClient.exists(path)) {
            throw new RuntimeException(String.format("can not find any service node on path: %s", path));
        }
        List<String> addressList = zookeeperClient.getChildren(path);
        return addressList;
    }

    /**
     * 创建零时节点
     * @param node
     * @param value
     */
    public void create(String node,String value) {
        String addressNode = zookeeperClient.createEphemeralSequential(node, value);
        LOGGER.info("create address node:",addressNode);
    }

    /**
     * 创建持久节点
     * @param path
     */
    public void creatrPersistentNode(String path) {
        if (!zookeeperClient.exists(path)) {
            zookeeperClient.createPersistent(path);
            LOGGER.info("create registry node:",path);
        }
    }

    /**
     * 删除节点
     * @param node
     * @return
     */
    public  boolean delete(String node) {
       return zookeeperClient.delete(node);
    }

}
