package com.zuicoding.platform.rpc.registry;

import com.zuicoding.platform.rpc.protocol.URL;

/**
 * Created by Stephen.lin on 2017/9/25.
 * <p>
 * Description :<p>注册中心</p>
 */
public interface RpcRegistry {

    /**
     * 需要注册的url
     * @param url
     */
    void registe(URL url);

    void subscribe(URL url,NotifyListener notifyListener);


}
