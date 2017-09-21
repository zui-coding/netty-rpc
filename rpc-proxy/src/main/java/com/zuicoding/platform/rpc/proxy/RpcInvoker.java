package com.zuicoding.platform.rpc.proxy;

import com.zuicoding.platform.rpc.common.RpcCaller;

/**
 * Created by Stepen.lin on 2017/9/21.
 * <p>
 * Description :<p></p>
 */
public interface RpcInvoker {

    Object invoke(RpcCaller caller);
}
