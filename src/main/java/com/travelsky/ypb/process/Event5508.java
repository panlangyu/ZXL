package com.travelsky.ypb.process;


import com.travelsky.ypb.domain.log.Log;
import com.travelsky.ypb.domain.message.Instance;
import com.travelsky.ypb.domain.model.FlightPlan;
import com.travelsky.ypb.domain.support.EventService;
import com.travelsky.ypb.domain.support.ServiceSupport;
import com.travelsky.ypb.domain.xml.CabinTicket;
import com.travelsky.ypb.model.lowestPriceOfPlan.LowestPrice;
import com.travelsky.ypb.publics.CommonUtil;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.util.List;
import java.util.Map;

/**
 * <p>Tilte:计划订阅通知 (按航班)</p>
 * <p>Description:</p>
 * @author huc
 */
@SuppressWarnings("ALL")
@Service
public class Event5508 extends EventService implements ServiceSupport {

    @Override
    public Instance process(Instance instance) throws JAXBException {
        // 查询飞行计划
        instance.setEventType(5508+"");
        instance.setpType(appConfig.getpTypeFlight());
        List<FlightPlan> flightPlanList =  getFlightPlan(instance);
        if (flightPlanList.isEmpty()) return instance;
        FlightPlan flightPlan = flightPlanList.get(0);
        Log.i(this.getClass(),"查询飞行计划","getFlightPlan",flightPlan);

        instance.setFlightDate(flightPlan.getFlightdate());
        instance.setDepartureAirport(flightPlan.getDeptcity());
        instance.setArrivalAirport(flightPlan.getDestcity());
        instance.setFlightNo(flightPlan.getFlightno());
        instance.setAirlineName(flightPlan.getAirlinecodes());
        instance.setAppid(new String[]{flightPlan.getAppid()});
        instance.setUserId(new String[]{flightPlan.getUserid()});
        instance.setTakeoffBegin(flightPlan.getTakeoffbegin());
        instance.setTakeoffEnd(flightPlan.getTakeoffend());

        instance.setWeekday(CommonUtil.getDayOfWeek(instance.getFlightDate()));
        instance.setDepartureAirportCn(cityAirportName(instance.getDepartureAirport()));
        instance.setArrivalAirportCn(cityAirportName(instance.getArrivalAirport()));
        instance.setAirlineCn(getAirlineCN(instance));
        instance.setFlightPlan(flightPlan);

        // 查询seamless
        Log.i(this.getClass(),"查询seamless开始");
        Map<String,String> seamLess = getSeamLess(instance);
        Log.i(this.getClass(),"查询seamless结束");
        CabinTicket cabin = CommonUtil.mapToObject(seamLess,CabinTicket.class);
        String ticketCount = String.valueOf(ticketSum(cabin));
        instance.setCabinTicket(seamLess);
        instance.setSeamLess(seamLess);
        Log.i(this.getClass(),"seamLess",seamLess);
        instance.setTicketCount(ticketCount);

        // 查询最低价
        Log.i(this.getClass(),"查询最低价开始");
        List<LowestPrice> lowestList = getLowestPrice(instance);
        Log.i(this.getClass(),"查询最低价结束");
        instance.setLowestPrice(lowestList);
        LowestPrice lowestPrice = getLowestPrice(lowestList);
        instance.setPrice(lowestPrice);

        // 拼装消息
        String airlineInfo = fmt(getAirlineCN(instance),instance.getFlightNo());
        String cabinInfo = getCabinInfo(instance,lowestPrice,5508);
        String endInfo = getEndInfo(instance,lowestPrice);
        String messagesBody = messageFormat(String.valueOf(airlineInfo),
                String.valueOf(cabinInfo),String.valueOf(endInfo),ticketCount,5508);
        instance.setMessagesBody(messagesBody);
        Log.i(this.getClass(),"messageFormat",messagesBody);
        Map<String,String> params = assembleParams(ticketCount,endInfo,cabinInfo,airlineInfo);
        instance.setParams(params);
        instance.setJumpParams(jumpParams());

        // 消息发送
        instance.setMsgTempleId(Long.valueOf(appConfig.getEventType5508()));
        instance.setValue(setValue(instance));
        pushServer.push(instance);

        return instance;
    }


}
