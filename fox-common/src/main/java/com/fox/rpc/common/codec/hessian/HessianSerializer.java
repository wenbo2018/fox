package com.fox.rpc.common.codec.hessian;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;
import com.fox.rpc.common.codec.AbstractSerializer;
import org.apache.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by shenwenbo on 2016/9/30.
 */
public class HessianSerializer extends  AbstractSerializer {

    private static Logger LOGGER=Logger.getLogger(HessianSerializer.class);

    private String serializerType="hessian";

    @Override
    public <T> byte[] serialize(T obj) {
        if(obj==null) throw new NullPointerException();
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        HessianOutput ho = new HessianOutput(os);
        try {
            ho.writeObject(obj);
        } catch (IOException e) {
            LOGGER.error("HessianSerializer error:"+e);
        }
        return os.toByteArray();
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> cls) {
        if(data==null) throw new NullPointerException();

        ByteArrayInputStream is = new ByteArrayInputStream(data);
        HessianInput hi = new HessianInput(is);
        T object= null;
        try {
            object = (T)hi.readObject();
        } catch (IOException e) {
            LOGGER.error("HessianSerializer error:" + e);
        }
        return object;
    }

    public String getSerializerType() {
        return serializerType;
    }
}
