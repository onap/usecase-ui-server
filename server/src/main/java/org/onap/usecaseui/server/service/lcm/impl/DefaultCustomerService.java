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
import org.onap.usecaseui.server.service.lcm.domain.aai.bean.PInterface;
import org.onap.usecaseui.server.service.lcm.domain.aai.bean.Results;
import org.onap.usecaseui.server.service.lcm.domain.aai.bean.AAINetworkInterfaceResponse;

import org.onap.usecaseui.server.service.lcm.domain.aai.exceptions.AAIException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;

import static org.onap.usecaseui.server.util.RestfulServices.extractBody;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("CustomerService")
@RequiredArgsConstructor
public class DefaultCustomerService implements CustomerService {

    private final AAIService aaiService;

    @Override
    public List<AAICustomer> listCustomer() {
        try {
            Response<AAICustomerRsp> response = this.aaiService.listCustomer().execute();
            if (response.isSuccessful()) {
                return response.body().getCustomer();
            } else {
                log.info(String.format("Can not get customers[code=%s, message=%s]", response.code(), response.message()));
                return Collections.emptyList();
            }
        } catch (IOException e) {
            log.error("list customers occur exception");
            throw new AAIException("AAI is not available.", e);
        }
    }

    @Override
    public JSONObject createOrUpdateCustomer(HttpServletRequest request,String customerId){
    		JSONObject result = new JSONObject();
		try {
			log.info("aai createOrUpdateCustomer is starting!");
			RequestBody requestBody = extractBody(request);
			Response<ResponseBody> response = this.aaiService.createOrUpdateCustomer(customerId,requestBody).execute();
			log.info("aai createOrUpdateCustomer is finished!");
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
			log.info("aai getCustomerById is starting!");
			Response<AAICustomer> response = this.aaiService.getCustomerById(customerId).execute();
			log.info("aai getCustomerById is finished!");
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
			log.info("aai deleteCustomer is starting!");
			Response<ResponseBody> response = this.aaiService.deleteCustomer(customerId,resourceVersion).execute();
			log.info("aai deleteCustomer is finished!");
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
                log.info(String.format("Can not get service-subscriptions[code=%s, message=%s]", response.code(), response.message()));
                return Collections.emptyList();
            }
        } catch (IOException e) {
            log.error("list customers occur exception");
            throw new AAIException("AAI is not available.", e);
        }
    }

    @Override
    public JSONObject createOrUpdateServiceType(HttpServletRequest request,String serviceType,String customerId){
		JSONObject result = new JSONObject();
		try {
			log.info("aai createOrUpdateServiceType is starting!");
			RequestBody requestBody = extractBody(request);
			Response<ResponseBody> response = this.aaiService.createOrUpdateServiceType(customerId,serviceType,requestBody).execute();
			log.info("aai createOrUpdateServiceType is finished!");
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
			log.info("aai deleteServiceType is starting!");
			Response<ResponseBody> response = this.aaiService.deleteServiceType(customerId,serviceType,resourceVersion).execute();
			log.info("aai deleteServiceType is finished!");
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
    public JSONObject getServiceTypeById(String customerId, String serviceType) {
        JSONObject result = new JSONObject();
        try {
            log.info("aai getServiceTypeById is starting!");
            Response<AAIServiceSubscription> response =
                    this.aaiService.getServiceTypeById(customerId, serviceType).execute();
            log.info("aai getServiceTypeById is finished!");
            if (response.isSuccessful()) {
                result.put("status", "SUCCESS");
                result.put("result", response.body());
            } else {
                result.put("status", "FAILED");
                result.put("errorMessage", String.format("Can not get getServiceTypeById[code=%s, message=%s]",
                        response.code(), response.message()));
            }
        } catch (IOException e) {
            result.put("status", "FAILED");
            result.put("errorMessage", "getServiceTypeById occur exception:" + e.getMessage());
        }
        return result;
    }

        @Override
    public List<String> fetchNIList(String networkInterfaceType) {
        List<String> niList = new ArrayList<String>();
        AAINetworkInterfaceResponse niResponse = null;
        ObjectMapper mapper = new ObjectMapper();
        Results[] interfaceList = null;
        try {
            log.info("aai fetchNIList is starting!");
            String body = "{\r\n" + "\"start\" : [\"network\"],\r\n" + "\"query\" : \"query/getInterfaceTypes?porttype="
                    + networkInterfaceType + "\"\r\n" + "}";
            log.info("request body {} for Interface type {}" , body,networkInterfaceType);
            RequestBody request = RequestBody.create(MediaType.parse("application/json"), body);
            Response<ResponseBody> response = this.aaiService.querynNetworkResourceList(request).execute();
            if (response.isSuccessful()) {
                String jsonResponse = response.body().string();
                log.info("response json returned {}", jsonResponse);
                try {
                    niResponse = mapper.readValue(jsonResponse, AAINetworkInterfaceResponse.class);
                } catch (IOException ex) {
                    log.info("read value exception", ex);
                }
                if (niResponse != null) {
                    interfaceList = niResponse.getResults();
                }
                for (Results result : interfaceList) {
                    PInterface pInterface = result.getPinterface();
                    niList.add(pInterface.getInterfaceName() + " (" + pInterface.getPortDescription() + ")");
                }
            } else {
                log.error("Request to AAI Fails dues to {} " , response.errorBody());
                throw new IOException(response.errorBody().toString());
            }
        } catch (Exception e) {
            niResponse = null;
            log.info("Request to AAI Fails dues to " + e);
            log.info("Mocking Response Data");

            String jsonMock = "{\r\n" + "    \"results\": [\r\n" + "        {\r\n"
                    + "            \"p-interface\": {\r\n"
                    + "                \"interface-name\": \"nodeId-11.11.11.12-ltpId-2\",\r\n"
                    + "                \"speed-value\": \"100\",\r\n" + "                \"speed-units\": \"Gbps\",\r\n"
                    + "                \"port-description\": \"\",\r\n"
                    + "                \"interface-type\": \"XPONDER-NETWORK\",\r\n"
                    + "                \"network-interface-type\": \"ENNI\",\r\n"
                    + "                \"resource-version\": \"1572522050145\",\r\n"
                    + "                \"in-maint\": true,\r\n"
                    + "                \"network-ref\": \"otn-topology\",\r\n"
                    + "                \"operational-status\": \"up\",\r\n"
                    + "                \"relationship-list\": {\r\n" + "                    \"relationship\": [\r\n"
                    + "                        {\r\n"
                    + "                            \"related-to\": \"logical-link\",\r\n"
                    + "                            \"relationship-label\": \"tosca.relationships.network.LinksTo\",\r\n"
                    + "                            \"related-link\": \"/aai/v16/network/logical-links/logical-link/nodeId-11.11.11.12-ltpId-2_nodeId-12.12.12.12-ltpId-1\",\r\n"
                    + "                            \"relationship-data\": [\r\n"
                    + "                                {\r\n"
                    + "                                    \"relationship-key\": \"logical-link.link-name\",\r\n"
                    + "                                    \"relationship-value\": \"nodeId-11.11.11.12-ltpId-2_nodeId-12.12.12.12-ltpId-1\"\r\n"
                    + "                                }\r\n" + "                            ]\r\n"
                    + "                        }\r\n" + "                    ]\r\n" + "                }\r\n"
                    + "           }\r\n" + "        },\r\n" + "        {\r\n" + "            \"p-interface\": {\r\n"
                    + "                \"interface-name\": \"nodeId-12.12.12.12-ltpId-1\",\r\n"
                    + "                \"speed-value\": \"100\",\r\n" + "                \"speed-units\": \"Gbps\",\r\n"
                    + "                \"port-description\": \"\",\r\n"
                    + "                \"interface-type\": \"XPONDER-NETWORK\",\r\n"
                    + "                \"network-interface-type\": \"ENNI\",\r\n"
                    + "                \"resource-version\": \"1572522469912\",\r\n"
                    + "                \"in-maint\": true,\r\n"
                    + "                \"network-ref\": \"tapi-topology\",\r\n"
                    + "                \"operational-status\": \"up\",\r\n"
                    + "                \"relationship-list\": {\r\n" + "                    \"relationship\": [\r\n"
                    + "                        {\r\n"
                    + "                            \"related-to\": \"logical-link\",\r\n"
                    + "                            \"relationship-label\": \"tosca.relationships.network.LinksTo\",\r\n"
                    + "                            \"related-link\": \"/aai/v16/network/logical-links/logical-link/nodeId-11.11.11.12-ltpId-2_nodeId-12.12.12.12-ltpId-1\",\r\n"
                    + "                            \"relationship-data\": [\r\n"
                    + "                                {\r\n"
                    + "                                    \"relationship-key\": \"logical-link.link-name\",\r\n"
                    + "                                    \"relationship-value\": \"nodeId-11.11.11.12-ltpId-2_nodeId-12.12.12.12-ltpId-1\"\r\n"
                    + "                                }\r\n" + "                            ]\r\n"
                    + "                        }\r\n" + "                    ]\r\n" + "                }\r\n"
                    + "            }\r\n" + "        }\r\n" + "    ]\r\n" + "}\r\n" + "";

            try {
                niResponse = mapper.readValue(jsonMock, AAINetworkInterfaceResponse.class);
            } catch (IOException ex) {
                log.info("ReadValue exception", ex);
            }

            if (niResponse != null) {
                interfaceList = niResponse.getResults();
            }
            for (Results result : interfaceList) {
                PInterface pInterface = result.getPinterface();
                niList.add(pInterface.getInterfaceName());
            }

	}
	Collections.sort(niList);
        return niList;
    }

}
