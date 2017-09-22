package com.zuicoding.platform;

import com.zuicoding.platform.demo.api.IDemoService;
import com.zuicoding.platform.rpc.proxy.impl.JdkConsumerProxy;

/**
 * Hello world!
 *
 */
public class Consumer
{
    public static void main( String[] args )
    {
        JdkConsumerProxy<IDemoService> proxy = new JdkConsumerProxy();
        IDemoService service = proxy.bind(IDemoService.class);
        service.sayHell("张三");
    }
}
