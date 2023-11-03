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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

@Controller
@CrossOrigin(origins="*")
@org.springframework.context.annotation.Configuration
@EnableAspectJAutoProxy
public class CustomerController {

    @Resource(name="CustomerService")
    private CustomerService customerService;

    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    @ResponseBody
    @GetMapping(value = {"/uui-lcm/customers"} , produces = "application/json")
    public List<AAICustomer> getCustomers(){
        return customerService.listCustomer();
    }
    
    @ResponseBody
    @PutMapping(value={"/uui-lcm/customers/{customerId}"},produces="application/json")
    public JSONObject createOrUpdateCustomer(HttpServletRequest request,@PathVariable String customerId){
    	return customerService.createOrUpdateCustomer(request, customerId);
    }
    
    @ResponseBody
    @GetMapping(value={"/uui-lcm/customers/{customerId}"},produces="application/json")
    public JSONObject getCustomerById(@PathVariable String customerId){
    	return customerService.getCustomerById(customerId);
    }
    
    @ResponseBody
    @DeleteMapping(value={"/uui-lcm/customers"},produces="application/json")
    public JSONObject deleteCustomer(@RequestParam String customerId,@RequestParam String resourceVersion){
    	return customerService.deleteCustomer(customerId,resourceVersion);
    }
    
    @ResponseBody
    @GetMapping(value = {"/uui-lcm/customers/{customerId}/service-subscriptions"} , produces = "application/json")
    public List<AAIServiceSubscription> getServiceSubscriptions(@PathVariable(value="customerId") String customerId){
        return customerService.listServiceSubscriptions(customerId);
    }
    
    @ResponseBody
    @PutMapping(value={"/uui-lcm/customers/{customerId}/service-subscriptions/{serviceType}"},produces="application/json")
    public JSONObject createOrUpdateServiceType(HttpServletRequest request,@PathVariable String serviceType,@PathVariable String customerId){
    	return customerService.createOrUpdateServiceType(request, serviceType,customerId);
    }
    
    @ResponseBody
    @DeleteMapping(value={"/uui-lcm/customers/{customerId}/service-subscriptions/{serviceType}"},produces="application/json")
    public JSONObject deleteServiceType(@PathVariable String customerId,@PathVariable String serviceType,@RequestParam String resourceVersion){
    	return customerService.deleteServiceType(customerId,serviceType,resourceVersion);
    }
    
    @ResponseBody
    @GetMapping(value={"/uui-lcm/customers/{customerId}/service-subscriptions/{serviceType}"}, produces="application/json")
    public JSONObject getServiceTypeById(@PathVariable String customerId,@PathVariable String serviceType){
    	return customerService.getServiceTypeById(customerId,serviceType);
    }
}
