package com.travelsky.ypb.domain.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Output 节点
 * @see Output
 */
@SuppressWarnings("ALL")
@XmlRootElement
public class Output implements Serializable{

	private static final long serialVersionUID = 8148863222734733188L;

	private Result result;

	public Result getResult() {
		return result;
	}

	@XmlElement(name = "Result")
	public void setResult(Result result) {
		this.result = result;
	}
	
	
}
