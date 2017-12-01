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
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * <p>Tilte:事件通知 (按航班)</p>
 * <p>Description:</p>
 * @author huc
 */
@Service
public class Event5502 extends EventService<Instance> implements ServiceSupport<Instance>{

    @Override
    public Instance process(Instance instance) {
        // TODO 查询有效飞行计划
        instance.setDepartureAirportCn(InitAirport.airportMap.get(instance.getDepartureAirport()));
        instance.setArrivalAirportCn(InitAirport.airportMap.get(instance.getArrivalAirport()));
        instance.setAirlineCn(InitAirport.aou.get(instance.getAirlineName()));

        instance.setFlightDate(CommonUtil.getStrOfDate(instance.getFlightDate()));
        List<YpbFlightPlan> flightPlanList =  getFlightPlan(instance);
        instance.setWeekday(CommonUtil.getDayOfWeek(instance.getFlightDate()));
        //TODO 获取公共数据
        YpbFlightPlan plan = flightPlanList.get(0);
        instance.setTakeoffBegin(plan.getTakeoffbegin());
        instance.setTakeoffEnd(plan.getTakeoffend());
        instance.setpType(plan.getPtype());

        Map<String,YpbFlightPlan> planMap = listToMap(flightPlanList);
        instance.setPlanMap(planMap);
        Log.i(this.getClass(),"getFlightPlan", String.valueOf(planMap));


        // TODO 查询Seamless
        Map seamLess =  getSeamLess(instance);
        instance.setCabinTicket(seamLess);
        instance.setSeamLess(seamLess);
        CabinTicket cabin = CommonUtil.mapToObject(seamLess,CabinTicket.class);
        String ticketCount = String.valueOf(ticketSum(cabin));
        Log.i(this.getClass(),"seamLess",seamLess);
        instance.setTicketCount(ticketCount);

        //TODO 查询最低价
        List<LowestPrice> lowestList = getLowestPrice(instance);
        instance.setLowestPrice(lowestList);
        LowestPrice lowestPrice = getLowestPrice(lowestList);


        // TODO 价格变动记录



        //TODO 是否抢票



        //TODO 拼装消息体
        String airlineInfo = fmt(InitAirport.aou.get(instance.getAirlineName()),instance.getFlightNo());
        StringBuilder cabinInfo = new StringBuilder();
        StringBuilder endInfo = new StringBuilder();
        cabinInfo.append(getCabinInfo(instance,lowestPrice,5502));
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


        //TODO 消息发送
        instance.setMsgTempleId(Long.valueOf(appConfig.getEventType5502()));
        Iterator<YpbFlightPlan> iterator = flightPlanList.iterator();
        while (iterator.hasNext()){
            YpbFlightPlan flightPlan = iterator.next();
            instance.setUserId(new String[]{flightPlan.getUserid()});
            instance.setAppid(new String[]{flightPlan.getAppid()});
            instance.setValue(setValue(instance));
            pushServer.push(instance);
        }


        return instance;
    }



}