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
package org.onap.usecaseui.server.bean.intent;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.onap.usecaseui.server.bean.csmf.CreateResponse;
import org.onap.usecaseui.server.bean.csmf.CreateResult;

public class CCVPNInstanceTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    @Test
    public void testSetAndGetCCVPNInstance() throws Exception {

        CCVPNInstance ccvpnInstance = new CCVPNInstance();
        ccvpnInstance.setAccessPointOneBandWidth(500);
        ccvpnInstance.setAccessPointOneName("name");
        ccvpnInstance.setCloudPointName("pointname");
        ccvpnInstance.setDeleteState(1);
        ccvpnInstance.setId(123);
        ccvpnInstance.setJobId("123");
        ccvpnInstance.setLineNum("5");
        ccvpnInstance.setName("ccvpn");
        ccvpnInstance.setProgress(3);
        ccvpnInstance.setInstanceId("123");
        ccvpnInstance.setResourceInstanceId("123");
        ccvpnInstance.setStatus("3");

        ccvpnInstance.getAccessPointOneBandWidth();
        ccvpnInstance.getAccessPointOneName();
        ccvpnInstance.getCloudPointName();
        ccvpnInstance.getDeleteState();
        ccvpnInstance.getId();
        ccvpnInstance.getInstanceId();
        ccvpnInstance.getJobId();
        ccvpnInstance.getLineNum();
        ccvpnInstance.getName();
        ccvpnInstance.getProgress();
        ccvpnInstance.getResourceInstanceId();
        ccvpnInstance.getStatus();
    }
}
