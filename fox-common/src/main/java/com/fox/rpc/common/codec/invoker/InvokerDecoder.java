package com.fox.rpc.common.codec.invoker;

import com.fox.rpc.common.codec.Serializer;
import com.fox.rpc.common.codec.SerializerFactory;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Created by shenwenbo on 2016/10/2.
 */
public class InvokerDecoder extends ByteToMessageDecoder {

    private Class<?> genericClass;

    public InvokerDecoder(Class<?> genericClass) {
        this.genericClass = genericClass;
    }

    @Override
    public void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        //获取序列化方式
        if (in.readableBytes() < 5)
            return;
        in.markReaderIndex();
        byte b = in.readByte();
//        //获取消息长度
//        if (in.readableBytes() < 4) {
//            return;
//        }
//        in.markReaderIndex();
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
}
