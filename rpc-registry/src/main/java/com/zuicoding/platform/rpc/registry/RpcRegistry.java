package com.zuicoding.platform.rpc.registry;


import com.zuicoding.platform.rpc.common.Provider;

/**
 * Created by Stephen.lin on 2017/9/25.
 * <p>
 * Description :<p>注册中心</p>
 */
public interface RpcRegistry {


    /**
     * 将provider 注册到 注册中心里
     * @param provider
     */
    void registe(Provider provider);
}
