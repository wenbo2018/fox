package com.fox.rpc.remoting.provider.async;

import com.fox.rpc.common.bean.InvokeRequest;
import com.fox.rpc.common.common.FoxConstants;
import com.fox.rpc.common.util.StringUtil;
import com.fox.rpc.remoting.enums.ReturnEnum;
import com.fox.rpc.remoting.provider.config.ProviderConfig;
import com.fox.rpc.remoting.provider.process.ServiceProviderChannel;
import com.fox.rpc.common.bean.InvokeResponse;
import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;

/**
 * Created by shenwenbo on 16/8/24.
 */
public class AsyncServiceRunnable<T> implements Callable{

    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncServiceRunnable.class);

    //private ChannelHandlerContext ctx;
    private ServiceProviderChannel channel;
    private InvokeRequest request;
    private ProviderConfig providerConfig;

    public AsyncServiceRunnable(ServiceProviderChannel channel, InvokeRequest request,ProviderConfig providerConfig) {
        this.channel=channel;
        this.request=request;
        this.providerConfig=providerConfig;
    }

    @Override
    public Object call() throws Exception {
        InvokeResponse response = new InvokeResponse();
        response.setRequestId(request.getRequestId());
        if (this.request.getMessageType()== FoxConstants.MESSAGE_TYPE_HEART) {
            LOGGER.info("message type:heart beat");
            response.setSerialize(request.getSerialize());
            response.setSeq(request.getSeq());
            channel.write(response);
            return null;
        }
        try {
            LOGGER.info("message type:service");
            Object result = handle(request);
            response.setResult(result);
            response.setSerialize(request.getSerialize());
            response.setReturnType(ReturnEnum.SERVICE.ordinal());
        } catch (Exception e) {
            System.out.println("handle result failure"+e);
            LOGGER.error("handle result failure", e);
            response.setException(e);
            response.setSerialize(request.getSerialize());
            response.setReturnType(ReturnEnum.SERVER_EXCEPTION.ordinal());
        }
        // 写入 RPC 响应对象并自动关闭连接
        channel.write(response);
        return null;
    }

    private Object handle(InvokeRequest request) throws Exception {
        String serviceName = request.getServiceName();
        String serviceVersion = request.getServiceVersion();
        if (StringUtil.isNotEmpty(serviceVersion)) {
            serviceName += "-" + serviceVersion;
        }
        //Object serviceBean = SpringUtil.fetchSpringBean(this.providerConfig);
        Object serviceBean=this.providerConfig.getService();
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
