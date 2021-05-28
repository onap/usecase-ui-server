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
package org.onap.usecaseui.server.bean.nsmf.task;

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.onap.usecaseui.server.bean.nsmf.resource.HostedNsiInfo;

public class BusinessDemandInfoTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    @Test
    public void testSetAndGetBusinessDemandInfo() throws Exception {

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
        businessDemandInfo.setServiceProfileAvailability("001");
        businessDemandInfo.setServiceProfilePLMNIdList("001");
        businessDemandInfo.setServiceProfileReliability("001");
        businessDemandInfo.setServiceProfileDLThptPerSlice("001");
        businessDemandInfo.setServiceProfileDLThptPerUE("001");
        businessDemandInfo.setServiceProfileULThptPerSlice("001");
        businessDemandInfo.setServiceProfileULThptPerUE("001");
        businessDemandInfo.setServiceProfileMaxPktSize("001");
        businessDemandInfo.setServiceProfileSurvivalTime("001");

        businessDemandInfo.getActivityFactor();
        businessDemandInfo.getAreaTrafficCapDL();
        businessDemandInfo.getAreaTrafficCapUL();
        businessDemandInfo.getCoverageAreaTaList();
        businessDemandInfo.getExpDataRateDL();
        businessDemandInfo.getExpDataRateUL();
        businessDemandInfo.getLatency();
        businessDemandInfo.getMaxNumberOfUEs();
        businessDemandInfo.getResourceSharingLevel();
        businessDemandInfo.getServiceName();
        businessDemandInfo.getServiceSnssai();
        businessDemandInfo.getUeMobilityLevel();
        businessDemandInfo.getUseInterval();
        businessDemandInfo.getServiceProfileAvailability();
        businessDemandInfo.getServiceProfilePLMNIdList();
        businessDemandInfo.getServiceProfileReliability();
        businessDemandInfo.getServiceProfileDLThptPerSlice();
        businessDemandInfo.getServiceProfileDLThptPerUE();
        businessDemandInfo.getServiceProfileULThptPerSlice();
        businessDemandInfo.getServiceProfileULThptPerUE();
        businessDemandInfo.getServiceProfileMaxPktSize();
        businessDemandInfo.getServiceProfileSurvivalTime();
    }
}
