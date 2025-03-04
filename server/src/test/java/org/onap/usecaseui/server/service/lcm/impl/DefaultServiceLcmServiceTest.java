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

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.onap.usecaseui.server.service.lcm.ServiceLcmService;
import org.onap.usecaseui.server.service.lcm.domain.so.SOClient;
import org.onap.usecaseui.server.service.lcm.domain.so.bean.DeleteOperationRsp;
import org.onap.usecaseui.server.service.lcm.domain.so.bean.Operation;
import org.onap.usecaseui.server.service.lcm.domain.so.bean.OperationProgressInformation;
import org.onap.usecaseui.server.service.lcm.domain.so.bean.SaveOrUpdateOperationRsp;
import org.onap.usecaseui.server.service.lcm.domain.so.bean.ServiceOperation;
import org.onap.usecaseui.server.service.lcm.domain.so.exceptions.SOException;

import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.onap.usecaseui.server.util.CallStub.emptyBodyCall;
import static org.onap.usecaseui.server.util.CallStub.failedCall;
import static org.onap.usecaseui.server.util.CallStub.successfulCall;

public class DefaultServiceLcmServiceTest {

	private static final long serialVersionUID = 1L;

    private final EntityManagerFactory entityManagerFactory = mock(EntityManagerFactory.class);

	 ServiceLcmService service = null;

	@Before
	public void before() throws Exception {
		SOClient soClient = mock(SOClient.class);
		service = new DefaultServiceLcmService(entityManagerFactory, soClient);

	}

    @Test
    public void itCanInstantiateService() throws IOException {
        SOClient soClient = mock(SOClient.class);
        Operation op = new Operation();
        op.setOperationId("1");
        op.setServiceId("1");
        ServiceOperation operation = new ServiceOperation();
        operation.setService(op);
        when(soClient.instantiateService(Mockito.any())).thenReturn(successfulCall(operation));

        HttpServletRequest request = mockRequest();

        ServiceLcmService service = new DefaultServiceLcmService(entityManagerFactory, soClient);

        Assert.assertSame(operation, service.instantiateService(request));
    }

    private HttpServletRequest mockRequest() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getContentLength()).thenReturn(0);
        ServletInputStream inStream = new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }

            @Override
            public int read() throws IOException {
                return 0;
            }
        };
        when(request.getInputStream()).thenReturn(inStream);
        return request;
    }

    @Test(expected = SOException.class)
    public void instantiateServiceWillThrowExceptionWhenSOIsNotAvailable() throws IOException {
        SOClient soClient = mock(SOClient.class);
        when(soClient.instantiateService(Mockito.any())).thenReturn(failedCall("SO is not available!"));
        HttpServletRequest request = mockRequest();

        ServiceLcmService service = new DefaultServiceLcmService(entityManagerFactory, soClient);

        service.instantiateService(request);
    }

    @Test(expected = SOException.class)
    public void instantiateServiceWillThrowExceptionWhenSOResponseError() throws IOException {
        SOClient soClient = mock(SOClient.class);
        when(soClient.instantiateService(Mockito.any())).thenReturn(emptyBodyCall());
        HttpServletRequest request = mockRequest();

        ServiceLcmService service = new DefaultServiceLcmService(entityManagerFactory, soClient);

        service.instantiateService(request);
    }

    @Test
    public void itCanTerminateService() throws IOException {
        SOClient soClient = mock(SOClient.class);
        String serviceId = "1";
        DeleteOperationRsp rsp = new DeleteOperationRsp();
        rsp.setOperationId("1");
        when(soClient.terminateService(eq(serviceId), Mockito.any())).thenReturn(successfulCall(rsp));
        HttpServletRequest request = mockRequest();

        ServiceLcmService service = new DefaultServiceLcmService(entityManagerFactory, soClient);

        Assert.assertSame(rsp, service.terminateService(serviceId, request));
    }

    @Test(expected = SOException.class)
    public void terminateServiceWillThrowExceptionWhenSOIsNotAvailable() throws IOException {
        SOClient soClient = mock(SOClient.class);
        String serviceId = "1";
        when(soClient.terminateService(eq(serviceId), Mockito.any())).thenReturn(failedCall("SO is not available!"));
        HttpServletRequest request = mockRequest();

        ServiceLcmService service = new DefaultServiceLcmService(entityManagerFactory, soClient);

        service.terminateService(serviceId, request);
    }

    @Test(expected = SOException.class)
    public void terminateServiceWillThrowExceptionWhenSOResponseError() throws IOException {
        SOClient soClient = mock(SOClient.class);
        String serviceId = "1";
        when(soClient.terminateService(eq(serviceId), Mockito.any())).thenReturn(emptyBodyCall());
        HttpServletRequest request = mockRequest();

        ServiceLcmService service = new DefaultServiceLcmService(entityManagerFactory, soClient);

        service.terminateService(serviceId, request);
    }

    @Test
    public void itCanQueryOperationProgress() {
        SOClient soClient = mock(SOClient.class);
        String serviceId = "1";
        String operationId = "1";
        OperationProgressInformation progress = new OperationProgressInformation();
        when(soClient.queryOperationProgress(serviceId, operationId)).thenReturn(successfulCall(progress));

        ServiceLcmService service = new DefaultServiceLcmService(entityManagerFactory, soClient);

        Assert.assertSame(progress, service.queryOperationProgress(serviceId, operationId));
    }

    @Test(expected = SOException.class)
    public void queryOperationProgressWillThrowExceptionWhenSOIsNotAvailable() {
        SOClient soClient = mock(SOClient.class);
        String serviceId = "1";
        String operationId = "1";
        when(soClient.queryOperationProgress(serviceId, operationId)).thenReturn(failedCall("SO is not available!"));

        ServiceLcmService service = new DefaultServiceLcmService(entityManagerFactory, soClient);

        service.queryOperationProgress(serviceId, operationId);
    }

    @Test(expected = SOException.class)
    public void queryOperationProgressWillThrowExceptionWhenSOResponseError() {
        SOClient soClient = mock(SOClient.class);
        String serviceId = "1";
        String operationId = "1";
        when(soClient.queryOperationProgress(serviceId, operationId)).thenReturn(emptyBodyCall());

        ServiceLcmService service = new DefaultServiceLcmService(entityManagerFactory, soClient);

        service.queryOperationProgress(serviceId, operationId);
    }

    @Test(expected = SOException.class)
    public void scaleServiceWillThrowExceptionWhenSOIsNotAvailable() throws IOException {
        SOClient soClient = mock(SOClient.class);
        String serviceId = "1";
        when(soClient.scaleService(eq(serviceId), Mockito.any())).thenReturn(failedCall("SO is not available!"));
        HttpServletRequest request = mockRequest();

        ServiceLcmService service = new DefaultServiceLcmService(entityManagerFactory, soClient);

        service.scaleService(serviceId, request);
    }

    @Test(expected = SOException.class)
    public void scaleServiceWillThrowExceptionWhenSOResponseError() throws IOException {
        SOClient soClient = mock(SOClient.class);
        String serviceId = "1";
        when(soClient.scaleService(eq(serviceId), Mockito.any())).thenReturn(emptyBodyCall());
        HttpServletRequest request = mockRequest();

        ServiceLcmService service = new DefaultServiceLcmService(entityManagerFactory, soClient);

        service.scaleService(serviceId, request);
    }

    @Test
    public void itCanScaleService() throws IOException {
        SOClient soClient = mock(SOClient.class);
        String serviceId = "1";
        SaveOrUpdateOperationRsp rsp = new SaveOrUpdateOperationRsp();
        rsp.setOperationId("1");
        when(soClient.scaleService(eq(serviceId), Mockito.any())).thenReturn(successfulCall(rsp));
        HttpServletRequest request = mockRequest();

        ServiceLcmService service = new DefaultServiceLcmService(entityManagerFactory, soClient);

        Assert.assertSame(rsp, service.scaleService(serviceId, request));
    }

    @Test(expected = SOException.class)
    public void updateServiceWillThrowExceptionWhenSOIsNotAvailable() throws IOException {
        SOClient soClient = mock(SOClient.class);
        String serviceId = "1";
        when(soClient.updateService(eq(serviceId), Mockito.any())).thenReturn(failedCall("SO is not available!"));
        HttpServletRequest request = mockRequest();

        ServiceLcmService service = new DefaultServiceLcmService(entityManagerFactory, soClient);

        service.updateService(serviceId, request);
    }

    @Test(expected = SOException.class)
    public void updateServiceWillThrowExceptionWhenSOResponseError() throws IOException {
        SOClient soClient = mock(SOClient.class);

        String serviceId = "1";
        when(soClient.updateService(eq(serviceId), Mockito.any())).thenReturn(emptyBodyCall());
        HttpServletRequest request = mockRequest();

        ServiceLcmService service = new DefaultServiceLcmService(entityManagerFactory, soClient);

        service.updateService(serviceId, request);
    }

    @Test
    public void itCanUpdateService() throws IOException {
        SOClient soClient = mock(SOClient.class);
        EntityManagerFactory entityManagerFactory = mock(EntityManagerFactory.class);
        String serviceId = "1";
        SaveOrUpdateOperationRsp rsp = new SaveOrUpdateOperationRsp();
        rsp.setOperationId("1");
        when(soClient.updateService(eq(serviceId), Mockito.any())).thenReturn(successfulCall(rsp));
        HttpServletRequest request = mockRequest();

        ServiceLcmService service = new DefaultServiceLcmService(entityManagerFactory, soClient);

        Assert.assertSame(rsp, service.updateService(serviceId, request));
    }
}
