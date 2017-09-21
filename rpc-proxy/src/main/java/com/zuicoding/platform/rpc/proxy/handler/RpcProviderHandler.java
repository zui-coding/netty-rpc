package com.zuicoding.platform.rpc.proxy.handler;

import com.zuicoding.platform.rpc.common.RpcCaller;
import com.zuicoding.platform.rpc.proxy.RpcInvoker;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.lang.reflect.Method;

/**
 * Created by Stephen.lin on 2017/9/21.
 * <p>
 * Description :<p></p>
 */
public class RpcProviderHandler extends ChannelInboundHandlerAdapter implements RpcInvoker {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
        if (msg instanceof RpcCaller){
            RpcCaller caller = (RpcCaller) msg;
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
    public Object invoke(RpcCaller caller) {
        return null;
    }
}
