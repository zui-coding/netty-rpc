package com.zuicoding.platform.rpc.registry.impl;

import com.zuicoding.platform.rpc.protocol.URL;
import com.zuicoding.platform.rpc.registry.NotifyListener;
import com.zuicoding.platform.rpc.registry.RpcRegistry;


/**
 * @author : Created by <a href="mailto:stephen.linicoding@gmail.com">Stephen.lin</a> on 2017/10/27
 * @description: <p>zookeeper 注册中心</p>
 */
public class ZookeeperRpcRegistry implements RpcRegistry {

    /**
     * 需要注册的url
     *
     * @param url
     */
    @Override
    public void registe(URL url) {

    }

    @Override
    public void subscribe(URL url, NotifyListener notifyListener) {

    }
}
