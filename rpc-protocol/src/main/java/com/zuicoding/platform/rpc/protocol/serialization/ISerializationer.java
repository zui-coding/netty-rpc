package com.zuicoding.platform.rpc.protocol.serialization;

import java.io.Serializable;

/**
 * Created by Stephen.lin on 2017/9/21.
 * <p>
 * Description :<p>序列化接口</p>
 */
public interface ISerializationer<T extends Serializable> {

    <T> byte[] serialize(T object);
    T deserialize(byte[] data,Class<T> clazz);

}
