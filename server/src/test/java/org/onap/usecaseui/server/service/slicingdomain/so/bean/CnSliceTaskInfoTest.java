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

public class CnSliceTaskInfoTest {
    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    @Test
    public void testSetAndGetCnSliceTaskInfoTest() throws Exception {
        CnSliceTaskInfo cnSliceTaskInfo = new CnSliceTaskInfo();
        cnSliceTaskInfo.setEndPointId("0003");
        cnSliceTaskInfo.setEnableNSSISelection(true);
        cnSliceTaskInfo.setInterfaceId("0092");
        cnSliceTaskInfo.setIpAdrress("172.86.36.2");
        cnSliceTaskInfo.setNetworkType("type1");
        cnSliceTaskInfo.setNextHopInfo("info1");
        cnSliceTaskInfo.setNsstinfo(new NstInfo());
        cnSliceTaskInfo.setProgress("60");
        cnSliceTaskInfo.setSliceInstanceId("0002-9865");
        cnSliceTaskInfo.setVendor("vendor");
        cnSliceTaskInfo.setNetworkType("type1");
        cnSliceTaskInfo.setSubnetType("type2");
        cnSliceTaskInfo.setScriptName("sci");
        cnSliceTaskInfo.setEndPointId("0003-5689");
        cnSliceTaskInfo.setNsstinfo(new NstInfo());

        cnSliceTaskInfo.getEndPointId();
        cnSliceTaskInfo.getEnableNSSISelection();
        cnSliceTaskInfo.getInterfaceId();
        cnSliceTaskInfo.getIpAdrress();
        cnSliceTaskInfo.getNetworkType();
        cnSliceTaskInfo.getNextHopInfo();
        cnSliceTaskInfo.getNsstinfo();
        cnSliceTaskInfo.getProgress();
        cnSliceTaskInfo.getSliceInstanceId();
        cnSliceTaskInfo.getVendor();
        cnSliceTaskInfo.getNetworkType();
        cnSliceTaskInfo.getSubnetType();
        cnSliceTaskInfo.getScriptName();
        cnSliceTaskInfo.getEndPointId();
        cnSliceTaskInfo.getNsstinfo();
    }
}
