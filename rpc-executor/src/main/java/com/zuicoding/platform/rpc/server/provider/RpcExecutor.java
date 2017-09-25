package com.zuicoding.platform.rpc.server.provider;

import com.zuicoding.platform.rpc.common.RpcCaller;

/**
 * Created by Stephen.lin on 2017/9/25.
 * <p>
 * Description :<p></p>
 */
public interface RpcExecutor {

    RpcCaller execute(RpcCaller caller);

    void setProvider(Provider provider);
}
