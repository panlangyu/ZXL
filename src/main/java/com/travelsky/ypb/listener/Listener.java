package com.travelsky.ypb.listener;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.travelsky.ypb.business.TicketLootManagerService;
import com.travelsky.ypb.domain.message.InitMessageBody;
import com.travelsky.ypb.domain.message.Instance;
import com.travelsky.ypb.domain.support.EventResponse;
import com.travelsky.ypb.domain.support.ServiceSupport;
import com.travelsky.ypb.model.RPCEntity;
import com.travelsky.ypb.publics.HttpClient;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * create Listener by huc
 * 2017/12/12  下午2:35
 */
@SuppressWarnings("ALL")
@RestController
@RequestMapping(value = "/MSG")
public class Listener {

    Logger log = Logger.getLogger(InformationListener.class);

    @Value("${activeMq:unknown}")
    public String queue;

    @Autowired
    ApplicationContext app;

    @Autowired
    HttpClient http;

    @Reference
    TicketLootManagerService service;

    @RequestMapping(value = "/Listener")
    public int Listener(@RequestParam String message){
        JSONObject jsonObject = (JSONObject) JSONObject.parse(message);
        log.info(jsonObject.getString("key"));

        // TODO 初始化消息体
        EventResponse init = InitMessageBody.init(jsonObject.getString("key"));
        Instance instance = init.getEventBody();
        check(instance);
        // TODO 任务分发
        try {
            ((ServiceSupport)app.getBean(Class.forName(init.getClazz()))).process(instance);
            return 0;
        } catch (Exception e) {
            log.error(e.getMessage());
            return -1;
        }
    }

    @RequestMapping(value = "/index")
    public String index(@RequestParam String param){
        if (param == null){
            param = "{\"flightNo\":\"CZ3569\",\"ticketCount\":\"88\",\"flightDate\":\"20180314\",\"departureAirport\":\"SZX\",\"cabinTicket\": {\"A\":\"0\",\"B\":\"0\",\"E\":\"0\",\"F\":\"2\",\"G\":\"0\",\"H\":\"0\",\"J\":\"0\",\"K\":\"0\",\"L\":\"0\",\"M\":\"0\",\"N\":\"0\",\"O\":\"1\",\"P\":\"0\",\"Q\":\"0\",\"R\":\"0\" ,\"S\":\"0\",\"T\":\"0\",\"U\":\"0\",\"V\":\"0\",\"W\":\"0\",\"X\":\"0\",\"Y\":\"85\",\"Z\":\"0\"},\"arrivalAirport\":\"HGH\",\"airlineName\":\"CZ\"}";
        }
        RPCEntity rpcEntity = new RPCEntity();
        try{
            rpcEntity.setParam(param);
            service.lootTicketInfo(rpcEntity);
            return "1";
        }catch (Exception e){
            return "-1";
        }
    }

    public void check(Instance t){
        if (t.getEventType().equals("5502")){
            RPCEntity rpcEntity = new RPCEntity();
            log.info(this.getClass()+"抢票通知开始");
            String request = t.getOriginalMessage().split("##")[7];
            JSONObject jsonObject = JSON.parseObject(request);
            rpcEntity.setParam(jsonObject.get("instance"));
            log.info(this.getClass()+"服务："+service+request);
            try{
                service.lootTicketInfo(rpcEntity);
                log.info(this.getClass()+"抢票通知完成");
            }catch (Exception e){
                log.info(this.getClass()+"抢票通知失败" , e);
            }
        }

    }


}
