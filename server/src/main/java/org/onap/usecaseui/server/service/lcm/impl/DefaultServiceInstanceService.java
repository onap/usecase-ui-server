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
package org.onap.usecaseui.server.service.lcm.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.onap.usecaseui.server.bean.ServiceBean;
import org.onap.usecaseui.server.service.lcm.CustomerService;
import org.onap.usecaseui.server.service.lcm.ServiceInstanceService;
import org.onap.usecaseui.server.service.lcm.ServiceLcmService;
import org.onap.usecaseui.server.service.lcm.domain.aai.AAIService;
import org.onap.usecaseui.server.service.lcm.domain.aai.bean.AAICustomer;
import org.onap.usecaseui.server.service.lcm.domain.aai.bean.AAIServiceSubscription;
import org.onap.usecaseui.server.service.lcm.domain.aai.bean.ServiceInstanceRsp;
import org.onap.usecaseui.server.util.RestfulServices;
import org.onap.usecaseui.server.util.UuiCommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.ResponseBody;
import retrofit2.Response;

@Service("ServiceInstanceService")
@org.springframework.context.annotation.Configuration
@EnableAspectJAutoProxy
public class DefaultServiceInstanceService implements ServiceInstanceService {

    private static final Logger logger = LoggerFactory.getLogger(DefaultServiceInstanceService.class);

    private AAIService aaiService;
    
    @Resource(name="ServiceLcmService")
    private ServiceLcmService serviceLcmService;
    
    @Resource(name="CustomerService")
    private CustomerService customerService;

    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }
    
	public void setServiceLcmService(ServiceLcmService serviceLcmService) {
		this.serviceLcmService = serviceLcmService;
	}

    public DefaultServiceInstanceService() {
        this(RestfulServices.create(AAIService.class));
    }

    public DefaultServiceInstanceService(AAIService aaiService) {
        this.aaiService = aaiService;
    }

    @Override
    public List<String> listServiceInstances(String customerId, String serviceType) {
    	List<String> result = new ArrayList<>();
        try {
            Response<ResponseBody> response = aaiService.listServiceInstances(customerId, serviceType).execute();
            if (response.isSuccessful()) {
            	String resultStr=new String(response.body().bytes());
            	JSONObject object = JSONObject.parseObject(resultStr);
            	if(UuiCommonUtil.isNotNullOrEmpty(object)){
            		result=this.parseServiceInstance(object, customerId, serviceType);
            	}
                return result;
            } else {
                logger.info(String.format("Can not get service instances[code=%s, message=%s]", response.code(), response.message()));
                return Collections.emptyList();
            }
        } catch (IOException e) {
            logger.error("list services instances occur exception"+e.getMessage());
            return Collections.emptyList();
        }
    }
    
	private List<String> parseServiceInstance(JSONObject objects,String customerId,String serviceType) throws JsonProcessingException{
    	ObjectMapper mapper = new ObjectMapper();
    	List<String> result = new ArrayList<>();
    	JSONArray serviceInstances=objects.getJSONArray("service-instance");
    	for(Object serviceInstance:serviceInstances){
    			JSONObject object =  JSON.parseObject(serviceInstance+"");
    			String serviceInstanceId=object.get("service-instance-id").toString();
    			ServiceBean serviceBean = serviceLcmService.getServiceBeanByServiceInStanceId(serviceInstanceId);
    			String serviceDomain = serviceBean.getServiceDomain();
				object.put("serviceDomain",serviceDomain);
				if("SOTN".equals(serviceDomain)||"CCVPN".equals(serviceDomain)||"E2E Service".equals(serviceDomain)||"Network Service".equals(serviceDomain)){
					List<String> parentIds = serviceLcmService.getServiceInstanceIdByParentId(serviceInstanceId);
					List<String> parentServiceInstances = new ArrayList<>();
					if(parentIds.size()>0){
						for(String id:parentIds){
							String parentServiceInstance=this.getRelationShipData(customerId, serviceType, id);
							parentServiceInstances.add(parentServiceInstance);
						}
					}
					object.put("childServiceInstances",mapper.writeValueAsString(parentServiceInstances));
					result.add(object.toString());
				}
    	}
    	return result;
    }
	
	@Override
	public String getRelationShipData(String customerId, String serviceType, String serviceId) {
        try {
            Response<ResponseBody> response = aaiService.getAAIServiceInstance(customerId, serviceType,serviceId).execute();
            if (response.isSuccessful()) {
            	String result=new String(response.body().bytes());
                return result;
            } else {
                logger.info(String.format("Can not get service instances[code=%s, message=%s]", response.code(), response.message()));
                return "";
            }
        } catch (IOException e) {
            logger.error("list services instances occur exception:"+e.getMessage());
            return "";
        }
	}
	
	@Override
	public String serviceNumByCustomer() throws JsonProcessingException{
		Map<String,Object> result = new HashMap();
		ObjectMapper omAlarm = new ObjectMapper();
		List<AAICustomer> customers = customerService.listCustomer();
		int total =0;
		List<Map<String,Object>> list = new ArrayList<>();
		if(customers.size()>0){
			for(AAICustomer customer : customers){
				Map<String,Object> customerMap = new HashMap<String,Object>();
				int customerNum = 0;
				List<AAIServiceSubscription> serviceSubscriptions = customerService.listServiceSubscriptions(customer.getGlobalCustomerId());
				if(serviceSubscriptions.size()>0){
					for(AAIServiceSubscription serviceSubscription:serviceSubscriptions){
						List<String> serviceInstances =this.listServiceInstances(customer.getGlobalCustomerId(), serviceSubscription.getServiceType());
						total+=serviceInstances.size();
						customerNum+=serviceInstances.size();
					}
				}
				customerMap.put("name",customer.getSubscriberName());
				customerMap.put("value",customerNum);
				list.add(customerMap);
			}
		}
		result.put("serviceTotalNum", total);
		result.put("customerServiceList", list);
		return omAlarm.writeValueAsString(result);
	}
}
