
package com.zuicoding.platform.rpc.common;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by <a href="mailto:stephen.linicoding@gmail.com">Stephen.lin</a> on 2018/8/15.
 * <p>
 *
 * </p>
 * @author Stephen.lin
 */
public class DefaultRequest implements Request {

    private static final long serialVersionUID = -5524184375692631304L;

    private long requestId;

    private String interfaceName;

    private String methodName;

    private Object[] arguments;

    private Class[] parameterClasses;

    private Map<String, String> attachments;

    public Request setRequestId(long requestId) {
        this.requestId = requestId;

        return this;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public Request setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
        return this;
    }

    public String getMethodName() {
        return methodName;
    }

    public Request setMethodName(String methodName) {
        this.methodName = methodName;

        return this;
    }

    public Object[] getArguments() {
        return arguments;
    }

    public Request setArguments(Object[] arguments) {
        this.arguments = arguments;
        return this;
    }

    public Map<String, String> getAttachments() {
        return attachments;
    }

    public Request setAttachments(Map<String, String> attachments) {
        this.attachments = attachments;

        return this;
    }

    public Request addAttachment(String name, String value) {
        if (this.attachments == null) {
            this.attachments = new HashMap<>();
        }
        this.attachments.put(name, value);

        return this;
    }

    public Request addAttachment(String name, Integer value) {
        if (this.attachments == null) {
            this.attachments = new HashMap<>();
        }
        this.attachments.put(name, value == null ? "": String.valueOf(value));
        return this;
    }

    public Request addAttachment(String name, Long value) {
        if (this.attachments == null) {
            this.attachments = new HashMap<>();
        }
        this.attachments.put(name, value == null ? "": String.valueOf(value));
        return this;
    }

    @Override
    public Class[] getParameterClasses() {
        return parameterClasses;
    }

    @Override
    public Request setParameterClasses(Class[] parameterClasses) {
        this.parameterClasses = parameterClasses;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("requestId", requestId)
                .append("interfaceName", interfaceName)
                .append("methodName", methodName)
                .append("arguments", arguments)
                .append("parameterClasses", parameterClasses)
                .append("attachments", attachments)
                .toString();
    }

    public long getRequestId() {
        return requestId;
    }
}
