
package com.zuicoding.platform.rpc.common;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by <a href="mailto:stephen.linicoding@gmail.com">stephen.lin</a> on 2018/3/23.
 * <p>
 * <p>
 * </p>
 */
public interface Response extends Serializable {

    Response setId(String id);

    String getId();

    boolean hasException();

    Object getResult();

    Throwable getException();

    Map<String, Object> getAttachments();

    Object getAttachment(String key);

    void setAttachments(Map<String, Object> attachments);

    Response addAttachment(String name, Object attachemnt);
}
