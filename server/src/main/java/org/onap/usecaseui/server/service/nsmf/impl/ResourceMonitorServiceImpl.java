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

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.annotation.Resource;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import org.onap.usecaseui.server.bean.nsmf.common.ResultHeader;
import org.onap.usecaseui.server.bean.nsmf.common.ServiceResult;
import org.onap.usecaseui.server.bean.nsmf.monitor.ServiceInfo;
import org.onap.usecaseui.server.bean.nsmf.monitor.ServiceList;
import org.onap.usecaseui.server.bean.nsmf.monitor.ServiceOnlineUserInfo;
import org.onap.usecaseui.server.bean.nsmf.monitor.ServiceOnlineUserList;
import org.onap.usecaseui.server.bean.nsmf.monitor.ServiceTotalBandwidthInfo;
import org.onap.usecaseui.server.bean.nsmf.monitor.ServiceTotalBandwidthList;
import org.onap.usecaseui.server.bean.nsmf.monitor.SlicingKpiReqInfo;
import org.onap.usecaseui.server.bean.nsmf.monitor.TrafficReqInfo;
import org.onap.usecaseui.server.bean.nsmf.monitor.UsageTrafficInfo;
import org.onap.usecaseui.server.bean.nsmf.monitor.UsageTrafficList;
import org.onap.usecaseui.server.constant.nsmf.NsmfCodeConstant;
import org.onap.usecaseui.server.constant.nsmf.NsmfParamConstant;
import org.onap.usecaseui.server.service.nsmf.ResourceMonitorService;
import org.onap.usecaseui.server.service.slicingdomain.kpi.KpiSliceService;
import org.onap.usecaseui.server.service.slicingdomain.kpi.bean.KpiTotalBandwidth;
import org.onap.usecaseui.server.service.slicingdomain.kpi.bean.KpiTotalTraffic;
import org.onap.usecaseui.server.service.slicingdomain.kpi.bean.KpiUserNumber;
import org.onap.usecaseui.server.util.RestfulServices;
import org.onap.usecaseui.server.util.nsmf.NsmfCommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;
import retrofit2.Response;

@Service("ResourceMonitorService")
@org.springframework.context.annotation.Configuration
@EnableAspectJAutoProxy
public class ResourceMonitorServiceImpl implements ResourceMonitorService {

    private final static Logger logger = LoggerFactory.getLogger(ResourceMonitorServiceImpl.class);

    @Resource(name = "ResourceMonitorConvertService")
    private ResourceMonitorServiceConvert resourceMonitorServiceConvert;

    Gson gson = new Gson();

    private KpiSliceService kpiSliceService;
    private int kpiHours;

    public ResourceMonitorServiceImpl() {
        this(RestfulServices.create(KpiSliceService.class));
    }

    public ResourceMonitorServiceImpl(KpiSliceService kpiSliceService) {
        this.kpiSliceService = kpiSliceService;
    }

    public void initConfig() {
        String slicingPath = System.getProperty("user.dir") + File.separator + "config" + File.separator + "slicing.properties";
        Properties p = new Properties();
        try {
            InputStream inputStream = new FileInputStream(new File(slicingPath));
            p.load(inputStream);
            String strKpiHours = p.getProperty("slicing.kpi.hours");
            this.kpiHours = Integer.parseInt(strKpiHours);
            logger.info("kpiHours configuration is :{}", this.kpiHours);
        } catch (IOException e1) {
            logger.error("get configuration file arise error :{}", e1);
        }
    }

    @Override
    public ServiceResult querySlicingUsageTraffic(String queryTimestamp, ServiceList serviceList) {
        ServiceResult serviceResult = new ServiceResult();
        ResultHeader resultHeader = new ResultHeader();
        initConfig();
        UsageTrafficList usageTrafficList = new UsageTrafficList();
        List<UsageTrafficInfo> usageTrafficInfoList = new ArrayList<>();
        List<ServiceInfo> serviceInfoList = serviceList.getServiceInfoList();
        String resultMsg = "";

        try {
            for (ServiceInfo serviceInfo : serviceInfoList) {
                String newTimestamp = NsmfCommonUtil.time2Timestamp(queryTimestamp)
                    .replace(NsmfParamConstant.SPACE, "T");
                TrafficReqInfo trafficReqInfo = resourceMonitorServiceConvert
                    .buildTrafficReqInfo(serviceInfo, newTimestamp);
                String jsonstr = JSON.toJSONString(trafficReqInfo);
                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonstr.toString());
                Response<KpiTotalTraffic> response = this.kpiSliceService.listTotalTraffic(requestBody).execute();
                if (response.isSuccessful()) {
                    KpiTotalTraffic kpiTotalTraffic = response.body();
                    logger.info("querySlicingUsageTraffic: listTotalTraffic reponse is:{}",
                        gson.toJson(kpiTotalTraffic).toString());
                    UsageTrafficInfo usageTrafficInfo = new UsageTrafficInfo();
                    resourceMonitorServiceConvert.convertUsageTrafficInfo(usageTrafficInfo, kpiTotalTraffic);
                    usageTrafficInfoList.add(usageTrafficInfo);
                    resultMsg = "5G slicing usage traffic query result.";
                    resultHeader.setResult_code(NsmfCodeConstant.SUCCESS_CODE);
                } else {
                    logger.error(String
                        .format("querySlicingUsageTraffic: Can not get ActivateService[code={}, message={}]",
                            response.code(),
                            response.message()));
                    resultMsg = "5G slicing usage traffic query failed!";
                    resultHeader.setResult_code(NsmfCodeConstant.ERROR_CODE_UNKNOWN);
                }
            }
        } catch (Exception e) {
            resultMsg = "5G slicing usage traffic query failed. Unknown exception occurred!";
            resultHeader.setResult_code(NsmfCodeConstant.ERROR_CODE_UNKNOWN);
            logger.error("Exception in querySlicingUsageTraffic :{}",e);
        }
        usageTrafficList.setUsageTrafficInfoList(usageTrafficInfoList);
        logger.info(resultMsg);
        logger.info("querySlicingUsageTraffic: 5G slicing usage traffic query has been finished.");
        resultHeader.setResult_message(resultMsg);
        serviceResult.setResult_header(resultHeader);
        serviceResult.setResult_body(usageTrafficList);
        return serviceResult;
    }

    @Override
    public ServiceResult querySlicingOnlineUserNumber(String queryTimestamp, ServiceList serviceList) {
        ServiceResult serviceResult = new ServiceResult();
        ResultHeader resultHeader = new ResultHeader();
        initConfig();
        ServiceOnlineUserList serviceOnlineUserList = new ServiceOnlineUserList();
        List<ServiceOnlineUserInfo> serviceOnlineUserInfoList = new ArrayList<>();
        List<ServiceInfo> serviceInfoList = serviceList.getServiceInfoList();

        String resultMsg = "";

        try {
            for (ServiceInfo serviceInfo : serviceInfoList) {
                String newTimestamp = NsmfCommonUtil.time2Timestamp(queryTimestamp)
                    .replace(NsmfParamConstant.SPACE, "T");
                SlicingKpiReqInfo slicingKpiReqInfo = resourceMonitorServiceConvert
                    .buildSlicingKpiReqInfo(serviceInfo, newTimestamp, kpiHours);
                String jsonstr = JSON.toJSONString(slicingKpiReqInfo);
                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonstr.toString());
                Response<KpiUserNumber> response = this.kpiSliceService.listUserNumber(requestBody).execute();

                if (response.isSuccessful()) {
                    KpiUserNumber kpiUserNumber = response.body();
                    logger.info("querySlicingOnlineUserNumber: listUserNumber reponse is:{}",
                        gson.toJson(kpiUserNumber).toString());
                    ServiceOnlineUserInfo serviceOnlineUserInfo = new ServiceOnlineUserInfo();
                    resourceMonitorServiceConvert.convertServiceOnlineUserInfo(serviceOnlineUserInfo, kpiUserNumber);
                    serviceOnlineUserInfoList.add(serviceOnlineUserInfo);
                    resultMsg = "5G slicing online users query result.";
                    resultHeader.setResult_code(NsmfCodeConstant.SUCCESS_CODE);
                } else {
                    logger.error(String
                        .format("querySlicingOnlineUserNumber: Can not get KpiUserNumber[code={}, message={}]",
                            response.code(),
                            response.message()));
                    resultMsg = "5G slicing online users query failed!";
                    resultHeader.setResult_code(NsmfCodeConstant.ERROR_CODE_UNKNOWN);
                }
            }
        } catch (Exception e) {
            resultMsg = "5G slicing online users query failed. Unknown exception occurred!";
            resultHeader.setResult_code(NsmfCodeConstant.ERROR_CODE_UNKNOWN);
            logger.error("Exception in querySlicingOnlineUserNumber :{}",e);
        }

        logger.info(resultMsg);
        logger.info("querySlicingOnlineUserNumber: 5G slicing online users query has been finished.");
        serviceOnlineUserList.setServiceOnlineUserInfoList(serviceOnlineUserInfoList);
        resultHeader.setResult_message(resultMsg);
        serviceResult.setResult_header(resultHeader);
        serviceResult.setResult_body(serviceOnlineUserList);
        return serviceResult;
    }

    @Override
    public ServiceResult querySlicingTotalBandwidth(String queryTimestamp, ServiceList serviceList) {
        ServiceResult serviceResult = new ServiceResult();
        ResultHeader resultHeader = new ResultHeader();
        initConfig();
        ServiceTotalBandwidthList serviceTotalBandwidthList = new ServiceTotalBandwidthList();

        List<ServiceTotalBandwidthInfo> serviceTotalBandwidthInfoList = new ArrayList<>();
        List<ServiceInfo> serviceInfoList = serviceList.getServiceInfoList();

        String resultMsg = "";

        try {
            for (ServiceInfo serviceInfo : serviceInfoList) {
                String newTimestamp = NsmfCommonUtil.time2Timestamp(queryTimestamp)
                    .replace(NsmfParamConstant.SPACE, "T");
                SlicingKpiReqInfo slicingKpiReqInfo = resourceMonitorServiceConvert
                    .buildSlicingKpiReqInfo(serviceInfo, newTimestamp, kpiHours);
                String jsonstr = JSON.toJSONString(slicingKpiReqInfo);
                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonstr.toString());
                Response<KpiTotalBandwidth> response = this.kpiSliceService.listTotalBandwidth(requestBody).execute();

                if (response.isSuccessful()) {
                    KpiTotalBandwidth kpiTotalBandwidth = response.body();
                    logger.info("querySlicingTotalBandwidth: listTotalBandwidth reponse is:{}",
                        gson.toJson(kpiTotalBandwidth).toString());
                    ServiceTotalBandwidthInfo serviceTotalBandwidthInfo = new ServiceTotalBandwidthInfo();
                    resourceMonitorServiceConvert
                        .convertServiceTotalBandwidthInfo(serviceTotalBandwidthInfo, kpiTotalBandwidth);
                    serviceTotalBandwidthInfoList.add(serviceTotalBandwidthInfo);
                    resultMsg = "5G slicing total bandwidth query result.";
                    resultHeader.setResult_code(NsmfCodeConstant.SUCCESS_CODE);
                } else {
                    logger.error(String
                        .format("querySlicingTotalBandwidth: Can not get KpiUserNumber[code={}, message={}]",
                            response.code(),
                            response.message()));
                    resultMsg = "5G slicing total bandwidth query failed!";
                    resultHeader.setResult_code(NsmfCodeConstant.ERROR_CODE_UNKNOWN);
                }
            }
        } catch (Exception e) {
            resultMsg = "5G slicing total bandwidth query failed. Unknown exception occurred!";
            resultHeader.setResult_code(NsmfCodeConstant.ERROR_CODE_UNKNOWN);
            logger.error("Exception in querySlicingTotalBandwidth :{}",e);
        }

        logger.info(resultMsg);
        logger.info("querySlicingTotalBandwidth: 5G slicing total bandwidth query has been finished.");
        serviceTotalBandwidthList.setServiceTotalBandwidthInfoList(serviceTotalBandwidthInfoList);
        resultHeader.setResult_message(resultMsg);
        serviceResult.setResult_header(resultHeader);
        serviceResult.setResult_body(serviceTotalBandwidthList);
        return serviceResult;
    }
}
