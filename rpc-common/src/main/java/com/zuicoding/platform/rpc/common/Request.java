
package com.zuicoding.platform.rpc.common;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by <a href="mailto:stephen.linicoding@gmail.com">Stephen.lin</a> on 2018/3/23.
 * <p>
 * <p>
 * </p>
 */
public interface Request extends Serializable {


    long getRequestId();

    Request setRequestId(long requestId);

    Request setArguments(Object[] arguments);
    Object[] getArguments();
    Request setInterfaceName(String interfaceName);
    String getInterfaceName();

    String getMethodName();

    Map<String, String> getAttachments();

    Request  setAttachments(Map<String, String> attachments);

    Request addAttachment(String name, String object);

    Class[] getParameterClasses();

    Request setParameterClasses(Class[] parameterClasses);
}
