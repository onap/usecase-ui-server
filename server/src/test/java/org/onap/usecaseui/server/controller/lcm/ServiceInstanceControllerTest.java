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

import org.junit.Test;
import org.onap.usecaseui.server.service.lcm.ServiceInstanceService;

import javax.servlet.http.HttpServletRequest;

import static org.mockito.Mockito.*;

public class ServiceInstanceControllerTest {
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

}