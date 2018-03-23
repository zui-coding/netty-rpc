
package com.zuicoding.platform.rpc.protocol;

import com.zuicoding.platform.rpc.protocol.serialization.Serializationer;
import com.zuicoding.platform.rpc.protocol.serialization.impl.JdkSerializationer;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by <a href="mailto:stephen.linicoding@gmail.com">Stephen.lin</a> on 2018/3/23.
 * <p>
 * <p>
 * rpc 自定义编码器
 * </p>
 */
public class RpcEncoder extends MessageToByteEncoder {

    private Class<?> encodeClass;

    private Serializationer serializationer;

    public RpcEncoder(Class<?> encodeClass) {
        this.encodeClass = encodeClass;
        this.serializationer = new JdkSerializationer();
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        if (!encodeClass.isInstance(msg)) {
            return;
        }
        byte[] data = this.serializationer.serialize(msg);
        //先写4个字节的包的长度
        out.writeInt(data.length);
        //在写包内容
        out.writeBytes(data);
    }

}
