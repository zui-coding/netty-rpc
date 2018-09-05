
package com.zuicoding.platform.rpc.common;

import java.util.concurrent.ThreadFactory;

/**
 * Created by <a href="mailto:stephen.linicoding@gmail.com">stephen.lin</a> on 2018/9/5.
 * <p>
 *
 * </p>
 */
public class DefaultRpcThreadFactory implements ThreadFactory {

    /**
     * Constructs a new {@code Thread}.  Implementations may also initialize
     * priority, name, daemon status, {@code ThreadGroup}, etc.
     *
     * @param r a runnable to be executed by new thread instance
     *
     * @return constructed thread, or {@code null} if the request to
     * create a thread is rejected
     */
    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
        thread.setName("rpc-thread");
        return thread;
    }
}
