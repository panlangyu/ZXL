package com.travelsky.ypb.publics;

import com.alibaba.fastjson.JSONObject;
import com.travelsky.ypb.configuration.AppConfig;
import com.travelsky.ypb.domain.log.Log;
import com.travelsky.ypb.domain.xml.FareInterface;
import com.travelsky.ypb.umeticket.http.HttpClientManager;
import com.travelsky.ypb.umeticket.http.HttpClientManagerImp;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * <p>Tilte:Http访问工具类</p>
 * <p>Description:用于访问http接口</p>
 * @author huc
 */
@SuppressWarnings("ALL")
@Component
@Order(2)
public class HttpClient {

    @Autowired
    protected AppConfig appConfig;

    HttpClientManager http = HttpClientManagerImp.getInstance();

    @Value("${com.travelsky.ypb.airLineCabinPolicy:unknown}")
    public String policyUrl;

    /**
     * 查询最低票价
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
            Log.i(this.getClass(),"httpLowestPrice",e);
        }
        return fareInterface;
    }

    public String getToken(String agrs){

        return http.httpGetRequest(appConfig.getGetTokenUrl()+agrs);
    }

    public String getAirlineCabinPolicy(){
        return httpPost(policyUrl,"key="+appConfig.getRequestParams());
    }

    public String httpPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        InputStreamReader inputStreamReader = null;
        InputStream inputStream = null;
        StringBuilder result = new StringBuilder();
        try {
            URL realUrl = new URL(url);
            URLEncoder.encode(url,"UTF-8");
            HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
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
            inputStream = conn.getInputStream();

            inputStreamReader =  new InputStreamReader(inputStream ,"UTF-8");
            in = new BufferedReader(inputStreamReader);
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            Log.i(this.getClass(),"httpPost",e);
        }
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
                if (inputStream != null){
                    inputStream.close();
                }
                if (inputStreamReader != null){
                    inputStreamReader.close();
                }
            } catch (IOException ex) {
                Log.i(this.getClass(),"httpPost",ex);
            }
        }
        return String.valueOf(result);
    }

    public org.apache.commons.httpclient.HttpClient getHttpClient(){
        org.apache.commons.httpclient.HttpClient httpClient = new org.apache.commons.httpclient.HttpClient();
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
        httpClient.getHttpConnectionManager().getParams().setSoTimeout(5000);
        return httpClient;
    }

    public HttpMethod postMethod(String url, NameValuePair[] param){
        PostMethod post = new PostMethod(url);
        post.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=utf-8");
        post.setRequestBody(param);
        post.releaseConnection();
        return post;
    }

    public String gradTicket(String departureAirport, String arrivalAirport, String flightDate, String flightNo, String BClass) {
        String param = "departureAirport=" + departureAirport + "&arrivalAirport="
                + arrivalAirport + "&flightDate=" + flightDate + "&clazz=" + BClass
                + "&flightNo=" + flightNo;
        return httpPost(appConfig.getGrabTicketUrl(), param);
    }


}
