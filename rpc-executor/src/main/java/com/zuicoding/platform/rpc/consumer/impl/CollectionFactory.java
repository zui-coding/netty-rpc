
package com.zuicoding.platform.rpc.consumer.impl;

import org.apache.commons.pool2.BaseKeyedPooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zuicoding.platform.rpc.common.exception.RpcException;
import com.zuicoding.platform.rpc.handler.RpcClientHandler;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

/**
 * Created by <a href="mailto:stephen.linicoding@gmail.com">Stephen.lin</a> on 2018/3/23.
 * <p>
 * <p>
 * </p>
 */
public class CollectionFactory extends BaseKeyedPooledObjectFactory<String, Channel> {

    private Logger logger = LoggerFactory.getLogger(CollectionFactory.class);
    private EventLoopGroup workerGroup;
    private Bootstrap bootstrap;

    @Override
    public Channel create(String key) throws Exception {

        return this.doOpen(key);
    }

    @Override
    public PooledObject<Channel> wrap(Channel value) {

        return new DefaultPooledObject<>(value);
    }

    private io.netty.channel.Channel doOpen(String url) {


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
            String[] address = url.split(":");
            final String host = address[0];
            final int port = Integer.valueOf(address[1]);
            ChannelFuture channelFuture = bootstrap.connect(host, port)
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
}
