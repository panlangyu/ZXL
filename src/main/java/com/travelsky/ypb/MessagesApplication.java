package com.travelsky.ypb;

import com.travelsky.ypb.domain.service.flightplan.LightStatusSubScService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.travelsky.ypb.*"})
public class MessagesApplication {

	public static void main(String[] args) {
		ApplicationContext run = SpringApplication.run(MessagesApplication.class, args);
		System.out.println(String.valueOf(run.getBean(LightStatusSubScService.class)));
	}



}
