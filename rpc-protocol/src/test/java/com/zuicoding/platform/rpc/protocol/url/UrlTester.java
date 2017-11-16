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
        url.addParam("timeout",6000).addParam("appName","test");
        System.err.println(url);
        String urlStr = "rpc://localhost:9999/com.zuicoding.platform.rpc.protocol.url.UrlTester?timeout=6000&appName=test";
        URL u1 = URL.parse(urlStr);

        System.err.println(url.equals(u1));
    }

    @Test
    public void testErrorUrl(){
        URL url = URL.parse("http://www.baidu.com");
    }
}
