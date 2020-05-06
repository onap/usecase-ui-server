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

import javax.annotation.Resource;
import org.onap.usecaseui.server.bean.nsmf.common.ServiceResult;
import org.onap.usecaseui.server.bean.nsmf.task.SlicingTaskAuditInfo;
import org.onap.usecaseui.server.service.nsmf.TaskMgtService;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@CrossOrigin(origins = "*")
@org.springframework.context.annotation.Configuration
@EnableAspectJAutoProxy
@RequestMapping("/uui-slicing/nsmf/task")
public class TaskMgtController {

    @Resource(name = "TaskMgtService")
    private TaskMgtService taskMgtService;

    public void setTaskMgtService(TaskMgtService taskMgtService) {
        this.taskMgtService = taskMgtService;
    }

    @ResponseBody
    @GetMapping(value = {
        "/business/pageNo/{pageNo}/pageSize/{pageSize}"}, produces = "application/json")
    public ServiceResult querySlicingTask(@PathVariable int pageNo, @PathVariable int pageSize) {
        return taskMgtService.querySlicingTask(pageNo, pageSize);
    }

    @ResponseBody
    @GetMapping(value = {
        "/{processingStatus}/business/pageNo/{pageNo}/pageSize/{pageSize}"}, produces = "application/json")
    public ServiceResult querySlicingTaskByStatus(@PathVariable String processingStatus, @PathVariable int pageNo,
        @PathVariable int pageSize) {
        return taskMgtService.querySlicingTaskByStatus(processingStatus, pageNo, pageSize);
    }

    @ResponseBody
    @GetMapping(value = {"/{taskId}/auditInfo"}, produces = "application/json")
    public ServiceResult queryTaskAuditInfo(@PathVariable String taskId) {
        return taskMgtService.queryTaskAuditInfo(taskId);
    }

    @ResponseBody
    @PutMapping(value = {"/auditInfo"}, produces = "application/json")
    public ServiceResult updateTaskAuditInfo(@RequestBody SlicingTaskAuditInfo slicingTaskAuditInfo) {
        return taskMgtService.updateTaskAuditInfo(slicingTaskAuditInfo);
    }

    @ResponseBody
    @GetMapping(value = {"/{taskId}/taskCreationInfo"}, produces = "application/json")
    public ServiceResult queryTaskCreationInfo(@PathVariable String taskId) {
        return taskMgtService.queryTaskCreationInfo(taskId);
    }

    @ResponseBody
    @GetMapping(value = {
        "/{taskId}/taskCreationProgress"}, produces = "application/json")
    public ServiceResult queryTaskCreationProgress(@PathVariable String taskId) {
        return taskMgtService.queryTaskCreationProgress(taskId);
    }

}
