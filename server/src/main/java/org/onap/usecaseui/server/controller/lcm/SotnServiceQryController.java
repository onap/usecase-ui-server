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

import org.onap.usecaseui.server.service.customer.CcvpnCustomerService;
import org.onap.usecaseui.server.service.lcm.CustomerService;
import org.onap.usecaseui.server.service.lcm.SotnServiceQryService;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.annotation.Resource;

@Controller
@org.springframework.context.annotation.Configuration
@EnableAspectJAutoProxy
public class SotnServiceQryController {
    private static final Logger logger = LoggerFactory.getLogger(SotnServiceQryController.class);

    @Resource(name="SotnServiceQry")
    private SotnServiceQryService sotnServiceQryService;

    @Resource(name="CcvpnCustomerService")
    public CcvpnCustomerService ccvpnCustomerService;

    @Resource(name="CustomerService")
    private CustomerService customerService;

    public void setServiceLcmService(SotnServiceQryService sotnServiceQryService, CcvpnCustomerService ccvpnCustomerService) {
        this.sotnServiceQryService = sotnServiceQryService;
        this.ccvpnCustomerService = ccvpnCustomerService;
    }

    @ResponseBody
    @GetMapping(value = {"/uui-lcm/Sotnservices/ServiceInstances/{serviceType}"}, produces = "application/json")
    public String getSotnServiceInstances(@PathVariable(value="serviceType") String serviceType){
        System.out.println("Get Service Instance called.");
        String response = sotnServiceQryService.getServiceInstances(serviceType);
        return response;
    }

    @ResponseBody
    @GetMapping(value = {"/uui-lcm/customers/service-subscriptions"}, produces = "application/json")
    public String getServiceSubscriptions(){
        System.out.println("Get Service Subscription called.");
        String response = ccvpnCustomerService.querySubscriptionType();
        return response;
    }

}
