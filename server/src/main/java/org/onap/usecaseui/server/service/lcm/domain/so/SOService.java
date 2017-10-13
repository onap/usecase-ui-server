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
package org.onap.usecaseui.server.service.lcm.domain.so;

import okhttp3.RequestBody;
import org.onap.usecaseui.server.service.lcm.domain.so.bean.OperationProgressInformation;
import org.onap.usecaseui.server.service.lcm.domain.so.bean.ServiceOperation;
import retrofit2.Call;
import retrofit2.http.*;

public interface SOService {

    @POST("/so/e2eServiceInstances/v2")
    Call<ServiceOperation> instantiateService(@Body RequestBody body);

    @GET("/so/e2eServiceInstances/v2/{serviceId}/operations/{operationId}")
    Call<OperationProgressInformation> queryOperationProgress(@Path("serviceId") String serviceId, @Path("operationId") String operationId);

    @DELETE("/so/e2eServiceInstances/v2/{serviceId}")
    Call<ServiceOperation> terminateService(@Path("serviceId") String serviceId);
}
