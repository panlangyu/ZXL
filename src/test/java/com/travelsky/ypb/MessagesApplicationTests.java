package com.travelsky.ypb;

import com.alibaba.fastjson.JSONObject;
import com.travelsky.ypb.domain.message.Instance;
import com.travelsky.ypb.domain.model.TicketChangePrice;
import com.travelsky.ypb.domain.service.CityAirlineCodeService;
import com.travelsky.ypb.domain.service.MessagesBodyService;
import com.travelsky.ypb.domain.support.Support;
import com.travelsky.ypb.publics.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MessagesApplication.class)
public class MessagesApplicationTests {

	String body5502 = "##5502##余票紧张消息-按航班##20170522131315##51898240fb3b470a96b13b6782ce3b6e##SC_TICKET_CHANGE##null##{\n" +
			"\t\"className\":\"com.travelsky.umetrip.event.domain.SCTicketChangeEventBody\",\n" +
			"\t\"instance\":{\n" +
			"\t\t\"flightDate\":\"20180228\",\n" +
			"\t\t\"flightNo\":\"ZH9101\",\n" +
			"\t\t\"departureAirport\":\"SZX\",\n" +
			"\t\t\"arrivalAirport\":\"PEK\",\n" +
			"\t\t\"airlineName\":\"ZH\",\n" +
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
			"\t\t\"flightDate\":\"20180228\",\n" +
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
			"\t\t\"planid\":\"3300\"\n" +
			"\t}\n" +
			"}";

	String body5507 = "##5507##余票量提醒 (按航线)##20170523091433##b28510f974044a54918c90d1276f2b15##SC_TICKET_CHANGE##null##{\n" +
			"\t\"className\":\"com.travelsky.umetrip.event.domain.SCTicketChangeEventBody\",\n" +
			"\t\"instance\":{\n" +
			"\t\t\"planid\":\"3311\"\n" +
			"\t}\n" +
			"}";

	String body5503 = "##5503##余票量提醒 (按航线)##20170523091433##b28510f974044a54918c90d1276f2b15##SC_TICKET_CHANGE##null##{\n" +
			"\t\"className\":\"com.travelsky.umetrip.event.domain.SCTicketChangeEventBody\",\n" +
			"\t\"instance\":{\n" +
			"\t\t\"planid\":\"3311\"\n" +
			"\t}\n" +
			"}";

	@Autowired // 也可以注入JmsTemplate，JmsMessagingTemplate对JmsTemplate进行了封装
	private JmsMessagingTemplate jmsTemplate;

	@Value("${activeMq:unknown}")
	private String queue;


	@Autowired
	CityAirlineCodeService service;


	@Test
	public void contextLoads() throws InterruptedException, IOException, ExecutionException {
		//jmsTemplate.convertAndSend(queue,body5507);
		/*jmsTemplate.convertAndSend(queue,body5508);
		jmsTemplate.convertAndSend(queue,body5508);
		jmsTemplate.convertAndSend(queue,body5508);
		jmsTemplate.convertAndSend(queue,body5508);*/
		//jmsTemplate.convertAndSend("UME_YPB_QUEUE",body5508);
		//jmsTemplate.convertAndSend(queue,body5501);
		jmsTemplate.convertAndSend("UME_YPB_QUEUE",body5502);
	}

	/*@Autowired
	TicketChangePriceService priceService;*/

	@Test
	public void testPrepareForIncrease() throws Exception {
		TicketChangePrice price = new TicketChangePrice();
		//price.setThisPrice("1025");
		price.setDept("SZX");
		price.setDest("PEK");
		price.setFlightDate("2018-01-31");
		price.setFlightNo("ZH9101");
		//List<TicketChangePrice> list =  priceService.findList(price);

		//System.out.println(list.size());
	}


	RestTemplate template = new RestTemplate();
	String url = "http://localhost:7080/YpbMessages/mq/Listener?";
	String urlPro = "http://localhost:7080/mq/Listener?";

	/*@Autowired
	HttpClient client;*/

	@Test
	public void testPrepareForReduction() {
		//String str = client.httpPost(url,"mq="+body5502.trim());
		//System.out.println(str);
	}

	@Test
	public void getSeamless(){
		JSONObject json = new JSONObject();
		String flightNo = ("ZH9101");
		json.put("dept", "SZX");
		json.put("dest", "PEK");
		json.put("deptDate", "2017-12-27");
		json.put("flightNo", flightNo);
		json.put("airline", "");
		//TicketResponse response = TicketByAirlineUtil.getTicketByFlight(String.valueOf(json));
		//System.out.println(response);
	}


	//测试推送5508事件
	public static void main(String[] args) {
		Instance instance = new Instance();
		NameValuePair[] value = new NameValuePair[6];
		value[0] = new NameValuePair("appid", "ume_68bdaf97b45a43cb9c4e335ca1797088");
		value[1] = new NameValuePair("userId", "47e7f182766ef7887502d362eeefbb19a0c667c7");
		value[2] = new NameValuePair("templateId", "75022");
		Map<String, String> params = new HashMap<>();
		params.put("\"ticketCount\"", "\"0\"");
		params.put("\"endInfo\"", "\"(当前最低票价¥1976,经济舱B/9.5折/10张)。[消息时间:01-15 12:45]。\"");
		params.put("\"airlineInfo\"", "\"深航ZH9101\"");
		params.put("\"cabinInfo\"", "\"(2018-01-31周三08:05出发　深圳宝安-北京首都),总余票\"");
		value[3] = new NameValuePair("params", params.toString().replace("=", ":"));
		Map<String, String> jumpParams = new HashMap<>();
		jumpParams.put("\"key\"", "\"planList\"");
		value[4] = new NameValuePair("jumpParams", jumpParams.toString().replace("=", ":"));
		value[5] = new NameValuePair("token", "IYwhT7zaoRuDy9geH1MIqNEa");
		instance.setValue(value);
		instance.setAppid(new String[]{"ume_68bdaf97b45a43cb9c4e335ca1797088"});
//		String result = pushServiceImpl.push(instance);
		String result = push(instance);
		System.out.println(result);
	}

	public static String push(Instance instance) {
		//TODO 获取token
		HttpClient http = new HttpClient();
//        instance.setToken(http.getToken(instance.getAppid()[0]));
		instance.setToken("tLZul6FH9xJ+ei7KMX7NRNEa");
		System.out.println("token:"+instance.getToken());
		//TODO 发送消息
		org.apache.commons.httpclient.HttpClient client = http.getHttpClient();
		HttpMethod httpPost;
		String result = "";
		try {
			//IOS
			httpPost = http.postMethod("http://119.254.233.176/UmeSDK/Msg/SendMsg.do", instance.getValue());
			//安卓
//			httpPost = http.postMethod("http://comp.umetrip.com/UmeSDK/Msg/SendMsg.do", instance.getValue());
			client.executeMethod(httpPost);
			result = httpPost.getResponseBodyAsString();
		} catch (IOException e) {

		}
		return result;
	}


	@Test
	public void index(){
		ApplicationContext factory=new ClassPathXmlApplicationContext("classpath:dubbo.xml");
	}


	@Autowired
	MessagesBodyService service1;

	@Test
	public void Test(){
		Support support = new Support() {
			@Override
			protected String getAirlineCN(Instance instance) {
				return super.getAirlineCN(instance);
			}
		};
	}



}
