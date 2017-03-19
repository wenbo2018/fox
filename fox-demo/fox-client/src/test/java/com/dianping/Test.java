package com.dianping;

import com.fox.dto.User;
import com.fox.rpc.common.codec.Serializer;
import com.fox.rpc.common.codec.hessian.HessianSerializer;

import java.util.Arrays;

/**
 * Created by shenwenbo on 2016/10/2.
 */
public class Test {
    public static void main(String[] args) {
        Serializer serializer=new HessianSerializer();
        User user=new User();
        user.setUsername("d");
        user.setPassword("123");
        byte[] data = serializer.serialize(user);
        System.out.println(Arrays.toString(data));
        System.out.println(serializer.deserialize(data,User.class).toString());
    }
}
