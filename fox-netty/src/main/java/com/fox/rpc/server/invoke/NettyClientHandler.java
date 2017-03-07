package com.fox.rpc.server.invoke;

import com.fox.rpc.common.bean.InvokeResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by shenwenbo on 16/8/10.
 */
public class NettyClientHandler extends SimpleChannelInboundHandler<InvokeResponse> {

    private static final Logger LOGGER = LoggerFactory.getLogger(NettyClientHandler.class);

    private NettyClient nettyClient;

    public NettyClientHandler(NettyClient nettyClient) {
        this.nettyClient=nettyClient;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, InvokeResponse invokeResponse) throws Exception {
        LOGGER.debug("client receive message:{}",invokeResponse.toString());
        nettyClient.processResponse(invokeResponse);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOGGER.error("api caught exception", cause);
        ctx.close();
    }

}
