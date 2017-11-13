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
package org.onap.usecaseui.server.service.lcm.domain.vfc;

import org.onap.usecaseui.server.service.lcm.domain.vfc.beans.Csar;
import org.onap.usecaseui.server.service.lcm.domain.vfc.beans.DistributionResult;
import org.onap.usecaseui.server.service.lcm.domain.vfc.beans.Job;
import org.onap.usecaseui.server.service.lcm.domain.vfc.beans.JobStatus;
import retrofit2.Call;
import retrofit2.http.*;

public interface VfcService {

    @POST("/api/catalog/v1/nspackages")
    Call<DistributionResult> distributeNsPackage(@Body Csar csar);

    @POST("/api/catalog/v1/vnfpackages")
    Call<Job> distributeVnfPackage(@Body Csar csar);

    @GET("/api/nslcm/v1/jobs/{jobId}")
    Call<JobStatus> getJobStatus(@Path("jobId") String jobId, @Query("responseId") String responseId);

    @DELETE("/api/catalog/v1/nspackages/{csarId}")
    Call<DistributionResult> deleteNsPackage(@Path("csarId") String csarId);

    @DELETE("/api/catalog/v1/vnfpackages/{csarId}")
    Call<DistributionResult> deleteVnfPackage(@Path("csarId") String csarId);
}
