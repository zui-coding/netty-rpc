
package com.zuicoding.platform.rpc.common;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by <a href="mailto:stephen.linicoding@gmail.com">stephen.lin</a> on 2018/9/5.
 * <p>
 *
 * </p>
 */
public class DefaultRpcThreadFactory implements ThreadFactory {

    private AtomicInteger seq = new AtomicInteger();

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
        thread.setName("rpc-thread-" + seq.getAndIncrement());
        return thread;
    }
}
