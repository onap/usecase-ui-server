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

        nsiAndSubNssiInfo.setAnEnableNSSISelection(true);
        nsiAndSubNssiInfo.setSliceProfile_AN_sNSSAI("scriptTest");
        nsiAndSubNssiInfo.setSliceProfile_AN_pLMNIdList("scriptTest");
        nsiAndSubNssiInfo.setSliceProfile_AN_maxNumberofUEs("scriptTest");
        nsiAndSubNssiInfo.setSliceProfile_AN_maxNumberofPDUSession("scriptTest");
        nsiAndSubNssiInfo.setSliceProfile_AN_expDataRateDL("scriptTest");
        nsiAndSubNssiInfo.setSliceProfile_AN_expDataRateUL("scriptTest");
        nsiAndSubNssiInfo.setSliceProfile_AN_areaTrafficCapDL("scriptTest");
        nsiAndSubNssiInfo.setSliceProfile_AN_areaTrafficCapUL("scriptTest");
        nsiAndSubNssiInfo.setSliceProfile_AN_overallUserDensity("scriptTest");
        nsiAndSubNssiInfo.setSliceProfile_AN_activityFactor("scriptTest");
        nsiAndSubNssiInfo.setSliceProfile_AN_uEMobilityLevel("scriptTest");
        nsiAndSubNssiInfo.setSliceProfile_AN_resourceSharingLevel("scriptTest");
        nsiAndSubNssiInfo.setSliceProfile_AN_sST("scriptTest");
        nsiAndSubNssiInfo.setSliceProfile_AN_cSAvailabilityTarget("scriptTest");
        nsiAndSubNssiInfo.setSliceProfile_AN_cSReliabilityMeanTime("scriptTest");
        nsiAndSubNssiInfo.setSliceProfile_AN_expDataRate("scriptTest");
        nsiAndSubNssiInfo.setSliceProfile_AN_msgSizeByte("scriptTest");
        nsiAndSubNssiInfo.setSliceProfile_AN_transferIntervalTarget("scriptTest");
        nsiAndSubNssiInfo.setSliceProfile_AN_survivalTime("scriptTest");
        nsiAndSubNssiInfo.setSliceProfile_AN_ipAddress("scriptTest");
        nsiAndSubNssiInfo.setSliceProfile_AN_logicInterfaceId("scriptTest");
        nsiAndSubNssiInfo.setSliceProfile_AN_nextHopInfo("scriptTest");

        nsiAndSubNssiInfo.setSliceProfile_TN_BH_jitte("scriptTest");
        nsiAndSubNssiInfo.setSliceProfile_TN_BH_pLMNIdList("scriptTest");
        nsiAndSubNssiInfo.setSliceProfile_TN_BH_sNSSAI("scriptTest");
        nsiAndSubNssiInfo.setSliceProfile_TN_BH_sST("scriptTest");
        nsiAndSubNssiInfo.setSliceProfile_TN_BH_resourceSharingLevel("scriptTest");
        nsiAndSubNssiInfo.setSliceProfile_TN_BH_connectionLinkId("scriptTest");
        nsiAndSubNssiInfo.setTnEnableNSSISelection(true);


        nsiAndSubNssiInfo.setCnEnableNSSISelection(true);
        nsiAndSubNssiInfo.setSliceProfile_CN_pLMNIdList("scriptTest");
        nsiAndSubNssiInfo.setSliceProfile_CN_maxNumberofPDUSession("scriptTest");
        nsiAndSubNssiInfo.setSliceProfile_CN_overallUserDensity("scriptTest");
        nsiAndSubNssiInfo.setSliceProfile_CN_coverageAreaTAList("scriptTest");
        nsiAndSubNssiInfo.setSliceProfile_CN_sST("scriptTest");
        nsiAndSubNssiInfo.setSliceProfile_CN_cSAvailabilityTarget("scriptTest");
        nsiAndSubNssiInfo.setSliceProfile_CN_cSReliabilityMeanTime("scriptTest");
        nsiAndSubNssiInfo.setSliceProfile_CN_expDataRate("scriptTest");
        nsiAndSubNssiInfo.setSliceProfile_CN_msgSizeByte("scriptTest");
        nsiAndSubNssiInfo.setSliceProfile_CN_logicInterfaceId("scriptTest");
        nsiAndSubNssiInfo.setSliceProfile_CN_transferIntervalTarget("scriptTest");
        nsiAndSubNssiInfo.setSliceProfile_CN_survivalTime("scriptTest");
        nsiAndSubNssiInfo.setSliceProfile_CN_ipAddress("scriptTest");
        nsiAndSubNssiInfo.setSliceProfile_CN_nextHopInfo("scriptTest");

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

        nsiAndSubNssiInfo.getAnEnableNSSISelection();
        nsiAndSubNssiInfo.getSliceProfile_AN_sNSSAI();
        nsiAndSubNssiInfo.getSliceProfile_AN_pLMNIdList();
        nsiAndSubNssiInfo.getSliceProfile_AN_maxNumberofUEs();
        nsiAndSubNssiInfo.getSliceProfile_AN_maxNumberofPDUSession();
        nsiAndSubNssiInfo.getSliceProfile_AN_expDataRateDL();
        nsiAndSubNssiInfo.getSliceProfile_AN_expDataRateUL();
        nsiAndSubNssiInfo.getSliceProfile_AN_areaTrafficCapDL();
        nsiAndSubNssiInfo.getSliceProfile_AN_areaTrafficCapUL();
        nsiAndSubNssiInfo.getSliceProfile_AN_overallUserDensity();
        nsiAndSubNssiInfo.getSliceProfile_AN_activityFactor();
        nsiAndSubNssiInfo.getSliceProfile_AN_uEMobilityLevel();
        nsiAndSubNssiInfo.getSliceProfile_AN_resourceSharingLevel();
        nsiAndSubNssiInfo.getSliceProfile_AN_sST();
        nsiAndSubNssiInfo.getSliceProfile_AN_cSAvailabilityTarget();
        nsiAndSubNssiInfo.getSliceProfile_AN_cSReliabilityMeanTime();
        nsiAndSubNssiInfo.getSliceProfile_AN_expDataRate();
        nsiAndSubNssiInfo.getSliceProfile_AN_msgSizeByte();
        nsiAndSubNssiInfo.getSliceProfile_AN_transferIntervalTarget();
        nsiAndSubNssiInfo.getSliceProfile_AN_survivalTime();
        nsiAndSubNssiInfo.getSliceProfile_AN_ipAddress();
        nsiAndSubNssiInfo.getSliceProfile_AN_logicInterfaceId();
        nsiAndSubNssiInfo.getSliceProfile_AN_nextHopInfo();

        nsiAndSubNssiInfo.getSliceProfile_TN_BH_jitte();
        nsiAndSubNssiInfo.getSliceProfile_TN_BH_pLMNIdList();
        nsiAndSubNssiInfo.getSliceProfile_TN_BH_sNSSAI();
        nsiAndSubNssiInfo.getSliceProfile_TN_BH_sST();
        nsiAndSubNssiInfo.getSliceProfile_TN_BH_resourceSharingLevel();
        nsiAndSubNssiInfo.getSliceProfile_TN_BH_connectionLinkId();
        nsiAndSubNssiInfo.getTnEnableNSSISelection();


        nsiAndSubNssiInfo.getCnEnableNSSISelection();
        nsiAndSubNssiInfo.getSliceProfile_CN_pLMNIdList();
        nsiAndSubNssiInfo.getSliceProfile_CN_maxNumberofPDUSession();
        nsiAndSubNssiInfo.getSliceProfile_CN_overallUserDensity();
        nsiAndSubNssiInfo.getSliceProfile_CN_coverageAreaTAList();
        nsiAndSubNssiInfo.getSliceProfile_CN_sST();
        nsiAndSubNssiInfo.getSliceProfile_CN_cSAvailabilityTarget();
        nsiAndSubNssiInfo.getSliceProfile_CN_cSReliabilityMeanTime();
        nsiAndSubNssiInfo.getSliceProfile_CN_expDataRate();
        nsiAndSubNssiInfo.getSliceProfile_CN_msgSizeByte();
        nsiAndSubNssiInfo.getSliceProfile_CN_logicInterfaceId();
        nsiAndSubNssiInfo.getSliceProfile_CN_transferIntervalTarget();
        nsiAndSubNssiInfo.getSliceProfile_CN_survivalTime();
        nsiAndSubNssiInfo.getSliceProfile_CN_ipAddress();
        nsiAndSubNssiInfo.getSliceProfile_CN_nextHopInfo();

    }
}
