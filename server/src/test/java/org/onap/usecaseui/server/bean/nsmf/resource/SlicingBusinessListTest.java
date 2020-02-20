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

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SlicingBusinessListTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    @Test
    public void testSetAndGetSlicingBusinessList() throws Exception {

        SlicingBusinessList slicingBusinessList = new SlicingBusinessList();
        List<SlicingBusinessInfo> slicingBusinessInfoList = new ArrayList<>();

        SlicingBusinessInfo slicingBusinessInfo = new SlicingBusinessInfo();
        slicingBusinessInfo.setServiceType("embb");
        slicingBusinessInfo.setServiceInstanceName("CSMFService");
        slicingBusinessInfo.setServiceInstanceId("774dadbf-e628-4f4f-869a-67c307777af3");
        slicingBusinessInfo.setOrchestrationStatus("Created");
        slicingBusinessInfo.setLastOperationType("activate");
        slicingBusinessInfo.setLastOperationProgress("90");
        slicingBusinessInfoList.add(slicingBusinessInfo);

        slicingBusinessList.setSlicingBusinessInfoList(slicingBusinessInfoList);
        slicingBusinessList.setRecordNumber(slicingBusinessInfoList.size());

        slicingBusinessList.getSlicingBusinessInfoList();
        slicingBusinessList.getRecordNumber();
    }
}
