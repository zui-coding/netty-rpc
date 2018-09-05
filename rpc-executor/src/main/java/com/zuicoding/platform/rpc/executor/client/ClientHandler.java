
package com.zuicoding.platform.rpc.executor.client;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zuicoding.platform.rpc.common.DefaultRequest;
import com.zuicoding.platform.rpc.common.Request;
import com.zuicoding.platform.rpc.common.Response;
import com.zuicoding.platform.rpc.protocol.URL;

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
public class ClientHandler extends SimpleChannelInboundHandler<Response> implements InvocationHandler {


    private static Logger logger = LoggerFactory.getLogger(ClientHandler.class);

    private Map<String, Response> responseMap = new HashMap<>();

    private Channel channel;

    private URL url;

    public ClientHandler(URL url) {
        logger.info("this ==> {}", this);
        this.url = url;
    }


    public Object createProxy() throws ClassNotFoundException {

        return Proxy.newProxyInstance(this.getClass().getClassLoader(),
                new Class[] {Class.forName(url.getPath())}, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        if ("toString".equals(method.getName())) {
            return "";
        }
        if ("equals".equals(method.getName())) {
            return true;
        }

        Request request = new DefaultRequest();
        request.setRequestId(UUID.randomUUID().toString())
                .setArguments(args);

        if (args != null && args.length > 0) {

            Class[] paramClasses = Arrays.stream(args)
                    .map(c -> c.getClass()).toArray(Class[]::new);
            request.setParameterClasses(paramClasses);
        }
        request.setInterfaceName(url.getPath());

        channel.writeAndFlush(request)
                .addListener(ChannelFutureListener.CLOSE)
                .addListener(new ChannelFutureListener() {
                    @Override
                    public void operationComplete(ChannelFuture future) throws Exception {
                        logger.info("====");
                    }
                });

        return null;
    }



    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
        this.channel = ctx.channel();
        logger.info("client channel registered...");
    }

    // 读取 返回回来的response
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Response msg) throws Exception {
        responseMap.put(msg.getId(), msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        logger.error("", cause);
        ctx.close();
    }
}
