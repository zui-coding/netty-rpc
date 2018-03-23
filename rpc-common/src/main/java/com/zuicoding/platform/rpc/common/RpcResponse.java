package com.zuicoding.platform.rpc.common;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by <a href="mailto:linyajun@yixia.com">林亚军</a> on 2017/11/16
 * <p>
 * <p></p>
 *
 */
public class RpcResponse implements Serializable {
    private static final long serialVersionUID = -6930609441560036391L;

    private Object result;
    private Exception exception;
    private String requestId;
    private long processTime;
    private int timeout;
    private Map<String, String> attachments;

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public long getProcessTime() {
        return processTime;
    }

    public void setProcessTime(long processTime) {
        this.processTime = processTime;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public Map<String, String> getAttachments() {
        return attachments;
    }

    public void setAttachments(Map<String, String> attachments) {
        this.attachments = attachments;
    }

    @Override
    public String toString() {
        return "RpcResponse{" +
                "value=" + result +
                ", exception=" + exception +
                ", requestId=" + requestId +
                ", processTime=" + processTime +
                ", timeout=" + timeout +
                ", attachments=" + attachments +
                '}';
    }
}
