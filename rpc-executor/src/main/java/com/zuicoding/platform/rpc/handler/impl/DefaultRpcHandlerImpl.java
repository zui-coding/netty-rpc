package com.zuicoding.platform.rpc.handler.impl;

import com.zuicoding.platform.rpc.common.RpcCaller;
import com.zuicoding.platform.rpc.handler.RpcHandler;
import com.zuicoding.platform.rpc.provider.Provider;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Stephen.lin on 2017/9/25.
 * <p>
 * Description :<p></p>
 */
@ChannelHandler.Sharable
public class DefaultRpcHandlerImpl extends ChannelInboundHandlerAdapter implements RpcHandler {

    private Logger logger = LoggerFactory.getLogger(DefaultRpcHandlerImpl.class);

    private Map<String,Provider> localMap = new ConcurrentHashMap<>();



    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
        if(msg instanceof RpcCaller){
            RpcCaller caller = (RpcCaller)msg;
            Provider provider = localMap.get(caller.getInterfaces());
            provider.invoke(caller);

        }
        ctx.writeAndFlush(msg);
    }



    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        logger.error("",cause);
    }


    @Override
    public void registe(Provider provider) {
        localMap.put(provider.getInterfaces(),provider);
    }
}
