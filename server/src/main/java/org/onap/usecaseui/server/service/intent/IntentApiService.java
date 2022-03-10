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

//    curl -X GET -H "content-type:application/json" http://so:8080/onap/so/infra/e2eServiceInstances/v3/cll-101/operations/0d698405-9109-49f2-9939-fd02ead31660 --user 'InfraPortalClient:password1$'

    @Headers({
            "Authorization: Basic SW5mcmFQb3J0YWxDbGllbnQ6cGFzc3dvcmQxJA==",
            "Accept: application/json"
    })
    @GET("/so/infra/e2eServiceInstances/v3/{serviceId}/operations/{operationId}")
    Call<JSONObject> queryOperationProgress(@Path("serviceId") String serviceId, @Path("operationId") String operationId);

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
            "X-TransactionId: 9999",
            "X-FromAppId: MSO",
            "Authorization: Basic QUFJOkFBSQ==",
            "Accept: application/json"
    })
    @GET("/aai/v24/business/customers/customer/IBNCustomer/service-subscriptions/service-subscription/IBN/service-instances/service-instance/{resource-service-id}")
    Call<JSONObject> getInstanceInfo(@Path("resource-service-id") String resourceServiceId);

    @Headers({
            "X-TransactionId: 9999",
            "X-FromAppId: MSO",
            "Authorization: Basic QUFJOkFBSQ==",
            "Accept: application/json"
    })
    @PUT("/aai/v24/business/customers/customer/{globalCustomerId}")
    Call<Void> addCustomer(@Path("globalCustomerId") String globalCustomerId,@Body RequestBody body);

    @Headers({
            "X-TransactionId: 9999",
            "X-FromAppId: MSO",
            "Authorization: Basic QUFJOkFBSQ==",
            "Accept: application/json"
    })
    @GET("/aai/v24/business/customers/customer/{globalCustomerId}")
    Call<JSONObject> queryCustomer(@Path("globalCustomerId") String globalCustomerId);
    @Headers({
            "X-TransactionId: 9999",
            "X-FromAppId: MSO",
            "Authorization: Basic QUFJOkFBSQ==",
            "Accept: application/json"
    })
    @PUT("/aai/v24/business/customers/customer/{globalCustomerId}/service-subscriptions/service-subscription/{serviceType}")
    Call<Void> addSubscription(@Path("globalCustomerId") String globalCustomerId, @Path("serviceType") String serviceType,@Body RequestBody body);

    @Headers({
            "X-TransactionId: 9999",
            "X-FromAppId: MSO",
            "Authorization: Basic QUFJOkFBSQ==",
            "Accept: application/json"
    })
    @GET("/aai/v24/business/customers/customer/{globalCustomerId}/service-subscriptions/service-subscription/{serviceType}")
    Call<JSONObject> querySubscription(@Path("globalCustomerId") String globalCustomerId, @Path("serviceType") String serviceType);

    @Headers({
            "X-TransactionId: 9999",
            "X-FromAppId: MSO",
            "Authorization: Basic QUFJOkFBSQ==",
            "Accept: application/json"
    })
    @PUT("/aai/v24/business/customers/customer/{globalCustomerId}/service-subscriptions/service-subscription/{serviceType}/service-instances/service-instance/{serviceInstanceId}")
    Call<Void> saveServiceInstance(@Path("globalCustomerId") String globalCustomerId, @Path("serviceType") String serviceType, @Path("serviceInstanceId") String serviceInstanceId, @Body RequestBody body);


    @Headers({
            "X-TransactionId: 9999",
            "X-FromAppId: MSO",
            "Authorization: Basic QUFJOkFBSQ==",
            "Accept: application/json"
    })
    @GET("/aai/v24/business/customers/customer/{globalCustomerId}/service-subscriptions/service-subscription/{serviceType}/service-instances/service-instance/{serviceInstanceId}")
    Call<JSONObject> queryServiceInstance(@Path("globalCustomerId") String globalCustomerId, @Path("serviceType") String serviceType, @Path("serviceInstanceId") String serviceInstanceId);


    @Headers({
            "X-TransactionId: 9999",
            "X-FromAppId: MSO",
            "Authorization: Basic QUFJOkFBSQ==",
            "Accept: application/json"
    })
    @DELETE("/aai/v24/business/customers/customer/{globalCustomerId}/service-subscriptions/service-subscription/{serviceType}/service-instances/service-instance/{serviceInstanceId}?resource-version={resourceVersion}")
    Call<Void> deleteServiceInstance(@Path("globalCustomerId") String globalCustomerId, @Path("serviceType") String serviceType, @Path("serviceInstanceId") String serviceInstanceId, @Path("resourceVersion") String resourceVersion);

    @Headers({
            "Authorization: Basic SW5mcmFQb3J0YWxDbGllbnQ6cGFzc3dvcmQxJA==",
            "Accept: application/json"
    })
    @HTTP(method="DELETE", path="/so/infra/serviceIntent/v1/delete", hasBody = true)
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
