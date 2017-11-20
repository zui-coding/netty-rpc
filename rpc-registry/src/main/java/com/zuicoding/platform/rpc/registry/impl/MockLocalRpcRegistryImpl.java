package com.zuicoding.platform.rpc.registry.impl;

import com.zuicoding.platform.rpc.common.Provider;
import com.zuicoding.platform.rpc.common.utils.RpcUtils;
import com.zuicoding.platform.rpc.protocol.URL;
import com.zuicoding.platform.rpc.protocol.URLUtils;
import com.zuicoding.platform.rpc.registry.RpcRegistry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Stephen.lin on 2017/9/25.
 * <p>
 * Description :<p>模拟一个本地注册中心</p>
 */
public class MockLocalRpcRegistryImpl implements RpcRegistry {

    Map<String,Provider> map = new ConcurrentHashMap<>();

    @Override
    public void registe(Provider provider) {

        map.put(RpcUtils.regsteKey(provider),provider);
    }

    @Override
    public Provider getProvider(String key) {
        return map.get(key);
    }


}
