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

public class TaskMgtServiceConvert {

    private static final Logger logger = LoggerFactory.getLogger(TaskMgtServiceConvert.class);

    private GeneralConvertImpl generalConvert = new GeneralConvertImpl();
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

        nsiAndSubNssiInfo.setTnSuggestNssiId(paramsObject.getString("TN.suggestNSSIId"));
        nsiAndSubNssiInfo.setTnSuggestNssiName(paramsObject.getString("TN.suggestNSSIName"));
        nsiAndSubNssiInfo.setTnLatency(paramsObject.getString("SliceProfile.TN.latency"));
        nsiAndSubNssiInfo.setTnBandwidth(paramsObject.getString("SliceProfile.TN.bandwidth"));
        nsiAndSubNssiInfo.setTnScriptName(paramsObject.getString("TN.ScriptName"));

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

        jsonObject.put("TN.suggestNSSIId", sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getTnSuggestNssiId());
        jsonObject.put("TN.suggestNSSIName", sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getTnSuggestNssiName());
        jsonObject.put("SliceProfile.TN.bandwidth", sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getTnBandwidth());
        jsonObject.put("SliceProfile.TN.latency", sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getTnLatency());
        jsonObject.put("TN.ScriptName", sourceSlicingTaskAuditInfo.getNsiAndSubNssiInfo().getTnScriptName());

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
        convertCreationBusinessDemandInfo(businessDemandInfo, paramsObject);
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

        String tnProgress = paramsObject.getString("TN.progress");
        slicingTaskCreationProgress.setTnProgress(tnProgress);

        String cnProgress = paramsObject.getString("CN.progress");
        slicingTaskCreationProgress.setCnProgress(cnProgress);

        String anStatus = paramsObject.getString("AN.status");
        slicingTaskCreationProgress.setAnStatus(anStatus);

        String tnStatus = paramsObject.getString("TN.status");
        slicingTaskCreationProgress.setTnStatus(tnStatus);

        String cnStatus = paramsObject.getString("CN.status");
        slicingTaskCreationProgress.setCnStatus(cnStatus);
    }
}
