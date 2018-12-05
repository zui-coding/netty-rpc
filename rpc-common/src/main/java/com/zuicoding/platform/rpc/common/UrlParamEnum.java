
package com.zuicoding.platform.rpc.common;

/**
 * Created by <a href="mailto:stephen.linicoding@gmail.com">stephen.lin</a> on 2018/9/10.
 * <p>
 *
 * </p>
 */
public enum  UrlParamEnum {


    VERSION("version", Constants.DEFAULT_VERSION),
    TIMEOUT("timeout", Constants.DEFAULT_TIMEOUT);


    private String name;
    private String value;
    private long longValue;
    private int intValue;

    UrlParamEnum(String name, String value) {
        this.name = name;
        this.value = value;
    }

    UrlParamEnum(String name, long longValue) {
        this.name = name;
        this.longValue = longValue;
    }

    UrlParamEnum(String name, int intValue) {
        this.name = name;
        this.intValue = intValue;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public long getLongValue() {
        return longValue;
    }

    public int getIntValue() {
        return intValue;
    }
}
