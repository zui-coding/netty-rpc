package com.zuicoding.platform.rpc.protocol;

import com.zuicoding.platform.rpc.common.RpcMessage;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by Stephen.lin on 2017/9/21.
 * <p>
 * Description :<p>讲对象转换成二进制</p>
 */
public abstract class RpcEncoder extends MessageToByteEncoder<RpcMessage> {


}
