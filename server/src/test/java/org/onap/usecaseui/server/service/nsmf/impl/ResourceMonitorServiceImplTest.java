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
package org.onap.usecaseui.server.service.nsmf.impl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.onap.usecaseui.server.util.CallStub.failedCall;
import static org.onap.usecaseui.server.util.CallStub.successfulCall;

import com.alibaba.fastjson.JSONObject;
import java.util.ArrayList;
import java.util.List;
import okhttp3.RequestBody;
import org.junit.Before;
import org.junit.Test;
import org.onap.usecaseui.server.bean.nsmf.monitor.ServiceInfo;
import org.onap.usecaseui.server.bean.nsmf.monitor.ServiceList;
import org.onap.usecaseui.server.constant.nsmf.NsmfParamConstant;
import org.onap.usecaseui.server.service.slicingdomain.aai.AAISliceService;
import org.onap.usecaseui.server.service.slicingdomain.kpi.KpiSliceService;
import org.onap.usecaseui.server.service.slicingdomain.kpi.bean.KpiTotalBandwidth;
import org.onap.usecaseui.server.service.slicingdomain.kpi.bean.KpiTotalTraffic;
import org.onap.usecaseui.server.service.slicingdomain.kpi.bean.KpiUserNumber;

public class ResourceMonitorServiceImplTest {

    ResourceMonitorServiceImpl resourceMonitorService = null;
    KpiSliceService kpiSliceService = null;

    @Before
    public void before() throws Exception {
        kpiSliceService = mock(KpiSliceService.class);
        resourceMonitorService = new ResourceMonitorServiceImpl(kpiSliceService);
    }

    @Test
    public void itCanInitConfig() {
        resourceMonitorService.initConfig();
    }

    @Test
    public void itCanQuerySlicingUsageTraffic() {
        ServiceList serviceList = new ServiceList();
        List<ServiceInfo> serviceInfoList = new ArrayList<>();
        ServiceInfo serviceInfo = new ServiceInfo();
        serviceInfo.setServiceId("123e-456t-567t-yui8");
        serviceInfoList.add(serviceInfo);
        serviceList.setServiceInfoList(serviceInfoList);
        String queryTimestamp = "1577071879000";

        KpiTotalTraffic kpiTotalTraffic = new KpiTotalTraffic();
        RequestBody body = null;
        when(kpiSliceService.listTotalTraffic(body)).thenReturn(successfulCall(kpiTotalTraffic));
        resourceMonitorService.querySlicingUsageTraffic(queryTimestamp, serviceList);
    }

    @Test
    public void querySlicingUsageTrafficWithThrowsException() {
        ServiceList serviceList = new ServiceList();
        List<ServiceInfo> serviceInfoList = new ArrayList<>();
        ServiceInfo serviceInfo = new ServiceInfo();
        serviceInfo.setServiceId("123e-456t-567t-yui8");
        serviceInfoList.add(serviceInfo);
        serviceList.setServiceInfoList(serviceInfoList);
        String queryTimestamp = "1577071879000";

        RequestBody body = null;
        when(kpiSliceService.listTotalTraffic(body)).thenReturn(failedCall("kpi is not exist!"));
        resourceMonitorService.querySlicingUsageTraffic(queryTimestamp, serviceList);
    }

    @Test
    public void itCanQuerySlicingOnlineUserNumber() {
        ServiceList serviceList = new ServiceList();
        List<ServiceInfo> serviceInfoList = new ArrayList<>();
        ServiceInfo serviceInfo = new ServiceInfo();
        serviceInfo.setServiceId("123e-456t-567t-yui8");
        serviceInfoList.add(serviceInfo);
        serviceList.setServiceInfoList(serviceInfoList);
        String queryTimestamp = "1577071879000";

        KpiUserNumber kpiUserNumber = new KpiUserNumber();
        RequestBody body = null;
        when(kpiSliceService.listUserNumber(body)).thenReturn(successfulCall(kpiUserNumber));
        resourceMonitorService.querySlicingOnlineUserNumber(queryTimestamp, serviceList);
    }

    @Test
    public void querySlicingOnlineUserNumberWithThrowsException() {
        ServiceList serviceList = new ServiceList();
        List<ServiceInfo> serviceInfoList = new ArrayList<>();
        ServiceInfo serviceInfo = new ServiceInfo();
        serviceInfo.setServiceId("123e-456t-567t-yui8");
        serviceInfoList.add(serviceInfo);
        serviceList.setServiceInfoList(serviceInfoList);
        String queryTimestamp = "1577071879000";

        RequestBody body = null;
        when(kpiSliceService.listUserNumber(body)).thenReturn(failedCall("kpi is not exist!"));
        resourceMonitorService.querySlicingOnlineUserNumber(queryTimestamp, serviceList);
    }

    @Test
    public void itCanQuerySlicingTotalBandwidth() {
        ServiceList serviceList = new ServiceList();
        List<ServiceInfo> serviceInfoList = new ArrayList<>();
        ServiceInfo serviceInfo = new ServiceInfo();
        serviceInfo.setServiceId("123e-456t-567t-yui8");
        serviceInfoList.add(serviceInfo);
        serviceList.setServiceInfoList(serviceInfoList);
        String queryTimestamp = "1577071879000";

        KpiTotalBandwidth kpiTotalBandwidth = new KpiTotalBandwidth();
        RequestBody body = null;
        when(kpiSliceService.listTotalBandwidth(body)).thenReturn(successfulCall(kpiTotalBandwidth));
        resourceMonitorService.querySlicingTotalBandwidth(queryTimestamp, serviceList);
    }

    @Test
    public void querySlicingTotalBandwidthWithThrowsException() {
        ServiceList serviceList = new ServiceList();
        List<ServiceInfo> serviceInfoList = new ArrayList<>();
        ServiceInfo serviceInfo = new ServiceInfo();
        serviceInfo.setServiceId("123e-456t-567t-yui8");
        serviceInfoList.add(serviceInfo);
        serviceList.setServiceInfoList(serviceInfoList);
        String queryTimestamp = "1577071879000";

        RequestBody body = null;
        when(kpiSliceService.listTotalBandwidth(body)).thenReturn(failedCall("kpi is not exist!"));
        resourceMonitorService.querySlicingTotalBandwidth(queryTimestamp, serviceList);
    }

    @Test
    public void querySlicingPDUSessionEstSRWithThrowsException() {
        ServiceList serviceList = new ServiceList();
        List<ServiceInfo> serviceInfoList = new ArrayList<>();
        ServiceInfo serviceInfo = new ServiceInfo();
        serviceInfo.setServiceId("123e-456t-567t-yui8");
        serviceInfoList.add(serviceInfo);
        serviceList.setServiceInfoList(serviceInfoList);
        String queryTimestamp = "1577071879000";

        RequestBody body = null;
        when(kpiSliceService.listPDUSessionEstSR(body)).thenReturn(failedCall("kpi is not exist!"));
        resourceMonitorService.querySlicingPDUSessionEstSR(queryTimestamp, serviceList);
    }
}
