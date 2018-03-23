
package com.zuicoding.platform.rpc.executor.client;

import com.zuicoding.platform.rpc.executor.RpcRequest;
import com.zuicoding.platform.rpc.executor.RpcResponse;

/**
 * Created by <a href="mailto:stephen.linicoding@gmail.com">Stephen.lin</a> on 2018/3/23.
 * <p>
 * <p>
 * </p>
 */
public interface Client {

    RpcResponse send(RpcRequest request);
}
