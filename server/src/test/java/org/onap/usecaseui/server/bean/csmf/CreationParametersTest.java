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
package org.onap.usecaseui.server.bean.csmf;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CreationParametersTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    @Test
    public void testSetAndGetCreationParameters() throws Exception {

        CreationRequestInputs creationRequestInputs = new CreationRequestInputs();
        creationRequestInputs.setCoverageAreaList("1233");
        creationRequestInputs.setExpDataRateDL(123);
        creationRequestInputs.setExpDataRateUL(234);
        creationRequestInputs.setLatency(12);
        creationRequestInputs.setMaxNumberofUEs(123);
        creationRequestInputs.setResourceSharingLevel("share");
        creationRequestInputs.setUEMobilityLevel("mobile");
        creationRequestInputs.setUseInterval("230");

        CreationParameters creationParameters = new CreationParameters();
        creationParameters.setRequestInputs(creationRequestInputs);
        creationParameters.getRequestInputs();
    }
}
