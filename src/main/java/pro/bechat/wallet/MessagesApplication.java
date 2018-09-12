package pro.bechat.wallet;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication()
@MapperScan("pro.bechat.wallet.domain.dao")
@ComponentScan("pro.bechat.wallet.*")
public class MessagesApplication {

    public static void main(String[] args){
        SpringApplication.run(MessagesApplication.class, args);
    }


}
