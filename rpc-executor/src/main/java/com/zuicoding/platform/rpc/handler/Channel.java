package com.zuicoding.platform.rpc.handler;

import com.zuicoding.platform.rpc.common.RpcRequest;
import com.zuicoding.platform.rpc.common.RpcResponse;
import com.zuicoding.platform.rpc.protocol.URL;

/**
 * Created by <a href="mailto:linyajun@baidu.com">linyajun</a> on 2018/1/5.
 * <p>
 * <p>
 *     链接管道
 * </p>
 */
public interface Channel {



    RpcResponse invoke(URL url,RpcRequest request);

    void close();
}
