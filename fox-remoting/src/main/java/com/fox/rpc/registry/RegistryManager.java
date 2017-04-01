package com.fox.rpc.registry;import com.fox.rpc.common.util.StringUtil;import com.fox.rpc.config.ConfigManagerLoader;import com.fox.rpc.registry.listener.ServiceProviderChangeListener;import com.fox.rpc.common.HostInfo;import com.fox.rpc.common.extension.UserServiceLoader;import com.fox.rpc.common.util.CollectionUtil;import com.fox.rpc.config.ConfigManager;import com.fox.rpc.registry.listener.ServiceProviderChangeEvent;import org.apache.commons.collections4.CollectionUtils;import org.slf4j.Logger;import org.slf4j.LoggerFactory;import java.util.*;import java.util.concurrent.ConcurrentHashMap;/** * Created by shenwenbo on 2016/10/27. */public class RegistryManager {    private static Logger LOGGER = LoggerFactory.getLogger(RegistryManager.class);    private static RegistryManager instance = new RegistryManager();    private static ConfigManager configManager = ConfigManagerLoader.getConfigManager();    private static RegistryConfigManager registryConfigManager = new DefaultRegistryConfigManager();    private static volatile boolean isInit = false;    //private static ConcurrentHashMap<String, ServerInfo> referencedAddresses = new ConcurrentHashMap<String, ServerInfo>();    private volatile static List<Registry> registryList = new ArrayList<>();    private static ConcurrentHashMap<String, String> registeredServices = new ConcurrentHashMap<String, String>();    //服务对应能获取的机器    private static ConcurrentHashMap<String, Set<HostInfo>> referencedServiceAddresses = new ConcurrentHashMap<String, Set<HostInfo>>();    private static ServerInfoListener serverInfoListener = new InnerServerInfoListener();    private static ServiceProviderChangeListener serviceProviderChangeListener = new InnerServiceProviderChangeListener();    public static RegistryManager getInstance() {        if (!isInit) {            synchronized (RegistryManager.class) {                if (!isInit) {                    instance.init(registryConfigManager.getRegistryConfig());                    RegistryEventListener.addListener(serverInfoListener);                    RegistryEventListener.addListener(serviceProviderChangeListener);                    isInit = true;                }            }        }        return instance;    }    public void init(Properties properties) {        List<Registry> _registryList = UserServiceLoader.getExtensionList(Registry.class);        if (_registryList.size() > 0) {            for (Registry registry : _registryList) {                registry.init(properties);                registryList.add(registry);            }        }    }    /***     * 获取服务，先从本地获取，本地获取不到时去注册中心获取；     * @param serviceName     * @return     */    public String getServiceAddress(String serviceName) {        Set<HostInfo> hostInfos = referencedServiceAddresses.get(serviceName);        String address = null;        if (CollectionUtil.isEmpty(hostInfos)) {            //从网络获取服务地址；            for (Registry registry : registryList) {                address = registry.getServiceAddress(serviceName);                LOGGER.info("successed get service adress from service center:"+address);                String[] addressArray = address.split(",");                List<String> addressList = new ArrayList<String>();                for (String addr : addressArray) {                    addr = addr.trim();                    if (addr.length() > 0 && !addressList.contains(addr)) {                        addressList.add(addr.trim());                    }                }                hostInfos = Collections.newSetFromMap(new ConcurrentHashMap<HostInfo, Boolean>());                for (String addr : addressList) {                    String[] array = StringUtil.split(addr, ":");                    String host = array[0];                    int port = Integer.parseInt(array[1]);                    HostInfo hostInfo = new HostInfo();                    hostInfo.setHost(host);                    hostInfo.setPort(port);                    hostInfos.add(hostInfo);                }                referencedServiceAddresses.put(serviceName,hostInfos);            }        }        //随机轮询现在服务器;        int max_zize = 0;        String result = null;        int round = 1 + (int) (Math.random() * hostInfos.size()) - 1;        for (HostInfo hostInfo : hostInfos) {            if (round == max_zize) {                result = hostInfo.getHost() + ":" + hostInfo.getPort();                break;            }        }        LOGGER.info("successed get service adress:"+result);        return result;    }    /***     * 获取服务的所有Host主机，本地有则从本地获取，本地没有则从注册中心     * 获取再保存到本地     * @param serviceName     * @return     */    public Set<HostInfo> getServiceHost(String serviceName) {        Set<HostInfo> hostInfos = referencedServiceAddresses.get(serviceName);        String address = null;        if (CollectionUtil.isEmpty(hostInfos)) {            //从网络获取服务地址；            for (Registry registry : registryList) {                address = registry.getServiceAddress(serviceName);                if (StringUtil.isEmpty(address))                    throw new RuntimeException("get service address fail from service center");                LOGGER.info("successed get service adress from service center:"+address);                String[] addressArray = address.split(",");                List<String> addressList = new ArrayList<String>();                for (String addr : addressArray) {                    addr = addr.trim();                    if (addr.length() > 0 && !addressList.contains(addr)) {                        addressList.add(addr.trim());                    }                }                hostInfos = Collections.newSetFromMap(new ConcurrentHashMap<HostInfo, Boolean>());                for (String addr : addressList) {                    String[] array = StringUtil.split(addr, ":");                    String host = array[0];                    int port = Integer.parseInt(array[1]);                    HostInfo hostInfo = new HostInfo();                    hostInfo.setHost(host);                    hostInfo.setPort(port);                    hostInfos.add(hostInfo);                }                referencedServiceAddresses.put(serviceName,hostInfos);            }        }        hostInfos = referencedServiceAddresses.get(serviceName);        return hostInfos;    }    public static Set<HostInfo> getReferencedServiceAddresses(String serviceName) {        Set<HostInfo> hostInfos = referencedServiceAddresses.get(serviceName);        if (hostInfos == null || hostInfos.size() == 0) {            LOGGER.info("empty address list for service:" + serviceName);        }        return hostInfos;    }    public void registerService(String serviceName, String serviceAddress) {        for (Registry registry : registryList) {            registry.registerService(serviceName, serviceAddress);            LOGGER.info("service register success:" + serviceAddress);        }        registeredServices.putIfAbsent(serviceName, serviceAddress);        Set<HostInfo> hostInfos = null;        hostInfos = referencedServiceAddresses.get(serviceName);        // 从 RPC 服务地址中解析主机名与端口号        String[] array = StringUtil.split(serviceAddress, ":");        String host = array[0];        int port = Integer.parseInt(array[1]);        HostInfo hostInfo = new HostInfo();        hostInfo.setHost(host);        hostInfo.setPort(port);        if (CollectionUtils.isEmpty(hostInfos))            hostInfos = Collections.newSetFromMap(new ConcurrentHashMap<HostInfo, Boolean>());        hostInfos.add(hostInfo);        referencedServiceAddresses.put(serviceName, hostInfos);        LOGGER.info("add service to referencedServiceAddresses:"+hostInfos);    }    public void addServiceAdress(String serviceName, HostInfo hostInfo) {        Set<HostInfo> hostInfos = referencedServiceAddresses.get(serviceName);        if (hostInfos == null) {            hostInfos = Collections.newSetFromMap(new ConcurrentHashMap<HostInfo, Boolean>());            Set<HostInfo> oldHostInfos = referencedServiceAddresses.putIfAbsent(serviceName, hostInfos);            if (oldHostInfos != null) {                hostInfos = oldHostInfos;            }        }        LOGGER.info("add service to referencedServiceAddresses:" + serviceName);        hostInfos.add(hostInfo);        referencedServiceAddresses.put(serviceName,hostInfos);    }    private void removeServiceAddress(String serviceName, HostInfo hostInfo) {        Set<HostInfo> hostInfos = getReferencedServiceAddresses(serviceName);        if (CollectionUtil.isEmpty(hostInfos) || !hostInfos.contains(hostInfo)) {            LOGGER.info(serviceName + "is not a servcice");            return;        }        hostInfos.remove(hostInfo);        if (isAddressReferenced(hostInfo)) {            registeredServices.remove(serviceName);        }    }    private boolean isAddressReferenced(HostInfo hostInfo) {        for (String key : referencedServiceAddresses.keySet()) {            Set<HostInfo> hostInfos = referencedServiceAddresses.get(key);            if (hostInfos.contains(hostInfo)) {                LOGGER.info("address:" + hostInfo + " still been referenced for service:" + key);                return true;            }        }        return false;    }    //服务信息改变监控处理    static class InnerServerInfoListener implements ServerInfoListener {        @Override        public void onServerVersionChange(String serverAddress, String version) {        }    }    //服务摘除监控处理    static class InnerServiceProviderChangeListener implements ServiceProviderChangeListener {        @Override        public void serviceProviderAdded(ServiceProviderChangeEvent event) {            HostInfo hostInfo = new HostInfo(event.getHost(), event.getPort());            RegistryManager.getInstance().addServiceAdress(event.getServiceName(), hostInfo);        }        @Override        public void serviceProviderRemoved(ServiceProviderChangeEvent event) {            HostInfo hostInfo = new HostInfo(event.getHost(), event.getPort());            RegistryManager.getInstance().removeServiceAddress(event.getServiceName(), hostInfo);        }    }}