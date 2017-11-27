package com.travelsky.ypb.process;


import com.travelsky.ypb.domain.message.Instance;
import com.travelsky.ypb.domain.service.flightplan.LightStatusSubScService;
import com.travelsky.ypb.domain.support.EventService;
import com.travelsky.ypb.domain.support.ServiceSupport;
import com.travelsky.ypb.domain.xml.CabinTicket;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>Tilte:计划订阅通知 (按航班)</p>
 * <p>Description:</p>
 *
 * @author huc
 */
@Service
public class Event5508 extends EventService implements ServiceSupport {

    private static final Logger log = Logger.getLogger("5508");

    @Override
    public void process(Instance instance) {
        //TODO process code ...
        log.info(instance);


        //TODO 查询飞行计划



        // TODO 查询seamless
        CabinTicket seamLess = (CabinTicket) getSeamLess(instance);
        log.info(seamLess);


        // TODO 查询最低价



        // TODO 拼装消息



        // TODO 消息发送



    }
}
