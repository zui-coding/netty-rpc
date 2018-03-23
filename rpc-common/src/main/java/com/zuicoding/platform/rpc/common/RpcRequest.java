package com.zuicoding.platform.rpc.common;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Map;

/**
 * Created by Stephen.lin on 2017/9/21.
 * <p>
 * Description :<p>rpc传输对象</p>
 */
public class RpcRequest implements Serializable {
    private static final long serialVersionUID = -8681715352602910337L;

    private String requestId;
    //调用的接口
    private String interfaceName;
    //调用的方法
    private String methodName;
    //方法依赖的参数
    private Object[] params;
    //附加参数
    private Map<String, String> attachments;

    public RpcRequest() {
    }

    public RpcRequest(String interfaceName, String methodName, Object[] params) {
        this.interfaceName = interfaceName;
        this.methodName = methodName;
        this.params = params;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }

    public Map<String, String> getAttachments() {
        return attachments;
    }

    public void setAttachments(Map<String, String> attachments) {
        this.attachments = attachments;
    }

    @Override
    public String toString() {
        return "RpcRequest{" +
                "requestId=" + requestId +
                ", interfaceName='" + interfaceName + '\'' +
                ", methodName='" + methodName + '\'' +
                ", params=" + Arrays.toString(params) +
                ", attachments=" + attachments +
                '}';
    }
}

