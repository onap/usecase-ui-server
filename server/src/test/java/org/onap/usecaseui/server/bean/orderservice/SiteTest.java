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
package org.onap.usecaseui.server.bean.orderservice;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SiteTest {
    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    @Test
    public void testGetSite() throws Exception {
        Site site = new Site();
        site.getAddress();
        site.getCapacity();
        site.getDescription();
        site.getEmail();
        site.getIsCloudSite();
        site.getLocation();
        site.getPrice();
        site.getRole();
        site.getSiteId();
        site.getSiteinterface();
        site.getSiteName();
        site.getSiteStatus();
        site.getSubnet();
        site.getType();
        site.getZipCode();
    }

    @Test
    public void testSetSite() throws Exception {
        Site site = new Site();
        site.setAddress("");
        site.setCapacity("");
        site.setDescription("");
        site.setEmail("");
        site.setIsCloudSite("");
        site.setLocation("");
        site.setPrice("");
        site.setRole("");
        site.setSiteId("");
        site.setSiteinterface("");
        site.setSiteName("");
        site.setSiteStatus("");
        site.setSubnet("");
        site.setType("");
        site.setZipCode("");
    }
}
