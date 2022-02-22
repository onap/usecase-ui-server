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
import static org.onap.usecaseui.server.service.nsmf.impl.TaskMgtServiceImplTest.readJsonFile;
import static org.onap.usecaseui.server.util.CallStub.failedCall;
import static org.onap.usecaseui.server.util.CallStub.successfulCall;

import com.alibaba.fastjson.JSONObject;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
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
import org.onap.usecaseui.server.service.slicingdomain.aai.bean.connection.ConnectionLink;
import org.onap.usecaseui.server.service.slicingdomain.aai.bean.connection.ConnectionLinkList;
import org.onap.usecaseui.server.service.slicingdomain.aai.bean.connection.RelationshipList;
import org.onap.usecaseui.server.service.slicingdomain.so.SOSliceService;
import org.onap.usecaseui.server.service.slicingdomain.so.bean.AnSliceTaskInfo;
import org.onap.usecaseui.server.service.slicingdomain.so.bean.CnSliceTaskInfo;
import org.onap.usecaseui.server.service.slicingdomain.so.bean.SOTask;
import org.onap.usecaseui.server.service.slicingdomain.so.bean.SOTaskRsp;
import org.onap.usecaseui.server.service.slicingdomain.so.bean.ServiceProfile;
import org.onap.usecaseui.server.service.slicingdomain.so.bean.SliceProfile;
import org.onap.usecaseui.server.service.slicingdomain.so.bean.SliceTaskParams;
import org.onap.usecaseui.server.service.slicingdomain.so.bean.TnBHSliceTaskInfo;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class TaskMgtServiceConvertTest {

    TaskMgtServiceConvert taskMgtServiceConvert = null;
    AAISliceService aaiSliceService;
    GeneralConvertImpl generalConvert;

    @Before
    public void before() throws Exception {
        aaiSliceService = mock(AAISliceService.class);
        generalConvert = mock(GeneralConvertImpl.class);
        taskMgtServiceConvert = new TaskMgtServiceConvert(aaiSliceService);
        taskMgtServiceConvert.setGeneralConvert(generalConvert);
    }

    @Test
    public void itCanConvertSlicingTaskList() {
        SlicingTaskList targetSlicingTaskList = new SlicingTaskList();
        SOTaskRsp soTaskRsp = new SOTaskRsp();
        List<SOTask> task = new ArrayList<>();
        SOTask soTask = new SOTask();
        SliceTaskParams sliceTaskParams = new SliceTaskParams();
        ServiceProfile serviceProfile = new ServiceProfile();
        serviceProfile.setSNSSAI("111222");
        sliceTaskParams.setServiceProfile(serviceProfile);
        soTask.setSliceTaskParams(sliceTaskParams);
        soTask.setParams(getParams());
        task.add(soTask);
        soTaskRsp.setTask(task);

        try {
            taskMgtServiceConvert.convertSlicingTaskList(targetSlicingTaskList, soTaskRsp, 1, 100);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    String getParams(){
        Properties properties = System.getProperties();
        String relativelyPath = properties.getProperty("user.dir");
        String jsonFilePath = relativelyPath+"/src/test/java/org/onap/usecaseui/server/service/nsmf/impl/json/params.json";
        String params = readJsonFile(jsonFilePath);
        return params;
    }

    @Test
    public void convertSlicingTaskListWithThrowsException() {
        when(aaiSliceService.getConnectionLinks()).thenReturn(failedCall("so is not exist!"));
        SlicingTaskList targetSlicingTaskList = new SlicingTaskList();
        SOTaskRsp soTaskRsp = new SOTaskRsp();
        soTaskRsp.setTask(null);
        try {
            taskMgtServiceConvert.convertSlicingTaskList(targetSlicingTaskList, soTaskRsp, 1, 100);
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
        SliceProfile sliceProfile = new SliceProfile();
        AnSliceTaskInfo anSliceTaskInfo = new AnSliceTaskInfo();
        anSliceTaskInfo.setSliceProfile(sliceProfile);
        TnBHSliceTaskInfo tnBHSliceTaskInfo = new TnBHSliceTaskInfo();
        tnBHSliceTaskInfo.setSliceProfile(sliceProfile);
        CnSliceTaskInfo cnSliceTaskInfo = new CnSliceTaskInfo();
        cnSliceTaskInfo.setSliceProfile(sliceProfile);
        ServiceProfile serviceProfile = new ServiceProfile();
        SliceTaskParams sliceTaskParams = new SliceTaskParams();
        sliceTaskParams.setAnSliceTaskInfo(anSliceTaskInfo);
        sliceTaskParams.setTnBHSliceTaskInfo(tnBHSliceTaskInfo);
        sliceTaskParams.setCnSliceTaskInfo(cnSliceTaskInfo);
        sliceTaskParams.setServiceProfile(serviceProfile);
        sliceTaskParams.setSuggestNsiName("123");
        sourceSoTaskInfo.setParams(getParams());
        sourceSoTaskInfo.setSliceTaskParams(sliceTaskParams);

        taskMgtServiceConvert.convertTaskAuditInfo(targetSlicingTaskAuditInfo, sourceSoTaskInfo);
    }

    @Test
    public void itCanConvertBusinessDemandInfo() {
        BusinessDemandInfo targetBusinessDemandInfo = new BusinessDemandInfo();
        SliceTaskParams sliceTaskParams = new SliceTaskParams();
        ServiceProfile serviceProfile = new ServiceProfile();
        sliceTaskParams.setServiceProfile(serviceProfile);

        taskMgtServiceConvert.convertBusinessDemandInfo(targetBusinessDemandInfo, sliceTaskParams);
    }

    @Test
    public void itCanConvertNstInfo() {
        NstInfo nstInfo = new NstInfo();
        SliceTaskParams sliceTaskParams = new SliceTaskParams();

        taskMgtServiceConvert.convertNstInfo(nstInfo, sliceTaskParams);
    }

    @Test
    public void itCanConvertNsiAndSubNssiInfo() {
        NsiAndSubNssiInfo nsiAndSubNssiInfo = new NsiAndSubNssiInfo();
        SliceProfile sliceProfile = new SliceProfile();
        AnSliceTaskInfo anSliceTaskInfo = new AnSliceTaskInfo();
        anSliceTaskInfo.setSliceProfile(sliceProfile);
        TnBHSliceTaskInfo tnBHSliceTaskInfo = new TnBHSliceTaskInfo();
        tnBHSliceTaskInfo.setSliceProfile(sliceProfile);
        CnSliceTaskInfo cnSliceTaskInfo = new CnSliceTaskInfo();
        cnSliceTaskInfo.setSliceProfile(sliceProfile);
        SliceTaskParams sliceTaskParams = new SliceTaskParams();
        sliceTaskParams.setAnSliceTaskInfo(anSliceTaskInfo);
        sliceTaskParams.setTnBHSliceTaskInfo(tnBHSliceTaskInfo);
        sliceTaskParams.setCnSliceTaskInfo(cnSliceTaskInfo);

        taskMgtServiceConvert.convertNsiAndSubNssiInfo(nsiAndSubNssiInfo, sliceTaskParams);
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

        targetSoTaskInfo.setParams(getParams());
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
        SliceProfile sliceProfile = new SliceProfile();
        AnSliceTaskInfo anSliceTaskInfo = new AnSliceTaskInfo();
        anSliceTaskInfo.setSliceProfile(sliceProfile);
        TnBHSliceTaskInfo tnBHSliceTaskInfo = new TnBHSliceTaskInfo();
        tnBHSliceTaskInfo.setSliceProfile(sliceProfile);
        CnSliceTaskInfo cnSliceTaskInfo = new CnSliceTaskInfo();
        cnSliceTaskInfo.setSliceProfile(sliceProfile);
        ServiceProfile serviceProfile = new ServiceProfile();
        SliceTaskParams sliceTaskParams = new SliceTaskParams();
        sliceTaskParams.setAnSliceTaskInfo(anSliceTaskInfo);
        sliceTaskParams.setTnBHSliceTaskInfo(tnBHSliceTaskInfo);
        sliceTaskParams.setCnSliceTaskInfo(cnSliceTaskInfo);
        sliceTaskParams.setServiceProfile(serviceProfile);
        sourceSoTaskInfo.setSliceTaskParams(sliceTaskParams);
        sourceSoTaskInfo.setParams(getParams());
        taskMgtServiceConvert.convertTaskCreationInfo(slicingTaskCreationInfo, sourceSoTaskInfo);
    }

    @Test
    public void itCanConvertTaskCreationProgress() {

        SlicingTaskCreationProgress slicingTaskCreationProgress = new SlicingTaskCreationProgress();
        SOTask soTask = new SOTask();
        taskMgtServiceConvert.convertTaskCreationProgress(slicingTaskCreationProgress, soTask);

        SOTask soTaskSuc = new SOTask();
        AnSliceTaskInfo anSliceTaskInfo = new AnSliceTaskInfo();
        TnBHSliceTaskInfo tnBHSliceTaskInfo = new TnBHSliceTaskInfo();
        CnSliceTaskInfo cnSliceTaskInfo = new CnSliceTaskInfo();
        SliceTaskParams sliceTaskParams = new SliceTaskParams();
        sliceTaskParams.setAnSliceTaskInfo(anSliceTaskInfo);
        sliceTaskParams.setTnBHSliceTaskInfo(tnBHSliceTaskInfo);
        sliceTaskParams.setCnSliceTaskInfo(cnSliceTaskInfo);
        soTaskSuc.setSliceTaskParams(sliceTaskParams);
        soTaskSuc.setParams(getParams());
        taskMgtServiceConvert.convertTaskCreationProgress(slicingTaskCreationProgress, soTaskSuc);
    }

    @Test
    public void itCanQueryEndPointId() {
        ConnectionLinkList connectionLinkList = new ConnectionLinkList();
        List<ConnectionLink> logicalLink = new ArrayList<>();
        ConnectionLink connectionLink = new ConnectionLink();
        connectionLink.setLinkName("name1");
        connectionLink.setLinkName2("name2");
        connectionLink.setLinkId("123");
        RelationshipList relationshipList = new RelationshipList();
        connectionLink.setRelationshipList(relationshipList);
        logicalLink.add(connectionLink);
        connectionLinkList.setLogicalLink(logicalLink);

        when(aaiSliceService.getConnectionLinks()).thenReturn(successfulCall(connectionLinkList));
        taskMgtServiceConvert.queryEndPointId("123");
    }

    @Test
    public void queryEndPointIdWithThrowsException() {
        when(aaiSliceService.getConnectionLinks()).thenReturn(failedCall("so is not exist!"));
        taskMgtServiceConvert.queryEndPointId("123");
    }
}
