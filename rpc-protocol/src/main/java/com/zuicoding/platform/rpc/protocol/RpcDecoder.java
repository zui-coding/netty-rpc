package com.zuicoding.platform.rpc.protocol;

import com.zuicoding.platform.rpc.common.RpcCaller;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * Created by Stephen.lin on 2017/9/21.
 * <p>
 * Description :<p></p>
 */
public class RpcDecoder extends MessageToMessageDecoder<RpcCaller> {


    @Override
    protected void decode(ChannelHandlerContext ctx, RpcCaller msg, List<Object> out) throws Exception {

    }
}