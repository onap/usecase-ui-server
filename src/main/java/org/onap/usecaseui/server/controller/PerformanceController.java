/*
 * Copyright (C) 2017 CMCC, Inc. and others. All rights reserved.
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
package org.onap.usecaseui.server.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.onap.usecaseui.server.bean.PerformanceHeader;
import org.onap.usecaseui.server.bean.PerformanceInformation;
import org.onap.usecaseui.server.service.PerformanceHeaderService;
import org.onap.usecaseui.server.service.PerformanceInformationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.xml.crypto.Data;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/*
 * Copyright (C) 2017 CMCC, Inc. and others. All rights reserved.
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
@Controller
@Configuration
@EnableAspectJAutoProxy
public class PerformanceController {

    @Resource(name = "PerformanceHeaderService")
    private PerformanceHeaderService performanceHeaderService;

    @Resource(name = "PerformanceInformationService")
    private PerformanceInformationService performanceInformationService;

    private final String csvPath = "";

    private Logger looger = LoggerFactory.getLogger(PerformanceController.class);

    @ResponseBody
    @RequestMapping(value = {"/pro/getData"},method = RequestMethod.GET, produces = "application/json")
    public String getPerformanceData(HttpServletRequest request) throws JsonProcessingException {
        String eventId = request.getParameter("PERFORMANCE_eventId");
        String eventName = request.getParameter("PERFORMANCE_eventName");
        String name = request.getParameter("PERFORMANCE_name");
        String value = request.getParameter("PERFORMANCE_value");
        int currentPage = Integer.parseInt(request.getParameter("PERFORMANCE_currentPage"));
        int pageSize = Integer.parseInt(request.getParameter("PERFORMANCE_pageSize"));
        PerformanceHeader performanceHeader = new PerformanceHeader();
        PerformanceInformation performanceInformation = new PerformanceInformation();
        if (null != eventId){
            performanceHeader.setEventId(eventId);
            performanceInformation.setEventId(eventId);
        }
        if (null != eventName)
            performanceHeader.setEventName(eventName);
        if (null != name)
            performanceInformation.setName(name);
        if (null != value)
            performanceInformation.setValue(value);
        List<PerformanceHeader> performanceHeaders = performanceHeaderService.queryPerformanceHeader(performanceHeader,currentPage,pageSize).getList();
        List<PerformanceInformation> performanceInformations = performanceInformationService.queryPerformanceInformation(performanceInformation,currentPage,pageSize).getList();
        Map<String,Object> maps = new HashMap<>();
        if (null != performanceHeaders && performanceHeaders.size() > 0)
            maps.put("performance_header",performanceHeaders);
        if (null != performanceInformations && performanceInformations.size() > 0)
            maps.put("performance_information",performanceInformations);
        return new ObjectMapper().writeValueAsString(maps);
    }

    @RequestMapping(value = {"/pro/genCsv"}, method = RequestMethod.GET, produces = "application/json")
    public String generateCsvFile(HttpServletRequest request){
        String[] headers = new String[]{"version",
                "eventName","domain","eventId","eventType","nfcNamingCode",
                "nfNamingCode","sourceId","sourceName","reportingEntityId",
                "reportingEntityName","priority","startEpochMicrosec","lastEpochMicroSec",
                "sequence","measurementsForVfScalingVersion","measurementInterval","value","name",
                "createTime","updateTime"};
        List<PerformanceHeader> performanceHeaders = performanceHeaderService.queryPerformanceHeader(null,1,100).getList();
        List<PerformanceInformation> performanceInformations = performanceInformationService.queryPerformanceInformation(null,1,100).getList();
        List<String[]> csvData = new ArrayList<>();

        return "";
    }

    @RequestMapping(value = {"/pro/genDiaCsv"}, method = RequestMethod.GET, produces = "application/json")
    public String generateDiaCsvFile(HttpServletRequest request){
        String[] headers = new String[]{"","","",""};
        List<PerformanceHeader> performanceHeaders = performanceHeaderService.queryPerformanceHeader(null,1,100).getList();
        List<PerformanceInformation> performanceInformations = performanceInformationService.queryPerformanceInformation(null,1,100).getList();
        List<String[]> csvData = new ArrayList<>();

        return "";
    }

    @ResponseBody
    @RequestMapping(value = {"/pro/genDia"}, method = RequestMethod.GET, produces = "application/json")
    public String generateDiagram(HttpServletRequest request) throws ParseException, JsonProcessingException {
        String id = request.getParameter("PERFORMANCE_id");
        String data = request.getParameter("PERFORMANCE_data");
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
        Date beginDate = new Date();
        Calendar date = Calendar.getInstance();
        date.setTime(beginDate);
        PerformanceInformation p = new PerformanceInformation();
        p.setEventId(id);
        if ("hour".equals(data)){
            date.set(Calendar.DATE, date.get(Calendar.HOUR) - 1);
            Date endDate = dft.parse(dft.format(date.getTime()));
            p.setCreateTime(endDate);
        }
        if ("day".equals(data)){
            date.set(Calendar.DATE, date.get(Calendar.DATE) - 1);
            Date endDate = dft.parse(dft.format(date.getTime()));
            p.setCreateTime(endDate);
        }
        if ("month".equals(data)){
            date.set(Calendar.DATE, date.get(Calendar.MONTH) - 1);
            Date endDate = dft.parse(dft.format(date.getTime()));
            p.setCreateTime(endDate);
        }
        if ("year".equals(data)){
            date.set(Calendar.DATE, date.get(Calendar.YEAR) - 1);
            Date endDate = dft.parse(dft.format(date.getTime()));
            p.setCreateTime(endDate);
        }
        List<PerformanceInformation> informationList = performanceInformationService.queryPerformanceInformation(p,1,4).getList();
        return new ObjectMapper().writeValueAsString(informationList);
    }


}
