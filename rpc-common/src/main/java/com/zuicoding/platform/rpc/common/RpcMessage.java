package com.zuicoding.platform.rpc.common;

import java.io.Serializable;

/**
 * Created by Stephen.lin on 2017/9/21.
 * <p>
 * Description :<p>rpc传输对象</p>
 */
public class RpcMessage<T> implements Serializable {
    private static final long serialVersionUID = -8681715352602910337L;

    private long id;
    //调用的接口
    private String interfaceClass;
    //调用的方法
    private String method;
    //方法依赖的参数
    private Object[] params;
    //方法返回的结果
    private T result;



    public RpcMessage() {
    }


    public RpcMessage(String interfaceClass, String method) {
        this.interfaceClass = interfaceClass;
        this.method = method;
    }

    public RpcMessage(String interfaceClass, String method, Object[] params) {
        this.interfaceClass = interfaceClass;
        this.method = method;
        this.params = params;
    }


    public String getInterfaceClass() {
        return interfaceClass;
    }

    public void setInterfaceClass(String interfaceClass) {
        this.interfaceClass = interfaceClass;
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

    public T getResult() {
        return result;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setResult(T result) {
        this.result = result;
    }


}

