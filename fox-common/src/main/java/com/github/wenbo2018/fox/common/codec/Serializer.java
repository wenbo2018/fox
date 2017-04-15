package com.github.wenbo2018.fox.common.codec;

/**
 * Created by shenwenbo on 2016/9/30.
 */
public interface Serializer {

    <T> byte[] serialize(T obj);

    <T> T deserialize(byte[] data, Class<T> cls);

    byte getSerializerType();

}
