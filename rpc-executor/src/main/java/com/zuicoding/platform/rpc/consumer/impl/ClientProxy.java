
package com.zuicoding.platform.rpc.consumer.impl;

import java.lang.reflect.Proxy;

/**
 * Created by <a href="mailto:stephen.linicoding@gmail.com">Stephen.lin</a> on 2018/3/22.
 * <p>
 * <p>
 * </p>
 */
public class ClientProxy {


    public static <T> T create(Class<T> interfaceClass) {

        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(),
                new Class[]{interfaceClass},new ClientHandler(interfaceClass));
    }
}
