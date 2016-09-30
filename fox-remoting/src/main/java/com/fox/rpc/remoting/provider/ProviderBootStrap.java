package com.fox.rpc.remoting.provider;

/**
 * Created by shenwenbo on 2016/9/27.
 */
public class ProviderBootStrap {

    static volatile boolean isInitialized = false;

    public static void init() {
//        if (!isInitialized) {
//            List<Server> servers = SpiServiceLoader.getExtensionList(Server.class);
//            for (Server server : servers) {
//                if (!server.isStarted()) {
//                    if (server.support(config)) {
//                        server.start(config);
//                        httpServer = server;
//                        serversMap.put(server.getProtocol() + server.getPort(), server);
//                        logger.warn("pigeon " + server + "[version:" + VersionUtils.VERSION
//                                + "] has been started");
//                    }
//                }
//            }
//            isInitialized = true;
//        }
    }

}
