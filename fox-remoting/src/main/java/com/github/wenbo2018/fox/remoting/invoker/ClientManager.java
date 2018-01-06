package com.github.wenbo2018.fox.remoting.invoker;

import com.github.wenbo2018.fox.common.extension.ExtensionServiceLoader;
import com.github.wenbo2018.fox.remoting.invoker.config.InvokerConfig;
import com.github.wenbo2018.fox.registry.listener.ServiceProviderChangeListener;
import com.github.wenbo2018.fox.common.HostInfo;
import com.github.wenbo2018.fox.remoting.common.ConnectInfo;
import com.github.wenbo2018.fox.remoting.invoker.api.ClientFactory;
import com.github.wenbo2018.fox.remoting.invoker.task.HeartBeatTask;
import com.github.wenbo2018.fox.remoting.invoker.cluster.Route;
import com.github.wenbo2018.fox.common.util.CollectionUtil;
import com.github.wenbo2018.fox.registry.RegistryEventListener;
import com.github.wenbo2018.fox.registry.RegistryManager;
import com.github.wenbo2018.fox.registry.listener.ServiceProviderChangeEvent;
import com.github.wenbo2018.fox.remoting.invoker.api.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by wenbo2018 on 2016/8/26.
 */
public class ClientManager {

    private final static Logger LOGGER = LoggerFactory.getLogger(ClientManager.class);

    private ServiceProviderChangeListener providerChangeListener = new InnerServiceProviderChangeListener();

    private static ClientManager instance = new ClientManager();

    private static volatile boolean isInit = false;

    private static Map<InvokerConfig, List<Client>> clientsMap = new ConcurrentHashMap<>();

    private static Map<HostInfo, InvokerConfig> hostInfoInvokerConfigMap = new ConcurrentHashMap<>();

    private ClientFactory clientFactory;

    private ExecutorService heartBeatThreadPool;

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
        this.heartBeatThreadPool = Executors.newFixedThreadPool(1, Executors.defaultThreadFactory());
        this.heartBeatTask = new HeartBeatTask();
        heartBeatThreadPool.execute(this.heartBeatTask);
        RegistryEventListener.addListener(providerChangeListener);

    }

    private void init() {
        clientFactory = ExtensionServiceLoader.newExtension(ClientFactory.class);
        clientFactory.init();
    }


    public void registerClient(InvokerConfig invokerConfig, HostInfo hostInfo) {
        ConnectInfo connectInfo = new ConnectInfo(hostInfo.getHost(), hostInfo.getPort());
        Client client = selectConnect(connectInfo);
        List<Client> clientList = clientsMap.get(invokerConfig);
        if (CollectionUtil.isEmpty(clientList)) {
            if (clientList == null) {
                clientList = new ArrayList<>();
            }
            clientList.add(client);
        } else {
            clientList.add(client);
        }
        hostInfoInvokerConfigMap.put(hostInfo, invokerConfig);
        clientsMap.put(invokerConfig, clientList);
    }


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
                registerClient(invokerConfig, hostInfo);
            }
            clients = clientsMap.get(invokerConfig);
        }
        Route route = ExtensionServiceLoader.getExtension(Route.class);
        return route.route(clients, invokerConfig);
    }


    public List<Client> getClients() {
        List<Client> clients = new ArrayList<Client>();
        Collection<List<Client>> _clients = clientsMap.values();
        for (List<Client> client : _clients) {
            clients.addAll(client);
        }
        return clients;
    }

    private void removeHost(HostInfo hostInfo) {
        if (hostInfoInvokerConfigMap.containsKey(hostInfo)) {
            InvokerConfig invokerConfig = hostInfoInvokerConfigMap.get(hostInfo);
            if (invokerConfig != null) {
                clientsMap.remove(invokerConfig);
                LOGGER.debug("currcet client info:{}", clientsMap.toString());
                LOGGER.debug("remove host success:{}", hostInfo.toString());
            }
        }
    }


    //服务变化监控处理
    class InnerServiceProviderChangeListener implements ServiceProviderChangeListener {

        @Override
        public void serviceProviderAdded(ServiceProviderChangeEvent event) {

        }

        @Override
        public void serviceProviderRemoved(ServiceProviderChangeEvent event) {
            HostInfo hostInfo = new HostInfo(event.getHost(), event.getPort());
            removeHost(hostInfo);
        }
    }

}
