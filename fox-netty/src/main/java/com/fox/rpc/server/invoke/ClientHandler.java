package com.fox.rpc.server.invoke;

import com.fox.rpc.common.bean.BaseRequestResponse;
import com.fox.rpc.server.CustomSimpleChannelInboundHandler;
import io.netty.channel.ChannelHandlerContext;

public class ClientHandler extends CustomSimpleChannelInboundHandler {

    private  NettyClient nettyClient;

    public ClientHandler(NettyClient nettyClient) {
        super("nettyClient");
        this.nettyClient=nettyClient;
    }

    @Override
    protected void handleData(ChannelHandlerContext channelHandlerContext,BaseRequestResponse baseRequestResponse) {
        nettyClient.processResponse(baseRequestResponse.getInvokeResponse());
    }

    @Override
    protected void handleAllIdle(ChannelHandlerContext ctx) {
        super.handleAllIdle(ctx);
        sendPingMsg(ctx);
    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        nettyClient.reConnect();
    }
}