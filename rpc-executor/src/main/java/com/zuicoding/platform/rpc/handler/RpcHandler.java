package com.zuicoding.platform.rpc.handler;

import com.zuicoding.platform.rpc.provider.Provider;
import io.netty.channel.ChannelHandler;

/**
 * Created by Stephen.lin on 2017/9/25.
 * <p>
 * Description :<p></p>
 */
public interface RpcHandler extends ChannelHandler {

    void registe(Provider provider);

}
