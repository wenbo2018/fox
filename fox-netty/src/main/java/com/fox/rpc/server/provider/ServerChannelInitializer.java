package com.fox.rpc.server.provider;

import com.fox.rpc.common.bean.InvokeRequest;
import com.fox.rpc.common.bean.InvokeResponse;
import com.fox.rpc.common.codec.provider.ProviderDecoder;
import com.fox.rpc.common.codec.provider.ProviderEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * Created by shenwenbo on 2016/9/28.
 */
public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {

    private NettyServer nettyServer;

    public ServerChannelInitializer(NettyServer nettyServer) {
        this.nettyServer=nettyServer;
    }

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast(new ProviderDecoder(InvokeRequest.class)); // 解码 RPC 请求
        pipeline.addLast(new ProviderEncoder(InvokeResponse.class)); // 编码 RPC 响应
        pipeline.addLast(new NettyServerHandler(this.nettyServer));
    }
}
