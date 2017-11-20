package com.zuicoding.platform.rpc.protocol;

import com.zuicoding.platform.rpc.common.Provider;
import com.zuicoding.platform.rpc.common.RpcRequest;
import com.zuicoding.platform.rpc.common.utils.RpcUtils;

/**
 * Created by Stephen.lin on 2017/11/20
 * <p>
 * <p></p>
 */
public final class URLUtils extends RpcUtils {

    private URLUtils(){}

    public static URL format(RpcRequest request){
        URL url = new URL();
        url.setQueryStr(request.getInterfaceName());
        if (request.getAttachments() != null){
            url.addParams(request.getAttachments());
        }

        return url;
    }

    public static URL format(Provider provider){
        URL url = new URL();
        url.setQueryStr(provider.getInterfaceName());
        return url;
    }
}
