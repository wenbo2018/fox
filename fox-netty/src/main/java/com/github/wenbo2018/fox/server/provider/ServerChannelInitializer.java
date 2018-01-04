package com.github.wenbo2018.fox.server.provider;

import com.github.wenbo2018.fox.common.bean.InvokeRequest;
import com.github.wenbo2018.fox.common.bean.InvokeResponse;
import com.github.wenbo2018.fox.common.codec.provider.ProviderDecoder;
import com.github.wenbo2018.fox.common.codec.provider.ProviderEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * Created by shenwenbo on 2016/9/28.
 */
public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {

    private NettyServer nettyServer;

    public ServerChannelInitializer(NettyServer nettyServer) {
        this.nettyServer = nettyServer;
    }

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast(new ProviderDecoder(InvokeRequest.class));
        pipeline.addLast(new ProviderEncoder(InvokeResponse.class));
        pipeline.addLast(new NettyServerHandler(this.nettyServer));
    }
}
