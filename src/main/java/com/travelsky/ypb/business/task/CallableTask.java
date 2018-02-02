package com.travelsky.ypb.business.task;

import com.travelsky.ypb.domain.message.Instance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.concurrent.Callable;

/**
 * @Author huc
 * @JDK 1.8
 */
@SuppressWarnings("ALL")
@Component
public class CallableTask<T extends Instance> implements Callable<T> {

    @Autowired
    ApplicationContext app;

    T t;

    public CallableTask(T t) {
        this.t = t;
    }

    public CallableTask(){

    }

    @Override
    public T call() {

        return  t;
    }


}
