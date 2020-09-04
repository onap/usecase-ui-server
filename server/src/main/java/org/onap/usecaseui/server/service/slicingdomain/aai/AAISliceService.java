/*
 * Copyright (C) 2019 CMCC, Inc. and others. All rights reserved.
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
package org.onap.usecaseui.server.service.slicingdomain.aai;

import org.onap.usecaseui.server.service.slicingdomain.aai.bean.AAIServiceNST;

import com.alibaba.fastjson.JSONObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AAISliceService {

	@Headers({
        "X-TransactionId: 9999",
        "X-FromAppId: MSO",
        "Authorization: Basic QUFJOkFBSQ==",
        "Accept: application/json"
	})
	@GET("/api/aai-business/v13/customers/customer/{global-customer-id}/service-subscriptions/service-subscription/{service-type}/service-instances?service-role=service-profile")
    Call<JSONObject> listService(@Path("global-customer-id") String globalCustomerId,@Path("service-type") String serviceType);
	
	@Headers({
        "X-TransactionId: 9999",
        "X-FromAppId: MSO",
        "Authorization: Basic QUFJOkFBSQ==",
        "Accept: application/json"
	})
	@GET("/api/aai-business/v13/customers/customer/{global-customer-id}/service-subscriptions/service-subscription/{service-type}/service-instances?service-role=service-profile")
	Call<JSONObject> listServiceByStatus(@Path("global-customer-id") String globalCustomerId,@Path("service-type") String serviceType,@Query("orchestration-status") String orchestrationStatus);

	@Headers({
        "X-TransactionId: 9999",
        "X-FromAppId: MSO",
        "Authorization: Basic QUFJOkFBSQ==",
        "Accept: application/json"
	})
	@GET("/api/aai-business/v13/customers/customer/{global-customer-id}/service-subscriptions/service-subscription/{service-type}/service-instances/service-instance/{service-instance-id}")
	Call<JSONObject> listServiceById(@Path("global-customer-id") String globalCustomerId,@Path("service-type") String serviceType,@Path("service-instance-id") String businessId);

	
	@Headers({
        "X-TransactionId: 9999",
        "X-FromAppId: MSO",
        "Authorization: Basic QUFJOkFBSQ==",
        "Accept: application/json"
	})
	@GET("/api/aai-business/v13/customers/customer/{global-customer-id}/service-subscriptions/service-subscription/{service-type}/service-instances?service-role=nsi")
	Call<JSONObject> listServiceNSI(@Path("global-customer-id") String globalCustomerId,@Path("service-type") String serviceType);

	@Headers({
        "X-TransactionId: 9999",
        "X-FromAppId: MSO",
        "Authorization: Basic QUFJOkFBSQ==",
        "Accept: application/json"
	})
	@GET("/api/aai-business/v13/customers/customer/{global-customer-id}/service-subscriptions/service-subscription/{service-type}/service-instances?service-role=nsi")
	Call<JSONObject> listServiceNSIByStatus(@Path("global-customer-id") String globalCustomerId,@Path("service-type") String serviceType,@Query("orchestration-status") String orchestrationStatus);
	
	@Headers({
        "X-TransactionId: 9999",
        "X-FromAppId: MSO",
        "Authorization: Basic QUFJOkFBSQ==",
        "Accept: application/json"
	})
	@GET("/api/aai-business/v13/customers/customer/{global-customer-id}/service-subscriptions/service-subscription/{service-type}/service-instances?service-role=nssi")
	Call<JSONObject> listServiceNSSI(@Path("global-customer-id") String globalCustomerId,@Path("service-type") String serviceType);
	
	@Headers({
        "X-TransactionId: 9999",
        "X-FromAppId: MSO",
        "Authorization: Basic QUFJOkFBSQ==",
        "Accept: application/json"
	})
	@GET("/api/aai-business/v13/customers/customer/{global-customer-id}/service-subscriptions/service-subscription/{service-type}/service-instances?service-role=nssi")
	Call<JSONObject> listServiceNSSIByStatus(@Path("global-customer-id") String globalCustomerId,@Path("service-type") String serviceType,@Query("orchestration-status") String orchestrationStatus);

	@Headers({
        "X-TransactionId: 9999",
        "X-FromAppId: MSO",
        "Authorization: Basic QUFJOkFBSQ==",
        "Accept: application/json"
	})
	@GET("/api/aai-business/v13/customers/customer/{global-customer-id}/service-subscriptions/service-subscription/{service-type}/service-instances?service-role=nssi")
	Call<JSONObject> listServiceNSSIByEnv(@Path("global-customer-id") String globalCustomerId,@Path("service-type") String serviceType,@Query("environment-context") String environmentContext);

	
	@Headers({
        "X-TransactionId: 9999",
        "X-FromAppId: MSO",
        "Authorization: Basic QUFJOkFBSQ==",
        "Accept: application/json"
	})
	@GET("/api/aai-business/v19/customers/customer/{global-customer-id}/service-subscriptions/service-subscription/{service-type}/service-instances/service-instance/{service-instance-id}/service-profiles")
	Call<JSONObject> getServiceProfiles(@Path("global-customer-id") String globalCustomerId,@Path("service-type") String serviceType,@Path("service-instance-id") String serviceInstanceId);

	@Headers({
        "X-TransactionId: 9999",
        "X-FromAppId: MSO",
        "Authorization: Basic QUFJOkFBSQ==",
        "Accept: application/json"
	})
	@GET("/api/aai-business/v13/customers/customer/{global-customer-id}/service-subscriptions/service-subscription/{service-type}/service-instances/service-instance/{service-instance-id}/allotted-resources")
	Call<JSONObject> queryAllottedResources(@Path("global-customer-id") String globalCustomerId,@Path("service-type") String serviceType,@Path("service-instance-id") String serviceInstanceId);

	@Headers({
        "X-TransactionId: 9999",
        "X-FromAppId: MSO",
        "Authorization: Basic QUFJOkFBSQ==",
        "Accept: application/json"
	})
	@GET("/api/aai-business/v13/customers/customer/{global-customer-id}/service-subscriptions/service-subscription/{service-type}/service-instances/service-instance/{service-instance-id}")
	Call<JSONObject> querySerAndSubInsByNSI(@Path("global-customer-id") String globalCustomerId,@Path("service-type") String serviceType,@Path("service-instance-id") String serviceInstanceId);

	@Headers({
        "X-TransactionId: 9999",
        "X-FromAppId: MSO",
        "Authorization: Basic QUFJOkFBSQ==",
        "Accept: application/json"
	})
	@GET("/api/aai-business/v13/customers/customer/{global-customer-id}/service-subscriptions/service-subscription/{service-type}/service-instances/service-instance/{service-instance-id}")
	Call<JSONObject> queryNSIByNSSI(@Path("global-customer-id") String globalCustomerId,@Path("service-type") String serviceType,@Path("service-instance-id") String serviceInstanceId);

	@Headers({
        "X-TransactionId: 9999",
        "X-FromAppId: MSO",
        "Authorization: Basic QUFJOkFBSQ==",
        "Accept: application/json"
	})
	@GET("/api/aai-business/v13/customers/customer/{global-customer-id}/service-subscriptions/service-subscription/{service-type}/service-instances/service-instance/{service-instance-id}")
	Call<JSONObject> queryOrderByService(@Path("global-customer-id") String globalCustomerId,@Path("service-type") String serviceType,@Path("service-instance-id") String serviceInstanceId);

	@Headers({
        "X-TransactionId: 9999",
        "X-FromAppId: MSO",
        "Authorization: Basic QUFJOkFBSQ==",
        "Accept: application/json"
	})
	@GET("/api/aai-business/v13/customers/customer/{global-customer-id}/service-subscriptions/service-subscription/{service-type}/service-instances/service-instance/{service-instance-id}")
	Call<JSONObject> queryOrderByOrderId(@Path("global-customer-id") String globalCustomerId,@Path("service-type") String serviceType,@Path("service-instance-id") String serviceInstanceId);

	
	@Headers({
        "X-TransactionId: 9999",
        "X-FromAppId: MSO",
        "Authorization: Basic QUFJOkFBSQ==",
        "Accept: application/json"
	})
	@GET("/api/aai-sdc/v13/models/model/{model-invariant-id}/model-vers/model-ver/{model-version-id}")
	Call<AAIServiceNST> queryServiceNST(@Path("model-invariant-id") String modelInvariantIid,
			@Path("model-version-id") String modelVersionId);

	@Headers({
		"X-TransactionId: 9999",
		"X-FromAppId: MSO",
		"Authorization: Basic QUFJOkFBSQ==",
		"Accept: application/json"
	})
	@GET("/api/aai-business/v13/customers/customer/{global-customer-id}/service-subscriptions/service-subscription/{service-type}/service-instances?service-role=communication-service")
	Call<JSONObject> listOrders(@Path("global-customer-id") String globalCustomerId,@Path("service-type") String serviceType);
}
