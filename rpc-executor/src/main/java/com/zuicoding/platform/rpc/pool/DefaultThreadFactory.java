package com.zuicoding.platform.rpc.pool;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Stephen.lin on 2017/11/20
 * <p>
 * <p></p>
 */
public class DefaultThreadFactory implements ThreadFactory {

    private String namePrefix = "";
    private int priority = Thread.NORM_PRIORITY;
    private AtomicLong longer = new AtomicLong(1);
    public DefaultThreadFactory(String namePrefix) {
        this.namePrefix = namePrefix;
    }

    public DefaultThreadFactory(String namePrefix, int priority) {
        this.namePrefix = namePrefix;
        this.priority = priority;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r,namePrefix + "-" + longer.incrementAndGet());
        thread.setPriority(this.priority);
        return thread;
    }
}
