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
import org.junit.Test;
import org.mockito.Mockito;
import org.onap.usecaseui.server.service.lcm.CustomerService;
import org.onap.usecaseui.server.service.lcm.domain.aai.AAIService;
import org.onap.usecaseui.server.service.lcm.domain.aai.bean.AAICustomer;
import org.onap.usecaseui.server.service.lcm.domain.aai.exceptions.AAIException;
import org.onap.usecaseui.server.util.CallStub;
import retrofit2.Call;

import java.util.List;

import static java.util.Collections.singletonList;

public class DefaultCustomerServiceTest {

    @Test
    public void itCanRetrieveCustomersFromAAI() {
        List<AAICustomer> customers = singletonList(new AAICustomer("1", "name", "type"));

        AAIService aaiService = Mockito.mock(AAIService.class);
        Call<List<AAICustomer>> call = CallStub.successfulCall(customers);
        Mockito.when(aaiService.listCustomer()).thenReturn(call);

        CustomerService customerService = new DefaultCustomerService(aaiService);
        Assert.assertSame(customers, customerService.listCustomer());
    }

    @Test(expected = AAIException.class)
    public void itWillThrowExceptionWhenAAIIsNotAvailable() {
        AAIService aaiService = Mockito.mock(AAIService.class);
        Call<List<AAICustomer>> call = CallStub.failedCall("AAI is not available!");
        Mockito.when(aaiService.listCustomer()).thenReturn(call);

        CustomerService customerService = new DefaultCustomerService(aaiService);
        customerService.listCustomer();
    }
}