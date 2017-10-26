package com.zuicoding.platform.rpc.protocol.serialization.impl;

import com.zuicoding.platform.rpc.common.RpcMessage;
import com.zuicoding.platform.rpc.protocol.serialization.ISerializationer;

/**
 * Created by Stephen.lin on 2017/9/21.
 * <p>
 * Description :<p>default use jdk</p>
 */
public class JdkSerializationer implements ISerializationer<RpcMessage> {



    @Override
    public <T> byte[] serialize(T object) {
        return null;
    }

    @Override
    public RpcMessage deserialize(byte[] data, Class<RpcMessage> clazz) {
        return null;
    }
}
