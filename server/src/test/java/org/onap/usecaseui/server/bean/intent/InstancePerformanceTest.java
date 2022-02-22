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

import java.util.Date;
import javax.xml.crypto.Data;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class InstancePerformanceTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    @Test
    public void testSetAndGetInstancePerformance() throws Exception {
        InstancePerformance instancePerformance = new InstancePerformance();
        instancePerformance.setBandwidth(5);
        Date date = new Date();
        instancePerformance.setDate(date);
        instancePerformance.setId(123);
        instancePerformance.setJobId("123");
        instancePerformance.setMaxBandwidth(123);
        instancePerformance.setResourceInstanceId("123");

        instancePerformance.getBandwidth();
        instancePerformance.getDate();
        instancePerformance.getId();
        instancePerformance.getJobId();
        instancePerformance.getMaxBandwidth();
        instancePerformance.getResourceInstanceId();
    }
}
