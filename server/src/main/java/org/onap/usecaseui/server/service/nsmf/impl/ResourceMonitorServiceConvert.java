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
import org.onap.usecaseui.server.bean.nsmf.monitor.ServiceInfo;
import org.onap.usecaseui.server.bean.nsmf.monitor.ServiceOnlineUserInfo;
import org.onap.usecaseui.server.bean.nsmf.monitor.ServiceTotalBandwidthInfo;
import org.onap.usecaseui.server.bean.nsmf.monitor.SlicingKpiReqInfo;
import org.onap.usecaseui.server.bean.nsmf.monitor.TotalBandwidthInfo;
import org.onap.usecaseui.server.bean.nsmf.monitor.TrafficReqInfo;
import org.onap.usecaseui.server.bean.nsmf.monitor.UsageTrafficInfo;
import org.onap.usecaseui.server.bean.nsmf.monitor.UserNumberInfo;
import org.onap.usecaseui.server.constant.nsmf.NsmfParamConstant;
import org.onap.usecaseui.server.service.slicingdomain.kpi.bean.KpiTotalBandwidth;
import org.onap.usecaseui.server.service.slicingdomain.kpi.bean.KpiTotalTraffic;
import org.onap.usecaseui.server.service.slicingdomain.kpi.bean.KpiUserNumber;
import org.onap.usecaseui.server.service.slicingdomain.kpi.bean.TotalBandwidth;
import org.onap.usecaseui.server.service.slicingdomain.kpi.bean.UserNumbers;
import org.onap.usecaseui.server.util.nsmf.NsmfCommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;

@Service("ResourceMonitorConvertService")
@org.springframework.context.annotation.Configuration
@EnableAspectJAutoProxy
public class ResourceMonitorServiceConvert {

    private static final Logger logger = LoggerFactory.getLogger(ResourceMonitorServiceConvert.class);

    TrafficReqInfo buildTrafficReqInfo(ServiceInfo serviceInfo, String queryTimestamp) {
        TrafficReqInfo trafficReqInfo = new TrafficReqInfo();
        trafficReqInfo.setId(serviceInfo.getServiceId());
        trafficReqInfo.setTimeStamp(queryTimestamp);
        return trafficReqInfo;
    }

    void convertUsageTrafficInfo(UsageTrafficInfo usageTrafficInfo, KpiTotalTraffic kpiTotalTraffic) {
        usageTrafficInfo.setServiceId(kpiTotalTraffic.getRequest().getId());
        usageTrafficInfo.setTrafficData(String.valueOf(kpiTotalTraffic.getResult().get(0)));
    }

    SlicingKpiReqInfo buildSlicingKpiReqInfo(ServiceInfo serviceInfo, String queryTimestamp, int kpiHours) {
        SlicingKpiReqInfo slicingKpiReqInfo = new SlicingKpiReqInfo();
        slicingKpiReqInfo.setId(serviceInfo.getServiceId());
        slicingKpiReqInfo.setTimeStamp(queryTimestamp);
        slicingKpiReqInfo.setHours(kpiHours);
        return slicingKpiReqInfo;
    }

    void convertServiceOnlineUserInfo(ServiceOnlineUserInfo serviceOnlineUserInfo, KpiUserNumber kpiUserNumber)
        throws InvocationTargetException, IllegalAccessException, ParseException {

        List<UserNumberInfo> userNumberInfoList = new ArrayList<>();
        serviceOnlineUserInfo.setId(kpiUserNumber.getRequest().getId());

        if (kpiUserNumber.getResult() != null) {
            for (UserNumbers userNumbers : kpiUserNumber.getResult()) {
                String newTimeStamp = NsmfCommonUtil
                    .timestamp2Time(userNumbers.getTimeStamp().replace("T", NsmfParamConstant.SPACE));
                UserNumberInfo userNumberInfo = new UserNumberInfo();
                userNumberInfo.setTimeStamp(newTimeStamp);
                userNumberInfo.setUserNumber(String.valueOf(userNumbers.getUserNumber()));
                userNumberInfoList.add(userNumberInfo);
            }
        }

        serviceOnlineUserInfo.setUserNumberInfoList(userNumberInfoList);
    }

    void convertServiceTotalBandwidthInfo(ServiceTotalBandwidthInfo serviceTotalBandwidthInfo,
        KpiTotalBandwidth kpiTotalBandwidth)
        throws InvocationTargetException, IllegalAccessException, ParseException {

        List<TotalBandwidthInfo> totalBandwidthInfoList = new ArrayList<>();
        serviceTotalBandwidthInfo.setId(kpiTotalBandwidth.getRequest().getId());
        if (kpiTotalBandwidth.getResult() != null) {
            for (TotalBandwidth totalBandwidth : kpiTotalBandwidth.getResult()) {
                String newTimeStamp = NsmfCommonUtil
                    .timestamp2Time(totalBandwidth.getTimeStamp().replace("T", NsmfParamConstant.SPACE));
                TotalBandwidthInfo totalBandwidthInfo = new TotalBandwidthInfo();
                totalBandwidthInfo.setTimestamp(newTimeStamp);
                totalBandwidthInfo.setBandwidth(String.valueOf(totalBandwidth.getBandwidth()));
                totalBandwidthInfoList.add(totalBandwidthInfo);
            }
        }

        serviceTotalBandwidthInfo.setTotalBandwidthInfoList(totalBandwidthInfoList);
    }
}
