package com.github.wenbo2018.fox.server.provider;

import com.github.wenbo2018.fox.common.bean.InvokeRequest;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.log4j.Logger;

public class NettyServerHandler extends SimpleChannelInboundHandler<InvokeRequest> {

    private static Logger LOGGER=Logger.getLogger(NettyServerHandler.class);

    private NettyServer nettyServer;

    public NettyServerHandler(NettyServer nettyServer) {
        this.nettyServer=nettyServer;
    }

    @Override
    public void channelRead0(final ChannelHandlerContext ctx, InvokeRequest request) throws Exception {
        this.nettyServer.processRequest(request,new NettyChannel(ctx.channel()));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        LOGGER.error("server caught exception", cause);
        ctx.close();
    }
}
