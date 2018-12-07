
package com.zuicoding.platform.rpc.executor.client;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zuicoding.platform.rpc.common.DefaultRequest;
import com.zuicoding.platform.rpc.common.Request;
import com.zuicoding.platform.rpc.common.Response;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by <a href="mailto:stephen.linicoding@gmail.com">stephen.lin</a> on 2018/3/23.
 * <p>
 * <p>
 * </p>
 */
public class ConsumerHandler extends SimpleChannelInboundHandler<Response> {


    private static Logger logger = LoggerFactory.getLogger(ConsumerHandler.class);

    private Channel channel;


    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
        this.channel = ctx.channel();
        logger.info("client channel registered...");
    }

    // 读取 返回回来的response
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Response msg) throws Exception {

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        logger.error("", cause);
        ctx.close();
    }
}
