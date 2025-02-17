
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

package org.onap.usecaseui.server.service.nsmf.impl;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.onap.usecaseui.server.bean.nsmf.common.ServiceResult;
import org.onap.usecaseui.server.bean.nsmf.monitor.ServiceInfo;
import org.onap.usecaseui.server.bean.nsmf.monitor.ServiceList;
import org.onap.usecaseui.server.bean.nsmf.monitor.ServiceOnlineUserInfo;
import org.onap.usecaseui.server.bean.nsmf.monitor.ServiceOnlineUserList;
import org.onap.usecaseui.server.config.AAIClientConfig;
import org.onap.usecaseui.server.config.SOClientConfig;
import org.onap.usecaseui.server.controller.lcm.CustomerController;
import org.onap.usecaseui.server.service.lcm.CustomerService;
import org.onap.usecaseui.server.service.lcm.domain.aai.bean.AAICustomer;
import org.onap.usecaseui.server.service.lcm.impl.DefaultCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpHeaders;
import org.wiremock.spring.EnableWireMock;

@EnableWireMock
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = {
    SOClientConfig.class, ResourceMonitorServiceImpl.class, ResourceMonitorServiceConvert.class
}, properties = {
    "spring.main.web-application-type=none", // only temporary
    "uui-server.client.so.baseUrl=${wiremock.server.baseUrl}",
    "uui-server.client.so.username=InfraPortalClient",
    "uui-server.client.so.password=password1",
})

public class ResourceMonitorServiceImplIntegrationTest {

  @Autowired
  ResourceMonitorServiceImpl resourceMonitorService;

  @Value("${uui-server.client.so.username}")
  String username;

  @Value("${uui-server.client.so.password}")
  String password;

  @Test
  void thatKpiRequestsAreCorrect() {
    stubFor(
        post("/api/datalake/v1/exposure/userNumber")
            .withBasicAuth(username, password)
            .withHeader(HttpHeaders.ACCEPT, equalTo("application/json"))
            .willReturn(
                aResponse().withBodyFile("kpiUserNumberResponse.json")));

    String timestamp = "1739307776";
    ServiceList serviceList = new ServiceList();
    List<ServiceInfo> serviceInfoList = List.of(new ServiceInfo());
    serviceList.setServiceInfoList(serviceInfoList);
    ServiceResult serviceResult = resourceMonitorService.querySlicingOnlineUserNumber(timestamp, serviceList);
    assertNotNull(serviceResult);
    List<ServiceOnlineUserInfo> onlineUserInfoList = ((ServiceOnlineUserList) serviceResult.getResult_body()).getServiceOnlineUserInfoList();
    assertEquals(1, onlineUserInfoList.size());
    assertEquals("abc", onlineUserInfoList.get(0).getId());
  }
}
