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

public class AnSliceTaskInfoTest {
    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    @Test
    public void testSetAndGetAnSliceTaskInfo() throws Exception {
        AnSliceTaskInfo anSliceTaskInfo = new AnSliceTaskInfo();
        anSliceTaskInfo.setEndPointId("0003");
        anSliceTaskInfo.setEnableNSSISelection(true);
        anSliceTaskInfo.setInterfaceId("0092");
        anSliceTaskInfo.setIpAdrress("172.86.36.2");
        anSliceTaskInfo.setNetworkType("type1");
        anSliceTaskInfo.setNextHopInfo("info1");
        anSliceTaskInfo.setNsstinfo(new NstInfo());
        anSliceTaskInfo.setProgress("60");
        anSliceTaskInfo.setSliceInstanceId("0002-9865");
        anSliceTaskInfo.setVendor("vendor");
        anSliceTaskInfo.setNetworkType("type1");
        anSliceTaskInfo.setSubnetType("type2");
        anSliceTaskInfo.setScriptName("sci");
        anSliceTaskInfo.setEndPointId("0003-5689");
        anSliceTaskInfo.setNsstinfo(new NstInfo());

        anSliceTaskInfo.getEndPointId();
        anSliceTaskInfo.getEnableNSSISelection();
        anSliceTaskInfo.getInterfaceId();
        anSliceTaskInfo.getIpAdrress();
        anSliceTaskInfo.getNetworkType();
        anSliceTaskInfo.getNextHopInfo();
        anSliceTaskInfo.getNsstinfo();
        anSliceTaskInfo.getProgress();
        anSliceTaskInfo.getSliceInstanceId();
        anSliceTaskInfo.getVendor();
        anSliceTaskInfo.getNetworkType();
        anSliceTaskInfo.getSubnetType();
        anSliceTaskInfo.getScriptName();
        anSliceTaskInfo.getEndPointId();
        anSliceTaskInfo.getNsstinfo();
    }
}
