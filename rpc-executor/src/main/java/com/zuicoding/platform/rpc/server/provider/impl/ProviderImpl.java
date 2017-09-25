package com.zuicoding.platform.rpc.server.provider.impl;

import com.zuicoding.platform.rpc.registry.Register;
import com.zuicoding.platform.rpc.registry.RpcRegistry;
import com.zuicoding.platform.rpc.server.provider.Provider;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Stephen.lin on 2017/9/25.
 * <p>
 * Description :<p></p>
 */
public class ProviderImpl implements Provider {

    private Map<String,Register> localMap = new ConcurrentHashMap<>();

    private RpcRegistry registry;


    @Override
    public void provide(Register register) {
        if (registry != null){
            registry.registry(register);
        }
        localMap.put(register.getIinterface(),register);
    }

    public Register getRegister(String iinterface){

        return localMap.get(iinterface);
    }

    public RpcRegistry getRegistry() {
        return registry;
    }

    public void setRegistry(RpcRegistry registry) {
        this.registry = registry;
    }
}
