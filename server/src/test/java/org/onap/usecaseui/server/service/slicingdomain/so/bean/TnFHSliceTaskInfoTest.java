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

public class TnFHSliceTaskInfoTest {
    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    @Test
    public void testSetAndGetTnFHSliceTaskInfo() throws Exception {
        TnFHSliceTaskInfo tnFHSliceTaskInfo = new TnFHSliceTaskInfo();
        tnFHSliceTaskInfo.setSuggestNssiName("name");
        tnFHSliceTaskInfo.setProgress("20");
        tnFHSliceTaskInfo.setStatus("failed");
        tnFHSliceTaskInfo.setStatusDescription("failed");
        tnFHSliceTaskInfo.setSliceProfile(new SliceProfile());
        tnFHSliceTaskInfo.setSliceInstanceId("0003-5898-8522");
        tnFHSliceTaskInfo.setScriptName("scipt");
        tnFHSliceTaskInfo.setVendor("vendor");
        tnFHSliceTaskInfo.setNetworkType("type1");
        tnFHSliceTaskInfo.setSubnetType("type1");
        tnFHSliceTaskInfo.setEndPointId("0003-5898-8522");
        tnFHSliceTaskInfo.setNsstinfo(new NstInfo());

        tnFHSliceTaskInfo.getSuggestNssiName();
        tnFHSliceTaskInfo.getProgress();
        tnFHSliceTaskInfo.getStatus();
        tnFHSliceTaskInfo.getStatusDescription();
        tnFHSliceTaskInfo.getSliceProfile();
        tnFHSliceTaskInfo.getSliceInstanceId();
        tnFHSliceTaskInfo.getScriptName();
        tnFHSliceTaskInfo.getVendor();
        tnFHSliceTaskInfo.getNetworkType();
        tnFHSliceTaskInfo.getSubnetType();
        tnFHSliceTaskInfo.getEndPointId();
        tnFHSliceTaskInfo.getNsstinfo();
    }
}
