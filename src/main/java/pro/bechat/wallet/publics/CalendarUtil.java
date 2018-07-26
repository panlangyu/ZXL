package pro.bechat.wallet.publics;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日历工具
 */
public class CalendarUtil {


    /**
     * 组装日期 月份 查询 01-30(31)
     * @param startTime
     * @return
     */
    public static String [] assemblyDate(String startTime)throws Exception{

        String [] str = new String[2];

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");

        Date date = sdf.parse(startTime);

        Calendar cal = Calendar.getInstance();//获取当前日期

        cal.setTime(date);

        cal.set(Calendar.DAY_OF_MONTH,1);    //设置为1号,当前日期既为本月第一天

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");

        startTime =  sdf1.format(cal.getTime());

        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));    //拿到本月最后1天

        String endTime = sdf1.format(cal.getTime());

        str[0] = startTime;
        str[1] = endTime;

        return str;
    }


}
