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
package org.onap.usecaseui.server.bean.nsmf.task;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SlicingTaskAuditInfoTest {
    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    @Test
    public void testSetAndGetSlicingTaskAuditInfo() throws Exception {

        SlicingTaskAuditInfo slicingTaskAuditInfo = new SlicingTaskAuditInfo();

        slicingTaskAuditInfo.setProcessingStatus("Planning");
        slicingTaskAuditInfo.setTaskName("SliceServiceTask");
        slicingTaskAuditInfo.setCreateTime("2019-12-17T02:58:31.000+0000");
        slicingTaskAuditInfo.setTaskId("06d1a5aa-63ae-4175-a377-627ca327456f");

        BusinessDemandInfo businessDemandInfo = new BusinessDemandInfo();
        slicingTaskAuditInfo.setBusinessDemandInfo(businessDemandInfo);

        NstInfo nstInfo = new NstInfo();
        slicingTaskAuditInfo.setNstInfo(nstInfo);

        NsiAndSubNssiInfo nsiAndSubNssiInfo = new NsiAndSubNssiInfo();
        slicingTaskAuditInfo.setNsiAndSubNssiInfo(nsiAndSubNssiInfo);

        slicingTaskAuditInfo.getTaskId();
        slicingTaskAuditInfo.getBusinessDemandInfo();
        slicingTaskAuditInfo.getCreateTime();
        slicingTaskAuditInfo.getNsiAndSubNssiInfo();
        slicingTaskAuditInfo.getNstInfo();
        slicingTaskAuditInfo.getProcessingStatus();
        slicingTaskAuditInfo.getTaskName();
    }
}
