/**
 * Copyright 2025 Deutsche Telekom.
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

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.onap.usecaseui.server.config.AAIClientConfig;
import org.onap.usecaseui.server.controller.lcm.CustomerController;
import org.onap.usecaseui.server.service.lcm.CustomerService;
import org.onap.usecaseui.server.service.lcm.domain.aai.bean.AAICustomer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpHeaders;
import org.wiremock.spring.EnableWireMock;

@EnableWireMock
@SpringBootTest(
    webEnvironment = WebEnvironment.RANDOM_PORT,
    classes = {
        AAIClientConfig.class, DefaultCustomerService.class, CustomerController.class
    },
    properties = {
        "spring.main.web-application-type=none", // only temporary
        "uui-server.client.aai.baseUrl=${wiremock.server.baseUrl}",
        "uui-server.client.aai.username=AAI",
        "uui-server.client.aai.password=AAI"
    })
public class DefaultCustomerServiceIntegrationTest {

    @Autowired
    CustomerService customerService;

    @Value("${uui-server.client.aai.username}")
    String username;

    @Value("${uui-server.client.aai.password}")
    String password;

    @Test
    void thatAAIRequestsAreCorrect() {
        stubFor(
            get("/api/aai-business/v13/customers")
            .withBasicAuth(username, password)
            .withHeader(HttpHeaders.ACCEPT, equalTo("application/json"))
            .withHeader("X-TransactionId", equalTo("7777"))
            .withHeader("X-FromAppId", equalTo("uui"))
            .willReturn(
                aResponse().withBodyFile("customersResponse.json")
            ));

        List<AAICustomer> customers = customerService.listCustomer();
        assertNotNull(customers);
        assertEquals(1, customers.size());
        assertEquals("someCustomer", customers.get(0).getGlobalCustomerId());
        assertEquals("someSubscriber", customers.get(0).getSubscriberName());
        assertEquals("someType", customers.get(0).getSubscriberType());
        assertEquals("abcd", customers.get(0).getResourceVersion());
    }
}
