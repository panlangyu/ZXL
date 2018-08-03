package pro.bechat.wallet;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@MapperScan("pro.bechat.wallet.domain.dao")
@ComponentScan("pro.bechat.wallet.*")
//EnableScheduling
public class MessagesApplication {

    public static void main(String[] args){
        SpringApplication.run(MessagesApplication.class, args);
    }


}
