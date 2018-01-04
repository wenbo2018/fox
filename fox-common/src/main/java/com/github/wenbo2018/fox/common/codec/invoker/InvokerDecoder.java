package com.github.wenbo2018.fox.common.codec.invoker;

import com.github.wenbo2018.fox.common.codec.Serializer;
import com.github.wenbo2018.fox.common.codec.SerializerFactory;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Created by shenwenbo on 2016/10/2.
 */
public class InvokerDecoder extends ByteToMessageDecoder {

    private Class<?> genericClass;

    @Override
    public void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < 5) {
            return;
        }
        in.markReaderIndex();
        byte b = in.readByte();
        int dataLength = in.readInt();
        if (in.readableBytes() < dataLength) {
            in.resetReaderIndex();
            return;
        }
        byte[] data = new byte[dataLength];
        in.readBytes(data);
        Serializer serializer = SerializerFactory.getSerializer(b);
        out.add(serializer.deserialize(data, genericClass));
    }

    public InvokerDecoder(Class<?> genericClass) {
        this.genericClass = genericClass;
    }


}
