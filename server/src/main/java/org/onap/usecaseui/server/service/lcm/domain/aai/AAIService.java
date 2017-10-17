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

import org.onap.usecaseui.server.service.lcm.domain.aai.bean.AAICustomer;
import org.onap.usecaseui.server.service.lcm.domain.aai.bean.AAIServiceSubscription;
import org.onap.usecaseui.server.service.lcm.domain.aai.bean.ServiceInstance;
import org.onap.usecaseui.server.service.lcm.domain.aai.bean.VimInfo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

import java.util.List;

public interface AAIService {

    @Headers({
            "X-TransactionId: 7777",
            "X-FromAppId: uui",
            "Authorization: QUFJOkFBSQ==",
            "Accept: application/json"
    })
//    @GET("/api/aai-business/v11/customers")
    @GET("/aai-business/v11/customers")
    Call<List<AAICustomer>> listCustomer();

    @Headers({
            "X-TransactionId: 7777",
            "X-FromAppId: uui",
            "Authorization: QUFJOkFBSQ==",
            "Accept: application/json"
    })
//    @GET("/api/aai-business/v11/customers/customer/{global-customer-id}/service-subscriptions/service-subscription/{service-type}/service-instances")
    @GET("/aai-business/v11/customers/customer/{global-customer-id}/service-subscriptions/service-subscription/{service-type}/service-instances")
    Call<List<ServiceInstance>> listServiceInstances(@Path("global-customer-id") String customerId, @Path("service-type") String serviceType);

    @Headers({
            "X-TransactionId: 7777",
            "X-FromAppId: uui",
            "Authorization: QUFJOkFBSQ==",
            "Accept: application/json"
    })
//    @GET("/cloud-infrastructure/cloud-regions")
    @GET("/aai-cloudInfrastructure/v11/cloud-regions")
    Call<List<VimInfo>> listVimInfo();

    @Headers({
            "X-TransactionId: 7777",
            "X-FromAppId: uui",
            "Authorization: QUFJOkFBSQ==",
            "Accept: application/json"
    })
//    @GET("/api/aai-business/v11/customers/customer/{global-customer-id}/service-subscriptions")
    @GET("/aai-business/v11/customers/customer/{global-customer-id}/service-subscriptions")
    Call<List<AAIServiceSubscription>> listServiceSubscriptions(@Path("global-customer-id") String customerId);
}
