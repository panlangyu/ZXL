package pro.bechat.wallet.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import pro.bechat.wallet.publics.TokenUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Author ch
 * @Date Create int 2018/7/25 14:04
 * @email 869360026@qq.com
 */
public class TokenInterceptor implements HandlerInterceptor {
    private final static Logger logger = LoggerFactory.getLogger(TokenInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        String requestURI = httpServletRequest.getRequestURI();
        String tokenStr = httpServletRequest.getHeader("token");
        String token = "";
        if (requestURI.contains("/user/")) {
            if(requestURI.contains("/user/login")){
                return true;
            }
            token = httpServletRequest.getHeader("token");
            if (token == null && tokenStr == null) {
                System.out.println("real token:======================is null");
                String str = "{'errorCode':801,'message':'缺少token，无法验证','data':null}";
                dealErrorReturn(httpServletRequest, httpServletResponse, str);
                return false;
            }
            if (tokenStr != null) {
                token = tokenStr;
            }
            try {
                token = TokenUtil.updateToken(token);
            }catch (Exception e){
                System.out.println("real token:======================is null");
                String str = "{'errorCode':801,'message':'token错误','data':null}";
                dealErrorReturn(httpServletRequest, httpServletResponse, str);
                return false;
            }
        }
        httpServletResponse.setHeader("token", token);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

    // 检测到没有token，直接返回不验证
    public void dealErrorReturn(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object obj) {
        String json = (String) obj;
        PrintWriter writer = null;
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("text/html; charset=utf-8");
        try {
            writer = httpServletResponse.getWriter();
            writer.print(json);

        } catch (IOException ex) {
            logger.error("response error", ex);
        } finally {
            if (writer != null)
                writer.close();
        }
    }

    public static class HttpRequstUtils {
        private static HttpServletRequest request;
        private static HttpServletResponse response;

        private HttpRequstUtils(HttpServletRequest request, HttpServletResponse response) {
            this.request = request;
            this.response = response;
        }

        public static String getToken() {
            return request.getParameter("token");
        }

        public static void setToken(String token) {
            response.setHeader("token", token);

        }
    }
}
