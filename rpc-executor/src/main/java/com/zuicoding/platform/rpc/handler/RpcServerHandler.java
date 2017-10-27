package com.zuicoding.platform.rpc.handler;

import com.zuicoding.platform.rpc.common.RpcMessage;
import com.zuicoding.platform.rpc.provider.ProviderInvoker;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : Created by <a href="mailto:stephen.linicoding@gmail.com">Stephen.lin</a> on 2017/10/25
 * @description: <p></p>
 */
@ChannelHandler.Sharable
public class RpcServerHandler extends ChannelInboundHandlerAdapter {

    private Logger logger = LoggerFactory.getLogger(RpcServerHandler.class);
    Map<String,ProviderInvoker> refMap = new ConcurrentHashMap<>();


    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (!(msg instanceof RpcMessage)){
            return;
        }
        RpcMessage message = (RpcMessage)msg;
        ProviderInvoker invoker = refMap.get(message.getInterfaceClass());
        if (invoker == null){
            return;
        }

        Object result = invoker.invoke(message);
        message.setResult(result);
        ctx.writeAndFlush(message);

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("",cause);
        ctx.close();
    }
}
