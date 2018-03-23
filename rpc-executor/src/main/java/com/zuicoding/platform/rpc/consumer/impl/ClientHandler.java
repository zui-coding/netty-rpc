
package com.zuicoding.platform.rpc.consumer.impl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.UUID;

import com.zuicoding.platform.rpc.common.RpcRequest;
import com.zuicoding.platform.rpc.common.RpcResponse;
import com.zuicoding.platform.rpc.consumer.Client;

/**
 * Created by <a href="mailto:stephen.linicoding">Stephen.lin</a> on 2018/3/22.
 * <p>
 * <p>
 * </p>
 */
public class ClientHandler implements InvocationHandler {


    private Class interfaceClass;
    private Client client;

    public ClientHandler(Class interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        //toString方法
        if ("toString".equals(method.getName()) &&
                (args == null || args.length == 0)) {
            return interfaceClass.toString();

        }
        //equals方法
        if ("equals".equals(method.getName()) &&
                (args != null && args.length == 1)) {

            return interfaceClass.equals(proxy.getClass());
        }

        RpcRequest request = new RpcRequest(this.interfaceClass.getName(), method.getName(), args);
        request.setRequestId(UUID.randomUUID().toString());
        RpcResponse response = client.invoke(request);
        return response.getResult();
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
