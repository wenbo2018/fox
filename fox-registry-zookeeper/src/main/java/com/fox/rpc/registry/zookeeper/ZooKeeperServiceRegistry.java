package com.fox.rpc.registry.zookeeper;

import com.fox.rpc.registry.RemotingServiceRegistry;
import org.I0Itec.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 基于 ZooKeeper 的服务注册接口实现
 */
public class ZooKeeperServiceRegistry implements RemotingServiceRegistry {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZooKeeperServiceRegistry.class);

    private static ZkClient zkClient;

    private RegistryConfig cfg;


    private void initZk() {
        // 创建 ZooKeeper 客户端
        zkClient = new ZkClient("127.0.0.1", Constants.ZK_SESSION_TIMEOUT, Constants.ZK_CONNECTION_TIMEOUT);
        LOGGER.debug("connect zookeeper");
    }

    @Override
    public void register(String serviceName, String serviceAddress) {
        //初始化zk客户端
        initZk();
        // 创建 registry 节点（持久）
        String registryPath = Constants.ZK_REGISTRY_PATH;
        if (!zkClient.exists(registryPath)) {
            zkClient.createPersistent(registryPath);
            LOGGER.debug("create registry node: {}", registryPath);
        }
        // 创建 service 节点（持久）
        String servicePath = registryPath + "/" + serviceName;
        if (!zkClient.exists(servicePath)) {
            zkClient.createPersistent(servicePath);
            LOGGER.debug("create service node: {}", servicePath);
        }
        // 创建 address 节点（临时）
        String addressPath = servicePath + "/address-";
        String addressNode = zkClient.createEphemeralSequential(addressPath, serviceAddress);
        LOGGER.debug("create address node: {}", addressNode);
        System.out.println("创建零时节点："+addressNode);
    }

    @Override
    public void setContext(RegistryConfig cfg) {
        this.cfg=cfg;
    }
}