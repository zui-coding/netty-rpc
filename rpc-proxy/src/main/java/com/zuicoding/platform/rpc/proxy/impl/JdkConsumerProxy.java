package com.zuicoding.platform.rpc.proxy.impl;

import com.zuicoding.platform.rpc.common.RpcCaller;
import com.zuicoding.platform.rpc.proxy.RpcConsumerProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by Stephen.lin on 2017/9/21.
 * <p>
 * Description :<p></p>
 */
public class JdkConsumerProxy extends RpcConsumerProxy implements InvocationHandler {

    private Object target;

    public Object bind(Object target){
        this.target = target;
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return null;
    }

    @Override
    public void buildCaller(RpcCaller caller) {

    }
}
