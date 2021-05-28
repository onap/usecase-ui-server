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
package org.onap.usecaseui.server.service.slicingdomain.aai.bean;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AAIServiceProfilesTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    @Test
    public void testSetAndGetAAIServiceProfiles() throws Exception {
        AAIServiceProfiles aaiServiceProfiles = new AAIServiceProfiles();
        aaiServiceProfiles.setActivityFactor(100);
        aaiServiceProfiles.setCoverageAreaTAList("test");
        aaiServiceProfiles.setJitter(20);
        aaiServiceProfiles.setLatency(20);
        aaiServiceProfiles.setMaxNumberOfUEs(20);
        aaiServiceProfiles.setProfileId("profile");
        aaiServiceProfiles.setReliability(200);
        aaiServiceProfiles.setResourceSharingLevel("share");
        aaiServiceProfiles.setResourceVersion("ver123");
        aaiServiceProfiles.setSurvivalTime(200);
        aaiServiceProfiles.setUeMobilityLevel("mobile");
        aaiServiceProfiles.setAvailability(6);
        aaiServiceProfiles.setDLThptPerSlice(200);
        aaiServiceProfiles.setDLThptPerUE(300);
        aaiServiceProfiles.setULThptPerSlice(200);
        aaiServiceProfiles.setULThptPerUE(100);
        aaiServiceProfiles.setMaxPktSize(200);
        aaiServiceProfiles.setMaxNumberofConns(200);
        aaiServiceProfiles.setTermDensity(300);

        aaiServiceProfiles.getActivityFactor();
        aaiServiceProfiles.getCoverageAreaTAList();
        aaiServiceProfiles.getJitter();
        aaiServiceProfiles.getLatency();
        aaiServiceProfiles.getMaxNumberOfUEs();
        aaiServiceProfiles.getProfileId();
        aaiServiceProfiles.getReliability();
        aaiServiceProfiles.getResourceSharingLevel();
        aaiServiceProfiles.getResourceVersion();
        aaiServiceProfiles.getSurvivalTime();
        aaiServiceProfiles.getUeMobilityLevel();

        aaiServiceProfiles.getAvailability();
        aaiServiceProfiles.getDLThptPerSlice();
        aaiServiceProfiles.getDLThptPerUE();
        aaiServiceProfiles.getULThptPerSlice();
        aaiServiceProfiles.getULThptPerUE();
        aaiServiceProfiles.getMaxPktSize();
        aaiServiceProfiles.getMaxNumberofConns();
        aaiServiceProfiles.getTermDensity();
    }
}
