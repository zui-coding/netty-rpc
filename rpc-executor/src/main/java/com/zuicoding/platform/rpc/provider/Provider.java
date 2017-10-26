package com.zuicoding.platform.rpc.provider;

import com.zuicoding.platform.rpc.common.RpcMessage;

/**
 * Created by Stephen.lin on 2017/9/25.
 * <p>
 * Description :<p></p>
 */
public interface Provider {

    RpcMessage invoke(RpcMessage caller);
    void setRef(Object ref);
    void setInterfaces(String interfaces);
    String getInterfaces();
    Object getRef();
}
