package com.zuicoding.platform.rpc.executor;

import com.zuicoding.platform.rpc.common.Response;

/**
 * Created by <a href="mailto:stephen.linicoding@gmail.com">stephen.lin</a> on 2018/3/23.
 * <p>
 * <p>
 * </p>
 */
public class RpcResponse implements Response {

    private String id;
    private Exception exception;
    private Object result;

    public RpcResponse() {
    }

    public RpcResponse(String id) {
        this.id = id;
    }

    public RpcResponse(String id, Object result) {
        this.id = id;
        this.result = result;
    }

    public RpcResponse(String id, Exception exception, Object result) {
        this.id = id;
        this.exception = exception;
        this.result = result;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return this.id;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
