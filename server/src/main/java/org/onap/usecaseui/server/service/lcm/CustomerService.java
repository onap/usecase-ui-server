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
package org.onap.usecaseui.server.service.lcm;

import org.onap.usecaseui.server.service.lcm.domain.aai.bean.AAICustomer;
import org.onap.usecaseui.server.service.lcm.domain.aai.bean.AAIServiceSubscription;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

public interface CustomerService {
	
    List<AAICustomer> listCustomer();
    
    JSONObject createOrUpdateCustomer(HttpServletRequest request,String customerId);
    
    JSONObject deleteCustomer(String customerId,String resourceVersion);
    
    JSONObject getCustomerById(String customerId);
    
    List<AAIServiceSubscription> listServiceSubscriptions(String customerId);
    
    JSONObject createOrUpdateServiceType(HttpServletRequest request,String serviceType,String customerId);
    
    JSONObject deleteServiceType(String customerId,String serviceType,String resourceVersion);
    
    JSONObject getServiceTypeById(String customerId,String serviceType);

    List<String> fetchNIList(String networkInterfaceType);
}
