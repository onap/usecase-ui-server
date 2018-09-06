package org.onap.usecaseui.server.bean.sotn;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Pinterface {
	
	private String interfaceName;
	
	public Pinterface() {
	}

	@JsonCreator
	public Pinterface(@JsonProperty("interface-name") String interfaceName) {
		this.interfaceName = interfaceName;
	}
	
	@JsonProperty("interface-name")
	public String getInterfaceName() {
		return interfaceName;
	}
	
	@JsonProperty("interface-name")
	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}
	
}
