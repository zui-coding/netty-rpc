package com.zuicoding.platform.rpc;

import com.zuicoding.platform.rpc.common.RpcRequest;
import com.zuicoding.platform.rpc.common.RpcResponse;

/**
 * @author: by Stephen.lin on 2017/9/25.
 * <p>
 * Description :<p></p>
 */
public interface RpcInvoker {

    RpcResponse invoke(RpcRequest request);


}
