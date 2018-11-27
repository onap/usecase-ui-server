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
package org.onap.usecaseui.server.service.lcm.domain.aai;

import org.onap.usecaseui.server.bean.sotn.PinterfaceRsp;
import org.onap.usecaseui.server.service.lcm.domain.aai.bean.AAICustomerRsp;
import org.onap.usecaseui.server.service.lcm.domain.aai.bean.SDNCControllerRsp;
import org.onap.usecaseui.server.service.lcm.domain.aai.bean.ServiceInstanceRsp;
import org.onap.usecaseui.server.service.lcm.domain.aai.bean.ServiceSubscriptionRsp;
import org.onap.usecaseui.server.service.lcm.domain.aai.bean.VimInfoRsp;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AAIService {

    @Headers({
            "X-TransactionId: 7777",
            "X-FromAppId: uui",
            "Authorization: Basic QUFJOkFBSQ==",
            "Accept: application/json"
    })
//    @GET("/api/aai-business/v11/customers")
    @GET("/api/aai-business/v11/customers")
    Call<AAICustomerRsp> listCustomer();

    @Headers({
            "X-TransactionId: 7777",
            "X-FromAppId: uui",
            "Authorization: Basic QUFJOkFBSQ==",
            "Accept: application/json"
    })
//    @GET("/api/aai-business/v11/customers/customer/{global-customer-id}/service-subscriptions/service-subscription/{service-type}/service-instances")
    @GET("/api/aai-business/v13/customers/customer/{global-customer-id}/service-subscriptions/service-subscription/{service-type}/service-instances")
    Call<ResponseBody> listServiceInstances(@Path("global-customer-id") String customerId, @Path("service-type") String serviceType);

    @Headers({
            "X-TransactionId: 7777",
            "X-FromAppId: uui",
            "Authorization: Basic QUFJOkFBSQ==",
            "Accept: application/json"
    })
//    @GET("/cloud-infrastructure/cloud-regions")
    @GET("/api/aai-cloudInfrastructure/v11/cloud-regions")
    Call<VimInfoRsp> listVimInfo();

    @Headers({
            "X-TransactionId: 7777",
            "X-FromAppId: uui",
            "Authorization: Basic QUFJOkFBSQ==",
            "Accept: application/json"
    })
//    @GET("/api/aai-business/v11/customers/customer/{global-customer-id}/service-subscriptions")
    @GET("/api/aai-business/v11/customers/customer/{global-customer-id}/service-subscriptions")
    Call<ServiceSubscriptionRsp> listServiceSubscriptions(@Path("global-customer-id") String customerId);

    @Headers({
            "X-TransactionId: 7777",
            "X-FromAppId: uui",
            "Authorization: Basic QUFJOkFBSQ==",
            "Accept: application/json"
    })
    @GET("/api/aai-externalSystem/v11/esr-thirdparty-sdnc-list")
    Call<SDNCControllerRsp> listSdncControllers();
    
    @Headers({
        "X-TransactionId: 7777",
        "X-FromAppId: uui",
        "Authorization: Basic QUFJOkFBSQ==",
        "Accept: application/json"
	})
	@GET("/api/aai-business/v11/customers/customer/{customerId}/service-subscriptions/service-subscription/{service-type}/service-instances/service-instance/{serviceId}")
	Call<ResponseBody> getAAIServiceInstance(@Path("customerId") String customerId,@Path("service-type") String seviceType,@Path("serviceId") String serviceId);
    
    @Headers({
        "X-TransactionId: 7777",
        "X-FromAppId: uui",
        "Authorization: Basic QUFJOkFBSQ==",
        "Accept: application/json"
    })
	@GET("/api/aai-network/v14/network-resources")
	Call<ResponseBody> listNetWorkResources();
    
    @Headers({
        "X-TransactionId: 7777",
        "X-FromAppId: uui",
        "Authorization: Basic QUFJOkFBSQ==",
        "Accept: application/json"
    })
	@GET("/api/aai-network/v14/pnfs/pnf/{pnfName}/p-interfaces")
	Call<PinterfaceRsp> getPinterfaceByPnfName(@Path("pnfName") String pnfName);
    
    @Headers({
        "X-TransactionId: 7777",
        "X-FromAppId: uui",
        "Authorization: Basic QUFJOkFBSQ==",
        "Accept: application/json"
    })
	@GET("/api/aai-network/v14/logical-links")
	Call<ResponseBody> getLogicalLinks();
    
    @Headers({
    	"X-TransactionId: 7777",
    	"X-FromAppId: uui",
    	"Authorization: Basic QUFJOkFBSQ==",
    	"Accept: application/json"
    })
    @GET("/api/aai-network/v14/logical-links/logical-link/{link-name}")
    Call<ResponseBody> getSpecificLogicalLink(@Path("link-name") String linkName);
    
    @Headers({
        "X-TransactionId: 7777",
        "X-FromAppId: uui",
        "Authorization: Basic QUFJOkFBSQ==",
        "Accept: application/json"
    })
    @PUT("/api/aai-network/v14/network-resources/network-resource/{networkId}")
    Call<ResponseBody> createTopoNetwork(@Body RequestBody body,@Path("networkId") String networkId);
    
    @Headers({
    	"X-TransactionId: 7777",
    	"X-FromAppId: uui",
    	"Authorization: Basic QUFJOkFBSQ==",
    	"Accept: application/json"
    })
    @PUT("/api/aai-network/v14/ext-aai-networks/ext-aai-network/{aai-id}")
    Call<ResponseBody> createHostUrl(@Body RequestBody body,@Path("aai-id") String aaiId);
    
    @Headers({
        "X-TransactionId: 7777",
        "X-FromAppId: uui",
        "Authorization: Basic QUFJOkFBSQ==",
        "Accept: application/json"
    })
	@GET("/api/aai-network/v14/ext-aai-networks/ext-aai-network/{aai-id}")
	Call<ResponseBody> getExtAaiId(@Path("aai-id") String aaiId);
    
    @Headers({
        "X-TransactionId: 7777",
        "X-FromAppId: uui",
        "Authorization: Basic QUFJOkFBSQ==",
        "Accept: application/json"
    })
	@GET("/api/aai-network/v14/ext-aai-networks/ext-aai-network/{aai-id}/esr-system-info")
	Call<ResponseBody> getHostUrl(@Path("aai-id") String aaiId);
    
    @Headers({
        "X-TransactionId: 7777",
        "X-FromAppId: uui",
        "Authorization: Basic QUFJOkFBSQ==",
        "Accept: application/json"
    })
    @PUT("/api/aai-network/v14/pnfs/pnf/{pnfName}/p-interfaces/p-interface/{tp-id}")
    Call<ResponseBody> createTerminationPoint(@Body RequestBody body,@Path("pnfName") String pnfName,@Path("tp-id") String tpId);
    
    @Headers({
        "X-TransactionId: 7777",
        "X-FromAppId: uui",
        "Authorization: Basic QUFJOkFBSQ==",
        "Accept: application/json"
    })
    @PUT("/api/aai-network/v14/pnfs/pnf/{pnfname}")
    Call<ResponseBody> createPnf(@Body RequestBody body,@Path("pnfname") String pnfname);
    
    @Headers({
    	"X-TransactionId: 7777",
    	"X-FromAppId: uui",
    	"Authorization: Basic QUFJOkFBSQ==",
    	"Accept: application/json"
    })
    @PUT("/api/aai-network/v14/logical-links/logical-link/{linkName}")
    Call<ResponseBody> createLink(@Body RequestBody body,@Path("linkName") String linkName);
    
    @Headers({
        "X-TransactionId: 7777",
        "X-FromAppId: uui",
        "Authorization: Basic QUFJOkFBSQ==",
        "Accept: application/json"
    })
    @DELETE("/api/aai-network/v14/logical-links/logical-link/{linkName}")
    Call<ResponseBody> deleteLink(@Path("linkName") String linkName,@Query("resource-version") String resourceVersion);
    
    @Headers({
        "X-TransactionId: 7777",
        "X-FromAppId: uui",
        "Authorization: Basic QUFJOkFBSQ==",
        "Accept: application/json"
    })
    @GET("/api/aai-business/v14/customers/customer/{global-customer-id}/service-subscriptions/service-subscription/{service-type}/service-instances")
    Call<ResponseBody> getServiceInstances(@Path("global-customer-id") String customerId,@Path("service-type") String serviceType);
    
    @Headers({
        "X-TransactionId: 7777",
        "X-FromAppId: uui",
        "Authorization: Basic QUFJOkFBSQ==",
        "Accept: application/json"
    })
    @GET("/api/aai-business/v14/customers/customer/{global-customer-id}/service-subscriptions/service-subscription/{service-type}/service-instances")
    Call<ResponseBody> serviceInstaneInfo(@Path("global-customer-id") String globalCustomerId,@Path("service-type") String serviceType,@Query("service-instance-id") String serviceInstanceId);
    
    @Headers({
    	"X-TransactionId: 7777",
    	"X-FromAppId: uui",
    	"Authorization: Basic QUFJOkFBSQ==",
    	"Accept: application/json"
    })
    @GET("/api/aai-business/v14/customers/customer/{global-customer-id}/service-subscriptions/service-subscription/{service-type}/service-instances/service-instance/{service-instance-id}/allotted-resources")
    Call<ResponseBody> getAllottedResources(@Path("global-customer-id") String globalCustomerId,@Path("service-type") String serviceType,@Path("service-instance-id") String serviceInstanceId);
    
    @Headers({
    	"X-TransactionId: 7777",
    	"X-FromAppId: uui",
    	"Authorization: Basic QUFJOkFBSQ==",
    	"Accept: application/json"
    })
    @GET("/api/aai-network/v14/pnfs/pnf/{pnfName}")
    Call<ResponseBody> getPnfInfo(@Path("pnfName") String pnfName);
    
    @Headers({
    	"X-TransactionId: 7777",
    	"X-FromAppId: uui",
    	"Authorization: Basic QUFJOkFBSQ==",
    	"Accept: application/json"
    })
    @GET("/api/aai-network/v14/connectivities")
    Call<ResponseBody> getConnectivityInfo(@Query("connectivity-id") String connectivityId);
    
    @Headers({
    	"X-TransactionId: 7777",
    	"X-FromAppId: uui",
    	"Authorization: Basic QUFJOkFBSQ==",
    	"Accept: application/json"
    })
    @GET("/api/aai-network/v14/vpn-bindings")
    Call<ResponseBody> getPinterfaceByVpnId(@Query("vpn-id") String vpnId);
    
    @Headers({
    	"X-TransactionId: 7777",
    	"X-FromAppId: uui",
    	"Authorization: Basic QUFJOkFBSQ==",
    	"Accept: application/json"
    })
    @DELETE("/api/aai-network/v14/ext-aai-networks/ext-aai-network/{aai-id}")
    Call<ResponseBody> deleteExtNetwork(@Path("aai-id") String aaiId,@Query("resource-version") String resourceVersion);
}
