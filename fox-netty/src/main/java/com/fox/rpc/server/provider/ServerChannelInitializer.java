package com.fox.rpc.server.provider;

import com.fox.rpc.common.bean.InvokeRequest;
import com.fox.rpc.common.bean.InvokeResponse;
import com.fox.rpc.common.codec.RpcDecoder;
import com.fox.rpc.common.codec.RpcEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

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
        pipeline.addLast(new LengthFieldBasedFrameDecoder(65536, 0, 4, 0, 0));
        pipeline.addLast(new RpcDecoder(InvokeRequest.class)); // 解码 RPC 请求
        pipeline.addLast(new RpcEncoder(InvokeResponse.class)); // 编码 RPC 响应
        pipeline.addLast(new NettyServerHandler(this.nettyServer));
    }
}
