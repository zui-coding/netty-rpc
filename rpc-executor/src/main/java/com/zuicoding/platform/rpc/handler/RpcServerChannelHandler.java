package com.zuicoding.platform.rpc.handler;

import com.zuicoding.platform.rpc.common.RpcRequest;
import com.zuicoding.platform.rpc.protocol.URL;
import com.zuicoding.platform.rpc.protocol.URLUtils;
import com.zuicoding.platform.rpc.provider.ProviderInvoker;
import com.zuicoding.platform.rpc.registry.RpcRegistry;
import com.zuicoding.platform.rpc.server.RpcServer;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author : Created by <a href="mailto:stephen.linicoding@gmail.com">Stephen.lin</a> on 2017/10/25
 * @description: <p></p>
 */
@ChannelHandler.Sharable
public class RpcServerChannelHandler extends ChannelInboundHandlerAdapter {

    private Logger logger = LoggerFactory.getLogger(RpcServerChannelHandler.class);

    private RpcRegistry registry;

    private ThreadPoolExecutor poolExecutor;

    public RpcServerChannelHandler(
                                   ThreadPoolExecutor poolExecutor) {
        this.poolExecutor = poolExecutor;
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx,final Object msg) throws Exception {
        if (!(msg instanceof RpcRequest)){
            return;
        }
        poolExecutor.submit(new Runnable() {
            @Override
            public void run() {
                RpcRequest request = (RpcRequest)msg;
                URL url = URLUtils.format(request);

                //refMap.get(url).invoke(request);
            }
        });


    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("",cause);
        ctx.close();
    }

    public void setRegistry(RpcRegistry registry) {

        this.registry = registry;
    }

    public void refreshLocalRegister(Map<String,ProviderInvoker> map){
        //refMap.clear();
        //refMap.putAll(map);
    }
}
