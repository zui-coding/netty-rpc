package com.zuicoding.platform;

import com.sun.xml.internal.ws.spi.ProviderImpl;
import com.zuicoding.platform.demo.impl.DemoServiceImpl;
import com.zuicoding.platform.rpc.RpcInvoker;
import com.zuicoding.platform.rpc.common.Provider;
import com.zuicoding.platform.rpc.provider.ProviderInvoker;
import com.zuicoding.platform.rpc.server.RpcServer;
import com.zuicoding.platform.rpc.server.impl.DefaultNettyRpcServer;

/**
 * Hello world!
 *
 */
public class Producer
{
    public static void main( String[] args ) {

        Provider provider = new Provider();
        provider.setInterfaceClass("com.zuicoding.platform.demo.api.IDemoService");
        provider.setRef(new DemoServiceImpl());
        RpcInvoker invoker = new ProviderInvoker(provider);

        RpcServer server = new DefaultNettyRpcServer();
        server.start();


    }
}
