package com.travelsky.ypb.process;


import com.travelsky.ypb.domain.log.Log;
import com.travelsky.ypb.domain.message.Instance;
import com.travelsky.ypb.domain.support.EventService;
import com.travelsky.ypb.domain.support.ServiceSupport;
import com.travelsky.ypb.domain.xml.CabinTicket;
import com.travelsky.ypb.publics.CommonUtil;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 抢票通知
 */
@Service
public class Event1024 extends EventService implements ServiceSupport{

	@Override
	public Instance process(final Instance instance) {
		//process code...
		instance.setEventType(1024+"");
		Log.i(this.getClass(),"process","抢票通知");
		//查询抢票计划

		//查询seamless
		Map seamLess =  getSeamLess(instance);
		instance.setCabinTicket(seamLess);
		instance.setSeamLess(seamLess);
		CabinTicket cabin = CommonUtil.mapToObject(seamLess,CabinTicket.class);
		String ticketCountSum = String.valueOf(ticketSum(cabin));
		String ticketCount = String.valueOf(cabin.getY());
		Log.i(this.getClass(),"seamLess",seamLess);
		instance.setTicketCountSum(ticketCountSum);
		instance.setTicketCount(ticketCount);

		if (!"0".equals(ticketCountSum)){
			// 执行抢票接口
			grabVotes(instance);
		}

		return instance;
	}





}
