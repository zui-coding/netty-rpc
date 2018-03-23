
package com.zuicoding.platform.rpc.executor.client;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import com.zuicoding.platform.rpc.protocol.URL;

/**
 * Created by <a href="mailto:stephen.linicoding@gmail.com">stephen.lin</a> on 2018/3/23.
 * <p>
 * <p>
 * </p>
 */
public class ClientHandler implements InvocationHandler {

    private URL url;

    public ClientHandler(URL url) {
        this.url = url;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {


        return null;
    }
}
