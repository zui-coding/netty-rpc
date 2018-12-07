
package com.zuicoding.platform.rpc.executor.client;

import com.zuicoding.platform.rpc.common.Request;

import io.netty.channel.Channel;

/**
 * Created by <a href="mailto:stephen.linicoding@gmail.com">Stephen.lin</a> on 2018/3/23.
 * <p>
 * <p>
 * </p>
 */
public interface Client  {

    Channel connect(String ip, int port, int timeout) throws InterruptedException;

    Object send(Request request);


}
