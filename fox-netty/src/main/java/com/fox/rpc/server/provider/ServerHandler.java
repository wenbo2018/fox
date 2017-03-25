package com.fox.rpc.server.provider;

import com.fox.rpc.common.bean.BaseRequestResponse;
import com.fox.rpc.server.CustomSimpleChannelInboundHandler;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;


public class ServerHandler extends CustomSimpleChannelInboundHandler {
    private NettyServer nettyServer;
    public ServerHandler(NettyServer nettyServer) {
        super("server");
        this.nettyServer=nettyServer;
    }

    @Override
    protected void handleData(ChannelHandlerContext channelHandlerContext, BaseRequestResponse baseRequestResponse) {
        this.nettyServer.processRequest(baseRequestResponse.getInvokeRequest(),new NettyChannel(channelHandlerContext.channel()));

    }

    @Override
    protected void handleReaderIdle(ChannelHandlerContext ctx) {
        super.handleReaderIdle(ctx);
//        System.err.println("---client " + ctx.channel().remoteAddress().toString() + " reader timeout, close it---");
//        ctx.close();
    }
}