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
package org.onap.usecaseui.server.bean.lcm.sotne2eservice;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.anyObject;

public class AllottedResourceTest {
    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }
    @Test
    public void testGetAllocattedResource() {
        AllottedResource ar = new AllottedResource();

        ar.getAccessClientId();
        ar.getAccessLtpId();
        ar.getAccessNodeId();
        ar.getAccessProviderId();
        ar.getAccessTopologyId();
        ar.getAdditionalProperties();
        ar.getCvlan();
        ar.getId();
        ar.getModelInvariantId();
        ar.getModelVersionId();
        ar.getOperationalStatus();
        ar.getRelationshipList();
        ar.getResourceVersion();
        ar.getSelflink();
        ar.getVpnName();
    }
    @Test
    public void testSetAllocattedResource() {
        AllottedResource ar = new AllottedResource();

        ar.setAccessClientId("");
        ar.setAccessLtpId("");
        ar.setAccessNodeId("");
        ar.setAccessProviderId("");
        ar.setAccessTopologyId("");
        ar.setAdditionalProperty("",null);
        ar.setCvlan("");
        ar.setId("");
        ar.setModelInvariantId("");
        ar.setModelVersionId("");
        ar.setOperationalStatus("");
        ar.setRelationshipList(null);
        ar.setResourceVersion("");
        ar.setSelflink("");
        ar.setVpnName("");
    }
}
