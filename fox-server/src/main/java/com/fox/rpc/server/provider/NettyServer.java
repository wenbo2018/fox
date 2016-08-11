package com.fox.rpc.server.provider;

import com.fox.rpc.common.bean.RpcRequest;
import com.fox.rpc.common.bean.RpcResponse;
import com.fox.rpc.common.codec.RpcDecoder;
import com.fox.rpc.common.codec.RpcEncoder;
import com.fox.rpc.common.util.StringUtil;
import com.fox.rpc.registry.ServiceRegistry;
import com.fox.rpc.remoting.provider.api.Server;
import com.fox.rpc.server.RpcServer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shenwenbo on 16/8/10.
 */
public class NettyServer implements Server{

    private static final Logger LOGGER = LoggerFactory.getLogger(NettyServer.class);

    private String serviceAddress;

    private ServiceRegistry serviceRegistry;

    /**
     * 存放 服务名 与 服务对象 之间的映射关系
     */
    private Map<String, Object> handlerMap = new HashMap<String, Object>();

    @Autowired
    private NettyServerHandler nettyServerHandler;
    @Autowired
    private RpcEncoder rpcEncoder;
    @Autowired
    private RpcDecoder rpcDecoder;

    public NettyServer(Map<String, Object> handlerMap) {
        this.handlerMap=handlerMap;
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
                    pipeline.addLast(rpcDecoder); // 解码 RPC 请求
                    pipeline.addLast(rpcEncoder); // 编码 RPC 响应
                    pipeline.addLast(nettyServerHandler); // 处理 RPC 请求
                }
            });
            bootstrap.option(ChannelOption.SO_BACKLOG, 1024);
            bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
            // 获取 RPC 服务器的 IP 地址与端口号
            String[] addressArray = StringUtil.split(serviceAddress, ":");
            String ip = addressArray[0];
            int port = Integer.parseInt(addressArray[1]);
            // 启动 RPC 服务器
            ChannelFuture future = bootstrap.bind(ip, port).sync();
            // 注册 RPC 服务地址
            //将这部门功能单独剥离出去
            if (serviceRegistry != null) {
                for (String interfaceName : handlerMap.keySet()) {
                    serviceRegistry.register(interfaceName, serviceAddress);
                    LOGGER.debug("register service: {} => {}", interfaceName, serviceAddress);
                }
            }
            LOGGER.debug("server started on port {}", port);
            // 关闭 RPC 服务器
            future.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
    @Override
    public void close() {

    }

}
