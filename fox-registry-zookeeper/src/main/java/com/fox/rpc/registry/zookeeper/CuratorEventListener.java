package com.fox.rpc.registry.zookeeper;

import com.fox.rpc.registry.DefaultServiceChangeListener;
import com.fox.rpc.registry.RegistryEventListener;
import com.fox.rpc.registry.ServiceChangeListener;
import com.fox.rpc.registry.exception.RegistryException;
import org.apache.commons.lang.StringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.CuratorListener;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shenwenbo on 2017/3/11.
 */
public class CuratorEventListener implements CuratorListener {

    private Logger LOGGER = LoggerFactory.getLogger(CuratorEventListener.class);

    private static final int ADDRESS = 1;
    private static final int WEIGHT = 2;
    private static final int VERSION = 4;

    private CuratorClient client;

    ServiceChangeListener serviceChangeListener = new DefaultServiceChangeListener();

    public CuratorEventListener(CuratorClient client) {
        this.client = client;
    }

    @Override
    public void eventReceived(CuratorFramework client, CuratorEvent curatorEvent) throws Exception {
        WatchedEvent event = (curatorEvent == null ? null : curatorEvent.getWatchedEvent());
        if (event == null
                || (event.getType() != Watcher.Event.EventType.NodeCreated && event.getType()
                != Watcher.Event.EventType.NodeDataChanged
                && event.getType() != Watcher.Event.EventType.NodeDeleted && event.getType()
                != Watcher.Event.EventType.NodeChildrenChanged)) {
            return;
        }
        logEvent(event);
        try {
            PathInfo pathInfo = parsePath(event.getPath());
            if (pathInfo == null) {
                LOGGER.warn("Failed to parse path " + event.getPath());
                return;
            }
            if (pathInfo.type == ADDRESS) {
                addressChanged(pathInfo);
            } else if (pathInfo.type == VERSION) {
                versionChanged(pathInfo);
            }
        } catch (Throwable e) {
            LOGGER.error("Error in ZookeeperWatcher.process()", e);
            return;
        }
    }

    /***
     * log日志
     * @param event
     */
    private void logEvent(WatchedEvent event) {
        StringBuilder sb = new StringBuilder();
        sb.append("zookeeper event received, type: ").append(event.getType()).append(", path: ")
                .append(event.getPath());
        LOGGER.info(sb.toString());
    }

    /***
     * 服务地址变化
     * @param pathInfo
     * @throws Exception
     */
    private void addressChanged(PathInfo pathInfo) throws Exception {
        String hosts = client.get(pathInfo.path);
        LOGGER.info("Service address changed, path " + pathInfo.path + " value " + hosts);
        List<String[]> hostDetail = getServiceIpPortList(hosts);
        serviceChangeListener.onServiceHostChange(pathInfo.serviceName, hostDetail);
        client.watch(pathInfo.path);
    }


    private void versionChanged(PathInfo pathInfo) throws RegistryException {
        try {
            String version = client.get(pathInfo.path);
            LOGGER.info("version changed, path " + pathInfo.path + " value " + version);
            RegistryEventListener.serverVersionChanged(pathInfo.server, version);
            client.watch(pathInfo.path);
        } catch (Exception e) {
            throw new RegistryException(e);
        }
    }


    public PathInfo parsePath(String path) {
        if (path == null)
            return null;
        PathInfo pathInfo = null;
        if (path.startsWith(Constants.SERVICE_PATH)) {
            pathInfo = new PathInfo(path);
            pathInfo.type = ADDRESS;
            pathInfo.serviceName = path.substring(Constants.SERVICE_PATH.length() + 1);
            int idx = pathInfo.serviceName.indexOf(Constants.PATH_SEPARATOR);
            if (idx != -1) {
                pathInfo.serviceName = pathInfo.serviceName.substring(0, idx);
            }
        }
        return pathInfo;
    }


    public List<String[]> getServiceIpPortList(String serviceAddress) {
        List<String[]> result = new ArrayList<String[]>();
        if (serviceAddress != null && serviceAddress.length() > 0) {
            String[] hostArray = serviceAddress.split(",");
            for (String host : hostArray) {
                int idx = host.lastIndexOf(":");
                if (idx != -1) {
                    String ip = null;
                    int port = -1;
                    try {
                        ip = host.substring(0, idx);
                        port = Integer.parseInt(host.substring(idx + 1));
                    } catch (RuntimeException e) {
                        LOGGER.warn("invalid host: " + host + ", ignored!");
                    }
                    if (ip != null && port > 0) {
                        result.add(new String[]{ip, port + ""});
                    }
                } else {
                    LOGGER.warn("invalid host: " + host + ", ignored!");
                }
            }
        }
        return result;
    }


    class PathInfo {
        String path;
        String serviceName;
        String group;
        String server;
        int type;

        PathInfo(String path) {
            this.path = path;
        }
    }
}
