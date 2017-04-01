package com.fox.rpc.remoting.invoker;

import com.fox.rpc.remoting.invoker.task.HeartBeatTask;
import com.fox.rpc.common.HostInfo;
import com.fox.rpc.common.extension.UserServiceLoader;
import com.fox.rpc.common.util.CollectionUtil;
import com.fox.rpc.registry.RegistryEventListener;
import com.fox.rpc.registry.RegistryManager;
import com.fox.rpc.registry.listener.ServiceProviderChangeEvent;
import com.fox.rpc.registry.listener.ServiceProviderChangeListener;
import com.fox.rpc.remoting.common.ConnectInfo;
import com.fox.rpc.remoting.invoker.api.Client;
import com.fox.rpc.remoting.invoker.api.ClientFactory;
import com.fox.rpc.remoting.invoker.config.InvokerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by wenbo2018 on 2016/8/26.
 */
public class ClientManager {

    private static Logger LOGGER = LoggerFactory.getLogger(ClientManager.class);

    private ServiceProviderChangeListener providerChangeListener = new InnerServiceProviderChangeListener();

    private static ClientManager instance = new ClientManager();

    private static volatile boolean isInit = false;

    private static Map<InvokerConfig, List<Client>> clientsMap = new ConcurrentHashMap<>();

    private ClientFactory clientFactory;

    private  ExecutorService heartBeatThreadPool;

    private HeartBeatTask heartBeatTask;

    public static ClientManager getInstance() {
        if (!isInit) {
            synchronized (RegistryManager.class) {
                if (!isInit) {
                    instance.init();
                    isInit = true;
                }
            }
        }
        return instance;
    }

    private ClientManager() {
        this.heartBeatThreadPool=Executors.newFixedThreadPool(1, Executors.defaultThreadFactory());
        this.heartBeatTask=new HeartBeatTask();
        heartBeatThreadPool.execute(this.heartBeatTask);
        RegistryEventListener.addListener(providerChangeListener);

    }


    private void init() {
        clientFactory = UserServiceLoader.newExtension(ClientFactory.class);
        clientFactory.init();
    }

    /**
     * 注册一个连接；
     *
     * @param invokerConfig
     * @param host
     * @param port
     */
    public void registerClient(InvokerConfig invokerConfig, String host, int port) {
        ConnectInfo connectInfo = new ConnectInfo(host, port);
        Client client = selectConnect(connectInfo);
        List<Client> clientList = clientsMap.get(invokerConfig);
        if (CollectionUtil.isEmpty(clientList)) {
            if (clientList == null)
                clientList = new ArrayList<>();
            clientList.add(client);
        } else {
            clientList.add(client);
        }
        clientsMap.put(invokerConfig, clientList);
    }

    /**
     * 创建一个netty连接
     * @param connectInfo
     * @return
     */
    public Client selectConnect(ConnectInfo connectInfo) {
        return clientFactory.getClient(connectInfo);
    }

    public Client getClient(InvokerConfig invokerConfig) {
        List<Client> clients = clientsMap.get(invokerConfig);
        if (CollectionUtil.isEmpty(clients)) {
            Set<HostInfo> hostInfos = RegistryManager.getInstance()
                    .getServiceHost(invokerConfig.getServiceName());
            if (CollectionUtil.isEmpty(hostInfos)) {
                throw new RuntimeException("service:" + invokerConfig.getServiceName() + ":is not can use");
            }
            for (HostInfo hostInfo : hostInfos) {
                registerClient(invokerConfig, hostInfo.getHost(), hostInfo.getPort());
            }
            clients = clientsMap.get(invokerConfig);
        }
        return clients.get(1 + (int) (Math.random() * clients.size()) - 1);
    }


    public List<Client> getClients() {
        List<Client> clients = new ArrayList<Client>();
        Collection<List<Client>> _clients = clientsMap.values();
        for (List<Client> client : _clients) {
            clients.addAll(client);
        }

        return clients;
    }

    //服务增加监控处理
    class InnerServiceProviderChangeListener implements ServiceProviderChangeListener {

        @Override
        public void serviceProviderAdded(ServiceProviderChangeEvent event) {

        }

        @Override
        public void serviceProviderRemoved(ServiceProviderChangeEvent event) {

        }
    }

}
