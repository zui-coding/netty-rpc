package com.zuicoding.platform;

import com.zuicoding.platform.demo.api.IDemoService;
import com.zuicoding.platform.rpc.proxy.impl.JdkConsumerProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hello world!
 *
 */
public class Consumer
{
    private static Logger logger = LoggerFactory.getLogger(Consumer.class);
    public static void main( String[] args ) throws NoSuchMethodException {
        JdkConsumerProxy<IDemoService> proxy = new JdkConsumerProxy();
        IDemoService service = proxy.bind(IDemoService.class);
        logger.info(" result value : {}",service.sayHell("张三"));
    }

}
