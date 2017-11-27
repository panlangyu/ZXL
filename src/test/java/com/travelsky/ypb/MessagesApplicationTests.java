package com.travelsky.ypb;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MessagesApplication.class)
public class MessagesApplicationTests {

	String body5502 = "##5502##余票紧张消息-按航班##20170522131315##51898240fb3b470a96b13b6782ce3b6e##SC_TICKET_CHANGE##null##{\n" +
			"\t\"className\":\"com.travelsky.umetrip.event.domain.SCTicketChangeEventBody\",\n" +
			"\t\"instance\":{\n" +
			"\t\t\"flightDate\":\"20171130\",\n" +
			"\t\t\"flightNo\":\"CA1823\",\n" +
			"\t\t\"departureAirport\":\"PEK\",\n" +
			"\t\t\"arrivalAirport\":\"YIH\",\n" +
			"\t\t\"airlineName\":\"CA\",\n" +
			"\t\t\"ticketCount\":\"88\",\n" +
			"\t\t\"cabinTicket\":{\n" +
			"\t\t\t\"E\":\"0\",\n" +
			"\t\t\t\"F\":\"2\",\n" +
			"\t\t\t\"G\":\"0\",\n" +
			"\t\t\t\"A\":\"0\",\n" +
			"\t\t\t\"B\":\"0\",\n" +
			"\t\t\t\"L\":\"0\",\n" +
			"\t\t\t\"M\":\"0\",\n" +
			"\t\t\t\"N\":\"0\",\n" +
			"\t\t\t\"O\":\"1\",\n" +
			"\t\t\t\"H\":\"0\",\n" +
			"\t\t\t\"J\":\"0\",\n" +
			"\t\t\t\"K\":\"0\",\n" +
			"\t\t\t\"U\":\"0\",\n" +
			"\t\t\t\"T\":\"0\",\n" +
			"\t\t\t\"W\":\"0\",\n" +
			"\t\t\t\"V\":\"0\",\n" +
			"\t\t\t\"Q\":\"0\",\n" +
			"\t\t\t\"P\":\"0\",\n" +
			"\t\t\t\"S\":\"0\",\n" +
			"\t\t\t\"R\":\"0\",\n" +
			"\t\t\t\"Y\":\"85\",\n" +
			"\t\t\t\"X\":\"0\",\n" +
			"\t\t\t\"Z\":\"0\"\n" +
			"\t\t}\n" +
			"\t}\n" +
			"}\n";


	String body5501 = "##5501##余票紧张消息-按航线##20170427151755##b71e866b35c3456a8c0788aaff6f595d##SC_TICKET_CHANGE##null##{\n" +
			"\t\"className\":\"com.travelsky.umetrip.event.domain.SCTicketChangeEventBody\",\n" +
			"\t\"instance\":{\n" +
			"\t\t\"flightDate\":\"20170523\",\n" +
			"\t\t\"departureAirport\":\"SZX\",\n" +
			"\t\t\"arrivalAirport\":\"PEK\",\n" +
			"\t\t\"airlineName\":\"ALL\",\n" +
			"\t\t\"takeoffBegin\":\"00\",\n" +
			"\t\t\"takeoffEnd\":\"24\",\n" +
			"\t\t\"ticketCount\":\"88\"\n" +
			"\t}\n" +
			"}";


	String body5508 = "##5508##余票量提醒 (按航班)##20170523091231##6b50fa86b20d4ec8ae2ceb5aa1d8e050##SC_TICKET_CHANGE##null##{\n" +
			"\t\"className\":\"com.travelsky.umetrip.event.domain.SCTicketChangeEventBody\",\n" +
			"\t\"instance\":{\n" +
			"\t\t\"planid\":\"25130\"\n" +
			"\t}\n" +
			"}";

	String body5507 = "##5507##余票量提醒 (按航线)##20170523091433##b28510f974044a54918c90d1276f2b15##SC_TICKET_CHANGE##null##{\n" +
			"\t\"className\":\"com.travelsky.umetrip.event.domain.SCTicketChangeEventBody\",\n" +
			"\t\"instance\":{\n" +
			"\t\t\"planid\":\"25131\"\n" +
			"\t}\n" +
			"}";

	@Autowired // 也可以注入JmsTemplate，JmsMessagingTemplate对JmsTemplate进行了封装
	private JmsMessagingTemplate jmsTemplate;

	@Value("${activeMq:unknown}")
	private String queue;

	@Test
	public void contextLoads() {
		jmsTemplate.convertAndSend(queue,body5502);
	}

}
