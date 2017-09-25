package com.zuicoding.platform.rpc.registry;

import java.io.Serializable;

/**
 * Created by Stephen.lin on 2017/9/25.
 * <p>
 * Description :<p></p>
 */
public class Register implements Serializable {

    private static final long serialVersionUID = -4900484963595751254L;
    private String iinterface;
    private String method;
    private Object ref;
    private String server;
    private int port;

    public Register() {
    }

    public Register(String iinterface, String method, Object ref, String server, int port) {
        this.iinterface = iinterface;
        this.method = method;
        this.ref = ref;
        this.server = server;
        this.port = port;
    }

    public String getIinterface() {
        return iinterface;
    }

    public void setIinterface(String iinterface) {
        this.iinterface = iinterface;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Object getRef() {
        return ref;
    }

    public void setRef(Object ref) {
        this.ref = ref;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
