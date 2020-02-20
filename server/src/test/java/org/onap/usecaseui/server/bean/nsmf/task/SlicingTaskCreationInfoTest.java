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

public class SlicingTaskCreationInfoTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    @Test
    public void testSetAndGetSlicingTaskCreationInfo() throws Exception {

        SlicingTaskCreationInfo slicingTaskCreationInfo = new SlicingTaskCreationInfo();


        slicingTaskCreationInfo.setProcessingStatus("Planning");
        slicingTaskCreationInfo.setTaskName("SliceServiceTask");
        slicingTaskCreationInfo.setCreateTime("2019-12-17T02:58:31.000+0000");
        slicingTaskCreationInfo.setTaskId("06d1a5aa-63ae-4175-a377-627ca327456f");

        BusinessDemandInfo businessDemandInfo = new BusinessDemandInfo();
        slicingTaskCreationInfo.setBusinessDemandInfo(businessDemandInfo);

        NstInfo nstInfo = new NstInfo();
        slicingTaskCreationInfo.setNstInfo(nstInfo);

        slicingTaskCreationInfo.getTaskId();
        slicingTaskCreationInfo.getBusinessDemandInfo();
        slicingTaskCreationInfo.getCreateTime();
        slicingTaskCreationInfo.getNstInfo();
        slicingTaskCreationInfo.getProcessingStatus();
        slicingTaskCreationInfo.getTaskName();
    }
}
