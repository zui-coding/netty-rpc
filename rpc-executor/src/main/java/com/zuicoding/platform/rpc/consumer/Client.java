package com.zuicoding.platform.rpc.consumer;

import com.zuicoding.platform.rpc.common.RpcCaller;

/**
 * Created by Stephen.lin on 2017/9/25.
 * <p>
 * Description :<p></p>
 */
public interface Client {

    void connect();
    void close();
    RpcCaller send(RpcCaller caller);
}