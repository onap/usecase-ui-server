/*
 * Copyright (C) 2020 CMCC, Inc. and others. All rights reserved.
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
package org.onap.usecaseui.server.service.csmf.impl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.onap.usecaseui.server.util.CallStub.emptyBodyCall;
import static org.onap.usecaseui.server.util.CallStub.failedCall;
import static org.onap.usecaseui.server.util.CallStub.successfulCall;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.Resource;
import okhttp3.RequestBody;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.onap.usecaseui.server.bean.ServiceInstanceOperations;
import org.onap.usecaseui.server.bean.csmf.CreateResponse;
import org.onap.usecaseui.server.bean.csmf.OrderInfo;
import org.onap.usecaseui.server.bean.csmf.OrderList;
import org.onap.usecaseui.server.bean.csmf.SlicingOrder;
import org.onap.usecaseui.server.bean.csmf.SlicingOrderDetail;
import org.onap.usecaseui.server.constant.nsmf.NsmfParamConstant;
import org.onap.usecaseui.server.controller.IntentController;
import org.onap.usecaseui.server.service.csmf.config.SlicingProperties;
import org.onap.usecaseui.server.service.lcm.ServiceLcmService;
import org.onap.usecaseui.server.service.lcm.impl.DefaultServiceLcmService;
import org.onap.usecaseui.server.service.slicingdomain.aai.AAISliceService;
import org.onap.usecaseui.server.service.slicingdomain.so.SOSliceService;
import org.onap.usecaseui.server.service.slicingdomain.so.bean.SOOperation;
import org.powermock.api.support.membermodification.MemberModifier;
import retrofit2.Call;

public class SlicingServiceImplTest {

    SlicingServiceImpl slicingService = null;
    SOSliceService soSliceService;
    AAISliceService aaiSliceService;
    ServiceLcmService serviceLcmService;
    SlicingProperties slicingProperties;

    @Before
    public void before() throws Exception {
        aaiSliceService = mock(AAISliceService.class);
        soSliceService = mock(SOSliceService.class);
        serviceLcmService = mock(DefaultServiceLcmService.class);
        slicingProperties = mock(SlicingProperties.class);
        slicingService = new SlicingServiceImpl(serviceLcmService, aaiSliceService, soSliceService, slicingProperties);
        MemberModifier.field(SlicingServiceImpl.class, "serviceLcmService").set(slicingService , serviceLcmService);
    }

    @Test
    public void itCanCreateSlicingService() {
        SlicingOrderDetail slicingOrderDetail = new SlicingOrderDetail();

        slicingOrderDetail.setCoverageArea("sdfgdert");
        slicingOrderDetail.setExpDataRateDL(100);
        slicingOrderDetail.setExpDataRateUL(200);
        slicingOrderDetail.setLatency(20);
        slicingOrderDetail.setMaxNumberofUEs(200);
        slicingOrderDetail.setUEMobilityLevel("share");
        slicingOrderDetail.setName("csmf");
        slicingOrderDetail.setResourceSharingLevel("share");
        slicingOrderDetail.setUseInterval("20");

        SlicingOrder slicingOrder = new SlicingOrder();
        slicingOrder.setSlicing_order_info(slicingOrderDetail);

        CreateResponse createResponse = new CreateResponse();
        RequestBody requestBody = null;
        when(soSliceService
            .submitOrders(requestBody))
            .thenReturn(successfulCall(createResponse));
        slicingService.createSlicingService(slicingOrder);
    }

    @Test
    public void createSlicingServiceWithThrowsException() {

        SlicingOrder slicingOrder = new SlicingOrder();
        RequestBody requestBody = null;
        when(soSliceService
            .submitOrders(requestBody))
            .thenReturn(failedCall("so is not exist!"));
        slicingService.createSlicingService(slicingOrder);
    }

    @Test
    public void itCanQuerySlicingOrderList() {
        JSONObject object = new JSONObject();
        when(aaiSliceService
            .listOrders(NsmfParamConstant.CUSTOM_5G, NsmfParamConstant.SERVICE_TYPE_5G))
            .thenReturn(successfulCall(object));
        slicingService.querySlicingOrderList("processing", "1", "100");
    }

    @Test
    public void querySlicingOrderListWithThrowsException() {
        when(aaiSliceService
            .listOrders(NsmfParamConstant.CUSTOM_5G, NsmfParamConstant.SERVICE_TYPE_5G))
            .thenReturn(failedCall("aai is not exist!"));
        slicingService.querySlicingOrderList("processing", "1", "100");
    }

    @Test
    public void emptyResponseWhenQuerySlicingOrderList() {
        Call<JSONObject> call = emptyBodyCall();
        when(aaiSliceService
            .listOrders(NsmfParamConstant.CUSTOM_5G, NsmfParamConstant.SERVICE_TYPE_5G)).thenReturn(call);
        slicingService.querySlicingOrderList("processing", "1", "100");
    }

    @Test
    public void itCanBuildOrderList() throws ParseException {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("service-instance-id", "id001");
        jsonObject.put("service-instance-name", "name001");
        jsonObject.put("created-at", "2019-12-23 11:31:19");
        jsonObject.put("service-type", "emBB");
        jsonObject.put("environment-context", "cn");
        jsonObject.put("orchestration-status", "activate");

        List<OrderInfo> orderList = new ArrayList<>();
        JSONArray array = new JSONArray();
        array.add(jsonObject);
        slicingService.buildOrderList(orderList, array);
    }

    @Test
    public void itCanAddProgressToOrder() {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrder_creation_time("34562123");
        orderInfo.setOrder_id("sde2345rr");
        orderInfo.setOrder_status("creating");
        orderInfo.setService_snssai("sdffg34-344");
        orderInfo.setOrder_name("dfer-fgree");
        orderInfo.setOrder_index("1");
        orderInfo.setLast_operation_type("activate");
        orderInfo.setLast_operation_progress("79");
        List<OrderInfo> slicing_order_list = new ArrayList<>();
        slicing_order_list.add(orderInfo);

        OrderList orderList = new OrderList();
        orderList.setRecord_number(3);
        orderList.setSlicing_order_list(slicing_order_list);

        SOOperation soOperation = new SOOperation();
        RequestBody requestBody = null;
        String businessId = "test123";
        String operationId = "opera123";
        when(serviceLcmService.getServiceInstanceOperationById(businessId)).thenReturn(new ServiceInstanceOperations());
        when(soSliceService.queryOperationProgress(businessId, operationId))
            .thenReturn(successfulCall(soOperation));
        slicingService.addProgressToOrder(orderList);
    }

    @Test
    public void addProgressToOrderWithThrowsException() {

        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrder_creation_time("34562123");
        orderInfo.setOrder_id("sde2345rr");
        orderInfo.setOrder_status("creating");
        orderInfo.setService_snssai("sdffg34-344");
        orderInfo.setOrder_name("dfer-fgree");
        orderInfo.setOrder_index("1");
        orderInfo.setLast_operation_type("activate");
        orderInfo.setLast_operation_progress("79");

        OrderList orderList = new OrderList();
        orderList.setRecord_number(3);

        String businessId = "test123";
        String operationId = "opera123";
        when(soSliceService.queryOperationProgress(businessId, operationId))
            .thenReturn(failedCall("so is not exist!"));
        slicingService.addProgressToOrder(orderList);
    }
}
