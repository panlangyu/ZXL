package com.travelsky.ypb.domain.support;

import com.alibaba.fastjson.JSON;
import com.travelsky.ypb.business.service.impl.PushServiceImpl;
import com.travelsky.ypb.domain.message.Instance;
import com.travelsky.ypb.domain.model.FlightPlan;
import com.travelsky.ypb.domain.service.TicketChangePriceService;
import com.travelsky.ypb.domain.xml.CabinTicket;
import com.travelsky.ypb.model.lowestPriceOfPlan.LowestPrice;
import com.travelsky.ypb.publics.CommonUtil;
import org.apache.commons.httpclient.NameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by huc on 2017/11/22.
 * 公共方法
 */
@SuppressWarnings("ALL")
@Component
public abstract class EventService<T extends Instance> extends Support<Instance> {

    @Autowired
    protected PushServiceImpl pushServer;

    @Autowired
    protected TicketChangePriceService changePriceService;

    protected Map<String, FlightPlan> listToMap(List<FlightPlan> list) {
        Map<String, FlightPlan> planMap = new HashMap<>();
        for (FlightPlan plan : list) {
            planMap.put(plan.getUserid(), plan);
        }
        return planMap;
    }

    protected int ticketSumForAirline(Map<String, Map<String, CabinTicket>> map) {
        int sum = 0;
        for (String key : map.keySet()) {
            CabinTicket ticket = CommonUtil.mapToObject(map.get(key), CabinTicket.class);
            sum += ticket.getF() + ticket.getC() + ticket.getY();
        }
        return sum;
    }

    protected String fmt(String... args) {
        StringBuilder builder = new StringBuilder();
        if (null != args) {
            for (int i = 0; i < args.length; i++) {
                builder.append(args[i]);
            }
        }
        return String.valueOf(builder);
    }

    /**
     * 消息模板拼装
     * @param airlineInfo a
     * @param cabinInfo   c
     * @param endInfo     e
     * @return str
     */
    protected String messageFormat(String airlineInfo, String cabinInfo, String endInfo, String ticketCount, int type) {
        switch (type) {
            case 5508:
                return fmt(airlineInfo, appConfig.getOf(), appConfig.getFlight(), appConfig.getComma()
                        , cabinInfo, appConfig.getHas(), ticketCount, appConfig.getPage(), endInfo);
            case 5502:
                return fmt(airlineInfo, appConfig.getOf(), appConfig.getFlight(), appConfig.getComma()
                        , cabinInfo, appConfig.getHas(), ticketCount, appConfig.getPage(), endInfo);
            case 5507:
                return fmt(airlineInfo, appConfig.getOf(), appConfig.getAirline(), appConfig.getComma()
                        , cabinInfo, appConfig.getHas(), ticketCount, appConfig.getPage(), endInfo);
            case 5501:
                return fmt(airlineInfo, appConfig.getOf(), appConfig.getAirline(), appConfig.getComma()
                        , cabinInfo, appConfig.getHas(), ticketCount, appConfig.getPage(), endInfo);
            default:
                return null;
        }
    }

    protected LowestPrice getLowestPrice(List<LowestPrice> lowest) {
        LowestPrice lowestPrice = new LowestPrice();
        if (lowest != null) {
            Iterator<LowestPrice> iterator = lowest.iterator();
            while (iterator.hasNext()) {
                LowestPrice price = iterator.next();
                if ("Y".equals(price.getCabin())) {
                    lowestPrice = price;
                    break;
                }
                if ("C".equals(price.getCabin())) {
                    lowestPrice = price;
                    break;
                }
                if ("F".equals(price.getCabin())) {
                    lowestPrice = price;
                    break;
                }
            }
        }
        return lowestPrice;
    }

    /**
     * t
     *
     * @param t t
     * @return t
     */
    protected String getCabinInfo(T t, LowestPrice lowestPrice, int eventType) {
        String head = (eventType == 5502 ? appConfig.getEconomyClass() : appConfig.getTicketCountCn());
        String takeOff = ("0".equals(t.getpType()) ? lowestPrice.getDeptm().substring(0, 2)
                + appConfig.getColon() + lowestPrice.getDeptm().substring(3, 4) : t.getTakeoffBegin());
        return fmt(appConfig.getParenthesesCodeLeft(), t.getFlightDate(), t.getWeekday(),
                CommonUtil.getCfStr(takeOff), appConfig.getSetOff(), appConfig.getSpace(), t.getDepartureAirportCn(),
                appConfig.getBar(), t.getArrivalAirportCn(), appConfig.getParenthesesCodeRight(), appConfig.getComma(),
                head);
    }

    /**
     * @param t           t
     * @param lowestPrice l
     * @return t
     */
    protected String getEndInfo(T t, LowestPrice lowestPrice) {
        String classTicket = String.valueOf(t.getCabinTicket().get(lowestPrice.getBkclass()));
        CabinTicket cabinTicket = CommonUtil.mapToObject(t.getSeamLess(),CabinTicket.class);
        String dis =  String.valueOf((lowestPrice.getDiscountValue().equals(1.0) ? appConfig.getFullPrice()
                : (Float.parseFloat(lowestPrice.getDiscountValue()) * 10) + appConfig.getDiscount()));
        if ("0".equals(t.getpType())) {
            Map map = (Map) t.getCabinTicket().get(lowestPrice.getAirline() + lowestPrice.getFltno());
            classTicket = String.valueOf(map.get(lowestPrice.getBkclass()));
        }
        return fmt(appConfig.getParenthesesCodeLeft(), appConfig.getLowestPrice(),
                appConfig.getCNY(), String.valueOf(lowestPrice.getDiscountPrice()),
                appConfig.getComma(), getCabinName(lowestPrice.getCabin()),
                lowestPrice.getBkclass(), appConfig.getSlash(),
                dis, appConfig.getSlash(), classTicket,
                appConfig.getPage(), appConfig.getParenthesesCodeRight(),
                appConfig.getPeriod(), CommonUtil.getMessageEndInfoTime(appConfig.getMsgTime()));
    }

    /**
     * 拼装key value 请求参数
     *
     * @param instance ins
     * @return t
     */
    protected NameValuePair[] setValue(Instance instance) {

        return new NameValuePair[]{
                new NameValuePair("appid", instance.getAppid()[0]),
                new NameValuePair("userId", instance.getUserId()[0]),
                new NameValuePair("templateId", String.valueOf(instance.getMsgTempleId())),
                new NameValuePair("params", JSON.toJSONString(instance.getParams())),
                new NameValuePair("jumpParams", JSON.toJSONString(instance.getJumpParams())),
                new NameValuePair("token", instance.getToken())
        };
    }

    protected Map assembleParams(String ticketCount,String endInfo ,String cabinInfo ,String airlineInfo){
        Map<String,String> params = new HashMap<>();
        params.put("ticketCount", ticketCount);
        params.put("endInfo", endInfo);
        params.put("cabinInfo", cabinInfo);
        params.put("airlineInfo", airlineInfo);
        return params;
    }

    protected Map assembleParamsSpecial(String ticketCount,String endInfo ,String cabinInfo ,String airlineInfo){
        Map<String,String> params = new HashMap<>();
        params.put("ticketCount", ticketCount);
        params.put("endInfo", endInfo);
        params.put("airlinedate", cabinInfo);
        params.put("airline", airlineInfo);
        return params;
    }

    protected Map roseFmt(String airlineInfo ,String endInfo){
        Map params = new HashMap<>();
        params.put("airlineInfo",airlineInfo);
        params.put("endInfo",endInfo);
        return params;
    }


    protected Map jumpParams(){
        Map map = new HashMap();
        map.put("key",appConfig.getKey());
        return map;
    }

    public String getCabinName(String cabin) {
        Map<String, String> cabinMap = new HashMap<>();
        cabinMap.put(appConfig.getEconomyClassEN(), appConfig.getEconomyClass());
        cabinMap.put(appConfig.getBusinessClassEn(), appConfig.getBusinessClass());
        cabinMap.put(appConfig.getFirstClassEn(), appConfig.getFirstClass());
        String cabins = cabinMap.get(cabin);
        if (cabins == null || "".equals(cabins))
            return appConfig.getEconomyClass();
        else {
            return cabins;
        }
    }






}
