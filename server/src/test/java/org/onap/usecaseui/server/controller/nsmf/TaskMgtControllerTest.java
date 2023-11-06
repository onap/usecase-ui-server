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
package org.onap.usecaseui.server.controller.nsmf;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.onap.usecaseui.server.bean.nsmf.task.SlicingTaskAuditInfo;
import org.onap.usecaseui.server.service.nsmf.TaskMgtService;

public class TaskMgtControllerTest {

    @Test
    public void testQuerySlicingTask() {
        TaskMgtService taskMgtService = mock(TaskMgtService.class);
        TaskMgtController taskMgtController = new TaskMgtController();
        taskMgtController.setTaskMgtService(taskMgtService);

        taskMgtController.querySlicingTask(1, 10);
        verify(taskMgtService, times(1)).querySlicingTask(1, 10);
    }

    @Test
    public void testQuerySlicingTaskByStatus() {
        TaskMgtService taskMgtService = mock(TaskMgtService.class);
        TaskMgtController taskMgtController = new TaskMgtController();
        taskMgtController.setTaskMgtService(taskMgtService);

        taskMgtController.querySlicingTaskByStatus("Planning", 1, 10);
        verify(taskMgtService, times(1)).querySlicingTaskByStatus("Planning", 1, 10);
    }

    @Test
    public void testQueryTaskAuditInfo() {
        TaskMgtService taskMgtService = mock(TaskMgtService.class);
        TaskMgtController taskMgtController = new TaskMgtController();
        taskMgtController.setTaskMgtService(taskMgtService);

        taskMgtController.queryTaskAuditInfo("oou9-876e-jhy7-uy78");
        verify(taskMgtService, times(1)).queryTaskAuditInfo("oou9-876e-jhy7-uy78");
    }

    @Test
    public void testUpdateTaskAuditInfo() {
        TaskMgtService taskMgtService = mock(TaskMgtService.class);
        TaskMgtController taskMgtController = new TaskMgtController();
        taskMgtController.setTaskMgtService(taskMgtService);

        SlicingTaskAuditInfo slicingTaskAuditInfo = new SlicingTaskAuditInfo();
        taskMgtController.updateTaskAuditInfo(slicingTaskAuditInfo);
        verify(taskMgtService, times(1)).updateTaskAuditInfo(slicingTaskAuditInfo);
    }

    @Test
    public void testQueryTaskCreationInfo() {
        TaskMgtService taskMgtService = mock(TaskMgtService.class);
        TaskMgtController taskMgtController = new TaskMgtController();
        taskMgtController.setTaskMgtService(taskMgtService);

        taskMgtController.queryTaskCreationInfo("oou9-876e-jhy7-uy78");
        verify(taskMgtService, times(1)).queryTaskCreationInfo("oou9-876e-jhy7-uy78");
    }

    @Test
    public void testQueryTaskCreationProgress() {
        TaskMgtService taskMgtService = mock(TaskMgtService.class);
        TaskMgtController taskMgtController = new TaskMgtController();
        taskMgtController.setTaskMgtService(taskMgtService);

        taskMgtController.queryTaskCreationProgress("oou9-876e-jhy7-uy78");
        verify(taskMgtService, times(1)).queryTaskCreationProgress("oou9-876e-jhy7-uy78");
    }

}
