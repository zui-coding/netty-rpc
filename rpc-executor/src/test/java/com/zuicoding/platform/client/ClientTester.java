
package com.zuicoding.platform.client;

import com.zuicoding.platform.rpc.executor.client.ClientHandler;
import com.zuicoding.platform.rpc.protocol.URL;
import com.zuicoding.platform.server.ITester;

/**
 * Created by <a href="mailto:stephen.linicoding@gmail.com">stephen.lin</a> on 2018/9/5.
 * <p>
 *
 * </p>
 */
public class ClientTester {

    public static void main(String[] args) throws ClassNotFoundException {

        URL url = new URL("localhost", 8080);
        url.setPath("com.zuicoding.platform.server.ITester");

        ClientHandler handler = new ClientHandler(url);

        ((ITester)handler.createProxy()).add(1,2);

    }
}
