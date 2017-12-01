package com.travelsky.ypb.process;


import com.travelsky.ypb.configuration.InitAirport;
import com.travelsky.ypb.domain.log.Log;
import com.travelsky.ypb.domain.message.Instance;
import com.travelsky.ypb.domain.support.EventService;
import com.travelsky.ypb.domain.support.ServiceSupport;
import com.travelsky.ypb.domain.xml.LowestPrice;
import com.travelsky.ypb.model.airplan.YpbFlightPlan;
import com.travelsky.ypb.publics.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Tilte:计划订阅通知 (按航线)</p>
 * <p>Description:</p>
 * @author huc
 */
@Service
public class Event5507 extends EventService implements ServiceSupport {

    @Override
    public Instance process(final Instance instance) {
        //TODO 查询飞行计划
        YpbFlightPlan flightPlan = (YpbFlightPlan) getFlightPlan(instance).get(0);
        Log.i(this.getClass(),"查询飞行计划","getFlightPlan",flightPlan);
        instance.setWeekday(CommonUtil.getDayOfWeek(flightPlan.getFlightdate()));
        instance.setDepartureAirportCn(InitAirport.airportMap.get(flightPlan.getDeptcity()));
        instance.setArrivalAirportCn(InitAirport.airportMap.get(flightPlan.getDestcity()));
        instance.setFlightDate(flightPlan.getFlightdate());
        instance.setDepartureAirport(flightPlan.getDeptcity());
        instance.setArrivalAirport(flightPlan.getDestcity());
        instance.setpType(flightPlan.getPtype());
        instance.setFlightNo(flightPlan.getFlightno());
        instance.setAirlineName(flightPlan.getAirlinecodes());

        instance.setAppid(new String[]{flightPlan.getAppid()});
        instance.setUserId(new String[]{flightPlan.getUserid()});

        instance.setTakeoffBegin(flightPlan.getTakeoffbegin());
        instance.setTakeoffEnd(flightPlan.getTakeoffend());
        instance.setFlightPlan(flightPlan);


        // TODO 查询seamless
        Map seamLess = getSeamLess(instance);
        Log.i(this.getClass(),"seamless",String.valueOf(seamLess));
        instance.setSeamLess(seamLess);
        instance.setCabinTicket(seamLess);
        int ticketCount = ticketSumForAirline(seamLess);
        Log.i(this.getClass(),"ticketCount",ticketCount);


        // TODO 查询最低价
        List<LowestPrice> lowestList = getLowestPrice(instance);
        instance.setLowestPrice(lowestList);
        LowestPrice lowestPrice = getLowestPrice(lowestList);


        // TODO 拼装消息
        String airlineInfo = fmt(instance.getDepartureAirportCn(),appConfig.getBar(),instance.getArrivalAirportCn());
        StringBuilder cabinInfo = new StringBuilder();
        StringBuilder endInfo = new StringBuilder();
        cabinInfo.append(getCabinInfo(instance,lowestPrice,5507));

        endInfo.append(getEndInfo(instance,lowestPrice));
        Log.i(this.getClass(),"messageFormat",messageFormat(String.valueOf(airlineInfo),
                String.valueOf(cabinInfo),String.valueOf(endInfo),String.valueOf(ticketCount),5507));
        Map<String,String> params = new HashMap<>();
        params.put("ticketCount", String.valueOf(ticketCount));
        params.put("endInfo", String.valueOf(endInfo));
        params.put("airlinedate", String.valueOf(cabinInfo));
        params.put("airline", airlineInfo);
        instance.setParams(params);
        Map<String, String> jumpParams = new HashMap<>();
        jumpParams.put("key", appConfig.getKey());
        instance.setJumpParams(jumpParams);


        // TODO 消息发送
        setValue(instance);
        instance.setMsgTempleId(Long.valueOf(appConfig.getEventType5507()));
        instance.setValue(setValue(instance));
        pushServer.push(instance);

        return instance;
    }
}
