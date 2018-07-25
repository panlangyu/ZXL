package pro.bechat.wallet.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import pro.bechat.wallet.interceptor.TokenInterceptor;

/**
 * create WebMvcConfig by huc
 * 2018/4/19  下午4:09
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
        registry.addInterceptor(getTokenHeader())
                .addPathPatterns("/user/*")
                .excludePathPatterns(
                        "/robots.txt");
    }
    //token 在header的拦截器
    @Bean
    public HandlerInterceptor getTokenHeader(){
        return new TokenInterceptor();
    }
}
