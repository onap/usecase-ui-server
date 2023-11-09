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

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.onap.usecaseui.server.util.CallStub.failedCall;
import static org.onap.usecaseui.server.util.CallStub.successfulCall;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONArray;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.Before;
import org.junit.Test;
import org.onap.usecaseui.server.constant.nsmf.NsmfParamConstant;
import org.onap.usecaseui.server.service.slicingdomain.aai.AAISliceService;
import org.onap.usecaseui.server.service.slicingdomain.aai.bean.AAIServiceAndInstance;
import org.onap.usecaseui.server.service.slicingdomain.aai.bean.Relationship;
import org.onap.usecaseui.server.service.slicingdomain.aai.bean.RelationshipData;

public class GeneralConvertImplTest {

    GeneralConvertImpl generalConvert = null;
    AAISliceService aaiSliceService = null;

    @Before
    public void before() throws Exception {
        aaiSliceService = mock(AAISliceService.class);
        generalConvert = new GeneralConvertImpl(aaiSliceService);
    }

    @Test
    public void itCanQueryServiceUtil() {
        JSONObject object = new JSONObject();
        object.put("relationship-list", "jsonTest");
        object.put("workload-context", "jsonTest");
        JSONObject object1 = new JSONObject();
        JSONObject object2 = new JSONObject();
        JSONObject object3 = new JSONObject();

        JSONArray jsonArrayrelationship_data = new JSONArray();
        jsonArrayrelationship_data.add(object3);
        object2.put("relationship-data", jsonArrayrelationship_data);

        JSONArray jsonArray = new JSONArray();
        jsonArray.add(object2);

        object1.put("relationship", jsonArray);
        object.put("relationship-list", object1);
        generalConvert.queryServiceUtil(object);
    }

    @Test
    public void itCanGetAreaTaList() {
        String sourceCoverageAreaTAList = "gansu linxia | gansu lanzhou";
        generalConvert.getAreaTaList(sourceCoverageAreaTAList);
    }

    @Test
    public void itCanListServiceByIdUtil() {
        JSONObject object = new JSONObject();
        generalConvert.listServiceByIdUtil(object);
    }

    @Test
    public void itCanGetUseInterval() {
        JSONObject object = new JSONObject();
        String globalCustomerId = NsmfParamConstant.CUSTOM_5G;
        String serviceType = NsmfParamConstant.SERVICE_TYPE_5G;
        String serviceInstanceId = "1234-ty23-rt56-oiu9";

        when(aaiSliceService.queryOrderByService(globalCustomerId, serviceType, serviceInstanceId))
            .thenReturn(successfulCall(object));

        String orderId = "order123";
        when(aaiSliceService
            .queryOrderByOrderId(NsmfParamConstant.CUSTOM_5G, NsmfParamConstant.SERVICE_TYPE_5G, orderId))
            .thenReturn(successfulCall(object));
        generalConvert.getUseInterval(serviceInstanceId);
    }

    @Test
    public void getUseIntervalWithThrowsException() throws IOException {
        String globalCustomerId = NsmfParamConstant.CUSTOM_5G;
        String serviceType = NsmfParamConstant.SERVICE_TYPE_5G;
        String serviceInstanceId = "1234-ty23-rt56-oiu9";

        when(aaiSliceService.queryOrderByService(globalCustomerId, serviceType, serviceInstanceId))
            .thenReturn(failedCall("aai is not exist!"));

        String orderId = "order123";
        when(aaiSliceService
            .queryOrderByOrderId(NsmfParamConstant.CUSTOM_5G, NsmfParamConstant.SERVICE_TYPE_5G, orderId))
            .thenReturn(failedCall("aai is not exist!"));

        generalConvert.getUseInterval(serviceInstanceId);
    }

    @Test
    public void itCanGetOrderIdFromRelation() {
        AAIServiceAndInstance aaiServiceAndInstance = new AAIServiceAndInstance();

        List<Relationship> relationshipList = new ArrayList<>();
        Relationship relationship = new Relationship();
        List<RelationshipData> relationshipDataList = new ArrayList<>();
        RelationshipData relationshipData = new RelationshipData();
        relationshipData.setRelationshipKey("service-instance.service-instance-id");
        relationshipDataList.add(relationshipData);
        relationship.setRelationshipData(relationshipDataList);
        relationshipList.add(relationship);
        aaiServiceAndInstance.setRelationshipList(relationshipList);

        generalConvert.getOrderIdFromRelation(aaiServiceAndInstance);
    }
}
