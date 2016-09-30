package com.fox.rpc.common.codec;

import com.fox.rpc.common.codec.hessian.HessianSerializer;
import com.fox.rpc.common.codec.java.JavaSerializer;
import com.fox.rpc.common.codec.protostuff.ProtostuffSerializer;
import com.fox.rpc.common.common.Constants;

import java.security.InvalidParameterException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by shenwenbo on 2016/9/30.
 */
public class SerializerFactory {

    private final static ConcurrentHashMap<String, Serializer> serializers = new ConcurrentHashMap<String, Serializer>();

    private static volatile boolean isInitialized = false;

    static {
        if (!isInitialized) {
             serializers.put(Constants.JAVA_DEFAULT_SERIALIEE,new JavaSerializer());
            serializers.put(Constants.HESSIAN_SERIALIEE,new HessianSerializer());
            serializers.put(Constants.PROTOSTUFF_SERIALIEE,new ProtostuffSerializer());
        }
        isInitialized=true;
    }

    public static Serializer getSerializer(String serializerTypr) {
        Serializer serializer=serializers.get(serializerTypr);
        if (serializer==null) {
            throw new  InvalidParameterException(" not serializer typr is found"+serializerTypr);
        } else {
            return serializer;
        }
    }
}
