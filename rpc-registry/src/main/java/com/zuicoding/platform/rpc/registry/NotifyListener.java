
package com.zuicoding.platform.rpc.registry;

import java.util.List;

import com.zuicoding.platform.rpc.protocol.URL;

/**
 * Created by <a href="mailto:stephen.linicoding@gmail.com">Stephen.lin</a> on 2018/3/22.
 * <p>
 * <p>
 * </p>
 */
public interface NotifyListener {

    /**
     * 变更时通知
     * @param urls
     */
    void  notify(List<URL> urls);
}
