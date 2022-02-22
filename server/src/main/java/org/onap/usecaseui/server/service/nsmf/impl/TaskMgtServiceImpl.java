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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import org.apache.commons.beanutils.BeanUtils;
import org.onap.usecaseui.server.bean.nsmf.common.PagedResult;
import org.onap.usecaseui.server.bean.nsmf.common.ResultHeader;
import org.onap.usecaseui.server.bean.nsmf.common.ServiceResult;
import org.onap.usecaseui.server.bean.nsmf.task.SlicingTaskAuditInfo;
import org.onap.usecaseui.server.bean.nsmf.task.SlicingTaskCreationInfo;
import org.onap.usecaseui.server.bean.nsmf.task.SlicingTaskCreationProgress;
import org.onap.usecaseui.server.bean.nsmf.task.SlicingTaskList;
import org.onap.usecaseui.server.constant.nsmf.NsmfCodeConstant;
import org.onap.usecaseui.server.service.nsmf.TaskMgtService;
import org.onap.usecaseui.server.service.slicingdomain.aai.AAISliceService;
import org.onap.usecaseui.server.service.slicingdomain.aai.bean.connection.ConnectionLink;
import org.onap.usecaseui.server.service.slicingdomain.aai.bean.connection.ConnectionLinkList;
import org.onap.usecaseui.server.service.slicingdomain.aai.bean.connection.EndPointInfoList;
import org.onap.usecaseui.server.service.slicingdomain.aai.bean.connection.NetworkPolicy;
import org.onap.usecaseui.server.service.slicingdomain.aai.bean.connection.Relationship;
import org.onap.usecaseui.server.service.slicingdomain.aai.bean.connection.RelationshipData;
import org.onap.usecaseui.server.service.slicingdomain.aai.bean.connectionvo.ConnectionListVo;
import org.onap.usecaseui.server.service.slicingdomain.aai.bean.connectionvo.ConnectionVo;
import org.onap.usecaseui.server.service.slicingdomain.aai.bean.connectionvo.EndPointInfoListVo;
import org.onap.usecaseui.server.service.slicingdomain.aai.bean.connectionvo.PropertiesVo;
import org.onap.usecaseui.server.service.slicingdomain.so.SOSliceService;
import org.onap.usecaseui.server.service.slicingdomain.so.bean.SOTask;
import org.onap.usecaseui.server.service.slicingdomain.so.bean.SOTaskRsp;
import org.onap.usecaseui.server.util.RestfulServices;
import org.onap.usecaseui.server.util.nsmf.NsmfCommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import retrofit2.Response;

import javax.annotation.Resource;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service("TaskMgtService")
@org.springframework.context.annotation.Configuration
@EnableAspectJAutoProxy
public class TaskMgtServiceImpl implements TaskMgtService {

    private static final Logger logger = LoggerFactory.getLogger(TaskMgtServiceImpl.class);

    @Resource(name = "TaskMgtConvertService")
    protected TaskMgtServiceConvert taskMgtServiceConvert;

    private SOSliceService soSliceService;

    private AAISliceService aaiSliceService;


    public TaskMgtServiceImpl() {
        this(RestfulServices.create(SOSliceService.class),RestfulServices.create(AAISliceService.class));

    }

    public TaskMgtServiceImpl(SOSliceService soSliceService,AAISliceService aaiSliceService) {
        this.soSliceService = soSliceService;
        this.aaiSliceService = aaiSliceService;

    }

    @Override
    public ServiceResult querySlicingTask(int pageNo, int pageSize) {

        ServiceResult serviceResult = new ServiceResult();
        ResultHeader resultHeader = new ResultHeader();

        SlicingTaskList slicingTaskList = new SlicingTaskList();
        SOTaskRsp soTaskRsp = new SOTaskRsp();
        String resultMsg;

        try {
            Response<JSONArray> response = this.soSliceService.listTask().execute();
            if (response.isSuccessful()) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<SOTask>>() {
                }.getType();
                soTaskRsp.setTask(gson.fromJson(response.body().toString(), type));
                logger.info("querySlicingTask: listTask response is:{}", response.body().toString());
                taskMgtServiceConvert.convertSlicingTaskList(slicingTaskList, soTaskRsp, pageNo, pageSize);
                resultMsg = "5G slicing task query result.";
                resultHeader.setResult_code(NsmfCodeConstant.SUCCESS_CODE);
            } else {
                logger.error(
                    String.format("querySlicingTask: Can not get slicingTaskList[code={}, message={}]", response.code(),
                        response.message()));
                resultMsg = "5G slicing task result query failed!";
                resultHeader.setResult_code(NsmfCodeConstant.ERROR_CODE_UNKNOWN);
            }
        } catch (Exception e) {
            resultMsg = "5G slicing task result query failed. Unknown exception occurred!";
            resultHeader.setResult_code(NsmfCodeConstant.ERROR_CODE_UNKNOWN);
            logger.error(e.getMessage());
        }

        logger.info(resultMsg);
        logger.info("querySlicingTask: 5G slicing task result query has been finished.");
        resultHeader.setResult_message(resultMsg);
        serviceResult.setResult_header(resultHeader);
        serviceResult.setResult_body(slicingTaskList);
        return serviceResult;
    }

    @Override
    public ServiceResult querySlicingTaskByStatus(String status, int pageNo, int pageSize) {

        ServiceResult serviceResult = new ServiceResult();
        ResultHeader resultHeader = new ResultHeader();

        SlicingTaskList slicingTaskList = new SlicingTaskList();
        SOTaskRsp soTaskRsp = new SOTaskRsp();
        String resultMsg;
        try {
            Response<JSONArray> response = this.soSliceService.listTaskByStage(status).execute();
            if (response.isSuccessful()) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<SOTask>>() {
                }.getType();
                soTaskRsp.setTask(gson.fromJson(response.body().toString(), type));
                logger.info("querySlicingTaskByStatus: listTaskByStage response is:{}", response.body().toString());
                taskMgtServiceConvert.convertSlicingTaskList(slicingTaskList, soTaskRsp, pageNo, pageSize);
                resultMsg = "5G slicing task query based on status result.";
                resultHeader.setResult_code(NsmfCodeConstant.SUCCESS_CODE);
            } else {
                logger.error(String.format("querySlicingTaskByStatus: Can not get slicingTaskList[code={}, message={}]",
                    response.code(),
                    response.message()));
                resultMsg = "5G slicing task result query based on status failed. ";
                resultHeader.setResult_code(NsmfCodeConstant.ERROR_CODE_UNKNOWN);
            }
        } catch (Exception e) {
            resultMsg = "5G slicing task result query based on status failed. Unknown exception occurred!";
            resultHeader.setResult_code(NsmfCodeConstant.ERROR_CODE_UNKNOWN);
            logger.error(e.getMessage());
        }

        logger.info(resultMsg);
        logger.info("querySlicingTaskByStatus: 5G slicing task result query based on status has been finished.");
        resultHeader.setResult_message(resultMsg);
        serviceResult.setResult_header(resultHeader);
        serviceResult.setResult_body(slicingTaskList);
        return serviceResult;
    }

    @Override
    public ServiceResult queryTaskAuditInfo(String taskId) {
        ServiceResult serviceResult = new ServiceResult();
        ResultHeader resultHeader = new ResultHeader();

        SlicingTaskAuditInfo slicingTaskAuditInfo = new SlicingTaskAuditInfo();
        String resultMsg = "";

        try {
            // TODO
            Response<SOTask> response = this.soSliceService.getTaskById(taskId).execute();
            if (response.isSuccessful()) {
                SOTask soTaskInfo = response.body();
                Gson gson = new Gson();
                logger.info("queryTaskAuditInfo: getTaskById response is:{}", gson.toJson(soTaskInfo));
                taskMgtServiceConvert.convertTaskAuditInfo(slicingTaskAuditInfo, soTaskInfo);
                // return normal result code
                resultMsg = "5G slicing task configure audit information query result.";
                resultHeader.setResult_code(NsmfCodeConstant.SUCCESS_CODE);
            } else {
                logger.error(String.format("queryTaskAuditInfo: Can not get slicingTaskAuditInfo[code={}, message={}]",
                    response.code(),
                    response.message()));
                resultMsg = "5G slicing task configure audit information query failed.";
                resultHeader.setResult_code(NsmfCodeConstant.ERROR_CODE_UNKNOWN);
            }
        } catch (Exception e) {
            resultMsg = "5G slicing task configure audit information query failed. Unknown exception occurred!";
            resultHeader.setResult_code(NsmfCodeConstant.ERROR_CODE_UNKNOWN);
            logger.error(e.getMessage());
        }

        logger.info(resultMsg);
        logger.info("queryTaskAuditInfo: 5G slicing task configure audit information query has been finished.");
        resultHeader.setResult_message(resultMsg);
        serviceResult.setResult_header(resultHeader);
        serviceResult.setResult_body(slicingTaskAuditInfo);
        return serviceResult;
    }

    public ServiceResult updateTaskAuditInfo(SlicingTaskAuditInfo slicingTaskAuditInfo) {
        ServiceResult serviceResult = new ServiceResult();
        ResultHeader resultHeader = new ResultHeader();
        String resultMsg;
        try {
            Response<SOTask> response = this.soSliceService.getTaskById(slicingTaskAuditInfo.getTaskId()).execute();
            if (response.isSuccessful()) {
                SOTask soTaskInfo = response.body();
                Gson gson = new Gson();
                logger.info("updateTaskAuditInfo: getTaskById response is:{}", gson.toJson(soTaskInfo));

                taskMgtServiceConvert.convertTaskAuditToSoTask(soTaskInfo, slicingTaskAuditInfo);

                String jsonstr = JSON.toJSONString(soTaskInfo);
                logger.info("updateTaskAuditInfo: the requestBody body is:{}", jsonstr);
                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonstr);
                Response<ResponseBody> updateResponse = soSliceService
                    .updateService(slicingTaskAuditInfo.getTaskId(), requestBody).execute();

                if (updateResponse.isSuccessful()) {
                    Response<ResponseBody> commitResponse = this.soSliceService
                        .commitTask(slicingTaskAuditInfo.getTaskId()).execute();
                    if (commitResponse.isSuccessful()) {
                        resultMsg = "5G slicing task submit result.";
                        resultHeader.setResult_code(NsmfCodeConstant.SUCCESS_CODE);
                    } else {
                        logger.error(String.format("updateTaskAuditInfo: Can not commitTask [code={}, message={}]",
                            commitResponse.code(),
                            commitResponse.message()));
                        resultMsg = "5G slicing task submit failed.";
                        resultHeader.setResult_code(NsmfCodeConstant.ERROR_CODE_UNKNOWN);
                    }
                } else {
                    logger.error(String
                        .format("updateTaskAuditInfo: Can not updateService [code={}, message={}]", response.code(),
                            response.message()));
                    resultMsg = "5G slicing task submit failed.";
                    resultHeader.setResult_code(NsmfCodeConstant.ERROR_CODE_UNKNOWN);
                }
            } else {
                logger.error(String.format("updateTaskAuditInfo: Can not get slicingTaskAuditInfo[code={}, message={}]",
                    response.code(),
                    response.message()));
                resultMsg = "5G slicing task submit failed.";
                resultHeader.setResult_code(NsmfCodeConstant.ERROR_CODE_UNKNOWN);
            }
        } catch (Exception e) {
            resultMsg = "5G slicing task submit failed. Unknown exception occurred!";
            resultHeader.setResult_code(NsmfCodeConstant.ERROR_CODE_UNKNOWN);
            logger.error(e.getMessage());
        }

        logger.info(resultMsg);
        logger.info("updateTaskAuditInfo: 5G slicing task submit has been finished.");
        resultHeader.setResult_message(resultMsg);
        serviceResult.setResult_header(resultHeader);
        return serviceResult;
    }

    @Override
    public ServiceResult queryTaskCreationInfo(String taskId) {
        ServiceResult serviceResult = new ServiceResult();
        ResultHeader resultHeader = new ResultHeader();

        SlicingTaskCreationInfo slicingTaskCreationInfo = new SlicingTaskCreationInfo();
        String resultMsg;

        try {
            Response<SOTask> response = this.soSliceService.getTaskByIdD(taskId).execute();
            if (response.isSuccessful()) {
                Gson gson = new Gson();
                SOTask soTask = response.body();
                logger.info("updateTaskAuditInfo: getTaskById response is:{}", gson.toJson(soTask));

                taskMgtServiceConvert.convertTaskCreationInfo(slicingTaskCreationInfo, soTask);
                // return normal result code
                resultMsg = "5G slicing task creation infomation query result.";
                resultHeader.setResult_code(NsmfCodeConstant.SUCCESS_CODE);
            } else {
                logger.error(String
                    .format("updateTaskAuditInfo: Can not get slicingTaskCreationInfo[code={}, message={}]",
                        response.code(),
                        response.message()));
                resultMsg = "5G slicing task creation infomation query failed.";
                resultHeader.setResult_code(NsmfCodeConstant.ERROR_CODE_UNKNOWN);
            }
        } catch (Exception e) {
            resultMsg = "5G slicing task creation infomation query failed. Unknown exception occurred!";
            resultHeader.setResult_code(NsmfCodeConstant.ERROR_CODE_UNKNOWN);
            logger.error(e.getMessage());
        }

        logger.info(resultMsg);
        logger.info("updateTaskAuditInfo: 5G slicing task creation infomation query has been finished.");
        resultHeader.setResult_message(resultMsg);
        serviceResult.setResult_header(resultHeader);
        serviceResult.setResult_body(slicingTaskCreationInfo);
        return serviceResult;
    }

    @Override
    public ServiceResult queryTaskCreationProgress(String taskId) {
        ServiceResult serviceResult = new ServiceResult();
        ResultHeader resultHeader = new ResultHeader();

        SlicingTaskCreationProgress slicingTaskCreationProgress = new SlicingTaskCreationProgress();
        String resultMsg;

        try {
            Response<SOTask> response = this.soSliceService.getTaskByIdD(taskId).execute();
            if (response.isSuccessful()) {
                SOTask soTask = response.body();
                Gson gson = new Gson();
                logger.info("queryTaskCreationProgress: getTaskById response is:{}", gson.toJson(soTask));
                taskMgtServiceConvert.convertTaskCreationProgress(slicingTaskCreationProgress, soTask);
                resultMsg = "5G slicing task operation progress query result.";
                resultHeader.setResult_code(NsmfCodeConstant.SUCCESS_CODE);
            } else {
                logger.error(String
                    .format("queryTaskCreationProgress: Can not get slicingTaskCreationProgress[code={}, message={}]",
                        response.code(), response.message()));
                resultMsg = "5G slicing task operation progress query failed.";
                resultHeader.setResult_code(NsmfCodeConstant.ERROR_CODE_UNKNOWN);
            }
        } catch (Exception e) {
            resultMsg = "5G slicing task operation progress query failed. Unknown exception occurred!";
            resultHeader.setResult_code(NsmfCodeConstant.ERROR_CODE_UNKNOWN);
            logger.error(e.getMessage());
        }

        logger.info(resultMsg);
        logger.info("queryTaskCreationProgress: 5G slicing task operation progress has been finished.");
        resultHeader.setResult_message(resultMsg);
        serviceResult.setResult_header(resultHeader);
        serviceResult.setResult_body(slicingTaskCreationProgress);
        return serviceResult;
    }

    @Override
    public ServiceResult queryConnectionLinks(int pageNo, int pageSize) {
        ServiceResult serviceResult = new ServiceResult();
        ResultHeader resultHeader = new ResultHeader();
        String resultMsg ="";

        ConnectionLinkList connectionLinkList = new ConnectionLinkList();
        ConnectionVo connectionVo = new ConnectionVo();
        ConnectionListVo connectionListVo = new ConnectionListVo();
        List<ConnectionListVo> list = new ArrayList<>();
        try {
            Response<ConnectionLinkList> response = this.aaiSliceService.getConnectionLinks().execute();
            if(response.isSuccessful()){
                connectionLinkList = response.body();
                logger.info(connectionLinkList.toString());
                List<ConnectionLink> connectionLinks = connectionLinkList.getLogicalLink();
                List<ConnectionLink> tsciConnectionLink = connectionLinks.stream().filter(e -> e.getLinkType().equals("TsciConnectionLink") && e.getRelationshipList()!=null).collect(Collectors.toList());
                if(!ObjectUtils.isEmpty(tsciConnectionLink)){
                    for (ConnectionLink connectionLink : tsciConnectionLink) {
                        Response<EndPointInfoList> anInfo = this.aaiSliceService.getEndpointByLinkName(connectionLink.getLinkName()).execute();
                        Response<EndPointInfoList> cnInfo = this.aaiSliceService.getEndpointByLinkName2(connectionLink.getLinkName2()).execute();

                        PropertiesVo propertiesVo = new PropertiesVo();
                        List<RelationshipData> relationshipDataList = connectionLink.getRelationshipList().getRelationship().get(0).getRelationshipDataList();
                        List<RelationshipData> allottedResourceId = relationshipDataList.stream().filter(e -> e.getRelationshipKey().equals("allotted-resource.id")).collect(Collectors.toList());
                        List<RelationshipData> serviceInstanceId = relationshipDataList.stream().filter(e -> e.getRelationshipKey().equals("service-instance.service-instance-id")).collect(Collectors.toList());
                        Response<ConnectionLink> AllottedResource=this.aaiSliceService.getAllottedResource(serviceInstanceId.get(0).getRelationshipValue(),allottedResourceId.get(0).getRelationshipValue()).execute();
                        List<Relationship> relationships= AllottedResource.body().getRelationshipList().getRelationship().stream().filter(a-> a.getRelatedTo().equals("network-policy")).collect(Collectors.toList());
                        List<RelationshipData> networkPolicyId=relationships.get(0).getRelationshipDataList().stream().filter(e -> e.getRelationshipKey().equals("network-policy.network-policy-id")).collect(Collectors.toList());
                        Response<NetworkPolicy> networkPolicy=this.aaiSliceService.getNetworkPolicy(networkPolicyId.get(0).getRelationshipValue()).execute();
                        propertiesVo.setJitter(networkPolicy.body().getJitter());
                        propertiesVo.setLatency(networkPolicy.body().getLatency());
                        propertiesVo.setMaxBandwidth(networkPolicy.body().getMaxBandwidth());
                        Response<ConnectionLink> serviceInstance=this.aaiSliceService.getServiceInstance(serviceInstanceId.get(0).getRelationshipValue()).execute();
                        propertiesVo.setResourceSharingLevel(serviceInstance.body().getServiceFunction());

                        connectionListVo.setLinkId(connectionLink.getLinkId());
                        EndPointInfoListVo anInfoVo = new EndPointInfoListVo();
                        EndPointInfoListVo cnInfoVo = new EndPointInfoListVo();
                        BeanUtils.copyProperties(anInfoVo,anInfo.body());
                        BeanUtils.copyProperties(cnInfoVo,cnInfo.body());
                        connectionListVo.setAnInfo(anInfoVo);
                        connectionListVo.setCnInfo(cnInfoVo);
                        connectionListVo.setProperties(propertiesVo);
                        list.add(connectionListVo);
                    }
                }
                PagedResult pagedOrderList = NsmfCommonUtil.getPagedList(list, pageNo, pageSize);
                connectionVo.setRecord_number(list.size());
                connectionVo.setConnection_links_list(pagedOrderList.getPagedList());
                resultMsg = "ConnectionLinks query result.";
                resultHeader.setResult_code(NsmfCodeConstant.SUCCESS_CODE);
            }else {
                logger.error(String
                        .format("queryConnectionLinks: Can not get ConnectionLinks[code={}, message={}]",
                                response.code(), response.message()));
                resultMsg = "ConnectionLinks progress query failed.";
                resultHeader.setResult_code(NsmfCodeConstant.ERROR_CODE_UNKNOWN);
            }
        } catch (Exception e) {
            resultMsg = "ConnectionLinks progress query failed. Unknown exception occurred!";
            resultHeader.setResult_code(NsmfCodeConstant.ERROR_CODE_UNKNOWN);
            logger.error(e.getMessage());
        }
        logger.info(resultMsg);
        logger.info("queryConnectionLinks: ConnectionLinks progress has been finished.");
        resultHeader.setResult_message(resultMsg);
        serviceResult.setResult_header(resultHeader);
        serviceResult.setResult_body(connectionVo);
        return serviceResult;
    }
}
