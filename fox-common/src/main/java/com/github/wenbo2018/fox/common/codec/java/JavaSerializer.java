package com.github.wenbo2018.fox.common.codec.java;

import com.github.wenbo2018.fox.common.codec.AbstractSerializer;
import com.github.wenbo2018.fox.common.common.FoxConstants;

import java.io.*;

/**
 * Created by shenwenbo on 2016/9/30.
 */
public class JavaSerializer extends AbstractSerializer {


    private byte serializerType= FoxConstants.JAVA_DEFAULT_SERIALIEE_byte;

    @Override
    public <T> byte[] serialize(T obj) {
        if(obj==null) throw new NullPointerException();
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ObjectOutputStream out = new ObjectOutputStream(os);
            out.writeObject(obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return os.toByteArray();
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> cls) {
        if(data==null) throw new NullPointerException();

        ByteArrayInputStream is = new ByteArrayInputStream(data);
        ObjectInputStream in = null;
        T obj=null;
        try {
            in = new ObjectInputStream(is);
            obj=(T)in.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return obj;
    }

    @Override
    public byte getSerializerType() {
        return this.serializerType;
    }
}
