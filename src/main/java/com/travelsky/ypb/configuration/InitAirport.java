package com.travelsky.ypb.configuration;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.travelsky.ypb.business.task.CacheManager;
import com.travelsky.ypb.dao.impl.RedisSupport;
import com.travelsky.ypb.domain.log.Log;
import com.travelsky.ypb.publics.HttpClient;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huc on 2017/11/22.
 */
@SuppressWarnings("ALL")
@Order(100)
@Component
public final class InitAirport {

    Logger logger = LogManager.getLogger(InitAirport.class.getName());
    public static List<String> list = new ArrayList<>();
    public static Map<String,String> airportMap = new HashMap<>();

    public static Map aou = new HashMap();

    @Autowired
    private HttpClient http;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    protected RedisSupport redis;


    public void initAirLineCabinPolicy(){
        String s = http.getAirlineCabinPolicy();
        logger.info("初始化航司基础数据");
        if (s.contains("rsParams")) {
            JSONObject jsonObject = (JSONObject) JSONArray.parse(s);
            List<JSONObject> lists = (List<JSONObject>) jsonObject.get("rsParams");
            for (JSONObject str : lists){
                cacheManager.putCacheInfo(str.getString("AIRLINECODE"),str.getString("SHOWAIRLINEZHNAME"),999999999);
                Log.i(this.getClass(),"航司数据缓存","[key]",
                        str.getString("AIRLINECODE"),"[value]",str.getString("SHOWAIRLINEZHNAME"));
            }
        }
    }

    public Map<String, String> getAirportMap() {
        return airportMap;
    }

    public void setAirportMap(Map<String, String> airportMap) {
        this.airportMap = airportMap;
    }

    public static String getAirport(String key){
        return airportMap.get(key);
    }



}
