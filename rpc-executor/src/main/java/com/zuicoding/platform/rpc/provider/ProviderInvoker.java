package com.zuicoding.platform.rpc.provider;

import com.zuicoding.platform.rpc.RpcInvoker;
import com.zuicoding.platform.rpc.common.Provider;
import com.zuicoding.platform.rpc.common.RpcRequest;
import com.zuicoding.platform.rpc.common.RpcResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * Created by <a href="mailto:stephen.linicoding@gmail.com">Stepn.lin</a> on 2017/10/27
 * @description: <p></p>
 */
public class ProviderInvoker implements RpcInvoker {

    private Logger logger = LoggerFactory.getLogger(ProviderInvoker.class);

    private Provider provider;

    public ProviderInvoker(Provider provider) {
        this.provider = provider;
    }

    @Override
    public RpcResponse invoke(RpcRequest request) {
        RpcResponse response = new RpcResponse();
        try {
            Class clazz = Class.forName(request.getInterfaceName());
            Class[] argClasses = null;
            if (request.getParams() != null){
                Object[] params = request.getParams();
                int len = params.length;
                argClasses = new  Class[len];
                for (int i = 0; i < len; i++) {
                    argClasses[i] = params[i].getClass();
                }
            }
            Method method= clazz.getMethod(request.getMethodName(),argClasses);
            if (method == null){
                return null;
            }
            Object result = method.invoke(this.provider.getImplRef(),request.getParams());
            response.setRequestId(request.getRequestId());
            return response;
        }catch (Exception e){
            response.setException(e);
        }

        return response;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }
}
