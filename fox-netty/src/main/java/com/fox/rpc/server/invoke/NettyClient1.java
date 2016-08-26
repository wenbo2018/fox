package com.fox.rpc.server.invoke;

import com.fox.rpc.common.bean.InvokeRequest;
import com.fox.rpc.common.bean.InvokeResponse;
import com.fox.rpc.common.codec.RpcDecoder;
import com.fox.rpc.common.codec.RpcEncoder;
import com.fox.rpc.remoting.invoker.api.Client;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class NettyClient1{
    //extends SimpleChannelInboundHandler<InvokeResponse> implements Client{
//
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(NettyClient1.class);
//
//    private  String host;
//    private  int port;
//
//    private InvokeResponse response;
//
//    @Override
//    public void channelRead0(ChannelHandlerContext ctx, InvokeResponse response) throws Exception {
//        System.out.println(response.toString());
//        this.response = response;
//    }
//
//    @Override
//    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        LOGGER.error("api caught exception", cause);
//        System.out.println("api caught exception"+cause);
//        ctx.close();
//    }
//
//    @Override
//    public void setContext(String host, int port) {
//        this.host=host;
//        this.port=port;
//    }
//
//
//    @Override
//    public InvokeResponse send(InvokeRequest request)throws Exception {
//        EventLoopGroup group = new NioEventLoopGroup();
//        try {
//            // 创建并初始化 Netty 客户端 Bootstrap 对象
//            Bootstrap bootstrap = new Bootstrap();
//            bootstrap.group(group);
//            bootstrap.channel(NioSocketChannel.class);
//            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
//                @Override
//                public void initChannel(SocketChannel channel) throws Exception {
//                    ChannelPipeline pipeline = channel.pipeline();
//                    pipeline.addLast(new RpcEncoder(InvokeRequest.class)); // 编码 RPC 请求
//                    pipeline.addLast(new RpcDecoder(InvokeResponse.class)); // 解码 RPC 响应
//                    pipeline.addLast(NettyClient1.this); // 处理 RPC 响应
//                }
//            });
//            bootstrap.option(ChannelOption.TCP_NODELAY, true);
//            // 连接 RPC 服务器
//            ChannelFuture future = bootstrap.connect(host, port).sync();
//
//            /****/
//            future.channel().writeAndFlush(request);
////            future.addListener(new ChannelFutureListener() {
////                @Override
////                public void operationComplete(ChannelFuture channelFuture) throws Exception {
////                    System.out.println(channelFuture.get().toString());
////                }
////            });
//
//            // 写入 RPC 请求数据并关闭连接
////            Channel channel = future.channel();
////            System.out.println("请求服务器");
////            channel.writeAndFlush(request).sync();
////
////            System.out.println("关闭连接");
////            channel.closeFuture().sync();
//            // 返回 RPC 响应对象
//            future.wait(1000);
//            System.out.println("返回值");
//
//            return response;
//        } finally {
//            group.shutdownGracefully();
//        }
//    }
//
//    public InvokeResponse getResponse() {
//        return response;
//    }
//
//    public void setResponse(InvokeResponse response) {
//        this.response = response;
//    }
}
