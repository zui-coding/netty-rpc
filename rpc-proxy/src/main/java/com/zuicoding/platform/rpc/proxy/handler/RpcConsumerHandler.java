package com.zuicoding.platform.rpc.proxy.handler;

import com.zuicoding.platform.rpc.common.RpcCaller;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

/**
 * Created by Stephen.lin on 2017/9/21.
 * <p>
 * Description :<p></p>
 */
@ChannelHandler.Sharable
public class RpcConsumerHandler extends ChannelInboundHandlerAdapter {

    private Logger logger = LoggerFactory.getLogger(RpcConsumerHandler.class);


    private ChannelHandlerContext context;

    private RpcCaller caller;

    public RpcCaller getCaller() {
        return caller;
    }

    public void setCaller(RpcCaller caller) {
        this.caller = caller;
    }

    public RpcConsumerHandler() {
    }

    public RpcConsumerHandler(RpcCaller caller) {
        this.caller = caller;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        logger.info("---------channelActive -----------");
        ctx.writeAndFlush(caller);
    }


    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
        this.context = ctx;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
        ctx.close();
        logger.info("---------channelRead--------");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
        logger.info("---------channelReadComplete--------");
        ctx.flush().close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        logger.error("",cause);
        ctx.close();
    }

    public void close(){
        context.writeAndFlush(Unpooled.EMPTY_BUFFER)
                .addListener(ChannelFutureListener.CLOSE);
    }

    public void invoke(RpcCaller caller){
        final CountDownLatch latch = new CountDownLatch(1);
        this.context.writeAndFlush(caller).addListener(new ChannelFutureListener() {
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
