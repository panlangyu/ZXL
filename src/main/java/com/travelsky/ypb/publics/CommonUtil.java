package com.travelsky.ypb.publics;

import org.apache.log4j.Logger;

import java.lang.reflect.Field;
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
@SuppressWarnings("ALL")
public final class CommonUtil {

    private CommonUtil() {
    }

    private static final Logger LOGGER = Logger.getLogger(CommonUtil.class);

    public static String getMessageEndInfoTime(String str) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
        return "["+str+":" + sdf.format(date) + "]。";
    }

    public static Map<String, String> objectToMap(Object obj) {
        Map<String, String> map = new HashMap<>();

        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field f : fields) {
            f.setAccessible(true);
            try {
                if (f.get(obj) != null)
                    map.put(f.getName(), String.valueOf(f.get(obj)));

            } catch (Exception e) {
                LOGGER.error(e.getMessage());
            }
        }
        return map;
    }

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
                        break;
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return tR;
    }

    public static String getDayOfWeek(String date) {
        SimpleDateFormat newSdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dt = null;
        try {
            dt = newSdf.parse(date);
        } catch (ParseException e) {
            LOGGER.error(e.getMessage());
        }
        String[] weekArr = new String[]{"周日", "周一", "周二", "周三", "周四", "周五",
                "周六"};
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dt);
        int weekDay = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        return weekArr[weekDay];
    }

    public static String getStrOfDate(String date) {
        return date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6, 8);
    }

    public static String getCfStr(String str) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Date date = null;
        try {
            date = sdf.parse(str);
        } catch (ParseException e) {
            LOGGER.error(e.getMessage());
        }
        return String.valueOf(sdf.format(date));
    }

    public static String getDiscountMath(float dis) {
        DecimalFormat decimalFormat = new DecimalFormat("##0.0");
        return decimalFormat.format(dis);
    }

    public static String personPNRDate(String date) {
        Map<String, String> date_map = new HashMap<>();// 放月份
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
            String mm_zho = "";
            if (!"".equals(mm)) {
                mm_zho = date_map.get(mm);
                date = date.replace(mm, mm_zho);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return date;
    }

    public static List removeDuplicate(List flightPlans) {
        HashSet hashSet = new HashSet(flightPlans);
        flightPlans.clear();
        flightPlans.addAll(hashSet);
        return flightPlans;
    }




}
