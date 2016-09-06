package com.fox.rpc.server.invoke;

import com.fox.rpc.common.bean.InvokeRequest;
import com.fox.rpc.common.bean.InvokeResponse;
import com.fox.rpc.remoting.invoker.api.CallFuture;
import com.fox.rpc.remoting.invoker.api.Client;
import com.fox.rpc.remoting.invoker.config.ConnectInfo;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by wenbo2018 on 2016/8/26.
 */
public class NettyClient implements Client{

    private Channel channel;

    private Bootstrap bootstrap=new Bootstrap();

    private ConnectInfo connectInfo;

    private volatile boolean connected = false;

    private InvokeResponse invokeResponse;

    private InvokeRequest invokeRequest;

    private final ConcurrentHashMap<String, BlockingQueue<InvokeResponse>> responseMap = new ConcurrentHashMap<String, BlockingQueue<InvokeResponse>>();

    public NettyClient (EventLoopGroup group,ConnectInfo connectInfo) {
        bootstrap.group(group);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.handler(new ClientChannelInitializer(this));
        bootstrap.option(ChannelOption.TCP_NODELAY, true);
        this.connectInfo=connectInfo;
    }

    @Override
    public CallFuture send(InvokeRequest request) throws Exception {
        this.invokeRequest=request;
        CallFuture callFuture=new CallFuture(request);
        //FutureMap.putFuture(request.getRequestId(),callFuture);
        if (channel.isWritable()) {
            channel.writeAndFlush(request);
        }
        return callFuture;
    }

    @Override
    public void connect() {
        if (this.connected) {
            return;
        }
        // 连接 RPC 服务器
        String host=connectInfo.getHostIp();
        int port=connectInfo.getHostPort();
        ChannelFuture channelFuture=null;
        try {
            channelFuture =bootstrap.connect(host, port).sync();
        } catch (InterruptedException e) {
            System.out.println(e);
            e.printStackTrace();
        }
         channelFuture.addListener(new ChannelFutureListener() {
             @Override
             public void operationComplete(ChannelFuture channelFuture) throws Exception {
                 System.out.println("连接已经建立");
             }
         });
        if (channelFuture.isSuccess()) {
            this.channel=channelFuture.channel();
            this.connected=true;
        }
    }

    @Override
    public void setContext(String host, int port) {

    }

    @Override
    public void processResponse(InvokeResponse invokeResponse) {

        BlockingQueue<InvokeResponse> queue=new LinkedBlockingQueue<InvokeResponse>(1);
        queue.add(invokeResponse);
        responseMap.put(invokeResponse.getRequestId(), queue);
        this.invokeResponse=invokeResponse;
    }

    @Override
    public InvokeResponse getResponse() {

        String messageId=invokeRequest.getRequestId();
        InvokeResponse invokeResponse=null;
        try {
            invokeResponse=responseMap.get(messageId).take();
        } catch (final InterruptedException ex) {

        } finally {
            responseMap.remove(messageId);
        }
        while (this.invokeResponse==null) {

        }
        return this.invokeResponse;
    }
}
