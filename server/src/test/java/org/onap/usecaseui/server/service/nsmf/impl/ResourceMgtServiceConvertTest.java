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
import static org.onap.usecaseui.server.util.CallStub.failedCall;
import static org.onap.usecaseui.server.util.CallStub.successfulCall;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.onap.usecaseui.server.bean.nsmf.resource.HostedBusinessInfo;
import org.onap.usecaseui.server.bean.nsmf.resource.HostedNsiList;
import org.onap.usecaseui.server.bean.nsmf.resource.IncludedNssiInfo;
import org.onap.usecaseui.server.bean.nsmf.resource.NsiDetailList;
import org.onap.usecaseui.server.bean.nsmf.resource.NsiInfo;
import org.onap.usecaseui.server.bean.nsmf.resource.NsiRelatedNssiInfo;
import org.onap.usecaseui.server.bean.nsmf.resource.NsiServiceInstanceList;
import org.onap.usecaseui.server.bean.nsmf.resource.NssiServiceInstanceList;
import org.onap.usecaseui.server.bean.nsmf.resource.SlicingBusinessDetails;
import org.onap.usecaseui.server.bean.nsmf.resource.SlicingBusinessList;
import org.onap.usecaseui.server.bean.nsmf.task.BusinessDemandInfo;
import org.onap.usecaseui.server.bean.nsmf.task.NstInfo;
import org.onap.usecaseui.server.constant.nsmf.NsmfParamConstant;
import org.onap.usecaseui.server.service.slicingdomain.aai.AAISliceService;
import org.onap.usecaseui.server.service.slicingdomain.aai.bean.AAIService;
import org.onap.usecaseui.server.service.slicingdomain.aai.bean.AAIServiceAndInstance;
import org.onap.usecaseui.server.service.slicingdomain.aai.bean.AAIServiceNST;
import org.onap.usecaseui.server.service.slicingdomain.aai.bean.AAIServiceProfiles;
import org.onap.usecaseui.server.service.slicingdomain.aai.bean.AAIServiceRsp;
import org.onap.usecaseui.server.service.slicingdomain.aai.bean.Relationship;
import org.onap.usecaseui.server.service.slicingdomain.aai.bean.RelationshipData;
import org.onap.usecaseui.server.service.slicingdomain.so.SOSliceService;

public class ResourceMgtServiceConvertTest {

    ResourceMgtServiceConvert resourceMgtServiceConvert = null;
    SOSliceService soSliceService = null;
    AAISliceService aaiSliceService = null;

    @Before
    public void before() throws Exception {
        aaiSliceService = mock(AAISliceService.class);
        soSliceService = mock(SOSliceService.class);
        resourceMgtServiceConvert = new ResourceMgtServiceConvert(soSliceService, aaiSliceService);
    }

    @Test
    public void itCanConvertSlicingBusinessList() {
        SlicingBusinessList slicingBusinessList = new SlicingBusinessList();
        AAIServiceRsp aAIServiceRsp = new AAIServiceRsp();
        List<AAIService> aAIService = new ArrayList<>();
        AAIService aaiService = new AAIService();
        aAIService.add(aaiService);
        aAIServiceRsp.setaAIService(aAIService);

        AAIServiceRsp aAIServiceRspNull = new AAIServiceRsp();
        try {
            resourceMgtServiceConvert.convertSlicingBusinessList(slicingBusinessList, aAIServiceRsp, 1, 10);
            resourceMgtServiceConvert.convertSlicingBusinessList(slicingBusinessList, aAIServiceRspNull, 1, 10);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void itCanConvertBusinessDetails() {
        SlicingBusinessDetails slicingBusinessDetails = new SlicingBusinessDetails();
        AAIServiceAndInstance aaiServiceAndInstance = new AAIServiceAndInstance();
        String businessId = "iu89-iu87-iu78-tr67";
        try {
            resourceMgtServiceConvert.convertBusinessDetails(businessId, slicingBusinessDetails, aaiServiceAndInstance);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void itCanGetNsiInfoByBusiness() {
        JSONObject object = new JSONObject();
        NsiInfo nsiInfo = new NsiInfo();
        String nsiId = "1234-ty23-rt56-oiu9";

        when(aaiSliceService.listServiceById(NsmfParamConstant.CUSTOM_5G, NsmfParamConstant.SERVICE_TYPE_5G, nsiId))
            .thenReturn(successfulCall(object));
        resourceMgtServiceConvert.getNsiInfoByBusiness(nsiInfo, nsiId);
    }

    @Test
    public void getNsiInfoByBusinessWithThrowsException() {
        NsiInfo nsiInfo = new NsiInfo();
        String nsiId = "1234-ty23-rt56-oiu9";

        when(aaiSliceService.listServiceById(NsmfParamConstant.CUSTOM_5G, NsmfParamConstant.SERVICE_TYPE_5G, nsiId))
            .thenReturn(failedCall("aai is not exist!"));
        resourceMgtServiceConvert.getNsiInfoByBusiness(nsiInfo, nsiId);
    }

    @Test
    public void itCanGetNstInfoByBusiness() {
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

        NstInfo nstInfo = new NstInfo();
        String nsiId = "1234-ty23-rt56-oiu9";

        when(aaiSliceService
            .querySerAndSubInsByNSI(NsmfParamConstant.CUSTOM_5G, NsmfParamConstant.SERVICE_TYPE_5G, nsiId))
            .thenReturn(successfulCall(object));

        String modelInvariantId = "120i-98er-67ty-87yt";
        String modelVersionId = "v00001";

        AAIServiceNST aaiServiceNST = new AAIServiceNST();
        when(aaiSliceService.queryServiceNST(modelInvariantId, modelVersionId))
            .thenReturn(successfulCall(aaiServiceNST));

        try {
            resourceMgtServiceConvert.getNstInfoByBusiness(nstInfo, nsiId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getNstInfoByBusinessWithThrowsException() {
        NstInfo nstInfo = new NstInfo();
        String nsiId = "1234-ty23-rt56-oiu9";

        when(aaiSliceService
            .querySerAndSubInsByNSI(NsmfParamConstant.CUSTOM_5G, NsmfParamConstant.SERVICE_TYPE_5G, nsiId))
            .thenReturn(failedCall("aai is not exist!"));

        String modelInvariantId = "120i-98er-67ty-87yt";
        String modelVersionId = "v00001";

        AAIServiceNST aaiServiceNST = new AAIServiceNST();
        when(aaiSliceService.queryServiceNST(modelInvariantId, modelVersionId))
            .thenReturn(failedCall("aai is not exist!"));

        try {
            resourceMgtServiceConvert.getNstInfoByBusiness(nstInfo, nsiId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void itCanGetNsiIdByBusiness() {
        JSONObject object = new JSONObject();
        String businessId = "1234-ty23-rt56-oiu9";

        when(aaiSliceService.queryAllottedResources(NsmfParamConstant.CUSTOM_5G,
            NsmfParamConstant.SERVICE_TYPE_5G, businessId))
            .thenReturn(successfulCall(object));
        try {
            resourceMgtServiceConvert.getNsiIdByBusiness(businessId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getNsiIdByBusinessWithThrowsException() {
        String businessId = "1234-ty23-rt56-oiu9";

        when(aaiSliceService
            .queryAllottedResources(NsmfParamConstant.CUSTOM_5G, NsmfParamConstant.SERVICE_TYPE_5G, businessId))
            .thenReturn(failedCall("aai is not exist!"));
        try {
            resourceMgtServiceConvert.getNsiIdByBusiness(businessId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void itCanConvertBusinessProfileDetails() {
        JSONObject object = new JSONObject();
        String businessId = "1234-ty23-rt56-oiu9";
        SlicingBusinessDetails slicingBusinessDetails = new SlicingBusinessDetails();
        AAIServiceAndInstance aaiServiceAndInstance = new AAIServiceAndInstance();

        when(aaiSliceService.getServiceProfiles(NsmfParamConstant.CUSTOM_5G,
            NsmfParamConstant.SERVICE_TYPE_5G, businessId))
            .thenReturn(successfulCall(object));
        try {
            resourceMgtServiceConvert
                .convertBusinessProfileDetails(businessId, slicingBusinessDetails, aaiServiceAndInstance);
        } catch (IOException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void itConvertServiceProfilesToBusinessDemandInfo() {
        BusinessDemandInfo businessDemandInfo = new BusinessDemandInfo();
        AAIServiceProfiles aaiServiceProfiles = new AAIServiceProfiles();
        try {
            resourceMgtServiceConvert.convertServiceProfilesToBusinessDemandInfo(businessDemandInfo, aaiServiceProfiles);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }


    @Test
    public void itCanConvertHostedNsiList() {
        NsiServiceInstanceList nsiServiceInstanceList = new NsiServiceInstanceList();
        AAIServiceRsp aAIServiceRsp = new AAIServiceRsp();
        List<AAIService> aaiServiceList = new ArrayList<>();
        AAIService aaiService = new AAIService();
        aaiServiceList.add(aaiService);
        aAIServiceRsp.setaAIService(aaiServiceList);

        AAIServiceRsp aAIServiceRspNull = new AAIServiceRsp();

        try {
            resourceMgtServiceConvert.convertHostedNsiList(nsiServiceInstanceList, aAIServiceRsp, 1, 10);
            resourceMgtServiceConvert.convertHostedNsiList(nsiServiceInstanceList, aAIServiceRspNull, 1, 10);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void itCanConvertNsiDetailList() {
        NsiDetailList nsiDetailList = new NsiDetailList();
        AAIServiceAndInstance aaiServiceAndInstance = new AAIServiceAndInstance();

        try {
            resourceMgtServiceConvert.convertNsiDetailList(nsiDetailList, aaiServiceAndInstance);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void itCanGetIncludedNssiInfoList() {
        List<IncludedNssiInfo> includedNssiInfoList = new ArrayList<>();
        List<String> nssiIdList = new ArrayList<>();
        String nssiId = "nssiTest01";
        nssiIdList.add(nssiId);
        JSONObject object = new JSONObject();

        when(aaiSliceService
            .listServiceById(NsmfParamConstant.CUSTOM_5G, NsmfParamConstant.SERVICE_TYPE_5G, nssiId))
            .thenReturn(successfulCall(object));

        try {
            resourceMgtServiceConvert.getIncludedNssiInfoList(includedNssiInfoList, nssiIdList);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getIncludedNssiInfoListWithThrowsException() {
        List<IncludedNssiInfo> includedNssiInfoList = new ArrayList<>();
        List<String> nssiIdList = new ArrayList<>();
        String nssiId = "nssiTest01";
        nssiIdList.add(nssiId);

        when(aaiSliceService
            .listServiceById(NsmfParamConstant.CUSTOM_5G, NsmfParamConstant.SERVICE_TYPE_5G, nssiId))
            .thenReturn(failedCall("aai is not exist!"));

        try {
            resourceMgtServiceConvert.getIncludedNssiInfoList(includedNssiInfoList, nssiIdList);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void itCanGetHostedBusinessInfoList() {
        List<HostedBusinessInfo> hostedBusinessInfoList = new ArrayList<>();
        List<String> businessIdList = new ArrayList<>();
        String businessId = "busiId001";
        businessIdList.add(businessId);
        JSONObject object = new JSONObject();

        when(aaiSliceService
            .listServiceById(NsmfParamConstant.CUSTOM_5G, NsmfParamConstant.SERVICE_TYPE_5G, businessId))
            .thenReturn(successfulCall(object));
        try {
            resourceMgtServiceConvert.getHostedBusinessInfoList(hostedBusinessInfoList, businessIdList);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getHostedBusinessInfoListWithThrowsException() {
        List<HostedBusinessInfo> hostedBusinessInfoList = new ArrayList<>();
        List<String> businessIdList = new ArrayList<>();
        String businessId = "busiId001";
        businessIdList.add(businessId);

        when(aaiSliceService
            .listServiceById(NsmfParamConstant.CUSTOM_5G, NsmfParamConstant.SERVICE_TYPE_5G, businessId))
            .thenReturn(failedCall("aai is not exist!"));
        try {
            resourceMgtServiceConvert.getHostedBusinessInfoList(hostedBusinessInfoList, businessIdList);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void itCanGetBusinessAndNssiIds() {
        AAIServiceAndInstance aaiServiceAndInstance = new AAIServiceAndInstance();
        List<Relationship> relationshipList = new ArrayList<>();
        Relationship relationship = new Relationship();
        relationship.setRelatedTo(NsmfParamConstant.ALLOTTED_RESOURCE);
        List<RelationshipData> relationshipDataList = new ArrayList<>();
        RelationshipData relationshipData = new RelationshipData();
        relationshipData.setRelationshipKey(NsmfParamConstant.SERVICE_INSTANCE_SERVICE_INSTANCE_ID);
        relationshipDataList.add(relationshipData);
        relationship.setRelationshipData(relationshipDataList);
        relationshipList.add(relationship);

        Relationship relationship1 = new Relationship();
        relationship1.setRelatedTo(NsmfParamConstant.SERVICE_INSTANCE);
        List<RelationshipData> relationshipDataList1 = new ArrayList<>();
        RelationshipData relationshipData1 = new RelationshipData();
        relationshipData1.setRelationshipKey(NsmfParamConstant.SERVICE_INSTANCE_SERVICE_INSTANCE_ID);
        relationshipDataList1.add(relationshipData1);
        relationship1.setRelationshipData(relationshipDataList1);
        relationshipList.add(relationship1);

        aaiServiceAndInstance.setRelationshipList(relationshipList);

        List<String> businessIdList = new ArrayList<>();
        List<String> nssiIdList = new ArrayList<>();

        resourceMgtServiceConvert.getBusinessAndNssiIds(aaiServiceAndInstance, businessIdList, nssiIdList);
    }

    @Test
    public void itCanConvertNsiRelatedNssiInfo() {
        NsiRelatedNssiInfo nsiRelatedNssiInfo = new NsiRelatedNssiInfo();
        AAIServiceAndInstance aaiServiceAndInstance = new AAIServiceAndInstance();

        try {
            resourceMgtServiceConvert.convertNsiRelatedNssiInfo(nsiRelatedNssiInfo, aaiServiceAndInstance);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void itCanGetNsiRelatedNssiInfo() {

        NsiRelatedNssiInfo nsiRelatedNssiInfo = new NsiRelatedNssiInfo();
        List<IncludedNssiInfo> includedNssiInfoList = new ArrayList<>();
        IncludedNssiInfo includedNssiInfo1 = new IncludedNssiInfo();
        includedNssiInfo1.setEnvironmentContext(NsmfParamConstant.AN_NAME);

        IncludedNssiInfo includedNssiInfo2 = new IncludedNssiInfo();
        includedNssiInfo2.setEnvironmentContext(NsmfParamConstant.TN_NAME);

        IncludedNssiInfo includedNssiInfo3 = new IncludedNssiInfo();
        includedNssiInfo3.setEnvironmentContext(NsmfParamConstant.CN_NAME);

        includedNssiInfoList.add(includedNssiInfo1);
        includedNssiInfoList.add(includedNssiInfo2);
        includedNssiInfoList.add(includedNssiInfo3);

        resourceMgtServiceConvert.getNsiRelatedNssiInfo(nsiRelatedNssiInfo, includedNssiInfoList);
    }

    @Test
    public void itCanConvertNssiServiceInstanceList() {

        NssiServiceInstanceList nssiServiceInstanceList = new NssiServiceInstanceList();
        AAIServiceRsp aAIServiceRsp = new AAIServiceRsp();

        List<AAIService> aAIService = new ArrayList<>();
        AAIService aaiService = new AAIService();
        aAIService.add(aaiService);
        aAIServiceRsp.setaAIService(aAIService);

        AAIServiceRsp aAIServiceRsp1 = new AAIServiceRsp();

        try {
            resourceMgtServiceConvert.convertNssiServiceInstanceList(nssiServiceInstanceList, aAIServiceRsp, 1, 100);
            resourceMgtServiceConvert.convertNssiServiceInstanceList(nssiServiceInstanceList, aAIServiceRsp1, 1, 100);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void itCanConvertNssiDetails() {
        JSONObject object = new JSONObject();
        String nsiId = "1234-ty23-rt56-oiu9";

        HostedNsiList hostedNsiList = new HostedNsiList();
        AAIServiceAndInstance aaiServiceAndInstance = new AAIServiceAndInstance();

        when(aaiSliceService.querySerAndSubInsByNSI(NsmfParamConstant.CUSTOM_5G,
            NsmfParamConstant.SERVICE_TYPE_5G, nsiId))
            .thenReturn(successfulCall(object));
        try {
            resourceMgtServiceConvert
                .convertNssiDetails(hostedNsiList, aaiServiceAndInstance);
        } catch (IOException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void convertNssiDetailsWithThrowsException() {
        String nsiId = "1234-ty23-rt56-oiu9";
        HostedNsiList hostedNsiList = new HostedNsiList();
        AAIServiceAndInstance aaiServiceAndInstance = new AAIServiceAndInstance();

        when(aaiSliceService
            .querySerAndSubInsByNSI(NsmfParamConstant.CUSTOM_5G, NsmfParamConstant.SERVICE_TYPE_5G, nsiId))
            .thenReturn(failedCall("aai is not exist!"));
        try {
            resourceMgtServiceConvert
                .convertNssiDetails(hostedNsiList, aaiServiceAndInstance);
        } catch (IOException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void itCanBuildSubscriberInfo() {
        resourceMgtServiceConvert.buildSubscriberInfo(NsmfParamConstant.CUSTOM_5G, NsmfParamConstant.SERVICE_TYPE_5G);
    }

    @Test
    public void itCanBuildAAIServiceRsp() {
        AAIServiceRsp aAIServiceRsp = new AAIServiceRsp();
        JSONArray array = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        array.add(jsonObject);
        resourceMgtServiceConvert.buildAAIServiceRsp(aAIServiceRsp, array);
    }

    @Test
    public void itCanQueryNsiDetailsUtils() {
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

        resourceMgtServiceConvert.queryNsiDetailsUtils(object);
    }

    @Test
    public void itCanQueryAllottedResourceUtil() {
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

        JSONObject objectAll = new JSONObject();
        JSONArray jsonArrayAll = new JSONArray();
        jsonArrayAll.add(object);
        objectAll.put("allotted-resource", jsonArrayAll);
        resourceMgtServiceConvert.queryAllottedResourceUtil(objectAll);
    }

}
