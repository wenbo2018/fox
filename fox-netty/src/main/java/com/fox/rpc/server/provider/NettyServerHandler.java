package com.fox.rpc.server.provider;

import com.fox.rpc.common.bean.InvokeRequest;
import com.fox.rpc.server.provider.async.AsyncServiceExecutor;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Map;

public class NettyServerHandler extends SimpleChannelInboundHandler<InvokeRequest> {

    private static final Logger LOGGER = LoggerFactory.getLogger(NettyServerHandler.class);

    private final Map<String, Object> handlerMap;

    public NettyServerHandler(Map<String, Object> handlerMap) {
        this.handlerMap = handlerMap;
    }

    @Override
    public void channelRead0(final ChannelHandlerContext ctx, InvokeRequest request) throws Exception {
        //线程池处理
        AsyncServiceExecutor.submitCallback(ctx,request,handlerMap.get(request.getServiceName()));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        LOGGER.error("server caught exception", cause);
        System.out.println("server caught exception"+cause);
        ctx.close();
    }
}
