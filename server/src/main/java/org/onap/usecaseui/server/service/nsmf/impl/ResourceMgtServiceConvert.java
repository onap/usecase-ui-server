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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;

import org.apache.commons.beanutils.BeanUtils;
import org.onap.usecaseui.server.bean.nsmf.common.PagedResult;
import org.onap.usecaseui.server.bean.nsmf.resource.HostedBusinessInfo;
import org.onap.usecaseui.server.bean.nsmf.resource.HostedNsiInfo;
import org.onap.usecaseui.server.bean.nsmf.resource.HostedNsiList;
import org.onap.usecaseui.server.bean.nsmf.resource.IncludedNssiInfo;
import org.onap.usecaseui.server.bean.nsmf.resource.NsiDetailList;
import org.onap.usecaseui.server.bean.nsmf.resource.NsiInfo;
import org.onap.usecaseui.server.bean.nsmf.resource.NsiRelatedNssiInfo;
import org.onap.usecaseui.server.bean.nsmf.resource.NsiServiceInstanceInfo;
import org.onap.usecaseui.server.bean.nsmf.resource.NsiServiceInstanceList;
import org.onap.usecaseui.server.bean.nsmf.resource.NssiServiceInstanceInfo;
import org.onap.usecaseui.server.bean.nsmf.resource.NssiServiceInstanceList;
import org.onap.usecaseui.server.bean.nsmf.resource.SlicingBusinessDetails;
import org.onap.usecaseui.server.bean.nsmf.resource.SlicingBusinessInfo;
import org.onap.usecaseui.server.bean.nsmf.resource.SlicingBusinessList;
import org.onap.usecaseui.server.bean.nsmf.resource.SubscriberInfo;
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
import org.onap.usecaseui.server.util.nsmf.NsmfCommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import retrofit2.Response;

@RequiredArgsConstructor
@Service("ResourceMgtConvertService")
public class ResourceMgtServiceConvert {

    private static final Logger logger = LoggerFactory.getLogger(ResourceMgtServiceConvert.class);
    private static final Gson gson = new Gson();

    @Resource(name = "GeneralConvertService")
    private GeneralConvertImpl generalConvert;

    private final AAISliceService aaiSliceService;

    void convertSlicingBusinessList(SlicingBusinessList slicingBusinessList, AAIServiceRsp aAIServiceRsp, int pageNo,
        int pageSize)
        throws InvocationTargetException, IllegalAccessException, IOException {
        if (aAIServiceRsp.getaAIService() == null || aAIServiceRsp.getaAIService().isEmpty()) {
            logger.error("convertSlicingBusinessList：aAIServiceRsp.getaAIService() is null or size is 0");
            return;
        }

        List<SlicingBusinessInfo> slicingBusinessInfoList = new ArrayList<>();
        for (AAIService aaiServiceInstance : aAIServiceRsp.getaAIService()) {
            SlicingBusinessInfo slicingBusinessInfo = new SlicingBusinessInfo();
            BeanUtils.copyProperties(slicingBusinessInfo, aaiServiceInstance);
            slicingBusinessInfoList.add(slicingBusinessInfo);
        }
        PagedResult pagedOrderList = NsmfCommonUtil.getPagedList(slicingBusinessInfoList, pageNo, pageSize);
        slicingBusinessList.setSlicingBusinessInfoList(pagedOrderList.getPagedList());
        slicingBusinessList.setRecordNumber(slicingBusinessInfoList.size());
    }

    void convertBusinessDetails(String businessId, SlicingBusinessDetails slicingBusinessDetails,
        AAIServiceAndInstance aaiServiceAndInstance)
        throws InvocationTargetException, IllegalAccessException, IOException {
        convertBusinessProfileDetails(businessId, slicingBusinessDetails, aaiServiceAndInstance);

        String nsiId = getNsiIdByBusiness(businessId);
        NstInfo nstInfo = new NstInfo();
        getNstInfoByBusiness(nstInfo, nsiId);
        slicingBusinessDetails.setNstInfo(nstInfo);

        NsiInfo nsiInfo = new NsiInfo();
        getNsiInfoByBusiness(nsiInfo, nsiId);
        slicingBusinessDetails.setNsiInfo(nsiInfo);

    }

    void getNsiInfoByBusiness(NsiInfo nsiInfo, String nsiId) {
        try {
            AAIServiceAndInstance aaiServiceAndInstance = new AAIServiceAndInstance();
            Response<JSONObject> response = this.aaiSliceService
                .listServiceById(NsmfParamConstant.CUSTOM_5G, NsmfParamConstant.SERVICE_TYPE_5G, nsiId)
                .execute();
            if (response.isSuccessful()) {
                logger.info("getNsiInfoByBusiness：listServiceById reponse is:{}", response.body());
                JSONObject object = response.body();
                aaiServiceAndInstance = generalConvert.listServiceByIdUtil(object);
                nsiInfo.setNsiId(aaiServiceAndInstance.getServiceInstanceId());
                nsiInfo.setNsiName(aaiServiceAndInstance.getServiceInstanceName());
                nsiInfo.setNsiOrchestrationStatus(aaiServiceAndInstance.getOrchestrationStatus());
                nsiInfo.setNsiType(aaiServiceAndInstance.getServiceType());
            } else {
                logger.error(String
                    .format("getNsiInfoByBusiness: Can not get listServiceById [code={}, message={}]", response.code(),
                        response.message()));
                return;
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return;
        }
    }

    void getNstInfoByBusiness(NstInfo nstInfo, String nsiId)
        throws IOException {
        String modelInvariantId;
        String modelVersionId;
        try {
            Response<JSONObject> response = this.aaiSliceService.querySerAndSubInsByNSI(NsmfParamConstant.CUSTOM_5G,
                NsmfParamConstant.SERVICE_TYPE_5G, nsiId).execute();
            if (response.isSuccessful()) {
                JSONObject object = response.body();
                logger.info("getNstInfoByBusiness: querySerAndSubInsByNSI reponse is:{}", response.body());
                AAIServiceAndInstance aaiNsi = queryNsiDetailsUtils(object);
                HostedNsiInfo hostedNsiInfo = new HostedNsiInfo();
                modelInvariantId = aaiNsi.getModelInvariantId();
                modelVersionId = aaiNsi.getModelVersionId();
            } else {
                logger.error(String
                    .format("getNstInfoByBusiness: Can not get listServiceById [code={}, message={}]", response.code(),
                        response.message()));
                return;
            }
        } catch (Exception e) {
            logger.error("Exception in getNstInfoByBusiness response",e);
            return;
        }

        try {
            Response<AAIServiceNST> nstResponse = this.aaiSliceService.queryServiceNST(modelInvariantId, modelVersionId)
                .execute();
            if (nstResponse.isSuccessful()) {
                AAIServiceNST aaiServiceNST = nstResponse.body();
                logger.info("getNstInfoByBusiness: queryServiceNST reponse is:{}", gson.toJson(aaiServiceNST));
                nstInfo.setNstId(aaiServiceNST.getModelVersionId());
                nstInfo.setNstName(aaiServiceNST.getModelName());
            } else {
                logger.error(String.format("Can not get queryServiceNST [code={}, message={}]", nstResponse.code(),
                    nstResponse.message()));
                return;
            }
        } catch (Exception e) {
            logger.error("Exception in getNstInfoByBusiness nstResponse",e);
            return;
        }
    }

    String getNsiIdByBusiness(String businessId)
        throws IOException {
        List<AAIServiceAndInstance> aaiServiceAndInstanceList = new ArrayList<>();
        try {
            Response<JSONObject> response = this.aaiSliceService.queryAllottedResources(NsmfParamConstant.CUSTOM_5G,
                NsmfParamConstant.SERVICE_TYPE_5G, businessId).execute();
            if (response.isSuccessful()) {
                logger.info("getNsiIdByBusiness: queryAllottedResources response is: {}", response.body());
                JSONObject object = response.body();
                aaiServiceAndInstanceList = queryAllottedResourceUtil(object);

                AAIServiceAndInstance aaiServiceAndInstance = aaiServiceAndInstanceList.get(0);
                Relationship relationship = aaiServiceAndInstance.getRelationshipList().get(0);
                List<RelationshipData> relationshipDataList = relationship.getRelationshipData();
                for (RelationshipData relationshipData : relationshipDataList) {
                    if (relationshipData.getRelationshipKey()
                        .equals(NsmfParamConstant.SERVICE_INSTANCE_SERVICE_INSTANCE_ID)) {
                        String nsiId = relationshipData.getRelationshipValue();
                        return nsiId;
                    }
                }

            } else {
                logger.error(String
                    .format("getNsiIdByBusiness: Can not get queryAllottedResources[code={}, message={}]",
                        response.code(),
                        response.message()));
                return "";
            }
        } catch (Exception e) {
            logger.error("Exception in getNsiIdByBusiness response",e);
            return "";
        }
        return "";
    }

    void convertBusinessProfileDetails(String businessId, SlicingBusinessDetails slicingBusinessDetails,
        AAIServiceAndInstance aaiServiceAndInstance)
        throws InvocationTargetException, IllegalAccessException, IOException {

        BusinessDemandInfo businessDemandInfo = new BusinessDemandInfo();
        businessDemandInfo.setServiceName(aaiServiceAndInstance.getServiceInstanceName());
        businessDemandInfo.setServiceSnssai(aaiServiceAndInstance.getEnvironmentContext());

        AAIServiceProfiles aaiServiceProfiles = new AAIServiceProfiles();
        try {
            Response<JSONObject> response = this.aaiSliceService.getServiceProfiles(NsmfParamConstant.CUSTOM_5G,
                NsmfParamConstant.SERVICE_TYPE_5G, businessId).execute();

            if (response.isSuccessful()) {
                logger.info("convertBusinessProfileDetails：getServiceProfiles is:{}", response.body());
                JSONObject object = response.body();
                if (object.containsKey("service-profile")) {
                    JSONArray array = object.getJSONArray("service-profile");
                    // return normal result code
                    JSONObject objectProfile = array.getJSONObject(0);
                    aaiServiceProfiles = objectProfile
                        .parseObject(objectProfile.toString(), AAIServiceProfiles.class);
                } else {
                    logger.error(String
                        .format("convertBusinessProfileDetails：Can not get getServiceProfiles[code={}, message={}]",
                            response.code(),
                            response.message()));
                }
            }
        } catch (Exception e) {
            logger.error("Exception in convertBusinessProfileDetails response",e);

        }

        String useInterval = generalConvert.getUseInterval(businessId);
        businessDemandInfo.setUseInterval(useInterval);
        convertServiceProfilesToBusinessDemandInfo(businessDemandInfo, aaiServiceProfiles);
        List<String> areaInfoList = generalConvert.getAreaTaList(aaiServiceProfiles.getCoverageAreaTAList());
        businessDemandInfo.setCoverageAreaTaList(areaInfoList);
        slicingBusinessDetails.setBusinessDemandInfo(businessDemandInfo);
    }

    void convertServiceProfilesToBusinessDemandInfo(BusinessDemandInfo businessDemandInfo, AAIServiceProfiles aaiServiceProfiles)
        throws InvocationTargetException, IllegalAccessException {
        BeanUtils.copyProperties(businessDemandInfo, aaiServiceProfiles);
        businessDemandInfo.setServiceProfileAvailability(String.valueOf(aaiServiceProfiles.getAvailability()));
        businessDemandInfo.setServiceProfileDLThptPerSlice(String.valueOf(aaiServiceProfiles.getDLThptPerSlice()));
        businessDemandInfo.setServiceProfileDLThptPerUE(String.valueOf(aaiServiceProfiles.getDLThptPerUE()));
        businessDemandInfo.setServiceProfileULThptPerSlice(String.valueOf(aaiServiceProfiles.getULThptPerSlice()));
        businessDemandInfo.setServiceProfileULThptPerUE(String.valueOf(aaiServiceProfiles.getULThptPerUE()));
        businessDemandInfo.setServiceProfileMaxPktSize(String.valueOf(aaiServiceProfiles.getMaxPktSize()));
        businessDemandInfo.setServiceProfileMaxNumberofConns(String.valueOf(aaiServiceProfiles.getMaxNumberofConns()));
        businessDemandInfo.setServiceProfileTermDensity(String.valueOf(aaiServiceProfiles.getTermDensity()));
    }

    void convertHostedNsiList(NsiServiceInstanceList nsiServiceInstanceList, AAIServiceRsp aAIServiceRsp, int pageNo,
        int pageSize)
        throws InvocationTargetException, IllegalAccessException {

        if (aAIServiceRsp.getaAIService() == null || aAIServiceRsp.getaAIService().size() == 0) {
            logger.error("convertHostedNsiList：aAIServiceRsp.getaAIService() is null or size is 0");
            return;
        }
        int i = 0;
        List<NsiServiceInstanceInfo> nsiServiceInstanceInfoList = new ArrayList<>();
        for (AAIService aaiServiceInstance : aAIServiceRsp.getaAIService()) {
            i++;
            NsiServiceInstanceInfo nsiServiceInstanceInfo = new NsiServiceInstanceInfo();
            nsiServiceInstanceInfo.setServiceInstanceOrder(String.valueOf(i));
            nsiServiceInstanceInfo.setOrchestrationStatus(aaiServiceInstance.getOrchestrationStatus());
            nsiServiceInstanceInfo.setServiceInstanceId(aaiServiceInstance.getServiceInstanceId());
            nsiServiceInstanceInfo.setServiceInstanceName(aaiServiceInstance.getServiceInstanceName());
            nsiServiceInstanceInfo.setServiceType(aaiServiceInstance.getServiceType());

            nsiServiceInstanceInfoList.add(nsiServiceInstanceInfo);
        }

        PagedResult pagedOrderList = NsmfCommonUtil.getPagedList(nsiServiceInstanceInfoList, pageNo, pageSize);
        nsiServiceInstanceList.setNsiServiceInstanceInfoList(pagedOrderList.getPagedList());
        nsiServiceInstanceList.setRecordNumber(nsiServiceInstanceInfoList.size());
    }

    void convertNsiDetailList(NsiDetailList nsiDetailList, AAIServiceAndInstance aaiServiceAndInstance)
        throws IllegalAccessException, IOException, InvocationTargetException {

        List<String> businessIdList = new ArrayList<>();
        List<String> nssiIdList = new ArrayList<>();
        getBusinessAndNssiIds(aaiServiceAndInstance, businessIdList, nssiIdList);

        List<HostedBusinessInfo> hostedBusinessInfoList = new ArrayList<>();
        getHostedBusinessInfoList(hostedBusinessInfoList, businessIdList);
        nsiDetailList.setHostedBusinessInfoList(hostedBusinessInfoList);

        List<IncludedNssiInfo> includedNssiInfoList = new ArrayList<>();
        getIncludedNssiInfoList(includedNssiInfoList, nssiIdList);
        nsiDetailList.setIncludedNssiInfoList(includedNssiInfoList);

    }

    void getIncludedNssiInfoList(List<IncludedNssiInfo> includedNssiInfoList, List<String> nssiIdList)
        throws IOException, InvocationTargetException, IllegalAccessException {
        int i = 0;
        for (String nssiId : nssiIdList) {
            i++;
            try {
                AAIServiceAndInstance aaiServiceAndInstance = new AAIServiceAndInstance();
                Response<JSONObject> response = this.aaiSliceService
                    .listServiceById(NsmfParamConstant.CUSTOM_5G, NsmfParamConstant.SERVICE_TYPE_5G, nssiId)
                    .execute();
                if (response.isSuccessful()) {
                    logger.info("getIncludedNssiInfoList：listServiceById reponse is:{}", response.body());
                    JSONObject object = response.body();
                    aaiServiceAndInstance = generalConvert.listServiceByIdUtil(object);
                    IncludedNssiInfo includedNssiInfo = new IncludedNssiInfo();
                    BeanUtils.copyProperties(includedNssiInfo, aaiServiceAndInstance);
                    includedNssiInfo.setServiceInstanceId(nssiId);
                    includedNssiInfo.setServiceInstanceOrder(String.valueOf(i));
                    includedNssiInfoList.add(includedNssiInfo);

                } else {
                    logger.error(String
                        .format("getIncludedNssiInfoList：Can not get listServiceById [code={}, message={}]",
                            response.code(),
                            response.message()));
                }
            } catch (Exception e) {
                logger.error("Exception in getIncludedNssiInfoList response",e);

            }
        }
    }

    void getHostedBusinessInfoList(List<HostedBusinessInfo> hostedBusinessInfoList, List<String> businessIdList)
        throws IOException, InvocationTargetException, IllegalAccessException {
        for (String businessId : businessIdList) {
            try {
                // 添加给slicingTaskCreationProgress赋值的代码
                AAIServiceAndInstance aaiServiceAndInstance = new AAIServiceAndInstance();
                Response<JSONObject> response = this.aaiSliceService
                    .listServiceById(NsmfParamConstant.CUSTOM_5G, NsmfParamConstant.SERVICE_TYPE_5G, businessId)
                    .execute();
                if (response.isSuccessful()) {
                    logger.info("getHostedBusinessInfoList: listServiceById reponse is:{}", response.body());
                    JSONObject object = response.body();
                    aaiServiceAndInstance = generalConvert.listServiceByIdUtil(object);
                    HostedBusinessInfo hostedBusinessInfo = new HostedBusinessInfo();
                    BeanUtils.copyProperties(hostedBusinessInfo, aaiServiceAndInstance);
                    hostedBusinessInfo.setServiceInstanceId(businessId);
                    hostedBusinessInfoList.add(hostedBusinessInfo);

                } else {
                    logger.error(String
                        .format("getHostedBusinessInfoList: Can not get listServiceById [code={}, message={}]",
                            response.code(),
                            response.message()));
                }
            } catch (Exception e) {
                logger.error("Exception in getHostedBusinessInfoList response",e);
            }
        }
    }

    void getBusinessAndNssiIds(AAIServiceAndInstance aaiServiceAndInstance, List<String> businessIdList,
        List<String> nssiIdList) {
        List<Relationship> relationshipList = aaiServiceAndInstance.getRelationshipList();
        if (relationshipList != null && relationshipList.size() > 0) {
            for (Relationship relationship : relationshipList) {
                if (relationship.getRelatedTo().equals(NsmfParamConstant.ALLOTTED_RESOURCE)) {
                    List<RelationshipData> relationshipDataList = relationship.getRelationshipData();
                    for (RelationshipData relationshipData : relationshipDataList) {
                        if (relationshipData.getRelationshipKey()
                            .equals(NsmfParamConstant.SERVICE_INSTANCE_SERVICE_INSTANCE_ID)) {
                            businessIdList.add(relationshipData.getRelationshipValue());
                        }
                    }
                }
                if (relationship.getRelatedTo().equals(NsmfParamConstant.SERVICE_INSTANCE)) {
                    List<RelationshipData> relationshipDataList = relationship.getRelationshipData();
                    for (RelationshipData relationshipData : relationshipDataList) {
                        if (relationshipData.getRelationshipKey()
                            .equals(NsmfParamConstant.SERVICE_INSTANCE_SERVICE_INSTANCE_ID)) {
                            nssiIdList.add(relationshipData.getRelationshipValue());
                        }
                    }
                }
            }
        }

    }

    void convertNsiRelatedNssiInfo(NsiRelatedNssiInfo nsiRelatedNssiInfo, AAIServiceAndInstance aaiServiceAndInstance)
        throws IllegalAccessException, IOException, InvocationTargetException {

        List<String> nssiIdList = new ArrayList<>();
        List<String> businessIdList = new ArrayList<>();
        getBusinessAndNssiIds(aaiServiceAndInstance, businessIdList, nssiIdList);
        List<IncludedNssiInfo> includedNssiInfoList = new ArrayList<>();
        getIncludedNssiInfoList(includedNssiInfoList, nssiIdList);
        getNsiRelatedNssiInfo(nsiRelatedNssiInfo, includedNssiInfoList);

    }

    void getNsiRelatedNssiInfo(NsiRelatedNssiInfo nsiRelatedNssiInfo, List<IncludedNssiInfo> includedNssiInfoList) {
        for (IncludedNssiInfo includedNssiInfo : includedNssiInfoList) {
            if (includedNssiInfo.getEnvironmentContext().equals(NsmfParamConstant.AN_NAME)) {
                nsiRelatedNssiInfo.setAnSuggestNssiId(includedNssiInfo.getServiceInstanceId());
                nsiRelatedNssiInfo.setAnSuggestNssiName(includedNssiInfo.getServiceInstanceName());
            }

            if (includedNssiInfo.getEnvironmentContext().equals(NsmfParamConstant.TN_NAME)) {
                nsiRelatedNssiInfo.setTnSuggestNssiId(includedNssiInfo.getServiceInstanceId());
                nsiRelatedNssiInfo.setTnSuggestNssiName(includedNssiInfo.getServiceInstanceName());
            }

            if (includedNssiInfo.getEnvironmentContext().equals(NsmfParamConstant.CN_NAME)) {
                nsiRelatedNssiInfo.setCnSuggestNssiId(includedNssiInfo.getServiceInstanceId());
                nsiRelatedNssiInfo.setCnSuggestNssiName(includedNssiInfo.getServiceInstanceName());
            }
        }
    }

    void convertNssiServiceInstanceList(NssiServiceInstanceList nssiServiceInstanceList,
        AAIServiceRsp aAIServiceRsp,
        int pageNo, int pageSize)
        throws InvocationTargetException, IllegalAccessException {
        if (aAIServiceRsp.getaAIService() == null || aAIServiceRsp.getaAIService().isEmpty()) {
            logger.error("convertNssiServiceInstanceList: aAIServiceRsp.getaAIService() is null or size is 0");
            return;
        }

        List<NssiServiceInstanceInfo> nssiServiceInstanceInfoList = new ArrayList<>();
        int i = 0;
        for (AAIService aaiService : aAIServiceRsp.getaAIService()) {
            i++;
            NssiServiceInstanceInfo nssiServiceInstanceInfo = new NssiServiceInstanceInfo();
            BeanUtils.copyProperties(nssiServiceInstanceInfo, aaiService);
            nssiServiceInstanceInfo.setServiceInstanceOrder(String.valueOf(i));
            nssiServiceInstanceInfoList.add(nssiServiceInstanceInfo);
        }
        PagedResult pagedOrderList = NsmfCommonUtil.getPagedList(nssiServiceInstanceInfoList, pageNo, pageSize);
        nssiServiceInstanceList.setNssiServiceInstanceInfoList(pagedOrderList.getPagedList());
        nssiServiceInstanceList.setRecordNumber(nssiServiceInstanceInfoList.size());
    }

    void convertNssiDetails(HostedNsiList hostedNsiList, AAIServiceAndInstance aaiServiceAndInstance)
        throws InvocationTargetException, IllegalAccessException, IOException {

        List<String> nsiIdList = new ArrayList<>();
        List<String> emptyList = new ArrayList<>();
        getBusinessAndNssiIds(aaiServiceAndInstance, emptyList, nsiIdList);
        List<HostedNsiInfo> hostedNsiInfoList = new ArrayList<>();
        for (String nsiId : nsiIdList) {
            try {
                Response<JSONObject> response = this.aaiSliceService.querySerAndSubInsByNSI(NsmfParamConstant.CUSTOM_5G,
                    NsmfParamConstant.SERVICE_TYPE_5G, nsiId).execute();
                if (response.isSuccessful()) {
                    JSONObject object = response.body();
                    logger.info("convertNssiDetails: querySerAndSubInsByNSI reponse is:{}", response.body());
                    AAIServiceAndInstance aaiNssi = queryNsiDetailsUtils(object);
                    HostedNsiInfo hostedNsiInfo = new HostedNsiInfo();
                    hostedNsiInfo.setServiceInstanceId(aaiNssi.getServiceInstanceId());
                    hostedNsiInfo.setServiceInstanceName(aaiNssi.getServiceInstanceName());
                    hostedNsiInfo.setServiceType(aaiNssi.getServiceType());
                    hostedNsiInfo.setOrchestrationStatus(aaiNssi.getOrchestrationStatus());
                    hostedNsiInfoList.add(hostedNsiInfo);
                } else {
                    logger.error(String.format("convertNssiDetails: Can not get listServiceById [code={}, message={}]",
                        response.code(),
                        response.message()));
                }
            } catch (Exception e) {
                logger.error("Exception in convertNssiDetails response",e);
            }
        }

        hostedNsiList.setHostedNsiInfoList(hostedNsiInfoList);
        hostedNsiList.setRecordNumber(hostedNsiInfoList.size());
    }

    SubscriberInfo buildSubscriberInfo(String globalSubscriberId, String serviceType) {

        SubscriberInfo subscriberInfo = new SubscriberInfo();
        subscriberInfo.setGlobalSubscriberId(globalSubscriberId);
        subscriberInfo.setServiceType(serviceType);
        return subscriberInfo;
    }

    void buildAAIServiceRsp(AAIServiceRsp aAIServiceRsp, JSONArray array) {
        List<AAIService> aaiserviceList = new ArrayList<AAIService>();
        for (int i = 0; i < array.size(); i++) {
            JSONObject object = array.getJSONObject(i);
            AAIService aaiService = new AAIService();
            aaiService.setServiceInstanceId(object.getString("service-instance-id"));
            aaiService.setServiceInstanceName(object.getString("service-instance-name"));
            aaiService.setServiceType(object.getString("service-type"));
            aaiService.setEnvironmentContext(object.getString("environment-context"));
            aaiService.setOrchestrationStatus(object.getString("orchestration-status"));
            aaiserviceList.add(aaiService);
        }
        aAIServiceRsp.setaAIService(aaiserviceList);
    }

    public AAIServiceAndInstance queryNsiDetailsUtils(JSONObject object) {
        if (object.containsKey("relationship-list")) {
            AAIServiceAndInstance aaiServiceAndInstance = new AAIServiceAndInstance();
            aaiServiceAndInstance.setServiceInstanceId(object.getString("service-instance-id"));
            aaiServiceAndInstance.setServiceInstanceName(object.getString("service-instance-name"));
            aaiServiceAndInstance.setModelInvariantId(object.getString("model-invariant-id"));
            aaiServiceAndInstance.setModelVersionId(object.getString("model-version-id"));
            aaiServiceAndInstance.setServiceType(object.getString("service-type"));
            aaiServiceAndInstance.setOrchestrationStatus(object.getString("orchestration-status"));

            JSONArray array = object.getJSONObject("relationship-list").getJSONArray("relationship");
            List<Relationship> relationshipList = new ArrayList<Relationship>();
            for (int i = 0; i < array.size(); i++) {

                Relationship relationship = new Relationship();
                JSONObject objectShip = array.getJSONObject(i);
                JSONArray arrayData = objectShip.getJSONArray("relationship-data");
                relationship.setRelatedTo(objectShip.getString("related-to"));
                List<RelationshipData> RelationshipDataList = new ArrayList<RelationshipData>();
                for (int j = 0; j < arrayData.size(); j++) {
                    RelationshipData relationshipData = new RelationshipData();
                    JSONObject objectData = arrayData.getJSONObject(j);
                    relationshipData.setRelationshipKey(objectData.getString("relationship-key"));
                    relationshipData.setRelationshipValue(objectData.getString("relationship-value"));
                    RelationshipDataList.add(relationshipData);
                }
                relationshipList.add(relationship);
                relationship.setRelationshipData(RelationshipDataList);

            }
            aaiServiceAndInstance.setRelationshipList(relationshipList);
            return aaiServiceAndInstance;
        } else {
            return null;
        }
    }

    public List<AAIServiceAndInstance> queryAllottedResourceUtil(JSONObject objectResource) {
        JSONArray arrayResource = objectResource.getJSONArray("allotted-resource");
        if (arrayResource == null || arrayResource.isEmpty()) {
            logger.error("arrayResource is null or size is 0");
            return null;
        }

        List<AAIServiceAndInstance> aaiServiceAndInstanceList = new ArrayList<AAIServiceAndInstance>();
        for (int i = 0; i < arrayResource.size(); i++) {
            JSONObject object = arrayResource.getJSONObject(i);
            if (object.containsKey("relationship-list")) {
                AAIServiceAndInstance aaiServiceAndInstance = new AAIServiceAndInstance();
                JSONArray array = object.getJSONObject("relationship-list").getJSONArray("relationship");
                List<Relationship> relationshipList = new ArrayList<Relationship>();
                for (int j = 0; j < array.size(); j++) {

                    Relationship relationship = new Relationship();
                    JSONObject objectShip = array.getJSONObject(j);
                    JSONArray arrayData = objectShip.getJSONArray("relationship-data");
                    relationship.setRelatedTo(objectShip.getString("related-to"));
                    List<RelationshipData> RelationshipDataList = new ArrayList<RelationshipData>();
                    for (int h = 0; h < arrayData.size(); h++) {
                        RelationshipData relationshipData = new RelationshipData();
                        JSONObject objectData = arrayData.getJSONObject(h);
                        relationshipData.setRelationshipKey(objectData.getString("relationship-key"));
                        relationshipData.setRelationshipValue(objectData.getString("relationship-value"));
                        RelationshipDataList.add(relationshipData);
                    }
                    relationshipList.add(relationship);
                    relationship.setRelationshipData(RelationshipDataList);

                }
                aaiServiceAndInstance.setRelationshipList(relationshipList);
                aaiServiceAndInstanceList.add(aaiServiceAndInstance);
            } else {
                return null;
            }
        }
        return aaiServiceAndInstanceList;
    }
}
