package com.zuicoding.platform.rpc.protocol.serialization.impl;

import com.zuicoding.platform.rpc.common.RpcCaller;
import com.zuicoding.platform.rpc.protocol.serialization.ISerializationer;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

/**
 * Created by Stephen.lin on 2017/9/21.
 * <p>
 * Description :<p>default use jdk</p>
 */
public class JdkSerializationer implements ISerializationer<RpcCaller> {



    @Override
    public <T> byte[] serialize(T object) {
        return null;
    }

    @Override
    public RpcCaller deserialize(byte[] data, Class<RpcCaller> clazz) {
        return null;
    }
}
