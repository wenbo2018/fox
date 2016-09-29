package com.fox.rpc.server.provider;

import com.fox.rpc.common.bean.InvokeRequest;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NettyServerHandler extends SimpleChannelInboundHandler<InvokeRequest> {

    private static final Logger LOGGER = LoggerFactory.getLogger(NettyServerHandler.class);


    private NettyServer nettyServer;

    public NettyServerHandler(NettyServer nettyServer) {
        this.nettyServer=nettyServer;
    }

    @Override
    public void channelRead0(final ChannelHandlerContext ctx, InvokeRequest request) throws Exception {
        //线程池处理
        //AsyncServiceExecutor.submitCallback(ctx,request,handlerMap.get(request.getServiceName()));

        this.nettyServer.processRequest(request,new NettyChannel(ctx.channel()));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        LOGGER.error("server caught exception", cause);
        ctx.close();
    }
}
