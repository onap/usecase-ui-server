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
package org.onap.usecaseui.server.bean.nsmf.resource;

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.onap.usecaseui.server.bean.nsmf.task.BusinessDemandInfo;
import org.onap.usecaseui.server.bean.nsmf.task.NstInfo;

public class SlicingBusinessDetailsTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    @Test
    public void testSetAndGetSlicingBusinessDetails() throws Exception {

        SlicingBusinessDetails slicingBusinessDetails = new SlicingBusinessDetails();

        BusinessDemandInfo businessDemandInfo = new BusinessDemandInfo();
        List<String> areaList = new ArrayList<>();
        areaList.add("gansu");
        areaList.add("linxia");
        businessDemandInfo.setCoverageAreaTaList(areaList);
        businessDemandInfo.setUseInterval("12");
        businessDemandInfo.setUeMobilityLevel("stationary");
        businessDemandInfo.setResourceSharingLevel("shared");
        businessDemandInfo.setMaxNumberOfUEs("1000");
        businessDemandInfo.setLatency("300");
        businessDemandInfo.setExpDataRateUL("600");
        businessDemandInfo.setExpDataRateDL("700");
        businessDemandInfo.setAreaTrafficCapUL("600");
        businessDemandInfo.setAreaTrafficCapDL("600");
        businessDemandInfo.setActivityFactor("600");
        businessDemandInfo.setServiceSnssai("1-10101");
        businessDemandInfo.setServiceName("aer-001");
        slicingBusinessDetails.setBusinessDemandInfo(businessDemandInfo);

        NstInfo nstInfo = new NstInfo();
        nstInfo.setNstId("srf0-223f-001r-iu34");
        nstInfo.setNstName("nst001");
        slicingBusinessDetails.setNstInfo(nstInfo);

        NsiInfo nsiInfo = new NsiInfo();
        nsiInfo.setNsiId("5G-888");
        nsiInfo.setNsiName("eMBB_Slice_NSI_5GCustomer");
        nsiInfo.setNsiOrchestrationStatus("activated");
        nsiInfo.setNsiType("eMBB");
        slicingBusinessDetails.setNsiInfo(nsiInfo);

        slicingBusinessDetails.getBusinessDemandInfo();
        slicingBusinessDetails.getNsiInfo();
        slicingBusinessDetails.getNstInfo();
    }
}
