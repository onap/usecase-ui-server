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

public class NsiAndSubNssiInfoTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    @Test
    public void testSetAndGetNsiAndSubNssiInfo() throws Exception {

        NsiAndSubNssiInfo nsiAndSubNssiInfo = new NsiAndSubNssiInfo();
        nsiAndSubNssiInfo.setSuggestNsiName("nsi01");
        nsiAndSubNssiInfo.setSuggestNsiId("nsi01-0090-0987");

        nsiAndSubNssiInfo.setTnBhSuggestNssiName("tn-nsi-01");
        nsiAndSubNssiInfo.setTnBhSuggestNssiId("tn01-0987-iu87");
        nsiAndSubNssiInfo.setTnBhLatency("60");
        nsiAndSubNssiInfo.setTnBhBandwidth("1000");
        nsiAndSubNssiInfo.setTnBhScriptName("scriptTest");

        nsiAndSubNssiInfo.setCnUeMobilityLevel("stationary");
        nsiAndSubNssiInfo.setCnServiceSnssai("1-10101");
        nsiAndSubNssiInfo.setCnMaxNumberOfUes("1000");
        nsiAndSubNssiInfo.setCnSuggestNssiName("cn-001");
        nsiAndSubNssiInfo.setCnSuggestNssiId("0902-oi89-8923-iu34");
        nsiAndSubNssiInfo.setCnResourceSharingLevel("shared");
        nsiAndSubNssiInfo.setCnExpDataRateUl("300");
        nsiAndSubNssiInfo.setCnExpDataRateDl("600");
        nsiAndSubNssiInfo.setCnScriptName("scriptTest");

        nsiAndSubNssiInfo.setAnSuggestNssiName("an-001");
        nsiAndSubNssiInfo.setAnSuggestNssiId("0923-982-34fe-4553");
        nsiAndSubNssiInfo.setAnLatency("300");
        List<String> areaList = new ArrayList<>();
        areaList.add("gansu");
        areaList.add("linxia");
        nsiAndSubNssiInfo.setAnCoverageAreaTaList(areaList);
        nsiAndSubNssiInfo.setAn5qi("er4");
        nsiAndSubNssiInfo.setAnScriptName("scriptTest");

        nsiAndSubNssiInfo.getAn5qi();
        nsiAndSubNssiInfo.getAnCoverageAreaTaList();
        nsiAndSubNssiInfo.getAnLatency();
        nsiAndSubNssiInfo.getAnSuggestNssiId();
        nsiAndSubNssiInfo.getAnSuggestNssiName();
        nsiAndSubNssiInfo.getAnScriptName();

        nsiAndSubNssiInfo.getCnActivityFactor();
        nsiAndSubNssiInfo.getCnAreaTrafficCapDl();
        nsiAndSubNssiInfo.getCnAreaTrafficCapUl();
        nsiAndSubNssiInfo.getCnExpDataRateDl();
        nsiAndSubNssiInfo.getCnExpDataRateUl();
        nsiAndSubNssiInfo.getCnLatency();
        nsiAndSubNssiInfo.getCnMaxNumberOfUes();
        nsiAndSubNssiInfo.getCnResourceSharingLevel();
        nsiAndSubNssiInfo.getCnServiceSnssai();
        nsiAndSubNssiInfo.getCnSuggestNssiId();
        nsiAndSubNssiInfo.getCnSuggestNssiName();
        nsiAndSubNssiInfo.getCnUeMobilityLevel();
        nsiAndSubNssiInfo.getCnScriptName();

        nsiAndSubNssiInfo.getTnBhBandwidth();
        nsiAndSubNssiInfo.getTnBhLatency();
        nsiAndSubNssiInfo.getTnBhSuggestNssiId();
        nsiAndSubNssiInfo.getTnBhSuggestNssiName();
        nsiAndSubNssiInfo.getTnBhScriptName();

        nsiAndSubNssiInfo.getSuggestNsiId();
        nsiAndSubNssiInfo.getSuggestNsiName();

    }
}
