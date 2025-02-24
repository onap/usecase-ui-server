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

import org.onap.usecaseui.server.service.slicingdomain.aai.bean.connection.NetworkInfo;
import org.onap.usecaseui.server.service.slicingdomain.aai.bean.AAIServiceNST;

import com.alibaba.fastjson.JSONObject;
import org.onap.usecaseui.server.service.slicingdomain.aai.bean.connection.ConnectionLink;
import org.onap.usecaseui.server.service.slicingdomain.aai.bean.connection.ConnectionLinkList;
import org.onap.usecaseui.server.service.slicingdomain.aai.bean.connection.EndPointInfoList;
import org.onap.usecaseui.server.service.slicingdomain.aai.bean.connection.NetworkPolicy;

import org.onap.usecaseui.server.service.slicingdomain.aai.bean.connection.SliceProfileList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AAISliceClient {

	@GET("business/customers/customer/{global-customer-id}/service-subscriptions/service-subscription/{service-type}/service-instances?service-role=service-profile")
  Call<JSONObject> listService(@Path("global-customer-id") String globalCustomerId,@Path("service-type") String serviceType);

	@GET("business/customers/customer/{global-customer-id}/service-subscriptions/service-subscription/{service-type}/service-instances?service-role=service-profile")
	Call<JSONObject> listServiceByStatus(@Path("global-customer-id") String globalCustomerId,@Path("service-type") String serviceType,@Query("orchestration-status") String orchestrationStatus);

	@GET("business/customers/customer/{global-customer-id}/service-subscriptions/service-subscription/{service-type}/service-instances/service-instance/{service-instance-id}")
	Call<JSONObject> listServiceById(@Path("global-customer-id") String globalCustomerId,@Path("service-type") String serviceType,@Path("service-instance-id") String businessId);

	@GET("business/customers/customer/{global-customer-id}/service-subscriptions/service-subscription/{service-type}/service-instances?service-role=nsi")
	Call<JSONObject> listServiceNSI(@Path("global-customer-id") String globalCustomerId,@Path("service-type") String serviceType);

	@GET("business/customers/customer/{global-customer-id}/service-subscriptions/service-subscription/{service-type}/service-instances?service-role=nsi")
	Call<JSONObject> listServiceNSIByStatus(@Path("global-customer-id") String globalCustomerId,@Path("service-type") String serviceType,@Query("orchestration-status") String orchestrationStatus);

	@GET("business/customers/customer/{global-customer-id}/service-subscriptions/service-subscription/{service-type}/service-instances?service-role=nssi")
	Call<JSONObject> listServiceNSSI(@Path("global-customer-id") String globalCustomerId,@Path("service-type") String serviceType);

	@GET("business/customers/customer/{global-customer-id}/service-subscriptions/service-subscription/{service-type}/service-instances?service-role=nssi")
	Call<JSONObject> listServiceNSSIByStatus(@Path("global-customer-id") String globalCustomerId,@Path("service-type") String serviceType,@Query("orchestration-status") String orchestrationStatus);

	@GET("business/customers/customer/{global-customer-id}/service-subscriptions/service-subscription/{service-type}/service-instances?service-role=nssi")
	Call<JSONObject> listServiceNSSIByEnv(@Path("global-customer-id") String globalCustomerId,@Path("service-type") String serviceType,@Query("environment-context") String environmentContext);

	@GET("business/customers/customer/{global-customer-id}/service-subscriptions/service-subscription/{service-type}/service-instances/service-instance/{service-instance-id}/service-profiles")
	Call<JSONObject> getServiceProfiles(@Path("global-customer-id") String globalCustomerId,@Path("service-type") String serviceType,@Path("service-instance-id") String serviceInstanceId);

	@GET("business/customers/customer/{global-customer-id}/service-subscriptions/service-subscription/{service-type}/service-instances/service-instance/{service-instance-id}/allotted-resources")
	Call<JSONObject> queryAllottedResources(@Path("global-customer-id") String globalCustomerId,@Path("service-type") String serviceType,@Path("service-instance-id") String serviceInstanceId);

	@GET("business/customers/customer/{global-customer-id}/service-subscriptions/service-subscription/{service-type}/service-instances/service-instance/{service-instance-id}")
	Call<JSONObject> querySerAndSubInsByNSI(@Path("global-customer-id") String globalCustomerId,@Path("service-type") String serviceType,@Path("service-instance-id") String serviceInstanceId);

	@GET("business/customers/customer/{global-customer-id}/service-subscriptions/service-subscription/{service-type}/service-instances/service-instance/{service-instance-id}")
	Call<JSONObject> queryNSIByNSSI(@Path("global-customer-id") String globalCustomerId,@Path("service-type") String serviceType,@Path("service-instance-id") String serviceInstanceId);

	@GET("business/customers/customer/{global-customer-id}/service-subscriptions/service-subscription/{service-type}/service-instances/service-instance/{service-instance-id}")
	Call<JSONObject> queryOrderByService(@Path("global-customer-id") String globalCustomerId,@Path("service-type") String serviceType,@Path("service-instance-id") String serviceInstanceId);

	@GET("business/customers/customer/{global-customer-id}/service-subscriptions/service-subscription/{service-type}/service-instances/service-instance/{service-instance-id}")
	Call<JSONObject> queryOrderByOrderId(@Path("global-customer-id") String globalCustomerId,@Path("service-type") String serviceType,@Path("service-instance-id") String serviceInstanceId);

	@GET("service-design-and-creation/models/model/{model-invariant-id}/model-vers/model-ver/{model-version-id}")
	Call<AAIServiceNST> queryServiceNST(@Path("model-invariant-id") String modelInvariantIid,
																			@Path("model-version-id") String modelVersionId);

	@GET("business/customers/customer/{global-customer-id}/service-subscriptions/service-subscription/{service-type}/service-instances?service-role=communication-service")
	Call<JSONObject> listOrders(@Path("global-customer-id") String globalCustomerId,@Path("service-type") String serviceType);

	@GET("network/logical-links")
	Call<ConnectionLinkList> getConnectionLinks();

	@GET("network/network-routes/network-route/{route-id}")
	Call<EndPointInfoList> getEndpointByLinkName(@Path("route-id") String linkName);

	@GET("network/network-routes/network-route/{route-id}")
	Call<EndPointInfoList> getEndpointByLinkName2(@Path("route-id") String linkName2);

	@GET("business/customers/customer/5GCustomer/service-subscriptions/service-subscription/5G/service-instances/service-instance/{service-instance-id}/allotted-resources/allotted-resource/{allotted-resource-id}")
	Call<ConnectionLink> getAllottedResource(@Path("service-instance-id") String serviceInstancesId,@Path("allotted-resource-id") String allottedResourceId);

	@GET("business/customers/customer/5GCustomer/service-subscriptions/service-subscription/5G/service-instances/service-instance/{service-instance-id}")
	Call<ConnectionLink> getServiceInstance(@Path("service-instance-id") String serviceInstancesId);

	@GET("business/customers/customer/5GCustomer/service-subscriptions/service-subscription/5G/service-instances/service-instance/{service-instance-id}")
	Call<NetworkInfo> getServiceNetworkInstance(@Path("service-instance-id") String serviceInstancesId);

	@GET("network/network-policies/network-policy/{network-policy-id}")
	Call<NetworkPolicy> getNetworkPolicy(@Path("network-policy-id")String relationshipValue);

	@GET("business/customers/customer/5GCustomer/service-subscriptions/service-subscription/5G/service-instances/service-instance/{service-instance-id}/slice-profiles")
	Call<SliceProfileList> getSliceProfiles(@Path("service-instance-id") String serviceInstancesId);

}
