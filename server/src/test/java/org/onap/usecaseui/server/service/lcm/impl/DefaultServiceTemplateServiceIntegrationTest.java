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
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.onap.usecaseui.server.config.AAIClientConfig;
import org.onap.usecaseui.server.config.SDCClientConfig;
import org.onap.usecaseui.server.config.SDCClientProperties;
import org.onap.usecaseui.server.service.lcm.domain.sdc.bean.SDCServiceTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.wiremock.spring.EnableWireMock;

import lombok.SneakyThrows;

@EnableWireMock
@EnableConfigurationProperties(SDCClientProperties.class)
@SpringBootTest(
    classes = {
        AAIClientConfig.class, SDCClientConfig.class , DefaultServiceTemplateService.class
    },
    properties = {
        "client.aai.baseUrl=${wiremock.server.baseUrl}",
        "client.aai.username=AAI",
        "client.aai.password=AAI",
        "uui-server.client.sdc.base-url=${wiremock.server.baseUrl}",
        "uui-server.client.sdc.username=someUser",
        "uui-server.client.sdc.password=somePassword",
    })
public class DefaultServiceTemplateServiceIntegrationTest {

    @Autowired
    DefaultServiceTemplateService defaultServiceTemplateService;

    @Value("${uui-server.client.sdc.username}")
    String username;

    @Value("${uui-server.client.sdc.password}")
    String password;

    @Test
    @SneakyThrows
    void thatSDCCatalogRequestsAreCorrect() {
        stubFor(
            get(urlPathEqualTo("/api/sdc/v1/catalog/services"))
            .withQueryParam("category", equalTo("E2E Service"))
            .withQueryParam("distributionStatus", equalTo("DISTRIBUTED"))
            .withBasicAuth(username, password)
            .withHeader(HttpHeaders.ACCEPT, equalTo("application/json"))
            .withHeader("X-ECOMP-InstanceID", equalTo("777"))
            .willReturn(
                aResponse().withBodyFile("serviceTemplateResponse.json")
            ));
        List<SDCServiceTemplate> serviceTemplates = defaultServiceTemplateService.listDistributedServiceTemplate();
        assertNotNull(serviceTemplates);
        assertEquals(1, serviceTemplates.size());
        assertEquals("someCategory", serviceTemplates.get(0).getCategory());
        assertEquals("someInvariantUuid", serviceTemplates.get(0).getInvariantUUID());
        assertEquals("someName", serviceTemplates.get(0).getName());
        assertEquals("/foo/bar", serviceTemplates.get(0).getToscaModelURL());
        assertEquals("someUuid", serviceTemplates.get(0).getUuid());
        assertEquals("someVersion", serviceTemplates.get(0).getVersion());
    }
}
