package com.zuicoding.platform;

import com.zuicoding.platform.demo.api.IDemoService;
import com.zuicoding.platform.rpc.RpcInvoker;
import com.zuicoding.platform.rpc.consumer.JdkRpcInvoker;
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
        RpcInvoker<IDemoService> proxy = new JdkRpcInvoker<>();
        IDemoService service = proxy.bind(IDemoService.class);
        logger.info(" result value : {}",service.sayHell("张三"));
    }

}
