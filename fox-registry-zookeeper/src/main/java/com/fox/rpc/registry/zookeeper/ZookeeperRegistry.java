package com.fox.rpc.registry.zookeeper;

import com.fox.rpc.common.util.CollectionUtil;
import com.fox.rpc.registry.Registry;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by shenwenbo on 2016/10/17.
 */
public class ZookeeperRegistry implements Registry {

    private static Logger LOGGER = Logger.getLogger(ZookeeperRegistry.class);

    private Properties properties;

    private ZookeeperClient zookeeperClient;

    private volatile boolean isInitialized = false;

    /**初始化zk**/
    @Override
    public void init(Properties properties) {
        this.properties = properties;
        if (!isInitialized) {
            synchronized (this) {
                if (!isInitialized) {
                    try {
                        //获取zk地址
                        String zkAddress = properties.getProperty(Constants.KEY_REGISTRY_ADDRESS);
                        if (zkAddress!=null) {
                            LOGGER.info("start to initialize zookeeper client:" + zkAddress);
                            zookeeperClient = new ZookeeperClient(zkAddress);
                            LOGGER.info("succeed to initialize zookeeper client:" + zkAddress);
                            isInitialized = true;
                        }else {
                            LOGGER.error("zookeeper server adress is null");
                        }
                    } catch (Exception ex) {
                        LOGGER.error("failed to initialize zookeeper client", ex);
                    }
                }
            }
        }
    }


    @Override
    public void registerService(String serviceName, String serviceAddress) {
        String registryPath = Constants.ZK_REGISTRY_PATH;
        zookeeperClient.creatrPersistentNode(registryPath);
        String servicePath = registryPath + "/" + serviceName;
        zookeeperClient.creatrPersistentNode(servicePath);
        String addressPath = servicePath + "/address-";
        zookeeperClient.create(addressPath, serviceAddress);
    }

    @Override
    public boolean unregisterService(String serviceName, String serviceAddress) {
        String registryPath = Constants.ZK_REGISTRY_PATH;
        String servicePath = registryPath + "/" + serviceName;
        return zookeeperClient.delete(servicePath);
    }

    @Override
    public String getServiceAddress(String serviceName) {
        // 获取 service 节点
        String servicePath = Constants.ZK_REGISTRY_PATH + "/" + serviceName;
        List<String> addressList = zookeeperClient.get(servicePath);
        if (CollectionUtil.isEmpty(addressList)) {
            throw new RuntimeException(String.format("can not find any address node on path: %s", servicePath));
        }

        // 获取 address 节点
        String address;
        int size = addressList.size();
        if (size == 1) {
            // 若只有一个地址，则获取该地址
            address = addressList.get(0);
            LOGGER.debug("get only address node:" + address);
        } else {
            //若存在多个地址，负载均衡则随机获取一个地址
            address = addressList.get(ThreadLocalRandom.current().nextInt(size));
            LOGGER.debug("get random address node:" + address);
        }
        //获取 address 节点的值
        String addressPath = servicePath + "/" + address;
        return zookeeperClient.getZookeeperClient().readData(addressPath);
    }
}
