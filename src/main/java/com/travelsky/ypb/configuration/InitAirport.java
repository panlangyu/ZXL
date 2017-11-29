package com.travelsky.ypb.configuration;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by huc on 2017/11/22.
 */
public final class InitAirport {

    public static Map<String,String> airportMap = new HashMap<String,String>();
    public static Map<String, String> aou = new HashMap<String, String>();
    public static JSONObject json = new JSONObject();

    //初始化出发地到达地
    static {
        airportMap.put("SZX","深圳");
        airportMap.put("PEK","北京");
        airportMap.put("YIH","宜昌");

        aou.put("KY", "昆明航空");
        aou.put("G5", "华夏航空");
        aou.put("CX", "国泰航空");
        aou.put("CA", "国航");
        aou.put("CZ", "南航");
        aou.put("MU", "东航");
        aou.put("HU", "海航");
        aou.put("ZH", "深航");
        aou.put("FM", "上海航空");
        aou.put("MF", "厦门航空");
        aou.put("JD", "首都航空");
        aou.put("TV", "西藏航空");
        aou.put("NS", "河北航空");
        aou.put("HO", "吉祥航空");
        aou.put("8L", "祥鹏航空");
        aou.put("PN", "西部航空");
        aou.put("CN", "大新华航空");
        aou.put("JR", "幸福航空");
        aou.put("VD", "鲲鹏航空");
        aou.put("OQ", "重庆航空");
        aou.put("BK", "奥凯航空");
        aou.put("3U", "四川航空");
        aou.put("SC", "山东航空");
        aou.put("KN", "联合航空");
        aou.put("EU", "成都航空");
        aou.put("GS", "天津航空");
        aou.put("9C", "春秋航空");
        aou.put("CI", "中华航空");
        aou.put("DZ", "东海航空");
        aou.put("Y8", "扬子江航空");
        aou.put("FU", "福州航空");
        aou.put("UQ", "乌鲁木齐航空");
        aou.put("9H", "长安航空");
        aou.put("GJ", "长龙航空");
        aou.put("X2", "新华航空");
        aou.put("Z2", "中原航空");
        aou.put("3W", "南京航空");
        aou.put("ZJ", "浙江航空");
        aou.put("QW", "青岛航空");
        aou.put("A6", "红土航空");



        json.put("rcMachineType","pc");
        json.put("rcVersion","2.1.2");
        json.put("rcHannelId","WEB");
        json.put("rcKey","acbe255a9ba6bb3dcde608a53ab0f2c4");
        json.put("rcAccessToken","okaccept");
        json.put("rcMachienId","121212");
        json.put("sign","ac59075b964b0715");
        json.put("rcParams","");

    }

    public InitAirport() {
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
