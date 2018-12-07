
package com.zuicoding.platform.rpc.executor.client;

import com.zuicoding.platform.rpc.common.DefaultRpcThreadFactory;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Created by <a href="mailto:stephen.linicoding@gmail.com">stephen.lin</a> on 2018/9/10.
 * <p>
 *
 * </p>
 */
public abstract class AbstractClient implements Client {

    private Bootstrap bootstrap;
    private EventLoopGroup woker = null;


    @Override
    public Channel connect(String host, int port, int timeout) throws InterruptedException {
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

        ChannelFuture futrue = bootstrap.connect(host, port).sync();

        //等待客户端链路关闭
        return futrue.channel();
    }
}
