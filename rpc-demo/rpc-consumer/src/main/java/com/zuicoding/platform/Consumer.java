package com.zuicoding.platform;

import com.zuicoding.platform.demo.api.IDemoService;
import com.zuicoding.platform.rpc.proxy.impl.JdkConsumerProxy;

/**
 * Hello world!
 *
 */
public class Consumer
{
    public void sayHello(){

    }
    public static void main( String[] args ) throws NoSuchMethodException {
        JdkConsumerProxy<IDemoService> proxy = new JdkConsumerProxy();
        IDemoService service = proxy.bind(IDemoService.class);
        service.sayHell("张三");
    }

}
