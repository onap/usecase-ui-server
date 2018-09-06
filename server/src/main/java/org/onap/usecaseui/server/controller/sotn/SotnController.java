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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
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
	
    @ResponseBody
    @RequestMapping(value = {"/getNetWorkResources"}, method = RequestMethod.GET , produces = "application/json")
	public List<NetWorkResource> getNetWorkResources(){
    	List<NetWorkResource> result = new ArrayList<NetWorkResource>();
    	String json  = sotnService.getNetWorkResources();
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
    
    @ResponseBody
    @RequestMapping(value = {"/getPinterfaceByPnfName/{pnfName}"}, method = RequestMethod.GET , produces = "application/json")
    public List<Pinterface>  getPinterfaceByPnfName(@PathVariable(value="pnfName") String pnfName){
    	return sotnService.getPinterfaceByPnfName(pnfName);
    }
    
    @ResponseBody
    @RequestMapping(value = {"/getLogicalLinks"}, method = RequestMethod.GET , produces = "application/json")
    public String getLogicalLinks(){
    	return sotnService.getLogicalLinks();
    }
    
    @ResponseBody
    @RequestMapping(value = {"/createTopoNetwork/{networkId}"}, method = RequestMethod.PUT , produces = "application/json")
    public String createTopoNetwork(HttpServletRequest request,@PathVariable(value="networkId") String networkId){
    	return sotnService.createTopoNetwork(request,networkId);
    }
    
    @ResponseBody
    @RequestMapping(value = {"/pnf/{pnfName}/p-interfaces/p-interface/{tp-id}/createTerminationPoint"}, method = RequestMethod.PUT , produces = "application/json")
    public String createTerminationPoint(HttpServletRequest request,@PathVariable(value="pnfName") String pnfName,@PathVariable(value="tp-id") String tpId){
    	return sotnService.createTerminationPoint(request,pnfName,tpId);
    }
    
    @ResponseBody
    @RequestMapping(value = {"/createLink/{linkName}"}, method = RequestMethod.PUT , produces = "application/json")
    public String createLink(HttpServletRequest request,@PathVariable(value="linkName") String linkName){
    	return sotnService.createLink(request, linkName);
    }
    
    @ResponseBody
    @RequestMapping(value = {"/createPnf/{pnfName}"}, method = RequestMethod.PUT , produces = "application/json")
    public String createPnf(HttpServletRequest request,@PathVariable(value="pnfName") String pnfName){
    	return sotnService.createPnf(request, pnfName);
    }
    
    @ResponseBody
    @RequestMapping(value = {"/deleteLink/{linkName}"}, method = RequestMethod.DELETE , produces = "application/json")
    public String deleteLink(@PathVariable(value="linkName") String linkName){
    	return sotnService.deleteLink(linkName);
    }
    
    @ResponseBody
    @RequestMapping(value = {"/getServiceInstanceInfo"}, method = RequestMethod.GET , produces = "application/json")
    public String getServiceInstanceInfo(HttpServletRequest request){
        String customerId = request.getParameter("customerId");
        String serviceType = request.getParameter("serviceType");
        String serviceId = request.getParameter("serviceId");
    	return sotnService.serviceInstanceInfo(customerId, serviceType, serviceId);
    }
    
    @ResponseBody
    @RequestMapping(value = {"/getServiceInstanceList"}, method = RequestMethod.GET , produces = "application/json")
    public String getServiceInstanceList(HttpServletRequest request){
        String customerId = request.getParameter("customerId");
        String serviceType = request.getParameter("serviceType");
    	return sotnService.getServiceInstances(customerId, serviceType);
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
				netResource.setNetworkId(networkId);
				String relationJson = netNode.get(i).get("relationship-list").toString();
				JsonNode relationNode = mapper.readTree(relationJson);
				
				JsonNode shipNode = relationNode.get("relationship");
				for(int j=0;j<shipNode.size();j++){
					Pnf pnf = new Pnf();
					JsonNode shipDataNode = shipNode.get(j).get("relationship-data");
					String shipDataValue = shipDataNode.get(0).get("relationship-value").toString();
					pnf.setPnfName(shipDataValue);
					pnfs.add(pnf);
				}
				netResource.setPnfs(pnfs);
				list.add(netResource);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
