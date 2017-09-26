package com.zuicoding.platform.rpc.consumer;

import com.zuicoding.platform.rpc.RpcInvoker;
import com.zuicoding.platform.rpc.common.RpcCaller;
import com.zuicoding.platform.rpc.consumer.impl.DefaultClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by Stephen.lin on 2017/9/25.
 * <p>
 * Description :<p></p>
 */
public class JdkRpcInvoker<T>
        implements RpcInvoker<T>,InvocationHandler {

    private Logger logger = LoggerFactory.getLogger(JdkRpcInvoker.class);

    private Client client = new DefaultClient();
    private Class<T> clazz;

    public T bind(Class<T> clazz){
        this.clazz = clazz;

        return (T) Proxy.newProxyInstance(clazz.getClassLoader(),
                new Class<?>[]{clazz},this);
    }
    @Override
    public T  invoke(RpcCaller caller) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        client.connect();
        if ("toString".equals(method.getName()) && (args == null || args.length == 0)) return null;
        RpcCaller caller = new RpcCaller(clazz.getName(),method.getName(),args);
        client.send(caller);
        return caller.getResult();
    }
}
