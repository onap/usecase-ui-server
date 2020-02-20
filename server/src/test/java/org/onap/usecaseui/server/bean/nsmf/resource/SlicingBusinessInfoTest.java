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

public class SlicingBusinessInfoTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    @Test
    public void testSetAndGetSlicingBusinessInfo() throws Exception {

        SlicingBusinessInfo slicingBusinessInfo = new SlicingBusinessInfo();
        slicingBusinessInfo.setServiceType("embb");
        slicingBusinessInfo.setServiceInstanceName("CSMFService");
        slicingBusinessInfo.setServiceInstanceId("774dadbf-e628-4f4f-869a-67c307777af3");
        slicingBusinessInfo.setOrchestrationStatus("Created");
        slicingBusinessInfo.setLastOperationType("activate");
        slicingBusinessInfo.setLastOperationProgress("90");
        slicingBusinessInfo.setEnvironmentContext("an");

        slicingBusinessInfo.getOrchestrationStatus();
        slicingBusinessInfo.getEnvironmentContext();
        slicingBusinessInfo.getLastOperationProgress();
        slicingBusinessInfo.getLastOperationType();
        slicingBusinessInfo.getServiceInstanceId();
        slicingBusinessInfo.getServiceInstanceName();
        slicingBusinessInfo.getServiceType();
    }
}
