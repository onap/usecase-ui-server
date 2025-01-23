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
package org.onap.usecaseui.server.service.slicingdomain.so;

import org.onap.usecaseui.server.bean.csmf.CreateResponse;
import org.onap.usecaseui.server.service.slicingdomain.so.bean.ActivateService;
import org.onap.usecaseui.server.service.slicingdomain.so.bean.SOOperation;
import org.onap.usecaseui.server.service.slicingdomain.so.bean.SOTask;

import com.alibaba.fastjson.JSONArray;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SOSliceService {

	@GET("/api/so-serviceInstances/v3/{serviceId}/operations/{operationId}")
	Call<SOOperation> queryOperationProgress(@Path("serviceId") String serviceId,
			@Path("operationId") String operationId);

	@POST("/api/so-serviceInstances/v3/{serviceInstanceId}/activate")
	Call<ActivateService> activeService(@Path("serviceInstanceId") String serviceInstanceId, @Body RequestBody body);

	@POST("/api/so-serviceInstances/v3/{serviceInstanceId}/deactivate")
	Call<ActivateService> deactiveService(@Path("serviceInstanceId") String serviceInstanceId, @Body RequestBody body);

	@HTTP(method = "DELETE",path = "/api/so-serviceInstances/v3/{serviceInstanceId}",hasBody = true)
	Call<ActivateService> terminateService(@Path("serviceInstanceId") String serviceInstanceId, @Body RequestBody body);

	@GET("/api/so-orchestrationTasks/v4")
	Call<JSONArray> listTask();

	@GET("/api/so-orchestrationTasks/v4")
	Call<JSONArray> listTaskByStage(@Query("status") String status );

	@GET("/api/so-orchestrationTasks/v4/{taskId}")
	Call<SOTask> getTaskById(@Path("taskId") String taskId);

	@GET("/api/so-orchestrationTasks/v4/{taskId}")
	Call<SOTask> getTaskByIdD(@Path("taskId") String taskId);

	@PUT("/api/so-orchestrationTasks/v4/{taskId}")
	Call<ResponseBody> updateService(@Path("taskId") String taskId, @Body RequestBody body);

	@POST("/api/so-orchestrationTasks/v4/{taskId}/commit")
	Call<ResponseBody> commitTask(@Path("taskId") String taskId);

	@POST("/api/so-serviceInstances/v3")
	Call<CreateResponse> submitOrders(@Body RequestBody body);
}
