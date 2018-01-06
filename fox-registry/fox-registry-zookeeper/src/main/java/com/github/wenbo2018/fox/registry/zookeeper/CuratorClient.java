package com.github.wenbo2018.fox.registry.zookeeper;

import io.netty.util.concurrent.DefaultThreadFactory;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by shenwenbo on 2016/10/17.
 */
public class CuratorClient {

    private static Logger LOGGER = LoggerFactory.getLogger(CuratorClient.class);

    private String address;

    private static final String CHARSET = "UTF-8";

    private CuratorFramework zookeeperClient;

    private static ExecutorService curatorEventListenerThreadPool = Executors
            .newCachedThreadPool(new DefaultThreadFactory("fox-Curator-Event-Listener"));

    public CuratorClient(String zkAddress) throws InterruptedException {
        this.address = zkAddress;
        newZkClient();
    }


    public boolean newZkClient() throws InterruptedException {
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .ensembleProvider(new DefaultEnsembleProvider(address))
                .sessionTimeoutMs(30 * 1000)
                .connectionTimeoutMs(15 * 1000)
                .retryPolicy(new ExponentialBackoffRetry(1000, Integer.MAX_VALUE))
                .build();

        client.getConnectionStateListenable().addListener(new ConnectionStateListener() {
            @Override
            public void stateChanged(CuratorFramework client, ConnectionState newState) {
                LOGGER.info("zookeeper state changed to " + newState);
                if (newState == ConnectionState.RECONNECTED) {
//                    RegistryEventListener.connectionReconnected();
                }
            }
        });
        //事件监听;
        client.getCuratorListenable().addListener(new CuratorEventListener(this), curatorEventListenerThreadPool);
        client.start();
        boolean isConnected = client.getZookeeperClient().blockUntilConnectedOrTimedOut();
        CuratorFramework oldClient = this.zookeeperClient;
        this.zookeeperClient = client;
        close(oldClient);
        LOGGER.info("succeed to create zookeeper curator, connected:" + isConnected);
        return isConnected;
    }

    public CuratorFramework getZookeeperClient() {
        return zookeeperClient;
    }


    public List<String> getChild(String path) throws Exception {
        if (!exists(path)) {
            throw new RuntimeException(String.format("can not find any service node on path: %s", path));
        }
        List<String> addressList = null;
        try {
            addressList = zookeeperClient.getChildren().watched().forPath(path);
        } catch (Exception e) {
            LOGGER.debug("node " + path + " does not exist");
            return null;
        }
        return addressList;
    }


    public String get(String path) throws Exception {
        return get(path, true);
    }


    public String get(String path, Stat stat) throws Exception {
        if (exists(path, false)) {
            byte[] bytes = zookeeperClient.getData().storingStatIn(stat).forPath(path);
            String value = new String(bytes, CHARSET);
            LOGGER.debug("get value of node " + path + ", value " + value);
            return value;
        } else {
            LOGGER.debug("node " + path + " does not exist");
            return null;
        }
    }


    public String get(String path, boolean watch) throws Exception {
        if (exists(path, watch)) {
            byte[] bytes = zookeeperClient.getData().forPath(path);
            String value = new String(bytes, CHARSET);
            LOGGER.debug("get value of node " + path + ", value " + value);
            return value;
        } else {
            LOGGER.debug("node " + path + " does not exist");
            return null;
        }
    }


    public void set(String path, Object value, int version) throws Exception {
        byte[] bytes = (value == null ? new byte[0] : value.toString().getBytes(CHARSET));
        if (exists(path, false)) {
            zookeeperClient.setData().withVersion(0).forPath(path, bytes);
            LOGGER.debug("set value of node " + path + " to " + value);
        } else {
            zookeeperClient.create().creatingParentsIfNeeded().forPath(path, bytes);
            LOGGER.debug("create node " + path + " value " + value);
        }
    }



    public void create(String node, String value) throws Exception {
        byte[] bytes = (value == null ? new byte[0] : value.toString().getBytes(CHARSET));
        String addressNode = zookeeperClient.create().withMode(CreateMode.EPHEMERAL).forPath(node, bytes);
        LOGGER.info("create address node:", addressNode);
    }


    public void creatrPersistentNode(String path) throws Exception {
        if (!exists(path, false)) {
            zookeeperClient.create().creatingParentsIfNeeded().forPath(path);
            LOGGER.info("create registry node:", path);
        }
    }


    public void delete(String path) throws Exception {
        zookeeperClient.delete().forPath(path);
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("delete node " + path);
        }
    }


    public void deleteIfExists(String path) throws Exception {
        if (exists(path, false)) {
            delete(path);
        } else {
            LOGGER.warn("node " + path + " not exists!");
        }
    }


    public void watch(String path) throws Exception {
        zookeeperClient.checkExists().watched().forPath(path);
    }

    private void close(CuratorFramework client) {
        if (client != null) {
            LOGGER.info("begin to close zookeeper curator");
            try {
                client.close();
                LOGGER.info("succeed to close zookeeper curator");
            } catch (Exception e) {
            }
        }
    }


    public boolean exists(String path) throws Exception {
        Stat stat = zookeeperClient.checkExists().watched().forPath(path);
        return stat != null;
    }


    public boolean exists(String path, boolean watch) throws Exception {
        Stat stat = watch ? zookeeperClient.checkExists().watched().
                forPath(path) : zookeeperClient.checkExists().forPath(path);
        return stat != null;
    }
}
