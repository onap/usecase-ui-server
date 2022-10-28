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

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.onap.usecaseui.server.bean.nsmf.monitor.ServiceInfo;
import org.onap.usecaseui.server.bean.nsmf.monitor.ServiceOnlineUserInfo;
import org.onap.usecaseui.server.bean.nsmf.monitor.ServicePDUSessionEstSRInfo;
import org.onap.usecaseui.server.bean.nsmf.monitor.ServiceTotalBandwidthInfo;
import org.onap.usecaseui.server.bean.nsmf.monitor.SlicingKpiReqInfo;
import org.onap.usecaseui.server.bean.nsmf.monitor.TrafficReqInfo;
import org.onap.usecaseui.server.bean.nsmf.monitor.UsageTrafficInfo;
import org.onap.usecaseui.server.service.slicingdomain.kpi.bean.KpiPDUSessionEstSR;
import org.onap.usecaseui.server.service.slicingdomain.kpi.bean.KpiTotalBandwidth;
import org.onap.usecaseui.server.service.slicingdomain.kpi.bean.KpiTotalTraffic;
import org.onap.usecaseui.server.service.slicingdomain.kpi.bean.KpiUserNumber;
import org.onap.usecaseui.server.service.slicingdomain.kpi.bean.PDUSessionEstSR;
import org.onap.usecaseui.server.service.slicingdomain.kpi.bean.TotalBandwidth;
import org.onap.usecaseui.server.service.slicingdomain.kpi.bean.TotalTraffic;
import org.onap.usecaseui.server.service.slicingdomain.kpi.bean.UserNumbers;

public class ResourceMonitorServiceConvertTest {

    ResourceMonitorServiceConvert resourceMonitorServiceConvert = null;

    @Before
    public void before() throws Exception {
        resourceMonitorServiceConvert = new ResourceMonitorServiceConvert();
    }

    @Test
    public void itCanBuildTrafficReqInfo() {
        ServiceInfo serviceInfo = new ServiceInfo();
        serviceInfo.setServiceId("23er-4567-rt56-rt54");
        String queryTimestamp = "1577071879000";
        resourceMonitorServiceConvert.buildTrafficReqInfo(serviceInfo, queryTimestamp);
    }

    @Test
    public void itCanConvertUsageTrafficInfo() {
        UsageTrafficInfo usageTrafficInfo = new UsageTrafficInfo();
        usageTrafficInfo.setServiceId("sdse-3456-ee34-rt45");
        usageTrafficInfo.setTrafficData("600");

        KpiTotalTraffic kpiTotalTraffic = new KpiTotalTraffic();
        List<TotalTraffic> totalTrafficList = new ArrayList<>();
        kpiTotalTraffic.setResult(totalTrafficList);

        TrafficReqInfo trafficReqInfo = new TrafficReqInfo();
        trafficReqInfo.setId("1234-5678-rt45-3456");
        kpiTotalTraffic.setRequest(trafficReqInfo);

        resourceMonitorServiceConvert.convertUsageTrafficInfo(usageTrafficInfo, kpiTotalTraffic);
    }

    @Test
    public void itCanBuildSlicingKpiReqInfo() {
        ServiceInfo serviceInfo = new ServiceInfo();
        serviceInfo.setServiceId("23er-4567-rt56-rt54");
        String queryTimestamp = "1577071879000";

        resourceMonitorServiceConvert.buildSlicingKpiReqInfo(serviceInfo, queryTimestamp, 4);
    }

    @Test
    public void itCanConvertServiceOnlineUserInfo() {
        ServiceOnlineUserInfo serviceOnlineUserInfo = new ServiceOnlineUserInfo();
        KpiUserNumber kpiUserNumber = new KpiUserNumber();
        List<UserNumbers> userNumbersList = new ArrayList<>();
        UserNumbers userNumbers = new UserNumbers();
        userNumbers.setUserNumber(1);
        userNumbers.setTimeStamp("2019-12-23 11:31:19");
        userNumbersList.add(userNumbers);

        SlicingKpiReqInfo slicingKpiReqInfo = new SlicingKpiReqInfo();
        slicingKpiReqInfo.setTimeStamp("2019-12-23 11:31:19");
        slicingKpiReqInfo.setId("112233");
        slicingKpiReqInfo.setHours(4);
        kpiUserNumber.setRequest(slicingKpiReqInfo);

        kpiUserNumber.setResult(userNumbersList);

        try {
            resourceMonitorServiceConvert.convertServiceOnlineUserInfo(serviceOnlineUserInfo, kpiUserNumber);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void itCanConvertServiceTotalBandwidthInfo() {
        ServiceTotalBandwidthInfo serviceTotalBandwidthInfo = new ServiceTotalBandwidthInfo();
        KpiTotalBandwidth kpiTotalBandwidth = new KpiTotalBandwidth();

        List<TotalBandwidth> totalBandwidthList = new ArrayList<>();
        TotalBandwidth totalBandwidth = new TotalBandwidth();
        totalBandwidth.setBandwidth(100);
        totalBandwidth.setTimeStamp("2019-12-23 11:31:19");
        totalBandwidthList.add(totalBandwidth);
        kpiTotalBandwidth.setResult(totalBandwidthList);


        SlicingKpiReqInfo slicingKpiReqInfo = new SlicingKpiReqInfo();
        slicingKpiReqInfo.setTimeStamp("2019-12-23 11:31:19");
        slicingKpiReqInfo.setId("112233");
        slicingKpiReqInfo.setHours(4);
        kpiTotalBandwidth.setRequest(slicingKpiReqInfo);

        try {
            resourceMonitorServiceConvert
                .convertServiceTotalBandwidthInfo(serviceTotalBandwidthInfo, kpiTotalBandwidth);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void itCanConvertconvertServicePDUSessionEstSRInfo() {
	ServicePDUSessionEstSRInfo servicePDUSessionEstSRInfo = new ServicePDUSessionEstSRInfo();
	KpiPDUSessionEstSR kpiPDUSessionEstSR = new KpiPDUSessionEstSR();

        List<PDUSessionEstSR> kpiPDUSessionEstSRInfoList = new ArrayList<>();
        PDUSessionEstSR kpiPDUSessionEstSRInfo = new PDUSessionEstSR();
        kpiPDUSessionEstSRInfo.setPDUSessionEstSR("100");
        kpiPDUSessionEstSRInfo.setTimeStamp("2019-12-23 11:31:19");
        kpiPDUSessionEstSRInfoList.add(kpiPDUSessionEstSRInfo);
        kpiPDUSessionEstSR.setResult(kpiPDUSessionEstSRInfoList);


        SlicingKpiReqInfo slicingKpiReqInfo = new SlicingKpiReqInfo();
        slicingKpiReqInfo.setTimeStamp("2019-12-23 11:31:19");
        slicingKpiReqInfo.setId("112233");
        slicingKpiReqInfo.setHours(4);
        kpiPDUSessionEstSR.setRequest(slicingKpiReqInfo);

        try {
            resourceMonitorServiceConvert
                .convertServicePDUSessionEstSRInfo(servicePDUSessionEstSRInfo, kpiPDUSessionEstSR);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
