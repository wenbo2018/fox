package com.fox.rpc.registry;


import com.fox.rpc.registry.listener.ServiceProviderChangeEvent;
import com.fox.rpc.registry.listener.ServiceProviderChangeListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shenwenbo on 2017/3/11.
 */
public class RegistryEventListener {

    private static List<ServerInfoListener> serverInfoListeners = new ArrayList<ServerInfoListener>();

    private static List<ServiceProviderChangeListener> serviceProviderChangeListeners
            = new ArrayList<ServiceProviderChangeListener>();


    public static void serverVersionChanged(String serverAddress, String version) {
        for (ServerInfoListener listener : serverInfoListeners) {
            listener.onServerVersionChange(serverAddress, version);
        }
    }

    public synchronized static void addListener(ServerInfoListener listener) {
        serverInfoListeners.add(listener);
    }


    public synchronized static void addListener(ServiceProviderChangeListener serviceProviderChangeListenerner) {
        serviceProviderChangeListeners.add(serviceProviderChangeListenerner);
    }


    public static void providerAdded(String serviceName, String host, int port, int weight) {
        List<ServiceProviderChangeListener> listeners = new ArrayList<ServiceProviderChangeListener>();
        listeners.addAll(serviceProviderChangeListeners);
        for (ServiceProviderChangeListener listener : listeners) {
            ServiceProviderChangeEvent event = new ServiceProviderChangeEvent(serviceName, host, port);
            listener.serviceProviderAdded(event);
        }
    }

}
