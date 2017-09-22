package com.zuicoding.platform.rpc.proxy.handler;

import com.zuicoding.platform.rpc.common.RpcCaller;
import com.zuicoding.platform.rpc.proxy.RpcInvoker;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * Created by Stephen.lin on 2017/9/21.
 * <p>
 * Description :<p></p>
 */
@ChannelHandler.Sharable
public class RpcProviderHandler extends ChannelInboundHandlerAdapter implements RpcInvoker {

    private Logger logger = LoggerFactory.getLogger(RpcProviderHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info("------channelRead--------");
        super.channelRead(ctx, msg);
        if (msg instanceof RpcCaller){
            RpcCaller caller = (RpcCaller) msg;
            String klass = caller.getKlass();
            Class clazz = Class.forName(klass);
            Object instance =clazz.newInstance();
            Method method= clazz.getMethod(caller.getMethod(),clazz);
            method.invoke(instance,caller.getParams());
        }
        ctx.writeAndFlush(msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
        logger.info("------channelReadComplete--------");
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        logger.error("",cause);
        ctx.close();
    }

    @Override
    public Object invoke(RpcCaller caller) {
        return null;
    }
}
