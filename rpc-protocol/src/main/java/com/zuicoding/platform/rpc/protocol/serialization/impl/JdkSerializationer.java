package com.zuicoding.platform.rpc.protocol.serialization.impl;

import com.zuicoding.platform.rpc.common.RpcRequest;
import com.zuicoding.platform.rpc.protocol.serialization.ISerializationer;

/**
 * Created by Stephen.lin on 2017/9/21.
 * <p>
 * Description :<p>default use jdk</p>
 */
public class JdkSerializationer implements ISerializationer<RpcRequest> {



    @Override
    public <T> byte[] serialize(T object) {
        return null;
    }

    @Override
    public RpcRequest deserialize(byte[] data, Class<RpcRequest> clazz) {
        return null;
    }
}
