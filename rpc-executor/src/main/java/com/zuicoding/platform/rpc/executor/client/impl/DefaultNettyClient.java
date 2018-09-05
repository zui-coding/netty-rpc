
package com.zuicoding.platform.rpc.executor.client.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zuicoding.platform.rpc.common.DefaultRpcThreadFactory;
import com.zuicoding.platform.rpc.executor.client.Client;
import com.zuicoding.platform.rpc.executor.client.ClientHandler;
import com.zuicoding.platform.rpc.protocol.RpcDecoder;
import com.zuicoding.platform.rpc.protocol.RpcEncoder;
import com.zuicoding.platform.rpc.protocol.URL;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Created by <a href="mailto:stephen.linicoding@gmail.com">stephen.lin</a> on 2018/3/23.
 * <p>
 * <p>
 * </p>
 */
public class DefaultNettyClient implements Client {


    private static Logger logger = LoggerFactory.getLogger(DefaultNettyClient.class);

    private Bootstrap bootstrap;

    private URL url;

    private EventLoopGroup woker = null;

    public DefaultNettyClient(URL url) {
        this.url = url;
    }

    @Override
    public boolean connect() {
        try {
            woker = new NioEventLoopGroup(Math.min(Runtime.getRuntime().availableProcessors() + 1, 32),
                    new DefaultRpcThreadFactory());
            bootstrap = new Bootstrap();
            bootstrap.group(woker)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            //获取管道
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new RpcDecoder(Object.class));
                            pipeline.addLast(new RpcEncoder(Object.class));
                            pipeline.addLast(new ClientHandler(DefaultNettyClient.this.url));
                        }
                    });
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
