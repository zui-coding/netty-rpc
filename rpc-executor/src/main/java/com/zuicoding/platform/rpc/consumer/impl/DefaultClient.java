package com.zuicoding.platform.rpc.consumer.impl;

import com.zuicoding.platform.rpc.common.RpcRequest;
import com.zuicoding.platform.rpc.common.exception.RpcException;
import com.zuicoding.platform.rpc.consumer.Client;
import com.zuicoding.platform.rpc.handler.RpcClientHandler;
import com.zuicoding.platform.rpc.handler.RpcMessageClientChannelInitializer;
import com.zuicoding.platform.rpc.protocol.URL;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
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

    private URL url;
    private EventLoopGroup workerGroup ;
    private Bootstrap bootstrap;
    private ChannelFuture channelFuture;
    private ChannelInitializer channelInitializer;

    public DefaultClient(URL url) {
        this.url = url;
    }



    @Override
    public void connect() {

        doOpen();
    }

    private void doOpen(){
        channelInitializer = new RpcMessageClientChannelInitializer();
        workerGroup = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(workerGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(channelInitializer);
        try {
            channelFuture = bootstrap.connect(url.getHost(),
                    url.getPort()).syncUninterruptibly();
            logger.info("connnected [{}]:[{}] server success....",this.url.getHost(),
                    this.url.getPort());
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
            logger.info("close {}:{} success...",this.url.getHost(),this.url.getPort());
        }catch (Exception e){
            throw new RpcException(e);
        }
    }

    @Override
    public RpcRequest send(RpcRequest message) {

        try {

            RpcClientHandler handler = channelFuture
                    .channel()
                    .pipeline()
                    .get(RpcClientHandler.class);
            handler.send(message);
            return message;
        }catch (Exception e){
            throw new RpcException(e);
        }
    }

}
