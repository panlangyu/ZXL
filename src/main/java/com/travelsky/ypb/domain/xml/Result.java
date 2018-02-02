package com.travelsky.ypb.domain.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;


/**
 * Result 节点
 * @see Result
 */
@SuppressWarnings("ALL")
@XmlRootElement
public class Result implements Serializable{
	
	private FlightShopResult flightShopResult;

	public FlightShopResult getFlightShopResult() {

		return flightShopResult;
	}

	@XmlElement(name = "FlightShopResult")
	public void setFlightShopResult(FlightShopResult flightShopResult) {

		this.flightShopResult = flightShopResult;
	}
	
}
