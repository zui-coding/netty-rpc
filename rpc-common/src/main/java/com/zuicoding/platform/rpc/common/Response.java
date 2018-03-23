
package com.zuicoding.platform.rpc.common;

import java.io.Serializable;

/**
 * Created by <a href="mailto:stephen.linicoding@gmail.com">stephen.lin</a> on 2018/3/23.
 * <p>
 * <p>
 * </p>
 */
public interface Response extends Serializable {

    void setId(String id);

    String getId();

}
