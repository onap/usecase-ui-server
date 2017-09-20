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

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.onap.usecaseui.server.bean.AlarmsHeader;
import org.onap.usecaseui.server.bean.AlarmsInformation;
import org.onap.usecaseui.server.service.AlarmsHeaderService;
import org.onap.usecaseui.server.service.AlarmsInformationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
@org.springframework.context.annotation.Configuration
@EnableAspectJAutoProxy
public class AlarmController
{


    private static final Logger logger = LoggerFactory.getLogger(AlarmController.class);

    @Resource(name = "AlarmsHeaderService")
    private AlarmsHeaderService alarmsHeaderService;

    @Resource(name = "AlarmsInformationService")
    private AlarmsInformationService alarmsInformationService;


    @ResponseBody
    @RequestMapping(value = {"/alarm/getData"}, method = RequestMethod.GET , produces = "application/json")
    public String getAlarmData(HttpServletRequest request) throws JsonProcessingException {
        String eventId = request.getParameter("ALARM_eventId");
        String vfStatus = request.getParameter("ALARM_vfStatus");
        String status = request.getParameter("ALARM_status");
        String eventName = request.getParameter("ALARM_eventName");
        String name = request.getParameter("ALARM_name");
        String value = request.getParameter("ALARM_value");
        int currentPage = Integer.parseInt(request.getParameter("ALARM_currentPage"));
        int pageSize = Integer.parseInt(request.getParameter("ALARM_pageSize"));
        AlarmsHeader alarmsHeader = new AlarmsHeader();
        AlarmsInformation alarmsInformation = new AlarmsInformation();
        if (null != eventId){
            alarmsHeader.setEventId(eventId);
            alarmsInformation.setEventId(eventId);
        }
        if (null != vfStatus)
            alarmsHeader.setVfStatus(vfStatus);
        if (null != status)
            alarmsHeader.setStatus(status);
        if (null != eventName)
            alarmsHeader.setEventName(eventName);
        if (null != name)
            alarmsInformation.setName(name);
        if (null != value)
            alarmsInformation.setValue(value);
        List<AlarmsHeader> alarmsHeaders = alarmsHeaderService.queryAlarmsHeader(alarmsHeader,currentPage,pageSize).getList();
        List<AlarmsInformation> alarmsInformations = alarmsInformationService.queryAlarmsInformation(alarmsInformation,currentPage,pageSize).getList();
        Map<String,Object> maps = new HashMap<>();
        if (null != alarmsHeaders && alarmsHeaders.size() > 0)
            maps.put("alarms_header",alarmsHeaders);
        if (null != alarmsInformations && alarmsInformations.size() > 0)
            maps.put("alarms_information",alarmsInformations);
        return new ObjectMapper().writeValueAsString(maps);
    }

    @RequestMapping(value = { "/alarm/genCsv" } , method = RequestMethod.GET , produces = "application/json")
    public String generateCsvFile(HttpServletRequest request){
        String[] headers = new String[]{"version",
                "eventName","domain","eventId","eventType","nfcNamingCode",
                "nfNamingCode","sourceId","sourceName","reportingEntityId",
                "reportingEntityName","priority","startEpochMicrosec","lastEpochMicroSec",
                "sequence","measurementsForVfScalingVersion","measurementInterval","value","name",
                "createTime","updateTime"};
        String event_ids = request.getParameter("ids");
        String[] eventId = event_ids.split(",");

        return "";
    }


    @RequestMapping(value = { "/alarm/updateStatus" } , method = RequestMethod.GET , produces = "application/json")
    public String updateStatus(HttpServletRequest request){
        String id = request.getParameter("ALARM_eventId");
        String vfStatus = request.getParameter("ALARM_vfstatus");
        String status = request.getParameter("ALARM_status");
        AlarmsHeader alarmsHeader = new AlarmsHeader();
        if (null != id)
            alarmsHeader.setEventId(id);
        if (null != vfStatus)
            alarmsHeader.setVfStatus(vfStatus);
        if (null != status)
            alarmsHeader.setStatus(status);
        String result = alarmsHeaderService.updateAlarmsHeader(alarmsHeader);
        return result;
    }




}
