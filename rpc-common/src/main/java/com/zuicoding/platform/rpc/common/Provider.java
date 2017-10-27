package com.zuicoding.platform.rpc.common;

/**
 * @author Created by Stephen.lin on 2017/9/25.
 * <p>
 * Description :<p></p>
 */
public class Provider {

    private String interfaceClass;
    private Object ref;



    public String getInterfaceClass() {
        return interfaceClass;
    }

    public void setInterfaceClass(String interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    public Object getRef() {
        return ref;
    }

    public void setRef(Object ref) {
        this.ref = ref;
    }
}
