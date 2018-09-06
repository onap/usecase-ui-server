package org.onap.usecaseui.server.bean.sotn;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class PinterfaceRsp {
	
	private List<Pinterface> pinterfaces;
	
	
	public PinterfaceRsp() {
	}
	
	@JsonCreator
	public PinterfaceRsp(@JsonProperty("p-interface")List<Pinterface> pinterfaces) {
		this.pinterfaces = pinterfaces;
	}
	
	@JsonProperty("p-interface")
	public List<Pinterface> getPinterfaces() {
		return pinterfaces;
	}
	
	@JsonProperty("p-interface")
	public void setPinterfaces(List<Pinterface> pinterfaces) {
		this.pinterfaces = pinterfaces;
	}
	
}
