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
public class CuratorRegistry implements Registry {

    private static Logger LOGGER = Logger.getLogger(CuratorRegistry.class);

    private static final String CHARSET = "UTF-8";

    private Properties properties;

    private CuratorClient zookeeperClient;

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
                            zookeeperClient = new CuratorClient(zkAddress);
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
        try {
            zookeeperClient.creatrPersistentNode(registryPath);
            String servicePath = registryPath + "/" + serviceName;
            zookeeperClient.creatrPersistentNode(servicePath);
            String addressPath = servicePath + "/address-";
            zookeeperClient.create(addressPath, serviceAddress);
        } catch (Exception e) {
            LOGGER.error("service register fail:"+serviceName+":"+serviceAddress);
        }
    }

    @Override
    public void unregisterService(String serviceName, String serviceAddress) throws Exception {
        String registryPath = Constants.ZK_REGISTRY_PATH;
        String servicePath = registryPath + "/" + serviceName;
        zookeeperClient.delete(servicePath);
    }

    @Override
    public String getServiceAddress(String serviceName) {
        // 获取 service 节点
        String servicePath = Constants.ZK_REGISTRY_PATH + "/" + serviceName;
        List<String> addressList = null;
        try {
            addressList = zookeeperClient.get(servicePath);
        } catch (Exception e) {
            LOGGER.error("get node fail:"+serviceName,e);
            return null;
        }
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
        String result=null;
        try {
            byte[] bytes  = zookeeperClient.getZookeeperClient().getData().forPath(addressPath);
            result= new String(bytes, CHARSET);
        } catch (Exception e) {
            LOGGER.error("get node fail:"+addressPath,e);
        }
        return result;
    }
}
