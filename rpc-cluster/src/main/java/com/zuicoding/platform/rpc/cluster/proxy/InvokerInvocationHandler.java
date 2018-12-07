
package com.zuicoding.platform.rpc.cluster.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zuicoding.platform.rpc.cluster.Invoker;
import com.zuicoding.platform.rpc.executor.server.RpcInvocation;

/**
 * Created by <a href="mailto:stephen.linicoding@gmail.com">Stephen.lin</a> on 2018-12-07.
 * <p>
 *
 * </p>
 */
public class InvokerInvocationHandler implements InvocationHandler {

    private static Logger logger = LoggerFactory.getLogger(InvokerInvocationHandler.class);

    private static final String TO_STRING = "toString";

    private static final String HASH_CODE  = "hashCode";

    private static final String EQUALS = "equals";


    private Invoker<?> invoker;
    private InvokerInvocationHandler(Invoker<?> invoker) {
        this.invoker = invoker;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String methodName = method.getName();

        switch (methodName) {
            case TO_STRING:
                return invoker.toString();

            case HASH_CODE:
                return invoker.hashCode();
            case EQUALS:
                return invoker.equals(args[0]);
                break;
        }

        return invoker.invoke(new RpcInvocation(method, args)).getResult();
    }
}
