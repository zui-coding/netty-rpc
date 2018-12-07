
package com.zuicoding.platform.rpc.executor.server.impl;

import java.util.Map;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zuicoding.platform.rpc.executor.server.ProviderChannelInitializer;
import com.zuicoding.platform.rpc.executor.server.Server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created by <a href="mailto:stephen.linicoding@gmail.com">stephen.lin</a> on 2018/3/23.
 * <p>
 * <p>
 *
 * </p>
 */
public class DefaultNettyServer implements Server {

    private Logger logger = LoggerFactory.getLogger(DefaultNettyServer.class);

    private EventLoopGroup bossGroup ;
    private EventLoopGroup workerGroup ;
    private Map<String,Object> refMap;
    private ChannelFuture serverFuture;
    private int port;
    public DefaultNettyServer(int port) {
        this.port = port;
    }

    public DefaultNettyServer() {

        if (StringUtils.isBlank(System.getProperty("server.port"))) {
            port = RandomUtils.nextInt(8000, 9999);
            System.setProperty("server.port", String.valueOf(port));
        }
        this.port = port;
    }

    @Override
    public void start() {
        this.doStart();
    }

    private void  doStart() {


        try {

            bossGroup =  Epoll.isAvailable() ? new EpollEventLoopGroup() : new NioEventLoopGroup();
            workerGroup = Epoll.isAvailable() ? new EpollEventLoopGroup() : new NioEventLoopGroup();
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup,workerGroup)
                    .channel(Epoll.isAvailable() ? EpollServerSocketChannel.class : NioServerSocketChannel.class)
                    .childHandler(new ProviderChannelInitializer())
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            serverFuture = bootstrap.bind(port).sync();

            logger.info("the server 127.0.0.1:{} has started...", port);
        }catch ( Exception e) {
            logger.error(String.format("the server 127.0.0.1:%s has started error", port));
            throw new RuntimeException(e);
        }
    }



    @Override
    public void stop() {
        try {
            if (serverFuture != null) {
                serverFuture.channel().closeFuture().sync();
                bossGroup.shutdownGracefully();
                workerGroup.shutdownGracefully();
                logger.info("The server 127.0.0.1:{} has shutdown successs...", port);
            }
        }catch ( Exception e) {
            logger.error(String.format("The server 127.0.0.1:%s has shutdown successs...", port));
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) {
        new DefaultNettyServer(8080).start();
    }


}
