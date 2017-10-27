package com.zuicoding.platform.rpc.server.impl;

import com.zuicoding.platform.rpc.common.exception.RpcException;
import com.zuicoding.platform.rpc.handler.RpcServerHandler;
import com.zuicoding.platform.rpc.provider.Provider;
import com.zuicoding.platform.rpc.server.RpcServer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Stephen.lin on 2017/9/25.
 * <p>
 * Description :<p>默认是netty实现</p>
 */
public class DefaultNettyRpcServer implements RpcServer {
    private Logger logger = LoggerFactory.getLogger(DefaultNettyRpcServer.class);
    private int bossThreads, workerThreads, port = 2017;
    private EventLoopGroup bossGroup, workerGroup;
    private ServerBootstrap bootstrap;
    private ChannelFuture channelFuture;
    private ChannelHandler handler;
    private boolean isStart = false;

    public DefaultNettyRpcServer() {
    }

    public DefaultNettyRpcServer(int port) {
        this.port = port;
    }

    public DefaultNettyRpcServer(int bossThreads, int workerThreads, int port) {
        this.bossThreads = bossThreads;
        this.workerThreads = workerThreads;
        this.port = port;
    }

    public DefaultNettyRpcServer(int bossThreads, int workerThreads) {
        this.bossThreads = bossThreads;
        this.workerThreads = workerThreads;
    }

    @Override
    public void start() {
        try {
            bossGroup = new NioEventLoopGroup(bossThreads);
            workerGroup = new NioEventLoopGroup(workerThreads);
            bootstrap = new ServerBootstrap();
            handler = new RpcServerHandler();
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
            channelFuture =  bootstrap.bind(port).sync();
            logger.info("server {} started success...",this.port);
            logger.info("boss thread count:{},worker thread count:{}...",this.bossThreads,this.workerThreads);
            isStart = true;
        }catch (Exception e){
            isStart = false;
            throw new RpcException(String.format("start port: %s server error", this.port),e);
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

    @Override
    public void addProvider(Provider provider) {
        if (!isStart){
            throw new RpcException("This server hasn't  started....");
        }
       // handler.registe(provider);
    }

    public int getBossThreads() {
        return bossThreads;
    }

    public void setBossThreads(int bossThreads) {
        this.bossThreads = bossThreads;
    }

    public int getWorkerThreads() {
        return workerThreads;
    }

    public void setWorkerThreads(int workerThreads) {
        this.workerThreads = workerThreads;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
