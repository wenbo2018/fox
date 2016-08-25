package com.fox.rpc.server.provider;

import com.fox.rpc.common.bean.InvokeRequest;
import com.fox.rpc.common.bean.InvokeResponse;
import com.fox.rpc.common.codec.RpcDecoder;
import com.fox.rpc.common.codec.RpcEncoder;
import com.fox.rpc.registry.RemotingServiceRegistry;
import com.fox.rpc.remoting.provider.api.Server;
import com.fox.rpc.remoting.provider.config.ProviderCfg;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import java.util.Map;

public class NettyServer implements Server {

    private static final Logger LOGGER = LoggerFactory.getLogger(NettyServer.class);


    private RemotingServiceRegistry serviceRegistry;

    private Map<String, Object> handlerMap = new HashMap<String, Object>();

    private String serviceIp;

    private int servicePort;



    @Override
    public void setContext(ProviderCfg cfg) {
        this.serviceRegistry =cfg.getRemotingServiceRegistry();
        this.handlerMap = cfg.getHandlerMap();
        this.serviceIp=cfg.getServiceAddress();
        this.servicePort=Integer.parseInt(cfg.getServicePort());
    }

    @Override
    public void star() throws Exception {

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            // 创建并初始化 Netty 服务端 Bootstrap 对象
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup);
            bootstrap.channel(NioServerSocketChannel.class);
            bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel channel) throws Exception {
                    ChannelPipeline pipeline = channel.pipeline();
                    pipeline.addLast(new RpcDecoder(InvokeRequest.class)); // 解码 RPC 请求
                    pipeline.addLast(new RpcEncoder(InvokeResponse.class)); // 编码 RPC 响应
                    pipeline.addLast(new NettyServerHandler(handlerMap)); // 处理 RPC 请求
                }
            });
            bootstrap.option(ChannelOption.SO_BACKLOG, 1024);
            bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture future = bootstrap.bind(serviceIp,servicePort).sync();
            LOGGER.debug("server started on port {}",serviceIp+":"+servicePort);
            future.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }

    }
}


