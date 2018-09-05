
package com.zuicoding.platform.rpc.common;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by <a href="mailto:stephen.linicoding@gmail.com">Stephen.lin</a> on 2018/8/15.
 * <p>
 *
 * </p>
 */
public class DefaultResponse implements Response {

    private static final long serialVersionUID = -8728156546472981669L;


    private boolean hasExcepiton;

    private Object result;

    private Throwable throwable;

    private Map<String, Object> attachments;

    private String id;

    public DefaultResponse(String id) {
        this.id = id;
    }

    public DefaultResponse() {
    }

    @Override
    public Response setId(String id) {
        this.id = id;
        return this;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public boolean hasException() {
        return this.hasExcepiton;
    }

    @Override
    public Object getResult() {
        return this.result;
    }

    @Override
    public Throwable getException() {
        return this.throwable;
    }

    @Override
    public Map<String, Object> getAttachments() {
        return this.attachments;
    }

    @Override
    public Object getAttachment(String key) {
        return this.attachments == null ? null : this.attachments.get(key);
    }

    @Override
    public void setAttachments(Map<String, Object> attachments) {
        this.attachments = attachments;
    }

    @Override
    public Response addAttachment(String name, Object attachemnt) {
        if (this.attachments == null) {
            this.attachments = new HashMap<>();
        }
        this.attachments.put(name, attachemnt);
        return this;
    }
}
