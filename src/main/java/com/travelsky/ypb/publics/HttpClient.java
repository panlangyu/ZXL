package com.travelsky.ypb.publics;

import com.alibaba.fastjson.JSONObject;
import com.travelsky.ypb.configuration.AppConfig;
import com.travelsky.ypb.domain.xml.FareInterface;
import com.travelsky.ypb.umeticket.http.HttpClientManager;
import com.travelsky.ypb.umeticket.http.HttpClientManagerImp;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
@Component
public final class HttpClient {

    @Autowired
    protected AppConfig appConfig;


    private static HttpClientManager http = HttpClientManagerImp.getInstance();

    /**
     * 查询最低票价
     *
     * @param
     * @return FareInterface
     */
    public FareInterface httpLowestPrice(JSONObject object) {
        FareInterface fareInterface = null;
        String params = "key=" + object.toJSONString();
        try {
            String lowestStr = httpPost(appConfig.getLowesPriceUrl(), params);
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

    public String getToken(String agrs){
        JSONObject json = new JSONObject();
        return http.httpGetRequest(appConfig.getGetTokenUrl()+agrs);
    }

    /**
     * 向指定 URL 发送POST方法的请求
     * @param url   发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public String httpPost(String url, String param) {
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

    public org.apache.commons.httpclient.HttpClient getHttpClient(){
        org.apache.commons.httpclient.HttpClient httpClient = new org.apache.commons.httpclient.HttpClient();
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
        httpClient.getHttpConnectionManager().getParams().setSoTimeout(5000);
        return httpClient;
    }

    public HttpMethod postMethod(String url, NameValuePair[] param) throws IOException{
        PostMethod post = new PostMethod(url);
        post.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=utf-8");
        post.setRequestBody(param);
        post.releaseConnection();
        return post;
    }


    /**
     * 通知抢票计划
     *
     * @param
     * @return
     */
    public String gradTicket(String departureAirport, String arrivalAirport, String flightDate, String flightNo, String BClass) {
        String param = "departureAirport=" + departureAirport + "&arrivalAirport=" + arrivalAirport + "&flightDate=" + flightDate + "&clazz=" + BClass + "&flightNo=" + flightNo;
        String httpPost = httpPost(appConfig.getGrabTicketUrl(), param);
        return httpPost;
    }


}
