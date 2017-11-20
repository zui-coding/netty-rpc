package com.zuicoding.platform.rpc.common.utils;

import com.zuicoding.platform.rpc.common.Constant;
import com.zuicoding.platform.rpc.common.Provider;
import com.zuicoding.platform.rpc.common.RpcRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;

/**
 * Created by Stephen.lin  on 2017/11/20
 *
 * <p>
 * <p></p>
 */
public   class RpcUtils {

    private static Logger logger = LoggerFactory.getLogger(RpcUtils.class);

    public static String getLocalAddress(){
        try {
            return InetAddress.getLocalHost().getHostAddress();
        }catch (Exception e){
            logger.error("",e);
            throw new RuntimeException(e);
        }
    }

    public static String regsteKey(Provider provider){

        return provider.registeKey();
    }
    public static String regsteKey(RpcRequest request){
        StringBuilder builder = new StringBuilder();
        builder.append(Constant.DEFAULT_PROTOCOL)
                .append(Constant.PROTOCOL_SEPARATOR)
                .append(getLocalAddress())
                .append(":")
                //.append()
        ;
        return builder.toString();
    }
}
