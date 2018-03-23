package com.zuicoding.platform.rpc.consumer.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zuicoding.platform.rpc.common.RpcRequest;
import com.zuicoding.platform.rpc.common.RpcResponse;
import com.zuicoding.platform.rpc.common.exception.RpcException;
import com.zuicoding.platform.rpc.consumer.AbstractPoolClient;
import com.zuicoding.platform.rpc.consumer.Client;
import com.zuicoding.platform.rpc.handler.RpcClientHandler;
import com.zuicoding.platform.rpc.protocol.URL;
import com.zuicoding.platform.rpc.registry.RpcRegistry;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

/**
 * Created by Stephen.lin on 2017/9/25.
 * <p>
 * Description :<p>默认使用nettyClient</p>
 */
public class DefaultClient extends AbstractPoolClient {

    private Logger logger = LoggerFactory.getLogger(DefaultClient.class);

    private Map<String, Client> clientMap = new ConcurrentHashMap<>();

    private Map<URL, Channel> channelMap = new ConcurrentHashMap<>();

    private EventLoopGroup workerGroup;
    private Bootstrap bootstrap;
    private ChannelFuture channelFuture;
    private ThreadPoolExecutor threadPoolExecutor;
    private Channel channel;
    private RpcRegistry registry;
    private URL url;

    public DefaultClient(URL url) {
        super(url);
        this.url = url;
        workerGroup = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
    }

    @Override
    public PooledObjectFactory<Channel> createPooledObjectFactory() {
        return new BasePooledObjectFactory<Channel>() {
            @Override
            public Channel create() throws Exception {
                return DefaultClient.this.doOpen();
            }

            @Override
            public PooledObject<Channel> wrap(Channel channel) {

                return null;
            }

            @Override
            public void destroyObject(PooledObject<Channel> p) throws Exception {
                p.getObject().disconnect();
            }

            @Override
            public boolean validateObject(PooledObject<Channel> p) {

                return p.getObject().isActive();
            }
        };
    }

    private Channel doOpen() {


        bootstrap.group(workerGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {

                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline()
                                .addLast(new ObjectEncoder())
                                .addLast(new ObjectDecoder(Integer.MAX_VALUE,
                                        ClassResolvers.cacheDisabled(null)))
                                .addLast((new RpcClientHandler(DefaultClient.this, threadPoolExecutor)))
                        ;
                    }
                });
        try {
            final String host = url.getHost();
            final int port = url.getPort();
            channelFuture = bootstrap.connect(host, port)
                    .addListener(new ChannelFutureListener() {
                        @Override
                        public void operationComplete(ChannelFuture future) throws Exception {
                            if (logger.isDebugEnabled()) {
                                logger.debug("connnected [{}]:[{}] service success....", host,
                                        port);
                            }
                        }
                    });
            return channelFuture.channel();

        } catch (Exception e) {
            throw new RpcException(e);
        }
    }

    @Override
    public RpcResponse send(RpcRequest request, boolean async) {

        Client client = null;
        try {
            channel = pool.borrowObject();
            ChannelFuture future = channel.writeAndFlush(request);
            future.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    future.isDone();
                }
            });
            return null;
        } catch (Exception e) {
            throw new RpcException(e);
        }
    }

    @Override
    public RpcResponse send(RpcRequest request) {
        ChannelFuture future = channel.writeAndFlush(request);
        boolean isDone = future.awaitUninterruptibly().isDone();
        if (isDone && future.isSuccess()) {
            future.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (future.isDone() && future.isSuccess()) {

                    }
                }
            });

            return new RpcResponse();
        }

        future.cancel(true);
        return null;

    }

    @Override
    public  RpcResponse invoke(RpcRequest request) {
        return send(request);
    }
}
