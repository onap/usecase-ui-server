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
import org.onap.usecaseui.server.bean.nsmf.monitor.ServiceList;
import org.onap.usecaseui.server.service.nsmf.ResourceMonitorService;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@CrossOrigin(origins = "*")
@org.springframework.context.annotation.Configuration
@EnableAspectJAutoProxy
@RequestMapping("/uui-slicing/nsmf/monitoring")
public class ResourceMonitorController {

    @Resource(name = "ResourceMonitorService")
    private ResourceMonitorService resourceMonitorService;

    public void setResourceMonitorService(ResourceMonitorService resourceMonitorService) {
        this.resourceMonitorService = resourceMonitorService;
    }

    @ResponseBody
    @PostMapping(value = {
        "/queryTimestamp/{queryTimestamp}/trafficData"}, produces = "application/json")
    public ServiceResult querySlicingUsageTraffic(@PathVariable(value = "queryTimestamp") String queryTimestamp,
        @RequestBody
            ServiceList serviceList) {
        return resourceMonitorService.querySlicingUsageTraffic(queryTimestamp, serviceList);
    }

    @ResponseBody
    @PostMapping(value = {
        "/queryTimestamp/{queryTimestamp}/onlineUsers"}, produces = "application/json")
    public ServiceResult querySlicingOnlineUserNumber(@PathVariable(value = "queryTimestamp") String queryTimestamp,
        @RequestBody
            ServiceList serviceList) {
        return resourceMonitorService.querySlicingOnlineUserNumber(queryTimestamp, serviceList);
    }

    @ResponseBody
    @PostMapping(value = {
        "/queryTimestamp/{queryTimestamp}/bandwidth"}, produces = "application/json")
    public ServiceResult querySlicingTotalBandwidth(@PathVariable(value = "queryTimestamp") String queryTimestamp,
        @RequestBody
            ServiceList serviceList) {
        return resourceMonitorService.querySlicingTotalBandwidth(queryTimestamp, serviceList);
    }
}
