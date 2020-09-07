/*
 * Copyright (C) 2020 CMCC, Inc. and others. All rights reserved.
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
package org.onap.usecaseui.server.service.slicingdomain.kpi.bean;

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.onap.usecaseui.server.bean.nsmf.monitor.SlicingKpiReqInfo;

public class KpiUserNumberTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    @Test
    public void testSetAndGetKpiUserNumber() throws Exception {
        KpiUserNumber kpiUserNumber = new KpiUserNumber();
        List<UserNumbers> userNumbersList = new ArrayList<>();
        kpiUserNumber.setResult(userNumbersList);

        SlicingKpiReqInfo slicingKpiReqInfo = new SlicingKpiReqInfo();
        kpiUserNumber.setRequest(slicingKpiReqInfo);
        kpiUserNumber.setResult_count(3);

        kpiUserNumber.getResult();
        kpiUserNumber.getRequest();
        kpiUserNumber.getResult_count();

    }

    @Test
    public void testSetAndGetUserNumbers() throws Exception {
        UserNumbers userNumbers = new UserNumbers();
        userNumbers.setTimeStamp("1234786543");
        userNumbers.setUserNumber(190);

        userNumbers.getTimeStamp();
        userNumbers.getUserNumber();
    }
}
