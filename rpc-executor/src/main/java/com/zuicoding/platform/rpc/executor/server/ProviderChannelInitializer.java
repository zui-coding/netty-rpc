
package com.zuicoding.platform.rpc.executor.server;

import com.zuicoding.platform.rpc.common.Request;
import com.zuicoding.platform.rpc.common.Response;
import com.zuicoding.platform.rpc.executor.server.impl.DefaultNettyServer;
import com.zuicoding.platform.rpc.protocol.RpcDecoder;
import com.zuicoding.platform.rpc.protocol.RpcEncoder;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 * Created by <a href="mailto:stephen.linicoding@gmail.com">stephen.lin</a> on 2018-12-05.
 * <p>
 *
 * </p>
 */
public class ProviderChannelInitializer extends ChannelInitializer<SocketChannel> {


    @Override
    protected void initChannel(SocketChannel sc) throws Exception {
        sc.pipeline()
                //.addLast(new LengthFieldBasedFrameDecoder(65536, 0, 4, 0, 0))
                .addLast(new RpcDecoder(Request.class))
                .addLast(new RpcEncoder(Response.class))
                .addLast(new ProviderServerHandler());
    }
}
