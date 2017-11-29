package com.travelsky.ypb.process;

import com.travelsky.ypb.domain.message.Instance;
import com.travelsky.ypb.domain.support.EventService;
import com.travelsky.ypb.domain.support.ServiceSupport;
import com.travelsky.ypb.model.airplan.YpbFlightPlan;
import com.travelsky.ypb.publics.CommonUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>Tilte:事件通知 (按航班)</p>
 * <p>Description:</p>
 * @author huc
 */
@Service
public class Event5502 extends EventService<Instance> implements ServiceSupport{
    private static final Logger log = Logger.getLogger(Event5502.class);

    @Override
    public Instance process(final Instance instance) {
        //TODO process code ...
        String planDate = CommonUtil.getStrOfDate(instance.getFlightDate());

        // TODO 查询有效飞行计划
        List<YpbFlightPlan> flightPlan = getFlightPlan(instance);
        System.out.println(flightPlan.size());


        // TODO 查询Seamless
        Map seamLess =  getSeamLess(instance);
        instance.setCabinTicket(seamLess);


        //TODO 查询最低价



        // TODO 价格变动记录



        //TODO 拼装消息体



        //TODO 消息发送



        return instance;
    }



}