package com.travelsky.ypb.domain.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * Result 节点
 * @see Result
 */
@XmlRootElement
public class Result {
	
	private FlightShopResult flightShopResult;

	public FlightShopResult getFlightShopResult() {
		return flightShopResult;
	}

	@XmlElement(name = "FlightShopResult")
	public void setFlightShopResult(FlightShopResult flightShopResult) {
		this.flightShopResult = flightShopResult;
	}
	
}
