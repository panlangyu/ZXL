package com.travelsky.ypb.domain.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Output 节点
 * @see Output
 */
@XmlRootElement
public class Output {
	
	private Result result;

	public Result getResult() {
		return result;
	}

	@XmlElement(name = "Result")
	public void setResult(Result result) {
		this.result = result;
	}
	
	
}
