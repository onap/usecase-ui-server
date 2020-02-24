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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okio.BufferedSink;
import org.junit.Before;
import org.junit.Test;
import org.onap.usecaseui.server.bean.nsmf.monitor.ServiceInfo;
import org.onap.usecaseui.server.bean.nsmf.monitor.ServiceList;
import org.onap.usecaseui.server.bean.nsmf.task.SlicingTaskAuditInfo;
import org.onap.usecaseui.server.service.slicingdomain.kpi.KpiSliceService;
import org.onap.usecaseui.server.service.slicingdomain.kpi.bean.KpiTotalTraffic;
import org.onap.usecaseui.server.service.slicingdomain.so.SOSliceService;
import org.onap.usecaseui.server.service.slicingdomain.so.bean.SOTask;

public class TaskMgtServiceImplTest {

    TaskMgtServiceImpl taskMgtService = null;
    SOSliceService soSliceService = null;

    @Before
    public void before() throws Exception {
        soSliceService = mock(SOSliceService.class);
        taskMgtService = new TaskMgtServiceImpl(soSliceService);
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

        ResponseBody responseBody = null;
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


}