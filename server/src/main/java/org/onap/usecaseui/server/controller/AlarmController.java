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
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.onap.usecaseui.server.bean.AlarmsHeader;
import org.onap.usecaseui.server.bean.AlarmsInformation;
import org.onap.usecaseui.server.bo.AlarmBo;
import org.onap.usecaseui.server.constant.Constant;
import org.onap.usecaseui.server.service.AlarmsHeaderService;
import org.onap.usecaseui.server.service.AlarmsInformationService;
import org.onap.usecaseui.server.util.CSVUtils;
import org.onap.usecaseui.server.util.DateUtils;
import org.onap.usecaseui.server.util.Page;
import org.onap.usecaseui.server.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@RestController
@org.springframework.context.annotation.Configuration
@EnableAspectJAutoProxy
public class AlarmController
{


    private final Logger logger = LoggerFactory.getLogger(AlarmController.class);

    @Resource(name = "AlarmsHeaderService")
    private AlarmsHeaderService alarmsHeaderService;

    @Resource(name = "AlarmsInformationService")
    private AlarmsInformationService alarmsInformationService;

    private  String[] AlarmCSVHeaders = new String[]{"version",
            "eventName","domain","eventId","eventType","nfcNamingCode",
            "nfNamingCode","sourceId","sourceName","reportingEntityId",
            "reportingEntityName","priority","startEpochMicrosec","lastEpochMicroSec",
            "sequence","faultFieldsVersion","eventServrity","eventSourceType",
            "eventCategory","alarmCondition","specificProblem","vfStatus",
            "alarmInterfaceA","status",
            "createTime","updateTime","name","value"};

    private ObjectMapper omAlarm = new ObjectMapper();


    @RequestMapping(value = {"/usecase-ui"}, method = RequestMethod.GET)
    public ModelAndView index(){
        return new ModelAndView("index");
    }


    @RequestMapping(value = {"/alarm/{currentPage}/{pageSize}",
            "/alarm/{currentPage}/{pageSize}/{eventId}/{eventName}/{name}/{value}/{createTime}/{status}/{vfStatus}"},
            method = RequestMethod.GET , produces = "application/json")
    public String getAlarmData(@PathVariable(required = false) String eventId,@PathVariable(required = false) String eventName,
                               @PathVariable(required = false) String name,@PathVariable(required = false) String value,
                               @PathVariable(required = false) String createTime,@PathVariable(required = false) String status,
                               @PathVariable(required = false) String vfStatus,
                               @PathVariable int currentPage, @PathVariable int pageSize) throws JsonProcessingException {
        logger.info("transfer getAlarmData Apis, " +
                "Parameter all follows : [currentPage : {} , pageSize : {} , eventId : {} , " +
                "eventName : {} , name : {} , value :{} , createTime : {} , status : {} , vfStatus : {}]"
                ,currentPage,pageSize,eventId,eventName,name,value,createTime,status,vfStatus);
        List<AlarmsHeader> alarmsHeaders = null;
        List<AlarmBo> list = new ArrayList<>();
        Page pa = null;
        if (null != eventId || null != eventName || null != name || null != value || null != createTime
                || null != status || null != vfStatus  ){
            AlarmsHeader alarm = new AlarmsHeader();
            alarm.setEventId(!"null".equals(eventId)?eventId:null);
            alarm.setEventName(!"null".equals(eventName)?eventName:null);
            alarm.setStatus(!"null".equals(status)?status:null);
            alarm.setVfStatus(!"null".equals(vfStatus)?vfStatus:null);
            try {
                alarm.setCreateTime(!"null".equals(createTime)?DateUtils.stringToDate(createTime):null);
            } catch (ParseException e) {
                logger.error("Parse date error :"+e.getMessage());
            }
            pa = alarmsHeaderService.queryAlarmsHeader(alarm,currentPage,pageSize);
            alarmsHeaders = pa.getList();
            if (null != alarmsHeaders && alarmsHeaders.size() > 0) {
                alarmsHeaders.forEach(a ->{
                    AlarmBo abo = new AlarmBo();
                    abo.setAlarmsHeader(a);
                    AlarmsInformation information = new AlarmsInformation();
                    information.setName(!"null".equals(name)?name:null);
                    information.setValue(!"null".equals(value)?value:null);
                    information.setEventId(a.getEventId());
                    abo.setAlarmsInformation(alarmsInformationService.queryAlarmsInformation(information,1,100).getList());
                    list.add(abo);
                });
            }
        }else {
            pa = alarmsHeaderService.queryAlarmsHeader(null, currentPage, pageSize);
            alarmsHeaders = pa.getList();
            if (null != alarmsHeaders && alarmsHeaders.size() > 0) {
                alarmsHeaders.forEach(a -> {
                    AlarmBo abo = new AlarmBo();
                    abo.setAlarmsHeader(a);
                    abo.setAlarmsInformation(alarmsInformationService.queryAlarmsInformation(new AlarmsInformation(a.getEventId()),currentPage,pageSize).getList());
                    list.add(abo);
                });
            }
        }
        try {
            Map<String,Object> map = new HashMap<>();
            map.put("alarms",list);
            map.put("totalRecords",pa.getTotalRecords());
            omAlarm.setDateFormat(new SimpleDateFormat(Constant.DATE_FORMAT));
            return omAlarm.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            logger.debug("JsonProcessingException :"+e.getMessage());
            return omAlarm.writeValueAsString("failed");
        }
    }

    @RequestMapping(value = { "/alarm/genCsv/{eventId}" } , method = RequestMethod.GET , produces = "application/json")
    public String generateCsvFile(HttpServletResponse response, @PathVariable String[] eventId) throws JsonProcessingException {
        logger.info("transfer generateCsvFile Apis, " +
                        "Parameter all follows : [eventId : {}]",eventId);
        String csvFile = "csvFiles/vnf_alarm_"+new SimpleDateFormat("yy-MM-ddHH:mm:ss").format(new Date())+".csv";
        List<AlarmsHeader> alarmsHeaders = alarmsHeaderService.queryId(eventId);
        List<String[]> csvData = new ArrayList<>();
        try{
            alarmsHeaders.forEach(ala ->{
                List<AlarmsInformation> information = alarmsInformationService.queryAlarmsInformation(new AlarmsInformation(ala.getEventId()),1,100).getList();
                String names = "";
                String values = "";
                if (0 < information.size() && null != information){
                    for (AlarmsInformation a : information){
                        names += a.getName()+",";
                        values += a.getValue()+",";
                    }
                    names = names.substring(0,names.lastIndexOf(','));
                    values = values.substring(0,values.lastIndexOf(','));
                }
                csvData.add(new String[]{
                        ala.getVersion(),ala.getEventName(),ala.getDomain(),ala.getEventId(),ala.getEventType(),
                        ala.getNfcNamingCode(),ala.getNfNamingCode(),ala.getSourceId(),ala.getSourceName(),
                        ala.getReportingEntityId(),ala.getReportingEntityName(),ala.getPriority(),ala.getStartEpochMicrosec(),
                        ala.getLastEpochMicroSec(),ala.getSequence(),ala.getFaultFieldsVersion(),ala.getEventServrity(),
                        ala.getEventSourceType(),ala.getEventCategory(),ala.getAlarmCondition(),ala.getSpecificProblem(),
                        ala.getVfStatus(),ala.getAlarmInterfaceA(),ala.getStatus(),DateUtils.dateToString(ala.getCreateTime()),
                        DateUtils.dateToString(ala.getUpdateTime()),names,values
                });
            });
            CSVUtils.writeCsv(AlarmCSVHeaders,csvData,csvFile);
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        if (ResponseUtil.responseDownload(csvFile,response)){
            return omAlarm.writeValueAsString("success");
        }else{
            return omAlarm.writeValueAsString("failed");
        }
    }

}
