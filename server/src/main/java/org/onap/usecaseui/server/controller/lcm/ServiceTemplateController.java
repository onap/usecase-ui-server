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

import java.util.List;
import jakarta.annotation.Resource;
import org.onap.usecaseui.server.bean.lcm.ServiceTemplateInput;
import org.onap.usecaseui.server.service.lcm.CustomerService;
import org.onap.usecaseui.server.service.lcm.ServiceTemplateService;
import org.onap.usecaseui.server.service.lcm.domain.aai.bean.SDNCController;
import org.onap.usecaseui.server.service.lcm.domain.aai.bean.VimInfo;
import org.onap.usecaseui.server.service.lcm.domain.sdc.bean.SDCServiceTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@org.springframework.context.annotation.Configuration
@EnableAspectJAutoProxy
public class ServiceTemplateController {

    private static final Logger logger = LoggerFactory.getLogger(ServiceTemplateController.class);

    @Resource(name="ServiceTemplateService")
    private ServiceTemplateService serviceTemplateService;

    @Resource(name="CustomerService")
        private CustomerService customerService;

    public void setServiceTemplateService(ServiceTemplateService serviceTemplateService) {
        this.serviceTemplateService = serviceTemplateService;
    }

    @ResponseBody
    @GetMapping(value = {"/uui-lcm/service-templates"} , produces = "application/json")
    public List<SDCServiceTemplate> getServiceTemplates(){
        return serviceTemplateService.listDistributedServiceTemplate();
    }

    @ResponseBody
    @GetMapping(value = {"/uui-lcm/service-templates/{uuid}"}, produces = "application/json")
    public ServiceTemplateInput getServiceTemplateInput(@PathVariable String uuid, @RequestParam String toscaModelPath){
	ServiceTemplateInput serviceTemplateInput = serviceTemplateService.fetchServiceTemplateInput(uuid, "/api"+toscaModelPath);
        return serviceTemplateInput;
    }

    @ResponseBody
    @GetMapping(value = {"/uui-lcm/locations/"}, produces = "application/json")
    public List<VimInfo> getLocations(){
        return serviceTemplateService.listVim();
    }

     @ResponseBody
         @GetMapping(value = {"/uui-lcm/getAllNI/{networkId}"}, produces = "application/json")
	     public List<String> getAllNetworkInterface(@PathVariable String networkId){
		         	List<String> nIList = customerService.fetchNIList(networkId);
				    	
	   	return nIList;
	}

    @ResponseBody
    @GetMapping(value = {"/uui-lcm/sdnc-controllers/"}, produces = "application/json")
    public List<SDNCController> getSDNCControllers(){
        return serviceTemplateService.listSDNCControllers();
    }
}
