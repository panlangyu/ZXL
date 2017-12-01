package com.travelsky.ypb.process;


import com.travelsky.ypb.domain.message.Instance;
import com.travelsky.ypb.domain.support.EventService;
import com.travelsky.ypb.domain.support.ServiceSupport;
import com.travelsky.ypb.domain.xml.CabinTicket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <p>Tilte:事件通知 (按航线)</p>
 * <p>Description:</p>
 * @author huc
 */
@Service
public class Event5501 extends EventService implements ServiceSupport{

	Logger log = LoggerFactory.getLogger("5501");

	@Override
	public Instance process(final Instance instance) {



		// TODO 查询航线飞行计划



		// TODO 查询seamless



		// TODO 查询航线最低价



		// TODO 拼装消息体



		// TODO 消息发送


		return instance;
	}



}
