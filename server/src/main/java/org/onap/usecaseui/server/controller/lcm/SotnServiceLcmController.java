/**
 * Copyright 2020 Huawei Corporation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.onap.usecaseui.server.controller.lcm;


import org.onap.usecaseui.server.service.lcm.SotnServiceTemplateService;
import org.onap.usecaseui.server.service.lcm.domain.so.bean.DeleteOperationRsp;
import org.onap.usecaseui.server.service.lcm.domain.so.bean.ServiceOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Controller
@CrossOrigin(origins = "*")
@org.springframework.context.annotation.Configuration
@EnableAspectJAutoProxy
public class SotnServiceLcmController {
    private static final Logger logger = LoggerFactory.getLogger(SotnServiceLcmController.class);

    @Resource(name = "SotnLcmService")
    private SotnServiceTemplateService sotnServiceTemplateService;
    private DeleteOperationRsp deleteOperationRsp;


    public void setServiceLcmService(SotnServiceTemplateService sotnServiceTemplateService) {
        this.sotnServiceTemplateService = sotnServiceTemplateService;
    }

    @ResponseBody
    @GetMapping(
            value = {"/uui-lcm/Sotnservices/servicesubscription/{subscriptionType}/serviceinstance/{instanceid}"},
            produces = "application/json")
    public String getSotnService(@PathVariable("subscriptionType") String subscriptionType,
            @PathVariable("instanceid") String instanceid) {
        logger.info("Get Service called");
        return sotnServiceTemplateService.getService(subscriptionType, instanceid);
    }

    @ResponseBody
    @DeleteMapping(
            value = {"/uui-lcm/Sotnservices/servicesubscription/{subscriptionType}/serviceinstance/{instanceid}"},
            produces = "application/json")
    public DeleteOperationRsp getDeleteSotnService(@PathVariable("subscriptionType") String subscriptionType,
            @PathVariable("instanceid") String instanceid) throws Exception {
        logger.info("Delete Service called");
        DeleteOperationRsp deleteOperationRsp = sotnServiceTemplateService.deleteService(subscriptionType, instanceid);
        return deleteOperationRsp;
    }

    @ResponseBody
    @PostMapping(value = {"/uui-lcm/Sotnservices_unni"}, produces = "application/json")
    public ServiceOperation instantiateService_sotnunni(HttpServletRequest request,
            @RequestBody HashMap<String, Object> serviceData) {
        logger.info("Create Service called");
        ServiceOperation serviceOperation = sotnServiceTemplateService.instantiate_CCVPN_Service(serviceData);
        return serviceOperation;
    }

    @ResponseBody
    @GetMapping(value = {
            "/uui-lcm/Sotnservices/topology/service/service-subscriptions/service-subscription/{service-type}/service-instances/service-instance/{serviceInstanceId}"},
            produces = "application/json")
    public String getSiteInformationTopology(@PathVariable("service-type") String servicetype,
            @PathVariable("serviceInstanceId") String serviceInstanceId) throws Exception {
        logger.info("Site topology called.");
        return sotnServiceTemplateService.getSOTNSiteInformationTopology(servicetype, serviceInstanceId);
    }

    @ResponseBody
    @GetMapping(value = {
            "/uui-lcm/Sotnservices/resourceTopology/service/service-subscriptions/service-subscription/{service-type}/service-instances/service-instance/{serviceInstanceId}"},
            produces = "application/json")
    public String getResourceInformationTopology(@PathVariable("service-type") String servicetype,
            @PathVariable("serviceInstanceId") String serviceInstanceId) throws Exception {
        logger.info("Resource topology called.");
        return sotnServiceTemplateService.getSOTNResourceInformationTopology(servicetype, serviceInstanceId);
    }

    @ResponseBody
    @GetMapping(value = {
            "/uui-lcm/Sotnservices/serviceTopology/service/service-subscriptions/service-subscription/{service-type}/service-instances/service-instance/{serviceInstanceId}"},
            produces = "application/json")
    public String getServiceInformationTopology(@PathVariable("service-type") String servicetype,
            @PathVariable("serviceInstanceId") String serviceInstanceId) throws Exception {
        logger.info("Service topology called.");
        return sotnServiceTemplateService.getServiceInformationTopology(servicetype, serviceInstanceId);
    }

    @ResponseBody
    @GetMapping(value = {
            "/uui-lcm/Sotnservices/vpnbindingTopology/service/service-subscriptions/service-subscription/{service-type}/service-instances/service-instance/{serviceInstanceId}/vpn-informations/vpn-information/{vpinId}"},
             produces = "application/json")
    public String getVpnInformationTopology(@PathVariable("service-type") String servicetype,
            @PathVariable("serviceInstanceId") String serviceInstanceId, @PathVariable("vpinId") String vpinId)
            throws Exception {
        logger.info("Vpn Binding topology called.");
        return sotnServiceTemplateService.getVPNBindingInformationTopology(servicetype, serviceInstanceId, vpinId);
    }

    @ResponseBody
    @GetMapping(value = {"/uui-lcm/Sotnservices/serviceStatus/service-instance/{instanceid}"}, produces = "application/json")
    public String getSotnServicestatus(@PathVariable("instanceid") String instanceid) {
        logger.info("service status called.");
        return sotnServiceTemplateService.getSOTNInstantiationstatus(instanceid);
    }
}
