package com.fox.rpc.common.codec.provider;

import com.fox.rpc.common.bean.InvokeResponse;
import com.fox.rpc.common.codec.Serializer;
import com.fox.rpc.common.codec.SerializerFactory;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by shenwenbo on 2016/10/2.
 */
public class ProviderEncoder extends MessageToByteEncoder {

    private Class<?> genericClass;

    public ProviderEncoder(Class<?> genericClass) {
        this.genericClass = genericClass;
    }

    @Override
    public void encode(ChannelHandlerContext ctx, Object in, ByteBuf out) throws Exception {
        if (genericClass.isInstance(in)) {
            InvokeResponse invokeResponse=(InvokeResponse)in;
            Serializer serializer= SerializerFactory.getSerializer(invokeResponse.getSerialize());
            byte[] data = serializer.serialize(in);
            out.writeByte(serializer.getSerializerType());
            out.writeInt(data.length);
            out.writeBytes(data);
        }
    }
}
