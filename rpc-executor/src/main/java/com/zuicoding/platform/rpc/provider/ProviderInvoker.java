package com.zuicoding.platform.rpc.provider;

import com.zuicoding.platform.rpc.RpcInvoker;
import com.zuicoding.platform.rpc.common.Provider;
import com.zuicoding.platform.rpc.common.RpcMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * @author : Created by <a href="mailto:stephen.linicoding@gmail.com">Stepn.lin</a> on 2017/10/27
 * @description: <p></p>
 */
public class ProviderInvoker implements RpcInvoker {

    private Logger logger = LoggerFactory.getLogger(ProviderInvoker.class);

    private Provider provider;

    public ProviderInvoker(Provider provider) {
        this.provider = provider;
    }

    @Override
    public Object invoke(RpcMessage message) {
        try {
            Class clazz = Class.forName(message.getInterfaceClass());
            Class[] argClasses = null;
            if (message.getParams() != null){
                Object[] params = message.getParams();
                int len = params.length;
                argClasses = new  Class[len];
                for (int i = 0; i < len; i++) {
                    argClasses[i] = params[i].getClass();
                }
            }
            Method method= clazz.getMethod(message.getMethod(),argClasses);
            Object result = method.invoke(this.provider.getRef(),message.getParams());
            message.setResult(result);
        }catch (Exception e){

        }

        return null;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }
}
