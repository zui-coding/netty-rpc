package com.zuicoding.platform.rpc.consumer.impl;

import com.zuicoding.platform.rpc.common.RpcMessage;
import com.zuicoding.platform.rpc.common.exception.RpcException;
import com.zuicoding.platform.rpc.consumer.Client;
import com.zuicoding.platform.rpc.handler.RpcHandler;
import com.zuicoding.platform.rpc.handler.RpcMessageClientChannelInitializer;
import com.zuicoding.platform.rpc.handler.impl.RpcMessageChannelHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
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
    private ChannelInitializer channelInitializer;
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

        doOpen();
    }

    private void doOpen(){
        channelInitializer = new RpcMessageClientChannelInitializer();
        workerGroup = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        handler = new RpcMessageChannelHandler();
        bootstrap.group(workerGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(channelInitializer);
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
    public RpcMessage send(RpcMessage message) {

        try {

            RpcMessageChannelHandler handler = channelFuture
                    .channel()
                    .pipeline()
                    .get(RpcMessageChannelHandler.class);

            return message;
        }catch (Exception e){
            throw new RpcException(e);
        }
    }

}
