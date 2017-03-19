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

    static int ksp=1;

    @Override
    public void onServiceHostChange(String serviceName, List<String[]> hostList) {
        //获取旧的服务;
        Set<HostInfo> oldHosts=RegistryManager.getInstance().getReferencedServiceAddresses(serviceName);
        Set<HostInfo> newHosts=parseHostPortList(serviceName,hostList);
        Set<HostInfo> needAddHpSet = Collections.emptySet();
        if (CollectionUtils.isEmpty(oldHosts)) {
            needAddHpSet=newHosts;
        } else {
            needAddHpSet = Collections.newSetFromMap(new ConcurrentHashMap<HostInfo, Boolean>());
            needAddHpSet.addAll(newHosts);
            needAddHpSet.removeAll(oldHosts);
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.info("service host change:"+newHosts);
        }
        for (HostInfo hostPort : needAddHpSet) {
            System.err.println(ksp++);
            RegistryEventListener.providerAdded(serviceName, hostPort.getHost(), hostPort.getPort(),1);
            RegistryEventListener.serverVersionChanged(serviceName,hostPort.getPort()+"1");
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
