
package com.zuicoding.platform.rpc.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 *
 * @author <a href="mailto:stephen.linicoding@gmail.com">stephen.lin</a>
 * @date 2018-12-07
 * <p>
 *
 * </p>
 */
public class RPCResult implements Serializable {

    private Object result;
    private Throwable cause;
    private Map<String, String> attachments = new HashMap<String, String>();

    public RPCResult() {
    }


    public Object getResult() throws Throwable {
        if (cause != null) {
            throw cause;
        }
        return result;
    }

    public RPCResult setResult(Object result) {
        this.result = result;
        return this;
    }

    public Throwable getCause() {
        return cause;
    }

    public RPCResult setCause(Throwable cause) {
        this.cause = cause;
        return this;
    }

    public Map<String, String> getAttachments() {
        return attachments;
    }

    public void setAttachments(Map<String, String> attachments) {
        this.attachments = attachments;
    }

    public RPCResult addAttachment(String name, String value) {
        this.attachments .put(name, value);
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("result", result)
                .append("cause", cause)
                .append("attachments", attachments)
                .toString();
    }
}
