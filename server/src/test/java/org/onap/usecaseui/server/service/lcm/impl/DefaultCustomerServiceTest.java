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
package org.onap.usecaseui.server.service.lcm.impl;

import static java.util.Collections.singletonList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.onap.usecaseui.server.util.CallStub.emptyBodyCall;
import static org.onap.usecaseui.server.util.CallStub.failedCall;
import static org.onap.usecaseui.server.util.CallStub.successfulCall;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import okhttp3.Response;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.onap.usecaseui.server.service.lcm.CustomerService;
import org.onap.usecaseui.server.service.lcm.domain.aai.AAIService;
import org.onap.usecaseui.server.service.lcm.domain.aai.bean.AAICustomer;
import org.onap.usecaseui.server.service.lcm.domain.aai.bean.AAICustomerRsp;
import org.onap.usecaseui.server.service.lcm.domain.aai.bean.AAIServiceSubscription;
import org.onap.usecaseui.server.service.lcm.domain.aai.bean.ServiceSubscriptionRsp;
import org.onap.usecaseui.server.service.lcm.domain.aai.exceptions.AAIException;

import okhttp3.ResponseBody;

import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.test.context.junit4.SpringRunner;
import retrofit2.Call;

public class DefaultCustomerServiceTest {
	
    @Test
    public void itCanRetrieveCustomersFromAAI() {
        List<AAICustomer> customers = singletonList(new AAICustomer("1", "name", "type","version"));

        AAIService aaiService = mock(AAIService.class);
        AAICustomerRsp rsp = new AAICustomerRsp();
        rsp.setCustomer(customers);
        Call<AAICustomerRsp> call = successfulCall(rsp);
        when(aaiService.listCustomer()).thenReturn(call);

        CustomerService customerService = new DefaultCustomerService(aaiService);
        Assert.assertSame(customers, customerService.listCustomer());
    }

    @Test(expected = AAIException.class)
    public void itWillThrowExceptionWhenAAIIsNotAvailable() {
        AAIService aaiService = mock(AAIService.class);
        Call<AAICustomerRsp> call = failedCall("AAI is not available!");
        when(aaiService.listCustomer()).thenReturn(call);

        CustomerService customerService = new DefaultCustomerService(aaiService);
        customerService.listCustomer();
    }

    @Test
    public void itWillRetrieveEmptyListWhenNoCustomerInAAI() {
        AAIService aaiService = mock(AAIService.class);
        Call<AAICustomerRsp> call = emptyBodyCall();
        when(aaiService.listCustomer()).thenReturn(call);

        CustomerService customerService = new DefaultCustomerService(aaiService);
        List<AAICustomer> customers = customerService.listCustomer();

        Assert.assertTrue("customers should be empty list.", customers.isEmpty());
    }

    @Test
    public void itCanRetrieveServiceSubscriptionsFromAAI() {
        List<AAIServiceSubscription> serviceSubscriptions = singletonList(new AAIServiceSubscription("service type","resourceVersion"));

        AAIService aaiService = mock(AAIService.class);
        ServiceSubscriptionRsp rsp = new ServiceSubscriptionRsp();
        rsp.setServiceSubscriptions(serviceSubscriptions);
        Call<ServiceSubscriptionRsp> call = successfulCall(rsp);
        when(aaiService.listServiceSubscriptions("1")).thenReturn(call);

        CustomerService customerService = new DefaultCustomerService(aaiService);
        Assert.assertSame(serviceSubscriptions, customerService.listServiceSubscriptions("1"));
    }

    @Test(expected = AAIException.class)
    public void listServiceSubscriptionsWillThrowExceptionWhenAAIIsNotAvailable() {
        AAIService aaiService = mock(AAIService.class);
        Call<ServiceSubscriptionRsp> call = failedCall("AAI is not available!");
        when(aaiService.listServiceSubscriptions("1")).thenReturn(call);

        CustomerService customerService = new DefaultCustomerService(aaiService);
        customerService.listServiceSubscriptions("1");
    }

    @Test
    public void itWillRetrieveEmptyListWhenNoServiceSubscriptionsInAAI() {
        AAIService aaiService = mock(AAIService.class);
        Call<ServiceSubscriptionRsp> call = emptyBodyCall();
        when(aaiService.listServiceSubscriptions("1")).thenReturn(call);

        CustomerService customerService = new DefaultCustomerService(aaiService);
        List<AAIServiceSubscription> serviceSubscriptions = customerService.listServiceSubscriptions("1");

        Assert.assertTrue("service subscription should be empty list.", serviceSubscriptions.isEmpty());
    }
    
    @Test
    public void itCanCreateOrUpdateCustomer(){
    	HttpServletRequest request = mock(HttpServletRequest.class);
        AAIService aaiService = mock(AAIService.class);
        CustomerService customerService = new DefaultCustomerService(aaiService);
        String customerId="1";
        ResponseBody result=null;
        when(aaiService.createOrUpdateCustomer(eq(customerId), Mockito.any())).thenReturn(successfulCall(result));
        customerService.createOrUpdateCustomer(request, customerId);
    }
    
    @Test
    public void createOrUpdateCustomerWillThrowExceptionWhenVFCIsNotAvailable(){
    	HttpServletRequest request = mock(HttpServletRequest.class);
        AAIService aaiService = mock(AAIService.class);
        CustomerService customerService = new DefaultCustomerService(aaiService);
        String customerId="1";
        when(aaiService.createOrUpdateCustomer(eq(customerId),Mockito.any())).thenReturn(failedCall("VFC is not available!"));
        customerService.createOrUpdateCustomer(request, customerId);
    }
    
    @Test
    public void createOrUpdateCustomerWillThrowExceptionWhenVFCResponseError(){
    	HttpServletRequest request = mock(HttpServletRequest.class);
        AAIService aaiService = mock(AAIService.class);
        CustomerService customerService = new DefaultCustomerService(aaiService);
        String customerId="1";
        when(aaiService.createOrUpdateCustomer(eq(customerId),Mockito.any())).thenReturn(emptyBodyCall());
        customerService.createOrUpdateCustomer(request, customerId);
    }

    @Test
    public void itCanGetCustomerById(){
        AAIService aaiService = mock(AAIService.class);
        CustomerService customerService = new DefaultCustomerService(aaiService);
        String customerId="1";
        AAICustomer customer = new AAICustomer(customerId, customerId, customerId,customerId);
        when(aaiService.getCustomerById(eq(customerId))).thenReturn(successfulCall(customer));
        customerService.getCustomerById(customerId);
    }

    @Test
    public void getCustomerByIdWillThrowExceptionWhenVFCIsNotAvailable(){
        AAIService aaiService = mock(AAIService.class);
        CustomerService customerService = new DefaultCustomerService(aaiService);
        String customerId="1";
        AAICustomer aaiCustomer = new AAICustomer("","","","");
        Call<AAICustomer> call = successfulCall(aaiCustomer);
        when(aaiService.getCustomerById(eq(customerId))).thenReturn(call);
        customerService.getCustomerById(customerId);
    }

    @Test
    public void getCustomerByIdWillThrowExceptionWhenVFCResponseError(){
        AAIService aaiService = mock(AAIService.class);
        CustomerService customerService = new DefaultCustomerService(aaiService);
        String customerId="1";
        when(aaiService.getCustomerById(eq(customerId))).thenReturn(emptyBodyCall());
        customerService.getCustomerById(customerId);
    }
    
    @Test
    public void itCanDeleteCustomerById(){
        AAIService aaiService = mock(AAIService.class);
        CustomerService customerService = new DefaultCustomerService(aaiService);
        String customerId="1";
        String resourceVersion="1412934";
        ResponseBody result= null;
        when(aaiService.deleteCustomer(eq(customerId),eq(resourceVersion))).thenReturn(successfulCall(result));
        customerService.deleteCustomer(customerId,resourceVersion);
    }
    
    @Test
    public void deleteCustomerByIdWillThrowExceptionWhenVFCIsNotAvailable(){
        AAIService aaiService = mock(AAIService.class);
        CustomerService customerService = new DefaultCustomerService(aaiService);
    	 String resourceVersion="1412934";
        String customerId="1";
        when(aaiService.deleteCustomer(eq(customerId),eq(resourceVersion))).thenReturn(failedCall("VFC is not available!"));
        customerService.deleteCustomer(customerId,resourceVersion);
    }
    
    @Test
    public void deleteCustomerByIdWillThrowExceptionWhenVFCResponseError(){
        AAIService aaiService = mock(AAIService.class);
        CustomerService customerService = new DefaultCustomerService(aaiService);
    	 String resourceVersion="1412934";
        String customerId="1";
        when(aaiService.deleteCustomer(eq(customerId),eq(resourceVersion))).thenReturn(emptyBodyCall());
        customerService.deleteCustomer(customerId,resourceVersion);
    }
    
    @Test
    public void itCreateOrUpdateServiceType(){
    	HttpServletRequest request2 = mock(HttpServletRequest.class);
        AAIService aaiService = mock(AAIService.class);
        CustomerService customerService = new DefaultCustomerService(aaiService);
        String serviceType="1";
        String customerId="demo";
        ResponseBody result=null;
        when(aaiService.createOrUpdateServiceType(eq(customerId),eq(serviceType),Mockito.any())).thenReturn(successfulCall(result));
        customerService.createOrUpdateServiceType(request2, serviceType,customerId);
    }
    
    @Test
    public void createOrUpdateServiceTypeWillThrowExceptionWhenVFCIsNotAvailable(){
    	HttpServletRequest request2 = mock(HttpServletRequest.class);
        String serviceType="1";
        String customerId="demo";
        AAIService aaiService = mock(AAIService.class);
        CustomerService customerService = new DefaultCustomerService(aaiService);
        when(aaiService.createOrUpdateServiceType(eq(customerId),eq(serviceType),Mockito.any())).thenReturn(failedCall("VFC is not available!"));
        customerService.createOrUpdateServiceType(request2, serviceType,customerId);
    }
    
    @Test
    public void createOrUpdateServiceTypeWillThrowExceptionWhenVFCResponseError(){
    	HttpServletRequest request2 = mock(HttpServletRequest.class);
        String serviceType="1";
        String customerId="demo";
        AAIService aaiService = mock(AAIService.class);
        CustomerService customerService = new DefaultCustomerService(aaiService);
        when(aaiService.createOrUpdateServiceType(eq(customerId),eq(serviceType),Mockito.any())).thenReturn(emptyBodyCall());
        customerService.createOrUpdateServiceType(request2, serviceType,customerId);
    }
    
    @Test
    public void itCanDeleteServiceType(){
        AAIService aaiService = mock(AAIService.class);
        CustomerService customerService = new DefaultCustomerService(aaiService);
        String serviceTypeName="1";
        String resourceVersion="214341235";
        String coustomerId="1";
        ResponseBody result=null;
        when(aaiService.deleteServiceType(eq(coustomerId),eq(serviceTypeName),eq(resourceVersion))).thenReturn(successfulCall(result));
        customerService.deleteServiceType(coustomerId,serviceTypeName,resourceVersion);
    }
    
    @Test
    public void deleteServiceTypeWillThrowExceptionWhenVFCIsNotAvailable(){
        AAIService aaiService = mock(AAIService.class);
        CustomerService customerService = new DefaultCustomerService(aaiService);
        String serviceTypeName="1";
        String resourceVersion="214341235";
        String coustomerId="1";
        when(aaiService.deleteServiceType(eq(coustomerId),eq(serviceTypeName),eq(resourceVersion))).thenReturn(failedCall("VFC is not available!"));
        customerService.deleteServiceType(coustomerId,serviceTypeName,resourceVersion);
    }
    
    @Test
    public void deleteServiceTypeByIdWillThrowExceptionWhenVFCResponseError(){
        AAIService aaiService = mock(AAIService.class);
        CustomerService customerService = new DefaultCustomerService(aaiService);
        String serviceTypeName="1";
        String resourceVersion="214341235";
        String coustomerId="1";
        when(aaiService.deleteServiceType(eq(coustomerId),eq(serviceTypeName),eq(resourceVersion))).thenReturn(emptyBodyCall());
        customerService.deleteServiceType(coustomerId,serviceTypeName,resourceVersion);
    }
    
    @Test
    public void itCanGetServiceTypeById(){
        AAIService aaiService = mock(AAIService.class);
        CustomerService customerService = new DefaultCustomerService(aaiService);
        String serviceTypeName="1";
        String resourceVersion="214341235";
        AAIServiceSubscription aaIServiceSubscription = new AAIServiceSubscription(serviceTypeName,resourceVersion);
        when(aaiService.getServiceTypeById(eq(serviceTypeName),eq(resourceVersion))).thenReturn(successfulCall(aaIServiceSubscription));
        customerService.getServiceTypeById(serviceTypeName,resourceVersion);
    }
    
    @Test
    public void getServiceTypeByIdWillThrowExceptionWhenVFCIsNotAvailable(){
        AAIService aaiService = mock(AAIService.class);
        CustomerService customerService = new DefaultCustomerService(aaiService);
        String serviceTypeName="1";
        String resourceVersion="214341235";
        when(aaiService.getServiceTypeById(eq(serviceTypeName),eq(resourceVersion))).thenReturn(failedCall("VFC is not available!"));
        customerService.getServiceTypeById(serviceTypeName,resourceVersion);
    }
    
    @Test
    public void getServiceTypeByIdWillThrowExceptionWhenVFCResponseError(){
        AAIService aaiService = mock(AAIService.class);
        CustomerService customerService = new DefaultCustomerService(aaiService);
        String serviceTypeName="1";
        String resourceVersion="214341235";
        when(aaiService.getServiceTypeById(eq(serviceTypeName),eq(resourceVersion))).thenReturn(emptyBodyCall());
        customerService.getServiceTypeById(serviceTypeName,resourceVersion);
    }
}