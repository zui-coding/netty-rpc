package com.zuicoding.platform.rpc.consumer;

import com.zuicoding.platform.rpc.RpcInvoker;
import com.zuicoding.platform.rpc.common.RpcRequest;
import com.zuicoding.platform.rpc.consumer.impl.DefaultClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author Created by Stephen.lin on 2017/9/25.
 * <p>
 * Description :<p></p>
 */
public class JdkRpcInvoker<T>
        implements RpcInvoker<T>,InvocationHandler {

    private Logger logger = LoggerFactory.getLogger(JdkRpcInvoker.class);


    private Class<T> clazz;

    private Client client;



    public JdkRpcInvoker(Class<T> clazz,Client client){
        this.clazz = clazz;
        this.client = client;
    }


    @Override
    public T  invoke(RpcRequest caller) {

        return (T) Proxy.newProxyInstance(clazz.getClassLoader(),
                new Class<?>[]{clazz},this);
    }

    @Override
    public final Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //toString方法
        if ("toString".equals(method.getName()) &&
                (args == null || args.length == 0)){
            return client.toString();

        }
        //equals方法
        if ("equals".equals(method.getName())&&
                (args != null && args.length == 1)){
            return true;
        }

        RpcRequest request = new RpcRequest(clazz.getName(),method.getName(),args);

        client.send(request);
        return null;
    }

}
