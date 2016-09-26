package com.fox.rpc.server.provider.async;

import com.fox.rpc.common.bean.InvokeRequest;
import com.fox.rpc.common.bean.InvokeResponse;
import com.fox.rpc.common.util.StringUtil;
import com.fox.rpc.server.provider.NettyServerHandler;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;

/**
 * Created by shenwenbo on 16/8/24.
 */
public class AsyncServiceRunnableExecutor<T> implements Callable{

    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncServiceRunnableExecutor.class);

    private ChannelHandlerContext ctx;
    private InvokeRequest request;
    private Object service;

    public AsyncServiceRunnableExecutor(ChannelHandlerContext ctx,InvokeRequest request,Object service) {
        this.ctx=ctx;
        this.request=request;
        this.service=service;
    }

    @Override
    public Object call() throws Exception {
        InvokeResponse response = new InvokeResponse();
        response.setRequestId(request.getRequestId());
        try {
            Object result = handle(request);
            response.setResult(result);
        } catch (Exception e) {
            System.out.println("handle result failure"+e);
            LOGGER.error("handle result failure", e);
            response.setException(e);
        }
        // 写入 RPC 响应对象并自动关闭连接
        ctx.writeAndFlush(response).addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                LOGGER.debug("Send response for request " + request.getRequestId());
            }
        });
        return null;
    }

    private Object handle(InvokeRequest request) throws Exception {
        String serviceName = request.getServiceName();
        String serviceVersion = request.getServiceVersion();
        if (StringUtil.isNotEmpty(serviceVersion)) {
            serviceName += "-" + serviceVersion;
        }

        Object serviceBean = this.service;
        if (serviceBean == null) {
            throw new RuntimeException(String.format("can not find service bean by key: %s", serviceName));
        }
        Class<?> serviceClass = serviceBean.getClass();
        String methodName = request.getMethodName();
        Class<?>[] parameterTypes = request.getParameterTypes();
        Object[] parameters = request.getParameters();
        FastClass serviceFastClass = FastClass.create(serviceClass);
        FastMethod serviceFastMethod = serviceFastClass.getMethod(methodName, parameterTypes);
        return serviceFastMethod.invoke(serviceBean, parameters);
    }


}
