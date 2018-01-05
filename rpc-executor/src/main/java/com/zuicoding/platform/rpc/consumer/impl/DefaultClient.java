package com.zuicoding.platform.rpc.consumer.impl;

import com.zuicoding.platform.rpc.common.RpcRequest;
import com.zuicoding.platform.rpc.common.RpcResponse;
import com.zuicoding.platform.rpc.common.exception.RpcException;
import com.zuicoding.platform.rpc.consumer.AbstractPoolClient;
import com.zuicoding.platform.rpc.consumer.Client;
import com.zuicoding.platform.rpc.handler.RpcClientHandler;
import com.zuicoding.platform.rpc.protocol.URL;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by Stephen.lin on 2017/9/25.
 * <p>
 * Description :<p>默认使用nettyClient</p>
 */
public class DefaultClient extends AbstractPoolClient {

    private Logger logger = LoggerFactory.getLogger(DefaultClient.class);

    private URL url;
    private EventLoopGroup workerGroup ;
    private Bootstrap bootstrap;
    private ChannelFuture channelFuture;
    private ThreadPoolExecutor threadPoolExecutor;
    private Channel channel;
    public DefaultClient(URL url) {
        super(url);
        this.url = url;

    }

    @Override
    public PooledObjectFactory<Client> createPooledObjectFactory() {
        return new BasePooledObjectFactory<Client>() {
            @Override
            public Client create() throws Exception {
                return DefaultClient.this;
            }

            @Override
            public PooledObject<Client> wrap(Client obj) {
                return null;
            }

            @Override
            public void destroyObject(PooledObject<Client> p) throws Exception {
                DefaultClient.this.close();
            }
        };
    }


    @Override
    public void connect() {

        doOpen();
    }

    private void doOpen(){

        workerGroup = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
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
                                .addLast((new RpcClientHandler(DefaultClient.this,threadPoolExecutor)))
                        ;
                    }
                });
        try {
            channelFuture = bootstrap.connect(url.getHost(),
                    url.getPort()).syncUninterruptibly();
            channel = channelFuture.channel();
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
    public RpcResponse send(RpcRequest request,boolean async) {

        Client client = null;
        try {
            client = pool.borrowObject();
            return client.send(request);
        } catch (Exception e) {
            throw new RpcException(e);
        }
    }

    @Override
    public RpcResponse send(RpcRequest request) {
        ChannelFuture  future = channel.writeAndFlush(request);
        boolean isDone = future.awaitUninterruptibly().isDone();
        if(isDone && future.isSuccess()){
            future.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (future.isDone() && future.isSuccess()){

                    }
                }
            });

            return  new RpcResponse();
        }

        future.cancel(true);
        return null;

    }
}
