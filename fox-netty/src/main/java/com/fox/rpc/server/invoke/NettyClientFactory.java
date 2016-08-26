package com.fox.rpc.server.invoke;


import com.fox.rpc.remoting.invoker.api.Client;
import com.fox.rpc.remoting.invoker.api.ClientFactory;
import com.fox.rpc.remoting.invoker.config.ConnectInfo;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by wenbo2018 on 2016/8/26.
 */
public class NettyClientFactory implements ClientFactory{

    EventLoopGroup group;

    Map<ConnectInfo,Channel> channelMap=new ConcurrentHashMap<ConnectInfo, Channel>();

    ConcurrentHashMap<ConnectInfo,Client> clients=new ConcurrentHashMap<ConnectInfo,Client>();

    private static volatile boolean isStartup = false;

    @Override
    public void init() {
        if (!isStartup) {
            this.group = new NioEventLoopGroup(4);
        }
    }

    @Override
    public Client createClient(ConnectInfo connectInfo) {
        Client client=new NettyClient(this.group,connectInfo);
        clients.put(connectInfo,client);
        return client;
    }

    @Override
    public Client getClient(ConnectInfo connectInfo) {
        Client client=clients.get(connectInfo);
        if (client==null) {
            client=new NettyClient(this.group,connectInfo);
            client.connect();;
            clients.put(connectInfo,client);
            return client;
        }
        return client;
    }
}
