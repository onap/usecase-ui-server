/**
 * Copyright (C) 2020 Huawei, Inc. and others. All rights reserved.
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

package org.onap.usecaseui.server.service.customer;

import okhttp3.ResponseBody;
import org.junit.Before;
import org.junit.Test;
import org.onap.usecaseui.server.service.customer.impl.CcvpnCustomerServiceImpl;
import org.onap.usecaseui.server.service.lcm.domain.aai.AAIClient;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.onap.usecaseui.server.util.CallStub.failedCall;
import static org.onap.usecaseui.server.util.CallStub.successfulCall;


public class CcvpnCustomerServiceImplTest {

    CcvpnCustomerServiceImpl dsts = null;
    AAIClient aaiClient = null;


    @Before
    public void before() throws Exception {
        aaiClient= mock(AAIClient.class);
        dsts = new CcvpnCustomerServiceImpl(aaiClient);


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

    @Test
    public void itCanGetAllServiceInformation(){
        ResponseBody result=null;
        when(aaiClient.getAllServiceInformation("abc", "123")).thenReturn(successfulCall(result));
        dsts.getAllServiceInstances("abc", "123");
    }

    @Test
    public void getQuerySubscriptionWithThrowsEexception(){
        when(aaiClient.getServiceSubscription("123")).thenReturn(failedCall("aai is not exist!"));
        dsts.querySubscriptionType();
    }

    @Test
    public  void testgetServiceInstances ()
    {

    }


}
