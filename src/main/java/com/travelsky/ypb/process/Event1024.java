package com.travelsky.ypb.process;


import com.travelsky.ypb.domain.message.Instance;
import com.travelsky.ypb.domain.support.EventService;
import com.travelsky.ypb.domain.support.ServiceSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 抢票
 */
@Service
public class Event1024 extends EventService implements ServiceSupport{

	private static final Logger LOGGER = LoggerFactory.getLogger("1024");


	@Override
	public Instance process(final Instance instance) {
		//TODO process code...


		return instance;
	}





}
