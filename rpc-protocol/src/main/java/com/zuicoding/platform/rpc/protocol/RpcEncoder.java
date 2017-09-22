package com.zuicoding.platform.rpc.protocol;

import com.zuicoding.platform.rpc.common.RpcCaller;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by Stephen.lin on 2017/9/21.
 * <p>
 * Description :<p>讲对象转换成二进制</p>
 */
public class RpcEncoder extends MessageToByteEncoder<RpcCaller> {




    @Override
    protected void encode(ChannelHandlerContext ctx, RpcCaller msg, ByteBuf out) throws Exception {

    }
}
