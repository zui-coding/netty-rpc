package com.zuicoding.platform.server;

import org.junit.Test;

import com.zuicoding.platform.rpc.executor.server.impl.DefaultNettyServer;

/**
 * @author : Created by <a href="mailto:stephen.linicoding@gmail.com">Stephen.lin</a> on 2017/10/25
 * @description: <p></p>
 */
public class ServerTester implements ITester {


    @Test
    public void startServer() {
        new DefaultNettyServer().start();
    }

    public static void main(String[] args) {
        new DefaultNettyServer().start();
    }
}
