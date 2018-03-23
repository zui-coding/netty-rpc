package com.zuicoding.platform.rpc.handler;

import java.util.concurrent.ThreadPoolExecutor;

import com.zuicoding.platform.rpc.consumer.Client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author : Created by <a href="mailto:stephen.linicoding@gmail.com">Stephen.lin</a> on 2017/10/25
 * @description: <p></p>
 */
public class RpcClientHandler extends ChannelInboundHandlerAdapter {


    private ThreadPoolExecutor threadPoolExecutor;

    private Client client;

    public RpcClientHandler( Client client,ThreadPoolExecutor threadPoolExecutor) {
        this.threadPoolExecutor = threadPoolExecutor;
        this.client = client;
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }




}
