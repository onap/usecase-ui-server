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


public class ConnectivityTest {
    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }
    @Test
    public void  testGetConnectivityTest() throws Exception{
        Connectivity c = new Connectivity();
        c.getConnectivityId();
        c.getAccessClientId();
        c.getAccessLtpId();
        c.getAccessNodeId();
        c.getAccessProviderId();
        c.getAccessTopologyId();
        c.getAdditionalProperties();
        c.getBandwidthProfileName();
        c.getCbs();
        c.getCir();
        c.getColorAware();
        c.getConnectivitySelflink();
        c.getEbs();
        c.getEir();
        c.getRelationshipList();
        c.getResourceVersion();
        c.getEthtSvcName();
        c.getOperationalStatus();
        c.getAdditionalProperties();
        c.getCouplingFlag();
        c.getModelCustomizationId();
        c.getModelInvariantId();
        c.getModelVersionId();
        c.toString();

    }
    @Test
    public void  testSetConnectivityTest() throws Exception{
        Connectivity c = new Connectivity();
        c.setAccessClientId("");
        c.setAccessLtpId("");
        c.setAccessNodeId("");
        c.setAccessProviderId("");
        c.setAccessTopologyId("");
        c.setAdditionalProperty("", "");
        c.setBandwidthProfileName("");
        c.setCbs("");
        c.setColorAware("");
        c.setConnectivityId("");
        c.setConnectivitySelflink("");
        c.setCouplingFlag("");
        c.setEbs("");
        c.setEthtSvcName("");
        c.setEir("");
        c.setCir("");
        c.setModelCustomizationId("");
        c.setModelInvariantId("");
        c.setResourceVersion("");
        c.setOperationalStatus("");
        c.setRelationshipList(null);
        c.setModelVersionId("");


    }
}
