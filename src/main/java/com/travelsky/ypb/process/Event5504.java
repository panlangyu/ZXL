package com.travelsky.ypb.process;

import com.travelsky.ypb.domain.message.Instance;
import com.travelsky.ypb.domain.support.EventService;
import com.travelsky.ypb.domain.support.ServiceSupport;
import org.springframework.stereotype.Service;

/**
 * Created by huc on 2017/11/27.
 * 涨价处理
 */
@Service
public class Event5504 extends EventService implements ServiceSupport {


    @Override
    public Instance process(Instance instance) {
        //TODO process code...



        return instance;
    }


}
