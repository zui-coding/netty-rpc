package com.zuicoding.platform.rpc.handler.impl;

import com.zuicoding.platform.rpc.common.RpcCaller;
import com.zuicoding.platform.rpc.handler.RpcHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * Created by Stephen.lin on 2017/9/25.
 * <p>
 * Description :<p></p>
 */
public class DefaultRpcHandlerImpl extends ChannelInboundHandlerAdapter implements RpcHandler {

    private Logger logger = LoggerFactory.getLogger(DefaultRpcHandlerImpl.class);



    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
        if(msg instanceof RpcCaller){
            RpcCaller caller = (RpcCaller)msg;
            String klass = caller.getKlass();
            Class clazz = Class.forName(klass);
            Object instance =clazz.newInstance();
            Method method= clazz.getMethod(caller.getMethod(),clazz);
            method.invoke(instance,caller.getParams());
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}