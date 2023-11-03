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
import org.onap.usecaseui.server.service.lcm.ServiceLcmService;

import jakarta.servlet.http.HttpServletRequest;
import org.onap.usecaseui.server.service.lcm.domain.so.bean.DeleteOperationRsp;
import org.onap.usecaseui.server.service.lcm.domain.so.bean.Operation;
import org.onap.usecaseui.server.service.lcm.domain.so.bean.ServiceOperation;

import static org.mockito.Mockito.*;

public class ServiceLcmControllerTest {

    private ServiceLcmService service;

    private ServiceLcmController controller = new ServiceLcmController();

    @Before
    public void setUp() {
        service = mock(ServiceLcmService.class);
        controller.setServiceLcmService(service);
    }

    @Test
    public void testInstantiateService() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        ServiceOperation serviceOperation = new ServiceOperation();
        Operation operation = new Operation();
        operation.setOperationId("vv");
        operation.setServiceId("bb");
        serviceOperation.setService(operation);
        when(service.instantiateService(eq(request))).thenReturn(serviceOperation);
        doNothing().when(service).saveOrUpdateServiceBean(any());
        doNothing().when(service).saveOrUpdateServiceInstanceOperation(any());
        controller.instantiateService(request);

        verify(service, times(1)).instantiateService(request);
    }

    @Test
    public void testQueryOperationProgress() throws Exception {
        String serviceId = "1";
        String operationId = "1";
        HttpServletRequest request = mock(HttpServletRequest.class);
        controller.queryOperationProgress(request,serviceId, operationId);

        verify(service, times(1)).queryOperationProgress(serviceId, operationId);
    }

    @Test
    public void testTerminateService() throws Exception {
        String serviceId = "1";
        HttpServletRequest request = mock(HttpServletRequest.class);
        DeleteOperationRsp deleteOperationRsp = new DeleteOperationRsp();
        deleteOperationRsp.setOperationId("qq");
        when(service.terminateService(serviceId,request)).thenReturn(deleteOperationRsp);
        doNothing().when(service).saveOrUpdateServiceInstanceOperation(any());
        controller.terminateService(serviceId, request);

        verify(service, times(1)).terminateService(serviceId, request);
    }

}