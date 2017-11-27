package com.travelsky.ypb.domain.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * FlightShopResult 节点
 */
@XmlRootElement
public class FlightShopResult {
	
	private List<LowestPrice> lowestPriceList;

	public List<LowestPrice>  getLowestPriceList() {
		return lowestPriceList;
	}

	@XmlElement(name = "LowestPrice")
	public void setLowestPriceList(List<LowestPrice>  lowestPriceList) {
		this.lowestPriceList = lowestPriceList;
	}
	
}
