
package com.zuicoding.platform.rpc.common.utils;

import java.util.Collection;
import java.util.Map;

/**
 * Created by <a href="mailto:stephen.linicoding@gmail.com">Stephen.lin</a> on 2018/3/23.
 * <p>
 * <p>
 * </p>
 */
public class CollectionUtils {

    private CollectionUtils() {

    }

    public static boolean isEmpty(Collection c) {

        return  c == null || c.isEmpty();
    }

    public static boolean isNotEmpty(Collection c) {

        return !isEmpty(c);
    }

    public static boolean isEmpty(Object[] os) {
        return os == null || os.length == 0;
    }

    public static boolean isNotEmpty(Object[] os) {
        return !isEmpty(os);
    }

    public static boolean isEmpty(Map map) {
        return map == null || map.isEmpty();
    }

    public static boolean isNotEmpty(Map map) {
        return !isEmpty(map);
    }
}
