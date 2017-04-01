package com.fox.rpc.common.codec;

import com.fox.rpc.common.codec.hessian.HessianSerializer;
import com.fox.rpc.common.codec.java.JavaSerializer;
import com.fox.rpc.common.codec.protostuff.ProtostuffSerializer;
import com.fox.rpc.common.common.FoxConstants;

import java.security.InvalidParameterException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by shenwenbo on 2016/9/30.
 */
public class SerializerFactory {

    private final static ConcurrentHashMap<String, Serializer> serializers = new ConcurrentHashMap<String, Serializer>();

    private final static ConcurrentHashMap<Byte, Serializer> serializersType = new ConcurrentHashMap<Byte, Serializer>();

    private static volatile boolean isInitialized = false;

    public static void init() {
        if (!isInitialized) {
            synchronized (SerializerFactory.class) {
                if (!isInitialized) {
                    serializers.put(FoxConstants.JAVA_DEFAULT_SERIALIEE,new JavaSerializer());
                    serializersType.put(FoxConstants.JAVA_DEFAULT_SERIALIEE_byte,new JavaSerializer());

                    serializers.put(FoxConstants.HESSIAN_SERIALIEE,new HessianSerializer());
                    serializersType.put(FoxConstants.HESSIAN_SERIALIEE_byte,new HessianSerializer());

                    serializers.put(FoxConstants.PROTOSTUFF_SERIALIEE,new ProtostuffSerializer());
                    serializersType.put(FoxConstants.PROTOSTUFF_SERIALIEE_byte,new ProtostuffSerializer());
                    isInitialized=true;
                }
            }
        }
    }

    public static Serializer getSerializer(String serializerType) {
        Serializer serializer=serializers.get(serializerType);
        if (serializer==null) {
            throw new  InvalidParameterException(" not serializer type is found"+serializerType);
        } else {
            return serializer;
        }
    }

    public static Serializer getSerializer(byte serializerType) {
        Serializer serializer=serializersType.get(serializerType);
        if (serializer==null) {
            throw new  InvalidParameterException(" not serializer type is found"+serializerType);
        } else {
            return serializer;
        }
    }
}
