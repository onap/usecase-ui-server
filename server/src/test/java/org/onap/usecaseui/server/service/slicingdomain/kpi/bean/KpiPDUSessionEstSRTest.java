/*
 * Copyright (C) 2022 Wipro Limited. All rights reserved.
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

public class KpiPDUSessionEstSRTest {

	@Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    @Test
    public void testSetAndGetKpiPDUSessionEstSR() throws Exception {

    	KpiPDUSessionEstSR kpiPDUSessionEstSR = new KpiPDUSessionEstSR();

    	List<PDUSessionEstSR> pDUSessionEstSRList = new ArrayList<>();
    	PDUSessionEstSR pDUSessionEstSR = new PDUSessionEstSR();
    	pDUSessionEstSR.setTimeStamp("12345634456");
        pDUSessionEstSR.setPDUSessionEstSR("123");

    	pDUSessionEstSRList.add(pDUSessionEstSR);
    	kpiPDUSessionEstSR.setResult(pDUSessionEstSRList);
        SlicingKpiReqInfo slicingKpiReqInfo = new SlicingKpiReqInfo();
        kpiPDUSessionEstSR.setRequest(slicingKpiReqInfo);
        kpiPDUSessionEstSR.setResult_count(1);

        kpiPDUSessionEstSR.getResult();
        kpiPDUSessionEstSR.getRequest();
        kpiPDUSessionEstSR.getResult_count();
    }

}
