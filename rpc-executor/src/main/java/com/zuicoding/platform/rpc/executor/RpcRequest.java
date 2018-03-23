
package com.zuicoding.platform.rpc.executor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.zuicoding.platform.rpc.common.Request;

/**
 * Created by <a href="mailto:stephen.linicoding@gmail.com">Stephen.lin</a> on 2018/3/23.
 * <p>
 * <p>
 * </p>
 */
public class RpcRequest implements Request {

    private String id;
    private String interfaceClass;
    private String method;
    private Object[] parameters;
    private Map<String, Object> attachment;

    public RpcRequest() {
    }

    public RpcRequest(String id, String interfaceClass, String method) {
        this.id = id;
        this.interfaceClass = interfaceClass;
        this.method = method;
    }

    public RpcRequest(String id, String interfaceClass, String method, Object[] parameters) {
        this.id = id;
        this.interfaceClass = interfaceClass;
        this.method = method;
        this.parameters = parameters;
    }

    public RpcRequest(String id, String interfaceClass, String method, Object[] parameters,
                      Map<String, Object> attachment) {
        this.id = id;
        this.interfaceClass = interfaceClass;
        this.method = method;
        this.parameters = parameters;
        this.attachment = attachment;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInterfaceClass() {
        return interfaceClass;
    }

    public void setInterfaceClass(String interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }

    public Map<String, Object> getAttachment() {
        return attachment;
    }

    public void setAttachment(Map<String, Object> attachment) {
        this.attachment = attachment;
    }

    public RpcRequest addAttachment(String name,Object value) {
        if (this.attachment == null) {
            this.attachment = new HashMap<>();
        }
        this.attachment.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        return "RpcRequest{" +
                "id='" + id + '\'' +
                ", interfaceClass='" + interfaceClass + '\'' +
                ", method='" + method + '\'' +
                ", parameters=" + Arrays.toString(parameters) +
                ", attachment=" + attachment +
                '}';
    }
}
