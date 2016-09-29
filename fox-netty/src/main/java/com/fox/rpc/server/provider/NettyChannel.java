package com.fox.rpc.server.provider;

import com.fox.rpc.common.bean.InvokeResponse;
import com.fox.rpc.remoting.provider.process.ServiceProviderChannel;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;


/**
 * Created by shenwenbo on 2016/9/28.
 */
public class NettyChannel implements ServiceProviderChannel {

    private Channel channel = null;

    private static final String protocol = "default";

    public NettyChannel(Channel channel) {
        this.channel = channel;
    }
    @Override
    public void write(final InvokeResponse invokeResponse) {
        ChannelFuture future = this.channel.write(invokeResponse);
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future)
                    throws Exception {
            }
        });
    }

}
