/*
 * Copyright (C) 2019 CMCC, Inc. and others. All rights reserved.
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
package org.onap.usecaseui.server.bean.nsmf.resource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class NssiServiceInstanceInfoTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    @Test
    public void testSetAndGetNssiServiceInstanceInfo() throws Exception {

        NssiServiceInstanceInfo nssiServiceInstanceInfo = new NssiServiceInstanceInfo();

        nssiServiceInstanceInfo.setServiceType("eMBB");
        nssiServiceInstanceInfo.setServiceInstanceName("eMBB_Slice_NSSI_5GCustomer");
        nssiServiceInstanceInfo.setServiceInstanceId("5G-999");
        nssiServiceInstanceInfo.setServiceInstanceOrder("1");
        nssiServiceInstanceInfo.setOrchestrationStatus("activated");
        nssiServiceInstanceInfo.setEnvironmentContext("cn");

        nssiServiceInstanceInfo.getEnvironmentContext();
        nssiServiceInstanceInfo.getOrchestrationStatus();
        nssiServiceInstanceInfo.getServiceInstanceId();
        nssiServiceInstanceInfo.getServiceInstanceName();
        nssiServiceInstanceInfo.getServiceInstanceOrder();
        nssiServiceInstanceInfo.getServiceType();
    }
}
