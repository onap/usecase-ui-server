package org.onap.usecaseui.server.service.sotn;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.onap.usecaseui.server.bean.sotn.Pinterface;

public interface SOTNService {
	
	public String getNetWorkResources();
	
	public List<Pinterface>  getPinterfaceByPnfName(String pnfName);
	
	public String getLogicalLinks();
	
	public String createTopoNetwork(HttpServletRequest request,String networkId);
	
	public String createTerminationPoint(HttpServletRequest request,String pnfName,String tpId);
	
	public String createLink(HttpServletRequest request,String linkName);
	
	public String createPnf(HttpServletRequest request,String pnfName);
	
	public String deleteLink(String linkName);
	
	public String getServiceInstances(String customerId,String serviceType);
	
	public String serviceInstanceInfo(String customerId,String serviceType,String serviceInstanceId);
}
