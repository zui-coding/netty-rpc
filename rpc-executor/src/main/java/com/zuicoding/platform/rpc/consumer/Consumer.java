
package com.zuicoding.platform.rpc.consumer;

/**
 * Created by <a href="mailto:stephen.linicoding@gmail.com">Stephen.lin</a> on 2018/3/22.
 * <p>
 * <p>
 * </p>
 */
public class Consumer {

    private String id;
    private String interfaceClass;

    public Consumer() {
    }

    public Consumer(String id, String interfaceClass) {
        this.id = id;
        this.interfaceClass = interfaceClass;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInterfaceClass() {
        return interfaceClass;
    }

    public void setInterfaceClass(String interfaceClass) {
        this.interfaceClass = interfaceClass;
    }
}
