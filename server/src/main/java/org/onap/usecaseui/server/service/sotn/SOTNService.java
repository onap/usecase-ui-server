/*
 * Copyright (C) 2018 CMCC, Inc. and others. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.onap.usecaseui.server.service.sotn;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.onap.usecaseui.server.bean.sotn.Pinterface;

public interface SOTNService {
	
	public String getNetWorkResources();
	
	public List<Pinterface>  getPinterfaceByPnfName(String pnfName);
	
	public String getLogicalLinks();
	
	public String getSpecificLogicalLink(String linkName);
	
	public String getHostUrl(String aaiId);
	
	public String getExtAaiId(String aaiId);
	
	public String createHostUrl(HttpServletRequest request,String aaiId);
	
	public String createTopoNetwork(HttpServletRequest request,String networkId);
	
	public String createTerminationPoint(HttpServletRequest request,String pnfName,String tpId);
	
	public String createLink(HttpServletRequest request,String linkName);
	
	public String createPnf(HttpServletRequest request,String pnfName);
	
	public String deleteLink(String linkName,String resourceVersion);
	
	public String getServiceInstances(String customerId,String serviceType);
	
	public String serviceInstanceInfo(String customerId,String serviceType,String serviceInstanceId);
	
	public String getPnfInfo(String pnfName);
	
	public String getAllottedResources(String customerId,String serviceType,String serviceId);
	
	public String getConnectivityInfo(String connectivityId);

	public String getVpnBindingInfo(String vpnId);

	public String getNetworkRouteInfo(String routeId);

	public String getUniInfo(String id);

	public String getPinterfaceByVpnId(String vpnId);
	
	public String deleteExtNetwork(String networkId,String resourceVersion);
}
