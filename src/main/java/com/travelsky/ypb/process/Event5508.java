package com.travelsky.ypb.process;


import com.travelsky.ypb.configuration.InitAirport;
import com.travelsky.ypb.domain.log.Log;
import com.travelsky.ypb.domain.message.Instance;
import com.travelsky.ypb.domain.support.EventService;
import com.travelsky.ypb.domain.support.ServiceSupport;
import com.travelsky.ypb.domain.xml.CabinTicket;
import com.travelsky.ypb.domain.xml.LowestPrice;
import com.travelsky.ypb.model.airplan.YpbFlightPlan;
import com.travelsky.ypb.publics.CommonUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Tilte:计划订阅通知 (按航班)</p>
 * <p>Description:</p>
 * @author huc
 */
@Service
public class Event5508 extends EventService implements ServiceSupport {

    @Override
    public Instance process(Instance instance) {
        //TODO 查询飞行计划
        YpbFlightPlan flightPlan = (YpbFlightPlan) getFlightPlan(instance).get(0);
        Log.i(this.getClass(),"查询飞行计划","getFlightPlan",flightPlan);
        instance.setWeekday(CommonUtil.getDayOfWeek(flightPlan.getFlightdate()));
        instance.setDepartureAirportCn(InitAirport.airportMap.get(flightPlan.getDeptcity()));
        instance.setArrivalAirportCn(InitAirport.airportMap.get(flightPlan.getDestcity()));
        instance.setAirlineCn(InitAirport.aou.get(flightPlan.getAirlinecodes()));
        instance.setFlightDate(flightPlan.getFlightdate());
        instance.setDepartureAirport(flightPlan.getDeptcity());
        instance.setArrivalAirport(flightPlan.getDestcity());
        instance.setFlightNo(flightPlan.getFlightno());
        instance.setAirlineName(flightPlan.getAirlinecodes());
        instance.setAppid(new String[]{flightPlan.getAppid()});
        instance.setUserId(new String[]{flightPlan.getUserid()});
        instance.setTakeoffBegin(flightPlan.getTakeoffbegin());
        instance.setTakeoffEnd(flightPlan.getTakeoffend());
        instance.setFlightPlan(flightPlan);
        instance.setpType(flightPlan.getPtype());

        // TODO 查询seamless
        Map<String,String> seamLess = getSeamLess(instance);
        CabinTicket cabin = CommonUtil.mapToObject(seamLess,CabinTicket.class);
        String ticketCount = String.valueOf(ticketSum(cabin));
        instance.setCabinTicket(seamLess);
        instance.setSeamLess(seamLess);
        Log.i(this.getClass(),"seamLess",seamLess);
        instance.setTicketCount(ticketCount);

        // TODO 查询最低价
        List<LowestPrice> lowestList = getLowestPrice(instance);
        instance.setLowestPrice(lowestList);
        LowestPrice lowestPrice = getLowestPrice(lowestList);

        // TODO 拼装消息
        String airlineInfo = fmt(InitAirport.aou.get(instance.getAirlineName()),instance.getFlightNo());
        StringBuilder cabinInfo = new StringBuilder();
        StringBuilder endInfo = new StringBuilder();
        cabinInfo.append(getCabinInfo(instance,lowestPrice,5508));

        endInfo.append(getEndInfo(instance,lowestPrice));
        Log.i(this.getClass(),"messageFormat",messageFormat(String.valueOf(airlineInfo),
                String.valueOf(cabinInfo),String.valueOf(endInfo),ticketCount,5508));
        Map<String,String> params = new HashMap<>();
        params.put("ticketCount", ticketCount);
        params.put("endInfo", String.valueOf(endInfo));
        params.put("cabinInfo", String.valueOf(cabinInfo));
        params.put("airlineInfo", airlineInfo);
        instance.setParams(params);
        Map<String, String> jumpParams = new HashMap<>();
        jumpParams.put("key", appConfig.getKey());
        instance.setJumpParams(jumpParams);

        // TODO 消息发送
        instance.setMsgTempleId(Long.valueOf(appConfig.getEventType5508()));
        instance.setValue(setValue(instance));
        pushServer.push(instance);

        return instance;
    }
}
