package com.travelsky.ypb.domain.support;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.travelsky.ypb.business.flightplan.FlightPlaneManage;
import com.travelsky.ypb.business.service.impl.PushServiceImpl;
import com.travelsky.ypb.configuration.AppConfig;
import com.travelsky.ypb.configuration.InitAirport;
import com.travelsky.ypb.domain.message.Instance;
import com.travelsky.ypb.domain.xml.CabinTicket;
import com.travelsky.ypb.domain.xml.LowestPrice;
import com.travelsky.ypb.model.airplan.YpbFlightPlan;
import com.travelsky.ypb.publics.CommonUtil;
import com.travelsky.ypb.publics.HttpClient;
import com.travelsky.ypb.rmi.client.core.RmiRegisterClient;
import com.travelsky.ypb.umeticket.domain.response.TicketResponse;
import com.travelsky.ypb.umeticket.util.imp.TicketByAirlineUtil;
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
@Component
public abstract class EventService<T extends Instance> {

    @Autowired
    private RmiRegisterClient rmiClient;

    @Autowired
    protected AppConfig appConfig;

    @Autowired
    protected PushServiceImpl pushServer;

    @Autowired
    private HttpClient httpClient;

    /**
     * 航班公共获取Seamless余票信息
     *
     * @param t Instance
     * @return Map
     */
    protected Map getSeamLess(T t) {
        JSONObject json = new JSONObject();
        TicketResponse response = null;
        json.put("dept", t.getDepartureAirport());
        json.put("dest", t.getArrivalAirport());
        json.put("deptDate", t.getFlightDate());
        json.put("flightNo", t.getFlightNo());
        json.put("airline", t.getAirlineName());
        if (t.getpType().equals("1")) {
            response = TicketByAirlineUtil.getTicketByFlight(String.valueOf(json), TicketResponse.class);
            return response.getFlightAll();
        } else {
            response = TicketByAirlineUtil.getTicketByRoute(JSON.toJSONString(json), TicketResponse.class);
            return response.getAirlineAll();
        }
    }


    /**
     * 查询飞行计划
     *
     * @param t T
     * @return list
     */
    protected List<YpbFlightPlan> getFlightPlan(T t) {
        FlightPlaneManage client = rmiClient.createClient(FlightPlaneManage.SERVICE_NAME, FlightPlaneManage.class);
        YpbFlightPlan bean = new YpbFlightPlan();
        bean.setPlanid(t.getPlanid());
        bean.setDeptcity(t.getDepartureAirport());
        bean.setDestcity(t.getArrivalAirport());
        bean.setFlightdate(t.getFlightDate());
        bean.setFlightno(t.getFlightNo());
        return client.queryAirlinePlan(bean);
    }


    protected Map<String, YpbFlightPlan> listToMap(List<YpbFlightPlan> list) {
        Map<String, YpbFlightPlan> planMap = new HashMap<>();
        for (YpbFlightPlan plan : list) {
            planMap.put(plan.getUserid(), plan);
        }
        return planMap;
    }


    /**
     * 航班总余票量
     *
     * @param cabin CabinTicket
     * @return int
     */
    protected int ticketSum(final CabinTicket cabin) {

        return cabin.getF() + cabin.getC() + cabin.getY();
    }


    protected int ticketSumForAirline(Map<String, Map<String, CabinTicket>> map) {
        int sum = 0;
        for (String key : map.keySet()) {
//            CabinTicket ticket = CommonUtil.mapToObject(((Map<String, CabinTicket>) map.get(key)),CabinTicket.class);
            CabinTicket ticket = CommonUtil.mapToObject(map.get(key), CabinTicket.class);

            sum += ticket.getF() + ticket.getC() + ticket.getY();
        }
        return sum;
    }


    /**
     * 获取最低价
     * @param t t
     * @return list
     */
    protected List<LowestPrice> getLowestPrice(T t) {
        JSONObject object = InitAirport.json;
        JSONObject json = new JSONObject();
        json.put("ori", t.getDepartureAirport());
        json.put("des", t.getArrivalAirport());
        json.put("departureDate", CommonUtil.personPNRDate(t.getFlightDate()));
        json.put("airline", (t.getAirlineName() == null ? "" : t.getAirlineName()));
        json.put("fltno", (t.getFlightNo() == null ? "" : t.getFlightNo().substring(2)));
        json.put("bkClass", "");
        json.put("dev", "");
        json.put("deptm", "00:00-23:59");
        object.put("rcParams", json);
        return httpClient.httpLowestPrice(json).getOutput().getResult().getFlightShopResult().getLowestPriceList();
    }

    /**
     * 字符串组合
     *
     * @param args 参数
     * @return str
     */
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
     *
     * @param airlineInfo a
     * @param cabinInfo   c
     * @param endInfo     e
     * @return str
     */
    protected String messageFormat(String airlineInfo, String cabinInfo, String endInfo, String ticketCount, int type) {
        switch (type) {
            case 5508:
                return fmt(airlineInfo, appConfig.getOf(), appConfig.getFlight(), appConfig.getComma(), cabinInfo, appConfig.getHas(), ticketCount,
                        appConfig.getPage(), endInfo);
            case 5502:
                return fmt(airlineInfo, appConfig.getOf(), appConfig.getFlight(), appConfig.getComma(), cabinInfo, appConfig.getHas(), ticketCount,
                        appConfig.getPage(), endInfo);
            case 5507:
                return fmt(airlineInfo, appConfig.getOf(), appConfig.getAirline(), appConfig.getComma(), cabinInfo, appConfig.getHas(), ticketCount,
                        appConfig.getPage(), endInfo);
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
        if ("0".equals(t.getpType())) {
            Map map = (Map) t.getCabinTicket().get(lowestPrice.getAirline() + lowestPrice.getFltno());
            classTicket = String.valueOf(map.get(lowestPrice.getBkclass()));
        }
        return fmt(appConfig.getParenthesesCodeLeft(), appConfig.getLowestPrice(),
                appConfig.getCNY(), String.valueOf(lowestPrice.getDiscountPrice()),
                appConfig.getComma(), CommonUtil.getCabinName(lowestPrice.getCabin()),
                lowestPrice.getBkclass(), appConfig.getSlash(),
                String.valueOf(lowestPrice.getDiscountValue()),
                appConfig.getDiscount(), appConfig.getSlash(), classTicket,
                appConfig.getPage(), appConfig.getParenthesesCodeRight(),
                appConfig.getPeriod(), CommonUtil.getMessageEndInfoTime());
    }


    /**
     * 拼装key value 请求参数
     *
     * @param instance ins
     * @return t
     */
    protected NameValuePair[] setValue(Instance instance) {

        return  new NameValuePair[]{
                new NameValuePair("appid", instance.getAppid()[0]),
                new NameValuePair("userId", instance.getUserId()[0]),
                new NameValuePair("templateId", String.valueOf(instance.getMsgTempleId())),
                new NameValuePair("params", JSON.toJSONString(instance.getParams())),
                new NameValuePair("jumpParams", JSON.toJSONString(instance.getJumpParams())),
                new NameValuePair("token", instance.getToken())
        };
    }

}
