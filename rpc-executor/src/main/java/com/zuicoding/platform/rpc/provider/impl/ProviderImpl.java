package com.zuicoding.platform.rpc.provider.impl;

import com.zuicoding.platform.rpc.common.RpcCaller;
import com.zuicoding.platform.rpc.common.RpcState;
import com.zuicoding.platform.rpc.common.exception.RpcException;
import com.zuicoding.platform.rpc.provider.Provider;
import com.zuicoding.platform.rpc.registry.Register;
import com.zuicoding.platform.rpc.registry.RpcRegistry;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Stephen.lin on 2017/9/25.
 * <p>
 * Description :<p></p>
 */
public class ProviderImpl implements Provider {

    private RpcRegistry registry;

    private String interfaces;
    private Object ref;

    public ProviderImpl() {
    }

    public ProviderImpl(String interfaces, Object ref) {
        this.interfaces = interfaces;
        this.ref = ref;
    }


    public void provide(Register register) {
        if (registry != null){
            registry.registry(register);
        }
    }

    public Register getRegister(String iinterface){

        return null;
    }

    @Override
    public RpcCaller invoke(RpcCaller caller) {
        try {
            Class clazz = Class.forName(interfaces);
            Class[] argClasses = null;
            if (caller.getParams() != null){
                Object[] params = caller.getParams();
                int len = params.length;
                argClasses = new  Class[len];
                for (int i = 0; i < len; i++) {
                    argClasses[i] = params[i].getClass();
                }
            }
            Method method= clazz.getMethod(caller.getMethod(),argClasses);
           Object result = method.invoke(ref,caller.getParams());
           caller.setResult(result);
           caller.setState(RpcState.RECIVE);
           return caller;
        }catch (Exception e){
            throw new RpcException(e);
        }
    }

    public RpcRegistry getRegistry() {
        return registry;
    }

    public void setRegistry(RpcRegistry registry) {
        this.registry = registry;
    }

    public String getInterfaces() {
        return interfaces;
    }

    public void setInterfaces(String interfaces) {
        this.interfaces = interfaces;
    }

    public Object getRef() {
        return ref;
    }

    public void setRef(Object ref) {
        this.ref = ref;
    }
}
