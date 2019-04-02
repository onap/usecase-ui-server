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

import org.onap.usecaseui.server.service.lcm.CustomerService;
import org.onap.usecaseui.server.service.lcm.domain.aai.AAIService;
import org.onap.usecaseui.server.service.lcm.domain.aai.bean.AAICustomer;
import org.onap.usecaseui.server.service.lcm.domain.aai.bean.AAICustomerRsp;
import org.onap.usecaseui.server.service.lcm.domain.aai.bean.AAIServiceSubscription;
import org.onap.usecaseui.server.service.lcm.domain.aai.bean.ServiceSubscriptionRsp;
import org.onap.usecaseui.server.service.lcm.domain.aai.exceptions.AAIException;
import org.onap.usecaseui.server.util.RestfulServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;

import static org.onap.usecaseui.server.util.RestfulServices.extractBody;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

@Service("CustomerService")
@org.springframework.context.annotation.Configuration
@EnableAspectJAutoProxy
public class DefaultCustomerService implements CustomerService {

    private static final Logger logger = LoggerFactory.getLogger(DefaultCustomerService.class);

    private AAIService aaiService;

    public DefaultCustomerService() {
        this(RestfulServices.create(AAIService.class));
    }

    public DefaultCustomerService(AAIService aaiService) {
        this.aaiService = aaiService;
    }

    @Override
    public List<AAICustomer> listCustomer() {
        try {
            Response<AAICustomerRsp> response = this.aaiService.listCustomer().execute();
            if (response.isSuccessful()) {
                return response.body().getCustomer();
            } else {
                logger.info(String.format("Can not get customers[code=%s, message=%s]", response.code(), response.message()));
                return Collections.emptyList();
            }
        } catch (IOException e) {
            logger.error("list customers occur exception");
            throw new AAIException("AAI is not available.", e);
        }
    }
    
    @Override
    public JSONObject createOrUpdateCustomer(HttpServletRequest request,String customerId){
    		JSONObject result = new JSONObject();
		try {
			logger.info("aai createOrUpdateCustomer is starting!");
			RequestBody requestBody = extractBody(request);
			Response<ResponseBody> response = this.aaiService.createOrUpdateCustomer(customerId,requestBody).execute();
			logger.info("aai createOrUpdateCustomer is finished!");
			if(response.isSuccessful()){
				result.put("status", "SUCCESS");
			}else{
				result.put("status", "FAILED");
				result.put("errorMessage",String.format("Can not get createOrUpdateCustomer[code=%s, message=%s]", response.code(), response.message()));
			}
		} catch (IOException e) {
			result.put("status", "FAILED");
			result.put("errorMessage","createOrUpdateCustomer occur exception:"+e.getMessage());
		}
		return result;
    }
    
    @Override
    public JSONObject getCustomerById(String customerId){
    		JSONObject result = new JSONObject();
		try {
			logger.info("aai getCustomerById is starting!");
			Response<AAICustomer> response = this.aaiService.getCustomerById(customerId).execute();
			logger.info("aai getCustomerById is finished!");
			if(response.isSuccessful()){
				result.put("status", "SUCCESS");
				result.put("result",response.body());
			}else{
				result.put("status", "FAILED");
				result.put("errorMessage",String.format("Can not get getCustomerById[code=%s, message=%s]", response.code(), response.message()));
			}
		} catch (IOException e) {
			result.put("status", "FAILED");
			result.put("errorMessage","getCustomerById occur exception:"+e.getMessage());
		}
		return result;
    }
    
    @Override
    public JSONObject deleteCustomer(String customerId,String resourceVersion){
		JSONObject result = new JSONObject();
		try {
			logger.info("aai deleteCustomer is starting!");
			Response<ResponseBody> response = this.aaiService.deleteCustomer(customerId,resourceVersion).execute();
			logger.info("aai deleteCustomer is finished!");
			if(response.isSuccessful()){
				result.put("status", "SUCCESS");
			}else{
				result.put("status", "FAILED");
				result.put("errorMessage",String.format("Can not get deleteCustomer[code=%s, message=%s]", response.code(), response.message()));
			}
		} catch (IOException e) {
			result.put("status", "FAILED");
			if(e.getMessage().contains("204")){
				result.put("status", "SUCCESS");
			}
			result.put("errorMessage","deleteCustomer occur exception:"+e.getMessage());
		}
		return result;
    }
    
    @Override
    public List<AAIServiceSubscription> listServiceSubscriptions(String serviceType) {
        try {
            Response<ServiceSubscriptionRsp> response = this.aaiService.listServiceSubscriptions(serviceType).execute();
            if (response.isSuccessful()) {
                return response.body().getServiceSubscriptions();
            } else {
                logger.info(String.format("Can not get service-subscriptions[code=%s, message=%s]", response.code(), response.message()));
                return Collections.emptyList();
            }
        } catch (IOException e) {
            logger.error("list customers occur exception");
            throw new AAIException("AAI is not available.", e);
        }
    }
    
    @Override
    public JSONObject createOrUpdateServiceType(HttpServletRequest request,String serviceType){
		JSONObject result = new JSONObject();
		try {
			logger.info("aai createOrUpdateServiceType is starting!");
			RequestBody requestBody = extractBody(request);
			Response<ResponseBody> response = this.aaiService.createOrUpdateCustomer(serviceType,requestBody).execute();
			logger.info("aai createOrUpdateServiceType is finished!");
			if(response.isSuccessful()){
				result.put("status", "SUCCESS");
			}else{
				result.put("status", "FAILED");
				result.put("errorMessage",String.format("Can not get createOrUpdateServiceType[code=%s, message=%s]", response.code(), response.message()));
			}
		} catch (IOException e) {
			result.put("status", "FAILED");
			result.put("errorMessage","createOrUpdateServiceType occur exception:"+e.getMessage());
		}
		return result;
    }
    
    @Override
    public JSONObject deleteServiceType(String customerId,String serviceType,String resourceVersion){
		JSONObject result = new JSONObject();
		try {
			logger.info("aai deleteServiceType is starting!");
			Response<ResponseBody> response = this.aaiService.deleteServiceType(customerId,serviceType,resourceVersion).execute();
			logger.info("aai deleteServiceType is finished!");
			if(response.isSuccessful()){
				result.put("status", "SUCCESS");
			}else{
				result.put("status", "FAILED");
				result.put("errorMessage",String.format("Can not get deleteServiceType[code=%s, message=%s]", response.code(), response.message()));
			}
		} catch (IOException e) {
			result.put("status", "FAILED");
			if(e.getMessage().contains("204")){
				result.put("status", "SUCCESS");
			}
			result.put("errorMessage","deleteServiceType occur exception:"+e.getMessage());
		}
		return result;
    }
    
    @Override
    public JSONObject getServiceTypeById(String customerId,String serviceType){
    		JSONObject result = new JSONObject();
		try {
			logger.info("aai getServiceTypeById is starting!");
			Response<AAIServiceSubscription> response = this.aaiService.getServiceTypeById(customerId,serviceType).execute();
			logger.info("aai getServiceTypeById is finished!");
			if(response.isSuccessful()){
				result.put("status", "SUCCESS");
				result.put("result",response.body());
			}else{
				result.put("status", "FAILED");
				result.put("errorMessage",String.format("Can not get getServiceTypeById[code=%s, message=%s]", response.code(), response.message()));
			}
		} catch (IOException e) {
			result.put("status", "FAILED");
			result.put("errorMessage","getServiceTypeById occur exception:"+e.getMessage());
		}
		return result;
    }
}
