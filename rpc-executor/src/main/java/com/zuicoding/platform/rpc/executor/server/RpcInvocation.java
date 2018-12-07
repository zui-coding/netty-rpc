
package com.zuicoding.platform.rpc.executor.server;

import java.lang.reflect.Method;

/**
 * Created by <a href="mailto:stephen.linicoding@gmail.com">Stephen.lin</a> on 2018-12-06.
 * <p>
 *
 * </p>
 */
public class RpcInvocation {

    private String intefaceName;
    private String methodName;
    private Method method;
    private Class[] argClasses;
    private Object[] args;
    private Object invoker;

    public RpcInvocation() {
    }

    public RpcInvocation(Method method, Object[] args) {
        this(null, method.getName(), method, method.getParameterTypes(), args, null);
    }

    public RpcInvocation(String intefaceName, String methodName, Method method, Class[] argClasses, Object[] args,
                         Object invoker) {
        this.intefaceName = intefaceName;
        this.methodName = methodName;
        this.method = method;
        this.argClasses = argClasses;
        this.args = args;
        this.invoker = invoker;
    }

    public RpcInvocation(Method method, Class[] argClasses, Object[] args) {
        this.method = method;
        this.methodName = method.getName();
        this.argClasses = argClasses;
        this.args = args;
    }

    public RpcInvocation(String intefaceName, String methodName, Class[] argClasses, Object[] args) {
        this.intefaceName = intefaceName;
        this.methodName = methodName;
        this.argClasses = argClasses;
        this.args = args;
    }

    public RpcInvocation(String intefaceName, Method method, Class[] argClasses, Object[] args) {
        this.intefaceName = intefaceName;
        this.method = method;
        this.argClasses = argClasses;
        this.args = args;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public String getIntefaceName() {
        return intefaceName;
    }

    public void setIntefaceName(String intefaceName) {
        this.intefaceName = intefaceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class[] getArgClasses() {
        return argClasses;
    }

    public void setArgClasses(Class[] argClasses) {
        this.argClasses = argClasses;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public Object getInvoker() {
        return invoker;
    }

    public void setInvoker(Object invoker) {
        this.invoker = invoker;
    }

    public Object invoke() throws Exception {
       return method.invoke(invoker, args);
    }


}
