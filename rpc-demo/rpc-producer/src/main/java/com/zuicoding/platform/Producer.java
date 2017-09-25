package com.zuicoding.platform;

import com.zuicoding.platform.demo.impl.DemoServiceImpl;
import com.zuicoding.platform.rpc.registry.Register;
import com.zuicoding.platform.rpc.provider.Provider;
import com.zuicoding.platform.rpc.provider.impl.DefaultRpcExecutor;
import com.zuicoding.platform.rpc.provider.impl.ProviderImpl;

/**
 * Hello world!
 *
 */
public class Producer
{
    public static void main( String[] args ) {

        Provider provider = new ProviderImpl();
        Register register = new Register();
        register.setIinterface("com.zuicoding.platform.demo.api.IDemoService");
        register.setMethod("sayHell");
        register.setPort(2017);
        register.setRef(new DemoServiceImpl());
        register.setServer("localhost");
        provider.provide(register);
        DefaultRpcExecutor executor = new DefaultRpcExecutor();
        executor.setProvider(provider);
        executor.start();

    }
}
