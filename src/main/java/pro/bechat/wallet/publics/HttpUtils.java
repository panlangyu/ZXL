package pro.bechat.wallet.publics;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @Author ch
 * @Date Create int 2018/7/24 16:47
 * @email 869360026@qq.com
 */
public class HttpUtils {
    /**
     * @param url    :请求接口
     * @param params :请求参数
     * @return 返回json结果
     */
    public static String post(String url, Map<String, String> params) throws IOException {
        URL postUrl = new URL(url);
        // 打开连接
        HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();
        // 设置是否向connection输出，因为这个是post请求，参数要放在
        // http正文内，因此需要设为true
        connection.setDoOutput(true);
        connection.setDoInput(true);
        // 默认是 GET方式
        connection.setRequestMethod("POST");

        // Post 请求不能使用缓存
        connection.setUseCaches(false);
        //设置本次连接是否自动重定向
        connection.setInstanceFollowRedirects(true);
        // 配置本次连接的Content-type，配置为application/x-www-form-urlencoded的
        // 意思是正文是urlencoded编码过的form参数
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.connect();
        DataOutputStream out = new DataOutputStream(connection
                .getOutputStream());
        // 正文，正文内容其实跟get的URL中 '? '后的参数字符串一致
        String content = mp2String(params);
        content =  content.substring(1,content.length());
        // DataOutputStream.writeBytes将字符串中的16位的unicode字符以8位的字符形式写到流里面
        out.writeBytes(content);
        //流用完记得关
        out.flush();
        out.close();
        //获取响应
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuffer sb = new StringBuffer();
        String line = "";
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        reader.close();
        //该干的都干完了,记得把连接断了
        connection.disconnect();
        return sb.toString();
    }

    private static String mp2String(Map<String, String> maps) throws UnsupportedEncodingException {
        StringBuffer sb = new StringBuffer();
        Set<Map.Entry<String, String>> entries = maps.entrySet();
        Iterator<Map.Entry<String, String>> iterator = entries.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            sb.append("&"+entry.getKey()+"="+URLEncoder.encode(entry.getValue(),"utf-8"));
        }
        return sb.toString();
    }

}
