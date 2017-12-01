package com.travelsky.ypb.publics;

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

	/**
	 * @return
	 * [消息时间:4-07 12:12]。
	 */
	public static String getMessageEndInfoTime(){
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
		return "[消息时间:"+sdf.format(date)+"]。";
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
	public static <T> T mapToObject(Map map, Class<T> t) {
		if (map.size() == 0) return null;
		T tR = null;
		Set keySet = map.keySet();
		Iterator keysI = keySet.iterator();
		try {
			tR = t.newInstance();
			while (keysI.hasNext()) {
				String key = (String) keysI.next();
				Field[] fields = t.getDeclaredFields();
				Field.setAccessible(fields, true);
				for (Field field : fields) {
					if (field.getName().equals(key)) {
						field.set(tR, Integer.parseInt((String) map.get(key)));
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
	public static String getDayOfWeek(String date) {
		SimpleDateFormat newSdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dt = null;
		try {
			dt = newSdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String[] weekArr = new String[] { "周日", "周一", "周二", "周三", "周四", "周五",
				"周六" };
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dt);
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
	



	/**
	 * 获取一个加了“出发”的String<br/>
	 * Examples <blockquote>
	 * <pre>
	 * getBracketsStr(String str) 例：18:22
	 * </pre>
	 * </blockquote>
	 * @return  例：18:22出发
	 */
	public static String getCfStr(String str){
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		Date date = null;
		try {
			date = sdf.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return sdf.format(date).toString();
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
	 * 日期格式转国际日期格式 <br>
	 * Examples <blockquote>
	 * personXMLDate(2016-10-14) 14OCT16
	 * </blockquote>
	 * @param date
	 *            例： 2016-10-14
	 * @return 例：14OCT16
	 */
	public static String personPNRDate(String date) {
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
					String mm_zho = "";
					if (!"".equals(mm)) {
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

	
}
