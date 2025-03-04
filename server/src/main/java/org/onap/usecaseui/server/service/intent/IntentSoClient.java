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
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IntentSoClient {

    @POST("/so/infra/serviceIntent/v1/create")
    Call<JSONObject> createIntentInstance(@Body RequestBody body);

    @GET("/so/infra/e2eServiceInstances/v3/{serviceId}/operations/{operationId}")
    Call<JSONObject> queryOperationProgress(@Path("serviceId") String serviceId, @Path("operationId") String operationId);

    @HTTP(method="DELETE", path="/so/infra/serviceIntent/v1/delete", hasBody = true)
    Call<JSONObject> deleteIntentInstance(@Body RequestBody body);
}
