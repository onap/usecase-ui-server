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
package org.onap.usecaseui.server.service.lcm.domain.sdc;

import okhttp3.ResponseBody;
import org.onap.usecaseui.server.service.lcm.domain.sdc.bean.SDCServiceTemplate;
import org.onap.usecaseui.server.service.lcm.domain.sdc.bean.Vnf;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface SDCCatalogService {

    @Headers({
            "X-ECOMP-InstanceID: 777",
            "authorization :  Basic YWFpOktwOGJKNFNYc3pNMFdYbGhhazNlSGxjc2UyZ0F3ODR2YW9HR21KdlV5MlU="
    })
    @GET("sdc/v1/catalog/services")
    Call<List<SDCServiceTemplate>> listServices(@Query("category")String category, @Query("distributionStatus") String distributionStatus);

    @Headers({
            "X-ECOMP-InstanceID: 777",
            "authorization :  Basic YWFpOktwOGJKNFNYc3pNMFdYbGhhazNlSGxjc2UyZ0F3ODR2YW9HR21KdlV5MlU="
    })
    @GET("sdc/v1/catalog/services/{uuid}/metadata")
    Call<SDCServiceTemplate> getService(@Path("uuid") String uuid);

    @Headers({
            "X-ECOMP-InstanceID: 777",
            "authorization :  Basic YWFpOktwOGJKNFNYc3pNMFdYbGhhazNlSGxjc2UyZ0F3ODR2YW9HR21KdlV5MlU="
    })
    @GET
    Call<ResponseBody> downloadCsar(@Url String fileUrl);

    @Headers({
            "X-ECOMP-InstanceID: 777",
            "authorization :  Basic YWFpOktwOGJKNFNYc3pNMFdYbGhhazNlSGxjc2UyZ0F3ODR2YW9HR21KdlV5MlU="
    })
    @GET("sdc/v1/catalog/resources")
    Call<List<Vnf>> listResources(@Query("resourceType") String resourceType, @Query("distributionStatus") String distributionStatus);
}