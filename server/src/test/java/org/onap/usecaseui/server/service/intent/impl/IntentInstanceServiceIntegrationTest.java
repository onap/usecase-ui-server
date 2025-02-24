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
package org.onap.usecaseui.server.service.intent.impl;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.equalToJson;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.put;
import static com.github.tomakehurst.wiremock.client.WireMock.putRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.onap.usecaseui.server.bean.intent.CCVPNInstance;
import org.onap.usecaseui.server.config.AAIClientConfig;
import org.onap.usecaseui.server.config.SOClientConfig;
import org.onap.usecaseui.server.service.csmf.SlicingService;
import org.onap.usecaseui.server.service.csmf.config.SlicingProperties;
import org.onap.usecaseui.server.service.csmf.impl.SlicingServiceImpl;
import org.onap.usecaseui.server.service.intent.IntentAaiClient;
import org.onap.usecaseui.server.service.intent.IntentSoService;
import org.onap.usecaseui.server.service.intent.config.IntentProperties;
import org.onap.usecaseui.server.service.lcm.impl.DefaultServiceLcmService;
import org.onap.usecaseui.server.service.nsmf.impl.ResourceMgtServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.wiremock.spring.EnableWireMock;

import com.github.tomakehurst.wiremock.client.WireMock;

import lombok.SneakyThrows;

@EnableWireMock
@SpringBootTest(classes = {
    AAIClientConfig.class,
    SOClientConfig.class,
    SlicingServiceImpl.class,
    SlicingProperties.class,
    IntentProperties.class
}, properties = {
    "uui-server.client.aai.baseUrl=${wiremock.server.baseUrl}",
    "uui-server.client.aai.username=AAI",
    "uui-server.client.aai.password=AAI",
    "uui-server.client.so.baseUrl=${wiremock.server.baseUrl}",
    "uui-server.client.so.username=InfraPortalClient",
    "uui-server.client.so.password=password1",
    "uui-server.slicing.service-invariant-uuid=someServiceInvariantUuid",
    "uui-server.slicing.service-uuid=someServiceUuid",
    "uui-server.slicing.global-subscriber-id=someGlobalSubscriberId",
    "uui-server.slicing.service-type=someServiceType",
    "uui-server.ccvpn.globalCustomerId=defaultGlobalCustomerId"
})
@EnableConfigurationProperties
public class IntentInstanceServiceIntegrationTest {

  @Value("${uui-server.client.so.username}")
  String soUsername;

  @Value("${uui-server.client.so.password}")
  String soPassword;

  @Value("${uui-server.client.aai.username}")
  String aaiUsername;

  @Value("${uui-server.client.aai.password}")
  String aaiPassword;

  @Value("${uui-server.client.aai.apiVersion}")
  String apiVersion;

  @Mock
  ResourceMgtServiceImpl resourceMgtServiceImpl;

  @MockBean
  DefaultServiceLcmService lcmService;

  @Autowired
  SlicingService slicingService;

  @Autowired
  IntentAaiClient intentAaiClient;

  @Autowired
  IntentSoService intentSoService;

  @Autowired
  IntentProperties intentProperties;

  @Mock
  Session session;

  @BeforeEach
  void setup() {
    SessionFactory sessionFactory = mock(SessionFactory.class);
    Transaction transaction = mock(Transaction.class);
    when(sessionFactory.openSession()).thenReturn(session);
    when(session.beginTransaction()).thenReturn(transaction);
    this.intentService = new IntentInstanceServiceImpl(slicingService, intentAaiClient, intentSoService, sessionFactory,
        resourceMgtServiceImpl, intentProperties);
  }

  IntentInstanceServiceImpl intentService;

  @Test
  @SneakyThrows
  void thatCCVPNInstanceCanBeCreated() {
    byte[] requestBytes = Files.readAllBytes(Paths.get("src/test/resources/__files/requests/createIntentRequest.json"));
    String expectedRequestBody = new String(requestBytes, StandardCharsets.UTF_8);
    stubFor(
        post("/so/infra/serviceIntent/v1/create")
            .withBasicAuth(soUsername, soPassword)
            .withHeader(HttpHeaders.ACCEPT, equalTo("application/json"))
            .withHeader("X-TransactionId", equalTo("9999"))
            .withHeader("X-FromAppId", equalTo("onap-cli"))
            .withRequestBody(equalToJson(expectedRequestBody))
            .willReturn(
                aResponse().withBodyFile("createIntentResponse.json")));

    stubFor(
        get("/aai/%s/business/customers/customer/defaultGlobalCustomerId".formatted(apiVersion))
            .withBasicAuth(aaiUsername, aaiPassword)
            .withHeader(HttpHeaders.ACCEPT, equalTo("application/json"))
            .withHeader("X-TransactionId", equalTo("7777"))
            .withHeader("X-FromAppId", equalTo("uui"))
            .willReturn(
                aResponse().withBodyFile("customersResponse.json")));

    stubFor(
        get("/aai/%s/business/customers/customer/defaultGlobalCustomerId/service-subscriptions/service-subscription/defaultServiceType".formatted(apiVersion))
            .withBasicAuth(aaiUsername, aaiPassword)
            .withHeader(HttpHeaders.ACCEPT, equalTo("application/json"))
            .withHeader("X-TransactionId", equalTo("7777"))
            .withHeader("X-FromAppId", equalTo("uui"))
            .willReturn(
                aResponse().withBodyFile("customersResponse.json")));

    stubFor(
        put("/aai/%s/business/customers/customer/defaultGlobalCustomerId/service-subscriptions/service-subscription/defaultServiceType/service-instances/service-instance/IBN-someInstanceId".formatted(apiVersion))
            .withBasicAuth(aaiUsername, aaiPassword)
            .withHeader(HttpHeaders.ACCEPT, equalTo("application/json"))
            .withHeader("X-TransactionId", equalTo("7777"))
            .withHeader("X-FromAppId", equalTo("uui"))
            .willReturn(
                aResponse().withBodyFile("customersResponse.json")));

    CCVPNInstance ccVpnInstance = new CCVPNInstance();
    ccVpnInstance.setInstanceId("someInstanceId");
    ccVpnInstance.setAccessPointOneName("accessPointOneName");
    int result = intentService.createCCVPNInstance(ccVpnInstance);
    assertEquals(1, result);
    WireMock.verify(postRequestedFor(urlEqualTo("/so/infra/serviceIntent/v1/create")));
    WireMock.verify(getRequestedFor(urlEqualTo("/aai/%s/business/customers/customer/defaultGlobalCustomerId".formatted(apiVersion))));
    WireMock.verify(getRequestedFor(urlEqualTo(
        "/aai/%s/business/customers/customer/defaultGlobalCustomerId/service-subscriptions/service-subscription/defaultServiceType".formatted(apiVersion))));
    WireMock.verify(putRequestedFor(urlEqualTo(
        "/aai/%s/business/customers/customer/defaultGlobalCustomerId/service-subscriptions/service-subscription/defaultServiceType/service-instances/service-instance/IBN-someInstanceId".formatted(apiVersion))));
  }

}
