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
package org.onap.usecaseui.server.bean.nsmf.monitor;

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
public class ServiceOnlineUserInfoTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    @Test
    public void testSetAndGetServiceOnlineUserInfo() throws Exception {

        ServiceOnlineUserInfo serviceOnlineUserInfo = new ServiceOnlineUserInfo();

        List<UserNumberInfo> userNumberInfoList = new ArrayList<>();
        UserNumberInfo  userNumberInfo = new UserNumberInfo();
        userNumberInfo.setUserNumber("999");
        userNumberInfo.setTimeStamp("1576143554000");
        userNumberInfoList.add(userNumberInfo);

        serviceOnlineUserInfo.setUserNumberInfoList(userNumberInfoList);
        serviceOnlineUserInfo.setId("90iu-u789-iu89-jh45");

        serviceOnlineUserInfo.getId();
        serviceOnlineUserInfo.getUserNumberInfoList();
    }
}
