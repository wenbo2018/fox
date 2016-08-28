package com.fox.rpc.server.invoke;

import com.fox.rpc.common.bean.InvokeRequest;
import com.fox.rpc.common.bean.InvokeResponse;
import com.fox.rpc.common.codec.RpcDecoder;
import com.fox.rpc.common.codec.RpcEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * Created by wenbo2018 on 2016/8/26.
 */
public class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {

    private NettyClient nettyClient;

    public ClientChannelInitializer(NettyClient nettyClient) {
        this.nettyClient=nettyClient;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new RpcEncoder(InvokeRequest.class)); // 编码 RPC 请求
        pipeline.addLast(new RpcDecoder(InvokeResponse.class)); // 解码 RPC 响应
        pipeline.addLast(new NettyClientHandler(this.nettyClient)); // 处
    }

}
