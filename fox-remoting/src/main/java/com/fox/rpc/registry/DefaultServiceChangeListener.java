package com.fox.rpc.registry;

import com.fox.rpc.common.HostInfo;
import com.fox.rpc.remoting.invoker.ClientManager;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by shenwenbo on 2017/3/11.
 */
public class DefaultServiceChangeListener implements ServiceChangeListener {

    private static Logger LOGGER= LoggerFactory.getLogger(DefaultServiceChangeListener.class);

    @Override
     public void  onServiceHostChange(String serviceName, List<String[]> hostList) {
           Set<HostInfo> oldHosts=RegistryManager.getInstance().getReferencedServiceAddresses(serviceName);
           Set<HostInfo> newHosts=parseHostPortList(serviceName,hostList);
           Set<HostInfo> needAddHpSet = Collections.emptySet();
           Set<HostInfo> needRemoveSet=Collections.emptySet();
           if (oldHosts==null) {
               needAddHpSet=newHosts;
           } else {
               needAddHpSet = Collections.newSetFromMap(new ConcurrentHashMap<HostInfo, Boolean>());
               needAddHpSet.addAll(newHosts);
               needAddHpSet.removeAll(oldHosts);

               needRemoveSet= Collections.newSetFromMap(new ConcurrentHashMap<HostInfo, Boolean>());
               needRemoveSet.addAll(oldHosts);
               needRemoveSet.removeAll(newHosts);
           }
           LOGGER.info("service host change:"+newHosts);
           for (HostInfo hostPort : needAddHpSet) {
               System.err.println("服务"+serviceName+"增加主机,主机名为:"+hostPort.getHost()+":"+hostPort.getPort()+"---"+needAddHpSet.size());
               RegistryEventListener.providerAdded(serviceName, hostPort.getHost(), hostPort.getPort(),1);
               RegistryEventListener.serverVersionChanged(serviceName,hostPort.getPort()+"1");
           }
           for (HostInfo hostPort :needRemoveSet) {
               RegistryEventListener.providerRemoved(serviceName, hostPort.getHost(), hostPort.getPort(),0);
           }
    }

    private Set<HostInfo> parseHostPortList(String serviceName, List<String[]> hostList) {
        Set<HostInfo> hpSet = Collections.newSetFromMap(new ConcurrentHashMap<HostInfo, Boolean>());
        if (hostList != null) {
            for (String[] parts : hostList) {
                String host = parts[0];
                String port = parts[1];
                HostInfo hostInfo=new HostInfo();
                hostInfo.setPort(Integer.parseInt(port));
                hostInfo.setHost(host);
                hpSet.add(hostInfo);
            }
        }
        return hpSet;
    }

}
