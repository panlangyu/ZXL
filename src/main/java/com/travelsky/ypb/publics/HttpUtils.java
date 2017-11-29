package com.travelsky.ypb.publics;

import com.alibaba.fastjson.JSONObject;
import com.travelsky.ypb.domain.log.Log;
import com.travelsky.ypb.domain.xml.FareInterface;
import org.apache.log4j.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;

/**
 * <p>Tilte:Http访问工具类</p>
 * <p>Description:用于访问http接口</p>
 * @author huc
 */
public final class HttpUtils {

    private static final Logger LOGGER = Logger.getLogger(HttpUtils.class);
    // 218.17.237.47:7080 获取最低票价URL
    private static final String LOWES_PRICE_URL = "http://218.17.237.47:7080/YPBShopping/xml/getLowestPriceOfPlan";
    private static final String GRAB_TICKET_URL = "http://10.128.150:7080/ycbback/gradTicket/noticeBuyTicket.do?";
    //内网
    //private static final String LOWES_PRICE_URL = "http://10.128.150.122:7080/YPBShopping/xml/getLowestPriceReal";

    /**
     * 查询最低票价
     *
     * @param
     * @return FareInterface
     */
    public static FareInterface httpLowestPrice(JSONObject object) {
        FareInterface fareInterface = null;
        String params = "key=" + object.toJSONString();
        try {
            String lowestStr = httpPost(LOWES_PRICE_URL, params);
            if (lowestStr.contains("100102")) {
                return fareInterface;
            } else {
                JAXBContext context = JAXBContext.newInstance(FareInterface.class);
                Unmarshaller unmarshaller = context.createUnmarshaller();
                Object obj = unmarshaller.unmarshal(new StringReader(lowestStr));
                if (obj != null) {
                    fareInterface = (FareInterface) obj;
                }
            }
        } catch (Exception e) {

        }
        return fareInterface;
    }


    /**
     * 向指定 URL 发送POST方法的请求
     * @param url   发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String httpPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            URLConnection conn = realUrl.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setConnectTimeout(50000);
            out = new PrintWriter(conn.getOutputStream());
            out.print(param);
            out.flush();
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
        }
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
            }
        }
        return result;
    }


    /**
     * 通知抢票计划
     *
     * @param
     * @return
     */
    public static String gradTicket(String departureAirport, String arrivalAirport, String flightDate, String flightNo, String BClass) {
        String param = "departureAirport=" + departureAirport + "&arrivalAirport=" + arrivalAirport + "&flightDate=" + flightDate + "&clazz=" + BClass + "&flightNo=" + flightNo;
        String httpPost = httpPost(GRAB_TICKET_URL, param);
        return httpPost;
    }


}
