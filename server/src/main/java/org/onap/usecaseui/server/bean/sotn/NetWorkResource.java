package org.onap.usecaseui.server.bean.sotn;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class NetWorkResource {
	
	private String networkId;
	
	private List<Pnf> pnfs;
	
	private List<Pinterface> tps;

	public NetWorkResource(String networkId, List<Pnf> pnfs, List<Pinterface> tps) {
		this.networkId = networkId;
		this.pnfs = pnfs;
		this.tps = tps;
	}

	public NetWorkResource() {
	}

	public String getNetworkId() {
		return networkId;
	}

	public void setNetworkId(String networkId) {
		this.networkId = networkId;
	}

	public List<Pnf> getPnfs() {
		return pnfs;
	}

	public void setPnfs(List<Pnf> pnfs) {
		this.pnfs = pnfs;
	}

	public List<Pinterface> getTps() {
		return tps;
	}

	public void setTps(List<Pinterface> tps) {
		this.tps = tps;
	}
	
}
