package com.travelsky.ypb.domain.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

/**
 * FlightShopResult 节点
 */
@SuppressWarnings("ALL")
@XmlRootElement
public class FlightShopResult implements Serializable{
	
	private List<LowestPrice> lowestPriceList;

	public List<LowestPrice>  getLowestPriceList() {
		return lowestPriceList;
	}

	@XmlElement(name = "LowestPrice")
	public void setLowestPriceList(List<LowestPrice>  lowestPriceList) {
		this.lowestPriceList = lowestPriceList;
	}

	@Override
	public String toString() {
		return "FlightShopResult{" +
				"lowestPriceList=" + lowestPriceList +
				'}';
	}
}
