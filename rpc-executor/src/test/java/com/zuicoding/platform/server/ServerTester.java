package com.zuicoding.platform.server;

import com.zuicoding.platform.rpc.server.RpcServer;
import com.zuicoding.platform.rpc.server.impl.DefaultNettyServer;
import org.junit.Before;
import org.junit.Test;

/**
 * @author : Created by <a href="mailto:stephen.linicoding@gmail.com">Stephen.lin</a> on 2017/10/25
 * @description: <p></p>
 */
public class ServerTester {


    private RpcServer server;

    @Before
    public void init(){
        server = new DefaultNettyServer();
    }

    @Test
    public void start(){
        server.start();
    }
}
