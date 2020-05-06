/**
 * Copyright 2016-2017 ZTE Corporation.
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
package org.onap.usecaseui.server.controller.lcm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.onap.usecaseui.server.service.lcm.PackageDistributionService;
import org.onap.usecaseui.server.service.lcm.ServiceInstanceService;
import org.onap.usecaseui.server.util.UuiCommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@org.springframework.context.annotation.Configuration
@EnableAspectJAutoProxy
public class ServiceInstanceController {

    private static final Logger logger = LoggerFactory.getLogger(ServiceInstanceController.class);

    @Resource(name="ServiceInstanceService")
    private ServiceInstanceService serviceInstanceService;
    
    @Resource(name="PackageDistributionService")
    private PackageDistributionService packageDistributionService;

    public void setPackageDistributionService(PackageDistributionService packageDistributionService) {
        this.packageDistributionService = packageDistributionService;
    }

    public void setServiceInstanceService(ServiceInstanceService serviceInstanceService) {
        this.serviceInstanceService = serviceInstanceService;
    }

	@ResponseBody
    @GetMapping(value = {"/uui-lcm/service-instances"}, produces = "application/json")
    public String listServiceInstances(HttpServletRequest request) throws JsonProcessingException{
		ObjectMapper mapper = new ObjectMapper();
        String customerId = request.getParameter("customerId");
        String serviceType = request.getParameter("serviceType");
        String currentPage = request.getParameter("currentPage");
        String pageSize = request.getParameter("pageSize");
        List<String> serviceInstances =serviceInstanceService.listServiceInstances(customerId, serviceType);
        Map<String,Object> map = new HashMap<>();
        map.put("total", serviceInstances.size());
        map.put("tableList", UuiCommonUtil.getPageList(serviceInstances, Integer.parseInt(currentPage), Integer.parseInt(pageSize)));
        return mapper.writeValueAsString(map);
    }
	
	@ResponseBody
    @GetMapping(value = {"/uui-lcm/service-ns-instances"}, produces = "application/json")
    public String listNsOrServiceInstances(HttpServletRequest request) throws JsonProcessingException{
		ObjectMapper mapper = new ObjectMapper();
        String customerId = request.getParameter("customerId");
        String serviceType = request.getParameter("serviceType");
        String currentPage = request.getParameter("currentPage");
        String pageSize = request.getParameter("pageSize");
        List<String> serviceInstances =serviceInstanceService.listServiceInstances(customerId, serviceType);
        List<String> nsServiceInstances = packageDistributionService.getNetworkServiceInfo();
        List<String> result = new ArrayList<>();
        result.addAll(serviceInstances);
        result.addAll(nsServiceInstances);
        Map<String,Object> map = new HashMap<>();
        map.put("total", result.size());
        map.put("tableList", UuiCommonUtil.getPageList(result, Integer.parseInt(currentPage), Integer.parseInt(pageSize)));
        return mapper.writeValueAsString(map);
    }
	
	@ResponseBody
    @GetMapping(value = {"/uui-lcm/serviceNumByCustomer"}, produces = "application/json")
    public String serviceNumByCustomer(HttpServletRequest request) throws JsonProcessingException{
		return serviceInstanceService.serviceNumByCustomer();
	}
	
	@ResponseBody
    @GetMapping(value = {"/uui-lcm/serviceNumByServiceType/{customerId}"}, produces = "application/json")
    public String serviceNumByServiceType(@PathVariable String customerId) throws JsonProcessingException{
		return serviceInstanceService.serviceNumByServiceType(customerId);
	}
	
    @ResponseBody
    @GetMapping(value = {"/uui-lcm/getServiceInstanceById"}, produces = "application/json")
    public String getServiceInstanceById(HttpServletRequest request){
        String customerId = request.getParameter("customerId");
        String serviceType = request.getParameter("serviceType");
        String serviceId = request.getParameter("serviceId");
        JSONArray result = new JSONArray();
        String servicesString=serviceInstanceService.getRelationShipData(customerId, serviceType, serviceId);
        if(!UuiCommonUtil.isNotNullOrEmpty(servicesString)){
        	return result.toString();
        }
        JSONObject services = JSONObject.parseObject(servicesString);
        JSONArray relations =JSONObject.parseArray(JSONObject.parseObject(services.getString("relationship-list")).getString("relationship"));
        for (int i = 0; i < relations.size(); i++) {
        	JSONObject relation = JSONObject.parseObject(relations.getString(i));
        	JSONArray relationShipData = JSONObject.parseArray(relation.getString("relationship-data"));
        	for (int j = 0; j < relationShipData.size(); j++) {
        		JSONObject res = new JSONObject();
        		JSONObject data = JSONObject.parseObject(relationShipData.getString(j));
        		String relationshipKey=data.getString("relationship-key");
        		String netWorkServiceId=data.getString("relationship-value");
        		if("service-instance.service-instance-id".equals(relationshipKey)){
        			JSONObject netWorkSerSring=JSONObject.parseObject(serviceInstanceService.getRelationShipData(customerId, serviceType, netWorkServiceId));
        			if("NetworkService".equals(netWorkSerSring.get("service-type"))){
        				res.put("netWorkServiceId", netWorkSerSring.get("service-instance-id"));
        				res.put("netWorkServiceName", netWorkSerSring.get("service-instance-name"));
        				res.put("scaleType","");
        				res.put("aspectId","");
        				res.put("numberOfSteps","");
        				res.put("scalingDirection","");
        				result.add(res);
        			}
        		}
        	}
		}
        return result.toString() ;
    }
}
