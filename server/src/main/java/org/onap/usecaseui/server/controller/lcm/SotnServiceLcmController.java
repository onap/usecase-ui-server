/**
 * Copyright 2020 Huawei Corporation.
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


//import org.onap.usecaseui.server.bean.lcm.sotne2eservice.SotnServiceTemplateInput;
import org.onap.usecaseui.server.service.lcm.SotnServiceTemplateService;
import org.onap.usecaseui.server.service.lcm.domain.so.bean.DeleteOperationRsp;
import org.onap.usecaseui.server.service.lcm.domain.so.bean.ServiceOperation;
        import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Controller
@CrossOrigin(origins="*")
@org.springframework.context.annotation.Configuration
@EnableAspectJAutoProxy
public class SotnServiceLcmController {
    private static final Logger logger = LoggerFactory.getLogger(ServiceLcmController.class);

    @Resource(name = "SotnLcmService")
    private SotnServiceTemplateService sotnServiceTemplateService;
    private DeleteOperationRsp deleteOperationRsp;


    public void setServiceLcmService(SotnServiceTemplateService sotnServiceTemplateService) {
        this.sotnServiceTemplateService = sotnServiceTemplateService;
    }

    @ResponseBody
    @RequestMapping(value = {"/uui-lcm/Sotnservices/servicesubscription/{subscriptionType}/serviceinstance/{instanceid}"}, method = RequestMethod.GET, produces = "application/json")
    public String getSotnService(@PathVariable("subscriptionType") String subscriptionType, @PathVariable("instanceid") String instanceid) {
        System.out.println("Get Service called.");
        return sotnServiceTemplateService.getService(subscriptionType, instanceid);
    }

    @ResponseBody
    @RequestMapping(value = {"/uui-lcm/Sotnservices/servicesubscription/{subscriptionType}/serviceinstance/{instanceid}"}, method = RequestMethod.DELETE, produces = "application/json")
    public DeleteOperationRsp getDeleteSotnService(@PathVariable("subscriptionType") String subscriptionType, @PathVariable("instanceid") String instanceid) throws Exception {
        System.out.println("Delete Service called.");
        DeleteOperationRsp deleteOperationRsp = sotnServiceTemplateService.deleteService(subscriptionType, instanceid);
        return deleteOperationRsp;
    }

    @ResponseBody
    @RequestMapping(value = {"/uui-lcm/Sotnservices_unni"}, method = RequestMethod.POST, produces = "application/json")
    public ServiceOperation instantiateService_sotnunni(HttpServletRequest request,@RequestBody HashMap<String, Object> serviceData) {
        System.out.println("Create Service called.");
        ServiceOperation serviceOperation = sotnServiceTemplateService.instantiate_CCVPN_Service(serviceData);
        return serviceOperation;
    }

    @ResponseBody
    @RequestMapping(value = {"/uui-lcm/Sotnservices/topology/service/service-subscriptions/service-subscription/{service-type}/service-instances/service-instance/{serviceInstanceId}"}, method = RequestMethod.GET, produces = "application/json")
    public String getSiteInformationTopology(@PathVariable("service-type") String servicetype, @PathVariable("serviceInstanceId") String serviceInstanceId) throws Exception {
        System.out.println("Site topology called.");
        return sotnServiceTemplateService.getSOTNSiteInformationTopology( servicetype, serviceInstanceId);
    }

    @ResponseBody
    @RequestMapping(value = {"/uui-lcm/Sotnservices/resourceTopology/service/service-subscriptions/service-subscription/{service-type}/service-instances/service-instance/{serviceInstanceId}"}, method = RequestMethod.GET, produces = "application/json")
    public String getResourceInformationTopology(@PathVariable("service-type") String servicetype, @PathVariable("serviceInstanceId") String serviceInstanceId) throws Exception {
        System.out.println("Resource topology called.");
        return sotnServiceTemplateService.getSOTNResourceInformationTopology( servicetype, serviceInstanceId);
    }

    @ResponseBody
    @RequestMapping(value = {"/uui-lcm/Sotnservices/serviceTopology/service/service-subscriptions/service-subscription/{service-type}/service-instances/service-instance/{serviceInstanceId}"}, method = RequestMethod.GET, produces = "application/json")
    public String getServiceInformationTopology(@PathVariable("service-type") String servicetype, @PathVariable("serviceInstanceId") String serviceInstanceId) throws Exception {
        System.out.println("Service topology called.");
        return sotnServiceTemplateService.getServiceInformationTopology( servicetype, serviceInstanceId);
    }

    @ResponseBody
    @RequestMapping(value = {"/uui-lcm/Sotnservices/vpnbindingTopology/service/service-subscriptions/service-subscription/{service-type}/service-instances/service-instance/{serviceInstanceId}/vpn-informations/vpn-information/{vpinId}"}, method = RequestMethod.GET, produces = "application/json")
    public String getVpnInformationTopology(@PathVariable("service-type") String servicetype, @PathVariable("serviceInstanceId") String serviceInstanceId, @PathVariable("vpinId") String vpinId) throws Exception {
        System.out.println("Vpn Binding topology called.");
        return sotnServiceTemplateService.getVPNBindingInformationTopology( servicetype, serviceInstanceId, vpinId);
    }
}