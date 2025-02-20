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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.commons.beanutils.BeanUtils;
import org.onap.usecaseui.server.bean.nsmf.common.PagedResult;
import org.onap.usecaseui.server.bean.nsmf.task.BusinessDemandInfo;
import org.onap.usecaseui.server.bean.nsmf.task.EndPointInfo;
import org.onap.usecaseui.server.bean.nsmf.task.NsiAndSubNssiInfo;
import org.onap.usecaseui.server.bean.nsmf.task.NstInfo;
import org.onap.usecaseui.server.bean.nsmf.task.SlicingTaskAuditInfo;
import org.onap.usecaseui.server.bean.nsmf.task.SlicingTaskCreationInfo;
import org.onap.usecaseui.server.bean.nsmf.task.SlicingTaskCreationProgress;
import org.onap.usecaseui.server.bean.nsmf.task.SlicingTaskInfo;
import org.onap.usecaseui.server.bean.nsmf.task.SlicingTaskList;
import org.onap.usecaseui.server.service.slicingdomain.aai.AAISliceClient;
import org.onap.usecaseui.server.service.slicingdomain.aai.bean.connection.ConnectionLink;
import org.onap.usecaseui.server.service.slicingdomain.aai.bean.connection.ConnectionLinkList;
import org.onap.usecaseui.server.service.slicingdomain.so.bean.AnSliceTaskInfo;
import org.onap.usecaseui.server.service.slicingdomain.so.bean.CnSliceTaskInfo;
import org.onap.usecaseui.server.service.slicingdomain.so.bean.SOTask;
import org.onap.usecaseui.server.service.slicingdomain.so.bean.SOTaskRsp;
import org.onap.usecaseui.server.service.slicingdomain.so.bean.SliceProfile;
import org.onap.usecaseui.server.service.slicingdomain.so.bean.SliceTaskParams;
import org.onap.usecaseui.server.service.slicingdomain.so.bean.TnBHSliceTaskInfo;
import org.onap.usecaseui.server.util.RestfulServices;
import org.onap.usecaseui.server.util.nsmf.NsmfCommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import jakarta.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Response;

@Setter
@RequiredArgsConstructor
@Service("TaskMgtConvertService")
public class TaskMgtServiceConvert {

    private static final Logger logger = LoggerFactory.getLogger(TaskMgtServiceConvert.class);

    @Resource(name = "GeneralConvertService")
    private GeneralConvertImpl generalConvert;

    private final AAISliceClient aaiSliceClient;

    void convertSlicingTaskList(SlicingTaskList targetSlicingTaskList, SOTaskRsp sourceSoTaskRsp, int pageNo,
        int pageSize)
        throws InvocationTargetException, IllegalAccessException {
        if (sourceSoTaskRsp.getTask() == null) {
            logger.error("convertSlicingTaskList: sourceSoTaskRsp.getTask() is null");
            return;
        }

        List<SlicingTaskInfo> slicingTaskInfoList = new ArrayList<>();
        for (SOTask soTask : sourceSoTaskRsp.getTask()) {
            SlicingTaskInfo slicingTaskInfo = new SlicingTaskInfo();
            BeanUtils.copyProperties(slicingTaskInfo, soTask);
            String params = soTask.getParams();
            Gson gson = new Gson();
            Type type = new TypeToken<SliceTaskParams>() {}.getType();
            SliceTaskParams sliceTaskParams = gson.fromJson(params, type);
            slicingTaskInfo.setServiceSnssai(sliceTaskParams.getServiceProfile().getSNSSAI());
            slicingTaskInfo.setServiceType(sliceTaskParams.getServiceProfile().getSST());
            slicingTaskInfo.setName(sliceTaskParams.getServiceName());
            slicingTaskInfo.setCreateTime(soTask.getCreatedTime());
            slicingTaskInfoList.add(slicingTaskInfo);
        }
        PagedResult pagedOrderList = NsmfCommonUtil.getPagedList(slicingTaskInfoList, pageNo, pageSize);
        targetSlicingTaskList.setSlicingTaskInfoList(pagedOrderList.getPagedList());
        targetSlicingTaskList.setRecordNumber(slicingTaskInfoList.size());
    }

    void convertTaskAuditInfo(SlicingTaskAuditInfo targetSlicingTaskAuditInfo, SOTask sourceSoTaskInfo) {
        targetSlicingTaskAuditInfo.setTaskId(sourceSoTaskInfo.getTaskId());
        targetSlicingTaskAuditInfo.setTaskName(sourceSoTaskInfo.getName());
        targetSlicingTaskAuditInfo.setCreateTime(sourceSoTaskInfo.getCreatedTime());
        targetSlicingTaskAuditInfo.setProcessingStatus(sourceSoTaskInfo.getStatus());

        String params = sourceSoTaskInfo.getParams();
        Gson gson = new Gson();
        Type type = new TypeToken<SliceTaskParams>() {}.getType();
        SliceTaskParams sliceTaskParams = gson.fromJson(params, type);
        if (sliceTaskParams == null) {
            logger.error("convertTaskAuditInfo: paramsObject is null");
            return;
        }

        BusinessDemandInfo businessDemandInfo = new BusinessDemandInfo();
        convertBusinessDemandInfo(businessDemandInfo, sliceTaskParams);
        targetSlicingTaskAuditInfo.setBusinessDemandInfo(businessDemandInfo);

        NstInfo nstInfo = new NstInfo();
        convertNstInfo(nstInfo, sliceTaskParams);
        targetSlicingTaskAuditInfo.setNstInfo(nstInfo);

        NsiAndSubNssiInfo nsiAndSubNssiInfo = new NsiAndSubNssiInfo();
        convertNsiAndSubNssiInfo(nsiAndSubNssiInfo, sliceTaskParams);
        targetSlicingTaskAuditInfo.setNsiAndSubNssiInfo(nsiAndSubNssiInfo);
    }

    void convertBusinessDemandInfo(BusinessDemandInfo targetBusinessDemandInfo, SliceTaskParams sliceTaskParams) {

        targetBusinessDemandInfo.setServiceName(sliceTaskParams.getServiceName());
        targetBusinessDemandInfo.setServiceSnssai(sliceTaskParams.getServiceProfile().getSNSSAI());
        targetBusinessDemandInfo.setExpDataRateDL(sliceTaskParams.getServiceProfile().getExpDataRateDL());
        targetBusinessDemandInfo.setExpDataRateUL(sliceTaskParams.getServiceProfile().getExpDataRateUL());
        targetBusinessDemandInfo.setUeMobilityLevel(sliceTaskParams.getServiceProfile().getUEMobilityLevel());
        targetBusinessDemandInfo.setLatency(sliceTaskParams.getServiceProfile().getLatency());
        String useInterval = generalConvert.getUseInterval(sliceTaskParams.getServiceId());
        targetBusinessDemandInfo.setUseInterval(useInterval);
        targetBusinessDemandInfo.setCoverageAreaTaList(generalConvert.getAreaTaList(sliceTaskParams.getServiceProfile().getCoverageAreaTAList()));

        targetBusinessDemandInfo.setActivityFactor(sliceTaskParams.getServiceProfile().getActivityFactor());
        targetBusinessDemandInfo.setResourceSharingLevel(sliceTaskParams.getServiceProfile().getResourceSharingLevel());
        targetBusinessDemandInfo.setAreaTrafficCapDL(sliceTaskParams.getServiceProfile().getAreaTrafficCapDL());
        targetBusinessDemandInfo.setAreaTrafficCapUL(sliceTaskParams.getServiceProfile().getAreaTrafficCapUL());
        targetBusinessDemandInfo.setMaxNumberOfUEs(sliceTaskParams.getServiceProfile().getMaxNumberofUEs());
        targetBusinessDemandInfo.setServiceProfileAvailability(sliceTaskParams.getServiceProfile().getAvailability());
        targetBusinessDemandInfo.setServiceProfilePLMNIdList(sliceTaskParams.getServiceProfile().getPLMNIdList());
        targetBusinessDemandInfo.setServiceProfileReliability(sliceTaskParams.getServiceProfile().getReliability());
        targetBusinessDemandInfo.setServiceProfileDLThptPerSlice(sliceTaskParams.getServiceProfile().getDLThptPerSlice());
        targetBusinessDemandInfo.setServiceProfileDLThptPerUE(sliceTaskParams.getServiceProfile().getDLThptPerUE());
        targetBusinessDemandInfo.setServiceProfileULThptPerSlice(sliceTaskParams.getServiceProfile().getULThptPerSlice());
        targetBusinessDemandInfo.setServiceProfileULThptPerUE(sliceTaskParams.getServiceProfile().getULThptPerUE());
        targetBusinessDemandInfo.setServiceProfileMaxPktSize(sliceTaskParams.getServiceProfile().getMaxPktSize());
        targetBusinessDemandInfo.setServiceProfileMaxNumberofConns(sliceTaskParams.getServiceProfile().getMaxNumberofConns());
        targetBusinessDemandInfo.setServiceProfileTermDensity(sliceTaskParams.getServiceProfile().getTermDensity());
        targetBusinessDemandInfo.setServiceProfileJitter(sliceTaskParams.getServiceProfile().getJitter());
        targetBusinessDemandInfo.setServiceProfileSurvivalTime(sliceTaskParams.getServiceProfile().getSurvivalTime());

    }

    void convertNstInfo(NstInfo nstInfo, SliceTaskParams sliceTaskParams) {
        nstInfo.setNstId(sliceTaskParams.getNstId());
        nstInfo.setNstName(sliceTaskParams.getNstName());
    }

    void convertNsiAndSubNssiInfo(NsiAndSubNssiInfo nsiAndSubNssiInfo, SliceTaskParams sliceTaskParams) {

        nsiAndSubNssiInfo.setSuggestNsiId(sliceTaskParams.getSuggestNsiId());
        nsiAndSubNssiInfo.setSuggestNsiName(sliceTaskParams.getSuggestNsiName());
        nsiAndSubNssiInfo.setAnScriptName(sliceTaskParams.getAnSliceTaskInfo().getScriptName());
        nsiAndSubNssiInfo.setAnEnableNSSISelection(sliceTaskParams.getAnSliceTaskInfo().getEnableNSSISelection());
        if(!ObjectUtils.isEmpty(sliceTaskParams.getAnSliceTaskInfo().getSliceProfile())){
            nsiAndSubNssiInfo.setAnSuggestNssiId(sliceTaskParams.getAnSliceTaskInfo().getSuggestNssiId());
            nsiAndSubNssiInfo.setAnSuggestNssiName(sliceTaskParams.getAnSliceTaskInfo().getSuggestNssiName());
            nsiAndSubNssiInfo.setAn5qi(sliceTaskParams.getAnSliceTaskInfo().getSliceProfile().getAn5qi());

            String anCoverageAreaTA = sliceTaskParams.getAnSliceTaskInfo().getSliceProfile().getCoverageAreaTAList();
            List<String> areaTaList = generalConvert.getAreaTaList(anCoverageAreaTA);
            nsiAndSubNssiInfo.setAnCoverageAreaTaList(areaTaList);
            nsiAndSubNssiInfo.setAnLatency(sliceTaskParams.getAnSliceTaskInfo().getSliceProfile().getLatency());

            nsiAndSubNssiInfo.setSliceProfile_AN_sNSSAI(sliceTaskParams.getAnSliceTaskInfo().getSliceProfile().getSNSSAI());
            nsiAndSubNssiInfo.setSliceProfile_AN_maxNumberofUEs(sliceTaskParams.getAnSliceTaskInfo().getSliceProfile().getMaxNumberofUEs());

            nsiAndSubNssiInfo.setSliceProfile_AN_maxNumberofPDUSession(sliceTaskParams.getAnSliceTaskInfo().getSliceProfile().getMaxNumberofPDUSession());
            nsiAndSubNssiInfo.setSliceProfile_AN_expDataRateDL(sliceTaskParams.getAnSliceTaskInfo().getSliceProfile().getExpDataRateDL());
            nsiAndSubNssiInfo.setSliceProfile_AN_expDataRateUL(sliceTaskParams.getAnSliceTaskInfo().getSliceProfile().getExpDataRateUL());
            nsiAndSubNssiInfo.setSliceProfile_AN_areaTrafficCapDL(sliceTaskParams.getAnSliceTaskInfo().getSliceProfile().getAreaTrafficCapDL());
            nsiAndSubNssiInfo.setSliceProfile_AN_areaTrafficCapUL(sliceTaskParams.getAnSliceTaskInfo().getSliceProfile().getAreaTrafficCapUL());

            nsiAndSubNssiInfo.setSliceProfile_AN_overallUserDensity(sliceTaskParams.getAnSliceTaskInfo().getSliceProfile().getOverallUserDensity());
            nsiAndSubNssiInfo.setSliceProfile_AN_activityFactor(sliceTaskParams.getAnSliceTaskInfo().getSliceProfile().getActivityFactor());
            nsiAndSubNssiInfo.setSliceProfile_AN_uEMobilityLevel(sliceTaskParams.getAnSliceTaskInfo().getSliceProfile().getUEMobilityLevel());
            nsiAndSubNssiInfo.setSliceProfile_AN_resourceSharingLevel(sliceTaskParams.getAnSliceTaskInfo().getSliceProfile().getResourceSharingLevel());
            nsiAndSubNssiInfo.setSliceProfile_AN_sST(sliceTaskParams.getAnSliceTaskInfo().getSliceProfile().getSST());

            nsiAndSubNssiInfo.setSliceProfile_AN_cSAvailabilityTarget(sliceTaskParams.getAnSliceTaskInfo().getSliceProfile().getCsAvailabilityTarget());
            nsiAndSubNssiInfo.setSliceProfile_AN_cSReliabilityMeanTime(sliceTaskParams.getAnSliceTaskInfo().getSliceProfile().getCSReliabilityMeanTime());
            nsiAndSubNssiInfo.setSliceProfile_AN_expDataRate(sliceTaskParams.getAnSliceTaskInfo().getSliceProfile().getExpDataRate());
            nsiAndSubNssiInfo.setSliceProfile_AN_msgSizeByte(sliceTaskParams.getAnSliceTaskInfo().getSliceProfile().getMsgSizeByte());
            nsiAndSubNssiInfo.setSliceProfile_AN_transferIntervalTarget(sliceTaskParams.getAnSliceTaskInfo().getSliceProfile().getTransferIntervalTarget());
            nsiAndSubNssiInfo.setSliceProfile_AN_survivalTime(sliceTaskParams.getAnSliceTaskInfo().getSliceProfile().getSurvivalTime());
            nsiAndSubNssiInfo.setSliceProfile_AN_ipAddress(sliceTaskParams.getAnSliceTaskInfo().getSliceProfile().getIpAddress());
            nsiAndSubNssiInfo.setSliceProfile_AN_logicInterfaceId(sliceTaskParams.getAnSliceTaskInfo().getSliceProfile().getLogicInterfaceId());
            nsiAndSubNssiInfo.setSliceProfile_AN_nextHopInfo(sliceTaskParams.getAnSliceTaskInfo().getSliceProfile().getNextHopInfo());
        }
        nsiAndSubNssiInfo.setTnBhSuggestNssiId(sliceTaskParams.getTnBHSliceTaskInfo().getSuggestNssiId());
        nsiAndSubNssiInfo.setTnBhSuggestNssiName(sliceTaskParams.getTnBHSliceTaskInfo().getSuggestNssiName());
        nsiAndSubNssiInfo.setTnBhScriptName(sliceTaskParams.getTnBHSliceTaskInfo().getScriptName());
        nsiAndSubNssiInfo.setTnEnableNSSISelection(sliceTaskParams.getTnBHSliceTaskInfo().getEnableNSSISelection());
        if(!ObjectUtils.isEmpty(sliceTaskParams.getTnBHSliceTaskInfo().getSliceProfile())){
            nsiAndSubNssiInfo.setTnBhLatency(sliceTaskParams.getTnBHSliceTaskInfo().getSliceProfile().getLatency());
            nsiAndSubNssiInfo.setTnBhBandwidth(sliceTaskParams.getTnBHSliceTaskInfo().getSliceProfile().getMaxBandwidth());

            nsiAndSubNssiInfo.setSliceProfile_TN_BH_jitte(sliceTaskParams.getTnBHSliceTaskInfo().getSliceProfile().getJitter());
            nsiAndSubNssiInfo.setSliceProfile_TN_BH_pLMNIdList(sliceTaskParams.getTnBHSliceTaskInfo().getSliceProfile().getPLMNIdList());
            nsiAndSubNssiInfo.setSliceProfile_TN_BH_sNSSAI(sliceTaskParams.getTnBHSliceTaskInfo().getSliceProfile().getSNSSAI());
            nsiAndSubNssiInfo.setSliceProfile_TN_BH_sST(sliceTaskParams.getTnBHSliceTaskInfo().getSliceProfile().getSST());
            nsiAndSubNssiInfo.setSliceProfile_TN_BH_connectionLinkId(sliceTaskParams.getTnBHSliceTaskInfo().getSliceProfile().getConnectionLinkId());
            nsiAndSubNssiInfo.setSliceProfile_TN_BH_resourceSharingLevel(sliceTaskParams.getTnBHSliceTaskInfo().getSliceProfile().getResourceSharingLevel());
        }
        nsiAndSubNssiInfo.setCnScriptName(sliceTaskParams.getCnSliceTaskInfo().getScriptName());
        nsiAndSubNssiInfo.setCnEnableNSSISelection(sliceTaskParams.getCnSliceTaskInfo().getEnableNSSISelection());
        nsiAndSubNssiInfo.setCnSuggestNssiId(sliceTaskParams.getCnSliceTaskInfo().getSuggestNssiId());
        nsiAndSubNssiInfo.setCnSuggestNssiName(sliceTaskParams.getCnSliceTaskInfo().getSuggestNssiName());
        if(!ObjectUtils.isEmpty(sliceTaskParams.getCnSliceTaskInfo().getSliceProfile())){
            nsiAndSubNssiInfo.setCnServiceSnssai(sliceTaskParams.getCnSliceTaskInfo().getSliceProfile().getSNSSAI());
            nsiAndSubNssiInfo.setCnResourceSharingLevel(sliceTaskParams.getCnSliceTaskInfo().getSliceProfile().getResourceSharingLevel());
            nsiAndSubNssiInfo.setCnUeMobilityLevel(sliceTaskParams.getCnSliceTaskInfo().getSliceProfile().getUEMobilityLevel());
            nsiAndSubNssiInfo.setCnLatency(sliceTaskParams.getCnSliceTaskInfo().getSliceProfile().getLatency());
            nsiAndSubNssiInfo.setCnMaxNumberOfUes(sliceTaskParams.getCnSliceTaskInfo().getSliceProfile().getMaxNumberofUEs());
            nsiAndSubNssiInfo.setCnActivityFactor(sliceTaskParams.getCnSliceTaskInfo().getSliceProfile().getActivityFactor());
            nsiAndSubNssiInfo.setCnExpDataRateDl(sliceTaskParams.getCnSliceTaskInfo().getSliceProfile().getExpDataRateDL());
            nsiAndSubNssiInfo.setCnExpDataRateUl(sliceTaskParams.getCnSliceTaskInfo().getSliceProfile().getExpDataRateUL());
            nsiAndSubNssiInfo.setCnAreaTrafficCapDl(sliceTaskParams.getCnSliceTaskInfo().getSliceProfile().getAreaTrafficCapDL());
            nsiAndSubNssiInfo.setCnAreaTrafficCapUl(sliceTaskParams.getCnSliceTaskInfo().getSliceProfile().getAreaTrafficCapUL());

            nsiAndSubNssiInfo.setSliceProfile_CN_maxNumberofPDUSession(sliceTaskParams.getCnSliceTaskInfo().getSliceProfile().getMaxNumberofPDUSession());
            nsiAndSubNssiInfo.setSliceProfile_CN_overallUserDensity(sliceTaskParams.getCnSliceTaskInfo().getSliceProfile().getOverallUserDensity());
            nsiAndSubNssiInfo.setSliceProfile_CN_coverageAreaTAList(sliceTaskParams.getCnSliceTaskInfo().getSliceProfile().getCoverageAreaTAList());
            nsiAndSubNssiInfo.setSliceProfile_CN_sST(sliceTaskParams.getCnSliceTaskInfo().getSliceProfile().getSST());
            nsiAndSubNssiInfo.setSliceProfile_CN_cSAvailabilityTarget(sliceTaskParams.getCnSliceTaskInfo().getSliceProfile().getCsAvailabilityTarget());
            nsiAndSubNssiInfo.setSliceProfile_CN_cSReliabilityMeanTime(sliceTaskParams.getCnSliceTaskInfo().getSliceProfile().getCSReliabilityMeanTime());
            nsiAndSubNssiInfo.setSliceProfile_CN_expDataRate(sliceTaskParams.getCnSliceTaskInfo().getSliceProfile().getExpDataRate());
            nsiAndSubNssiInfo.setSliceProfile_CN_msgSizeByte(sliceTaskParams.getCnSliceTaskInfo().getSliceProfile().getMsgSizeByte());
            nsiAndSubNssiInfo.setSliceProfile_CN_transferIntervalTarget(sliceTaskParams.getCnSliceTaskInfo().getSliceProfile().getTransferIntervalTarget());
            nsiAndSubNssiInfo.setSliceProfile_CN_survivalTime(sliceTaskParams.getCnSliceTaskInfo().getSliceProfile().getSurvivalTime());
            nsiAndSubNssiInfo.setSliceProfile_CN_ipAddress(sliceTaskParams.getCnSliceTaskInfo().getSliceProfile().getIpAddress());
            nsiAndSubNssiInfo.setSliceProfile_CN_logicInterfaceId(sliceTaskParams.getCnSliceTaskInfo().getSliceProfile().getLogicInterfaceId());
            nsiAndSubNssiInfo.setSliceProfile_CN_nextHopInfo(sliceTaskParams.getCnSliceTaskInfo().getSliceProfile().getNextHopInfo());
        }
    }

    void convertTaskAuditToSoTask(SOTask targetSoTaskInfo, SlicingTaskAuditInfo sourceSlicingTaskAuditInfo) {
        if (sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo() == null) {
            logger.error("convertTaskAuditToSoTask: sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo() is null");
            return;
        }

        String params = targetSoTaskInfo.getParams();
        Gson gson = new Gson();
        Type type = new TypeToken<SliceTaskParams>() {}.getType();
        SliceTaskParams sliceTaskParams = gson.fromJson(params, type);
        if (sliceTaskParams == null) {
            logger.error("convertTaskAuditToSoTask: sliceTaskParams is null");
            return;
        }

        sliceTaskParams.setSuggestNsiId(sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getSuggestNsiId());
        sliceTaskParams.setSuggestNsiName(sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getSuggestNsiName());

        AnSliceTaskInfo anSliceTaskInfo = sliceTaskParams.getAnSliceTaskInfo();
        CnSliceTaskInfo cnSliceTaskInfo = sliceTaskParams.getCnSliceTaskInfo();
        if (sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getSliceProfile_TN_BH_connectionLinkId() != null){
            EndPointInfo endPointInfo = queryEndPointId(sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getSliceProfile_TN_BH_connectionLinkId());
            anSliceTaskInfo.setEndPointId(endPointInfo.getANEndPointId());
            cnSliceTaskInfo.setEndPointId(endPointInfo.getCNEndPointId());
        }

        anSliceTaskInfo.setSuggestNssiId(sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getAnSuggestNssiId());
        anSliceTaskInfo.setSuggestNssiName(sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getAnSuggestNssiName());
        SliceProfile sliceProfile = anSliceTaskInfo.getSliceProfile();
        sliceProfile.setLatency(sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getAnLatency());
        sliceProfile.setAn5qi(sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getAn5qi());
        String aNCoverageAreaTAList = getAreaTaListString
            (sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getAnCoverageAreaTaList(), '|');
        sliceProfile.setCoverageAreaTAList(aNCoverageAreaTAList);
        anSliceTaskInfo.setScriptName(sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getAnScriptName());
        anSliceTaskInfo.setEnableNSSISelection(sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getAnEnableNSSISelection());
        sliceProfile.setSNSSAI(sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getSliceProfile_AN_sNSSAI());
        sliceProfile.setMaxNumberofUEs(sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getSliceProfile_AN_maxNumberofUEs());
        sliceProfile.setMaxNumberofPDUSession(sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getSliceProfile_AN_maxNumberofPDUSession());
        sliceProfile.setExpDataRateDL(sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getSliceProfile_AN_expDataRateDL());
        sliceProfile.setExpDataRateUL(sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getSliceProfile_AN_expDataRateUL());
        sliceProfile.setAreaTrafficCapDL(sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getSliceProfile_AN_areaTrafficCapDL());
        sliceProfile.setAreaTrafficCapUL(sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getSliceProfile_AN_areaTrafficCapUL());
        sliceProfile.setOverallUserDensity(sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getSliceProfile_AN_overallUserDensity());
        sliceProfile.setActivityFactor(sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getSliceProfile_AN_activityFactor());
        sliceProfile.setUEMobilityLevel(sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getSliceProfile_AN_uEMobilityLevel());
        sliceProfile.setResourceSharingLevel(sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getSliceProfile_AN_resourceSharingLevel());
        sliceProfile.setCsAvailabilityTarget(sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getSliceProfile_AN_cSAvailabilityTarget());
        sliceProfile.setCSReliabilityMeanTime(sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getSliceProfile_AN_cSReliabilityMeanTime());
        sliceProfile.setExpDataRate(sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getSliceProfile_AN_expDataRate());
        sliceProfile.setMsgSizeByte(sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getSliceProfile_AN_msgSizeByte());
        sliceProfile.setTransferIntervalTarget(sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getSliceProfile_AN_transferIntervalTarget());
        sliceProfile.setSurvivalTime(sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getSliceProfile_AN_survivalTime());
        sliceProfile.setIpAddress(sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getSliceProfile_AN_ipAddress());
        sliceProfile.setLogicInterfaceId(sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getSliceProfile_AN_logicInterfaceId());
        sliceProfile.setNextHopInfo(sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getSliceProfile_AN_nextHopInfo());
        anSliceTaskInfo.setSliceProfile(sliceProfile);
        sliceTaskParams.setAnSliceTaskInfo(anSliceTaskInfo);

        TnBHSliceTaskInfo tnBHSliceTaskInfo = sliceTaskParams.getTnBHSliceTaskInfo();
        SliceProfile tnSliceProfile = tnBHSliceTaskInfo.getSliceProfile();
        tnBHSliceTaskInfo.setSuggestNssiId(sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getTnBhSuggestNssiId());
        tnBHSliceTaskInfo.setSuggestNssiName(sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getTnBhSuggestNssiName());
        tnSliceProfile.setMaxBandwidth(sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getTnBhBandwidth());
        tnSliceProfile.setLatency(sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getTnBhLatency());
        tnBHSliceTaskInfo.setScriptName(sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getTnBhScriptName());
        tnBHSliceTaskInfo.setEnableNSSISelection(sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getTnEnableNSSISelection());
        tnSliceProfile.setJitter(sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getSliceProfile_TN_BH_jitte());
        tnSliceProfile.setPLMNIdList(sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getSliceProfile_TN_BH_pLMNIdList());
        tnSliceProfile.setSNSSAI(sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getSliceProfile_TN_BH_sNSSAI());
        tnSliceProfile.setConnectionLinkId(sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getSliceProfile_TN_BH_connectionLinkId());
        tnSliceProfile.setResourceSharingLevel(sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getSliceProfile_TN_BH_resourceSharingLevel());
        tnBHSliceTaskInfo.setSliceProfile(tnSliceProfile);
        sliceTaskParams.setTnBHSliceTaskInfo(tnBHSliceTaskInfo);

        SliceProfile cnSliceProfile = cnSliceTaskInfo.getSliceProfile();
        cnSliceTaskInfo.setSuggestNssiId(sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getCnSuggestNssiId());
        cnSliceTaskInfo.setSuggestNssiName(sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getCnSuggestNssiName());
        cnSliceProfile.setSNSSAI(sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getCnServiceSnssai());
        cnSliceProfile.setResourceSharingLevel(sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getCnResourceSharingLevel());
        cnSliceProfile.setUEMobilityLevel(sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getCnUeMobilityLevel());
        cnSliceProfile.setLatency(sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getCnLatency());
        cnSliceProfile.setMaxNumberofUEs(sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getCnMaxNumberOfUes());
        cnSliceProfile.setActivityFactor(sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getCnActivityFactor());
        cnSliceProfile.setExpDataRateDL( sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getCnExpDataRateDl());
        cnSliceProfile.setExpDataRateUL(sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getCnExpDataRateUl());
        cnSliceProfile.setAreaTrafficCapDL(sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getCnAreaTrafficCapDl());
        cnSliceProfile.setAreaTrafficCapUL(sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getCnAreaTrafficCapUl());
        cnSliceTaskInfo.setScriptName(sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getCnScriptName());
        cnSliceTaskInfo.setEnableNSSISelection(sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getCnEnableNSSISelection());
        cnSliceProfile.setMaxNumberofPDUSession(sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getSliceProfile_CN_maxNumberofPDUSession());
        cnSliceProfile.setOverallUserDensity(sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getSliceProfile_CN_overallUserDensity());
        cnSliceProfile.setCoverageAreaTAList(sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getSliceProfile_CN_coverageAreaTAList());
        cnSliceProfile.setCsAvailabilityTarget(sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getSliceProfile_CN_cSAvailabilityTarget());
        cnSliceProfile.setCSReliabilityMeanTime(sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getSliceProfile_CN_cSReliabilityMeanTime());
        cnSliceProfile.setExpDataRate(sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getSliceProfile_CN_expDataRate());
        cnSliceProfile.setMsgSizeByte(sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getSliceProfile_CN_msgSizeByte());
        cnSliceProfile.setTransferIntervalTarget(sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getSliceProfile_CN_transferIntervalTarget());
        cnSliceProfile.setSurvivalTime(sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getSliceProfile_CN_survivalTime());
        cnSliceProfile.setIpAddress(sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getSliceProfile_CN_ipAddress());
        cnSliceProfile.setLogicInterfaceId(sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getSliceProfile_CN_logicInterfaceId());
        cnSliceProfile.setNextHopInfo(sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getSliceProfile_CN_nextHopInfo());
        cnSliceTaskInfo.setSliceProfile(cnSliceProfile);
        sliceTaskParams.setCnSliceTaskInfo(cnSliceTaskInfo);

        Gson gson1 = new Gson();
        String objectStr = gson1.toJson(sliceTaskParams);
        targetSoTaskInfo.setParams(objectStr);
    }

    public EndPointInfo queryEndPointId(String connectionLinkId) {
        ConnectionLinkList connectionLinkList = new ConnectionLinkList();
        EndPointInfo endPointInfo = new EndPointInfo();
        try {
            Response<ConnectionLinkList> response = this.aaiSliceClient.getConnectionLinks().execute();
            if(response.isSuccessful()){
                connectionLinkList = response.body();
                logger.info(connectionLinkList.toString());
                List<ConnectionLink> connectionLinks = connectionLinkList.getLogicalLink();
                List<ConnectionLink> tsciConnectionLink = connectionLinks.stream().filter(e -> e.getLinkId().equals(connectionLinkId) && e.getRelationshipList()!=null).collect(
                    Collectors.toList());

                ConnectionLink connectionLink = tsciConnectionLink.get(0);
                endPointInfo.setANEndPointId(connectionLink.getLinkName());
                endPointInfo.setCNEndPointId(connectionLink.getLinkName2());
            }else {
                logger.error(String
                    .format("queryEndPointId: Can not get ConnectionLinks[code={}, message={}]",
                        response.code(), response.message()));
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.info("queryEndPointId: ConnectionLinks progress has been finished.");

        return endPointInfo;
    }

    String getAreaTaListString(List<String> anCoverageAreaTaList, char separator) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < anCoverageAreaTaList.size(); i++) {
            sb.append(anCoverageAreaTaList.get(i)).append(separator);
        }
        return sb.toString().substring(0, sb.toString().length() - 1);
    }

    void convertTaskCreationInfo(SlicingTaskCreationInfo slicingTaskCreationInfo, SOTask soTask) {
        slicingTaskCreationInfo.setTaskId(soTask.getTaskId());
        slicingTaskCreationInfo.setCreateTime(soTask.getCreatedTime());
        slicingTaskCreationInfo.setProcessingStatus(soTask.getStatus());
        slicingTaskCreationInfo.setTaskName(soTask.getName());

        String params = soTask.getParams();
        Gson gson = new Gson();
        Type type = new TypeToken<SliceTaskParams>() {}.getType();
        SliceTaskParams sliceTaskParams = gson.fromJson(params, type);
        if (sliceTaskParams == null) {
            logger.error("convertTaskCreationInfo: paramsObject is null");
            return;
        }

        BusinessDemandInfo businessDemandInfo = new BusinessDemandInfo();
        convertBusinessDemandInfo(businessDemandInfo, sliceTaskParams);
        slicingTaskCreationInfo.setBusinessDemandInfo(businessDemandInfo);

        NstInfo nstInfo = new NstInfo();
        convertNstInfo(nstInfo, sliceTaskParams);
        slicingTaskCreationInfo.setNstInfo(nstInfo);
    }



    void convertTaskCreationProgress(SlicingTaskCreationProgress slicingTaskCreationProgress, SOTask soTask) {

        String params = soTask.getParams();
        Gson gson = new Gson();
        Type type = new TypeToken<SliceTaskParams>() {}.getType();
        SliceTaskParams sliceTaskParams = gson.fromJson(params, type);        if (sliceTaskParams == null) {
            logger.error("convertTaskCreationProgress: paramsObject is null");
            return;
        }
        String anProgress = sliceTaskParams.getAnSliceTaskInfo().getProgress();
        slicingTaskCreationProgress.setAnProgress(anProgress);

        String tnProgress = sliceTaskParams.getTnBHSliceTaskInfo().getProgress();
        slicingTaskCreationProgress.setTnProgress(tnProgress);

        String cnProgress = sliceTaskParams.getCnSliceTaskInfo().getProgress();
        slicingTaskCreationProgress.setCnProgress(cnProgress);

        String anStatus =sliceTaskParams.getAnSliceTaskInfo().getStatus();
        slicingTaskCreationProgress.setAnStatus(anStatus);

        String tnStatus = sliceTaskParams.getTnBHSliceTaskInfo().getStatus();
        slicingTaskCreationProgress.setTnStatus(tnStatus);

        String cnStatus =sliceTaskParams.getCnSliceTaskInfo().getStatus();
        slicingTaskCreationProgress.setCnStatus(cnStatus);

        String anStatusDescription =sliceTaskParams.getAnSliceTaskInfo().getStatusDescription();
        slicingTaskCreationProgress.setAnStatusDescription(anStatusDescription);

        String tnStatusDescription = sliceTaskParams.getTnBHSliceTaskInfo().getStatusDescription();
        slicingTaskCreationProgress.setTnStatusDescription(tnStatusDescription);

        String cnStatusDescription =sliceTaskParams.getCnSliceTaskInfo().getStatusDescription();
        slicingTaskCreationProgress.setCnStatusDescription(cnStatusDescription);
    }
}
