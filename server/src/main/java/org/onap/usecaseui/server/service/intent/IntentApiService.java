/*
 * Copyright (C) 2017 CTC, Inc. and others. All rights reserved.
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
package org.onap.usecaseui.server.service.intent;


import com.alibaba.fastjson.JSONObject;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface IntentApiService {

    @Headers({
            "Authorization: Basic SW5mcmFQb3J0YWxDbGllbnQ6cGFzc3dvcmQxJA==",
            "Accept: application/json"
    })
    @POST("/so/infra/serviceIntent/v1/create")
    Call<JSONObject> createIntentInstance(@Body RequestBody body);

    @Headers({
            "X-TransactionId: 9999",
            "X-FromAppId: MSO",
            "Authorization: Basic SW5mcmFQb3J0YWxDbGllbnQ6cGFzc3dvcmQxJA==",
            "Accept: application/json"
    })
    @GET("/aai/v24/business/customers/customer/IBNCustomer/service-subscriptions/service-subscription/IBN/service-instances/service-instance/{resource-service-id}?depth=all")
    Call<JSONObject> getInstanceNetworkInfo(@Path("resource-service-id") String resourceServiceId);

    @Headers({
            "X-TransactionId: 9999",
            "X-FromAppId: MSO",
            "Authorization: Basic SW5mcmFQb3J0YWxDbGllbnQ6cGFzc3dvcmQxJA==",
            "Accept: application/json"
    })
    @GET("/aai/v24/network/network-policies/network-policy/{networkPolicyId}?depth=all")
    Call<JSONObject> getInstanceNetworkPolicyInfo(@Path("networkPolicyId") String networkPolicyId);

    @Headers({
            "X-TransactionId: 9999",
            "X-FromAppId: MSO",
            "Authorization: Basic SW5mcmFQb3J0YWxDbGllbnQ6cGFzc3dvcmQxJA==",
            "Accept: application/json"
    })
    @GET("/aai/v24/business/customers/customer/IBNCustomer/service-subscriptions/service-subscription/IBN/service-instances/service-instance/{resource-service-id}/metadata")
    Call<JSONObject> getInstanceBandwidth(@Path("resource-service-id") String resourceServiceId);

    @Headers({
            "Authorization: Basic SW5mcmFQb3J0YWxDbGllbnQ6cGFzc3dvcmQxJA==",
            "Accept: application/json"
    })
    @DELETE("/so/infra/serviceIntent/v1/delete")
    Call<JSONObject> deleteIntentInstance(@Body RequestBody body);

    @Headers({
            "X-TransactionId: 9999",
            "X-FromAppId: MSO",
            "Authorization: Basic SW5mcmFQb3J0YWxDbGllbnQ6cGFzc3dvcmQxJA==",
            "Accept: application/json"
    })
    @GET("/aai/v24/network/network-routes")
    Call<JSONObject> queryNetworkRoute();

}
