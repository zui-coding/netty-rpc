
package com.zuicoding.platform.rpc.cluster;

import com.zuicoding.platform.rpc.common.RPCResult;
import com.zuicoding.platform.rpc.executor.server.RpcInvocation;

/**
 * Created by <a href="mailto:stephen.linicoding@gmail.com">Stephen.lin</a> on 2018-12-07.
 * <p>
 *
 * </p>
 */
public interface Invoker<T> {

    Class<T> getInterface();

    RPCResult invoke(RpcInvocation invocation) throws Exception;
}
