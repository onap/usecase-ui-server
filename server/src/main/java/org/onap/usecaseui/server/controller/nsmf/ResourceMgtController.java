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
import org.onap.usecaseui.server.service.nsmf.ResourceMgtService;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@CrossOrigin(origins = "*")
@org.springframework.context.annotation.Configuration
@EnableAspectJAutoProxy
@RequestMapping("/uui-slicing/nsmf/resource")
public class ResourceMgtController {

    @Resource(name = "ResourceMgtService")
    private ResourceMgtService resourceMgtService;

    public void setResourceMgtService(ResourceMgtService resourceMgtService) {
        this.resourceMgtService = resourceMgtService;
    }

    @ResponseBody
    @RequestMapping(value = {
        "/business/pageNo/{pageNo}/pageSize/{pageSize}"}, method = RequestMethod.GET, produces = "application/json")
    public ServiceResult querySlicingBusiness(@PathVariable int pageNo, @PathVariable int pageSize) {
        return resourceMgtService.querySlicingBusiness(pageNo, pageSize);
    }

    @ResponseBody
    @RequestMapping(value = {
        "/{businessStatus}/business/pageNo/{pageNo}/pageSize/{pageSize}"}, method = RequestMethod.GET, produces = "application/json")
    public ServiceResult querySlicingBusinessByStatus(@PathVariable String businessStatus, @PathVariable int pageNo,
        @PathVariable int pageSize) {
        return resourceMgtService.querySlicingBusinessByStatus(businessStatus, pageNo, pageSize);
    }

    @ResponseBody
    @RequestMapping(value = {
        "/business/{businessId}/details"}, method = RequestMethod.GET, produces = "application/json")
    public ServiceResult querySlicingBusinessDetails(@PathVariable String businessId) {
        return resourceMgtService.querySlicingBusinessDetails(businessId);
    }

    @ResponseBody
    @RequestMapping(value = {
        "/nsi/instances/pageNo/{pageNo}/pageSize/{pageSize}"}, method = RequestMethod.GET, produces = "application/json")
    public ServiceResult queryNsiInstances(@PathVariable int pageNo, @PathVariable int pageSize) {
        return resourceMgtService.queryNsiInstances(pageNo, pageSize);
    }

    @ResponseBody
    @RequestMapping(value = {
        "/nsi/{instanceStatus}/instances/pageNo/{pageNo}/pageSize/{pageSize}"}, method = RequestMethod.GET, produces = "application/json")
    public ServiceResult queryNsiInstancesByStatus(@PathVariable String instanceStatus, @PathVariable int pageNo,
        @PathVariable int pageSize) {
        return resourceMgtService.queryNsiInstancesByStatus(instanceStatus, pageNo, pageSize);
    }

    @ResponseBody
    @RequestMapping(value = {"/nsi/{nsiId}/details"}, method = RequestMethod.GET, produces = "application/json")
    public ServiceResult queryNsiDetails(@PathVariable String nsiId) {
        return resourceMgtService.queryNsiDetails(nsiId);
    }

    @ResponseBody
    @RequestMapping(value = {"/nsi/{nsiId}/nssiInstances"}, method = RequestMethod.GET, produces = "application/json")
    public ServiceResult queryNsiRelatedNssiInfo(@PathVariable String nsiId) {
        return resourceMgtService.queryNsiRelatedNssiInfo(nsiId);
    }

    @ResponseBody
    @RequestMapping(value = {
        "/nssi/instances/pageNo/{pageNo}/pageSize/{pageSize}"}, method = RequestMethod.GET, produces = "application/json")
    public ServiceResult queryNssiInstances(@PathVariable int pageNo, @PathVariable int pageSize) {
        return resourceMgtService.queryNssiInstances(pageNo, pageSize);
    }

    @ResponseBody
    @RequestMapping(value = {
        "/nssi/instanceStatus/{instanceStatus}/instances/pageNo/{pageNo}/pageSize/{pageSize}"}, method = RequestMethod.GET, produces = "application/json")
    public ServiceResult queryNssiInstancesByStatus(@PathVariable String instanceStatus, @PathVariable int pageNo,
        @PathVariable int pageSize) {
        return resourceMgtService.queryNssiInstancesByStatus(instanceStatus, pageNo, pageSize);
    }

    @ResponseBody
    @RequestMapping(value = {
        "/nssi/environmentContext/{environmentContext}/instances/pageNo/{pageNo}/pageSize/{pageSize}"}, method = RequestMethod.GET, produces = "application/json")
    public ServiceResult queryNssiInstancesByEnvironment(@PathVariable String environmentContext,
        @PathVariable int pageNo, @PathVariable int pageSize) {
        return resourceMgtService.queryNssiInstancesByEnvironment(environmentContext, pageNo, pageSize);
    }

    @ResponseBody
    @RequestMapping(value = {"/nssi/{nssiId}/details"}, method = RequestMethod.GET, produces = "application/json")
    public ServiceResult queryNssiDetails(@PathVariable String nssiId) {
        return resourceMgtService.queryNssiDetails(nssiId);
    }

    @ResponseBody
    @RequestMapping(value = {"/{serviceId}/activate"}, method = RequestMethod.PUT, produces = "application/json")
    public ServiceResult activateSlicingService(@PathVariable(value = "serviceId") String serviceId) {
        return resourceMgtService.activateSlicingService(serviceId);
    }

    @ResponseBody
    @RequestMapping(value = {"/{serviceId}/deactivate"}, method = RequestMethod.PUT, produces = "application/json")
    public ServiceResult deactivateSlicingService(@PathVariable(value = "serviceId") String serviceId) {
        return resourceMgtService.deactivateSlicingService(serviceId);
    }

    @ResponseBody
    @RequestMapping(value = {"/{serviceId}"}, method = RequestMethod.DELETE, produces = "application/json")
    public ServiceResult terminateSlicingService(@PathVariable(value = "serviceId") String serviceId) {
        return resourceMgtService.terminateSlicingService(serviceId);
    }

    @ResponseBody
    @RequestMapping(value = {"/{serviceId}/progress"}, method = RequestMethod.GET, produces = "application/json")
    public ServiceResult queryOperationProgress(@PathVariable(value = "serviceId") String serviceId) {
        return resourceMgtService.queryOperationProgress(serviceId);
    }
}
