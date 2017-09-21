package com.zuicoding.platform.rpc.proxy.handler;

import com.zuicoding.platform.rpc.common.RpcCaller;
import com.zuicoding.platform.rpc.proxy.RpcInvoker;
import io.netty.channel.*;

import java.util.concurrent.CountDownLatch;

/**
 * Created by Stephen.lin on 2017/9/21.
 * <p>
 * Description :<p></p>
 */
public class RpcConsumerHandler extends ChannelInboundHandlerAdapter {



    private Channel channel;


    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
        this.channel = ctx.channel();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }


    public void invoke(RpcCaller caller){
        final CountDownLatch latch = new CountDownLatch(1);
        this.channel.writeAndFlush(caller).addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                latch.countDown();
            }
        });
        try {
            latch.await();
        }catch (Exception e){
            e.printStackTrace();
        }

    }



}
