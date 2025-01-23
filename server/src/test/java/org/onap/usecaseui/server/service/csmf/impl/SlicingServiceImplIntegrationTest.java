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

package org.onap.usecaseui.server.service.csmf.impl;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.equalToJson;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.onap.usecaseui.server.bean.csmf.ServiceCreateResult;
import org.onap.usecaseui.server.bean.csmf.SlicingOrder;
import org.onap.usecaseui.server.bean.csmf.SlicingOrderDetail;
import org.onap.usecaseui.server.bean.nsmf.common.ServiceResult;
import org.onap.usecaseui.server.config.AAIClientConfig;
import org.onap.usecaseui.server.config.SOClientConfig;
import org.onap.usecaseui.server.service.csmf.SlicingService;
import org.onap.usecaseui.server.service.csmf.config.SlicingProperties;
import org.onap.usecaseui.server.service.lcm.ServiceLcmService;
import org.onap.usecaseui.server.service.lcm.impl.DefaultServiceLcmService;
import org.onap.usecaseui.server.service.slicingdomain.aai.AAISliceService;
import org.onap.usecaseui.server.service.slicingdomain.so.SOSliceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.wiremock.spring.EnableWireMock;

import lombok.SneakyThrows;

@EnableWireMock
@EnableConfigurationProperties
@SpringBootTest(
    webEnvironment = WebEnvironment.RANDOM_PORT,
    classes = {
        AAIClientConfig.class,
        SOClientConfig.class,
        SlicingServiceImpl.class,
        SlicingProperties.class,
        DefaultServiceLcmService.class,
    },
    properties = {
        "spring.main.web-application-type=none", // only temporary
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
    })
public class SlicingServiceImplIntegrationTest {

    @MockBean ServiceLcmService serviceLcmService;
    @Autowired AAISliceService aaiSliceService;
    @Autowired SOSliceService soSliceService;
    @Autowired SlicingProperties slicingProperties;

    SlicingService slicingService;

    @Value("${uui-server.client.so.username}")
    String username;

    @Value("${uui-server.client.so.password}")
    String password;

    @BeforeEach
    void setup() {
      slicingService = new SlicingServiceImpl(serviceLcmService,aaiSliceService,soSliceService, slicingProperties);
    }

    @Test
    @SneakyThrows
    void thatSORequestsAreCorrect() {
        byte[] requestBytes = Files.readAllBytes(Paths.get("src/test/resources/__files/requests/submitOrdersRequest.json"));
        String expectedRequestBody = new String(requestBytes, StandardCharsets.UTF_8);
        stubFor(
          post("/api/so-serviceInstances/v3")
            .withBasicAuth(username, password)
            .withHeader(HttpHeaders.ACCEPT, equalTo("application/json"))
            .withHeader("X-TransactionId", equalTo("9999"))
            .withHeader("X-FromAppId", equalTo("onap-cli"))
            .withRequestBody(equalToJson(expectedRequestBody))
            .willReturn(
                aResponse().withBodyFile("submitOrdersResponse.json")
            ));

        SlicingOrder slicingOrder = new SlicingOrder();
        SlicingOrderDetail detail = new SlicingOrderDetail();
        detail.setLatency(10);
        detail.setMaxNumberofUEs(10);
        slicingOrder.setSlicing_order_info(detail);
        ServiceResult serviceResult = slicingService.createSlicingService(slicingOrder);
        ServiceCreateResult result = (ServiceCreateResult) serviceResult.getResult_body();
        assertEquals("someServiceId", result.getService_id());
        assertEquals("someOperationId", result.getOperation_id());
    }
}
