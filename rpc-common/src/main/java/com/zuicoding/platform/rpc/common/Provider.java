package com.zuicoding.platform.rpc.common;

import com.zuicoding.platform.rpc.common.utils.RpcUtils;

/**
 * @author Created by Stephen.lin on 2017/9/25.
 * <p>
 * Description :<p></p>
 */
public class Provider {

    private String host = RpcUtils.getLocalAddress();
    private Integer port;
    private String interfaceName;
    //实现类引用
    private Object implRef;

    //
    private String methodName;

    public Provider() {
    }

    public Provider(String host, Integer port, String interfaceName, Object implRef,String methodName) {
        this.host = host;
        this.port = port;
        this.interfaceName = interfaceName;
        this.implRef = implRef;
        this.methodName = methodName;
    }

    public Provider(Integer port, String interfaceName, Object implRef) {
        this.port = port;
        this.interfaceName = interfaceName;
        this.implRef = implRef;
    }

    public Provider(String interfaceName, Object implRef) {
        this.interfaceName = interfaceName;
        this.implRef = implRef;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public Object getImplRef() {
        return implRef;
    }

    public void setImplRef(Object implRef) {
        this.implRef = implRef;
    }

    /**
     * 构建注册中心的key
     *
     * @return
     */
    public String registeKey(){
        return new StringBuilder()
                .append(Constant.DEFAULT_PROTOCOL)
                .append(Constant.PROTOCOL_SEPARATOR)
                .append(this.host)
                .append(this.port)
                .append(Constant.QUERYSTR_SEPARATOR)
                .append(this.interfaceName)
                .append(Constant.FIRST_PARAM_SEPARATOR)
                .append(Constant.IMPL_REF_PARAM_NAME)
                .append(Constant.PARAM_VALUE_SEPARATOR)
                .append(this.implRef)
                .append(Constant.PARAM_SEPARATOR)
                .append(Constant.METHOD_PARAM_NAME)
                .append(Constant.PARAM_VALUE_SEPARATOR)
                .append(this.methodName)
                .toString();
    }

    @Override
    public String toString() {
        return "Provider{" +
                "host='" + host + '\'' +
                ", port=" + port +
                ", interfaceName='" + interfaceName + '\'' +
                ", implRef=" + implRef +
                ", methodName="+methodName+
                '}';
    }

}
