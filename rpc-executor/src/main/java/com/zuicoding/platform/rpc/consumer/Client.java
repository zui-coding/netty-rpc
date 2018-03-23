package com.zuicoding.platform.rpc.consumer;

import com.zuicoding.platform.rpc.RpcInvoker;
import com.zuicoding.platform.rpc.common.RpcRequest;
import com.zuicoding.platform.rpc.common.RpcResponse;

/**
 * Created by Stephen.lin on 2017/9/25.
 * <p>
 * Description :<p></p>
 */
public interface Client extends RpcInvoker {

    RpcResponse send(RpcRequest request);
    RpcResponse send(RpcRequest request,boolean async);
}
