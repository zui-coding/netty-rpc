package com.zuicoding.platform.rpc.provider;

/**
 * Created by Stephen.lin on 2017/9/25.
 * <p>
 * Description :<p>服务器接口</p>
 */
public interface RpcServer {

    void  start();
    void stop();
    void addProvider(Provider provider);
}
