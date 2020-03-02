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
    // @RequestMapping(value = {"ccvpncustomer/servicesubscriptions/uui-lcm/Sotnservices/servicesubscription/{subscriptionType}/serviceinstance/{instanceid}"}, method = RequestMethod.GET, produces = "application/json")
    public String getSotnService(@PathVariable("subscriptionType") String subscriptionType, @PathVariable("instanceid") String instanceid) {

        return sotnServiceTemplateService.getService(subscriptionType, instanceid);

        //return serviceOperation;
    }

    @ResponseBody
    @RequestMapping(value = {"/uui-lcm/Sotnservices_unni"}, method = RequestMethod.POST, produces = "application/json")
    public ServiceOperation instantiateService_sotnunni(HttpServletRequest request,@RequestBody HashMap<String, Object> mp) {
        System.out.println("request body  == "+mp);
        //ServiceOperation serviceOperation = sotnServiceTemplateService.instantiateService_sotnunni(mp);

        ServiceOperation serviceOperation = sotnServiceTemplateService.instantiate_CCVPN_Service(mp);

        return serviceOperation;
    }



    @ResponseBody
    @RequestMapping(value = {"/uui-lcm/Sotnservices/topology/service/service-subscriptions/service-subscription/{service-type}/service-instances/service-instance/{serviceInstanceId}"}, method = RequestMethod.GET, produces = "application/json")
    public String getSiteInformationTopology(@PathVariable("service-type") String servicetype, @PathVariable("serviceInstanceId") String serviceInstanceId) throws Exception {
        return sotnServiceTemplateService.getSOTNSiteInformationTopology( servicetype, serviceInstanceId);
    }

    @ResponseBody
    @RequestMapping(value = {"/uui-lcm/Sotnservices/resourceTopology/service/service-subscriptions/service-subscription/{service-type}/service-instances/service-instance/{serviceInstanceId}"}, method = RequestMethod.GET, produces = "application/json")
    public String getResourceInformationTopology(@PathVariable("service-type") String servicetype, @PathVariable("serviceInstanceId") String serviceInstanceId) throws Exception {
        return sotnServiceTemplateService.getSOTNResourceInformationTopology( servicetype, serviceInstanceId);
    }


}