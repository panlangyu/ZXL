package com.travelsky.ypb.domain.support;

import com.alibaba.fastjson.JSONObject;
import com.travelsky.ypb.business.flightplan.FlightPlaneManage;
import com.travelsky.ypb.business.service.iface.IPushServer;
import com.travelsky.ypb.business.service.impl.PushServiceImpl;
import com.travelsky.ypb.configuration.AppConfig;
import com.travelsky.ypb.configuration.InitAirport;
import com.travelsky.ypb.domain.message.Instance;
import com.travelsky.ypb.domain.xml.CabinTicket;
import com.travelsky.ypb.domain.xml.LowestPrice;
import com.travelsky.ypb.model.airplan.YpbFlightPlan;
import com.travelsky.ypb.publics.CommonUtil;
import com.travelsky.ypb.publics.HttpUtils;
import com.travelsky.ypb.rmi.client.core.RmiRegisterClient;
import com.travelsky.ypb.umeticket.domain.response.TicketResponse;
import com.travelsky.ypb.umeticket.util.imp.TicketByAirlineUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by huc on 2017/11/22.
 * 公共方法
 */
@Component
public abstract class EventService <T extends Instance>{

    @Autowired
    RmiRegisterClient rmiClient;

    @Autowired
    protected AppConfig appConfig;

    @Autowired
    protected PushServiceImpl pushServer;

    /**
     * 公共获取Seamless余票信息
     * @param t Instance
     * @return  Map
     */
    protected Map getSeamLess(T t){
        JSONObject json = new JSONObject();
        json.put("dept",t.getDepartureAirport());
        json.put("dest",t.getArrivalAirport());
        json.put("deptDate", t.getFlightDate());
        json.put("flightNo",t.getFlightNo());
        json.put("airline",t.getAirlineName());
        TicketResponse response = TicketByAirlineUtil.getTicketByFlight(String.valueOf(json),TicketResponse.class);
        if (response.getAirlineAll() == null)
            return response.getFlightAll();
        else
            return response.getAirlineAll();
    }

    /**
     * 查询飞行计划
     * @param t T
     * @return list
     */
    protected List<YpbFlightPlan> getFlightPlan(T t){
        FlightPlaneManage client = rmiClient.createClient(FlightPlaneManage.SERVICE_NAME, FlightPlaneManage.class);
        YpbFlightPlan bean = new YpbFlightPlan(t.getPlanid());
        bean.setDeptcity(t.getDepartureAirport());
        bean.setDestcity(t.getDepartureAirport());
        bean.setFlightdate(t.getFlightDate());
        return client.queryAirlinePlan(bean);
    }


    /**
     * 总余票量
     * @param cabin CabinTicket
     * @return int
     */
   protected int ticketSum(CabinTicket cabin){
        return cabin.getF() + cabin.getC() + cabin.getY();
   }

   protected List<LowestPrice> getLowestPrice(T t){
       JSONObject object = InitAirport.json;
       JSONObject json = new JSONObject();
       json.put("ori", t.getFlightPlan().getDeptcity());
       json.put("des", t.getFlightPlan().getDestcity());
       json.put("departureDate", CommonUtil.personPNRDate(t.getFlightPlan().getFlightdate()));
       json.put("airline", t.getFlightPlan().getAirlinecodes());
       json.put("fltno", t.getFlightPlan().getFlightno().substring(2));
       json.put("bkClass", "");
       json.put("dev", "");
       json.put("deptm", "00:00-23:59");
       object.put("rcParams",json);
       return  HttpUtils.httpLowestPrice(json).getOutput().getResult().getFlightShopResult().getLowestPriceList();
   }

    /**
     *  字符串组合
     * @param args 参数
     * @return  str
     */
   protected String fmt(String... args){
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
     * @param cabinInfo c
     * @param endInfo e
     * @return str
     */
   protected String messageFormat(String airlineInfo,String cabinInfo,String endInfo,String ticketCount,int type){
       switch (type){
           case 5508:
               return fmt(airlineInfo,appConfig.getOf(),appConfig.getFlight(),appConfig.getComma(),cabinInfo,appConfig.getHas(),ticketCount,
                       appConfig.getPage(),endInfo);
       }
       return null;
   }

   protected LowestPrice getLowestPrice(List<LowestPrice> lowest){
       LowestPrice lowestPrice = new LowestPrice();
       if (lowest != null){
           Iterator iterator =  lowest.iterator();
           while (iterator.hasNext()){
               LowestPrice  price = (LowestPrice)iterator.next();
               if ("Y".equals(price.getCabin())) {
                   lowestPrice = price;
                   break;
               }
               if ("C".equals(price.getCabin())){
                   lowestPrice = price;
                   break;
               }
               if ("F".equals(price.getCabin())){
                   lowestPrice = price;
                   break;
               }
           }
       }
       return lowestPrice;
   }


    /**
     *  t
     * @param t t
     * @return t
     */
   protected String getCabinInfo(T t){
       return fmt(appConfig.getParenthesesCodeLeft(),t.getFlightDate(),t.getWeekday(),
               CommonUtil.getCfStr(t.getTakeoffBegin()),appConfig.getSetOff(),appConfig.getSpace(),t.getDepartureAirportCn(),
               appConfig.getBar(),t.getArrivalAirportCn(),appConfig.getParenthesesCodeRight(),appConfig.getComma(),
               appConfig.getTicketCountCn());
   }

    /**
     *
     * @param t t
     * @param lowestPrice l
     * @return t
     */
   protected String getEndInfo(T t,LowestPrice lowestPrice){
       return fmt(appConfig.getParenthesesCodeLeft(),appConfig.getLowestPrice(),appConfig.getCNY(),String.valueOf(lowestPrice.getDiscountPrice()),
               appConfig.getComma(),CommonUtil.getCabinName(lowestPrice.getCabin()),lowestPrice.getBkclass(),appConfig.getSlash(),
               String.valueOf(lowestPrice.getDiscountValue()),appConfig.getDiscount(),appConfig.getSlash(),String.valueOf(t.getCabinTicket().get(lowestPrice.getBkclass())),
               appConfig.getPage(),appConfig.getParenthesesCodeRight(),appConfig.getPeriod(),CommonUtil.getMessageEndInfoTime());
   }



}
