package com.travelsky.ypb.business.task;

import com.travelsky.ypb.domain.message.Instance;
import com.travelsky.ypb.domain.support.ServiceSupport;
import org.springframework.stereotype.Component;

/**
 * create Tasker by huc
 * 2017/12/11  下午4:25
 */
@SuppressWarnings("ALL")
@Component
public class Tasker<T extends ServiceSupport> implements Runnable{

    T t;

    Instance i;

    public Tasker(){

        super();
    }

    public Tasker(T t,Instance i){
        this.t = t;
        this.i = i;
    }

    @Override
    public void run() {


    }


}
