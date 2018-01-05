package com.zuicoding.platform.rpc.handler.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

import com.zuicoding.platform.rpc.common.RpcRequest;
import com.zuicoding.platform.rpc.common.RpcResponse;
import com.zuicoding.platform.rpc.common.exception.RpcException;
import com.zuicoding.platform.rpc.handler.Channel;
import com.zuicoding.platform.rpc.protocol.URL;
import com.zuicoding.platform.rpc.server.RpcServer;

/**
 * Created by <a href="mailto:linyajun@baidu.com">linyajun</a> on 2018/1/5.
 * <p>
 * <p>
 * </p>
 */
public class RpcChannel implements Channel {

    private Logger logger  = Logger.getLogger(getClass().getName());

    private Map<String,RpcServer> serverMap = new ConcurrentHashMap<>();

    @Override
    public RpcResponse invoke(URL url, RpcRequest request) {
        if (url == null){
            throw new RpcException("url == null");
        }
        if (request == null){
            throw new RpcException("request == null");
        }
        RpcServer server = serverMap.get(url.getAddress());
        return null;
    }

    @Override
    public void close() {

    }
}
