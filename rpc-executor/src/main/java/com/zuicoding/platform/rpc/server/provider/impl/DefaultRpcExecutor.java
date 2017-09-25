package com.zuicoding.platform.rpc.server.provider.impl;

import com.zuicoding.platform.rpc.common.RpcCaller;
import com.zuicoding.platform.rpc.common.exception.RpcException;
import com.zuicoding.platform.rpc.registry.Register;
import com.zuicoding.platform.rpc.server.RpcServer;
import com.zuicoding.platform.rpc.server.provider.Provider;
import com.zuicoding.platform.rpc.server.provider.RpcExecutor;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.lang.reflect.Method;

/**
 * Created by Stephen.lin on 2017/9/25.
 * <p>
 * Description :<p></p>
 */
public class DefaultRpcExecutor extends ChannelInboundHandlerAdapter implements RpcExecutor {


    private Provider provider;

    private RpcServer server;

    public void start(){
        server = new RpcServer(this);
        server.start();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
        if(msg instanceof  RpcCaller ){
            RpcCaller caller = (RpcCaller)msg;
            if ("toString".equals(caller.getMethod()) && (caller.getParams() == null)) return;
            execute(caller);

        }

    }

    @Override
    public RpcCaller execute(RpcCaller caller) {
        try {
            Register register = provider.getRegister(caller.getKlass());
            if (register == null){
                throw new RpcException(String.format("%s can't find provier",caller.getKlass()));
            }
            String klass = register.getIinterface();
            Class clazz = Class.forName(klass);
            Class[] argsClass = null;
            if (caller.getParams() != null){
                argsClass = new Class[caller.getParams().length];
                for (int i = 0,len = caller.getParams().length; i < len; i++) {
                    argsClass[i] = caller.getParams()[i].getClass();
                }
            }
            Method method= clazz.getMethod(caller.getMethod(),argsClass);
            Object result =  method.invoke(register.getRef(),caller.getParams());
            caller.setResult(result);
            return caller;
        }catch (Exception e){
            throw new RpcException(e);
        }
    }


    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }
}
