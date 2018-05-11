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

import org.onap.usecaseui.server.service.lcm.domain.aai.bean.*;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

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
    @GET("/api/aai-business/v11/customers/customer/{global-customer-id}/service-subscriptions/service-subscription/{service-type}/service-instances")
    Call<ServiceInstanceRsp> listServiceInstances(@Path("global-customer-id") String customerId, @Path("service-type") String serviceType);

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
}
