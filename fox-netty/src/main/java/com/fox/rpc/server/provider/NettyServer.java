package com.fox.rpc.server.provider;

import com.fox.rpc.remoting.provider.AbstractServer;
import com.fox.rpc.remoting.provider.config.ServerConfig;
import com.fox.rpc.remoting.provider.config.ServiceProviderConfig;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer extends AbstractServer {

    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(NettyServer.class);

    private ServerBootstrap bootstrap;

    private EventLoopGroup bossGroup;

    private EventLoopGroup workerGroup;


    private String serviceIp;

    private int servicePort;

    private volatile boolean started = false;


    public NettyServer() {
        this.bossGroup = new NioEventLoopGroup();
        this.workerGroup = new NioEventLoopGroup();
        // 创建并初始化 Netty 服务端 Bootstrap 对象
        this.bootstrap = new ServerBootstrap();
        this.bootstrap.group(bossGroup, workerGroup);
        this.bootstrap.channel(NioServerSocketChannel.class);
        this.bootstrap.childHandler(new ServerChannelInitializer(this));
        bootstrap.option(ChannelOption.SO_BACKLOG, 1024);
        bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
    }


    @Override
    public void setContext(ServiceProviderConfig cfg) {
        this.serviceIp=cfg.getServiceAddress();
        this.servicePort=Integer.parseInt(cfg.getServicePort());
    }

    @Override
    public boolean isStarted() {
        return this.started;
    }

    @Override
    public void star() throws Exception {

    }

    @Override
    protected void doStart(ServerConfig serverConfig) {
        if (!started) {
            ChannelFuture future = null;
            try {
                future = this.bootstrap.bind(serverConfig.getIp(),serverConfig.getPort()).sync();
                future.addListener(new ChannelFutureListener() {
                    @Override
                    public void operationComplete(ChannelFuture future) throws Exception {
                         LOGGER.info("netty service stared");
                    }
                });
                //future.channel().closeFuture().sync();
            } catch (InterruptedException e) {
                LOGGER.error("Netty start error:"+e);
            }
            this.started=true;
            LOGGER.debug("server started on port :"+serviceIp+":"+servicePort);
        }
    }

    @Override
    protected void doStop() {
        if (this.started) {
            if (this.workerGroup!=null) {
                this.workerGroup.shutdownGracefully();
            }
            if (this.bossGroup!=null) {
                this.bossGroup.shutdownGracefully();
            }
            this.started = false;
        }
    }
}


