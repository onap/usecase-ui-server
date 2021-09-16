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
package org.onap.usecaseui.server.controller.sotn;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.onap.usecaseui.server.bean.sotn.NetWorkResource;
import org.onap.usecaseui.server.bean.sotn.Pinterface;
import org.onap.usecaseui.server.bean.sotn.Pnf;
import org.onap.usecaseui.server.service.sotn.SOTNService;
import org.onap.usecaseui.server.util.UuiCommonUtil;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@CrossOrigin(origins="*")
@RequestMapping("/uui-sotn")
public class SotnController {
	
	@Resource(name="SOTNService")
	public SOTNService sotnService;

	public SOTNService getSotnService() {
		return sotnService;
	}

	public void setSotnService(SOTNService sotnService) {
		this.sotnService = sotnService;
	}
	
    @GetMapping(value = {"/getNetWorkResources"})
	public List<NetWorkResource> getNetWorkResources(){
    	List<NetWorkResource> result = new ArrayList<NetWorkResource>();
    	String json  = sotnService.getNetWorkResources();
    	if(json.indexOf("FAILED")!=-1){
    		return result;
    	}
    	createJson(json,result);
    	for(NetWorkResource networks:result){
    		List<Pinterface> pinterfaces = new ArrayList<Pinterface>();
    		List<Pnf> pnfs = networks.getPnfs();
    		for(Pnf pnf :pnfs){
    			List<Pinterface> tps= sotnService.getPinterfaceByPnfName(pnf.getPnfName());
    			pinterfaces.addAll(tps);
    		}
    		networks.setTps(pinterfaces);
    	}
		return result;
	}
    
    @GetMapping(value = {"/getPinterfaceByPnfName/{pnfName:.+}"})
    public List<Pinterface>  getPinterfaceByPnfName(@PathVariable(value="pnfName") String pnfName){
    	return sotnService.getPinterfaceByPnfName(pnfName);
    }
    
    @GetMapping(value = {"/getLogicalLinks"})
    public String getLogicalLinks(){
    	return sotnService.getLogicalLinks();
    }
    
    @GetMapping(value = {"/getSpecificLogicalLink/{linkName:.+}"})
    public String getSpecificLogicalLink(@PathVariable(value="linkName") String linkName){
    	return sotnService.getSpecificLogicalLink(linkName);
    }
    
    @GetMapping(value = {"/getHostUrl/{aaiId:.+}"})
    public String getHostUrl(@PathVariable(value="aaiId") String aaiId){
    	return sotnService.getHostUrl(aaiId);
    }
    
    @GetMapping(value = {"/getExtAaiId/{aaiId:.+}"})
    public String getExtAaiId(@PathVariable(value="aaiId") String aaiId){
    	return sotnService.getExtAaiId(aaiId);
    }
    
    @PutMapping(value = {"/createHostUrl/{aaiId:.+}"})
    public String createHostUrl(HttpServletRequest request,@PathVariable(value="aaiId") String aaiId){
    	return sotnService.createHostUrl(request, aaiId);
    }
    
    @PutMapping(value = {"/createTopoNetwork/{networkId:.+}"})
    public String createTopoNetwork(HttpServletRequest request,@PathVariable(value="networkId") String networkId){
    	return sotnService.createTopoNetwork(request,networkId);
    }
    
    @PutMapping(value = {"/pnf/{pnfName:.+}/p-interfaces/p-interface/{tp-id:.+}/createTerminationPoint"})
    public String createTerminationPoint(HttpServletRequest request,@PathVariable(value="pnfName") String pnfName,@PathVariable(value="tp-id") String tpId){
    	return sotnService.createTerminationPoint(request,pnfName,tpId);
    }
    
    @PutMapping(value = {"/createLink/{linkName:.+}"})
    public String createLink(HttpServletRequest request,@PathVariable(value="linkName") String linkName){
    	return sotnService.createLink(request, linkName);
    }
    
    @PutMapping(value = {"/createPnf/{pnfName:.+}"})
    public String createPnf(HttpServletRequest request,@PathVariable(value="pnfName") String pnfName){
    	return sotnService.createPnf(request, pnfName);
    }
    
    @DeleteMapping(value = {"/deleteLink/{linkName:.+}/{resourceVersion:.+}"})
    public String deleteLink(@PathVariable(value="linkName") String linkName,@PathVariable(value="resourceVersion") String resourceVersion){
    	return sotnService.deleteLink(linkName,resourceVersion);
    }
    
    @GetMapping(value = {"/getServiceInstanceInfo"})
    public String getServiceInstanceInfo(HttpServletRequest request){
        String customerId = request.getParameter("customerId");
        String serviceType = request.getParameter("serviceType");
        String serviceId = request.getParameter("serviceId");
    	return sotnService.serviceInstanceInfo(customerId, serviceType, serviceId);
    }
    
    @GetMapping(value = {"/getPnfInfo/{pnfName:.+}"})
    public String getPnfInfo(@PathVariable(value="pnfName") String pnfName){
    	return sotnService.getPnfInfo(pnfName);
    }
    
    @GetMapping(value = {"/getAllottedResources"})
    public String getAllottedResources(HttpServletRequest request){
        String customerId = request.getParameter("customerId");
        String serviceType = request.getParameter("serviceType");
        String serviceId = request.getParameter("serviceId");
    	return sotnService.getAllottedResources(customerId, serviceType,serviceId);
    }
    
    @GetMapping(value = {"/getConnectivityInfo", "/getConnectivityInfo/{connectivityId}"})
    public String getConnectivityInfo(@PathVariable(required = false) String connectivityId){
    	return sotnService.getConnectivityInfo(connectivityId);
    }
    
    @GetMapping(value = {"/getPinterfaceByVpnId/{vpnId:.+}"})
    public String getPinterfaceByVpnId(@PathVariable(value="vpnId") String vpnId){
    	return sotnService.getPinterfaceByVpnId(vpnId);
    }
    
    @GetMapping(value = {"/getServiceInstanceList"})
    public String getServiceInstanceList(HttpServletRequest request){
        String customerId = request.getParameter("customerId");
        String serviceType = request.getParameter("serviceType");
    	return sotnService.getServiceInstances(customerId, serviceType);
    }

    @DeleteMapping(value = {"/deleteExtNetWork"})
    public String deleteExtNetwork(@RequestParam String extNetworkId,@RequestParam(value="resourceVersion") String resourceVersion){
    	return sotnService.deleteExtNetwork(extNetworkId,resourceVersion);
    }
    
    private void createJson(String json,List<NetWorkResource> list){

    	ObjectMapper mapper = new ObjectMapper();
    	
    	try {
			JsonNode node = mapper.readTree(json);
			JsonNode netNode = node.get("network-resource");
			for(int i=0;i<netNode.size();i++){
				NetWorkResource netResource = new NetWorkResource();
				List<Pnf> pnfs = new ArrayList<Pnf>();
				String networkId=netNode.get(i).get("network-id").toString();
				if(networkId.indexOf("\"")!=-1){
					networkId = networkId.substring(1, networkId.length()-1);
				}
				netResource.setNetworkId(networkId);
				if(UuiCommonUtil.isNotNullOrEmpty(netNode.get(i).get("relationship-list"))){
					String relationJson = netNode.get(i).get("relationship-list").toString();
					JsonNode relationNode = mapper.readTree(relationJson);
					
					JsonNode shipNode = relationNode.get("relationship");
					for(int j=0;j<shipNode.size();j++){
						Pnf pnf = new Pnf();
						JsonNode shipDataNode = shipNode.get(j).get("relationship-data");
						String shipDataValue = shipDataNode.get(0).get("relationship-value").toString();
						String shipDataKey = shipDataNode.get(0).get("relationship-key").toString();
						if(shipDataKey.indexOf("\"")!=-1){
							shipDataKey = shipDataKey.substring(1, shipDataKey.length()-1);
						}
						if("ext-aai-network.aai-id".equals(shipDataKey)){
							netResource.setAaiId(shipDataKey);
							continue;
						}
						if(shipDataValue.indexOf("\"")!=-1){
							shipDataValue = shipDataValue.substring(1, shipDataValue.length()-1);
						}
						pnf.setPnfName(shipDataValue);
						pnfs.add(pnf);
					}
					netResource.setPnfs(pnfs);
					list.add(netResource);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
