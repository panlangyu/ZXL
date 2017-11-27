package com.travelsky.ypb.publics;

import com.sun.tools.javac.util.Convert;
import com.travelsky.ypb.domain.xml.LowestPrice;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 常用工具类
 * 
 * @author huc
 * @see CommonUtil
 */
public final class CommonUtil {
	
	private static final Logger LOGGER = Logger.getLogger(CommonUtil.class);

	//private static final ResourceBundle resource = ResourceBundle.getBundle("msg");
	/**
	 * 指定日期格式转换 <br>
	 * Examples <blockquote>
	 * 
	 * <pre>
	 * convertDate("20160815")   2016-08-15
	 * </pre>
	 * 
	 * </blockquote>
	 * 
	 * @param date
	 *            例：20160815
	 * @return 例：2016-08-15
	 */
	public static String convertDate(String date) {
		if (StringUtils.isBlank(date)) {
			return "";
		}
		if (8 != date.length())
			throw new RuntimeException("CommonUtil.convertDate date 类型不对  {"
					+ date + "}");
		String year = date.substring(0, 4);
		String month = date.substring(4, 6);
		String day = date.substring(6);
		return year + "-" + month + "-" + day;
	}
	
	
	public static boolean verificationUserId(String userId){
		boolean flag = true;
		/*String[] mq_user = resource.getString("MQ_USER").split("##");
		for (int i = 0; i < mq_user.length; i++) {
			if (userId.equals(mq_user[i])) {
				flag = false;
			}
		}
		*//*if (userId.equals("71251341")||userId.equals("30824674") || userId.equals("36346065") || userId.equals("71982115") || userId.equals("23237764") || userId.equals("5284")) {
			flag = false;
		}*/
		return flag;
	}
	
	
	/**
	 * 模糊余票量
	 * @param count
	 * @return
	 */
	public static String getCabinCountBlurry(String count){
		int cabinCount = Integer.parseInt(count);
		if (cabinCount > 9) {
			return "9张以上";
		}else {
			return String.valueOf(cabinCount)+"张";
		}
	}
	
	/**
	 * (当前最低票价￥1222
	 * @param d
	 * @return
	 */
	public static String getLoweetPriceCn(Float d){
		return "(当前最低票价￥"+d.toString();
	}
	
	public static String getClazzInfo(LowestPrice lowestPrice, String ticketCount){
		String begin = "(当前最低票价￥"+(lowestPrice.getDiscountPrice()==null?lowestPrice.getDisAmt():lowestPrice.getDiscountPrice());
		String info =  String.format(" ，%s%s/%s/%s)。"
				       ,CommonUtil.getCabinName(lowestPrice.getCabin()),lowestPrice.getBkclass()
				       ,getPriceDiscount(lowestPrice.getDiscountValue()),getCabinCountBlurry(ticketCount));
		LOGGER.info("最低价---------------->:"+begin + info);
		return begin +info;
	}
	
	public static String getRouteClazzInfo(LowestPrice lowestPrice, String ticketCount) {
		if (lowestPrice != null) {
			LOGGER.info("----------------------->lowestPrice:" + lowestPrice.toString());
		}
		
		String routeLowestPrice = lowestPrice.getDiscountPrice() == null ? lowestPrice.getDisAmt().toString() : lowestPrice.getDiscountPrice().toString();
		String routeLowestPriceInfo = String.format("(目前最低票价￥%s/%s/%s)。", routeLowestPrice, getPriceDiscount(lowestPrice.getDiscountValue()), getCabinCountBlurry(ticketCount));
		
		LOGGER.info("最低价---------------->:" + routeLowestPriceInfo);
		return routeLowestPriceInfo;
	}
	
	/**
	 * 
	 * @return 目前最低价：
	 */
	public static  String getStringLowerInfoCn() {
		return "目前最低价：";
	}
	
	/**
	 * @param HeadTcount
	 * @return 
	 * 头等商务舱还有"+HeadTcount+"张
	 */
	public static String getHeadCabin(int HeadTcount){
		return "头等商务舱还有"+HeadTcount+"张。";
	}
	
	/**
	 * @return 建议尽快购票！
	 */
	public static String getPriceDeclineSuggest() {
		return "建议尽快购票！";
	}
	
	/**
	 * @return 用余票宝及时关注航线价格变动！
	 */
	public static String getPriceRiseSuggest() {
		return "用余票宝及时关注航线价格变动！";
	}
	
	/**
	 * ￥XXXX降为￥XXXX，
	 * @param lastTimePrice 上次价格
	 * @param thisTimePrice 本次价格
	 * @return
	 */
	public static String getDeclineStr(String lastTimePrice ,String thisTimePrice){
		
		return "￥"+lastTimePrice+"下降为￥"+thisTimePrice+"，";
	}
	
	/**
	 * XXXX上涨为￥XXXX，
	 * @param lastTimePrice 上次价格
	 * @param thisTimePrice 本次价格
	 * @return
	 */
	public static String getisestr(String lastTimePrice ,String thisTimePrice){
			
	   return "￥"+lastTimePrice+"上涨为￥"+thisTimePrice+"，";
	}
	
	/**
	 * @return
	 * [消息时间:4-07 12:12]。
	 */
	public static String getMessageEndInfoTime(){
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
		return "[消息时间:"+sdf.format(date)+"]。";
	}
	
	public static String cabinTitle(){
		return "好消息！";
	}
	
	public static String resTitle(){
		return "啊哦~~";
	}

	/**
	 * 指定时间格式转换 <br>
	 * Examples <blockquote>
	 * 
	 * <pre>
	 * convertTime("10")   10:00:00  
	 * 如果是24  时间转23:59:59
	 * </pre>
	 * 
	 * </blockquote>
	 * 
	 * @param take
	 *            例：10
	 * @return 例：10:00:00
	 */
	public static String convertTime(String take) {
		if (StringUtils.isBlank(take)) {
			return "";
		}
		if (2 != take.length()) {
			throw new RuntimeException("CommonUtil.convertTime take 格式不对 {"
					+ take + "}");
		}
		if ("24".equals(take)) // 如果时间是24 需要转换成23:59:59
			take = "23:59:59";
		else
			take = take + ":00:00";
		return take;
	}

	/**
	 * 对象属性值 转map
	 * 
	 * @param
	 * @return map
	 */
	public static Map<String, String> objectToMap(Object obj) {
		Map<String, String> map = new HashMap<String, String>();
		
		Field[] fields = obj.getClass().getDeclaredFields();
		for (Field f : fields) {
			f.setAccessible(true);
			try {
				if (f.get(obj) != null)
					map.put(f.getName(), String.valueOf(f.get(obj)));

			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return map;
	}
	
	/**
	 * map转对象 Key必须对应属性名称
	 * 
	 * @param map
	 * @param t
	 * @return
	 */
	public static <T> T mapToObject(Map<String, String> map, Class<T> t) {
		if (map.size() == 0) return null;
		T tR = null;
		Set<String> keySet = map.keySet();
		Iterator<String> keysI = keySet.iterator();
		try {
			tR = t.newInstance();
			while (keysI.hasNext()) {
				String key = keysI.next();
				Field[] fields = t.getDeclaredFields();
				Field.setAccessible(fields, true);
				for (Field field : fields) {
					if (field.getName().equals(key)) {
						field.set(tR, map.get(key));
						break ;
					}
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} 
		return tR;
	}
	
	/**
	 * map转对象 Key必须对应属性名称
	 * 
	 * @param map
	 * @param t
	 * @param fs 匹配过滤字段名称
	 * @return
	 */
	public static <T> T mapToObjectForField(Map<String, String> map, Class<T> t,String[] fs) {
		if (map.size() == 0) return null;
		T tR = null;
		Set<String> keySet = map.keySet();
		Iterator<String> keysI = keySet.iterator();
		try {
			tR = t.newInstance();
			while (keysI.hasNext()) {
				String key = keysI.next();
				Field[] fields = t.getDeclaredFields();
				Field.setAccessible(fields, true);
				for (Field field : fields) {
					for (String f : fs) {
						//如果属性名称和传进来的一样 则放入对象中
						if (field.getName().equals(f)) {
							if (field.getName().equals(key)) {
								field.set(tR, map.get(key));
								break ;
							}
						}
						
					}
				}
			}
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return tR;
	}

	/**
	 * 指定时间获取星期几 <br>
	 * Examples <blockquote>
	 * 
	 * <pre>
	 * getDayOfWeek(2016-10-14 12:22:22)  星期五
	 * </pre>
	 * 
	 * </blockquote>
	 * 
	 * @param date
	 *            例：new Date()
	 * @return 例：星期五
	 */
	public static String getDayOfWeek(Date date) {
		String[] weekArr = new String[] { "周日", "周一", "周二", "周三", "周四", "周五",
				"周六" };
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int weekDay = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		return weekArr[weekDay];
	}
	
	/**
	 * 指定时间字符串转时间格式 <br>
	 * Examples <blockquote>
	 * <pre>
	 * getStrOfDate(2016-12-10,yyyy-MM-dd) Date
	 * </pre>
	 * </blockquote>
	 * @param date
	 *            例：2016-12-10
	 * @return 例：Date
	 */
	public static String getStrOfDate(String date){
		return date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6, 8);
	}


	public static String strToDateStr(String str){
		String s =  (String) str.subSequence(0, 4);
		String s1 = str.substring(4, 6);
		String s2 = str.substring(6, 8);
		String newStr = s + "-" + s1 +"-" + s2;
		return newStr;
	}
	
	/**
	 * 将日期字符串转换成指定格式
	 * @param date 例：20161210
	 * @return 例: 2016-12-10
	 */
	public static String getDateStr(String date){
		String ret = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat newSdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date newDate = sdf.parse(date);
			ret = newSdf.format(newDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	/**
	 * 获取简单日期格式 如果计划中的日期是今年则返回当前日期，如果不是则返回带了年的日期<br/>
	 * Examples <blockquote>
	 * <pre>
	 * getSimpleDate(Date date) Date
	 * </pre>
	 * </blockquote>
	 * @param date 
	 * 			例：2016-12-10
	 * @return  例：12-10
	 */
	public static String getSimpleDate(Date date){
		String resultDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
		Calendar planDate = Calendar.getInstance();
		planDate.setTime(date);
		
		Calendar nowDate = Calendar.getInstance();
		if (nowDate.get(Calendar.YEAR) == planDate.get(Calendar.YEAR)) {
			String month = (planDate.get(Calendar.MONTH) + 1) + "";
			String day = planDate.get(Calendar.DAY_OF_MONTH) + "";
			if ((planDate.get(Calendar.MONTH) + 1) < 10)
				month = "0" + month;
			if (planDate.get(Calendar.DAY_OF_MONTH) < 10) 
				day = "0" + day;
			resultDate = month + "-" + day;
		}
		return resultDate;
	}
	
	/**
	 * 校验5501和5502事件
	 */
	public static Boolean checkEvent(String eventType, String tCount) {
		Boolean ret = false;
		int ticketCount = Integer.parseInt(tCount);
		if (eventType.equals("5501") && ticketCount <= 200) {
			ret = true;
		}
		else if (eventType.equals("5502") && ticketCount <= 100) {
			ret = true;
		}
		return ret;
	}
	
	/**
	 * 获取当前日期的字符串格式<br/>
	 * Examples <blockquote>
	 * <pre>
	 * getNowDateStr()
	 * </pre>
	 * </blockquote>
	 * @return  例：2016-12-09 18:22:00
	 */
	public static String getNowDateStr(){
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	}
	
	/**
	 * 获取航班一个简单余票量标记的String (后期可配置动态)<br/>
	 * Examples <blockquote>
	 * <pre>
	 * getSimpleTicketCount(String ticketCount) 例：21
	 * </pre>
	 * </blockquote>
	 * @return  例：T20
	 */
	/*public static String getAirFlightSimpleTicketCount(String ticketCount){
		if (ticketCount.length() == 1) { //如果票量少于10张以下 算1-10 张区间 只当一个区间
			return "T1-10";
		} else {
			int tcInt = Integer.parseInt(ticketCount);
			if (100 > tcInt && tcInt >= 50)
				return "T50-100";
			if (50 >= tcInt && tcInt >=30)
				return "T30-50";
			if (30 >= tcInt && tcInt >=10)
				return "T10-30";
			return "T" + ticketCount;
		}
	}*/
	
	public static String getAirFlightSimpleTicketCount(String ticketCount){
		int tCount = Integer.parseInt(ticketCount);
		if (0 < tCount && tCount <= 5) {
			return "T1-5";
		}else if (5 < tCount && tCount <= 10) {
			return "T5-10";
		}else if (10 < tCount && tCount <= 20){
			return "T10-20";
		}else if (20 < tCount && tCount <= 30){
			return "T20-30";
		}else if (30 < tCount && tCount <= 50){
			return "T30-50";
		}else if (50 < tCount && tCount <= 100){
			return "T50-100";
		}
		return "T" + ticketCount;
	}
	

	
	/**
	 * 获取航线一个简单余票量标记的String (后期可配置动态)<br/>
	 * Examples <blockquote>
	 * <pre>
	 * getSimpleTicketCount(String ticketCount) 例：21
	 * </pre>
	 * </blockquote>
	 * @return  例：T20
	 */
	public static String getAirLineSimpleTicketCount(String ticketCount){
		if (ticketCount.length() == 1) { //如果票量少于10张以下 算1-10 张区间 只当一个区间
			return "T1-10";
		} else {
			int tcInt = Integer.parseInt(ticketCount);
			if (200 > tcInt && tcInt >= 100)
				return "T200-100";
			if (100 > tcInt && tcInt >= 50)
				return "T100-50";
			if (50 >= tcInt && tcInt >= 30)
				return "T50-30";
			if (30 >= tcInt && tcInt >= 20)
				return "T30-20";
			if (20 >= tcInt && tcInt >= 10)
				return "T20-10";
			return "T" + ticketCount;
		}
		
	}
	 
	
	
	/**
	 * 获取一个加了[]的String<br/>
	 * Examples <blockquote>
	 * <pre>
	 * getBracketsStr(String str) 例：周二
	 * </pre>
	 * </blockquote>
	 * @return  例：[周二]
	 */
	public static String getBracketsStr(String str){
		return "[" + str + "]";
	}
	
	
	public static String getBracketsString(String str){
		return str;
	}
	/**
	 * 拼接2个参数 加上-<br/>
	 * Examples <blockquote>
	 * <pre>
	 * getMsStr(String str1,String str2) 例：深圳，北京
	 * </pre>
	 * </blockquote>
	 * @return  例：深圳-北京
	 */
	public static String getMsStr(String str1,String str2){
		return str1 + "-" + str2;
	}
	
	/**
	 * 获取一个加了“出发”的String<br/>
	 * Examples <blockquote>
	 * <pre>
	 * getBracketsStr(String str) 例：18:22
	 * </pre>
	 * </blockquote>
	 * @return  例：18:22出发
	 */
	@SuppressWarnings("deprecation")
	public static String getCfStr(String str){
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		Date date = null;
		try {
			date = sdf.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return sdf.format(date).toString()+ "出发  ";
	}
	
	/**
	 * 获取一个加了“出发”的String<br/>
	 * @return  例：2017-05-10出发
	 */
	public static String getTakeOffWeekStr(String weekday){
		return weekday + "出发），";
	}
	
	/**
	 * 
	 * @return 
	 * <p>2017-04-07</p>
	 */
	@SuppressWarnings("deprecation")
	public static String getYeay(String newDate){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = sdf.parse(newDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sdf.format(date).toString();
	}
	
	/**
	 * 将时间字符串转换成带格式的时间<br/>
	 * Examples <blockquote>
	 * <pre>
	 * getStandardTime(String str) 例：1800
	 * </pre>
	 * </blockquote>
	 * @return  例：18:00
	 */
	public static String getStandardTime(String timeStr){
		String hour = timeStr.substring(0, 2);
		String min = timeStr.substring(2);
		return hour + ":" + min;
	}
	
	/**
	 * 根据飞行计划时间返回标识 相隔 1 3 7 天<br/>
	 * Examples <blockquote>
	 * <pre>
	 * getDateFlag(String flight) 例：2016-12-15
	 * </pre>
	 * </blockquote>
	 * @return  例：相隔1天 D1 相隔3天 D3  相隔7天 D7
	 */
	public static String getDateFlag(String flightDate){
		String flag = "";
		try {
			Calendar planDate = Calendar.getInstance();
			planDate.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(flightDate));
			//计划中的日期
			int planDay = planDate.get(Calendar.DAY_OF_YEAR);
			//当前时间
			Calendar currentDate = Calendar.getInstance();
			//当前日期
			int currentDay = currentDate.get(Calendar.DAY_OF_YEAR);
			
			if ((planDay - currentDay) == 1 || (planDay - currentDay) == 3 || (planDay - currentDay) >= 7) {
				if ((planDay - currentDay) >= 7)
					flag = "D7";
				else
					flag = "D" + (planDay - currentDay);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	/**
	 * 传入的float 返回保留小数点后一位的字符串 <br/>
	 * Examples <blockquote>
	 * <pre>
	 * getDiscountMath(float dis) 例：6.100004
	 * </pre>
	 * </blockquote>
	 * @return  例：6.1
	 */
	public static String getDiscountMath(float dis){
		DecimalFormat decimalFormat = new DecimalFormat("##0.0");
		return decimalFormat.format(dis);
	}
	
	/**
	 * 获取redis需要保存数据的秒 根据当前时间计算 <br/>
	 * Examples <blockquote>
	 * <pre>
	 * getRedisSecond
	 * </pre>
	 * </blockquote>
	 * @return  例：1000l
	 */
	/*public static long getRedisSecond(String flight){
		Date date = getStrOfDate(flight);
		long second = 0l;
		Calendar nowCalendar = Calendar.getInstance();
		Calendar flightCalendar = Calendar.getInstance();
		flightCalendar.setTime(date);
		if (nowCalendar.get(Calendar.DAY_OF_YEAR) == flightCalendar.get(Calendar.DAY_OF_YEAR)){
			//如果是当天的 只保存到第二天00:00
			second = ((24 - nowCalendar.get(Calendar.HOUR_OF_DAY)) * 3600) - (nowCalendar.get(Calendar.MINUTE) * 60);
		} else {
			//如果不是当天的 保存到航班起飞之后
			second = (flightCalendar.get(Calendar.DAY_OF_YEAR) - nowCalendar.get(Calendar.DAY_OF_YEAR)) * 24 * 3600; 
		}
		
		return second;
	}*/
	
	/**
	 * 校验当前时间是否是 23-06 时间段<br/>
	 * Examples <blockquote>
	 * <pre>
	 * isNightTime
	 * </pre>
	 * </blockquote>
	 * @return  例：true of false
	 */
	public static boolean isNightTime(){
		boolean f = false;
		Calendar calendar = Calendar.getInstance();
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		if (hour >= 23 || hour < 6)
			f = true;
		return f;
	}
	
	/**
	 * 返回一个替换了“-”的字符<br/>
	 * Examples <blockquote>
	 * <pre>
	 * replaceStr(String str) 2016-12-12
	 * </pre>
	 * </blockquote>
	 * @return  例：20161212
	 */
	public static String replaceStr(String str){
		return str.replace("-", "");
	}
	
	/**
	 * 返回一个替换舱位对应的中文名<br/>
	 * Examples <blockquote>
	 * <pre>
	 * getCabinName(String cabin) Y | C | F
	 * </pre>
	 * </blockquote>
	 * @return  例：经济舱
	 */
	public static String getCabinName(String cabin){
		Map<String, String> cabinMap = new HashMap<String, String>();
		cabinMap.put("Y", "经济舱");
		cabinMap.put("C", "商务舱");
		cabinMap.put("F", "头等舱");
		cabinMap.put("S", "总余票");
		String cabins = cabinMap.get(cabin);
		if (cabins == null ||"".equals(cabins)) 
			return cabinMap.put("Y", "经济舱");
		else {
			return cabins;
		}
	}
	
	/**
	 * 返回一个价格对应的折扣信息<br/>
	 * Examples <blockquote>
	 * <pre>
	 * getPriceDiscount(Float discountValue)
	 * </pre>
	 * </blockquote>
	 * @return  例：全价 | X折
	 */
	public static String getPriceDiscount(Float discountValue){
		String discount = "";
		if (discountValue >= 1.00)
			discount = "全价";
		else 
			discount = getDiscountMath((discountValue * 10)) + "折";
		return discount;
	}
	
	/**
	 * 返回一个AV对应的票价信息<br/>
	 * Examples <blockquote>
	 * <pre>
	 * getAvCount(String av)
	 * </pre>
	 * </blockquote>
	 * @return  例：9张以上
	 */
	public static String getAvCount(String av){
		String ticketNumber = "";
		if (av.equals("A"))
			ticketNumber = "9张以上";
		else
			ticketNumber = av + "张";
		
		return ticketNumber;
	}
	
	/**
	 * 日期格式转国际日期格式 <br>
	 * Examples <blockquote>
	 * 
	 * <pre>
	 * personXMLDate(2016-10-14) 14OCT16
	 * </pre>
	 * 
	 * </blockquote>
	 * 
	 * @param date
	 *            例： 2016-10-14
	 * @return 例：14OCT16
	 */
	public static String personPNRDate(String date) {
		LOGGER.info("需要转换的时间："+date);
		Map<String, String> date_map = new HashMap<String, String>();// 放月份
		date_map.put("一月", "JAN");// 1
		date_map.put("二月", "FEB");// 2
		date_map.put("三月", "MAR");// 3
		date_map.put("四月", "APR");// 4
		date_map.put("五月", "MAY");// 7
		date_map.put("六月", "JUN");// 6
		date_map.put("七月", "JUL");// 7
		date_map.put("八月", "AUG");// 8
		date_map.put("九月", "SEP");// 9
		date_map.put("十月", "OCT");// 10
		date_map.put("十一月", "NOV");// 11
		date_map.put("十二月", "DEC");// 12
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMMyy");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		try {
			int mout = Integer.parseInt(date.substring(5, 7));
			date = sdf.format((sdf2.parse(date)));
			String mm = "";
			if (mout > 10) {
				mm = date.substring(2, 5);
			} else {
				mm = date.substring(2, 4);
			}
			String ip;
			try {
				ip = InetAddress.getLocalHost().getHostAddress();
				if (ip.contains("10.5.146.39") || ip.contains("10.5.146.40")) {
					return date.toUpperCase();
				}else {
					LOGGER.info("需要转换的时间："+date);
					String mm_zho = "";
					if (!"".equals(mm)) {
						LOGGER.info("需要转换的时间："+mm);
						mm_zho = date_map.get(mm);
						date = date.replace(mm, mm_zho);
					}
				}
			} catch (UnknownHostException e) {
				LOGGER.error(e.getMessage());
			}
		} catch (ParseException e) {
			LOGGER.error(e.getMessage());
		}
		return date;
	}
	
	/**
	 * 转换标准日期（如"15JUL12" 转换成 "2012-07-15"）
	 * 
	 * @param strDate
	 * @return
	 */
	public static String changeHostFormatDateToStandards(String strDate) {
		if (strDate == null)
			return null;
		String standardDate = "";
		strDate = strDate.trim();
		if (strDate.length() == 0)
			return null;
		String year = strDate.substring(5, 7);

		if (Integer.parseInt(year) > 90) {
			year = (new StringBuffer()).append("19").append(year).toString();
		} else
			year = (new StringBuffer()).append("20").append(year).toString();

		String month = strDate.substring(2, 5);
		String day = strDate.substring(0, 2);

		String mon = "";

		if (month.equals("JAN"))
			mon = "01";
		else if (month.equals("FEB"))
			mon = "02";
		else if (month.equals("MAR"))
			mon = "03";
		else if (month.equals("APR"))
			mon = "04";
		else if (month.equals("MAY"))
			mon = "05";
		else if (month.equals("JUN"))
			mon = "06";
		else if (month.equals("JUL"))
			mon = "07";
		else if (month.equals("AUG"))
			mon = "08";
		else if (month.equals("SEP"))
			mon = "09";
		else if (month.equals("OCT"))
			mon = "10";
		else if (month.equals("NOV"))
			mon = "11";
		else if (month.equals("DEC"))
			mon = "12";

		standardDate = (new StringBuilder()).append(year.trim()).append("-").append(mon.trim()).append("-").append(day.trim()).toString();

		return standardDate;
	}
	
	/**
	 * list 去重
	 * @param flightPlans
	 * @return
	 */
	public static List removeDuplicate(List flightPlans){      
    	HashSet hashSet = new HashSet(flightPlans);      
    	flightPlans.clear();      
    	flightPlans.addAll(hashSet);      
    	return flightPlans;
    }
	
	/***
	 * 去掉航线机场名称
	 * @param city
	 * @return
	 */
	public static String getCityName(String city){
		if (city.contains(Constant.PEK_IS_TURE)) {
			return Constant.PEK_IS_TURE;
		}else if(city.contains(Constant.SHA_IS_TUEN)){
			return Constant.SHA_IS_TUEN;
		}else {
			return city;
		}
	}
	

	
}
