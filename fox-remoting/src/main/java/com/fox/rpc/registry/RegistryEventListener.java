package com.fox.rpc.registry;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by shenwenbo on 2017/3/11.
 */
public class RegistryEventListener {

    private static List<ServerInfoListener> serverInfoListeners = new ArrayList<ServerInfoListener>();

    public static void serverVersionChanged(String serverAddress, String version) {
        for (ServerInfoListener listener : serverInfoListeners) {
            listener.onServerVersionChange(serverAddress, version);
        }
    }

    public synchronized static void addListener(ServerInfoListener listener) {
        serverInfoListeners.add(listener);
    }

}
