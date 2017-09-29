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

import org.onap.usecaseui.server.bean.lcm.ServiceTemplateInputRsp;
import org.onap.usecaseui.server.service.lcm.ServiceTemplateService;
import org.onap.usecaseui.server.service.lcm.domain.sdc.bean.SDCServiceTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Controller
@org.springframework.context.annotation.Configuration
@EnableAspectJAutoProxy
public class ServiceTemplateController {

    private static final Logger logger = LoggerFactory.getLogger(ServiceTemplateController.class);

    @Resource(name="ServiceTemplateService")
    private ServiceTemplateService serviceTemplateService;

    public void setServiceTemplateService(ServiceTemplateService serviceTemplateService) {
        this.serviceTemplateService = serviceTemplateService;
    }

    @ResponseBody
    @RequestMapping(value = {"/lcm/service-templates"}, method = RequestMethod.GET , produces = "application/json")
    public List<SDCServiceTemplate> getServiceTemplates(){
        return serviceTemplateService.listDistributedServiceTemplate();
    }

    @ResponseBody
    @RequestMapping(value = {"/lcm/service-templates/service-template/{uuid}"}, method = RequestMethod.GET , produces = "application/json")
    public ServiceTemplateInputRsp getServiceTemplateInput(@PathVariable("uuid") String uuid, @RequestParam("toscaModelPath") String toscaModelPath){
        return serviceTemplateService.fetchServiceTemplateInput(uuid, toscaModelPath);
    }
}
