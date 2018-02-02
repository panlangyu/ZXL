package com.travelsky.ypb.process;


import com.travelsky.ypb.domain.log.Log;
import com.travelsky.ypb.domain.message.Instance;
import com.travelsky.ypb.domain.model.FlightPlan;
import com.travelsky.ypb.domain.support.EventService;
import com.travelsky.ypb.domain.support.ServiceSupport;
import com.travelsky.ypb.model.lowestPriceOfPlan.LowestPrice;
import com.travelsky.ypb.publics.CommonUtil;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.util.List;
import java.util.Map;

/**
 * <p> Tilte:计划订阅通知 (按航线) </p>
 * <p> Description: </p>
 * @author huc
 */
@SuppressWarnings("ALL")
@Service
public class Event5507 extends EventService implements ServiceSupport {

    @Override
    public Instance process(final Instance instance) throws JAXBException {
        // 查询飞行计划
        instance.setEventType(5507+"");
        instance.setpType(appConfig.getpTypeAirline());
        List<FlightPlan> flightPlanList = getFlightPlan(instance);
        if (flightPlanList == null) return instance;
        FlightPlan flightPlan = flightPlanList.get(0);
        Log.i(this.getClass(),"查询飞行计划","getFlightPlan",flightPlan);
        instance.setFlightDate(flightPlan.getFlightdate());
        instance.setDepartureAirport(flightPlan.getDeptcity());
        instance.setArrivalAirport(flightPlan.getDestcity());
        instance.setWeekday(CommonUtil.getDayOfWeek(instance.getFlightDate()));
        instance.setDepartureAirportCn(cityAirportName(instance.getDepartureAirport()));
        instance.setArrivalAirportCn(cityAirportName(instance.getArrivalAirport()));
        instance.setpType(flightPlan.getPtype());
        instance.setFlightNo(flightPlan.getFlightno());
        instance.setAirlineName(flightPlan.getAirlinecodes());
        instance.setAppid(new String[]{flightPlan.getAppid()});
        instance.setUserId(new String[]{flightPlan.getUserid()});
        instance.setTakeoffBegin(flightPlan.getTakeoffbegin());
        instance.setTakeoffEnd(flightPlan.getTakeoffend());
        instance.setFlightPlan(flightPlan);

        // 查询seamless
        Map seamLess = getSeamLess(instance);
        Log.i(this.getClass(),"seamless",String.valueOf(seamLess));
        instance.setSeamLess(seamLess);
        instance.setCabinTicket(seamLess);
        String ticketCount = String.valueOf(ticketSumForAirline(seamLess));
        Log.i(this.getClass(),"ticketCount",ticketCount);

        // 查询最低价
        List<LowestPrice> lowestList = getLowestPrice(instance);
        instance.setLowestPrice(lowestList);
        LowestPrice lowestPrice = getLowestPrice(lowestList);
        instance.setPrice(lowestPrice);

        // 拼装消息
        String airlineInfo = fmt(instance.getDepartureAirportCn(),appConfig.getBar(),instance.getArrivalAirportCn());
        String cabinInfo = getCabinInfo(instance,lowestPrice,5507);
        String endInfo = getEndInfo(instance,lowestPrice);

        String messagesBody = messageFormat(String.valueOf(airlineInfo),
                String.valueOf(cabinInfo),String.valueOf(endInfo),ticketCount,5507);
        instance.setMessagesBody(messagesBody);
        Log.i(this.getClass(),"messageFormat",messagesBody);
        Map<String,String> params = assembleParamsSpecial(ticketCount,endInfo,cabinInfo,airlineInfo);
        instance.setParams(params);
        Map<String, String> jumpParams = jumpParams();
        instance.setJumpParams(jumpParams);

        // 消息发送
        setValue(instance);
        instance.setMsgTempleId(Long.valueOf(appConfig.getEventType5507()));
        instance.setValue(setValue(instance));
        pushServer.push(instance);

        return instance;
    }


}
