package com.zuicoding.platform.rpc.common;

import java.io.Serializable;

/**
 * Created by Stephen.lin on 2017/9/21.
 * <p>
 * Description :<p>rpc传输对象</p>
 */
public class RpcCaller implements Serializable  {
    private static final long serialVersionUID = -8681715352602910337L;

    private long id;
    //调用的接口
    private String klass;
    //调用的方法
    private String method;
    //方法依赖的参数
    private Object[] params;
    //方法返回的结果
    private Object result;

    public RpcCaller() {
    }

    public RpcCaller(String klass, String method, Object[] params, Object result) {
        this.klass = klass;
        this.method = method;
        this.params = params;
        this.result = result;
    }

    public RpcCaller(String klass, String method) {
        this.klass = klass;
        this.method = method;
    }

    public RpcCaller(String klass, String method, Object[] params) {
        this.klass = klass;
        this.method = method;
        this.params = params;
    }


    public String getKlass() {
        return klass;
    }

    public void setKlass(String klass) {
        this.klass = klass;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
