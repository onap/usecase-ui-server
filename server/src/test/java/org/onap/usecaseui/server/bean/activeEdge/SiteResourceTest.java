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
package org.onap.usecaseui.server.bean.activeEdge;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.onap.usecaseui.server.bean.activateEdge.SiteResource;


public class SiteResourceTest {
    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    @Test
    public void testGetSiteResource() throws Exception {
        SiteResource sr = new SiteResource();
        sr.getDescription();
        sr.getRelationshipList();
        sr.getSiteResourceId();
        sr.getSiteResourceName();
        sr.getAdditionalProperties();
        sr.getCity();
        sr.getOperationalStatus();
        sr.getPostalcode();
        sr.getResourceVersion();
        sr.getRole();
        sr.getSelflink();
        sr.getType();
        sr.toString();
    }

    @Test
    public void testSetSiteResource() throws Exception {
        SiteResource sr = new SiteResource();
        sr.setDescription("");
        sr.setRelationshipList(null);
        sr.setSiteResourceId("");
        sr.setSiteResourceName("");
        sr.setAdditionalProperty("", "");
        sr.setCity("");
        sr.setOperationalStatus("");
        sr.setPostalcode("");
        sr.setResourceVersion("");
        sr.setRole("");
        sr.setSelflink("");
        sr.setType("");
    }
}
