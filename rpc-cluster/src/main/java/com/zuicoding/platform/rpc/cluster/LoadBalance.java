
package com.zuicoding.platform.rpc.cluster;

import com.zuicoding.platform.rpc.common.EndPoint;

/**
 * Created by <a href="mailto:stephen.linicoding@gmail.com">stephen.lin</a> on 2018/8/31.
 * <p>
 *
 * </p>
 */
public interface LoadBalance {

    EndPoint select();
}
