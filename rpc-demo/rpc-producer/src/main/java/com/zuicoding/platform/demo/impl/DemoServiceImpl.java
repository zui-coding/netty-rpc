package com.zuicoding.platform.demo.impl;

import com.zuicoding.platform.demo.api.IDemoService;

/**
 * Created by Stephen.lin on 2017/9/21.
 * <p>
 * Description :<p></p>
 */
public class DemoServiceImpl  implements IDemoService{
    @Override
    public String sayHell(String name) {
        System.err.println("hello,"+name);
        return "hello,"+name;
    }
}
