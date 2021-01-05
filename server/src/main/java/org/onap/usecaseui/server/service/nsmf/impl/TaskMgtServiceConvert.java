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
import com.alibaba.fastjson.JSONObject;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.beanutils.BeanUtils;
import org.onap.usecaseui.server.bean.nsmf.common.PagedResult;
import org.onap.usecaseui.server.bean.nsmf.task.BusinessDemandInfo;
import org.onap.usecaseui.server.bean.nsmf.task.NsiAndSubNssiInfo;
import org.onap.usecaseui.server.bean.nsmf.task.NstInfo;
import org.onap.usecaseui.server.bean.nsmf.task.SlicingTaskAuditInfo;
import org.onap.usecaseui.server.bean.nsmf.task.SlicingTaskCreationInfo;
import org.onap.usecaseui.server.bean.nsmf.task.SlicingTaskCreationProgress;
import org.onap.usecaseui.server.bean.nsmf.task.SlicingTaskInfo;
import org.onap.usecaseui.server.bean.nsmf.task.SlicingTaskList;
import org.onap.usecaseui.server.service.slicingdomain.aai.AAISliceService;
import org.onap.usecaseui.server.service.slicingdomain.so.bean.SOTask;
import org.onap.usecaseui.server.service.slicingdomain.so.bean.SOTaskRsp;
import org.onap.usecaseui.server.util.RestfulServices;
import org.onap.usecaseui.server.util.nsmf.NsmfCommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;

@Service("TaskMgtConvertService")
@org.springframework.context.annotation.Configuration
@EnableAspectJAutoProxy
public class TaskMgtServiceConvert {

    private static final Logger logger = LoggerFactory.getLogger(TaskMgtServiceConvert.class);

    @Resource(name = "GeneralConvertService")
    private GeneralConvertImpl generalConvert;

    private AAISliceService aaiSliceService;

    public TaskMgtServiceConvert() {
        this(RestfulServices.create(AAISliceService.class));
    }

    public TaskMgtServiceConvert(AAISliceService aaiSliceService) {
        this.aaiSliceService = aaiSliceService;
    }

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
            JSONObject paramsObject = JSON.parseObject(soTask.getParams());
            slicingTaskInfo.setServiceSnssai(paramsObject.getString("ServiceProfile.sNSSAI"));
            slicingTaskInfo.setServiceType(paramsObject.getString("ServiceProfile.sST"));
            slicingTaskInfo.setCreateTime(soTask.getCreatedTime());
            slicingTaskInfo.setName(paramsObject.getString("ServiceName"));
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

        JSONObject paramsObject = JSON.parseObject(sourceSoTaskInfo.getParams());
        if (paramsObject == null) {
            logger.error("convertTaskAuditInfo: paramsObject is null");
            return;
        }

        BusinessDemandInfo businessDemandInfo = new BusinessDemandInfo();
        convertBusinessDemandInfo(businessDemandInfo, paramsObject);
        targetSlicingTaskAuditInfo.setBusinessDemandInfo(businessDemandInfo);

        NstInfo nstInfo = new NstInfo();
        convertNstInfo(nstInfo, paramsObject);
        targetSlicingTaskAuditInfo.setNstInfo(nstInfo);

        NsiAndSubNssiInfo nsiAndSubNssiInfo = new NsiAndSubNssiInfo();
        convertNsiAndSubNssiInfo(nsiAndSubNssiInfo, paramsObject);
        targetSlicingTaskAuditInfo.setNsiAndSubNssiInfo(nsiAndSubNssiInfo);
    }

    void convertBusinessDemandInfo(BusinessDemandInfo targetBusinessDemandInfo, JSONObject paramsObject) {

        targetBusinessDemandInfo.setServiceName(paramsObject.getString("ServiceName"));
        targetBusinessDemandInfo.setServiceSnssai(paramsObject.getString("ServiceProfile.sNSSAI"));
        targetBusinessDemandInfo.setExpDataRateDL(paramsObject.getString("ServiceProfile.expDataRateDL"));
        targetBusinessDemandInfo.setExpDataRateUL(paramsObject.getString("ServiceProfile.expDataRateUL"));
        targetBusinessDemandInfo.setUeMobilityLevel(paramsObject.getString("ServiceProfile.uEMobilityLevel"));
        targetBusinessDemandInfo.setLatency(paramsObject.getString("ServiceProfile.latency"));
        String useInterval = generalConvert.getUseInterval(paramsObject.getString("ServiceId"));
        targetBusinessDemandInfo.setUseInterval(useInterval);

        String coverageAreaTA = paramsObject.getString("ServiceProfile.coverageAreaTAList");
        targetBusinessDemandInfo.setCoverageAreaTaList(generalConvert.getAreaTaList(coverageAreaTA));
        targetBusinessDemandInfo.setActivityFactor(paramsObject.getString("ServiceProfile.activityFactor"));
        targetBusinessDemandInfo.setResourceSharingLevel(paramsObject.getString("ServiceProfile.resourceSharingLevel"));
        targetBusinessDemandInfo.setAreaTrafficCapDL(paramsObject.getString("ServiceProfile.areaTrafficCapDL"));
        targetBusinessDemandInfo.setAreaTrafficCapUL(paramsObject.getString("ServiceProfile.areaTrafficCapUL"));
        targetBusinessDemandInfo.setMaxNumberOfUEs(paramsObject.getString("ServiceProfile.maxNumberofUEs"));
        targetBusinessDemandInfo.setServiceProfileAvailability(paramsObject.getString("ServiceProfile.availability"));
        targetBusinessDemandInfo.setServiceProfilePLMNIdList(paramsObject.getString("ServiceProfile.pLMNIdList"));
        targetBusinessDemandInfo.setServiceProfileReliability(paramsObject.getString("ServiceProfile.reliability"));
        targetBusinessDemandInfo.setServiceProfileDLThptPerSlice(paramsObject.getString("ServiceProfile.dLThptPerSlice"));
        targetBusinessDemandInfo.setServiceProfileDLThptPerUE(paramsObject.getString("ServiceProfile.dLThptPerUE"));
        targetBusinessDemandInfo.setServiceProfileULThptPerSlice(paramsObject.getString("ServiceProfile.uLThptPerSlice"));
        targetBusinessDemandInfo.setServiceProfileULThptPerUE(paramsObject.getString("ServiceProfile.uLThptPerUE"));
        targetBusinessDemandInfo.setServiceProfileMaxPktSize(paramsObject.getString("ServiceProfile.maxPktSize"));
        targetBusinessDemandInfo.setServiceProfileMaxNumberofConns(paramsObject.getString("ServiceProfile.maxNumberofConns"));
        targetBusinessDemandInfo.setServiceProfileTermDensity(paramsObject.getString("ServiceProfile.termDensity"));
        targetBusinessDemandInfo.setServiceProfileJitter(paramsObject.getString("ServiceProfile.jitter"));
        targetBusinessDemandInfo.setServiceProfileSurvivalTime(paramsObject.getString("ServiceProfile.survivalTime"));
    }

    void convertNstInfo(NstInfo nstInfo, JSONObject paramsObject) {
        nstInfo.setNstId(paramsObject.getString("NSTId"));
        nstInfo.setNstName(paramsObject.getString("NSTName"));
    }

    void convertNsiAndSubNssiInfo(NsiAndSubNssiInfo nsiAndSubNssiInfo, JSONObject paramsObject) {
        nsiAndSubNssiInfo.setSuggestNsiId(paramsObject.getString("suggestNSIId"));
        nsiAndSubNssiInfo.setSuggestNsiName(paramsObject.getString("suggestNSIName"));
        nsiAndSubNssiInfo.setAnSuggestNssiId(paramsObject.getString("AN.suggestNSSIId"));
        nsiAndSubNssiInfo.setAnSuggestNssiName(paramsObject.getString("AN.suggestNSSIName"));
        nsiAndSubNssiInfo.setAn5qi(paramsObject.getString("SliceProfile.AN.5QI"));

        String anCoverageAreaTA = paramsObject.getString("SliceProfile.AN.coverageAreaTAList");
        nsiAndSubNssiInfo.setAnCoverageAreaTaList(generalConvert.getAreaTaList(anCoverageAreaTA));
        nsiAndSubNssiInfo.setAnLatency(paramsObject.getString("SliceProfile.AN.latency"));
        nsiAndSubNssiInfo.setAnScriptName(paramsObject.getString("AN.ScriptName"));
        nsiAndSubNssiInfo.setAnEnableNSSISelection(paramsObject.getBoolean("AN.enableNSSISelection"));
        nsiAndSubNssiInfo.setSliceProfile_AN_sNSSAI(paramsObject.getString("SliceProfile.AN.sNSSAI"));
        nsiAndSubNssiInfo.setSliceProfile_AN_maxNumberofUEs(paramsObject.getString("SliceProfile.AN.maxNumberofUEs"));
        nsiAndSubNssiInfo.setSliceProfile_AN_maxNumberofPDUSession(paramsObject.getString("SliceProfile.AN.maxNumberofPDUSession"));
        nsiAndSubNssiInfo.setSliceProfile_AN_expDataRateDL(paramsObject.getString("SliceProfile.AN.expDataRateDL"));
        nsiAndSubNssiInfo.setSliceProfile_AN_expDataRateUL(paramsObject.getString("SliceProfile.AN.expDataRateUL"));
        nsiAndSubNssiInfo.setSliceProfile_AN_areaTrafficCapDL(paramsObject.getString("SliceProfile.AN.areaTrafficCapDL"));
        nsiAndSubNssiInfo.setSliceProfile_AN_areaTrafficCapUL(paramsObject.getString("SliceProfile.AN.areaTrafficCapUL"));
        nsiAndSubNssiInfo.setSliceProfile_AN_overallUserDensity(paramsObject.getString("SliceProfile.AN.overallUserDensity"));
        nsiAndSubNssiInfo.setSliceProfile_AN_activityFactor(paramsObject.getString("SliceProfile.AN.activityFactor"));
        nsiAndSubNssiInfo.setSliceProfile_AN_uEMobilityLevel(paramsObject.getString("SliceProfile.AN.uEMobilityLevel"));
        nsiAndSubNssiInfo.setSliceProfile_AN_resourceSharingLevel(paramsObject.getString("SliceProfile.AN.resourceSharingLevel"));
        nsiAndSubNssiInfo.setSliceProfile_AN_sST(paramsObject.getString("SliceProfile.AN.sST"));
        nsiAndSubNssiInfo.setSliceProfile_AN_cSAvailabilityTarget(paramsObject.getString("SliceProfile.AN.cSAvailabilityTarget"));
        nsiAndSubNssiInfo.setSliceProfile_AN_cSReliabilityMeanTime(paramsObject.getString("SliceProfile.AN.cSReliabilityMeanTime"));
        nsiAndSubNssiInfo.setSliceProfile_AN_expDataRate(paramsObject.getString("SliceProfile.AN.expDataRate"));
        nsiAndSubNssiInfo.setSliceProfile_AN_msgSizeByte(paramsObject.getString("SliceProfile.AN.msgSizeByte"));
        nsiAndSubNssiInfo.setSliceProfile_AN_transferIntervalTarget(paramsObject.getString("SliceProfile.AN.transferIntervalTarget"));
        nsiAndSubNssiInfo.setSliceProfile_AN_survivalTime(paramsObject.getString("SliceProfile.AN.survivalTime"));
        nsiAndSubNssiInfo.setSliceProfile_AN_ipAddress(paramsObject.getString("SliceProfile.AN.ipAddress"));
        nsiAndSubNssiInfo.setSliceProfile_AN_logicInterfaceId(paramsObject.getString("SliceProfile.AN.logicInterfaceId"));
        nsiAndSubNssiInfo.setSliceProfile_AN_nextHopInfo(paramsObject.getString("SliceProfile.AN.nextHopInfo"));

        nsiAndSubNssiInfo.setTnBhSuggestNssiId(paramsObject.getString("TN.BH.suggestNSSIId"));
        nsiAndSubNssiInfo.setTnBhSuggestNssiName(paramsObject.getString("TN.BH.suggestNSSIName"));
        nsiAndSubNssiInfo.setTnBhLatency(paramsObject.getString("SliceProfile.TN.BH.latency"));
        nsiAndSubNssiInfo.setTnBhBandwidth(paramsObject.getString("SliceProfile.TN.BH.maxBandwidth"));
        nsiAndSubNssiInfo.setTnBhScriptName(paramsObject.getString("TN.BH.ScriptName"));
        nsiAndSubNssiInfo.setTnEnableNSSISelection(paramsObject.getBoolean("TN.BH.enableNSSISelection"));
        nsiAndSubNssiInfo.setSliceProfile_TN_BH_jitte(paramsObject.getString("SliceProfile.TN.BH.jitter"));
        nsiAndSubNssiInfo.setSliceProfile_TN_BH_pLMNIdList(paramsObject.getString("SliceProfile.TN.BH.pLMNIdList"));
        nsiAndSubNssiInfo.setSliceProfile_TN_BH_sNSSAI(paramsObject.getString("SliceProfile.TN.BH.sNSSAI"));
        nsiAndSubNssiInfo.setSliceProfile_TN_BH_sST(paramsObject.getString("SliceProfile.TN.BH.sST"));

        nsiAndSubNssiInfo.setCnSuggestNssiId(paramsObject.getString("CN.suggestNSSIId"));
        nsiAndSubNssiInfo.setCnSuggestNssiName(paramsObject.getString("CN.suggestNSSIName"));
        nsiAndSubNssiInfo.setCnServiceSnssai(paramsObject.getString("SliceProfile.CN.sNSSAI"));
        nsiAndSubNssiInfo.setCnResourceSharingLevel(paramsObject.getString("SliceProfile.CN.resourceSharingLevel"));
        nsiAndSubNssiInfo.setCnUeMobilityLevel(paramsObject.getString("SliceProfile.CN.uEMobilityLevel"));
        nsiAndSubNssiInfo.setCnLatency(paramsObject.getString("SliceProfile.CN.latency"));
        nsiAndSubNssiInfo.setCnMaxNumberOfUes(paramsObject.getString("SliceProfile.CN.maxNumberofUEs"));
        nsiAndSubNssiInfo.setCnActivityFactor(paramsObject.getString("SliceProfile.CN.activityFactor"));
        nsiAndSubNssiInfo.setCnExpDataRateDl(paramsObject.getString("SliceProfile.CN.expDataRateDL"));
        nsiAndSubNssiInfo.setCnExpDataRateUl(paramsObject.getString("SliceProfile.CN.expDataRateUL"));
        nsiAndSubNssiInfo.setCnAreaTrafficCapDl(paramsObject.getString("SliceProfile.CN.areaTrafficCapDL"));
        nsiAndSubNssiInfo.setCnAreaTrafficCapUl(paramsObject.getString("SliceProfile.CN.areaTrafficCapUL"));
        nsiAndSubNssiInfo.setCnScriptName(paramsObject.getString("CN.ScriptName"));
        nsiAndSubNssiInfo.setCnEnableNSSISelection(paramsObject.getBoolean("CN.enableNSSISelection"));
        nsiAndSubNssiInfo.setSliceProfile_CN_maxNumberofPDUSession(paramsObject.getString("SliceProfile.CN.maxNumberofPDUSession"));
        nsiAndSubNssiInfo.setSliceProfile_CN_overallUserDensity(paramsObject.getString("SliceProfile.CN.overallUserDensity"));
        nsiAndSubNssiInfo.setSliceProfile_CN_coverageAreaTAList(paramsObject.getString("SliceProfile.CN.coverageAreaTAList"));
        nsiAndSubNssiInfo.setSliceProfile_CN_sST(paramsObject.getString("SliceProfile.CN.sST"));
        nsiAndSubNssiInfo.setSliceProfile_CN_cSAvailabilityTarget(paramsObject.getString("SliceProfile.CN.cSAvailabilityTarget"));
        nsiAndSubNssiInfo.setSliceProfile_CN_cSReliabilityMeanTime(paramsObject.getString("SliceProfile.CN.cSReliabilityMeanTime"));
        nsiAndSubNssiInfo.setSliceProfile_CN_expDataRate(paramsObject.getString("SliceProfile.CN.expDataRate"));
        nsiAndSubNssiInfo.setSliceProfile_CN_msgSizeByte(paramsObject.getString("SliceProfile.CN.msgSizeByte"));
        nsiAndSubNssiInfo.setSliceProfile_CN_transferIntervalTarget(paramsObject.getString("SliceProfile.CN.transferIntervalTarget"));
        nsiAndSubNssiInfo.setSliceProfile_CN_survivalTime(paramsObject.getString("SliceProfile.CN.survivalTime"));
        nsiAndSubNssiInfo.setSliceProfile_CN_ipAddress(paramsObject.getString("SliceProfile.CN.ipAddress"));
        nsiAndSubNssiInfo.setSliceProfile_CN_logicInterfaceId(paramsObject.getString("SliceProfile.CN.logicInterfaceId"));
        nsiAndSubNssiInfo.setSliceProfile_CN_nextHopInfo(paramsObject.getString("SliceProfile.CN.nextHopInfo"));

    }

    void convertTaskAuditToSoTask(SOTask targetSoTaskInfo, SlicingTaskAuditInfo sourceSlicingTaskAuditInfo) {
        if (sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo() == null) {
            logger.error("convertTaskAuditToSoTask: sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo() is null");
            return;
        }

        JSONObject jsonObject = JSONObject.parseObject(targetSoTaskInfo.getParams());
        jsonObject.put("suggestNSIId", sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getSuggestNsiId());
        jsonObject.put("suggestNSIName", sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getSuggestNsiName());
        jsonObject.put("AN.suggestNSSIId", sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getAnSuggestNssiId());
        jsonObject.put("AN.suggestNSSIName", sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getAnSuggestNssiName());
        jsonObject.put("SliceProfile.AN.latency", sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getAnLatency());
        jsonObject.put("SliceProfile.AN.5QI", sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getAn5qi());

        String aNCoverageAreaTAList = getAreaTaListString
            (sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getAnCoverageAreaTaList(), '|');
        jsonObject.put("SliceProfile.AN.coverageAreaTAList", aNCoverageAreaTAList);
        jsonObject.put("AN.ScriptName", sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getAnScriptName());
        jsonObject.put("AN.enableNSSISelection", sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getAnEnableNSSISelection());
        jsonObject.put("SliceProfile.AN.sNSSAI", sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getSliceProfile_AN_sNSSAI());
        jsonObject.put("SliceProfile.AN.maxNumberofUEs", sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getSliceProfile_AN_maxNumberofUEs());
        jsonObject.put("SliceProfile.AN.maxNumberofPDUSession", sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getSliceProfile_AN_maxNumberofPDUSession());
        jsonObject.put("SliceProfile.AN.expDataRateDL", sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getSliceProfile_AN_expDataRateDL());
        jsonObject.put("SliceProfile.AN.expDataRateUL", sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getSliceProfile_AN_expDataRateUL());
        jsonObject.put("SliceProfile.AN.areaTrafficCapDL", sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getSliceProfile_AN_areaTrafficCapDL());
        jsonObject.put("SliceProfile.AN.areaTrafficCapUL", sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getSliceProfile_AN_areaTrafficCapUL());
        jsonObject.put("SliceProfile.AN.overallUserDensity", sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getSliceProfile_AN_overallUserDensity());
        jsonObject.put("SliceProfile.AN.activityFactor", sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getSliceProfile_AN_activityFactor());
        jsonObject.put("SliceProfile.AN.uEMobilityLevel", sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getSliceProfile_AN_uEMobilityLevel());
        jsonObject.put("SliceProfile.AN.resourceSharingLevel", sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getSliceProfile_AN_resourceSharingLevel());
        jsonObject.put("SliceProfile.AN.sST", sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getSliceProfile_AN_sST());
        jsonObject.put("SliceProfile.AN.cSAvailabilityTarget", sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getSliceProfile_AN_cSAvailabilityTarget());
        jsonObject.put("SliceProfile.AN.cSReliabilityMeanTime", sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getSliceProfile_AN_cSReliabilityMeanTime());
        jsonObject.put("SliceProfile.AN.expDataRate", sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getSliceProfile_AN_expDataRate());
        jsonObject.put("SliceProfile.AN.msgSizeByte", sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getSliceProfile_AN_msgSizeByte());
        jsonObject.put("SliceProfile.AN.transferIntervalTarget", sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getSliceProfile_AN_transferIntervalTarget());
        jsonObject.put("SliceProfile.AN.survivalTime", sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getSliceProfile_AN_survivalTime());
        jsonObject.put("SliceProfile.AN.ipAddress", sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getSliceProfile_AN_ipAddress());
        jsonObject.put("SliceProfile.AN.logicInterfaceId", sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getSliceProfile_AN_logicInterfaceId());
        jsonObject.put("SliceProfile.AN.nextHopInfo", sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getSliceProfile_AN_nextHopInfo());

        jsonObject.put("TN.BH.suggestNSSIId", sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getTnBhSuggestNssiId());
        jsonObject.put("TN.BH.suggestNSSIName", sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getTnBhSuggestNssiName());
        jsonObject.put("SliceProfile.TN.BH.maxBandwidth", sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getTnBhBandwidth());
        jsonObject.put("SliceProfile.TN.BH.latency", sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getTnBhLatency());
        jsonObject.put("TN.BH.ScriptName", sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getTnBhScriptName());
        jsonObject.put("TN.BH.enableNSSISelection", sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getTnEnableNSSISelection());
        jsonObject.put("SliceProfile.TN.BH.jitter", sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getSliceProfile_TN_BH_jitte());
        jsonObject.put("SliceProfile.TN.BH.pLMNIdList", sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getSliceProfile_TN_BH_pLMNIdList());
        jsonObject.put("SliceProfile.TN.BH.sNSSAI", sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getSliceProfile_TN_BH_sNSSAI());
        jsonObject.put("SliceProfile.TN.BH.sST", sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getSliceProfile_TN_BH_sST());

        jsonObject.put("CN.suggestNSSIId", sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getCnSuggestNssiId());
        jsonObject.put("CN.suggestNSSIName", sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getCnSuggestNssiName());
        jsonObject
            .put("SliceProfile.CN.sNSSAI", sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getCnServiceSnssai());
        jsonObject.put("SliceProfile.CN.resourceSharingLevel",
            sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getCnResourceSharingLevel());
        jsonObject.put("SliceProfile.CN.uEMobilityLevel",
            sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getCnUeMobilityLevel());
        jsonObject.put("SliceProfile.CN.latency", sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getCnLatency());
        jsonObject.put("SliceProfile.CN.maxNumberofUEs",
            sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getCnMaxNumberOfUes());
        jsonObject.put("SliceProfile.CN.activityFactor",
            sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getCnActivityFactor());
        jsonObject.put("SliceProfile.CN.expDataRateDL",
            sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getCnExpDataRateDl());
        jsonObject.put("SliceProfile.CN.expDataRateUL",
            sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getCnExpDataRateUl());
        jsonObject.put("SliceProfile.CN.areaTrafficCapDL",
            sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getCnAreaTrafficCapDl());
        jsonObject.put("SliceProfile.CN.areaTrafficCapUL",
            sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getCnAreaTrafficCapUl());
        jsonObject.put("CN.ScriptName", sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getCnScriptName());
        jsonObject.put("CN.enableNSSISelection", sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getCnEnableNSSISelection());
        jsonObject.put("SliceProfile.CN.maxNumberofPDUSession", sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getSliceProfile_CN_maxNumberofPDUSession());
        jsonObject.put("SliceProfile.CN.overallUserDensity", sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getSliceProfile_CN_overallUserDensity());
        jsonObject.put("SliceProfile.CN.coverageAreaTAList", sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getSliceProfile_CN_coverageAreaTAList());
        jsonObject.put("SliceProfile.CN.sST", sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getSliceProfile_CN_sST());
        jsonObject.put("SliceProfile.CN.cSAvailabilityTarget", sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getSliceProfile_CN_cSAvailabilityTarget());
        jsonObject.put("SliceProfile.CN.cSReliabilityMeanTime", sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getSliceProfile_CN_cSReliabilityMeanTime());
        jsonObject.put("SliceProfile.CN.expDataRate", sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getSliceProfile_CN_expDataRate());
        jsonObject.put("SliceProfile.CN.msgSizeByte", sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getSliceProfile_CN_msgSizeByte());
        jsonObject.put("SliceProfile.CN.transferIntervalTarget", sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getSliceProfile_CN_transferIntervalTarget());
        jsonObject.put("SliceProfile.CN.survivalTime", sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getSliceProfile_CN_survivalTime());
        jsonObject.put("SliceProfile.CN.ipAddress", sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getSliceProfile_CN_ipAddress());
        jsonObject.put("SliceProfile.CN.logicInterfaceId", sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getSliceProfile_CN_logicInterfaceId());
        jsonObject.put("SliceProfile.CN.nextHopInfo", sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getSliceProfile_CN_nextHopInfo());

        String param = jsonObject.toJSONString();
        targetSoTaskInfo.setParams(param);
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

        JSONObject paramsObject = JSON.parseObject(soTask.getParams());
        if (paramsObject == null) {
            logger.error("convertTaskCreationInfo: paramsObject is null");
            return;
        }

        BusinessDemandInfo businessDemandInfo = new BusinessDemandInfo();
        convertBusinessDemandInfo(businessDemandInfo, paramsObject);
        slicingTaskCreationInfo.setBusinessDemandInfo(businessDemandInfo);

        NstInfo nstInfo = new NstInfo();
        convertNstInfo(nstInfo, paramsObject);
        slicingTaskCreationInfo.setNstInfo(nstInfo);
    }

    void convertCreationBusinessDemandInfo(BusinessDemandInfo targetBusinessDemandInfo, JSONObject paramsObject) {
        targetBusinessDemandInfo.setServiceName(paramsObject.getString("ServiceName"));
        targetBusinessDemandInfo.setServiceSnssai(paramsObject.getString("ServiceProfile.sNSSAI"));
        targetBusinessDemandInfo.setExpDataRateDL(paramsObject.getString("ServiceProfile.expDataRateDL"));
        targetBusinessDemandInfo.setExpDataRateUL(paramsObject.getString("ServiceProfile.expDataRateUL"));
        targetBusinessDemandInfo.setUeMobilityLevel(paramsObject.getString("ServiceProfile.uEMobilityLevel"));
        targetBusinessDemandInfo.setLatency(paramsObject.getString("ServiceProfile.latency"));
        String useInterval = generalConvert.getUseInterval(paramsObject.getString("ServiceId"));
        targetBusinessDemandInfo.setUseInterval(useInterval);

        String coverageAreaTA = paramsObject.getString("ServiceProfile.coverageAreaTAList");
        targetBusinessDemandInfo.setCoverageAreaTaList(generalConvert.getAreaTaList(coverageAreaTA));
        targetBusinessDemandInfo.setActivityFactor(paramsObject.getString("ServiceProfile.activityFactor"));
        targetBusinessDemandInfo.setResourceSharingLevel(paramsObject.getString("ServiceProfile.resourceSharingLevel"));
        targetBusinessDemandInfo.setAreaTrafficCapDL(paramsObject.getString("ServiceProfile.areaTrafficCapDL"));
        targetBusinessDemandInfo.setAreaTrafficCapUL(paramsObject.getString("ServiceProfile.areaTrafficCapUL"));
        targetBusinessDemandInfo.setMaxNumberOfUEs(paramsObject.getString("ServiceProfile.maxNumberofUEs"));
    }

    void convertTaskCreationProgress(SlicingTaskCreationProgress slicingTaskCreationProgress, SOTask soTask) {

        JSONObject paramsObject = JSON.parseObject(soTask.getParams());
        if (paramsObject == null) {
            logger.error("convertTaskCreationProgress: paramsObject is null");
            return;
        }

        String anProgress = paramsObject.getString("AN.progress");
        slicingTaskCreationProgress.setAnProgress(anProgress);

        String tnProgress = paramsObject.getString("TN.BH.progress");
        slicingTaskCreationProgress.setTnProgress(tnProgress);

        String cnProgress = paramsObject.getString("CN.progress");
        slicingTaskCreationProgress.setCnProgress(cnProgress);

        String anStatus = paramsObject.getString("AN.status");
        slicingTaskCreationProgress.setAnStatus(anStatus);

        String tnStatus = paramsObject.getString("TN.BH.status");
        slicingTaskCreationProgress.setTnStatus(tnStatus);

        String cnStatus = paramsObject.getString("CN.status");
        slicingTaskCreationProgress.setCnStatus(cnStatus);

        String anStatusDescription = paramsObject.getString("AN.statusDescription");
        slicingTaskCreationProgress.setAnStatusDescription(anStatusDescription);

        String tnStatusDescription = paramsObject.getString("TN.BH.statusDescription");
        slicingTaskCreationProgress.setTnStatusDescription(tnStatusDescription);

        String cnStatusDescription = paramsObject.getString("CN.statusDescription");
        slicingTaskCreationProgress.setCnStatusDescription(cnStatusDescription);
    }
}
