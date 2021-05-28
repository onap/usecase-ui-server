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

public class TnBHSliceTaskInfoTest {
    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    @Test
    public void testSetAndGetTnBHSliceTaskInfo() throws Exception {
        TnBHSliceTaskInfo tnBHSliceTaskInfo = new TnBHSliceTaskInfo();
        tnBHSliceTaskInfo.setSuggestNssiName("name");
        tnBHSliceTaskInfo.setProgress("20");
        tnBHSliceTaskInfo.setStatus("failed");
        tnBHSliceTaskInfo.setStatusDescription("failed");
        tnBHSliceTaskInfo.setSliceProfile(new SliceProfile());
        tnBHSliceTaskInfo.setSliceInstanceId("0003-5898-8522");
        tnBHSliceTaskInfo.setScriptName("scipt");
        tnBHSliceTaskInfo.setVendor("vendor");
        tnBHSliceTaskInfo.setNetworkType("type1");
        tnBHSliceTaskInfo.setSubnetType("type1");
        tnBHSliceTaskInfo.setEndPointId("0003-5898-8522");
        tnBHSliceTaskInfo.setEnableNSSISelection(true);
        tnBHSliceTaskInfo.setLantency("20");
        tnBHSliceTaskInfo.setMaxBandWidth("20");
        tnBHSliceTaskInfo.setLinkType("type2");
        tnBHSliceTaskInfo.setNsstinfo(new NstInfo());

        tnBHSliceTaskInfo.getSuggestNssiName();
        tnBHSliceTaskInfo.getProgress();
        tnBHSliceTaskInfo.getStatus();
        tnBHSliceTaskInfo.getStatusDescription();
        tnBHSliceTaskInfo.getSliceProfile();
        tnBHSliceTaskInfo.getSliceInstanceId();
        tnBHSliceTaskInfo.getScriptName();
        tnBHSliceTaskInfo.getVendor();
        tnBHSliceTaskInfo.getNetworkType();
        tnBHSliceTaskInfo.getSubnetType();
        tnBHSliceTaskInfo.getEndPointId();
        tnBHSliceTaskInfo.getEnableNSSISelection();
        tnBHSliceTaskInfo.getLantency();
        tnBHSliceTaskInfo.getMaxBandWidth();
        tnBHSliceTaskInfo.getLinkType();
        tnBHSliceTaskInfo.getNsstinfo();
    }
}
