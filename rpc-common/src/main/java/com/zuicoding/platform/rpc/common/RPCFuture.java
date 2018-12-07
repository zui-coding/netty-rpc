
package com.zuicoding.platform.rpc.common;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by <a href="mailto:stephen.linicoding@gmail.com">Stephen,lin</a> on 2018-12-07.
 * <p>
 *
 * </p>
 */
public class RPCFuture implements Future {

    public final static int STATE_AWAIT = 0;
    public final static int STATE_SUCCESS = 1;
    public final static int STATE_EXCEPTION = 2;

    private CountDownLatch latch = null;

    private int state;
    public RPCFuture() {
        latch = new CountDownLatch(1);
        state = STATE_AWAIT;
    }


    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }


    @Override
    public boolean isCancelled() {
        return false;
    }


    @Override
    public boolean isDone() {
        return false;
    }


    @Override
    public Object get() throws InterruptedException, ExecutionException {
        latch.await();
        return null;
    }


    @Override
    public Object get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return null;
    }
}
