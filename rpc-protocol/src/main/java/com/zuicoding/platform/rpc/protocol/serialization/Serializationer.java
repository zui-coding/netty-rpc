package com.zuicoding.platform.rpc.protocol.serialization;

import java.io.IOException;

import com.zuicoding.platform.rpc.common.Response;

/**
 * Created by Stephen.lin on 2017/9/21.
 * <p>
 * Description :<p>序列化接口</p>
 */
public interface Serializationer {

    byte[] serialize(Object object) throws IOException;

    <T> T deserialize(byte[] data, Class<T> clazz) throws IOException, ClassNotFoundException;

}
