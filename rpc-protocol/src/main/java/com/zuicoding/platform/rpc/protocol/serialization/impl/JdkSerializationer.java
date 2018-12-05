package com.zuicoding.platform.rpc.protocol.serialization.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.zuicoding.platform.rpc.common.Response;
import com.zuicoding.platform.rpc.protocol.serialization.Serializationer;

/**
 * Created by Stephen.lin on 2017/9/21.
 * <p>
 * Description :<p>default use jdk</p>
 */
public class JdkSerializationer implements Serializationer {

    @Override
    public byte[] serialize(Object object) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(object);
        oos.flush();
        baos.flush();
        oos.close();
        baos.close();
        return baos.toByteArray();
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> clazz) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ObjectInputStream ois = new ObjectInputStream(bais);
        Object o = ois.readObject();
        ois.close();
        bais.close();

        return (T)o;
    }
}
