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

import org.onap.usecaseui.server.service.lcm.CustomerService;
import org.onap.usecaseui.server.service.lcm.domain.aai.bean.AAICustomer;
import org.onap.usecaseui.server.service.lcm.domain.aai.bean.AAIServiceSubscription;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.List;

@Controller
@org.springframework.context.annotation.Configuration
@EnableAspectJAutoProxy
public class CustomerController {

    @Resource(name="CustomerService")
    private CustomerService customerService;

    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    @ResponseBody
    @RequestMapping(value = {"/uui-lcm/customers"}, method = RequestMethod.GET , produces = "application/json")
    public List<AAICustomer> getCustomers(){
        return customerService.listCustomer();
    }
    
    @ResponseBody
    @RequestMapping(value={"/uui-lcm/customers/{customerId}"},method = RequestMethod.PUT,produces="application/json")
    public JSONObject createOrUpdateCustomer(HttpServletRequest request,@PathVariable String customerId){
    	return customerService.createOrUpdateCustomer(request, customerId);
    }
    
    @ResponseBody
    @RequestMapping(value={"/uui-lcm/customers/{customerId}"},method = RequestMethod.GET,produces="application/json")
    public JSONObject getCustomerById(@PathVariable String customerId){
    	return customerService.getCustomerById(customerId);
    }
    
    @ResponseBody
    @RequestMapping(value={"/uui-lcm/customers"},method = RequestMethod.DELETE,produces="application/json")
    public JSONObject deleteCustomer(@RequestParam String customerId,@RequestParam String resourceVersion){
    	return customerService.deleteCustomer(customerId,resourceVersion);
    }
    
    @ResponseBody
    @RequestMapping(value = {"/uui-lcm/customers/{customerId}/service-subscriptions"}, method = RequestMethod.GET , produces = "application/json")
    public List<AAIServiceSubscription> getServiceSubscriptions(@PathVariable(value="customerId") String customerId){
        return customerService.listServiceSubscriptions(customerId);
    }
    
    @ResponseBody
    @RequestMapping(value={"/uui-lcm/customers/{customerId}/service-subscriptions/{serviceType}"},method = RequestMethod.PUT,produces="application/json")
    public JSONObject createOrUpdateServiceType(HttpServletRequest request,@PathVariable String serviceType){
    	return customerService.createOrUpdateServiceType(request, serviceType);
    }
    
    @ResponseBody
    @RequestMapping(value={"/uui-lcm/customers/{customerId}/service-subscriptions/{serviceType}"},method = RequestMethod.DELETE,produces="application/json")
    public JSONObject deleteServiceType(@PathVariable String customerId,@PathVariable String serviceType,@RequestParam String resourceVersion){
    	return customerService.deleteServiceType(customerId,serviceType,resourceVersion);
    }
    
    @ResponseBody
    @RequestMapping(value={"/uui-lcm/customers/{customerId}/service-subscriptions/{serviceType}"},method = RequestMethod.GET,produces="application/json")
    public JSONObject getServiceTypeById(@PathVariable String customerId,@PathVariable String serviceType){
    	return customerService.getServiceTypeById(customerId,serviceType);
    }
}
