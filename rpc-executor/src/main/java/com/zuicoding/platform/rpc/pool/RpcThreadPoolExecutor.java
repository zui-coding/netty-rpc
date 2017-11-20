package com.zuicoding.platform.rpc.pool;

import java.util.concurrent.*;

/**
 * Created by Stephen.lin on 2017/11/20
 * <p>
 * <p></p>
 */
public class RpcThreadPoolExecutor extends ThreadPoolExecutor {

    private static final int DEFAULT_MIN_POOL_SIZE = 20;
    private static final int default_max_pool_size = 60;
    public static final long DEFAULT_MAX_IDLE_TIME = 60 * 1000;

    public RpcThreadPoolExecutor(int corePoolSize,
                                 int maximumPoolSize,
                                 long keepAliveTime,
                                 TimeUnit unit,
                                 BlockingQueue<Runnable> workQueue,
                                 ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    public RpcThreadPoolExecutor(int minPoolSize,int maxPoolSzie,
                                 ThreadFactory threadFactory){
        super(minPoolSize,maxPoolSzie,DEFAULT_MAX_IDLE_TIME,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingDeque<Runnable>(),
                threadFactory);
    }
}
