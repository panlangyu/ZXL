package com.travelsky.ypb.domain.support;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.travelsky.ypb.business.LowestPriceOfPlanDubbo;
import com.travelsky.ypb.business.TicketLootManagerService;
import com.travelsky.ypb.business.task.Cache;
import com.travelsky.ypb.business.task.CacheManager;
import com.travelsky.ypb.configuration.AppConfig;
import com.travelsky.ypb.configuration.InitAirport;
import com.travelsky.ypb.dao.impl.RedisSupport;
import com.travelsky.ypb.domain.log.Log;
import com.travelsky.ypb.domain.message.Instance;
import com.travelsky.ypb.domain.model.CityAirlineCode;
import com.travelsky.ypb.domain.model.FlightPlan;
import com.travelsky.ypb.domain.model.TicketChangePrice;
import com.travelsky.ypb.domain.service.CityAirlineCodeService;
import com.travelsky.ypb.domain.service.FlightPlanService;
import com.travelsky.ypb.domain.service.TicketChangePriceService;
import com.travelsky.ypb.domain.service.TicketInfoService;
import com.travelsky.ypb.domain.xml.CabinTicket;
import com.travelsky.ypb.model.RPCEntity;
import com.travelsky.ypb.model.lowestPriceOfPlan.FareInterface;
import com.travelsky.ypb.process.Event5505;
import com.travelsky.ypb.process.Event5506;
import com.travelsky.ypb.publics.CommonUtil;
import com.travelsky.ypb.publics.HttpClient;
import com.travelsky.ypb.publics.IdWorker;
import com.travelsky.ypb.umeticket.domain.response.TicketResponse;
import com.travelsky.ypb.umeticket.http.HttpClientManager;
import com.travelsky.ypb.umeticket.http.HttpClientManagerImp;
import org.apache.commons.lang.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

/**
 * 基础数据
 * Created by huc on 2017/12/6.
 */
@Service
public abstract class Support<T extends Instance> {

    private static org.apache.logging.log4j.Logger logger = LogManager.getLogger(Support.class.getName());;

    @Autowired
    protected ApplicationContext app;

    @Autowired
    protected AppConfig appConfig;

    @Autowired
    private HttpClient httpClient;

    @Autowired
    protected CityAirlineCodeService codeService;

    @Autowired
    protected FlightPlanService flightPlanService;

    @Autowired
    protected Executor executor;

    @Autowired
    protected TicketChangePriceService priceService;

    @Autowired
    private TicketInfoService ticketInfoService;

    HttpClientManager http = HttpClientManagerImp.getInstance();

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private InitAirport init;

    @Reference
    TicketLootManagerService service;

    @Reference(version = "1.1_xwh")
    LowestPriceOfPlanDubbo lowestPrice;

    @Autowired
    protected RedisSupport redis;

    /**
     * 航班公共获取Seamless余票信息
     * @param t Instance
     * @return Map
     */
    protected Map getSeamLess(T t) {
        logger.info("getSeamLess in...." + ticketInfoService);
        JSONObject json = new JSONObject();
        TicketResponse response = null;
        String airline = t.getFlightPlan().getAirlinecodes();
        String flightNo = (t.getFlightPlan().getCodeShare() != null ? t.getFlightPlan().getCodeShare() : t.getFlightPlan().getFlightno());
        if (t.getFlightPlan().getCodeShare() != null){
            flightNo = t.getFlightPlan().getCodeShare();
            airline = t.getFlightPlan().getCodeShare().substring(0,2);
        }

        json.put("dept", t.getDepartureAirport());
        json.put("dest", t.getArrivalAirport());
        json.put("deptDate", t.getFlightDate());
        json.put("flightNo", flightNo);
        json.put("airline", airline);
        logger.info(this.getClass() + "-- start --" + String.valueOf(json));

        if (t.getpType().equals("1")) {
            Log.i(this.getClass(),"in",String.valueOf(json));
            response = ticketInfoService.getTicketByFlight(String.valueOf(json));
            Log.i(this.getClass(),"out",String.valueOf(response));
            return response.getFlightAll();
        } else {
            Log.i(this.getClass(),"in",String.valueOf(json));
            response = ticketInfoService.getTicketByRoute(JSON.toJSONString(json));
            Log.i(this.getClass(),"out",String.valueOf(response));
            return response.getAirlineAll();
        }
    }

    /**
     * 查询飞行计划
     * @param t T
     * @return list
     */
    protected List<FlightPlan> getFlightPlan(T t) {
        // FlightPlaneManage client = rmiClient.createClient(FlightPlaneManage.SERVICE_NAME, FlightPlaneManage.class);
        FlightPlan bean = new FlightPlan();
        bean.setPlanid(t.getPlanid());
        bean.setDeptcity(t.getDepartureAirport());
        bean.setDestcity(t.getArrivalAirport());
        bean.setFlightdate(t.getFlightDate());
        bean.setFlightno(t.getFlightNo());
        bean.setPtype(t.getpType());
        return flightPlanService.findList(bean);
    }

    /**
     * 更新飞行计划
     * @param t
     */
    protected void updateFlightPlan(T t){
        CabinTicket cabinTicket;
        FlightPlan bean = new FlightPlan();
        String classTicket = String.valueOf(t.getSeamLess().get(t.getPrice().getBkclass()));
        bean.setDeptcity(t.getDepartureAirport());
        bean.setDestcity(t.getArrivalAirport());
        bean.setFlightdate(t.getFlightDate());
        bean.setFlightno(t.getFlightNo());
        try {
            cabinTicket = CommonUtil.mapToObject(t.getSeamLess(),CabinTicket.class);
            bean.setFcabinticketcount(String.valueOf(cabinTicket.getF()));
            bean.setCcabinticketcount(String.valueOf(cabinTicket.getC()));
            bean.setYcabinticketcount(String.valueOf(cabinTicket.getY()));
            bean.setTotalcabinticketcount(String.valueOf(ticketSum(cabinTicket)));
            bean.setMindiscountclazz(t.getPrice().getBkclass());
            bean.setMindiscountcabin(t.getPrice().getCabin());
            bean.setMinprice(String.valueOf(t.getPrice().getDiscountPrice()));
            bean.setMinpricediscount(String.valueOf(t.getPrice().getDiscountValue()));
            bean.setMindiscountticketcount(classTicket);
        } catch (Exception e) {
            Log.i(this.getClass(),"updateFlightPlan",e);
        }
        Log.i(this.getClass(),"updateFlightPlan","更新飞行计划结束！",String.valueOf(bean));
        flightPlanService.update(bean);
    }


    /**
     * 获取最低价
     * @param t t
     * @return list
     */
    protected List<com.travelsky.ypb.model.lowestPriceOfPlan.LowestPrice> getLowestPrice(T t) throws JAXBException {
        RPCEntity request = new RPCEntity();
        JSONObject json = new JSONObject();
        RPCEntity response = new RPCEntity();
        json.put("airlineCode", t.getAirlineName());
        json.put("arrivalCity", t.getArrivalAirport());
        json.put("clazz", ("null".equals(t.getFlightPlan().getClazz()) || t.getFlightPlan().getClazz() == null) ? "ALL" : t.getFlightPlan().getClazz());
        json.put("departureCity", t.getDepartureAirport());
        json.put("equipmentCode", "ALL");
        json.put("flightNo", t.getFlightPlan().getFlightno());
        // 日期 可能需要改格式
        json.put("flightDate", t.getFlightDate());
        // 航班类型 0 代表国内航班
        json.put("pType", "0");
        json.put("takeoffBegin", t.getFlightPlan().getTakeoffbegin());
        json.put("takeoffEnd", t.getFlightPlan().getTakeoffend());
        json.put("userId", t.getFlightPlan().getUserid());

        Log.i(this.getClass(),"获取航线最低价");
        request.setParam(json);
       /* if (t.getpType().equals("1")){
            response = lowestPrice.getLowestPriceByFlight(request);
        }
        FareInterface fareInterface = (FareInterface) response.getParam();*/
        String lowestStr = httpClient.httpPost(appConfig.getLowesPriceUrl(),String.valueOf(json));
        FareInterface fareInterface = JSONObject.parseObject(lowestStr,FareInterface.class);
        return fareInterface.getOutput().getResult().getFlightShopResult().getLowestPrice();
    }

    protected String getAirlineCN(T t){
            Log.i(this.getClass(),"查询航司中文名",t.getAirlineName());
            Cache cache =cacheManager.getCacheInfo(t.getAirlineName());
            Log.i(this.getClass(),"cache",String.valueOf(cache));
            if (cache == null){
                Log.i(this.getClass(),"初始化查询航司中文名-cache");
                init.initAirLineCabinPolicy();
                cache = cacheManager.getCacheInfo(t.getAirlineName());
                Log.i(this.getClass(),"初始化查询航司中文名-cache 完毕！" ,cache);
            }
            Log.i(this.getClass(),"cache ent" ,String.valueOf(cache));
            if (cache == null || cache.equals("null")){
                return null;
            }
            /*String ailine = redis.get(t.getAirlineName());
            if (ailine == null || ailine.equals("null")){
                init.initAirLineCabinPolicy();
                ailine = redis.get(t.getAirlineName());
            }*/
            return String.valueOf(cache.getValue());

    }

    protected String cityAirportName(String str) {
        List<CityAirlineCode> list = codeService.findList(new CityAirlineCode(str));
        Log.i(this.getClass(), "查询机场中文名", ArrayUtils.toString(list));
        if (!list.isEmpty() || list.size() != 0) {
            return list.get(0).getCityAirportName();
        } else {
            return " ";
        }
    }

    /**
     * 航班总余票量
     * @param cabin CabinTicket
     * @return int
     */
    protected int ticketSum(final CabinTicket cabin) {

        return cabin.getF() + cabin.getC() + cabin.getY();
    }


    /**
     * -1:降价 0:价格不变 1:涨价
     * @param t
     * @return
     */
    protected List<TicketChangePrice> changePrice(T t){
        TicketChangePrice changePrice = new TicketChangePrice();
        changePrice.setDept(t.getDepartureAirport());
        changePrice.setDest(t.getArrivalAirport());
        changePrice.setFlightDate(t.getFlightDate());
        changePrice.setFlightNo(t.getFlightNo());

        return priceService.findList(changePrice);
    }

    protected void paser(T t){
        List<TicketChangePrice>  changePrices = changePrice(t);
        if (changePrice(t).isEmpty()){ saveChangePrice(t); return;}
        TicketChangePrice price = changePrices.get(0);
        Log.i(this.getClass(),"TicketChangePrice",price);
        if (price == null) return;
        t.setTicketChangePrice(price);
        int k = Double.compare(Double.parseDouble(String.valueOf(t.getPrice().getDiscountPrice()))
                ,Double.parseDouble(price.getLastPrice()));
        switch (k){
            case -1:
                Log.i(this.getClass(),"paser",k,"价格下降");
                saveChangePrice(t);
                ((Event5506)app.getBean(Event5506.class)).process(t);
                break;
            case 1 :
                Log.i(this.getClass(),"paser",k,"价格上涨");
                saveChangePrice(t);
                ((Event5505)app.getBean(Event5505.class)).process(t);
                break;
             default:
                 Log.i(this.getClass(),"paser",k,"价格不变");
                 break;
        }
    }


    /**
     * 保存当前最低价
     * @param t
     */
    protected void saveChangePrice(T t){
        CabinTicket cabinTicket = CommonUtil.mapToObject(t.getSeamLess(),CabinTicket.class);
        TicketChangePrice ticketChangePrice = new TicketChangePrice();
        ticketChangePrice.setId(String.valueOf(IdWorker.nextId()));
        ticketChangePrice.setPlandId(t.getFlightPlan().getPlanid());
        ticketChangePrice.setpType(t.getpType());
        ticketChangePrice.setFlightDate(t.getFlightDate());
        ticketChangePrice.setFlightNo(t.getFlightNo());
        ticketChangePrice.setAirlineCode(t.getAirlineName());
        ticketChangePrice.setAppId(String.valueOf(t.getAppid()));
        ticketChangePrice.setBusinessClass(String.valueOf(cabinTicket.getC()));
        ticketChangePrice.setClazz(t.getPrice().getCabin());
        ticketChangePrice.setLastPrice(String.valueOf(t.getPrice().getDiscountPrice()));
        ticketChangePrice.setThisPrice("");
        ticketChangePrice.setDept(t.getDepartureAirport());
        ticketChangePrice.setDest(t.getArrivalAirport());
        ticketChangePrice.setIsShare(t.getFlightPlan().getCodeShare());
        ticketChangePrice.setEconomyClass(String.valueOf(cabinTicket.getY()));
        ticketChangePrice.setFirstClass(String.valueOf(cabinTicket.getF()));
        ticketChangePrice.setTakeOffBegin(t.getFlightPlan().getTakeoffbegin());
        ticketChangePrice.setTakeOffEnd(t.getFlightPlan().getTakeoffend());
        ticketChangePrice.setLastId("");
        priceService.save(ticketChangePrice);
    }


    /**
     * 抢票
     * @param t
     */
    public void grabVotes(T t){
        RPCEntity rpcEntity = new RPCEntity() ;
        Log.i(this.getClass(),"抢票通知开始");
        String request = t.getOriginalMessage().split("##")[7];
        rpcEntity.setParam(request);
        Log.i(this.getClass(),"服务：",service,request);
        try{
            t.getService().lootTicketInfo(rpcEntity);
            Log.i(this.getClass(),"抢票通知完成");
        }catch (Exception e){
            Log.i(this.getClass(),"抢票通知失败" , e);
        }


    }


}
