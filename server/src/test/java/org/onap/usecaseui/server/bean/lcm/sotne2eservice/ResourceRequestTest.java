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

import java.util.HashMap;


public class ResourceRequestTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    @Test
    public void testGetResourceRequest() throws Exception {
        ResourceRequest rr = new ResourceRequest();
        rr.getAdditionalProperties();
        rr.getParameters();
        rr.getResourceCustomizationUuid();
        rr.getResourceInvariantUuid();
        rr.getResourceName();
        rr.getResourceUuid();
    }

    @Test
    public void testSetResourceRequest() throws Exception {
        ResourceRequest rr = new ResourceRequest();
        HashMap<String , Object> hr = new HashMap<String, Object>();
        rr.setAdditionalProperties(hr);
        E2EParameters e2EParameters = new E2EParameters();
        rr.setParameters(e2EParameters);
        rr.setResourceCustomizationUuid("");
        rr.setResourceInvariantUuid("");
        rr.setResourceName("");
        rr.setResourceUuid("");
    }
}
