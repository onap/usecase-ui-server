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
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.onap.usecaseui.server.bean.ServiceBean;
import org.onap.usecaseui.server.service.lcm.ServiceInstanceService;
import org.onap.usecaseui.server.service.lcm.ServiceLcmService;
import org.onap.usecaseui.server.util.UuiCommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
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
    
    @Resource(name="ServiceLcmService")
    private ServiceLcmService serviceLcmService;

    public void setServiceInstanceService(ServiceInstanceService serviceInstanceService) {
        this.serviceInstanceService = serviceInstanceService;
    }

	public void setServiceLcmService(ServiceLcmService serviceLcmService) {
		this.serviceLcmService = serviceLcmService;
	}

	@ResponseBody
    @RequestMapping(value = {"/uui-lcm/service-instances"}, method = RequestMethod.GET , produces = "application/json")
    public List<String> listServiceInstances(HttpServletRequest request){
		List<String> result = new ArrayList<String>();
        String customerId = request.getParameter("customerId");
        String serviceType = request.getParameter("serviceType");
        String currentPage = request.getParameter("currentPage");
        String pageSize = request.getParameter("pageSize");
        List<String> serviceInstances =serviceInstanceService.listServiceInstances(customerId, serviceType);
        if(serviceInstances.size()>0){
        	try {
				result = this.parseServiceInstance(serviceInstances,currentPage,pageSize,customerId,serviceType);
			} catch (JsonProcessingException e) {
				logger.error("exception occurred while performing ServiceInstanceController listServiceInstances. Details:" + e.getMessage());
			}
        }
        return result;
    }
    @ResponseBody
    @RequestMapping(value = {"/uui-lcm/getServiceInstanceById"}, method = RequestMethod.GET , produces = "application/json")
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
    
    @SuppressWarnings("unchecked")
	private List<String> parseServiceInstance(List<String> list,String currentPage,String pageSize,String customerId,String serviceType) throws JsonProcessingException{
    	ObjectMapper mapper = new ObjectMapper();
    	List<String> result = new ArrayList<>();
    	for(String serviceInstance:list){
    			JSONObject object =  JSON.parseObject(serviceInstance);
    			String serviceInstanceId=object.get("service-instance-id").toString();
    			ServiceBean serviceBean = serviceLcmService.getServiceBeanByServiceInStanceId(serviceInstanceId);
    			String serviceDomain = serviceBean.getServiceDomain();
				object.put("serviceDomain",serviceDomain);
				if("SOTN".equals(serviceDomain)||"CCVPN".equals(serviceDomain)||"E2E Service".equals(serviceDomain)||"Network Service".equals(serviceDomain)){
					List<String> parentIds = serviceLcmService.getServiceInstanceIdByParentId(serviceInstanceId);
					List<String> parentServiceInstances = new ArrayList<>();
					if(parentIds.size()>0){
						for(String id:parentIds){
							String parentServiceInstance=serviceInstanceService.getRelationShipData(customerId, serviceType, id);
							parentServiceInstances.add(parentServiceInstance);
						}
					}
					object.put("childServiceInstances",mapper.writeValueAsString(parentServiceInstances));
					result.add(mapper.writeValueAsString(object));
				}
    	}
    	return UuiCommonUtil.getPageList(result, Integer.parseInt(currentPage), Integer.parseInt(pageSize));
    }
}
