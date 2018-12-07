
package com.zuicoding.platform.rpc.executor.client.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zuicoding.platform.rpc.common.DefaultRequest;
import com.zuicoding.platform.rpc.common.Request;
import com.zuicoding.platform.rpc.common.UrlParamEnum;
import com.zuicoding.platform.rpc.executor.client.AbstractClient;
import com.zuicoding.platform.rpc.executor.client.Client;
import com.zuicoding.platform.rpc.protocol.URL;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;

/**
 * Created by <a href="mailto:stephen.linicoding@gmail.com">stephen.lin</a> on 2018/3/23.
 * <p>
 * <p>
 * </p>
 */
public class DefaultNettyClient extends AbstractClient {


    private static Logger logger = LoggerFactory.getLogger(DefaultNettyClient.class);

    private String host;
    private int port;
    private int timeout;

    private Channel channel;

    public DefaultNettyClient(URL url) {
        this.host = url.getHost();
        this.port = url.getPort();
        timeout = url.getIntParameter(UrlParamEnum.TIMEOUT.getName(),
                UrlParamEnum.TIMEOUT.getIntValue());
    }

    public void connect() {
        try {

            if (this.channel == null && !this.channel.isActive()) {
                this.channel = super.connect(this.host, this.port, timeout);
            }

        } catch (Exception e) {
            logger.error(String.format("connect %s:%s error", this.host, this.port), e);
            throw new RuntimeException(e);
        }

    }

    @Override
    public Object send(Request request) {

        ChannelFuture future = this.channel.writeAndFlush(request);
        future.await(timeout);
        Throwable cause = future.cause();
        if (cause != null) {
            throw cause;
        }
        return null;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public int getTimeout() {
        return timeout;
    }

    public Channel getChannel() {
        return channel;
    }

    public static void main(String[] args) {

        Client client = new DefaultNettyClient(URL.valueOf("linrpc://localhost:8080/com.zuicoding.platform.rpc"
                + ".executor"
            + ".client.impl"
            + ".DefaultNettyClient"));
        Request request = new DefaultRequest();
        request.setRequestId(123);
        request.setArguments(new Object[]{1});
        request.setInterfaceName("123456");
        ((DefaultRequest) request).setMethodName("add");
        request.setParameterClasses(new Class[]{Integer.class});
        ((DefaultNettyClient) client).connect();
        client.send(request);
    }
}
