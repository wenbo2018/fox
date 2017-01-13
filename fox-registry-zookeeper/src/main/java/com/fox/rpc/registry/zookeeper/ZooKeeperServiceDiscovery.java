package com.fox.rpc.registry.zookeeper;

import com.fox.rpc.common.util.CollectionUtil;
import com.fox.rpc.registry.RemotingServiceDiscovery;
import org.I0Itec.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 基于 ZooKeeper 的服务发现接口实现
 */
public class ZooKeeperServiceDiscovery implements RemotingServiceDiscovery {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZooKeeperServiceDiscovery.class);

    private String zkAddress;


    @Override
    public String discover(String name) {
        // 创建 ZooKeeper 客'户端
        System.out.println();
        ZkClient zkClient = new ZkClient("127.0.0.1", Constants.ZK_SESSION_TIMEOUT,
                Constants.ZK_CONNECTION_TIMEOUT);
        LOGGER.debug("connect zookeeper");
        try {
            // 获取 service 节点
            String servicePath = Constants.ZK_REGISTRY_PATH + "/" + name;
            if (!zkClient.exists(servicePath)) {
                throw new RuntimeException(String.format("can not find any service node on path: %s", servicePath));
            }
            List<String> addressList = zkClient.getChildren(servicePath);
            if (CollectionUtil.isEmpty(addressList)) {
                throw new RuntimeException(String.format("can not find any address node on path: %s", servicePath));
            }
            // 获取 address 节点
            String address;
            int size = addressList.size();
            if (size == 1) {
                // 若只有一个地址，则获取该地址
                address = addressList.get(0);
                LOGGER.debug("get only address node: {}", address);
            } else {
                // 若存在多个地址，则随机获取一个地址
                address = addressList.get(ThreadLocalRandom.current().nextInt(size));
                LOGGER.debug("get random address node: {}", address);
            }
            // 获取 address 节点的值
            String addressPath = servicePath + "/" + address;
            return zkClient.readData(addressPath);
        } finally {
            zkClient.close();
        }
    }

    @Override
    public void init(String zkAddress) {
          this.zkAddress=zkAddress;
    }
}