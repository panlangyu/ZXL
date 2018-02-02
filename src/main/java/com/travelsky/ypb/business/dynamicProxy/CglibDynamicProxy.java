package com.travelsky.ypb.business.dynamicProxy;

import com.travelsky.ypb.domain.message.Instance;
import com.travelsky.ypb.domain.support.ServiceSupport;
import com.travelsky.ypb.process.Event5501;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import javax.xml.bind.JAXBException;
import java.lang.reflect.Method;

/**
 * Created by huc on 2017/11/26.
 * 对继承的类进行代理，用final修饰的类除外，被final修饰不能被继承
 */
public class CglibDynamicProxy implements MethodInterceptor{

    private Enhancer enhancer = new Enhancer();

    public Object newProxyInstance(Class clazz){
        //设置需要创建子类的类
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(this);
        //通过字节码技术动态创建子类实例
        return enhancer.create();
    }
    //实现MethodInterceptor接口方法
    @Override
    public Object intercept(Object obj, Method method, Object[] args,MethodProxy proxy) throws Throwable {
        return proxy.invokeSuper(obj, args);
    }

    public static void main(String[] args) throws JAXBException {
        ServiceSupport proxy = (ServiceSupport)new CglibDynamicProxy().newProxyInstance(Event5501.class);
        proxy.process(new Instance());
    }


}
