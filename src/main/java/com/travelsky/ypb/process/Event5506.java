package com.travelsky.ypb.process;

import com.travelsky.ypb.domain.log.Log;
import com.travelsky.ypb.domain.message.Instance;
import com.travelsky.ypb.domain.model.FlightPlan;
import com.travelsky.ypb.domain.model.TicketChangePrice;
import com.travelsky.ypb.domain.support.EventService;
import com.travelsky.ypb.domain.support.ServiceSupport;
import com.travelsky.ypb.model.lowestPriceOfPlan.LowestPrice;
import com.travelsky.ypb.publics.CommonUtil;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

/**
 * 降价处理
 * create Event5506 by huc
 * 2017/12/11  下午4:21
 */
@SuppressWarnings("ALL")
@Service
public class Event5506 extends EventService implements ServiceSupport {

    @Override
    public Instance process(final Instance instance){
        instance.setEventType(5506+"");
        Log.i(this.getClass(),"降价处理",instance);

        Log.i(this.getClass(),"process","涨价处理");
        LowestPrice lowestPrice = getLowestPrice(instance.getLowestPrice());
        TicketChangePrice changePrice = instance.getTicketChangePrice();

        Log.i(this.getClass(),"process",changePrice.toString());
        String airlineInfo = fmt(appConfig.getGoodMessages(),instance.getDepartureAirportCn(),appConfig.getBar(),instance.getArrivalAirportCn());
        String endInfo = fmt(appConfig.getCNY(),changePrice.getThisPrice(),appConfig.getDecline(),appConfig.getCNY(),String.valueOf(lowestPrice.getDiscountPrice())
                ,appConfig.getComma(),appConfig.getTicketCountCn(),appConfig.getLeft(),instance.getTicketCountSum(),appConfig.getPage(),appConfig.getPeriod()
                ,appConfig.getChangeMessages(), CommonUtil.getMessageEndInfoTime(appConfig.getMsgTime()));

        instance.setParams(roseFmt(airlineInfo,endInfo));
        instance.setJumpParams(jumpParams());

        List<FlightPlan> flightPlans = instance.getFlightPlanList();
        Iterator<FlightPlan> it = flightPlans.iterator();
        instance.setMsgTempleId(Long.valueOf(appConfig.getEventType5505()));
        while (it.hasNext()){
            FlightPlan flightPlan = it.next();
            instance.setUserId(new String[]{flightPlan.getUserid()});
            instance.setAppid(new String[]{flightPlan.getAppid()});
            instance.setValue(setValue(instance));
            pushServer.push(instance);
        }

        return instance;
    }
}
