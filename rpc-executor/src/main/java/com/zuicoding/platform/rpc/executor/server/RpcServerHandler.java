
package com.zuicoding.platform.rpc.executor.server;

import java.lang.reflect.Method;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zuicoding.platform.rpc.common.exception.RpcException;
import com.zuicoding.platform.rpc.common.utils.CollectionUtils;
import com.zuicoding.platform.rpc.executor.RpcRequest;
import com.zuicoding.platform.rpc.executor.RpcResponse;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by <a href="mailto:stephen.linicoding@gmail.com">stephen.lin</a> on 2018/3/23.
 * <p>
 * <p>
 * </p>
 */
public class RpcServerHandler extends SimpleChannelInboundHandler<RpcRequest> {

    private Logger logger = LoggerFactory.getLogger(RpcServerHandler.class);

    private Map<String,Object> refMap;


    public RpcServerHandler(Map<String,Object> refMap) {
        this.refMap = refMap;
        logger.info(String.valueOf(this));
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcRequest request) throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("request info : {}",request);
        }

        invoke(request);



    }

    private RpcResponse invoke(RpcRequest request) {
        RpcResponse response = new RpcResponse(request.getId());
        String interfaceClass = request.getInterfaceClass();
        String methodName = request.getMethod();
        Object[] parameters = request.getParameters();
        Class[] parameterClasses = null;
        if (CollectionUtils.isNotEmpty(parameters)) {
            parameterClasses = new Class[parameters.length];
            for (int i = 0; i < parameters.length; i++) {
                if (parameters[i] == null) {
                    parameterClasses[i] = null;
                    continue;
                }
                parameterClasses[i] = parameters[i].getClass();
            }
        }

        Object ref = refMap.get(interfaceClass);
        if (ref == null ) {
            response.setException(new RpcException(interfaceClass + " has't registed"));
            return response;
        }
        Class refClass = ref.getClass();
        try {
            Method method = refClass.getDeclaredMethod(methodName,parameterClasses);
            Object result =  method.invoke(ref,parameters);
            response.setResult(result);
            return response;
        }catch ( Exception e) {
            logger.error(e.getMessage(),e);
            response.setException(e);
            return response;
        }


    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        logger.error("server exception",cause);
        ctx.close();
    }
}
