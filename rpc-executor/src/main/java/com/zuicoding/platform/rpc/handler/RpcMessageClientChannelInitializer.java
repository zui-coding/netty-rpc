package com.zuicoding.platform.rpc.handler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

/**
 * @author : Created by <a href="mailto:stephen.linicoding@gmail.com">Stephen.lin</a> on 2017/10/25
 * @description: <p></p>
 */
public final class RpcMessageClientChannelInitializer
        extends ChannelInitializer<SocketChannel>{


    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline()
                .addLast(new ObjectEncoder())
                .addLast(new ObjectDecoder(Integer.MAX_VALUE,
                        ClassResolvers.cacheDisabled(null)))
                .addLast((new RpcClientHanlder()))
        ;
    }
}
