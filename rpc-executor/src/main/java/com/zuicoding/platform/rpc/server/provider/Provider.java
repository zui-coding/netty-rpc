package com.zuicoding.platform.rpc.server.provider;

import com.zuicoding.platform.rpc.registry.Register;

/**
 * Created by Stephen.lin on 2017/9/25.
 * <p>
 * Description :<p></p>
 */
public interface Provider {

    void provide(Register register);
    Register getRegister(String iinterface);
}
