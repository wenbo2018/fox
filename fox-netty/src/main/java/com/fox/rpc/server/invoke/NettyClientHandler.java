package com.fox.rpc.server.invoke;

import com.fox.rpc.common.bean.InvokeResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by shenwenbo on 16/8/10.
 */
public class NettyClientHandler extends SimpleChannelInboundHandler<InvokeResponse> {

    private static final Logger LOGGER = LoggerFactory.getLogger(NettyClientHandler.class);

    private NettyClient nettyClient;

    public NettyClientHandler(NettyClient nettyClient) {
        this.nettyClient = nettyClient;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext,
                                InvokeResponse invokeResponse) throws Exception {
        LOGGER.debug("client receive message:{}", invokeResponse.toString());
        nettyClient.processResponse(invokeResponse);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOGGER.error("api caught exception", cause);
        ctx.close();
    }


//    public void userEventTriggered(ChannelHandlerContext ctx, Object evt)
//            throws Exception {
//        if (evt instanceof IdleStateEvent) {
//            IdleStateEvent event = (IdleStateEvent) evt;
//            if (event.state().equals(IdleState.READER_IDLE)) {
//                System.out.println("READER_IDLE");
//                // 超时关闭channel
//                ctx.close();
//            } else if (event.state().equals(IdleState.WRITER_IDLE)) {
//                System.out.println("WRITER_IDLE");
//            } else if (event.state().equals(IdleState.ALL_IDLE)) {
//                System.out.println("ALL_IDLE");
//                // 发送心跳
//                ctx.channel().write("ping\n");
//            }
//        }
//        super.userEventTriggered(ctx, evt);
//    }

}
