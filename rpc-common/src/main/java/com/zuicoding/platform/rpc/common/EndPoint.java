
package com.zuicoding.platform.rpc.common;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by <a href="mailto:stephen.linicoding@gmail.com">Stephen.lin</a> on 2018-12-05.
 * <p>
 *
 * </p>
 */
public class EndPoint {
    private String host;
    private String port;

    public EndPoint(String host, String port) {
        this.host = host;
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public String getPort() {
        return port;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("host", host)
                .append("port", port)
                .toString();
    }
}
