package com.zuicoding.platform.server;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import com.zuicoding.platform.rpc.executor.server.Server;
import com.zuicoding.platform.rpc.executor.server.impl.DefaultNettyServer;
import com.zuicoding.platform.rpc.protocol.URL;

/**
 * @author : Created by <a href="mailto:stephen.linicoding@gmail.com">Stephen.lin</a> on 2017/10/25
 * @description: <p></p>
 */
public class ServerTester implements ITester {


    private Server server;

    @Before
    public void init(){
        URL url = new URL();
        url.setHost("localhost");
        url.setPort(8080);
        url.setPath("com.zuicoding.platform.server.ITester");
        server = new DefaultNettyServer(url,new HashMap<String, Object>(){
            {
                put("com.zuicoding.platform.server.ITester",this);
            }
        });
    }

    public static void main(String[] args) {
        ServerTester tester = new ServerTester();
        tester.init();
        tester.start();
    }

    @Test
    public void start(){
        server.start();
    }

    @Override
    public int add(int... numbers) {
        if (numbers == null) {
            return 0;
        }
        int sum = 0;
        for (int number : numbers) {
            sum += number;
        }
        return sum;
    }
}
