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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import jakarta.annotation.Nullable;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okio.BufferedSink;
import okio.BufferedSource;
import org.junit.Before;
import org.junit.Test;
import org.onap.usecaseui.server.bean.nsmf.task.SlicingTaskAuditInfo;
import org.onap.usecaseui.server.service.slicingdomain.aai.bean.connection.ConnectionLink;
import org.onap.usecaseui.server.service.slicingdomain.aai.bean.connection.EndPointInfoList;
import org.onap.usecaseui.server.service.slicingdomain.aai.bean.connection.Relationship;
import org.onap.usecaseui.server.service.slicingdomain.aai.bean.connection.RelationshipData;
import org.onap.usecaseui.server.service.slicingdomain.aai.bean.connection.RelationshipList;
import org.onap.usecaseui.server.service.slicingdomain.so.SOSliceService;
import org.onap.usecaseui.server.service.slicingdomain.so.bean.SOTask;
import org.onap.usecaseui.server.service.slicingdomain.aai.AAISliceClient;
import org.onap.usecaseui.server.service.slicingdomain.aai.bean.connection.ConnectionLinkList;

public class TaskMgtServiceImplTest {

    TaskMgtServiceImpl taskMgtService = null;
    SOSliceService soSliceService = null;
    AAISliceClient aaiSliceClient = null;
    TaskMgtServiceConvert taskMgtServiceConvert = null;

    @Before
    public void before() throws Exception {
        soSliceService = mock(SOSliceService.class);
        aaiSliceClient = mock(AAISliceClient.class);
        taskMgtService = new TaskMgtServiceImpl(soSliceService,aaiSliceClient);
        taskMgtServiceConvert = mock(TaskMgtServiceConvert.class);
        taskMgtService.taskMgtServiceConvert = taskMgtServiceConvert;
    }

    @Test
    public void itCanQuerySlicingTask() {
        JSONArray jsonArray = new JSONArray();
        when(soSliceService.listTask()).thenReturn(successfulCall(jsonArray));
        taskMgtService.querySlicingTask(1, 100);
    }

    @Test
    public void querySlicingTaskWithThrowsException() {
        when(soSliceService.listTask()).thenReturn(failedCall("so is not exist!"));
        taskMgtService.querySlicingTask(1, 100);
    }

    @Test
    public void itCanQuerySlicingTaskByStatus() {
        JSONArray jsonArray = new JSONArray();
        String status = "Planning";
        when(soSliceService.listTaskByStage(status)).thenReturn(successfulCall(jsonArray));
        taskMgtService.querySlicingTaskByStatus(status, 1, 100);
    }

    @Test
    public void querySlicingTaskByStatusWithThrowsException() {
        String status = "Planning";
        when(soSliceService.listTaskByStage(status)).thenReturn(failedCall("so is not exist!"));
        taskMgtService.querySlicingTaskByStatus(status, 1, 100);
    }

    @Test
    public void itCanQueryTaskAuditInfo() {
        String taskId = "we23-345r-45ty-5687";
        SOTask soTask = new SOTask();
        when(soSliceService.getTaskById(taskId)).thenReturn(successfulCall(soTask));
        taskMgtService.queryTaskAuditInfo(taskId);
    }

    @Test
    public void queryTaskAuditInfoWithThrowsException() {
        String taskId = "we23-345r-45ty-5687";
        when(soSliceService.getTaskById(taskId)).thenReturn(failedCall("so is not exist!"));
        taskMgtService.queryTaskAuditInfo(taskId);
    }

    @Test
    public void itCanUpdateTaskAuditInfo() {
        SlicingTaskAuditInfo slicingTaskAuditInfo = new SlicingTaskAuditInfo();
        String taskId = "we23-3456-rte4-er43";
        slicingTaskAuditInfo.setTaskId(taskId);

        Properties properties = System.getProperties();
        String relativelyPath = properties.getProperty("user.dir");
        String jsonFilePath = relativelyPath+"/src/test/java/org/onap/usecaseui/server/service/nsmf/impl/json/params.json";
        String params = readJsonFile(jsonFilePath);

        ResponseBody responseBody = new ResponseBody() {
            @Nullable
            @Override
            public MediaType contentType() {
                return null;
            }

            @Override
            public long contentLength() {
                return 0;
            }

            @Override
            public BufferedSource source() {
                return null;
            }
        };
        SOTask soTask = new SOTask();
        soTask.setParams(params);
        String jsonstr = JSON.toJSONString(soTask);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonstr);
        when(soSliceService.getTaskById(taskId)).thenReturn(successfulCall(soTask));
        when(soSliceService.updateService(taskId, requestBody)).thenReturn(successfulCall(responseBody));

        taskMgtService.updateTaskAuditInfo(slicingTaskAuditInfo);
    }

    @Test
    public void updateTaskAuditInfoWithThrowsException() {
        SlicingTaskAuditInfo slicingTaskAuditInfo = new SlicingTaskAuditInfo();
        String taskId = "we23-3456-rte4-er43";
        RequestBody requestBody = new RequestBody() {
            @Nullable
            @Override
            public MediaType contentType() {
                return null;
            }

            @Override
            public void writeTo(BufferedSink bufferedSink) throws IOException {

            }
        };

        when(soSliceService.updateService(taskId, requestBody)).thenReturn(failedCall("so is not exist!"));
        taskMgtService.updateTaskAuditInfo(slicingTaskAuditInfo);
    }

    @Test
    public void itCanQueryTaskCreationInfo() {
        String taskId = "we23-345r-45ty-5687";
        SOTask soTask = new SOTask();
        when(soSliceService.getTaskByIdD(taskId)).thenReturn(successfulCall(soTask));
        taskMgtService.queryTaskCreationInfo(taskId);
    }

    @Test
    public void queryTaskCreationInfoWithThrowsException() {
        String taskId = "we23-345r-45ty-5687";
        when(soSliceService.getTaskByIdD(taskId)).thenReturn(failedCall("so is not exist!"));
        taskMgtService.queryTaskCreationInfo(taskId);
    }

    @Test
    public void itCanQueryTaskCreationProgress() {
        String taskId = "we23-345r-45ty-5687";
        SOTask soTask = new SOTask();
        when(soSliceService.getTaskByIdD(taskId)).thenReturn(successfulCall(soTask));
        taskMgtService.queryTaskCreationProgress(taskId);
    }

    @Test
    public void queryTaskCreationProgressWithThrowsException() {
        String taskId = "we23-345r-45ty-5687";
        when(soSliceService.getTaskByIdD(taskId)).thenReturn(failedCall("so is not exist!"));
        taskMgtService.queryTaskCreationProgress(taskId);
    }

    @Test
    public void queryConnectionLinksWithThrowsException() {
        String taskId = "we23-345r-45ty-5687";
        when(aaiSliceClient.getConnectionLinks()).thenReturn(failedCall("so is not exist!"));
        taskMgtService.queryConnectionLinks(3,5);
    }

    @Test
    public void itCanqueryConnectionLinks() {
        String taskId = "we23-345r-45ty-5687";
        ConnectionLinkList connectionLinkList = new ConnectionLinkList();
        List<ConnectionLink> connectionLinks = new ArrayList<>();
        ConnectionLink connectionLink = new ConnectionLink();
        connectionLink.setLinkType("TsciConnectionLink");
        connectionLink.setLinkId(taskId);
        connectionLink.setLinkName2("name2");
        connectionLink.setLinkName("name1");
        RelationshipList relationshipList = new RelationshipList();
        List<Relationship> relationships = new ArrayList<>();
        Relationship relationship1 = new Relationship();
        List<RelationshipData> relationshipDataList = new ArrayList<>();
        relationship1.setRelationshipDataList(relationshipDataList);
        relationships.add(relationship1);
        relationshipList.setRelationship(relationships);

        connectionLink.setRelationshipList(relationshipList);
        connectionLink.setServiceFunction("servicefunction");
        connectionLink.setResourceVersion("resouceversion");
        connectionLinks.add(connectionLink);
        connectionLinkList.setLogicalLink(connectionLinks);
        String name1 = "name1";
        String name2 = "name2";
        EndPointInfoList endPointInfoList = new EndPointInfoList();

        when(aaiSliceClient.getConnectionLinks()).thenReturn(successfulCall(connectionLinkList));
        when(aaiSliceClient.getEndpointByLinkName(name1)).thenReturn(successfulCall(endPointInfoList));
        when(aaiSliceClient.getEndpointByLinkName2(name2)).thenReturn(successfulCall(endPointInfoList));
        when(aaiSliceClient.getAllottedResource(taskId, taskId)).thenReturn(successfulCall(connectionLink));

        taskMgtService.queryConnectionLinks(3,5);
    }

    //read json file
    public static String readJsonFile(String fileName) {
        String jsonStr = "";
        try {
            File jsonFile = new File(fileName);
            FileReader fileReader = new FileReader(jsonFile);
            Reader reader = new InputStreamReader(new FileInputStream(jsonFile),"utf-8");
            int ch = 0;
            StringBuffer sb = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();
            jsonStr = sb.toString();
            return jsonStr;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
