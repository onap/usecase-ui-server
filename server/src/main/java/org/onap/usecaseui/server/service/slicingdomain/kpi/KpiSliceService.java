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
package org.onap.usecaseui.server.service.slicingdomain.kpi;


import org.onap.usecaseui.server.service.slicingdomain.kpi.bean.KpiTotalBandwidth;
import org.onap.usecaseui.server.service.slicingdomain.kpi.bean.KpiTotalTraffic;
import org.onap.usecaseui.server.service.slicingdomain.kpi.bean.KpiUserNumber;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface KpiSliceService {

    @Headers({"Authorization: Basic SW5mcmFQb3J0YWxDbGllbnQ6cGFzc3dvcmQxJA==", "Accept: application/json"})
    @POST("/api/datalake/v1/exposure/userNumber")
    Call<KpiUserNumber> listUserNumber(@Body RequestBody body);

    @Headers({"Authorization: Basic SW5mcmFQb3J0YWxDbGllbnQ6cGFzc3dvcmQxJA==", "Accept: application/json"})
    @POST("/api/datalake/v1/exposure/totalBandwidth")
    Call<KpiTotalBandwidth> listTotalBandwidth(@Body RequestBody body);

    @Headers({"Authorization: Basic SW5mcmFQb3J0YWxDbGllbnQ6cGFzc3dvcmQxJA==", "Accept: application/json"})
    @POST("/api/datalake/v1/exposure/totalTraffic")
    Call<KpiTotalTraffic> listTotalTraffic(@Body RequestBody body);

}
