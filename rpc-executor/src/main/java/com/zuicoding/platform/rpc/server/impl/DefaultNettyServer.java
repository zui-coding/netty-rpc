package com.zuicoding.platform.rpc.server.impl;

import java.util.concurrent.ThreadPoolExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zuicoding.platform.rpc.common.exception.RpcException;
import com.zuicoding.platform.rpc.common.utils.RpcUtils;
import com.zuicoding.platform.rpc.handler.RpcServerChannelHandler;
import com.zuicoding.platform.rpc.pool.DefaultThreadFactory;
import com.zuicoding.platform.rpc.pool.RpcThreadPoolExecutor;
import com.zuicoding.platform.rpc.registry.RpcRegistry;
import com.zuicoding.platform.rpc.server.RpcServer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

/**
 * Created by Stephen.lin on 2017/9/25.
 * <p>
 * Description :<p>默认是netty实现</p>
 */
public class DefaultNettyServer implements RpcServer {
    private Logger logger = LoggerFactory.getLogger(DefaultNettyServer.class);
    private int
            port = 2017,
            poolMinSize = 100,
            poolMaxSize = 200;
    private EventLoopGroup bossGroup, workerGroup;
    private ServerBootstrap bootstrap;
    private ChannelFuture channelFuture;
    private ChannelHandler handler;
    private boolean isStart = false;

    private ThreadPoolExecutor poolExecutor;

    private com.zuicoding.platform.rpc.protocol.URL url;
    private RpcRegistry registry;

    public DefaultNettyServer(com.zuicoding.platform.rpc.protocol.URL url, RpcRegistry registry) {

        this.url = url;
        this.registry = registry;
    }


    @Override
    public void start() {
        try {
            if (isStart) {
                logger.info("the server has started...");
                return;
            }
            poolExecutor = new RpcThreadPoolExecutor(poolMaxSize,
                    poolMaxSize, new DefaultThreadFactory("rpc-"+ RpcUtils.getLocalAddress() + ":"+this.port));
            bossGroup = new NioEventLoopGroup();
            workerGroup = new NioEventLoopGroup();
            bootstrap = new ServerBootstrap();
            handler = new RpcServerChannelHandler(poolExecutor);
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast(new ObjectEncoder())
                                    .addLast(new ObjectDecoder(Integer.MAX_VALUE,
                                            ClassResolvers.cacheDisabled(null)))
                            .addLast(handler);

                        }
                    }).option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            channelFuture =  bootstrap.bind(url.getHost(),url.getPort()).sync();
            logger.info("service{}: {} started success...",this.url.getHost(),this.url.getHost());

            isStart = true;
            registry.registe(url);
        }catch (Exception e){
            isStart = false;
            throw new RpcException(String.format("start port: %s service error", this.port),e);
        }

    }

    @Override
    public void stop() {
        try {
            if (channelFuture != null){
                channelFuture.channel().closeFuture().sync();
            }
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
            logger.info("stop port:{} success...",this.port);
            isStart = false;
        }catch (Exception e){
            isStart = false;
            throw new RpcException(e);
        }

    }



}
