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

import static org.mockito.ArgumentMatchers.any;


public class E2EServiceInstanceRequestTest {
    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }
    @Test
    public void  testGetE2EServiceInstanceRequest() throws Exception{
        E2EServiceInstanceRequest e2esir = new E2EServiceInstanceRequest();
        e2esir.getAdditionalProperties();
        e2esir.getService();

    }
    @Test
    public void  testSetE2EServiceInstanceRequest() throws Exception{
        E2EServiceInstanceRequest e2esir = new E2EServiceInstanceRequest();
        e2esir.setAdditionalProperty("","");
        e2esir.setAdditionalProperties(null);
        e2esir.setService(null);
    }
}
