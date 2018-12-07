
package com.zuicoding.platform.rpc.executor.server;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zuicoding.platform.rpc.common.DefaultRpcThreadFactory;
import com.zuicoding.platform.rpc.common.Request;
import com.zuicoding.platform.rpc.common.Response;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.collection.LongObjectHashMap;
import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.FastThreadLocal;
import io.netty.util.concurrent.Promise;

/**
 * Created by <a href="mailto:stephen.linicoding@gmail.com">stephen.lin</a> on 2018/3/23.
 *
 * <p>
 *     provider server handler
 * </p>
 */
public class ProviderServerHandler extends SimpleChannelInboundHandler<Request> {

    private Logger logger = LoggerFactory.getLogger(ProviderServerHandler.class);

    private static FastThreadLocal<LongObjectHashMap<Promise<Response>>> promisHolder = new FastThreadLocal<LongObjectHashMap<Promise<Response>>>() {

        @Override
        protected LongObjectHashMap<Promise<Response>> initialValue() throws Exception {
            return new LongObjectHashMap<Promise<Response>>();
        }
    };

    private Executor bizExecutor;

    public ProviderServerHandler() {

        bizExecutor = Executors.newCachedThreadPool(new DefaultRpcThreadFactory());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, final Request request) throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("request info : {}",request);
        }
        Promise<Response> promise = new DefaultPromise<>(ctx.executor());
        promise.addListener(future -> {
            Response response = (Response) future.get();
            ctx.writeAndFlush(response);
        });

        promisHolder.get().put(request.getRequestId(), promise);

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        if (logger.isDebugEnabled()) {
            logger.debug("client channel {} connection", ctx.channel());
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        logger.error("server exception",cause);
        ctx.close();
    }

    private void handlerMessage(Channel channel, Request request) {

    }
}
