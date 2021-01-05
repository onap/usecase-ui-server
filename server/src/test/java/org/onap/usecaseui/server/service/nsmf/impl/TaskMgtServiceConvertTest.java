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

import com.alibaba.fastjson.JSONObject;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.onap.usecaseui.server.bean.nsmf.monitor.ServiceInfo;
import org.onap.usecaseui.server.bean.nsmf.task.BusinessDemandInfo;
import org.onap.usecaseui.server.bean.nsmf.task.NsiAndSubNssiInfo;
import org.onap.usecaseui.server.bean.nsmf.task.NstInfo;
import org.onap.usecaseui.server.bean.nsmf.task.SlicingTaskAuditInfo;
import org.onap.usecaseui.server.bean.nsmf.task.SlicingTaskCreationInfo;
import org.onap.usecaseui.server.bean.nsmf.task.SlicingTaskCreationProgress;
import org.onap.usecaseui.server.bean.nsmf.task.SlicingTaskList;
import org.onap.usecaseui.server.service.slicingdomain.aai.AAISliceService;
import org.onap.usecaseui.server.service.slicingdomain.so.SOSliceService;
import org.onap.usecaseui.server.service.slicingdomain.so.bean.SOTask;
import org.onap.usecaseui.server.service.slicingdomain.so.bean.SOTaskRsp;

public class TaskMgtServiceConvertTest {

    TaskMgtServiceConvert taskMgtServiceConvert = null;
    AAISliceService aaiSliceService;

    @Before
    public void before() throws Exception {
        aaiSliceService = mock(AAISliceService.class);
        taskMgtServiceConvert = new TaskMgtServiceConvert(aaiSliceService);
    }

    @Test
    public void itCanConvertSlicingTaskList() {
        SlicingTaskList targetSlicingTaskList = new SlicingTaskList();
        SOTaskRsp soTaskRsp = new SOTaskRsp();
        SOTaskRsp soTaskRspSuc = new SOTaskRsp();
        List<SOTask> task = new ArrayList<>();
        SOTask soTask = new SOTask();
        task.add(soTask);
        soTaskRspSuc.setTask(task);

        try {
            taskMgtServiceConvert.convertSlicingTaskList(targetSlicingTaskList, soTaskRsp, 1, 100);
            taskMgtServiceConvert.convertSlicingTaskList(targetSlicingTaskList, soTaskRspSuc, 1, 100);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void itCanConvertTaskAuditInfo() {
        SlicingTaskAuditInfo targetSlicingTaskAuditInfo = new SlicingTaskAuditInfo();
        SOTask sourceSoTaskInfo = new SOTask();
        String params = "{\"globalSubscriberId\":\"5GCustomer\",\"serviceType\":\"5G\"}";
        sourceSoTaskInfo.setParams(params);

        taskMgtServiceConvert.convertTaskAuditInfo(targetSlicingTaskAuditInfo, sourceSoTaskInfo);
    }

    @Test
    public void itCanConvertBusinessDemandInfo() {
        BusinessDemandInfo targetBusinessDemandInfo = new BusinessDemandInfo();
        JSONObject paramsObject = new JSONObject();

        taskMgtServiceConvert.convertBusinessDemandInfo(targetBusinessDemandInfo, paramsObject);
    }

    @Test
    public void itCanConvertNstInfo() {
        NstInfo nstInfo = new NstInfo();
        JSONObject paramsObject = new JSONObject();

        taskMgtServiceConvert.convertNstInfo(nstInfo, paramsObject);
    }

    @Test
    public void itCanConvertNsiAndSubNssiInfo() {
        NsiAndSubNssiInfo nsiAndSubNssiInfo = new NsiAndSubNssiInfo();
        JSONObject paramsObject = new JSONObject();

        taskMgtServiceConvert.convertNsiAndSubNssiInfo(nsiAndSubNssiInfo, paramsObject);
    }

    @Test
    public void itCanConvertTaskAuditToSoTask() {

        SOTask targetSoTaskInfo = new SOTask();
        SlicingTaskAuditInfo sourceSlicingTaskAuditInfo = new SlicingTaskAuditInfo();
        taskMgtServiceConvert.convertTaskAuditToSoTask(targetSoTaskInfo, sourceSlicingTaskAuditInfo);

        SlicingTaskAuditInfo sourceSlicingTaskAuditInfoSuc = new SlicingTaskAuditInfo();
        NsiAndSubNssiInfo nsiAndSubNssiInfo = new NsiAndSubNssiInfo();
        nsiAndSubNssiInfo.setSuggestNsiName("nsi01");
        nsiAndSubNssiInfo.setSuggestNsiId("nsi01-0090-0987");

        nsiAndSubNssiInfo.setTnBhSuggestNssiName("tn-nsi-01");
        nsiAndSubNssiInfo.setTnBhSuggestNssiId("tn01-0987-iu87");
        nsiAndSubNssiInfo.setTnBhLatency("60");
        nsiAndSubNssiInfo.setTnBhBandwidth("1000");
        nsiAndSubNssiInfo.setTnBhScriptName("scriptTest");

        nsiAndSubNssiInfo.setCnUeMobilityLevel("stationary");
        nsiAndSubNssiInfo.setCnServiceSnssai("1-10101");
        nsiAndSubNssiInfo.setCnMaxNumberOfUes("1000");
        nsiAndSubNssiInfo.setCnSuggestNssiName("cn-001");
        nsiAndSubNssiInfo.setCnSuggestNssiId("0902-oi89-8923-iu34");
        nsiAndSubNssiInfo.setCnResourceSharingLevel("shared");
        nsiAndSubNssiInfo.setCnExpDataRateUl("300");
        nsiAndSubNssiInfo.setCnExpDataRateDl("600");
        nsiAndSubNssiInfo.setCnScriptName("scriptTest");

        nsiAndSubNssiInfo.setAnSuggestNssiName("an-001");
        nsiAndSubNssiInfo.setAnSuggestNssiId("0923-982-34fe-4553");
        nsiAndSubNssiInfo.setAnLatency("300");
        List<String> areaList = new ArrayList<>();
        areaList.add("gansu");
        areaList.add("linxia");
        nsiAndSubNssiInfo.setAnCoverageAreaTaList(areaList);
        nsiAndSubNssiInfo.setAn5qi("er4");
        nsiAndSubNssiInfo.setAnScriptName("scriptTest");
        sourceSlicingTaskAuditInfoSuc.setNsiAndSubNssiInfo(nsiAndSubNssiInfo);
        targetSoTaskInfo.setParams("{\n"
            + "\t\t\"ServiceId\": \"46da8cf8-0878-48ac-bea3-f2200959411a\",\n"
            + "\t\t\"ServiceName\": \"eMBB E2EService\",\n"
            + "\t\t\"NSTId\": \"46da8cf8-0878-48ac-bea3-f2200959411a\",\n"
            + "\t\t\"NSTName\": \"eMBB NST\",\n"
            + "\t\t\"AN.ScriptName\": \"an script\",\n"
            + "\t\t\"TN.ScriptName\": \"tn script\",\n"
            + "\t\t\"CN.ScriptName\": \"cn_script\",\n"
            + "\t\t\"ServiceProfile.S-NSSAI\": \"1-010101\",\n"
            + "\t\t\"ServiceProfile.sST\": \"eMBB\",\n"
            + "\t\t\"ServiceProfile.nsMaxNumberofUEs\": \"100000\",\n"
            + "\t\t\"ServiceProfile.nsCoverageAreaTAList\": \"xxxx;xxxxx;xxxxx\",\n"
            + "\t\t\"ServiceProfile.nsLatency\": \"30\",\n"
            + "\t\t\"ServiceProfile.nsUEMobilityLevel\": \"stageary\",\n"
            + "\t\t\"ServiceProfile.nsResourceSharingLevel\": \"shared\",\n"
            + "\t\t\"ServiceProfile.nsPerfReq.expDataRateDL\": \"300\",\n"
            + "\t\t\"ServiceProfile.nsPerfReq.expDataRateUL\": \"300\",\n"
            + "\t\t\"ServiceProfile.nsPerfReq.areaTrafficCapDL\": \"300\",\n"
            + "\t\t\"ServiceProfile.nsPerfReq.areaTrafficCapUL\": \"300\",\n"
            + "\t\t\"ServiceProfile.nsPerfReq.activityFactor\": \"60\",\n"
            + "\t\t\"AN.progress\": 40,\n"
            + "\t\t\"AN.status\": \"processing\",\n"
            + "\t\t\"AN.statusDescription\": \"5QI instantiating\",\n"
            + "\t\t\"TN.progress\": 30,\n"
            + "\t\t\"TN.status\": \"processing\",\n"
            + "\t\t\"TN.statusDescription\": \"L3VPN creating\",\n"
            + "\t\t\"CN.progress\": 40,\n"
            + "\t\t\"CN.status\": \"processing\",\n"
            + "\t\t\"CN.statusDescription\": \"VNF processing\"\n"
            + "\t}");
        taskMgtServiceConvert.convertTaskAuditToSoTask(targetSoTaskInfo, sourceSlicingTaskAuditInfoSuc);
    }

    @Test
    public void itCanGetAreaTaListString() {

        List<String> anCoverageAreaTaList = new ArrayList<>();
        anCoverageAreaTaList.add("Gansu");
        anCoverageAreaTaList.add("Linxia");

        taskMgtServiceConvert.getAreaTaListString(anCoverageAreaTaList, '|');
    }

    @Test
    public void itCanConvertTaskCreationInfo() {

        SlicingTaskCreationInfo slicingTaskCreationInfo = new SlicingTaskCreationInfo();
        SOTask sourceSoTaskInfo = new SOTask();
        String params = "{\"globalSubscriberId\":\"5GCustomer\",\"serviceType\":\"5G\"}";
        sourceSoTaskInfo.setParams(params);
        taskMgtServiceConvert.convertTaskCreationInfo(slicingTaskCreationInfo, sourceSoTaskInfo);
    }

    @Test
    public void itCanConvertCreationBusinessDemandInfo() {

        BusinessDemandInfo targetBusinessDemandInfo = new BusinessDemandInfo();
        JSONObject paramsObject = new JSONObject();

        taskMgtServiceConvert.convertCreationBusinessDemandInfo(targetBusinessDemandInfo, paramsObject);
    }

    @Test
    public void itCanConvertTaskCreationProgress() {

        SlicingTaskCreationProgress slicingTaskCreationProgress = new SlicingTaskCreationProgress();
        SOTask soTask = new SOTask();
        taskMgtServiceConvert.convertTaskCreationProgress(slicingTaskCreationProgress, soTask);

        SOTask soTaskSuc = new SOTask();
        soTaskSuc.setParams("{\n"
            + "\t\"AN.progress\": \"99\",\n"
            + "\t\"TN.progress\": \"99\",\n"
            + "\t\"CN.progress\": \"2020-02-10T09:22:58.000+0000\",\n"
            + "\t\"AN.status\": \"WaitingToConfirm\",\n"
            + "\t\"TN.status\": \"WaitingToConfirm\",\n"
            + "\t\"CN.status\": \"WaitingToConfirm\"\n"
            + "}");
        taskMgtServiceConvert.convertTaskCreationProgress(slicingTaskCreationProgress, soTaskSuc);
    }

}
