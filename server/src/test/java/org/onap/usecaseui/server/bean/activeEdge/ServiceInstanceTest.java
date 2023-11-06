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
package org.onap.usecaseui.server.bean.activeEdge;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.onap.usecaseui.server.bean.activateEdge.ServiceInstance;


public class ServiceInstanceTest {
    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    @Test
    public void testGetServiceInstance() throws Exception {
        ServiceInstance si = new ServiceInstance();
        si.getRelationshipList();
        si.getInputparameters();
        si.getServiceInstanceName();
        si.getServiceInstanceId();
        si.getAdditionalProperties();
        si.getOrchestrationstatus();
        si.getResourceVersion();
        si.getSelflink();
        si.getServiceRole();
        si.getServiceType();
    }

    @Test
    public void testSetServiceInstance() throws Exception {
        ServiceInstance si = new ServiceInstance();
        si.setRelationshipList(null);
        si.setInputparameters("");
        si.setServiceInstanceName("");
        si.setServiceInstanceId("");
        si.setAdditionalProperty("", "");
        si.setOrchestrationstatus("");
        si.setResourceVersion("");
        si.setSelflink("");
        si.setServiceRole("");
        si.setServiceType("");
    }

}
