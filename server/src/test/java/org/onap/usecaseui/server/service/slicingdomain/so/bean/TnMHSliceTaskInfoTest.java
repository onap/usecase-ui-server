/*
 * Copyright (C) 2021 CMCC, Inc. and others. All rights reserved.
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
package org.onap.usecaseui.server.service.slicingdomain.so.bean;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TnMHSliceTaskInfoTest {
    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    @Test
    public void testSetAndGetTnMHSliceTaskInfo() throws Exception {
        TnMHSliceTaskInfo tnMHSliceTaskInfo = new TnMHSliceTaskInfo();
        tnMHSliceTaskInfo.setSuggestNssiName("name");
        tnMHSliceTaskInfo.setProgress("20");
        tnMHSliceTaskInfo.setStatus("failed");
        tnMHSliceTaskInfo.setStatusDescription("failed");
        tnMHSliceTaskInfo.setSliceProfile(new SliceProfile());
        tnMHSliceTaskInfo.setSliceInstanceId("0003-5898-8522");
        tnMHSliceTaskInfo.setScriptName("scipt");
        tnMHSliceTaskInfo.setVendor("vendor");
        tnMHSliceTaskInfo.setNetworkType("type1");
        tnMHSliceTaskInfo.setSubnetType("type1");
        tnMHSliceTaskInfo.setEndPointId("0003-5898-8522");
        tnMHSliceTaskInfo.setNsstinfo(new NstInfo());

        tnMHSliceTaskInfo.getSuggestNssiName();
        tnMHSliceTaskInfo.getProgress();
        tnMHSliceTaskInfo.getStatus();
        tnMHSliceTaskInfo.getStatusDescription();
        tnMHSliceTaskInfo.getSliceProfile();
        tnMHSliceTaskInfo.getSliceInstanceId();
        tnMHSliceTaskInfo.getScriptName();
        tnMHSliceTaskInfo.getVendor();
        tnMHSliceTaskInfo.getNetworkType();
        tnMHSliceTaskInfo.getSubnetType();
        tnMHSliceTaskInfo.getEndPointId();
        tnMHSliceTaskInfo.getNsstinfo();
    }
}
