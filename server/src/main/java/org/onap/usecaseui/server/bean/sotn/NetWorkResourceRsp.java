package org.onap.usecaseui.server.bean.sotn;

import java.util.List;

public class NetWorkResourceRsp {
	
    private List<NetWorkResource> netWorkResources;
    
    public void setNetWorkResourceSkins(List<NetWorkResource> netWorkResources) {
        this.netWorkResources = netWorkResources;
    }
    
	public List<NetWorkResource> getNetWorkResources() {
		return netWorkResources;
	}
    
}
