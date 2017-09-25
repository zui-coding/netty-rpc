package com.zuicoding.platform.rpc.proxy.impl;

import com.zuicoding.platform.rpc.common.RpcCaller;
import com.zuicoding.platform.rpc.proxy.RpcInvoker;
import com.zuicoding.platform.rpc.proxy.handler.RpcConsumerHandler;
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
 * Created by Stephen.lin on 2017/9/21.
 * <p>
 * Description :<p></p>
 */
public class RpcConsumerInoker implements RpcInvoker {

    private Logger logger = LoggerFactory.getLogger(RpcConsumerInoker.class);

    private String host = "127.0.0.1";
    private int port = 2017;
    private EventLoopGroup workerGroup ;
    private Bootstrap bootstrap;
    private RpcConsumerHandler consumerHandler;

    private RpcCaller caller;
    public RpcConsumerInoker() {
        init();
    }

    public RpcConsumerInoker(String host, int port) {
        this.host = host;
        this.port = port;
        init();
    }

    public RpcConsumerInoker(String host, int port, RpcCaller caller) {
        this.host = host;
        this.port = port;
        this.caller = caller;
        init();
    }

    public RpcConsumerInoker(RpcCaller caller) {
        this.caller = caller;
        init();
    }

    private void init(){
        workerGroup  = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        consumerHandler = new RpcConsumerHandler();
        consumerHandler.setCaller(caller);
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
                                .addLast(consumerHandler);
                    }
                });
        try {
            ChannelFuture cf = bootstrap.connect(host, port).sync();
            logger.info("connnected [{}]:[{}] server success....",this.host,this.port);
            cf.channel().closeFuture().sync();
            logger.info("close connection success...");
        }catch (Exception e){
           logger.error("",e);
        }finally {
            workerGroup.shutdownGracefully();

        }


    }

    @Override
    public Object invoke(RpcCaller caller) {
        consumerHandler.invoke(caller);
        return null;
    }
}
