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

public class KpiTotalBandwidthTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    @Test
    public void testSetAndGetKpiTotalBandwidth() throws Exception {
        KpiTotalBandwidth kpiTotalBandwidth = new KpiTotalBandwidth();
        List<TotalBandwidth> totalBandwidthList = new ArrayList<>();
        TotalBandwidth totalBandwidth = new TotalBandwidth();
        totalBandwidth.setTimeStamp("12345634456");
        totalBandwidth.setBandwidth(123);

        totalBandwidth.getTimeStamp();
        totalBandwidth.getBandwidth();
        totalBandwidthList.add(totalBandwidth);
        kpiTotalBandwidth.setResult(totalBandwidthList);
        SlicingKpiReqInfo slicingKpiReqInfo = new SlicingKpiReqInfo();
        kpiTotalBandwidth.setRequest(slicingKpiReqInfo);
        kpiTotalBandwidth.setResult_count(1);

        kpiTotalBandwidth.getResult();
        kpiTotalBandwidth.getRequest();
        kpiTotalBandwidth.getResult_count();

    }

}
