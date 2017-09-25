package com.zuicoding.platform.rpc.common.exception;

/**
 * Created by Stephen.lin on 2017/9/21.
 * <p>
 * Description :<p></p>
 */
public class RpcException extends RuntimeException {


    public RpcException(String message){
        super(message);
    }
    public RpcException(Throwable thr){
        super(thr);
    }
    public RpcException(String message,Throwable thr){
        super(message,thr);
    }
}
