package pro.bechat.wallet.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;

/**
 * Created by huc on 2017/11/29.
 */
@Configuration
@PropertySource("classpath:wallet.properties")
@ConfigurationProperties(prefix = "pro.bechat.wallet")
@Order(5)
public class AppConfig {

}
