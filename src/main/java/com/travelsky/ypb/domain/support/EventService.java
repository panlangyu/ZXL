package com.travelsky.ypb.domain.support;

import com.alibaba.fastjson.JSONObject;
import com.travelsky.ypb.domain.message.Instance;
import com.travelsky.ypb.domain.service.flightplan.LightStatusSubScService;
import com.travelsky.ypb.publics.CommonUtil;
import com.travelsky.ypb.umeticket.domain.response.TicketResponse;
import com.travelsky.ypb.umeticket.util.imp.TicketByAirlineUtil;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by huc on 2017/11/22.
 * 公共方法
 */
@Component
public abstract class EventService <T extends Instance>{

    /**
     * 公共获取Seamless余票信息
     * @param t Instance
     * @return  Map
     */
    protected Map getSeamLess(T t){
        JSONObject json = new JSONObject();
        json.put("dept",t.getDepartureAirport());
        json.put("dest",t.getArrivalAirport());
        json.put("deptDate", CommonUtil.getStrOfDate(t.getFlightDate()));
        json.put("flightNo",t.getFlightNo());
        json.put("airline",t.getAirlineName());
        TicketResponse response = TicketByAirlineUtil.getTicketByFlight(String.valueOf(json),TicketResponse.class);
        if (response.getAirlineAll() == null)
            return response.getFlightAll();
        else
            return response.getAirlineAll();
    }




}
