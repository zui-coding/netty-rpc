package com.zuicoding.platform.rpc.registry.impl;

import com.zuicoding.platform.rpc.common.Provider;
import com.zuicoding.platform.rpc.registry.RpcRegistry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Stephen.lin on 2017/9/25.
 * <p>
 * Description :<p>模拟一个注册中心</p>
 */
public class MockRpcRegistryImpl implements RpcRegistry {

    Map<String,Provider> map = new ConcurrentHashMap<>();

    @Override
    public void registe(Provider provider) {
        map.put(provider.getInterfaceClass(),provider);
    }


}
