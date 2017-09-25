package com.zuicoding.platform.rpc;

import com.zuicoding.platform.rpc.common.RpcCaller;

/**
 * Created by Stephen.lin on 2017/9/25.
 * <p>
 * Description :<p></p>
 */
public interface RpcInvoker<T> {

    T invoke(RpcCaller caller);

    T bind(Class<T> clazz);

}
