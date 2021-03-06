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
import org.onap.usecaseui.server.service.lcm.CustomerService;

import static org.mockito.Mockito.*;

public class CustomerControllerTest {
    @Test
    public void testGetCustomers() {
        CustomerService customerService = mock(CustomerService.class);
        CustomerController controller = new CustomerController();
        controller.setCustomerService(customerService);

        controller.getCustomers();

        verify(customerService, times(1)).listCustomer();
    }

    @Test
    public void testGetServiceSubscriptions() {
        CustomerService customerService = mock(CustomerService.class);
        CustomerController controller = new CustomerController();
        controller.setCustomerService(customerService);

        controller.getServiceSubscriptions("1");

        verify(customerService, times(1)).listServiceSubscriptions("1");
    }
}