
package com.zuicoding.platform.rpc.executor.client;

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
public class ConsumerInitializer extends ChannelInitializer<SocketChannel> {


    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        channel.pipeline()
                .addLast(new RpcEncoder())
                .addLast(new RpcDecoder())
                .addLast(new ConsumerHandler());
    }
}
