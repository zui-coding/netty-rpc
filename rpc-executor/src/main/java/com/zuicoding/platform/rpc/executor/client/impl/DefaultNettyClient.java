
package com.zuicoding.platform.rpc.executor.client.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zuicoding.platform.rpc.common.UrlParamEnum;
import com.zuicoding.platform.rpc.common.DefaultRpcThreadFactory;
import com.zuicoding.platform.rpc.executor.client.AbstractClient;
import com.zuicoding.platform.rpc.executor.client.ConsumerHandler;
import com.zuicoding.platform.rpc.executor.client.ConsumerInitializer;
import com.zuicoding.platform.rpc.protocol.RpcDecoder;
import com.zuicoding.platform.rpc.protocol.RpcEncoder;
import com.zuicoding.platform.rpc.protocol.URL;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Created by <a href="mailto:stephen.linicoding@gmail.com">stephen.lin</a> on 2018/3/23.
 * <p>
 * <p>
 * </p>
 */
public class DefaultNettyClient extends AbstractClient {


    private static Logger logger = LoggerFactory.getLogger(DefaultNettyClient.class);

    private Bootstrap bootstrap;

    private URL url;

    private EventLoopGroup woker = null;

    private int timeout;

    public DefaultNettyClient(URL url) {
        this.url = url;
        timeout = url.getIntParameter(UrlParamEnum.TIMEOUT.getName(),
                UrlParamEnum.TIMEOUT.getIntValue());
    }


    public boolean connect() {
        try {
            woker = new NioEventLoopGroup(Math.min(Runtime.getRuntime().availableProcessors() + 1, 32),
                    new DefaultRpcThreadFactory());
            bootstrap = new Bootstrap();
            bootstrap.group(woker)
                    .channel(Epoll.isAvailable() ? EpollSocketChannel.class : NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    .handler(new ConsumerInitializer());

            bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, timeout);

            ChannelFuture futrue = bootstrap.connect(url.getHost(), url.getPort()).sync();

            //等待客户端链路关闭
            futrue.channel().closeFuture().sync();
        }catch (Exception e) {
            logger.error("", e);
        }finally {
            woker.shutdownGracefully();
        }


        return true;
    }


    public static void main(String[] args) {

        new DefaultNettyClient(URL.valueOf("linrpc://localhost:8080/com.zuicoding.platform.rpc.executor.client.impl.DefaultNettyClient")).connect();
    }
}
