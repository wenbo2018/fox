package com.fox.rpc.server.provider;

import com.fox.rpc.common.bean.InvokeResponse;
import com.fox.rpc.remoting.provider.process.ServiceProviderChannel;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import org.apache.log4j.Logger;


/**
 * Created by shenwenbo on 2016/9/28.
 */
public class NettyChannel implements ServiceProviderChannel {

    private  static Logger LOGGER=Logger.getLogger(NettyChannel.class);

    private Channel channel = null;

    public NettyChannel(Channel channel) {
        this.channel = channel;
    }

    @Override
    public void write(final InvokeResponse invokeResponse) {
        ChannelFuture future = this.channel.writeAndFlush(invokeResponse);
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {

            }
        });
    }
}
