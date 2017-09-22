package com.zuicoding.platform;

import com.zuicoding.platform.rpc.server.RpcServer;

/**
 * Hello world!
 *
 */
public class Provider
{
    public static void main( String[] args )
    {
        RpcServer server = new RpcServer();
        server.start();
    }
}
