/*
 * Copyright (C) 2020 CMCC, Inc. and others. All rights reserved.
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
package org.onap.usecaseui.server.controller.csmf;

import jakarta.annotation.Resource;
import org.onap.usecaseui.server.bean.csmf.SlicingOrder;
import org.onap.usecaseui.server.bean.nsmf.common.ServiceResult;
import org.onap.usecaseui.server.service.csmf.SlicingService;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@CrossOrigin(origins = "*")
@org.springframework.context.annotation.Configuration
@EnableAspectJAutoProxy
@RequestMapping("/uui-slicing/csmf")
public class SlicingController {

    @Resource(name = "SlicingService")
    private SlicingService slicingService;

    public void setSlicingService(SlicingService slicingService) {
        this.slicingService = slicingService;
    }

    @ResponseBody
    @PostMapping(
        value = {"/5gSlicing"},
        produces = "application/json")
    public ServiceResult createSlicingService(@RequestBody SlicingOrder slicingOrder) {
        return slicingService.createSlicingService(slicingOrder);
    }

    @ResponseBody
    @GetMapping(
        value = {"/5gSlicing/orders/status/{status}/pageNo/{pageNo}/pageSize/{pageSize}"},
        produces = "application/json")
    public ServiceResult querySlicingServiceOrder(
        @PathVariable(value="status") String status,
        @PathVariable(value="pageNo") String pageNo,
        @PathVariable(value="pageSize") String pageSize) {
        return slicingService.querySlicingOrderList(status, pageNo, pageSize);
    }

}
