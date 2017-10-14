/**
 * Copyright 2016-2017 ZTE Corporation.
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
package org.onap.usecaseui.server.controller.lcm;

import org.onap.usecaseui.server.service.lcm.ServiceInstanceService;
import org.onap.usecaseui.server.service.lcm.domain.aai.bean.ServiceInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@org.springframework.context.annotation.Configuration
@EnableAspectJAutoProxy
public class ServiceInstanceController {

    private static final Logger logger = LoggerFactory.getLogger(ServiceInstanceController.class);

    @Resource(name="ServiceInstanceService")
    private ServiceInstanceService serviceInstanceService;

    public void setServiceInstanceService(ServiceInstanceService serviceInstanceService) {
        this.serviceInstanceService = serviceInstanceService;
    }

    @ResponseBody
    @RequestMapping(value = {"/uui-lcm/service-instances"}, method = RequestMethod.GET , produces = "application/json")
    public List<ServiceInstance> listServiceInstances(HttpServletRequest request){
        String customerId = request.getParameter("customerId");
        String serviceType = request.getParameter("serviceType");
        logger.info(String.format(
                "list service instances with [customerId=%s, serviceType=%s]",
                customerId,
                serviceType));

        return serviceInstanceService.listServiceInstances(customerId, serviceType);
    }
}
