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
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * <p>Tilte:事件通知 (按航班)</p>
 * <p>Description:</p>
 *
 * @author huc
 */
@SuppressWarnings("ALL")
@Service
public class Event5502 extends EventService<Instance> implements ServiceSupport<Instance> {

    @Override
    public Instance process(Instance instance) throws JAXBException {

        // 通知抢票
        //grabVotes(instance);
        // 查询有效飞行计划
        instance.setDepartureAirportCn(cityAirportName(instance.getDepartureAirport()));
        instance.setArrivalAirportCn(cityAirportName(instance.getArrivalAirport()));
        instance.setAirlineCn(getAirlineCN(instance));
        instance.setFlightDate(CommonUtil.getStrOfDate(instance.getFlightDate()));
        instance.setWeekday(CommonUtil.getDayOfWeek(instance.getFlightDate()));
        List<FlightPlan> flightPlanList = getFlightPlan(instance);
        if (flightPlanList.isEmpty()) return instance;
        //  获取公共数据
        instance.setFlightPlanList(flightPlanList);

        Map<String, FlightPlan> planMap = listToMap(flightPlanList);
        instance.setPlanMap(planMap);
        Log.i(this.getClass(), "getFlightPlan", String.valueOf(planMap));

        //  查询Seamless
        Iterator<FlightPlan> iterator = flightPlanList.iterator();
        while (iterator.hasNext()) {
            FlightPlan plan1 = iterator.next();
            instance.setTakeoffBegin(plan1.getTakeoffbegin());
            instance.setTakeoffEnd(plan1.getTakeoffend());
            instance.setShareFlightNo(plan1.getCodeShare());
            instance.setFlightPlan(plan1);
            instance.setpType(plan1.getPtype());
            Map seamLess = getSeamLess(instance);
            instance.setCabinTicket(seamLess);
            instance.setSeamLess(seamLess);
            CabinTicket cabin = CommonUtil.mapToObject(seamLess, CabinTicket.class);
            String ticketCountSum = String.valueOf(ticketSum(cabin));
            String ticketCount = String.valueOf(cabin.getY());
            Log.i(this.getClass(), "seamLess", seamLess);
            instance.setTicketCountSum(ticketCountSum);
            instance.setTicketCount(ticketCount);

            // 查询最低价
            List<LowestPrice> lowestList = getLowestPrice(instance);
            instance.setLowestPrice(lowestList);
            LowestPrice lowestPrice = getLowestPrice(lowestList);
            instance.setPrice(lowestPrice);

            //  价格变动记录
            paser(instance);

            //  拼装消息体
            String airlineInfo = fmt(getAirlineCN(instance), instance.getFlightNo());
            String cabinInfo = getCabinInfo(instance, lowestPrice, 5502);
            String endInfo = getEndInfo(instance, lowestPrice);
            String messagesBody = messageFormat(String.valueOf(airlineInfo),
                    String.valueOf(cabinInfo), String.valueOf(endInfo), ticketCount, 5502);
            instance.setMessagesBody(messagesBody);
            Log.i(this.getClass(), "messageFormat", messagesBody);
            Map<String, String> params = assembleParams(ticketCount, endInfo, cabinInfo, airlineInfo);
            instance.setParams(params);
            instance.setJumpParams(jumpParams());

            // 更新飞行计划
            updateFlightPlan(instance);

            // 消息发送
            instance.setMsgTempleId(Long.valueOf(appConfig.getEventType5502()));
            instance.setUserId(new String[]{plan1.getUserid()});
            instance.setAppid(new String[]{plan1.getAppid()});
            instance.setValue(setValue(instance));
            pushServer.push(instance);
        }

        return instance;
    }

}