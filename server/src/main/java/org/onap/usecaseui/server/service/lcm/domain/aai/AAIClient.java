/**
 * Copyright 2016-2017 ZTE Corporation.
 * Copyright 2020 Huawei Corporation.
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
package org.onap.usecaseui.server.service.lcm.domain.aai;

import org.onap.usecaseui.server.bean.sotn.PinterfaceRsp;
import org.onap.usecaseui.server.service.lcm.domain.aai.bean.AAICustomer;
import org.onap.usecaseui.server.service.lcm.domain.aai.bean.AAICustomerRsp;
import org.onap.usecaseui.server.service.lcm.domain.aai.bean.AAIOrchestratorRsp;
import org.onap.usecaseui.server.service.lcm.domain.aai.bean.AAIServiceSubscription;
import org.onap.usecaseui.server.service.lcm.domain.aai.bean.AAISingleOrchestratorRsp;
import org.onap.usecaseui.server.service.lcm.domain.aai.bean.SDNCControllerRsp;
import org.onap.usecaseui.server.service.lcm.domain.aai.bean.ServiceSubscriptionRsp;
import org.onap.usecaseui.server.service.lcm.domain.aai.bean.VimInfoRsp;


import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AAIClient {


//    @GET("/api/aai-business/v11/customers")
    @GET("/api/aai-business/v13/customers")
    Call<AAICustomerRsp> listCustomer();

	@GET("/api/aai-externalSystem/v16/esr-nfvo-list")
	Call<AAIOrchestratorRsp> listOrchestrator();

	@GET("/api/aai-externalSystem/v16/esr-nfvo-list/esr-nfvo/{nfvo-id}?depth=all")
	Call<AAISingleOrchestratorRsp> getOrchestrator(@Path("nfvo-id") String nfvoId);

    @PUT("/api/aai-business/v13/customers/customer/{global-customer-id}")
    Call<ResponseBody> createOrUpdateCustomer(@Path("global-customer-id") String customerId,@Body RequestBody body);

    @DELETE("/api/aai-business/v13//customers/customer/{global-customer-id}")
    Call<ResponseBody> deleteCustomer(@Path("global-customer-id") String customerId,@Query("resource-version") String resourceVersion);

    @GET("/api/aai-business/v13//customers/customer/{global-customer-id}")
    Call<AAICustomer> getCustomerById(@Path("global-customer-id") String customerId);

//    @GET("/api/aai-business/v11/customers/customer/{global-customer-id}/service-subscriptions/service-subscription/{service-type}/service-instances")
    @GET("/api/aai-business/v16/customers/customer/{global-customer-id}/service-subscriptions/service-subscription/{service-type}/service-instances")
    Call<ResponseBody> listServiceInstances(@Path("global-customer-id") String customerId, @Path("service-type") String serviceType);

//    @GET("/cloud-infrastructure/cloud-regions")
    @GET("/api/aai-cloudInfrastructure/v11/cloud-regions")
    Call<VimInfoRsp> listVimInfo();

//    @GET("/api/aai-business/v11/customers/customer/{global-customer-id}/service-subscriptions")
    @GET("/api/aai-business/v11/customers/customer/{global-customer-id}/service-subscriptions")
    Call<ServiceSubscriptionRsp> listServiceSubscriptions(@Path("global-customer-id") String customerId);

	//@GET("/api/aai-business/v11/customers/customer/{global-customer-id}/service-subscriptions")
	@PUT("/api/aai-business/v11/customers/customer/{global-customer-id}/service-subscriptions/service-subscription/{service-type}")
	Call<ResponseBody> createOrUpdateServiceType(@Path("global-customer-id") String customerId,@Path("service-type") String serviceType,@Body RequestBody body);

	//@GET("/api/aai-business/v11/customers/customer/{global-customer-id}/service-subscriptions")
	@DELETE("/api/aai-business/v11/customers/customer/{global-customer-id}/service-subscriptions/service-subscription/{service-type}")
	Call<ResponseBody> deleteServiceType(@Path("global-customer-id") String customerId,@Path("service-type") String serviceType,@Query("resource-version") String resourceVersion);

	//@GET("/api/aai-business/v11/customers/customer/{global-customer-id}/service-subscriptions")
	@GET("/api/aai-business/v11/customers/customer/{global-customer-id}/service-subscriptions/service-subscription/{service-type}")
	Call<AAIServiceSubscription> getServiceTypeById(@Path("global-customer-id") String customerId,@Path("service-type") String serviceType);

    @GET("/api/aai-externalSystem/v11/esr-thirdparty-sdnc-list")
    Call<SDNCControllerRsp> listSdncControllers();

	@GET("/api/aai-business/v11/customers/customer/{customerId}/service-subscriptions/service-subscription/{service-type}/service-instances/service-instance/{serviceId}")
	Call<ResponseBody> getAAIServiceInstance(@Path("customerId") String customerId,@Path("service-type") String seviceType,@Path("serviceId") String serviceId);

	@GET("/api/aai-network/v14/network-resources")
	Call<ResponseBody> listNetWorkResources();

	@GET("/api/aai-network/v14/pnfs/pnf/{pnfName}/p-interfaces")
	Call<PinterfaceRsp> getPinterfaceByPnfName(@Path("pnfName") String pnfName);

	@GET("/aai/v24/network/logical-links")
	Call<ResponseBody> getLogicalLinks();

    @GET("/api/aai-network/v14/logical-links/logical-link/{link-name}")
    Call<ResponseBody> getSpecificLogicalLink(@Path("link-name") String linkName);

    @PUT("/api/aai-network/v14/network-resources/network-resource/{networkId}")
    Call<ResponseBody> createTopoNetwork(@Body RequestBody body,@Path("networkId") String networkId);

    @PUT("/api/aai-network/v14/ext-aai-networks/ext-aai-network/{aai-id}")
    Call<ResponseBody> createHostUrl(@Body RequestBody body,@Path("aai-id") String aaiId);

	@GET("/api/aai-network/v14/ext-aai-networks/ext-aai-network/{aai-id}")
	Call<ResponseBody> getExtAaiId(@Path("aai-id") String aaiId);

	@GET("/api/aai-network/v14/ext-aai-networks/ext-aai-network/{aai-id}/esr-system-info")
	Call<ResponseBody> getHostUrl(@Path("aai-id") String aaiId);

    @PUT("/api/aai-network/v14/pnfs/pnf/{pnfName}/p-interfaces/p-interface/{tp-id}")
    Call<ResponseBody> createTerminationPoint(@Body RequestBody body,@Path("pnfName") String pnfName,@Path("tp-id") String tpId);

    @PUT("/api/aai-network/v14/pnfs/pnf/{pnfname}")
    Call<ResponseBody> createPnf(@Body RequestBody body,@Path("pnfname") String pnfname);

    @PUT("/api/aai-network/v14/logical-links/logical-link/{linkName}")
    Call<ResponseBody> createLink(@Body RequestBody body,@Path("linkName") String linkName);

    @DELETE("/api/aai-network/v14/logical-links/logical-link/{linkName}")
    Call<ResponseBody> deleteLink(@Path("linkName") String linkName,@Query("resource-version") String resourceVersion);

    @GET("/api/aai-business/v14/customers/customer/{global-customer-id}/service-subscriptions/service-subscription/{service-type}/service-instances")
    Call<ResponseBody> getServiceInstances(@Path("global-customer-id") String customerId,@Path("service-type") String serviceType);

    @GET("/api/aai-business/v14/customers/customer/{global-customer-id}/service-subscriptions/service-subscription/{service-type}/service-instances")
    Call<ResponseBody> serviceInstaneInfo(@Path("global-customer-id") String globalCustomerId,@Path("service-type") String serviceType,@Query("service-instance-id") String serviceInstanceId);

    @GET("/api/aai-business/v14/customers/customer/{global-customer-id}/service-subscriptions/service-subscription/{service-type}/service-instances/service-instance/{service-instance-id}/allotted-resources")
    Call<ResponseBody> getAllottedResources(@Path("global-customer-id") String globalCustomerId,@Path("service-type") String serviceType,@Path("service-instance-id") String serviceInstanceId);

    @GET("/aai/v24/network/pnfs")
    Call<ResponseBody> getPnfInfo(@Query("pnfName") String pnfName);

    @GET("/aai/v24/network/connectivities")
    Call<ResponseBody> getConnectivityInfo(@Query("connectivity-id") String connectivityId);

    @GET("/aai/v24/network/vpn-bindings")
    Call<ResponseBody> getVpnBindingInfo(@Query("vpn-id") String vpnId);

    @GET("/aai/v24/network/network-routes")
    Call<ResponseBody> getNetworkRouteInfo(@Query("route-id") String routeId);

    @GET("/aai/v24/network/unis")
    Call<ResponseBody> getUniInfo(@Query("uni-id") String uniId);

    @GET("/api/aai-network/v14/vpn-bindings")
    Call<ResponseBody> getPinterfaceByVpnId(@Query("vpn-id") String vpnId);

    @DELETE("/api/aai-network/v14/ext-aai-networks/ext-aai-network/{aai-id}")
    Call<ResponseBody> deleteExtNetwork(@Path("aai-id") String aaiId,@Query("resource-version") String resourceVersion);

    @PUT("/api/aai-query/v19?format=resource")
    Call<ResponseBody> querynNetworkResourceList(@Body RequestBody body);

    @GET("/api/aai-business/v14/customers/customer/{global-customer-id}/service-subscriptions/service-subscription/{service-type}/service-instances/service-instance/{service-instance-id}")
    Call<ResponseBody> getServiceInstancesForEdge(@Path("global-customer-id") String globalCustomerId,@Path("service-type") String serviceType,
                                                  @Path("service-instance-id") String serviceinstanceid);
    @GET("/api/aai-network/v14/connectivities/connectivity")
    Call<ResponseBody> getConnectivityInformation(@Query("connectivity-id") String connectivityId);

    @GET("/api/aai-network/v14/pnfs/pnf/{pnfName}/p-interfaces/p-interface/{tp-id}")
    Call<ResponseBody> getTerminationPoint(@Path("pnfName") String pnfName,@Path("tp-id") String tpId);

    @GET("/api/aai-business/v16/customers/customer/{global-customer-id}/service-subscriptions/service-subscription/{service-type}/service-instances/service-instance/{service-instance-id}/allotted-resources/allotted-resource/{allotted-resource-id}")
    Call<ResponseBody> getAllotedResourceFor5G(@Path("global-customer-id") String globalCustomerId,@Path("service-type") String serviceType,
                                               @Path("service-instance-id") String serviceinstanceid,@Path("allotted-resource-id") String allottedResourceId);

    @GET("/api/aai-network/v14/site-resources/site-resource/{site-resource-id}")
    Call<ResponseBody> getSiteResourceInfo(@Path("site-resource-id") String siteResourceId);

    @GET("/api/aai-cloudInfrastructure/v14/complexes/complex/{complex-id}")
    Call<ResponseBody> getComplexObject(@Path("complex-id") String complexId);

    @GET("/api/aai-business/v14/customers/customer/{global-customer-id}/service-subscriptions/service-subscription/{service-type}/service-instances")
    Call<ResponseBody> getAllServiceInformation(@Path("global-customer-id") String customerId, @Path("service-type") String serviceType);

    @GET("/api/aai-business/v13/customers/customer/{global-customer-id}/service-subscriptions")
    Call<ResponseBody> getServiceSubscription(@Path("global-customer-id") String customerID);

    @GET("/aai/v19/network/generic-vnfs/generic-vnf/{vnf-id}")
    Call<ResponseBody> getVNFsDetail(@Path("vnf-id") String vnfId);

    @GET("/aai/v19/network/unis/uni/{uni-id}")
    Call<ResponseBody> getUNIInfo(@Path("uni-id") String uniId);
}
