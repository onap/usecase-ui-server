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
package org.onap.usecaseui.server.controller.csmf;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.onap.usecaseui.server.bean.csmf.SlicingOrder;
import org.onap.usecaseui.server.service.csmf.SlicingService;

public class SlicingControllerTest {

    @Test
    public void testCreateSlicingService() {
        SlicingService slicingService = mock(SlicingService.class);

        SlicingController slicingController = new SlicingController();
        slicingController.setSlicingService(slicingService);

        SlicingOrder slicingOrder = new SlicingOrder();
        slicingController.createSlicingService(slicingOrder);
        verify(slicingService, times(1)).createSlicingService(slicingOrder);
    }

    @Test
    public void testQuerySlicingServiceOrder() {
        SlicingService slicingService = mock(SlicingService.class);

        SlicingController slicingController = new SlicingController();
        slicingController.setSlicingService(slicingService);

        SlicingOrder slicingOrder = new SlicingOrder();
        slicingController.querySlicingServiceOrder("processing", "1", "100");
        verify(slicingService, times(1)).querySlicingOrderList("processing", "1", "100");
    }
}
