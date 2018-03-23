
package com.zuicoding.platform.rpc.protocol;

import java.util.List;

import com.zuicoding.platform.rpc.protocol.serialization.Serializationer;
import com.zuicoding.platform.rpc.protocol.serialization.impl.JdkSerializationer;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

/**
 * Created by <a href="mailto:stephen.linicoding@gmail.com">stephen.lin</a> on 2018/3/23.
 * <p>
 * <p>
 *      rpc 自定义解码器
 *      定义包的格式，前 4个字节是包的长度
 * </p>
 */
public class RpcDecoder extends ByteToMessageDecoder {


    private Class<?> decodeClass;

    private Serializationer serializationer;

    public RpcDecoder(Class<?> decodeClass) {
        this.decodeClass = decodeClass;
        serializationer = new JdkSerializationer();
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        //前 4个字节是包的长度，如果可读的包 长度不够4个字节，则过滤解析
        if (in.readableBytes() < 4) {
            return;
        }
        in.markReaderIndex();
        //读取包的长度
        int dataLength = in.readInt();
        //如果可读的包，不够包的长度，则不解析
        if (in.readableBytes() < dataLength) {
            return;
        }

        byte[] data = new byte[dataLength];
        in.readBytes(data);
        Object o = serializationer.deserialize(data,this.decodeClass);
        out.add(o);
    }
}
