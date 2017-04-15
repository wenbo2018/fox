package com.github.wenbo2018.fox.server.invoke;

import com.github.wenbo2018.fox.common.bean.InvokeResponse;
import com.github.wenbo2018.fox.common.bean.InvokeRequest;
import com.github.wenbo2018.fox.common.codec.invoker.InvokerEncoder;
import com.github.wenbo2018.fox.common.codec.invoker.InvokerDecoder;
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
        pipeline.addLast(new InvokerEncoder(InvokeRequest.class)); // 编码 RPC 请求
        pipeline.addLast(new InvokerDecoder(InvokeResponse.class)); // 解码 RPC 响应
        pipeline.addLast(new NettyClientHandler(this.nettyClient)); // 处
    }

}
