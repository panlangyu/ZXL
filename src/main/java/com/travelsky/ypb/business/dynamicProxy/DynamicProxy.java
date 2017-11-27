package com.travelsky.ypb.business.dynamicProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by huc on 2017/11/26.
 * 对接口进行代理
 */
public class DynamicProxy implements InvocationHandler{

    Object target;

    public Object newProxyInstance(Object target) {
        this.target = target;
        return  Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return method.invoke(target, args);
    }


}
