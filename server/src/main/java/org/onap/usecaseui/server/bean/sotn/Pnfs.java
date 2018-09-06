package org.onap.usecaseui.server.bean.sotn;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Pnfs {
	
	private List<Pnf> pnfs;
	
	@JsonCreator
	public Pnfs(@JsonProperty("pnf") List<Pnf> pnfs) {
		this.pnfs = pnfs;
	}
	
	@JsonProperty("pnf")
	public List<Pnf> getPnfs() {
		return pnfs;
	}
	
	@JsonProperty("pnf")
	public void setPnfs(List<Pnf> pnfs) {
		this.pnfs = pnfs;
	}
	
}
