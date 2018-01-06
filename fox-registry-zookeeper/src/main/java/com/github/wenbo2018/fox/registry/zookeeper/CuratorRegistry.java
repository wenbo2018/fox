package com.github.wenbo2018.fox.registry.zookeeper;

import com.github.wenbo2018.fox.registry.Registry;
import org.apache.commons.lang.StringUtils;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

/**
 * Created by shenwenbo on 2016/10/17.
 */
public class CuratorRegistry implements Registry {


    private static final Logger LOGGER = LoggerFactory.getLogger(CuratorRegistry.class);
    private static final String CHARSET = "UTF-8";

    private Properties properties;

    private CuratorClient zookeeperClient;

    private volatile boolean isInitialized = false;

    /**
     * 初始化zk
     **/
    @Override
    public void init(Properties properties) {
        this.properties = properties;
        if (!isInitialized) {
            synchronized (this) {
                if (!isInitialized) {
                    try {
                        //获取zk地址
                        String zkAddress = properties.getProperty(Constants.KEY_REGISTRY_ADDRESS);
                        if (zkAddress != null) {
                            LOGGER.info("start to initialize zookeeper curator:{}", zkAddress);
                            zookeeperClient = new CuratorClient(zkAddress);
                            LOGGER.info("succeed to initialize zookeeper curator:{}", zkAddress);
                            isInitialized = true;
                        } else {
                            LOGGER.error("zookeeper server adress is null");
                        }
                    } catch (Exception ex) {
                        LOGGER.error("failed to initialize zookeeper curator", ex);
                    }
                }
            }
        }
    }


    @Override
    public void registerService(String serviceName, String serviceAddress) {
        String registryPath = Constants.ZK_REGISTRY_PATH;
        try {
            //根目录不存在,创建根目录;
            if (!zookeeperClient.exists(registryPath)) {
                zookeeperClient.creatrPersistentNode(registryPath);
            }
            //注册服务，如果服务节点存在，那么将节点再讲不存在的数据设置进行
            String serviceAddressPath = Constants.ZK_REGISTRY_PATH + "/" + serviceName;
            if (zookeeperClient.exists(serviceAddressPath)) {
                Stat stat = new Stat();
                String addressValues = zookeeperClient.get(serviceAddressPath, stat);
                String[] addressArray = addressValues.split(",");
                List<String> addressList = new ArrayList<String>();
                for (String addr : addressArray) {
                    addr = addr.trim();
                    if (addr.length() > 0 && !addressList.contains(addr)) {
                        addressList.add(addr.trim());
                    }
                }
                if (!addressList.contains(serviceAddress)) {
                    addressList.add(serviceAddress);
                    Collections.sort(addressList);
                    zookeeperClient.set(serviceAddressPath, StringUtils.join(addressList.iterator(), ","), stat.getVersion());
                }
            } else {
                zookeeperClient.create(serviceAddressPath, serviceAddress);
            }
            LOGGER.info("service register success:{}:{}", serviceName, serviceAddress);
        } catch (Exception e) {
            LOGGER.error("service register fail:{}:{}", serviceName, serviceAddress);
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
        String result = null;
        String servicePath = Constants.ZK_REGISTRY_PATH + "/" + serviceName;
        String serviceAddressPath = Constants.ZK_REGISTRY_PATH + "/" + serviceName;
        try {
            if (zookeeperClient.exists(serviceAddressPath)) {
                Stat stat = new Stat();
                result = zookeeperClient.get(serviceAddressPath, stat);
//                String[] addressArray = addressValues.split(",");
//                List<String> addressList = new ArrayList<String>();
//                for (String addr : addressArray) {
//                    addr = addr.trim();
//                    if (addr.length() > 0 && !addressList.contains(addr)) {
//                        addressList.add(addr.trim());
//                    }
//                }
//                //随机获取可提供服务的机器;
//                result=addressList.get(1 + (int)(Math.random()*addressList.size())-1);
//
            } else {
                LOGGER.warn("can not find any address node on path:{}",serviceAddressPath);
            }
        } catch (Exception e) {
            LOGGER.error("get service address fail:{}", e);
        }
        return result;
    }
}
