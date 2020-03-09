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

import java.util.List;

public class ServiceEstimationTest {
    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    @Test
    public void testGetServiceEstimation() throws Exception {
        ServiceEstimationBean seb = new ServiceEstimationBean();
        seb.getBandWidth();
        seb.getBandwidthCost();
        seb.getCameraCost();
        seb.getServiceCost();
        seb.getVpnCost();
        seb.getVpnInformations();
        seb.getWlanAccess();
    }

    @Test
    public void testSetServiceEstimation() throws Exception {
        ServiceEstimationBean seb = new ServiceEstimationBean();
        seb.setBandWidth("");
        seb.setBandwidthCost("");
        seb.setCameraCost("");
        seb.setServiceCost("");
        seb.setVpnCost("");
        seb.setVpnInformations(null);
        seb.setWlanAccess(null);
    }
}
