package com.zuicoding.platform.rpc.consumer.impl;

import com.zuicoding.platform.rpc.common.RpcCaller;
import com.zuicoding.platform.rpc.common.exception.RpcException;
import com.zuicoding.platform.rpc.consumer.Client;
import com.zuicoding.platform.rpc.handler.RpcHandler;
import com.zuicoding.platform.rpc.handler.impl.DefaultRpcHandlerImpl;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Stephen.lin on 2017/9/25.
 * <p>
 * Description :<p>默认使用nettyClient</p>
 */
public class DefaultClient implements Client {

    private Logger logger = LoggerFactory.getLogger(DefaultClient.class);

    private String server = "127.0.0.1";
    private int port = 2017;
    private EventLoopGroup workerGroup ;
    private Bootstrap bootstrap;
    private ChannelFuture channelFuture;
    private RpcHandler handler;
    public DefaultClient() {
    }

    public DefaultClient(String server, int port) {
        this.server = server;
        this.port = port;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public void connect() {
        workerGroup = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        handler = new DefaultRpcHandlerImpl();
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
                                .addLast(handler)
                        ;
                    }
                });
        try {
            channelFuture = bootstrap.connect(server,port).syncUninterruptibly();
            logger.info("connnected [{}]:[{}] server success....",this.server,this.port);
        }catch (Exception e){
            throw new RpcException(e);
        }

    }

    @Override
    public void close() {
        try {
            if (channelFuture != null){
                channelFuture.channel().closeFuture().sync();
            }
            workerGroup.shutdownGracefully();
            logger.info("close {}:{} success...",this.port,this.port);
        }catch (Exception e){
            throw new RpcException(e);
        }
    }

    @Override
    public RpcCaller send( RpcCaller caller) {
        try {
            channelFuture.channel().writeAndFlush(caller);
        }catch (Exception e){
            throw new RpcException(e);
        }
        return null;

    }

}
