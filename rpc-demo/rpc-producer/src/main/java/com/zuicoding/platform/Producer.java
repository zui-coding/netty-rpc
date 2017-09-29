package com.zuicoding.platform;

import com.zuicoding.platform.demo.impl.DemoServiceImpl;
import com.zuicoding.platform.rpc.server.RpcServer;
import com.zuicoding.platform.rpc.server.impl.DefaultNettyRpcServer;
import com.zuicoding.platform.rpc.provider.Provider;
import com.zuicoding.platform.rpc.provider.impl.ProviderImpl;

/**
 * Hello world!
 *
 */
public class Producer
{
    public static void main( String[] args ) {

        Provider provider = new ProviderImpl();
        provider.setInterfaces("com.zuicoding.platform.demo.api.IDemoService");
        provider.setRef(new DemoServiceImpl());
        RpcServer server = new DefaultNettyRpcServer();
        server.start();
        server.addProvider(provider);

    }
}
