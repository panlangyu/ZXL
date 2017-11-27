package com.travelsky.ypb.publics;

import com.alibaba.fastjson.JSONObject;
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
//	 private static final String LOWES_PRICE_URL = "http://218.17.237.47:7080/YPBShopping/xml/getLowestPriceOfPlan";
	// "http://122.119.111.249:7080/YPBShopping/xml/getLowestPriceOfPlan"
	//外网
	//private static final String LOWES_PRICE_URL = "http://218.17.237.47 :7080/YPBShopping/xml/getLowestPriceReal";
	
	//获取舱位表
	private static final String CABIN_POLICY = "http://10.128.150:8082/YpbManage/app/intCabinstandardBasic/getCabinList";
	
	
	private static final String CABIN_POLICY_WAI = "http://218.17.237.47:8082/YpbManage/app/intCabinstandardBasic/getCabinList";

	
	private static final String LOWES_PRICE_URL_WAI = "http://218.17.237.47:7080/YPBShopping/xml/getLowestPriceOfPlan";
	
	private static final String GRAB_TICKET_URL_WAI = "http://218.17.237.47:7080/ycbback/gradTicket/noticeBuyTicket.do?";
	
	private static final String GRAB_TICKET_URL = "http://10.128.150:7080/ycbback/gradTicket/noticeBuyTicket.do?";

	/** 新接口 **/
//	private static final String LOWES_PRICE_URL_WAI = "http://218.17.237.47:7080/YpbManage/app/intGetFlightData/getLowestPriceXml";
	
	private static final String LOWES_PRICE_URL_ALL = "http://218.17.237.47:7080/YpbManage/app/intGetFlightData/getLowestPriceXml";
	//内网
	private static final String LOWES_PRICE_URL = "http://10.128.150.122:7080/YPBShopping/xml/getLowestPriceReal";
	/**
	 * 查询最低票价
	 * @param
	 * @return FareInterface
	 */
	public static FareInterface httpLowestPrice(JSONObject object) {
		LOGGER.info("httpLowestPrice begin ...");
		
		FareInterface fareInterface = null;
		String host = LOWES_PRICE_URL;
		LOGGER.info("获取最低价请求接口地址："+ host);
//		String params = "key={\"rcMachineType\":\"pc\",\"rcVersion\":\"2.1.2\",\"rcHannelId\":\"WEB\",\"rcKey\":\"acbe255a9ba6bb3dcde608a53ab0f2c4\",\"rcAccessToken\":\"okaccept\",\"rcMachienId\":\"121212\",\"sign\":\"ac59075b964b0715\",\"rcParams\":"+object.toString()+"}";
		String params = "key="+object.toJSONString();
		LOGGER.info("获取最低价请求接口地址："+ host);
		try {
			LOGGER.info("params >>>>> " + params);
			String lowestStr = httpPost(host, params);
			LOGGER.info("result >>>>> " + lowestStr);
			// 如果显示无可用AV 则直接返回null
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
			LOGGER.info("httpLowestPrice end ...");
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return fareInterface;
	} 
	
	
	/**
	 * 向指定 URL 发送POST方法的请求
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String httpPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setConnectTimeout(50000);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream(),"UTF-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
			LOGGER.info("请求状态："+conn.getHeaderFields());
		} catch (Exception e) {
			LOGGER.error("httpPost Error : " + e.getMessage());
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				LOGGER.error(ex.getMessage());
			}
		}
		return result;
	}
	
	
	/**
	 * 向指定 URL 发送POST方法的请求
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String httpPostEmail(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			String key = "key={\"rcMachineType\":\"pc\",\"rcVersion\":\"2.1.2\",\"rcHannelId\":\"WEB\",\"rcKey\":\"acbe255a9ba6bb3dcde608a53ab0f2c4\",\"rcAccessToken\":\"okaccept\",\"rcMachienId\":\"121212\",\"sign\":\"ac59075b964b0715\",\"rcParams\":"+param+"}";
			LOGGER.info("发送邮件参数："+key);
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行 
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setConnectTimeout(50000);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(key);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream(),"UTF-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
			//key={"rcMachineType":"pc","rcVersion":"2.1.2","rcHannelId":"WEB","rcKey":"acbe255a9ba6bb3dcde608a53ab0f2c4","rcAccessToken":"okaccept","rcMachienId":"121212","sign":"ac59075b964b0715","rcParams":{"recipient":"admin@geeknets.com","context":"fffffff","title":"f信息中心异常-余票宝"}}
			LOGGER.info("请求状态："+conn.getHeaderFields());
			LOGGER.info("status："+line);
		} catch (Exception e) {
			LOGGER.error("httpPost Error : " + e.getMessage());
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				LOGGER.error(ex.getMessage());
			}
		}
		return result;
	}


	/**
	 * 通知抢票计划
	 * @param
	 * @return
	 */
	public static String gradTicket(String departureAirport,String arrivalAirport ,String flightDate, String flightNo ,String BClass){
		String param = "departureAirport="+departureAirport+"&arrivalAirport="+arrivalAirport+"&flightDate="+flightDate+"&clazz="+BClass+"&flightNo="+flightNo;
		String httpPost = httpPost(GRAB_TICKET_URL, param);
		return httpPost;
	} 

	
	
	
	
}
