package com.zuicoding.platform.rpc.protocol.url;

import com.zuicoding.platform.rpc.protocol.URL;
import org.junit.Test;

/**
 * Created by Stephen.lin on 2017/11/16
 * <p>
 * <p></p>
 */
public class UrlTester {

    @Test
    public void testUrl(){
        URL url = new URL("localhost",9999,"com.zuicoding.platform.rpc.protocol.url.UrlTester");
        url.addParameter("timeout","6000").addParameter("appName","test");
        System.err.println(url);
        String urlStr = "linrpc://localhost:9999/com.zuicoding.platform.rpc.protocol.url"
                + ".UrlTester?timeout=6000&appName=test";
        URL u1 = URL.valueOf(urlStr);

        System.err.println(url.equals(u1));
    }


}
