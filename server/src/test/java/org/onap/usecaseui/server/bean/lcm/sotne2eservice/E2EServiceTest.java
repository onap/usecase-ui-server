/**
 * Copyright (C) 2020 Huawei, Inc. and others. All rights reserved.
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
package org.onap.usecaseui.server.bean.lcm.sotne2eservice;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class E2EServiceTest {
    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }
    @Test
    public void  testGetE2EService() throws Exception{
        E2EService e2es = new E2EService();
        e2es.getAdditionalProperties();
        e2es.getDescription();
        e2es.getGlobalSubscriberId();
        e2es.getName();
        e2es.getParameters();
        e2es.getServiceInvariantUuid();
        e2es.getServiceType();
        e2es.getClass();
        e2es.getServiceUuid();
    }
    @Test
    public void  testSetE2EService() throws Exception{
        E2EService e2es = new E2EService();
        e2es.setAdditionalProperties(null);
        e2es.setDescription("");
        e2es.setGlobalSubscriberId("");
        e2es.setName("");
        e2es.setParameters(null);
        e2es.setServiceInvariantUuid("");
        e2es.setServiceType("");
        e2es.setServiceUuid("");
    }
}
