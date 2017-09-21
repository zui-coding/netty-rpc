package com.zuicoding.platform.rpc.proxy.impl;

import com.zuicoding.platform.rpc.common.RpcCaller;
import com.zuicoding.platform.rpc.proxy.RpcInvoker;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

/**
 * Created by Stephen.lin on 2017/9/21.
 * <p>
 * Description :<p></p>
 */
public class RpcConsumerInoker implements RpcInvoker {

    private String host = "127.0.0.1";
    private int port = 2017;
    private EventLoopGroup workerGroup ;
    public RpcConsumerInoker() {
    }

    public RpcConsumerInoker(String host, int port) {
        this.host = host;
        this.port = port;
    }

    private void init(){
        workerGroup  = new NioEventLoopGroup();
    }

    @Override
    public Object invoke(RpcCaller caller) {
        return null;
    }
}
