package com.zuicoding.platform.rpc.proxy.impl;

import com.zuicoding.platform.rpc.common.RpcCaller;
import com.zuicoding.platform.rpc.proxy.RpcInvoker;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by Stephen.lin on 2017/9/21.
 * <p>
 * Description :<p></p>
 */
public class JdkConsumerProxy<T>  implements InvocationHandler {

    private Class<T> clazz;

    private RpcInvoker invoker;

    public T bind(Class<T> clazz){
        this.clazz = clazz;

        return (T) Proxy.newProxyInstance(clazz.getClassLoader(),
                new Class<?>[]{clazz},this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if ("toString".equals(method.getName()) && (args == null || args.length == 0)) return null;
        RpcCaller caller = new RpcCaller(clazz.getName(),method.getName(),args);
        invoker = new RpcConsumerInoker(caller);
        return null;
    }

}
