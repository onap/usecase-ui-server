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

public class SlicingOrderTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    @Test
    public void testSetAndGetSlicingOrder() throws Exception {

        SlicingOrderDetail slicingOrderDetail = new SlicingOrderDetail();

        slicingOrderDetail.setCoverageArea("sdfgdert");
        slicingOrderDetail.setExpDataRateDL(100);
        slicingOrderDetail.setExpDataRateUL(200);
        slicingOrderDetail.setLatency(20);
        slicingOrderDetail.setMaxNumberofUEs(200);
        slicingOrderDetail.setUEMobilityLevel("share");
        slicingOrderDetail.setName("csmf");
        slicingOrderDetail.setResourceSharingLevel("share");
        slicingOrderDetail.setUseInterval("20");

        SlicingOrder slicingOrder = new SlicingOrder();
        slicingOrder.setSlicing_order_info(slicingOrderDetail);
        slicingOrder.getSlicing_order_info();
    }

}
