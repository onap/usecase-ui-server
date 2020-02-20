/*
 * Copyright (C) 2020 CMCC, Inc. and others. All rights reserved.
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
package org.onap.usecaseui.server.service.slicingdomain.aai.bean;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.onap.usecaseui.server.bean.csmf.SlicingOrderDetail;

public class AAIServiceTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    @Test
    public void testSetAndGetAAIService() throws Exception {
        AAIService aaiService = new AAIService();
        aaiService.setDescription("des");
        aaiService.setEnvironmentContext("cn");
        aaiService.setModelInvariantId("id001");
        aaiService.setModelVersionId("id001");
        aaiService.setOrchestrationStatus("activate");
        aaiService.setResourceVersion("ver001");
        aaiService.setServiceInstanceId("id001");
        aaiService.setServiceInstanceLocationId("loc001");
        aaiService.setServiceInstanceName("name001");
        aaiService.setServiceRole("role");
        aaiService.setServiceType("embb");

        aaiService.getDescription();
        aaiService.getEnvironmentContext();
        aaiService.getModelInvariantId();
        aaiService.getModelVersionId();
        aaiService.getOrchestrationStatus();
        aaiService.getResourceVersion();
        aaiService.getServiceInstanceId();
        aaiService.getServiceInstanceLocationId();
        aaiService.getServiceInstanceName();
        aaiService.getServiceRole();
        aaiService.getServiceType();
        aaiService.getSerialversionuid();

    }
}
