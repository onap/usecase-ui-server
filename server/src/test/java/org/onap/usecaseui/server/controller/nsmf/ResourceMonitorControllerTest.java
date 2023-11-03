/*
 * Copyright (C) 2019 CMCC, Inc. and others. All rights reserved.
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
package org.onap.usecaseui.server.controller.nsmf;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.onap.usecaseui.server.bean.nsmf.monitor.ServiceInfo;
import org.onap.usecaseui.server.bean.nsmf.monitor.ServiceList;
import org.onap.usecaseui.server.service.nsmf.ResourceMonitorService;

public class ResourceMonitorControllerTest {

    @Test
    public void testQuerySlicingUsageTraffic() {
        ResourceMonitorService resourceMonitorService = mock(ResourceMonitorService.class);
        ResourceMonitorController resourceMonitorController = new ResourceMonitorController();
        resourceMonitorController.setResourceMonitorService(resourceMonitorService);

        ServiceList serviceList = new ServiceList();
        List<ServiceInfo> serviceInfoList = new ArrayList<>();
        ServiceInfo serviceInfo = new ServiceInfo();
        serviceInfo.setServiceId("1234-9067-4356-9876");
        serviceInfoList.add(serviceInfo);
        serviceList.setServiceInfoList(serviceInfoList);

        resourceMonitorController.querySlicingUsageTraffic("1577016963000", serviceList);
        verify(resourceMonitorService, times(1)).querySlicingUsageTraffic("1577016963000", serviceList);

    }

    @Test
    public void testQuerySlicingOnlineUserNumber() {
        ResourceMonitorService resourceMonitorService = mock(ResourceMonitorService.class);
        ResourceMonitorController resourceMonitorController = new ResourceMonitorController();
        resourceMonitorController.setResourceMonitorService(resourceMonitorService);

        ServiceList serviceList = new ServiceList();
        List<ServiceInfo> serviceInfoList = new ArrayList<>();
        ServiceInfo serviceInfo = new ServiceInfo();
        serviceInfo.setServiceId("1234-9067-4356-9876");
        serviceInfoList.add(serviceInfo);
        serviceList.setServiceInfoList(serviceInfoList);

        resourceMonitorController.querySlicingOnlineUserNumber("1577016963000", serviceList);
        verify(resourceMonitorService, times(1)).querySlicingOnlineUserNumber("1577016963000", serviceList);
    }

    @Test
    public void testQuerySlicingTotalBandwidth() {
        ResourceMonitorService resourceMonitorService = mock(ResourceMonitorService.class);
        ResourceMonitorController resourceMonitorController = new ResourceMonitorController();
        resourceMonitorController.setResourceMonitorService(resourceMonitorService);

        ServiceList serviceList = new ServiceList();
        List<ServiceInfo> serviceInfoList = new ArrayList<>();
        ServiceInfo serviceInfo = new ServiceInfo();
        serviceInfo.setServiceId("1234-9067-4356-9876");
        serviceInfoList.add(serviceInfo);
        serviceList.setServiceInfoList(serviceInfoList);

        resourceMonitorController.querySlicingTotalBandwidth("1577016963000", serviceList);
        verify(resourceMonitorService, times(1)).querySlicingTotalBandwidth("1577016963000", serviceList);
    }

    @Test
    public void testQuerySlicingPDUSessionEstSR() {
        ResourceMonitorService resourceMonitorService = mock(ResourceMonitorService.class);
        ResourceMonitorController resourceMonitorController = new ResourceMonitorController();
        resourceMonitorController.setResourceMonitorService(resourceMonitorService);

        ServiceList serviceList = new ServiceList();
        List<ServiceInfo> serviceInfoList = new ArrayList<>();
        ServiceInfo serviceInfo = new ServiceInfo();
        serviceInfo.setServiceId("1234-9067-4356-9876");
        serviceInfoList.add(serviceInfo);
        serviceList.setServiceInfoList(serviceInfoList);

        resourceMonitorController.querySlicingPDUSessionEstSR("1577016963000", serviceList);
        verify(resourceMonitorService, times(1)).querySlicingPDUSessionEstSR("1577016963000", serviceList);
    }
}
