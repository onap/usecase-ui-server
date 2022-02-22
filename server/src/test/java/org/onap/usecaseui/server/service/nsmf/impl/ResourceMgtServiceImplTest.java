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
package org.onap.usecaseui.server.service.nsmf.impl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.onap.usecaseui.server.util.CallStub.emptyBodyCall;
import static org.onap.usecaseui.server.util.CallStub.failedCall;
import static org.onap.usecaseui.server.util.CallStub.successfulCall;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.onap.usecaseui.server.bean.nsmf.resource.NsiInfo;
import org.onap.usecaseui.server.bean.nsmf.resource.SlicingBusinessInfo;
import org.onap.usecaseui.server.bean.nsmf.resource.SlicingBusinessList;
import org.onap.usecaseui.server.constant.nsmf.NsmfParamConstant;
import org.onap.usecaseui.server.service.lcm.CustomerService;
import org.onap.usecaseui.server.service.lcm.domain.aai.AAIService;
import org.onap.usecaseui.server.service.lcm.domain.aai.bean.AAIServiceSubscription;
import org.onap.usecaseui.server.service.lcm.domain.aai.bean.ServiceSubscriptionRsp;
import org.onap.usecaseui.server.service.lcm.impl.DefaultCustomerService;
import org.onap.usecaseui.server.service.slicingdomain.aai.AAISliceService;
import org.onap.usecaseui.server.service.slicingdomain.aai.bean.connection.NetworkInfo;
import org.onap.usecaseui.server.service.slicingdomain.so.SOSliceService;
import org.onap.usecaseui.server.service.slicingdomain.so.bean.ActivateService;
import org.onap.usecaseui.server.service.slicingdomain.so.bean.SOOperation;
import retrofit2.Call;
import retrofit2.Response;

public class ResourceMgtServiceImplTest {

    ResourceMgtServiceImpl resourceMgtService = null;
    SOSliceService soSliceService;
    AAISliceService aaiSliceService;

    @Before
    public void before() throws Exception {
        aaiSliceService = mock(AAISliceService.class);
        soSliceService = mock(SOSliceService.class);
        resourceMgtService = new ResourceMgtServiceImpl(aaiSliceService, soSliceService);
    }

    @Test
    public void itCanQuerySlicingBusiness() {
        JSONObject object = new JSONObject();

        when(aaiSliceService
            .listService(NsmfParamConstant.CUSTOM_5G, NsmfParamConstant.SERVICE_TYPE_5G))
            .thenReturn(successfulCall(object));
        resourceMgtService.querySlicingBusiness(1, 100);
    }

    @Test
    public void querySlicingBusinessWithThrowsException() {
        JSONObject object = new JSONObject();

        when(aaiSliceService
            .listService(NsmfParamConstant.CUSTOM_5G, NsmfParamConstant.SERVICE_TYPE_5G))
            .thenReturn(failedCall("aai is not exist!"));
        resourceMgtService.querySlicingBusiness(1, 100);
    }

    @Test
    public void emptyResponseWhenQuerySlicingBusiness() {
        Call<JSONObject> call = emptyBodyCall();
        when(aaiSliceService
            .listService(NsmfParamConstant.CUSTOM_5G, NsmfParamConstant.SERVICE_TYPE_5G)).thenReturn(call);
        resourceMgtService.querySlicingBusiness(1, 100);
    }

    @Test
    public void itCanAddBusinessProgress() {
        SOOperation soOperation = new SOOperation();
        SlicingBusinessList slicingBusinessList = new SlicingBusinessList();
        List<SlicingBusinessInfo> slicingBusinessInfoList = new ArrayList<>();
        SlicingBusinessInfo slicingBusinessInfo = new SlicingBusinessInfo();
        slicingBusinessInfoList.add(slicingBusinessInfo);
        slicingBusinessList.setSlicingBusinessInfoList(slicingBusinessInfoList);
        String businessId = "test001";
        String operationId = "test001";
        when(soSliceService.queryOperationProgress(businessId, operationId))
            .thenReturn(successfulCall(soOperation));
        resourceMgtService.addBusinessProgress(slicingBusinessList);

        SlicingBusinessList slicingBusinessListNull = new SlicingBusinessList();
        resourceMgtService.addBusinessProgress(slicingBusinessListNull);
    }

    @Test
    public void itCanQuerySlicingBusinessByStatus() {
        JSONObject object = new JSONObject();

        when(aaiSliceService
            .listServiceByStatus(NsmfParamConstant.CUSTOM_5G, NsmfParamConstant.SERVICE_TYPE_5G, "activated"))
            .thenReturn(successfulCall(object));
        resourceMgtService.querySlicingBusinessByStatus("activated", 1, 100);
    }

    @Test
    public void emptyResponseWhenQuerySlicingBusinessByStatus() {
        Call<JSONObject> call = emptyBodyCall();
        when(aaiSliceService
            .listServiceByStatus(NsmfParamConstant.CUSTOM_5G, NsmfParamConstant.SERVICE_TYPE_5G, "activated")).thenReturn(call);
        resourceMgtService.querySlicingBusinessByStatus("activated", 1, 100);
    }

    @Test
    public void querySlicingBusinessByStatusWithThrowsException() {
        when(aaiSliceService
            .listServiceByStatus(NsmfParamConstant.CUSTOM_5G, NsmfParamConstant.SERVICE_TYPE_5G, "activated"))
            .thenReturn(failedCall("aai is not exist!"));
        resourceMgtService.querySlicingBusinessByStatus("activated", 1, 100);
    }

    @Test
    public void itCanQuerySlicingBusinessDetails() {
        JSONObject object = new JSONObject();
        String businessId = "23er-56ty-4567-rgdf5";
        when(aaiSliceService
            .listServiceById(NsmfParamConstant.CUSTOM_5G, NsmfParamConstant.SERVICE_TYPE_5G, businessId))
            .thenReturn(successfulCall(object));
        NetworkInfo networkInfo = new NetworkInfo();
        when(aaiSliceService
            .getServiceNetworkInstance(businessId))
            .thenReturn(successfulCall(networkInfo));

        resourceMgtService.querySlicingBusinessDetails(businessId);
    }

    @Test
    public void querySlicingBusinessDetailsWithThrowsException() {

        String businessId = "23er-56ty-4567-rgdf5";
        when(aaiSliceService
            .listServiceById(NsmfParamConstant.CUSTOM_5G, NsmfParamConstant.SERVICE_TYPE_5G, businessId))
            .thenReturn(failedCall("aai is not exist!"));
        resourceMgtService.querySlicingBusinessDetails(businessId);
    }

    @Test
    public void emptyResponseWhenQuerySlicingBusinessDetails() {
        String businessId = "23er-56ty-4567-rgdf5";
        Call<JSONObject> call = emptyBodyCall();
        when(aaiSliceService
            .listServiceById(NsmfParamConstant.CUSTOM_5G, NsmfParamConstant.SERVICE_TYPE_5G, businessId)).thenReturn(call);
        resourceMgtService.querySlicingBusinessDetails(businessId);
    }

    @Test
    public void itCanQueryNsiInstances() {
        JSONObject object = new JSONObject();

        when(aaiSliceService
            .listServiceNSI(NsmfParamConstant.CUSTOM_5G, NsmfParamConstant.SERVICE_TYPE_5G))
            .thenReturn(successfulCall(object));
        resourceMgtService.queryNsiInstances(1, 100);
    }

    @Test
    public void queryNsiInstancesWithThrowsException() {

        when(aaiSliceService
            .listServiceNSI(NsmfParamConstant.CUSTOM_5G, NsmfParamConstant.SERVICE_TYPE_5G))
            .thenReturn(failedCall("aai is not exist!"));
        resourceMgtService.queryNsiInstances(1, 100);
    }

    @Test
    public void emptyResponseWhenQueryNsiInstances() {
        Call<JSONObject> call = emptyBodyCall();
        when(aaiSliceService
            .listServiceNSI(NsmfParamConstant.CUSTOM_5G, NsmfParamConstant.SERVICE_TYPE_5G)).thenReturn(call);
        resourceMgtService.queryNsiInstances(1, 100);
    }

    @Test
    public void itCanQueryNsiInstancesByStatus() {
        JSONObject object = new JSONObject();

        when(aaiSliceService
            .listServiceNSIByStatus(NsmfParamConstant.CUSTOM_5G, NsmfParamConstant.SERVICE_TYPE_5G, "activated"))
            .thenReturn(successfulCall(object));
        resourceMgtService.queryNsiInstancesByStatus("activated", 1, 100);
    }

    @Test
    public void queryNsiInstancesByStatusWithThrowsException() {

        when(aaiSliceService
            .listServiceNSIByStatus(NsmfParamConstant.CUSTOM_5G, NsmfParamConstant.SERVICE_TYPE_5G, "activated"))
            .thenReturn(failedCall("aai is not exist!"));
        resourceMgtService.queryNsiInstancesByStatus("activated", 1, 100);
    }

    @Test
    public void emptyResponseWhenQueryNsiInstancesByStatus() {
        Call<JSONObject> call = emptyBodyCall();
        when(aaiSliceService
            .listServiceNSIByStatus(NsmfParamConstant.CUSTOM_5G, NsmfParamConstant.SERVICE_TYPE_5G, "activated")).thenReturn(call);
        resourceMgtService.queryNsiInstancesByStatus("activated", 1, 100);
    }

    @Test
    public void itCanQueryNsiDetails() {
        JSONObject object = new JSONObject();
        String serviceInstanceId = "23er-56ty-4567-rgdf5";
        when(aaiSliceService
            .querySerAndSubInsByNSI(NsmfParamConstant.CUSTOM_5G, NsmfParamConstant.SERVICE_TYPE_5G, serviceInstanceId))
            .thenReturn(successfulCall(object));
        resourceMgtService.queryNsiDetails(serviceInstanceId);
    }

    @Test
    public void queryNsiDetailsWithThrowsException() {

        String serviceInstanceId = "23er-56ty-4567-rgdf5";
        when(aaiSliceService
            .querySerAndSubInsByNSI(NsmfParamConstant.CUSTOM_5G, NsmfParamConstant.SERVICE_TYPE_5G, serviceInstanceId))
            .thenReturn(failedCall("aai is not exist!"));
        resourceMgtService.queryNsiDetails(serviceInstanceId);
    }

    @Test
    public void emptyResponseWhenQueryNsiDetails() {
        Call<JSONObject> call = emptyBodyCall();
        String serviceInstanceId = "23er-56ty-4567-rgdf5";
        when(aaiSliceService
            .querySerAndSubInsByNSI(NsmfParamConstant.CUSTOM_5G, NsmfParamConstant.SERVICE_TYPE_5G, serviceInstanceId)).thenReturn(call);
        resourceMgtService.queryNsiDetails(serviceInstanceId);
    }

    @Test
    public void itCanQueryNsiRelatedNssiInfo() {
        JSONObject object = new JSONObject();
        String nsiId = "23er-56ty-4567-rgdf5";
        when(aaiSliceService
            .querySerAndSubInsByNSI(NsmfParamConstant.CUSTOM_5G, NsmfParamConstant.SERVICE_TYPE_5G, nsiId))
            .thenReturn(successfulCall(object));
        resourceMgtService.queryNsiRelatedNssiInfo(nsiId);
    }

    @Test
    public void queryNsiRelatedNssiInfoWithThrowsException() {

        String nsiId = "23er-56ty-4567-rgdf5";
        when(aaiSliceService
            .querySerAndSubInsByNSI(NsmfParamConstant.CUSTOM_5G, NsmfParamConstant.SERVICE_TYPE_5G, nsiId))
            .thenReturn(failedCall("aai is not exist!"));
        resourceMgtService.queryNsiRelatedNssiInfo(nsiId);
    }

    @Test
    public void emptyResponseWhenQuueryNsiRelatedNssiInfo() {
        Call<JSONObject> call = emptyBodyCall();
        String nsiId = "23er-56ty-4567-rgdf5";
        when(aaiSliceService
            .querySerAndSubInsByNSI(NsmfParamConstant.CUSTOM_5G, NsmfParamConstant.SERVICE_TYPE_5G, nsiId)).thenReturn(call);
        resourceMgtService.queryNsiRelatedNssiInfo(nsiId);
    }

    @Test
    public void itCanQueryNssiInstances() {
        JSONObject object = new JSONObject();

        when(aaiSliceService
            .listServiceNSSI(NsmfParamConstant.CUSTOM_5G, NsmfParamConstant.SERVICE_TYPE_5G))
            .thenReturn(successfulCall(object));
        resourceMgtService.queryNssiInstances(1, 100);
    }

    @Test
    public void queryNssiInstancesWithThrowsException() {

        when(aaiSliceService
            .listServiceNSSI(NsmfParamConstant.CUSTOM_5G, NsmfParamConstant.SERVICE_TYPE_5G))
            .thenReturn(failedCall("aai is not exist!"));
        resourceMgtService.queryNssiInstances(1, 100);
    }

    @Test
    public void emptyResponseWhenQueryNssiInstances() {
        Call<JSONObject> call = emptyBodyCall();
        when(aaiSliceService
            .listServiceNSSI(NsmfParamConstant.CUSTOM_5G, NsmfParamConstant.SERVICE_TYPE_5G)).thenReturn(call);
        resourceMgtService.queryNssiInstances(1, 100);
    }

    @Test
    public void itCanQueryNssiInstancesByStatus() {
        JSONObject object = new JSONObject();

        when(aaiSliceService
            .listServiceNSSIByStatus(NsmfParamConstant.CUSTOM_5G, NsmfParamConstant.SERVICE_TYPE_5G, "activated"))
            .thenReturn(successfulCall(object));
        resourceMgtService.queryNssiInstancesByStatus("activated", 1, 100);
    }

    @Test
    public void queryNssiInstancesByStatusWithThrowsException() {

        when(aaiSliceService
            .listServiceNSSIByStatus(NsmfParamConstant.CUSTOM_5G, NsmfParamConstant.SERVICE_TYPE_5G, "activated"))
            .thenReturn(failedCall("aai is not exist!"));
        resourceMgtService.queryNssiInstancesByStatus("activated", 1, 100);
    }

    @Test
    public void emptyResponseWhenQueryNssiInstancesByStatus() {
        Call<JSONObject> call = emptyBodyCall();
        when(aaiSliceService
            .listServiceNSSIByStatus(NsmfParamConstant.CUSTOM_5G, NsmfParamConstant.SERVICE_TYPE_5G, "activated")).thenReturn(call);
        resourceMgtService.queryNssiInstancesByStatus("activated", 1, 100);
    }

    @Test
    public void itCanQueryNssiInstancesByEnvironment() {
        JSONObject object = new JSONObject();
        String environmentContext = "cn";
        when(aaiSliceService
            .listServiceNSSIByEnv(NsmfParamConstant.CUSTOM_5G,
                NsmfParamConstant.SERVICE_TYPE_5G, environmentContext))
            .thenReturn(successfulCall(object));
        resourceMgtService.queryNssiInstancesByEnvironment(environmentContext, 1, 100);
    }

    @Test
    public void queryNssiInstancesByEnvironmentWithThrowsException() {
        String environmentContext = "cn";
        when(aaiSliceService
            .listServiceNSSIByEnv(NsmfParamConstant.CUSTOM_5G,
                NsmfParamConstant.SERVICE_TYPE_5G, environmentContext))
            .thenReturn(failedCall("aai is not exist!"));
        resourceMgtService.queryNssiInstancesByEnvironment(environmentContext, 1, 100);
    }

    @Test
    public void emptyResponseWhenQueryNssiInstancesByEnvironment() {
        String environmentContext = "cn";
        Call<JSONObject> call = emptyBodyCall();
        when(aaiSliceService
            .listServiceNSSIByEnv(NsmfParamConstant.CUSTOM_5G,
                NsmfParamConstant.SERVICE_TYPE_5G, environmentContext)).thenReturn(call);
        resourceMgtService.queryNssiInstancesByEnvironment(environmentContext, 1, 100);
    }

    @Test
    public void itCanQueryNssiDetails() {
        JSONObject object = new JSONObject();
        String nssiId = "23er-56ty-4567-rgdf5";
        when(aaiSliceService
            .queryNSIByNSSI(NsmfParamConstant.CUSTOM_5G, NsmfParamConstant.SERVICE_TYPE_5G, nssiId))
            .thenReturn(successfulCall(object));
        resourceMgtService.queryNssiDetails(nssiId);
    }

    @Test
    public void queryNssiDetailsWithThrowsException() {

        String nssiId = "23er-56ty-4567-rgdf5";
        when(aaiSliceService
            .queryNSIByNSSI(NsmfParamConstant.CUSTOM_5G, NsmfParamConstant.SERVICE_TYPE_5G, nssiId))
            .thenReturn(failedCall("aai is not exist!"));
        resourceMgtService.queryNssiDetails(nssiId);
    }

    @Test
    public void emptyResponseWhenQueryNssiDetails() {
        String nssiId = "23er-56ty-4567-rgdf5";
        Call<JSONObject> call = emptyBodyCall();
        when(aaiSliceService
            .queryNSIByNSSI(NsmfParamConstant.CUSTOM_5G, NsmfParamConstant.SERVICE_TYPE_5G, nssiId)).thenReturn(call);
        resourceMgtService.queryNssiDetails(nssiId);
    }

    @Test
    public void itCanActivateSlicingService() {
        String serviceId = "23er-56ty-4567-rgdf5";
        String jsonstr = "testJson";
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonstr.toString());

        ActivateService activateService = new ActivateService();
        when(soSliceService.activeService(serviceId, requestBody))
            .thenReturn(successfulCall(activateService));
        resourceMgtService.activateSlicingService(serviceId);
    }

    @Test
    public void activateSlicingServiceWithThrowsException() {

        String serviceId = "23er-56ty-4567-rgdf5";
        String jsonstr = "testJson";
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonstr.toString());
        when(soSliceService.activeService(serviceId, requestBody))
            .thenReturn(failedCall("so is not exist!"));
        resourceMgtService.activateSlicingService(serviceId);
    }

    @Test
    public void emptyResponseWhenActivateSlicingService() {
        String serviceId = "23er-56ty-4567-rgdf5";
        String jsonstr = "testJson";
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonstr.toString());
        Call<ActivateService> call = emptyBodyCall();

        when(soSliceService.activeService(serviceId, requestBody)).thenReturn(call);
        resourceMgtService.activateSlicingService(serviceId);
    }

    @Test
    public void itCanDeactivateSlicingService() {
        String serviceId = "23er-56ty-4567-rgdf5";

        ActivateService activateService = new ActivateService();
        RequestBody requestBody = null;
        when(soSliceService.deactiveService(serviceId, requestBody))
            .thenReturn(successfulCall(activateService));
        resourceMgtService.deactivateSlicingService(serviceId);
    }

    @Test
    public void deactivateSlicingServiceWithThrowsException() {

        String serviceId = "23er-56ty-4567-rgdf5";
        RequestBody requestBody = null;
        when(soSliceService.deactiveService(serviceId, requestBody))
            .thenReturn(failedCall("so is not exist!"));
        resourceMgtService.deactivateSlicingService(serviceId);
    }

    @Test
    public void emptyResponseWhenDeactivateSlicingService() {
        String serviceId = "23er-56ty-4567-rgdf5";
        String jsonstr = "testJson";
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonstr.toString());
        Call<ActivateService> call = emptyBodyCall();

        when(soSliceService.deactiveService(serviceId, requestBody)).thenReturn(call);
        resourceMgtService.deactivateSlicingService(serviceId);
    }

    @Test
    public void itCanTerminateSlicingService() {
        String serviceId = "23er-56ty-4567-rgdf5";

        ActivateService activateService = new ActivateService();
        RequestBody requestBody = null;
        when(soSliceService.terminateService(serviceId, requestBody))
            .thenReturn(successfulCall(activateService));
        resourceMgtService.terminateSlicingService(serviceId);
    }

    @Test
    public void terminateSlicingServiceWithThrowsException() {

        String serviceId = "23er-56ty-4567-rgdf5";
        RequestBody requestBody = null;
        when(soSliceService.terminateService(serviceId, requestBody))
            .thenReturn(failedCall("so is not exist!"));
        resourceMgtService.terminateSlicingService(serviceId);
    }

    @Test
    public void emptyResponseWhenTerminateSlicingService() {
        String serviceId = "23er-56ty-4567-rgdf5";
        String jsonstr = "testJson";
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonstr.toString());
        Call<ActivateService> call = emptyBodyCall();

        when(soSliceService.terminateService(serviceId, requestBody)).thenReturn(call);
        resourceMgtService.terminateSlicingService(serviceId);
    }

    @Test
    public void itCanQueryOperationProgress() {
        String serviceId = "23er-56ty-4567-rgdf5";
        String operationId = "2345-5675-3456-er45";
        SOOperation soOperation = new SOOperation();

        when(soSliceService.queryOperationProgress(serviceId, operationId))
            .thenReturn(successfulCall(soOperation));
        resourceMgtService.queryOperationProgress(serviceId);
    }

    @Test
    public void queryOperationProgressWithThrowsException() {

        String serviceId = "23er-56ty-4567-rgdf5";
        String operationId = "2345-5675-3456-er45";

        when(soSliceService.queryOperationProgress(serviceId, operationId))
            .thenReturn(failedCall("so is not exist!"));
        resourceMgtService.queryOperationProgress(serviceId);
    }

    @Test
    public void emptyResponseWhenQueryOperationProgress() {
        String serviceId = "23er-56ty-4567-rgdf5";
        String operationId = "2345-5675-3456-er45";
        Call<SOOperation> call = emptyBodyCall();

        when(soSliceService.queryOperationProgress(serviceId, operationId)).thenReturn(call);
        resourceMgtService.queryOperationProgress(serviceId);
    }
}
