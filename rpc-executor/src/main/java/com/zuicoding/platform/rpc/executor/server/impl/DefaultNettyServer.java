
package com.zuicoding.platform.rpc.executor.server.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zuicoding.platform.rpc.common.Request;
import com.zuicoding.platform.rpc.common.Response;
import com.zuicoding.platform.rpc.executor.server.RpcServerHandler;
import com.zuicoding.platform.rpc.executor.server.Server;
import com.zuicoding.platform.rpc.protocol.RpcDecoder;
import com.zuicoding.platform.rpc.protocol.RpcEncoder;
import com.zuicoding.platform.rpc.protocol.URL;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
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

    private URL url;
    private EventLoopGroup bossGroup ;
    private EventLoopGroup workerGroup ;
    private Map<String,Object> refMap;
    private ChannelFuture serverFuture;
    public DefaultNettyServer(URL url,Map<String,Object> refMap) {
        this.url = url;
        this.refMap = refMap;
    }

    @Override
    public void start() {
        try {
            bossGroup = new NioEventLoopGroup();
            workerGroup = new NioEventLoopGroup();
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel sc) throws Exception {
                            sc.pipeline()
                                    //.addLast(new LengthFieldBasedFrameDecoder(65536, 0, 4, 0, 0))
                                    .addLast(new RpcDecoder(Request.class))
                                    .addLast(new RpcEncoder(Response.class))
                                    .addLast(new RpcServerHandler(DefaultNettyServer.this.refMap));
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            serverFuture = bootstrap.bind(url.getHost(),url.getPort()).sync();

            logger.info("the server {}:{} has started...",url.getHost(),url.getPort());
        }catch ( Exception e) {
            logger.error(String.format("the server %s:%s has started error", url.getHost(),url.getPort()));
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
                logger.info("The server {}:{} has shutdown successs...",url.getHost(),url.getPort());
            }
        }catch ( Exception e) {
            logger.error(String.format("The server %s:%s has shutdown successs...",url.getHost(),url.getPort()));
            throw new RuntimeException(e);
        }

    }


}
