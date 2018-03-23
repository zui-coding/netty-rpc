package com.zuicoding.platform.rpc.registry.impl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import com.zuicoding.platform.rpc.common.Provider;
import com.zuicoding.platform.rpc.protocol.URL;
import com.zuicoding.platform.rpc.registry.NotifyListener;
import com.zuicoding.platform.rpc.registry.RpcRegistry;

/**
 * Created by Stephen.lin on 2017/9/25.
 * <p>
 * Description :<p>模拟一个本地注册中心</p>
 */
public class MockLocalRpcRegistry implements RpcRegistry {

    private List<URL> urls = new CopyOnWriteArrayList<>();

    /**
     * 需要注册的url
     *
     * @param url
     */
    @Override
    public void registe(URL url) {
        urls.add(url);
    }

    @Override
    public void subscribe(URL url, NotifyListener notifyListener) {
        for (URL oldUrl : urls) {
            if (oldUrl.getHost().equals(url.getHost())) {
                if (!oldUrl.equals(url)) {
                    notifyListener.notify(urls);
                }
            }
        }
    }

}
