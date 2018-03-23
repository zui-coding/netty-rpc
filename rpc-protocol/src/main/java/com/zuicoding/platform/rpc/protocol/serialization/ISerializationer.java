package com.zuicoding.platform.rpc.protocol.serialization;

import java.io.IOException;

/**
 * Created by Stephen.lin on 2017/9/21.
 * <p>
 * Description :<p>序列化接口</p>
 */
public interface ISerializationer {

    byte[] serialize(Object object) throws IOException;
    <T> T deserialize(byte[] data,Class<T> klazz) throws IOException, ClassNotFoundException;

}
