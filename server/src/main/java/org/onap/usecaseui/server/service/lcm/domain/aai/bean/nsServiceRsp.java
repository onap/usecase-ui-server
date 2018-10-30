package org.onap.usecaseui.server.service.lcm.domain.aai.bean;

import java.util.List;

public class nsServiceRsp {
	
	public List<String> nsServices;

	public List<String> getNsServices() {
		return nsServices;
	}

	public void setNsServices(List<String> nsServices) {
		this.nsServices = nsServices;
	}
	
}
