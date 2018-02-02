package com.travelsky.ypb.domain.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.travelsky.ypb.dao.impl.RedisSupport;
import com.travelsky.ypb.umeticket.domain.response.TicketResponse;
import com.travelsky.ypb.umeticket.util.imp.TicketByAirlineUtil;
import com.travelsky.ypb.util.LogUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class TicketInfoService {

	@Autowired
	private FlightBaseInfoServiceImpl flightBaseInfoService;


	@Autowired
	protected RedisSupport redis;
	/**
	 * 获取航线余票量
	 * @param param
	 * @return
	 */
	public TicketResponse getTicketByRoute(String param){
		if (flightBaseInfoService==null) {
			flightBaseInfoService = new FlightBaseInfoServiceImpl();
		}
		JSONObject json = JSON.parseObject(param);
		List<String> depList = new ArrayList<String>();
		List<String> desList = new ArrayList<String>();
		String dept  = json.getString("dept");
		String dest  = json.getString("dest");
		String depCode = getRedisValue(dept);
		String desCode = getRedisValue(dest);
		if (StringUtils.isNotEmpty(depCode)&&StringUtils.isNotEmpty(desCode)) {
			depList = getList(depCode);
			desList = getList(desCode);
		}else {
			try {
				depList = flightBaseInfoService.getDepApcodeByCtcode(dept);
				desList = flightBaseInfoService.getDesApcodeByCtcode(dest);
				String depValue = getString(depList);
				String desValue = getString(desList);
				final String finalDepValue = depValue;
				final String finalDesValue = desValue;
				final String finalDepKey = getCityRedisKey(dept);
				final String finalDesKey = getCityRedisKey(dest);
				Thread thread = new Thread(new Runnable() {
					@Override
					public void run() {
						redis.set(finalDepKey, 60*60*24, finalDepValue);
						redis.set(finalDesKey, 60*60*24, finalDesValue);
					}
				});
				thread.start();
			} catch (Exception e) {
				LogUtil.error("400", TicketInfoService.class, "查询机场三字码信息失败", e);
			}
		}
		TicketResponse ticketResponse = TicketByAirlineUtil.getTicketByRoute(depList, desList, param, TicketResponse.class);
		return ticketResponse;
	}
	/**
	 * 获取航班余票量
	 * @param param
	 * @return
	 */
	public TicketResponse getTicketByFlight(String param){
		if (flightBaseInfoService==null) {
			flightBaseInfoService = new FlightBaseInfoServiceImpl();
		}
		JSONObject json = JSON.parseObject(param);
		List<String> depList = new ArrayList<String>();
		List<String> desList = new ArrayList<String>();
		String dept  = json.getString("dept");
		String dest  = json.getString("dest");
		String depCode = getRedisValue(dept);
		String desCode = getRedisValue(dest);
		if (StringUtils.isNotEmpty(depCode)&&StringUtils.isNotEmpty(desCode)) {
			depList = getList(depCode);
			desList = getList(desCode);
		}else {
			try {
				depList = flightBaseInfoService.getDepApcodeByCtcode(dept);
				desList = flightBaseInfoService.getDesApcodeByCtcode(dest);
				String depValue = getString(depList);
				String desValue = getString(desList);
				final String finalDepValue = depValue;
				final String finalDesValue = desValue;
				final String finalDepKey = getCityRedisKey(dept);
				final String finalDesKey = getCityRedisKey(dest);
				Thread thread = new Thread(new Runnable() {
					@Override
					public void run() {
						redis.set(finalDepKey, 60*60*24, finalDepValue);
						redis.set(finalDesKey, 60*60*24, finalDesValue);
					}
				});
				thread.start();
			} catch (Exception e) {
				LogUtil.error("400", TicketInfoService.class, "查询机场三字码信息失败", e);
			}
		}
		TicketResponse ticketResponse = TicketByAirlineUtil.getTicketByFlight(depList, desList, param, TicketResponse.class);
		return ticketResponse;
	}
	// 集合转成都好分隔的字符串
	public String getString(List<String> list){
		StringBuilder sb = new StringBuilder("");
		if (!list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				if (i==list.size()-1) {
					sb.append(list.get(i));
				}else{
					sb.append(list.get(i)+",");
				}
				
			}
		}
		return sb.toString();
	}
	
	// 获取redisKey
	public String getCityRedisKey(String key){
		String redisKey = "cityCode";
		if (StringUtils.isNotEmpty(key)) {
			redisKey = redisKey+"["+key+"]";
		}
		return redisKey;
	}
	//根据redisKey获取值
	public  String getRedisValue(String key){
		String redisKey = getCityRedisKey(key);
		String value = redis.get(redisKey);
		return value;
	}
	//将字符串转成集合
	public List<String> getList(String value){
		List<String> list = new ArrayList<String>();
		if (StringUtils.isNotEmpty(value)) {
			if (value.indexOf(",")>-1) {
				String[] split = value.split(",");
				list = Arrays.asList(split);
			}else{
				list.add(value);
			}
		}
		return list;
	}
}
