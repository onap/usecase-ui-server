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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.onap.usecaseui.server.service.lcm.ServiceInstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import jakarta.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ServiceInstanceController.class })
@WebAppConfiguration
@EnableWebMvc

public class ServiceInstanceControllerTest {
    
    private MockMvc mockMvc;
    
    @Autowired
    private WebApplicationContext wac;
    
    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }
    
    @Test
    public void testListServiceInstances() throws Exception {
        ServiceInstanceController controller = new ServiceInstanceController();
        ServiceInstanceService service = mock(ServiceInstanceService.class);
        controller.setServiceInstanceService(service);

        HttpServletRequest request = mock(HttpServletRequest.class);
        String customerId = "1";
        when(request.getParameter("customerId")).thenReturn(customerId);
        String serviceType = "service";
        when(request.getParameter("serviceType")).thenReturn(serviceType);

        controller.listServiceInstances(request);

        verify(service, times(1)).listServiceInstances(customerId, serviceType);
    }
    

    @Test
    public void testListNsOrServiceInstances() throws Exception {
        
        ServiceInstanceController controller = new ServiceInstanceController();
        ServiceInstanceService service = mock(ServiceInstanceService.class);
        controller.setServiceInstanceService(service);

       String uri = "/uui-lcm/service-ns-instances";
       MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)
          .accept(MediaType.APPLICATION_JSON)).andReturn();
       
       int status = mvcResult.getResponse().getStatus();
       assertEquals(200, status);
  
    }


}