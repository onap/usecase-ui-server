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


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import org.apache.commons.beanutils.BeanUtils;
import org.onap.usecaseui.server.bean.ServiceInstanceOperations;
import org.onap.usecaseui.server.bean.nsmf.common.ResultHeader;
import org.onap.usecaseui.server.bean.nsmf.common.ServiceResult;
import org.onap.usecaseui.server.bean.nsmf.resource.AnSliceTaskInfoToUI;
import org.onap.usecaseui.server.bean.nsmf.resource.CnSliceTaskInfoToUI;
import org.onap.usecaseui.server.bean.nsmf.resource.HostedNsiList;
import org.onap.usecaseui.server.bean.nsmf.resource.TnBHSliceTaskInfoToUI;
import org.onap.usecaseui.server.service.slicingdomain.aai.bean.connection.NetworkInfo;
import org.onap.usecaseui.server.bean.nsmf.resource.NsiDetailList;
import org.onap.usecaseui.server.bean.nsmf.resource.NsiRelatedNssiInfo;
import org.onap.usecaseui.server.bean.nsmf.resource.NsiServiceInstanceList;
import org.onap.usecaseui.server.bean.nsmf.resource.NssiServiceInstanceList;
import org.onap.usecaseui.server.bean.nsmf.resource.ServiceOperationProgress;
import org.onap.usecaseui.server.bean.nsmf.resource.ServiceOperationResult;
import org.onap.usecaseui.server.bean.nsmf.resource.SlicingBusinessDetails;
import org.onap.usecaseui.server.bean.nsmf.resource.SlicingBusinessInfo;
import org.onap.usecaseui.server.bean.nsmf.resource.SlicingBusinessList;
import org.onap.usecaseui.server.bean.nsmf.resource.SubscriberInfo;
import org.onap.usecaseui.server.constant.CommonConstant;
import org.onap.usecaseui.server.constant.nsmf.NsmfCodeConstant;
import org.onap.usecaseui.server.constant.nsmf.NsmfParamConstant;
import org.onap.usecaseui.server.service.lcm.ServiceLcmService;
import org.onap.usecaseui.server.service.nsmf.ResourceMgtService;
import org.onap.usecaseui.server.service.slicingdomain.aai.AAISliceService;
import org.onap.usecaseui.server.service.slicingdomain.aai.bean.AAIServiceAndInstance;
import org.onap.usecaseui.server.service.slicingdomain.aai.bean.AAIServiceInstance;
import org.onap.usecaseui.server.service.slicingdomain.aai.bean.AAIServiceRsp;
import org.onap.usecaseui.server.service.slicingdomain.aai.bean.connection.EndPointInfoList;
import org.onap.usecaseui.server.service.slicingdomain.aai.bean.connection.Relationship;
import org.onap.usecaseui.server.service.slicingdomain.aai.bean.connection.RelationshipData;
import org.onap.usecaseui.server.bean.nsmf.resource.ConnectionLinkInfo;
import org.onap.usecaseui.server.service.slicingdomain.aai.bean.connection.SliceProfileInfo;
import org.onap.usecaseui.server.service.slicingdomain.aai.bean.connection.SliceProfileList;
import org.onap.usecaseui.server.service.slicingdomain.so.SOSliceService;
import org.onap.usecaseui.server.service.slicingdomain.so.bean.ActivateService;
import org.onap.usecaseui.server.service.slicingdomain.so.bean.AnSliceTaskInfo;
import org.onap.usecaseui.server.service.slicingdomain.so.bean.CnSliceTaskInfo;
import org.onap.usecaseui.server.service.slicingdomain.so.bean.SOOperation;
import org.onap.usecaseui.server.service.slicingdomain.so.bean.TnBHSliceTaskInfo;
import org.onap.usecaseui.server.util.DateUtils;
import org.onap.usecaseui.server.util.RestfulServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import retrofit2.Response;

import javax.annotation.Resource;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

@Service("ResourceMgtService")
@org.springframework.context.annotation.Configuration
@EnableAspectJAutoProxy
public class ResourceMgtServiceImpl implements ResourceMgtService {

    private static final Logger logger = LoggerFactory.getLogger(ResourceMgtServiceImpl.class);
    @Resource(name = "ServiceLcmService")
    private ServiceLcmService serviceLcmService;

    @Resource(name = "GeneralConvertService")
    private GeneralConvertImpl generalConvert;

    @Resource(name = "ResourceMgtConvertService")
    private ResourceMgtServiceConvert resourceMgtServiceConvert;

    private AAISliceService aaiSliceService;
    private SOSliceService soSliceService;
    Gson gson = new Gson();

    public ResourceMgtServiceImpl() {
        this(RestfulServices.create(AAISliceService.class), RestfulServices.create(SOSliceService.class));
    }

    public ResourceMgtServiceImpl(AAISliceService aaiSliceService, SOSliceService soSliceService) {
        this.aaiSliceService = aaiSliceService;
        this.soSliceService = soSliceService;
    }

    @Override
    public ServiceResult querySlicingBusiness(int pageNo, int pageSize) {
        ServiceResult serviceResult = new ServiceResult();
        ResultHeader resultHeader = new ResultHeader();

        SlicingBusinessList slicingBusinessList = new SlicingBusinessList();
        AAIServiceRsp aAIServiceRsp = new AAIServiceRsp();
        String resultMsg;

        try {
            Response<JSONObject> response = this.aaiSliceService
                .listService(NsmfParamConstant.CUSTOM_5G, NsmfParamConstant.SERVICE_TYPE_5G).execute();
            if (response.isSuccessful()) {
                logger.info("querySlicingBusiness: listService reponse is:{}", response.body());
                Type type = new TypeToken<List<AAIServiceInstance>>() {
                }.getType();

                JSONArray array = response.body().getJSONArray("service-instance");
                resourceMgtServiceConvert.buildAAIServiceRsp(aAIServiceRsp, array);
                resourceMgtServiceConvert.convertSlicingBusinessList(slicingBusinessList,
                    aAIServiceRsp, pageNo, pageSize);
                addBusinessProgress(slicingBusinessList);
                // return normal result code
                resultMsg = "5G slicing business instances query result.";
                resultHeader.setResult_code(NsmfCodeConstant.SUCCESS_CODE);
            } else {
                logger.error(String.format("querySlicingBusiness: Can not get slicingBusinessList[code={}, message={}]",
                    response.code(),
                    response.message()));
                resultMsg = "5G slicing task result query failed!";
                if (response.code() == NsmfCodeConstant.RESOURCE_NOT_FOUND_CODE){
                    resultHeader.setResult_code(NsmfCodeConstant.SUCCESS_CODE);
                }else {
                    resultHeader.setResult_code(NsmfCodeConstant.ERROR_CODE_UNKNOWN);
                }
            }
        } catch (Exception e) {
            resultMsg = "5G slicing business instances query failed. Unknown exception occurred!";
            resultHeader.setResult_code(NsmfCodeConstant.ERROR_CODE_UNKNOWN);
            logger.error("Exception in querySlicingBusiness response",e);
        }

        logger.info(resultMsg);
        logger.info("querySlicingBusiness: 5G slicing business instances query has been finished.");
        resultHeader.setResult_message(resultMsg);
        serviceResult.setResult_header(resultHeader);
        serviceResult.setResult_body(slicingBusinessList);
        return serviceResult;
    }

    public void addBusinessProgress(SlicingBusinessList slicingBusinessList)  {
        if (slicingBusinessList.getSlicingBusinessInfoList() == null
            || slicingBusinessList.getSlicingBusinessInfoList().size() == 0) {
            logger.error(
                "addBusinessProgress: slicingBusinessList.getSlicingBusinessInfoList() is null or slicingBusinessList.getSlicingBusinessInfoList() size is 0.");
            return;
        }

        for (SlicingBusinessInfo slicingBusinessInfo : slicingBusinessList.getSlicingBusinessInfoList()) {
            String businessId = slicingBusinessInfo.getServiceInstanceId();
            ServiceInstanceOperations serviceInstanceOperations = serviceLcmService
                .getServiceInstanceOperationById(businessId);
            if (null == serviceInstanceOperations || serviceInstanceOperations.getOperationId() == null) {
                logger.error(
                    "addBusinessProgress: null == serviceInstanceOperations for businessId:{}.",
                    businessId);
                continue;
            }
            String operationId = serviceInstanceOperations.getOperationId();
            Response<SOOperation> response = null;
            try {
                response = this.soSliceService.queryOperationProgress(businessId, operationId)
                    .execute();
                if (response.isSuccessful()) {
                    SOOperation soOperation = response.body();
                    Gson gson = new Gson();
                    logger.info("addBusinessProgress: queryOperationProgress reponse is:{}",
                        gson.toJson(soOperation));
                    if (soOperation == null || soOperation.getOperation() == null) {
                        logger.error("addBusinessProgress: soOperation is null or getOperation() is null for businessId {}!", businessId);
                        continue;
                    }
                    String operationResult = soOperation.getOperation().getResult();
                    String operationType = soOperation.getOperation().getOperation();
                    int progress = soOperation.getOperation().getProgress();
                    logger.info("addBusinessProgress: operationResult is:{}, operationType is {}, progress is {}",
                        operationResult, operationType, progress);
                    if (operationResult.equals(NsmfCodeConstant.OPERATION_ERROR_STATUS)) {
                        logger.error("addBusinessProgress: progress is ok, but operationResult is error for businessId {}!", businessId);
                        continue;
                    }
                    slicingBusinessInfo.setLastOperationType(operationType);
                    slicingBusinessInfo.setLastOperationProgress(String.valueOf(progress));
                }
            } catch (IOException e) {
                logger.error(e.getMessage());
                logger.error("addBusinessProgress: catch an IOException for businessId {}!", businessId);
                continue;
            }
        }
    }

    @Override
    public ServiceResult querySlicingBusinessByStatus(String processingStatus, int pageNo, int pageSize) {
        ServiceResult serviceResult = new ServiceResult();
        ResultHeader resultHeader = new ResultHeader();

        SlicingBusinessList slicingBusinessList = new SlicingBusinessList();
        String resultMsg;
        AAIServiceRsp aAIServiceRsp = new AAIServiceRsp();
        try {
            Response<JSONObject> response = this.aaiSliceService.listServiceByStatus(
                NsmfParamConstant.CUSTOM_5G, NsmfParamConstant.SERVICE_TYPE_5G, processingStatus).execute();
            if (response.isSuccessful()) {
                logger.info("querySlicingBusinessByStatus: listServiceByStatus reponse is:{}", response.body());
                JSONArray array = response.body().getJSONArray("service-instance");
                resourceMgtServiceConvert.buildAAIServiceRsp(aAIServiceRsp, array);
                resourceMgtServiceConvert.convertSlicingBusinessList(slicingBusinessList,
                    aAIServiceRsp, pageNo, pageSize);
                addBusinessProgress(slicingBusinessList);
                // return normal result code
                resultMsg = "5G slicing business instances query based on status result.";
                resultHeader.setResult_code(NsmfCodeConstant.SUCCESS_CODE);
            } else {
                logger.error(String
                    .format("querySlicingBusinessByStatus: Can not get slicingBusinessList[code={}, message={}]",
                        response.code(),
                        response.message()));
                resultMsg = "5G slicing task result query based on status failed!";
                if (response.code() == NsmfCodeConstant.RESOURCE_NOT_FOUND_CODE){
                    resultHeader.setResult_code(NsmfCodeConstant.SUCCESS_CODE);
                }else {
                    resultHeader.setResult_code(NsmfCodeConstant.ERROR_CODE_UNKNOWN);
                }
            }
        } catch (Exception e) {
            resultMsg = "5G slicing business instances query based on status failed. Unknown exception occurred!";
            resultHeader.setResult_code(NsmfCodeConstant.ERROR_CODE_UNKNOWN);
            logger.error("Exception in querySlicingBusinessByStatus response",e);
        }

        logger.info(resultMsg);
        logger.info(
            "querySlicingBusinessByStatus: 5G slicing business instances query based on status has been finished.");
        resultHeader.setResult_message(resultMsg);
        serviceResult.setResult_header(resultHeader);
        serviceResult.setResult_body(slicingBusinessList);
        return serviceResult;
    }

    @Override
    public ServiceResult querySlicingBusinessDetails(String businessId) {
        ServiceResult serviceResult = new ServiceResult();
        ResultHeader resultHeader = new ResultHeader();
        AAIServiceAndInstance aaiServiceAndInstance = new AAIServiceAndInstance();
        SlicingBusinessDetails slicingBusinessDetails = new SlicingBusinessDetails();
        CnSliceTaskInfoToUI cnSliceTaskInfo = new CnSliceTaskInfoToUI();
        TnBHSliceTaskInfoToUI tnBHSliceTaskInfo = new TnBHSliceTaskInfoToUI();
        AnSliceTaskInfoToUI anSliceTaskInfo = new AnSliceTaskInfoToUI();
        ConnectionLinkInfo connectionLinkInfo = new ConnectionLinkInfo();
        String resultMsg;

        try {
            Response<JSONObject> response = this.aaiSliceService
                .listServiceById(NsmfParamConstant.CUSTOM_5G, NsmfParamConstant.SERVICE_TYPE_5G, businessId)
                .execute();
            if (response.isSuccessful()) {
                logger.info("querySlicingBusinessDetails: listServiceById reponse is:{}", response.body());
                JSONObject object = response.body();
                aaiServiceAndInstance = generalConvert.listServiceByIdUtil(object);
                resourceMgtServiceConvert.convertBusinessDetails(businessId,
                    slicingBusinessDetails, aaiServiceAndInstance);
                resultMsg = "5G slicing business details query result.";
                resultHeader.setResult_code(NsmfCodeConstant.SUCCESS_CODE);
            } else {
                logger.error(String
                    .format("querySlicingBusinessDetails: Can not get AAIServiceInstance[code={}, message={}]",
                        response.code(),
                        response.message()));
                resultMsg = "5G slicing business details query failed!";
                resultHeader.setResult_code(NsmfCodeConstant.ERROR_CODE_UNKNOWN);
            }
            Response<NetworkInfo> networkInfoResponse = this.aaiSliceService
                .getServiceNetworkInstance(businessId).execute();
            if (networkInfoResponse.isSuccessful()) {
                NetworkInfo networkInfo = networkInfoResponse.body();
                logger.info("networkInfo: {}", networkInfo);
                List<Relationship> relationshipList = networkInfo.getRelationshipList().getRelationship();

                for (Relationship relationshipInfo : relationshipList) {
                    List<RelationshipData> relationshipDataList = relationshipInfo.getRelationshipDataList();
                    List<RelationshipData> serviceInstanceInfo = relationshipDataList.stream()
                        .filter(e -> e.getRelationshipKey()
                            .equals("service-instance.service-instance-id")).collect(Collectors.toList());
                    String networkRouteCnId = serviceInstanceInfo.get(0).getRelationshipValue();
                    Response<NetworkInfo> networkInfoResponse1 = this.aaiSliceService
                        .getServiceNetworkInstance(networkRouteCnId).execute();
                    if (networkInfoResponse1.isSuccessful()) {
                        NetworkInfo networkInfo1 = networkInfoResponse1.body();
                        String networkType = networkInfo1.getWorkloadContext();
                        if (networkType.equals("AN") || networkType.equals("CN")) {
                            List<Relationship> networkInfoRelationship = networkInfo1.getRelationshipList()
                                .getRelationship();
                            List<Relationship> networkRouteCn = networkInfoRelationship.stream()
                                .filter(e -> e.getRelatedTo()
                                    .equals("network-route")).collect(Collectors.toList());
                            List<RelationshipData> networkInfoRelationshipDataList = networkRouteCn.get(0)
                                .getRelationshipDataList();
                            String networkRouteId = networkInfoRelationshipDataList.get(0).getRelationshipValue();
                            Response<EndPointInfoList> networkInfoEndPointInfoList = this.aaiSliceService
                                .getEndpointByLinkName(networkRouteId).execute();
                            if (networkInfoEndPointInfoList.isSuccessful()) {
                                EndPointInfoList endPointInfoList = networkInfoEndPointInfoList.body();
                                if (networkType.equals("CN")) {
                                    cnSliceTaskInfo.setInterfaceId(endPointInfoList.getLogicId());
                                    cnSliceTaskInfo.setIpAdrress(endPointInfoList.getIpAddress());
                                    cnSliceTaskInfo.setNextHopInfo(endPointInfoList.getNextHop());
                                }
                                if (networkType.equals("AN")) {
                                    anSliceTaskInfo.setInterfaceId(endPointInfoList.getLogicId());
                                    anSliceTaskInfo.setIpAdrress(endPointInfoList.getIpAddress());
                                    anSliceTaskInfo.setNextHopInfo(endPointInfoList.getNextHop());
                                }
                            } else {
                                logger.error(String.format(
                                    "querySlicingBusinessDetails: Can not get getEndpointByLinkName[code={}, message={}]",
                                    response.code(),
                                    response.message()));
                                resultMsg = "5G slicing business details query failed!";
                                resultHeader.setResult_code(NsmfCodeConstant.ERROR_CODE_UNKNOWN);
                            }
                        }
                        if (networkType.equals("TN_BH")) {
                            List<RelationshipData> tnRelationshipDataList = relationshipInfo.getRelationshipDataList();
                            List<RelationshipData> tnServiceInstanceInfo = tnRelationshipDataList.stream()
                                .filter(e -> e.getRelationshipKey()
                                    .equals("service-instance.service-instance-id")).collect(Collectors.toList());

                            Response<SliceProfileList> executeTn = this.aaiSliceService
                                .getSliceProfiles(tnServiceInstanceInfo.get(0).getRelationshipValue()).execute();
                            if (executeTn.isSuccessful()) {
                                SliceProfileList body = executeTn.body();
                                SliceProfileInfo sliceProfileInfo = body.getSliceProfileInfoList().get(0);
                                tnBHSliceTaskInfo.setLantency(sliceProfileInfo.getLatency());
                                tnBHSliceTaskInfo.setMaxBandWidth(sliceProfileInfo.getMaxBandwidth());
                                tnBHSliceTaskInfo.setLinkType("P2P");
                            } else {
                                logger.error(String.format(
                                    "querySlicingBusinessDetails: Can not getSliceProfiles [code={}, message={}]",
                                    response.code(),
                                    response.message()));
                                resultMsg = "5G slicing business details query failed!";
                                resultHeader.setResult_code(NsmfCodeConstant.ERROR_CODE_UNKNOWN);
                            }
                        }
                    } else {
                        logger.error(String.format(
                            "querySlicingBusinessDetails: Can not get getServiceNetworkInstance [code={}, message={}]",
                            response.code(),
                            response.message()));
                        resultMsg = "5G slicing business details query failed!";
                        resultHeader.setResult_code(NsmfCodeConstant.ERROR_CODE_UNKNOWN);
                    }
                }
            } else {
                logger.error(String.format(
                    "querySlicingBusinessDetails: Can not init getServiceNetworkInstance [code={}, message={}]",
                    response.code(),
                    response.message()));
                resultMsg = "5G slicing service operation progress query failed!";
                resultHeader.setResult_code(NsmfCodeConstant.ERROR_CODE_UNKNOWN);
            }
        } catch (Exception e) {
            resultMsg = "5G slicing business details query failed. Unknown exception occurred!";
            resultHeader.setResult_code(NsmfCodeConstant.ERROR_CODE_UNKNOWN);
            logger.error("Exception in querySlicingBusinessDetails response",e);
        }
        connectionLinkInfo.setCnSliceTaskInfoToUI(cnSliceTaskInfo);
        connectionLinkInfo.setTnBHSliceTaskInfoToUI(tnBHSliceTaskInfo);
        connectionLinkInfo.setAnSliceTaskInfoToUI(anSliceTaskInfo);
        slicingBusinessDetails.setConnectionLinkInfo(connectionLinkInfo);

        logger.info(resultMsg);
        logger.info("querySlicingBusinessDetails: 5G slicing business details query has been finished.");
        resultHeader.setResult_message(resultMsg);
        serviceResult.setResult_header(resultHeader);
        serviceResult.setResult_body(slicingBusinessDetails);
        return serviceResult;
    }

    @Override
    public ServiceResult queryNsiInstances(int pageNo, int pageSize) {
        ServiceResult serviceResult = new ServiceResult();
        ResultHeader resultHeader = new ResultHeader();

        NsiServiceInstanceList nsiServiceInstanceList = new NsiServiceInstanceList();
        AAIServiceRsp aAIServiceRsp = new AAIServiceRsp();

        String resultMsg;
        try {
            Response<JSONObject> response = this.aaiSliceService
                .listServiceNSI(NsmfParamConstant.CUSTOM_5G, NsmfParamConstant.SERVICE_TYPE_5G).execute();
            if (response.isSuccessful()) {
                logger.info("queryNsiInstances: listServiceNSI reponse is:{}", response.body());
                JSONArray array = response.body().getJSONArray("service-instance");
                resourceMgtServiceConvert.buildAAIServiceRsp(aAIServiceRsp, array);
                resourceMgtServiceConvert.convertHostedNsiList(nsiServiceInstanceList, aAIServiceRsp,
                    pageNo, pageSize);
                resultMsg = "5G slicing NSI service instances query result.";
                resultHeader.setResult_code(NsmfCodeConstant.SUCCESS_CODE);
            } else {
                logger.error(String.format("queryNsiInstances: Can not get AAIServiceInstanceRsp[code={}, message={}]",
                    response.code(),
                    response.message()));
                resultMsg = "5G slicing NSI service instances query failed!";
                if (response.code() == NsmfCodeConstant.RESOURCE_NOT_FOUND_CODE){
                    resultHeader.setResult_code(NsmfCodeConstant.SUCCESS_CODE);
                }else {
                    resultHeader.setResult_code(NsmfCodeConstant.ERROR_CODE_UNKNOWN);
                }
            }
        } catch (Exception e) {
            resultMsg = "5G slicing NSI service instances query failed. Unknown exception occurred!";
            resultHeader.setResult_code(NsmfCodeConstant.ERROR_CODE_UNKNOWN);
            logger.error("Exception in queryNsiInstances response",e);
        }

        logger.info(resultMsg);
        logger.info("queryNsiInstances: 5G slicing NSI service instances query has been finished.");
        resultHeader.setResult_message(resultMsg);
        serviceResult.setResult_header(resultHeader);
        serviceResult.setResult_body(nsiServiceInstanceList);
        return serviceResult;
    }

    @Override
    public ServiceResult queryNsiInstancesByStatus(String instanceStatus, int pageNo, int pageSize) {
        ServiceResult serviceResult = new ServiceResult();
        ResultHeader resultHeader = new ResultHeader();

        NsiServiceInstanceList nsiServiceInstanceList = new NsiServiceInstanceList();
        AAIServiceRsp aAIServiceRsp = new AAIServiceRsp();
        String resultMsg;

        try {
            Response<JSONObject> response = this.aaiSliceService.listServiceNSIByStatus(
                NsmfParamConstant.CUSTOM_5G, NsmfParamConstant.SERVICE_TYPE_5G, instanceStatus).execute();
            if (response.isSuccessful()) {
                logger.info("queryNsiInstancesByStatus: listServiceNSIByStatus reponse is:{}", response.body());
                JSONArray array = response.body().getJSONArray("service-instance");
                resourceMgtServiceConvert.buildAAIServiceRsp(aAIServiceRsp, array);
                resourceMgtServiceConvert.convertHostedNsiList(nsiServiceInstanceList, aAIServiceRsp,
                    pageNo, pageSize);
                resultMsg = "5G slicing NSI service instances based on status result.";
                resultHeader.setResult_code(NsmfCodeConstant.SUCCESS_CODE);
            } else {
                logger.error(String
                    .format("queryNsiInstancesByStatus: Can not get AAIServiceInstanceRsp[code={}, message={}]",
                        response.code(),
                        response.message()));
                resultMsg = "5G slicing NSI service instances based on status query failed!";
                if (response.code() == NsmfCodeConstant.RESOURCE_NOT_FOUND_CODE){
                    resultHeader.setResult_code(NsmfCodeConstant.SUCCESS_CODE);
                }else {
                    resultHeader.setResult_code(NsmfCodeConstant.ERROR_CODE_UNKNOWN);
                }
            }
        } catch (Exception e) {
            resultMsg = "5G slicing NSI service instances based on status query failed. Unknown exception occurred!";
            resultHeader.setResult_code(NsmfCodeConstant.ERROR_CODE_UNKNOWN);
            logger.error("Exception in queryNsiInstancesByStatus response",e);
        }

        logger.info(resultMsg);
        logger.info(
            "queryNsiInstancesByStatus: 5G slicing NSI service instances based on status query has been finished.");
        resultHeader.setResult_message(resultMsg);
        serviceResult.setResult_header(resultHeader);
        serviceResult.setResult_body(nsiServiceInstanceList);
        return serviceResult;
    }


    @Override
    public ServiceResult queryNsiDetails(String serviceInstanceId) {
        ServiceResult serviceResult = new ServiceResult();
        ResultHeader resultHeader = new ResultHeader();

        NsiDetailList nsiDetailList = new NsiDetailList();
        String resultMsg;

        try {
            Response<JSONObject> response = this.aaiSliceService.querySerAndSubInsByNSI(NsmfParamConstant.CUSTOM_5G,
                NsmfParamConstant.SERVICE_TYPE_5G, serviceInstanceId).execute();
            if (response.isSuccessful()) {
                JSONObject object = response.body();
                logger.info("queryNsiDetails: querySerAndSubInsByNSI reponse is:{}", response.body());
                AAIServiceAndInstance aaiServiceAndInstance = resourceMgtServiceConvert.queryNsiDetailsUtils(object);
                resourceMgtServiceConvert.convertNsiDetailList(nsiDetailList,
                    aaiServiceAndInstance);
                resultMsg = "5G slicing business details query result.";
                resultHeader.setResult_code(NsmfCodeConstant.SUCCESS_CODE);
            } else {
                logger.error(String
                    .format("queryNsiDetails: Can not get AAIServiceAndInstance[code={}, message={}]", response.code(),
                        response.message()));
                resultMsg = "5G slicing business details query failed!";
                resultHeader.setResult_code(NsmfCodeConstant.ERROR_CODE_UNKNOWN);
            }
        } catch (Exception e) {
            resultMsg = "5G slicing business details query failed. Unknown exception occurred!";
            resultHeader.setResult_code(NsmfCodeConstant.ERROR_CODE_UNKNOWN);
            logger.error("Exception in queryNsiDetails response",e);
        }

        logger.info(resultMsg);
        logger.info("queryNsiDetails: 5G slicing business details query has been finished.");
        resultHeader.setResult_message(resultMsg);
        serviceResult.setResult_header(resultHeader);
        serviceResult.setResult_body(nsiDetailList);
        return serviceResult;
    }

    @Override
    public ServiceResult queryNsiRelatedNssiInfo(String nsiId) {
        ServiceResult serviceResult = new ServiceResult();
        ResultHeader resultHeader = new ResultHeader();

        NsiRelatedNssiInfo nsiRelatedNssiInfo = new NsiRelatedNssiInfo();
        String resultMsg;
        try {
            Response<JSONObject> response = this.aaiSliceService
                .querySerAndSubInsByNSI(NsmfParamConstant.CUSTOM_5G, NsmfParamConstant.SERVICE_TYPE_5G, nsiId)
                .execute();
            if (response.isSuccessful()) {
                JSONObject object = response.body();
                logger.info("queryNsiRelatedNssiInfo: querySerAndSubInsByNSI reponse is:{}", response.body());
                AAIServiceAndInstance aaiServiceAndInstance = resourceMgtServiceConvert.queryNsiDetailsUtils(object);
                resourceMgtServiceConvert.convertNsiRelatedNssiInfo(nsiRelatedNssiInfo,
                    aaiServiceAndInstance);
                resultMsg = "NSSI associated with NSI result.";
                resultHeader.setResult_code(NsmfCodeConstant.SUCCESS_CODE);
            } else {
                logger.error(String
                    .format("queryNsiRelatedNssiInfo: Can not get AAIServiceAndInstance[code={}, message={}]",
                        response.code(),
                        response.message()));
                resultMsg = "NSSI associated with NSI result query failed!";
                resultHeader.setResult_code(NsmfCodeConstant.ERROR_CODE_UNKNOWN);
            }
        } catch (Exception e) {
            resultMsg = "NSSI associated with NSI query failed. Unknown exception occurred!";
            resultHeader.setResult_code(NsmfCodeConstant.ERROR_CODE_UNKNOWN);
            logger.error("Exception in queryNsiRelatedNssiInfo response",e);
        }

        logger.info(resultMsg);
        logger.info("queryNsiRelatedNssiInfo: NSSI associated with NSI query has been finished.");
        resultHeader.setResult_message(resultMsg);
        serviceResult.setResult_header(resultHeader);
        serviceResult.setResult_body(nsiRelatedNssiInfo);
        return serviceResult;
    }

    @Override
    public ServiceResult queryNssiInstances(int pageNo, int pageSize) {
        ServiceResult serviceResult = new ServiceResult();
        ResultHeader resultHeader = new ResultHeader();

        NssiServiceInstanceList nssiServiceInstanceList = new NssiServiceInstanceList();
        String resultMsg;
        AAIServiceRsp aAIServiceRsp = new AAIServiceRsp();
        try {
            Response<JSONObject> response = this.aaiSliceService
                .listServiceNSSI(NsmfParamConstant.CUSTOM_5G, NsmfParamConstant.SERVICE_TYPE_5G).execute();
            if (response.isSuccessful()) {
                logger.info("queryNssiInstances: listServiceNSSI reponse is:{}", response.body());
                JSONArray array = response.body().getJSONArray("service-instance");
                resourceMgtServiceConvert.buildAAIServiceRsp(aAIServiceRsp, array);
                resourceMgtServiceConvert
                    .convertNssiServiceInstanceList(nssiServiceInstanceList, aAIServiceRsp, pageNo, pageSize);
                resultMsg = "5G slicing NSI service instances query result.";
                resultHeader.setResult_code(NsmfCodeConstant.SUCCESS_CODE);
            } else {
                logger.error(String.format("queryNssiInstances: Can not get AAIServiceInstanceRsp[code={}, message={}]",
                    response.code(),
                    response.message()));
                resultMsg = "5G slicing NSSI service instances result query failed!";
                if (response.code() == NsmfCodeConstant.RESOURCE_NOT_FOUND_CODE){
                    resultHeader.setResult_code(NsmfCodeConstant.SUCCESS_CODE);
                }else {
                    resultHeader.setResult_code(NsmfCodeConstant.ERROR_CODE_UNKNOWN);
                }
            }

        } catch (Exception e) {
            resultMsg = "5G slicing NSSI service instances query failed. Unknown exception occurred!";
            resultHeader.setResult_code(NsmfCodeConstant.ERROR_CODE_UNKNOWN);
            logger.error("Exception in queryNssiInstances response",e);
        }

        logger.info(resultMsg);
        logger.info("queryNssiInstances: 5G slicing NSSI service instances query has been finished.");
        resultHeader.setResult_message(resultMsg);
        serviceResult.setResult_header(resultHeader);
        serviceResult.setResult_body(nssiServiceInstanceList);
        return serviceResult;
    }

    @Override
    public ServiceResult queryNssiInstancesByStatus(String instanceStatus, int pageNo, int pageSize) {
        ServiceResult serviceResult = new ServiceResult();
        ResultHeader resultHeader = new ResultHeader();

        NssiServiceInstanceList nssiServiceInstanceList = new NssiServiceInstanceList();
        String resultMsg;
        AAIServiceRsp aAIServiceRsp = new AAIServiceRsp();
        try {
            Response<JSONObject> response = this.aaiSliceService.listServiceNSSIByStatus(
                NsmfParamConstant.CUSTOM_5G, NsmfParamConstant.SERVICE_TYPE_5G, instanceStatus).execute();
            if (response.isSuccessful()) {
                logger.info("queryNssiInstancesByStatus: listServiceNSSIByStatus reponse is:{}", response.body());
                JSONArray array = response.body().getJSONArray("service-instance");
                resourceMgtServiceConvert.buildAAIServiceRsp(aAIServiceRsp, array);
                resourceMgtServiceConvert
                    .convertNssiServiceInstanceList(nssiServiceInstanceList, aAIServiceRsp, pageNo, pageSize);
                // return normal result code
                resultMsg = "5G slicing NSSI service instances based on status result.";
                resultHeader.setResult_code(NsmfCodeConstant.SUCCESS_CODE);
            } else {
                logger.error(String
                    .format("queryNssiInstancesByStatus: Can not get AAIServiceInstanceRsp[code={}, message={}]",
                        response.code(),
                        response.message()));
                resultMsg = "5G slicing NSSI service instances based on status query failed!";
                if (response.code() == NsmfCodeConstant.RESOURCE_NOT_FOUND_CODE){
                    resultHeader.setResult_code(NsmfCodeConstant.SUCCESS_CODE);
                }else {
                    resultHeader.setResult_code(NsmfCodeConstant.ERROR_CODE_UNKNOWN);
                }
            }
        } catch (Exception e) {
            resultMsg = "5G slicing NSSI service instances based on status query failed. Unknown exception occurred!";
            resultHeader.setResult_code(NsmfCodeConstant.ERROR_CODE_UNKNOWN);
            logger.error("Exception in queryNssiInstancesByStatus response",e);
        }

        logger.info(resultMsg);
        logger.info(
            "queryNssiInstancesByStatus: 5G slicing NSSI service instances based on status query has been finished.");
        resultHeader.setResult_message(resultMsg);
        serviceResult.setResult_header(resultHeader);
        serviceResult.setResult_body(nssiServiceInstanceList);
        return serviceResult;
    }

    @Override
    public ServiceResult queryNssiInstancesByEnvironment(String environmentContext, int pageNo, int pageSize) {
        ServiceResult serviceResult = new ServiceResult();
        ResultHeader resultHeader = new ResultHeader();

        NssiServiceInstanceList nssiServiceInstanceList = new NssiServiceInstanceList();
        String resultMsg;
        AAIServiceRsp aAIServiceRsp = new AAIServiceRsp();
        try {
            Response<JSONObject> response = this.aaiSliceService.listServiceNSSIByEnv(NsmfParamConstant.CUSTOM_5G,
                NsmfParamConstant.SERVICE_TYPE_5G, environmentContext).execute();
            if (response.isSuccessful()) {
                logger.info("queryNssiInstancesByEnvironment: listServiceNSSIByEnv reponse is:{}", response.body());
                JSONArray array = response.body().getJSONArray("service-instance");
                resourceMgtServiceConvert.buildAAIServiceRsp(aAIServiceRsp, array);
                resourceMgtServiceConvert
                    .convertNssiServiceInstanceList(nssiServiceInstanceList, aAIServiceRsp, pageNo, pageSize);
                resultMsg = "5G slicing NSSI query based on environment context result.";
                resultHeader.setResult_code(NsmfCodeConstant.SUCCESS_CODE);
            } else {
                logger.error(String
                    .format("queryNssiInstancesByEnvironment: Can not get AAIServiceInstanceRsp[code={}, message={}]",
                        response.code(),
                        response.message()));
                resultMsg = "5G slicing NSSI service instances based on environment query failed!";
                if (response.code() == NsmfCodeConstant.RESOURCE_NOT_FOUND_CODE){
                    resultHeader.setResult_code(NsmfCodeConstant.SUCCESS_CODE);
                }else {
                    resultHeader.setResult_code(NsmfCodeConstant.ERROR_CODE_UNKNOWN);
                }
            }
        } catch (Exception e) {
            resultMsg = "5G slicing NSSI service instances based on environment query failed. Unknown exception occurred!";
            resultHeader.setResult_code(NsmfCodeConstant.ERROR_CODE_UNKNOWN);
            logger.error("Exception in queryNssiInstancesByEnvironment response",e);
        }

        logger.info(resultMsg);
        logger.info(
            "queryNssiInstancesByEnvironment: 5G slicing NSSI service instances based on environment query has been finished.");
        resultHeader.setResult_message(resultMsg);
        serviceResult.setResult_header(resultHeader);
        serviceResult.setResult_body(nssiServiceInstanceList);
        return serviceResult;
    }

    @Override
    public ServiceResult queryNssiDetails(String nssiId) {
        ServiceResult serviceResult = new ServiceResult();
        ResultHeader resultHeader = new ResultHeader();
        AAIServiceAndInstance aaiServiceAndInstance = new AAIServiceAndInstance();
        HostedNsiList hostedNsiList = new HostedNsiList();
        String resultMsg;

        try {
            Response<JSONObject> response = this.aaiSliceService
                .queryNSIByNSSI(NsmfParamConstant.CUSTOM_5G, NsmfParamConstant.SERVICE_TYPE_5G, nssiId).execute();
            if (response.isSuccessful()) {
                logger.info("queryNssiDetails: queryNSIByNSSI reponse is:{}", response.body());
                JSONObject object = response.body();
                aaiServiceAndInstance = generalConvert.queryServiceUtil(object);
                resourceMgtServiceConvert.convertNssiDetails(hostedNsiList, aaiServiceAndInstance);
                resultMsg = "5G slicing service nssi details query result.";
                resultHeader.setResult_code(NsmfCodeConstant.SUCCESS_CODE);
            } else {
                logger.error(String
                    .format("queryNssiDetails: Can not get AAIServiceAndInstance[code={}, message={}]", response.code(),
                        response.message()));
                resultMsg = "5G slicing service nssi details query failed!";
                resultHeader.setResult_code(NsmfCodeConstant.ERROR_CODE_UNKNOWN);
            }
        } catch (Exception e) {
            resultMsg = "5G slicing service nssi details query failed. Unknown exception occurred!";
            resultHeader.setResult_code(NsmfCodeConstant.ERROR_CODE_UNKNOWN);
            logger.error("Exception in queryNssiDetails response",e);
        }

        logger.info(resultMsg);
        logger.info("queryNssiDetails: 5G slicing service nssi details query has been finished.");
        resultHeader.setResult_message(resultMsg);
        serviceResult.setResult_header(resultHeader);
        serviceResult.setResult_body(hostedNsiList);
        return serviceResult;
    }

    @Override
    public ServiceResult activateSlicingService(String serviceId) {
        ServiceResult serviceResult = new ServiceResult();
        ResultHeader resultHeader = new ResultHeader();
        ServiceOperationResult serviceActiveResult = new ServiceOperationResult();
        String resultMsg;

        try {
            SubscriberInfo subscriberInfo = resourceMgtServiceConvert.buildSubscriberInfo(NsmfParamConstant.CUSTOM_5G,
                NsmfParamConstant.SERVICE_TYPE_5G);
            String jsonstr = JSON.toJSONString(subscriberInfo);
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonstr);
            Response<ActivateService> activeResponse = this.soSliceService.activeService(serviceId, requestBody)
                .execute();

            if (activeResponse.isSuccessful()) {
                ActivateService activateService = activeResponse.body();
                logger.info("activateSlicingService: activeService reponse is:{}",
                    gson.toJson(activateService));
                String operationId = activateService.getOperationId();
                serviceActiveResult.setOperationId(operationId);
                ServiceInstanceOperations serviceOpera = new ServiceInstanceOperations(serviceId, operationId,
                    NsmfCodeConstant.ACTIVATE_TYPE, "0", CommonConstant.IN_PROGRESS_CODE,
                    DateUtils.dateToString(DateUtils.now()), null);
                serviceLcmService.saveOrUpdateServiceInstanceOperation(serviceOpera);
                resultMsg = "5G slicing service activated normally.";
                resultHeader.setResult_code(NsmfCodeConstant.SUCCESS_CODE);
            } else {
                logger.error(String.format("activateSlicingService: Can not get ActivateService[code={}, message={}]",
                    activeResponse.code(),
                    activeResponse.message()));
                resultMsg = "5G slicing service activated failed!";
                resultHeader.setResult_code(NsmfCodeConstant.ERROR_CODE_UNKNOWN);
            }
        } catch (Exception e) {
            resultMsg = "5G slicing service activation failed. Unknown exception occurred!";
            resultHeader.setResult_code(NsmfCodeConstant.ERROR_CODE_UNKNOWN);
            logger.error("Exception in activateSlicingService response",e);
        }

        logger.info(resultMsg);
        logger.info("activateSlicingService: 5G slicing service activation has been finished.");
        resultHeader.setResult_message(resultMsg);
        serviceResult.setResult_header(resultHeader);
        serviceResult.setResult_body(serviceActiveResult);
        return serviceResult;
    }

    @Override
    public ServiceResult deactivateSlicingService(String serviceId) {
        ServiceResult serviceResult = new ServiceResult();
        ResultHeader resultHeader = new ResultHeader();
        ServiceOperationResult serviceDeactivateResult = new ServiceOperationResult();
        String resultMsg;

        try {
            SubscriberInfo subscriberInfo = resourceMgtServiceConvert.buildSubscriberInfo(NsmfParamConstant.CUSTOM_5G,
                NsmfParamConstant.SERVICE_TYPE_5G);
            String jsonstr = JSON.toJSONString(subscriberInfo);
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonstr.toString());
            Response<ActivateService> activeResponse = this.soSliceService.deactiveService(serviceId, requestBody)
                .execute();

            if (activeResponse.isSuccessful()) {
                ActivateService activateService = activeResponse.body();
                logger.info("deactivateSlicingService: deactiveService reponse is:{}",
                    gson.toJson(activateService));
                String operationId = activateService.getOperationId();
                serviceDeactivateResult.setOperationId(operationId);
                ServiceInstanceOperations serviceOpera = new ServiceInstanceOperations(serviceId, operationId,
                    NsmfCodeConstant.DEACTIVATE_TYPE, "0", CommonConstant.IN_PROGRESS_CODE,
                    DateUtils.dateToString(DateUtils.now()), null);
                serviceLcmService.saveOrUpdateServiceInstanceOperation(serviceOpera);
                resultMsg = "5G slicing service deactivated normally.";
                resultHeader.setResult_code(NsmfCodeConstant.SUCCESS_CODE);
            } else {
                logger.error(String.format("deactivateSlicingService: Can not get ActivateService[code={}, message={}]",
                    activeResponse.code(),
                    activeResponse.message()));
                resultMsg = "5G slicing service deactivation failed!";
                resultHeader.setResult_code(NsmfCodeConstant.ERROR_CODE_UNKNOWN);
            }
        } catch (Exception e) {
            resultMsg = "5G slicing service deactivation failed. Unknown exception occurred!";
            resultHeader.setResult_code(NsmfCodeConstant.ERROR_CODE_UNKNOWN);
            logger.error("Exception in deactivateSlicingService response",e);
        }

        logger.info(resultMsg);
        logger.info("deactivateSlicingService: 5G slicing service deactivation has been finished.");
        resultHeader.setResult_message(resultMsg);
        serviceResult.setResult_header(resultHeader);
        serviceResult.setResult_body(serviceDeactivateResult);
        return serviceResult;
    }

    @Override
    public ServiceResult terminateSlicingService(String serviceId) {
        ServiceResult serviceResult = new ServiceResult();
        ResultHeader resultHeader = new ResultHeader();
        ServiceOperationResult serviceTerminateResult = new ServiceOperationResult();
        String resultMsg;

        try {
            SubscriberInfo subscriberInfo = resourceMgtServiceConvert.buildSubscriberInfo(NsmfParamConstant.CUSTOM_5G,
                NsmfParamConstant.SERVICE_TYPE_5G);
            String jsonstr = JSON.toJSONString(subscriberInfo);
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonstr);
            Response<ActivateService> activeResponse = this.soSliceService.terminateService(serviceId, requestBody)
                .execute();

            if (activeResponse.isSuccessful()) {
                ActivateService activateService = activeResponse.body();
                logger.info("terminateSlicingService: terminateService reponse is:{}",
                    gson.toJson(activateService));
                String operationId = activateService.getOperationId();
                serviceTerminateResult.setOperationId(activateService.getOperationId());
                ServiceInstanceOperations serviceOpera = new ServiceInstanceOperations(serviceId, operationId,
                    NsmfCodeConstant.DELETE_TYPE, "0", CommonConstant.IN_PROGRESS_CODE,
                    DateUtils.dateToString(DateUtils.now()), null);
                serviceLcmService.saveOrUpdateServiceInstanceOperation(serviceOpera);
                resultMsg = "5G slicing service terminated normally.";
                resultHeader.setResult_code(NsmfCodeConstant.SUCCESS_CODE);
            } else {
                logger.error(String.format("terminateSlicingService: Can not get ActivateService[code={}, message={}]",
                    activeResponse.code(),
                    activeResponse.message()));
                resultMsg = "5G slicing service termination failed!";
                resultHeader.setResult_code(NsmfCodeConstant.ERROR_CODE_UNKNOWN);
            }
        } catch (Exception e) {
            resultMsg = "5G slicing service termination failed. Unknown exception occurred!";
            resultHeader.setResult_code(NsmfCodeConstant.ERROR_CODE_UNKNOWN);
            logger.error("Exception in terminateSlicingService response",e);
        }

        logger.info(resultMsg);
        logger.info("terminateSlicingService: 5G slicing service termination has been finished.");
        resultHeader.setResult_message(resultMsg);
        serviceResult.setResult_header(resultHeader);
        serviceResult.setResult_body(serviceTerminateResult);
        return serviceResult;
    }

    @Override
    public ServiceResult queryOperationProgress(String serviceId) {
        ServiceResult serviceResult = new ServiceResult();
        ResultHeader resultHeader = new ResultHeader();
        ServiceOperationProgress operationProgress = new ServiceOperationProgress();
        String resultMsg = "";

        try {
            ServiceInstanceOperations serviceInstanceOperations = serviceLcmService
                .getServiceInstanceOperationById(serviceId);
            if (serviceInstanceOperations != null) {
                String operationId = serviceInstanceOperations.getOperationId();
                logger.info("queryOperationProgress: operationId is:{}", operationId);
                Response<SOOperation> response = this.soSliceService.queryOperationProgress(serviceId, operationId)
                    .execute();
                if (response.isSuccessful()) {
                    SOOperation soOperation = response.body();
                    Gson gson = new Gson();
                    logger.info("queryOperationProgress: queryOperationProgress reponse is:{}",
                        gson.toJson(soOperation));
                    if (soOperation == null || soOperation.getOperation() == null
                        || soOperation.getOperation().getOperation() == null) {
                        logger.error("queryOperationProgress: soOperation is null or getOperation() is null!");
                        resultMsg = "5G slicing service operation progress query failed!";
                        resultHeader.setResult_code(NsmfCodeConstant.ERROR_CODE_UNKNOWN);
                    } else {
                        String operationResult = soOperation.getOperation().getResult();
                        String operationType = soOperation.getOperation().getOperation();
                        int progress = soOperation.getOperation().getProgress();
                        logger
                            .info("queryOperationProgress: operationResult is:{}, operationType is {}, progress is {}",
                                operationResult, operationType, String.valueOf(progress));
                        if (operationResult.equals(NsmfCodeConstant.OPERATION_ERROR_STATUS)) {
                            logger.error("queryOperationProgress: operationResult is error");
                            resultMsg = "5G slicing service operation progress query failed!";
                            resultHeader.setResult_code(NsmfCodeConstant.ERROR_CODE_UNKNOWN);
                        } else {
                            // return normal result code
                            operationProgress.setOperationType(operationType);
                            operationProgress.setOperationProgress(String.valueOf(progress));
                            resultMsg = "5G slicing service operation progress.";
                            resultHeader.setResult_code(NsmfCodeConstant.SUCCESS_CODE);
                        }
                    }
                } else {
                    logger.error(String.format("Can not get SOOperation[code={}, message={}]", response.code(),
                        response.message()));
                    resultMsg = "5G slicing service operation progress query failed!";
                    resultHeader.setResult_code(NsmfCodeConstant.ERROR_CODE_UNKNOWN);
                }
            } else {
                logger.error("queryOperationProgress: serviceInstanceOperations is null!");
                resultMsg = "5G slicing service operation progress query failed!";
                resultHeader.setResult_code(NsmfCodeConstant.ERROR_CODE_UNKNOWN);
            }

        } catch (Exception e) {
            resultMsg = "5G slicing service operation progress query failed. Unknown exception occurred!";
            resultHeader.setResult_code(NsmfCodeConstant.ERROR_CODE_UNKNOWN);
            logger.error("Exception in queryOperationProgress response",e);
        }

        logger.info(resultMsg);
        logger.info("5G slicing service operation progress query has been finished.");
        resultHeader.setResult_message(resultMsg);
        serviceResult.setResult_header(resultHeader);
        serviceResult.setResult_body(operationProgress);
        return serviceResult;
    }

}
