package com.travelsky.ypb.process;


import com.travelsky.ypb.domain.log.Log;
import com.travelsky.ypb.domain.message.Instance;
import com.travelsky.ypb.domain.model.FlightPlan;
import com.travelsky.ypb.domain.support.EventService;
import com.travelsky.ypb.domain.support.ServiceSupport;
import com.travelsky.ypb.model.lowestPriceOfPlan.LowestPrice;
import com.travelsky.ypb.publics.CommonUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * <p>Tilte:事件通知 (按航线)</p>
 * <p>Description:</p>
 *
 * @author huc
 */
@SuppressWarnings("ALL")
@Service
public class Event5501 extends EventService implements ServiceSupport {

    Logger log = Logger.getLogger("5501");

    @Override
    public Instance process(Instance instance) throws JAXBException {

        instance.setFlightDate(CommonUtil.getStrOfDate(instance.getFlightDate()));
        instance.setWeekday(CommonUtil.getDayOfWeek(instance.getFlightDate()));
        instance.setDepartureAirportCn(cityAirportName(instance.getDepartureAirport()));
        instance.setArrivalAirportCn(cityAirportName(instance.getArrivalAirport()));
//		instance.setAirlineCn(getAirlineCN(instance));

        // 查询航线飞行计划
        instance.setpType(appConfig.getpTypeAirline());
        List<FlightPlan> planList = getFlightPlan(instance);
        if (planList.isEmpty()) return instance;
        Map<String, FlightPlan> planMap = listToMap(planList);
        instance.setPlanMap(planMap);
        Log.i(this.getClass(), "getFlightPlan", String.valueOf(planList));

        // 查询seamless
        Iterator<FlightPlan> iterator = planList.iterator();
        while (iterator.hasNext()) {
            instance.setFlightPlan(iterator.next());
            Map seamLess = getSeamLess(instance);
            Log.i(this.getClass(), "seamless", String.valueOf(seamLess));
            instance.setSeamLess(seamLess);
            instance.setCabinTicket(seamLess);
            String ticketCount = String.valueOf(ticketSumForAirline(seamLess));
            Log.i(this.getClass(), "ticketCount", ticketCount);

            // 查询航线最低价
            List<LowestPrice> lowestList = getLowestPrice(instance);
            instance.setLowestPrice(lowestList);
            LowestPrice lowestPrice = getLowestPrice(lowestList);
            instance.setPrice(lowestPrice);

            // 拼装消息体
            String airlineInfo = fmt(instance.getDepartureAirportCn(), appConfig.getBar(), instance.getArrivalAirportCn());
            String cabinInfo = getCabinInfo(instance, lowestPrice, 5507);
            String endInfo = getEndInfo(instance, lowestPrice);

            String messagesBody = messageFormat(String.valueOf(airlineInfo),
                    String.valueOf(cabinInfo), String.valueOf(endInfo), ticketCount, 5501);
            instance.setMessagesBody(messagesBody);
            Log.i(this.getClass(), "messageFormat", messagesBody);
            Map<String, String> params = assembleParamsSpecial(ticketCount, endInfo, cabinInfo, airlineInfo);
            instance.setParams(params);
            instance.setJumpParams(jumpParams());

            // 消息发送
            instance.setMsgTempleId(Long.valueOf(appConfig.getEventType5501()));

            FlightPlan flightPlan = iterator.next();
            instance.setUserId(new String[]{flightPlan.getUserid()});
            instance.setAppid(new String[]{flightPlan.getAppid()});
            instance.setValue(setValue(instance));
            pushServer.push(instance);
        }
        return instance;
    }


}
