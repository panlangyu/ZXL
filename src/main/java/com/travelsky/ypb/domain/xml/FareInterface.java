package com.travelsky.ypb.domain.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * <p>Tilte:最低价信息实体Bean</p>
 * <p>Description:</p>
 * @author huc
 */
@SuppressWarnings("ALL")
@XmlRootElement(name = "FareInterface")
public class FareInterface implements Serializable{
	
	private static final long serialVersionUID = 8148863222734733189L;
	
	private Output output;

	public Output getOutput() {
		return output;
	}
	
	@XmlElement(name = "Output")
	public void setOutput(Output output) {
		this.output = output;
	}
	
	
}
