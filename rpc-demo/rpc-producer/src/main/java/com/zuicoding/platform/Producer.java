package com.zuicoding.platform;

import com.zuicoding.platform.rpc.protocol.URL;
import com.zuicoding.platform.rpc.registry.RpcRegistry;
import com.zuicoding.platform.rpc.registry.impl.MockLocalRpcRegistry;
import com.zuicoding.platform.rpc.server.RpcServer;
import com.zuicoding.platform.rpc.server.impl.DefaultNettyServer;

/**
 * Hello world!
 *
 */
public class Producer
{
    public static void main( String[] args ) {

//        Provider provider = new Provider();
//        provider.setInterfaceClass("com.zuicoding.platform.demo.api.IDemoService");
//        provider.setRef(new DemoServiceImpl());
//        RpcInvoker invoker = new ProviderInvoker(provider);
        URL url = new URL();
        url.setHost("localhost")
                .setPort(9999)
                .addParam("interface","com.zuicoding.platform.demo.api.IDemoService");

        RpcRegistry registry = new MockLocalRpcRegistry();
        RpcServer server = new DefaultNettyServer(url,registry);
        server.start();


    }
}
