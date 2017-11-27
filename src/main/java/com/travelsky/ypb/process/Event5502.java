package com.travelsky.ypb.process;

import com.travelsky.ypb.domain.message.Instance;
import com.travelsky.ypb.domain.support.EventService;
import com.travelsky.ypb.domain.support.ServiceSupport;
import com.travelsky.ypb.domain.xml.CabinTicket;
import com.travelsky.ypb.publics.CommonUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>Tilte:事件通知 (按航班)</p>
 * <p>Description:</p>
 * @author huc
 */
@Service()
public class Event5502 extends EventService<Instance> implements ServiceSupport{
    private static final Logger log = Logger.getLogger(Event5502.class);

    @Override
    public void process(final Instance instance) {
        //TODO process code ...
        log.info(String.valueOf(instance));
        String planDate = CommonUtil.getStrOfDate(instance.getFlightDate());
        System.out.println(planDate);
        // TODO 查询有效飞行计划



        // TODO 查询Seamless
        Map seamLess = getSeamLess(instance);
        System.out.println(seamLess);


        //TODO 查询最低价



        // TODO 价格变动记录



        //TODO 拼装消息体



        //TODO 消息发送



    }



}