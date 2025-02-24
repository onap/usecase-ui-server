/**
 * Copyright 2025 Deutsche Telekom.
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
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IntentAaiClient {
    @GET("business/customers/customer/IBNCustomer/service-subscriptions/service-subscription/IBN/service-instances/service-instance/{resource-service-id}?depth=all")
    Call<JSONObject> getInstanceNetworkInfo(@Path("resource-service-id") String resourceServiceId);

    @GET("network/network-policies/network-policy/{networkPolicyId}?depth=all")
    Call<JSONObject> getInstanceNetworkPolicyInfo(@Path("networkPolicyId") String networkPolicyId);

    @GET("business/customers/customer/IBNCustomer/service-subscriptions/service-subscription/IBN/service-instances/service-instance/{resource-service-id}/metadata")
    Call<JSONObject> getInstanceBandwidth(@Path("resource-service-id") String resourceServiceId);

    @GET("business/customers/customer/IBNCustomer/service-subscriptions/service-subscription/IBN/service-instances/service-instance/{resource-service-id}")
    Call<JSONObject> getInstanceInfo(@Path("resource-service-id") String resourceServiceId);

    @PUT("business/customers/customer/{globalCustomerId}")
    Call<Void> addCustomer(@Path("globalCustomerId") String globalCustomerId,@Body RequestBody body);

    @GET("business/customers/customer/{globalCustomerId}")
    Call<JSONObject> queryCustomer(@Path("globalCustomerId") String globalCustomerId);

    @PUT("business/customers/customer/{globalCustomerId}/service-subscriptions/service-subscription/{serviceType}")
    Call<Void> addSubscription(@Path("globalCustomerId") String globalCustomerId, @Path("serviceType") String serviceType,@Body RequestBody body);

    @GET("business/customers/customer/{globalCustomerId}/service-subscriptions/service-subscription/{serviceType}")
    Call<JSONObject> querySubscription(@Path("globalCustomerId") String globalCustomerId, @Path("serviceType") String serviceType);

    @PUT("business/customers/customer/{globalCustomerId}/service-subscriptions/service-subscription/{serviceType}/service-instances/service-instance/{serviceInstanceId}")
    Call<Void> saveServiceInstance(@Path("globalCustomerId") String globalCustomerId, @Path("serviceType") String serviceType, @Path("serviceInstanceId") String serviceInstanceId, @Body RequestBody body);

    @GET("business/customers/customer/{globalCustomerId}/service-subscriptions/service-subscription/{serviceType}/service-instances/service-instance/{serviceInstanceId}")
    Call<JSONObject> queryServiceInstance(@Path("globalCustomerId") String globalCustomerId, @Path("serviceType") String serviceType, @Path("serviceInstanceId") String serviceInstanceId);

    @DELETE("business/customers/customer/{globalCustomerId}/service-subscriptions/service-subscription/{serviceType}/service-instances/service-instance/{serviceInstanceId}")
    Call<Void> deleteServiceInstance(@Path("globalCustomerId") String globalCustomerId, @Path("serviceType") String serviceType, @Path("serviceInstanceId") String serviceInstanceId, @Query("resource-version") String resourceVersion);

    @GET("network/network-routes")
    Call<JSONObject> queryNetworkRoute();
}
