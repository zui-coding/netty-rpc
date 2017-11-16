package com.zuicoding.platform.rpc.consumer;

import com.zuicoding.platform.rpc.common.Constant;
import com.zuicoding.platform.rpc.common.exception.RpcException;
import com.zuicoding.platform.rpc.protocol.URL;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Stephen.lin on 2017/11/16
 * <p>
 * <p>池化客户端</p>
 */
public abstract class AbstractPoolClient implements Client {

    private Logger logger = LoggerFactory.getLogger(AbstractPoolClient.class);

    private URL url;
    protected GenericObjectPool<Client> pool;
    private GenericObjectPoolConfig poolConfig;
    private PooledObjectFactory<Client> pooledObjectFactory;
    public AbstractPoolClient(URL url) {
        if (url == null){
            throw new RpcException("url is null");
        }
        this.url = url;
    }

    protected void  initPool(){
        poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMaxIdle(url.getIntParamVal(Constant.PARAM_MAXIDLE_NAME));
        poolConfig.setMaxTotal(url.getIntParamVal(Constant.PARAM_MAXTOTAL_NAME));
        pooledObjectFactory = createPooledObjectFactory();
        pool = new GenericObjectPool(pooledObjectFactory,poolConfig);
    }

    public abstract PooledObjectFactory<Client> createPooledObjectFactory();
}
