
package com.zuicoding.platform.rpc.common.utils;

/**
 * Created by <a href="mailto:stephen.linicoding@gamil.com">stephen.lin</a> on 2018/3/23.
 * <p>
 * <p>
 * </p>
 */
public class StringTools {

    public static final String EMPTY = "";

    private StringTools () {

    }

    /**
     * <code>
     *     <li>str == "" return true</li>
     *     <li>str == null return true</li>
     *     <li>str == " " return true</li>
     *
     * </code>
     * @param str
     * @return
     */
    public static boolean isBank(String str) {

        return str == null || str.trim().length() == 0;
    }

    /**
     * @see StringTools#isBank(java.lang.String)
     * @param str
     * @return
     */
    public static boolean isNotBank(String str) {

        return !isBank(str);
    }

    /**
     * <code>
     *     if the value is bank,return defValue
     * </code>
     * @param value
     * @param defVal
     * @return
     */
    public static String getValue(String value,String defVal) {

        return isBank(value) ? defVal : value;
    }

    /**
     * <code>the default value is ""</code>
     * @see StringTools#getValue(java.lang.String, java.lang.String)
     * @param value
     * @return
     */
    public static String getValue(String value) {

        return isBank(value) ? EMPTY : value;
    }

}
