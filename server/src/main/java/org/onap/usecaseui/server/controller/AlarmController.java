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
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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

    private ObjectMapper omAlarm = new ObjectMapper();

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    @RequestMapping(value = {"/alarm/{currentPage}/{pageSize}",
            "/alarm/{currentPage}/{pageSize}/{sourceId}/{sourceName}/{priority}/{startTime}/{endTime}/{vfStatus}"},
            method = RequestMethod.GET , produces = "application/json")
    public String getAlarmData(@PathVariable(required = false) String sourceId,@PathVariable(required = false) String sourceName,
                               @PathVariable(required = false) String priority,@PathVariable(required = false) String startTime,
                               @PathVariable(required = false) String endTime,@PathVariable(required = false) String vfStatus,
                               @PathVariable int currentPage, @PathVariable int pageSize) throws JsonProcessingException {
        logger.info("transfer getAlarmData Apis, " +
                "Parameter all follows : [currentPage : {} , pageSize : {} , sourceId : {} , " +
                "sourceName : {} , priority : {} , startTime :{} , endTime : {}  , vfStatus : {}]"
                ,currentPage,pageSize,sourceId,sourceName,priority,startTime,endTime,vfStatus);
        List<AlarmsHeader> alarmsHeaders = null;
        List<AlarmBo> list = new ArrayList<>();
        Page pa = null;
        if (null != sourceId || null != sourceName || null != priority || null != startTime || null != endTime
                || null != vfStatus  ){
            AlarmsHeader alarm = new AlarmsHeader();
            alarm.setSourceId(!"null".equals(sourceId)?sourceId:null);
            alarm.setSourceName(!"null".equals(sourceName)?sourceName:null);
            alarm.setStatus(!"null".equals(vfStatus)?vfStatus:null);
            try {
                alarm.setCreateTime(!"null".equals(startTime)?new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(startTime):null);
                alarm.setUpdateTime(!"null".equals(endTime)?new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(endTime):null);
            } catch (ParseException e) {
                logger.error("Parse date error :"+e.getMessage());
            }
            pa = alarmsHeaderService.queryAlarmsHeader(alarm,currentPage,pageSize);

            alarmsHeaders = pa.getList();
            if (null != alarmsHeaders && alarmsHeaders.size() > 0) {
                alarmsHeaders.forEach(a ->{
                    logger.info(a.toString());
                    AlarmBo abo = new AlarmBo();
                    if (!a.getStatus().equals("3")){
                        abo.setAlarmsHeader(a);
                        AlarmsInformation information = new AlarmsInformation();
                        information.setEventId(a.getSourceId());
                        List<AlarmsInformation> informationList = alarmsInformationService.queryAlarmsInformation(information,1,100).getList();
                        abo.setAlarmsInformation(informationList);
                        list.add(abo);
                    }
                });
            }
        }else {
            pa = alarmsHeaderService.queryAlarmsHeader(null, currentPage, pageSize);
            alarmsHeaders = pa.getList();
            if (null != alarmsHeaders && alarmsHeaders.size() > 0) {
                alarmsHeaders.forEach(a -> {
                    AlarmBo abo = new AlarmBo();
                    if (!a.getStatus().equals("3")){
                        abo.setAlarmsHeader(a);
                        abo.setAlarmsInformation(alarmsInformationService.queryAlarmsInformation(new AlarmsInformation(a.getEventId()),currentPage,pageSize).getList());
                        list.add(abo);
                    }

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

    @RequestMapping(value = "/alarm/statusCount",method = RequestMethod.GET , produces = "application/json")
    public String getStatusCount(){
        List<String> statusCount = new ArrayList<>();
        try {
            statusCount.add(alarmsHeaderService.queryStatusCount("0"));
            statusCount.add(alarmsHeaderService.queryStatusCount("1"));
            statusCount.add(alarmsHeaderService.queryStatusCount("2"));
            return omAlarm.writeValueAsString(statusCount);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return null;
    }

    @RequestMapping(value = {"/alarm/sourceId"},method = RequestMethod.GET)
    public String getSourceId(){
        List<String> sourceIds = new ArrayList<>();
        alarmsHeaderService.queryAlarmsHeader(new AlarmsHeader(),1,Integer.MAX_VALUE).getList().forEach( al ->{
            if (!al.getStatus().equals("3") &&
                    !sourceIds.contains(al.getSourceId()))
                sourceIds.add(al.getSourceId());
        } );
        try {
            return omAlarm.writeValueAsString(sourceIds);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }
    }

    @RequestMapping(value = {"/alarm/diagram"},method = RequestMethod.POST)
    public String genDiagram(@RequestParam String sourceId,@RequestParam String startTime,@RequestParam String endTime,@RequestParam String showMode){
        try {
            return omAlarm.writeValueAsString(diagramDate(sourceId,startTime,endTime,showMode));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }
    }

    private List<List<Long>> dateProcess(String sourceId,long startTimeL,long endTimeL,long timeIteraPlusVal,long keyVal,long keyValIteraVal,String keyUnit) throws ParseException {
        List<List<Long>> dataList = new ArrayList<>();
        long tmpEndTimeL = startTimeL + timeIteraPlusVal;
        while (endTimeL >= tmpEndTimeL){
            List<Map<String,String>> maps = alarmsInformationService.queryDateBetween(sourceId,sdf.format(new Date(startTimeL)),sdf.format(new Date(tmpEndTimeL)));
            maps.forEach( map -> {
                try {
                    List<Long> longList = new ArrayList<>();
                    if (map.get("Time") != null && !"".equals(map.get("Time")) && !"NULL".equals(map.get("Time")) ){
                        longList.add(sdf.parse(map.get("Time")).getTime());
                        if (map.get("Count") != null && !"".equals(map.get("Count")))
                            longList.add(Long.parseLong(map.get("Count")));
                        else
                            longList.add(0L);
                    }
                    if (longList.size() > 0)
                        dataList.add(longList);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            } );
            startTimeL += timeIteraPlusVal;
            tmpEndTimeL += timeIteraPlusVal;
            keyVal += keyValIteraVal;
        }
        return dataList;
    }

    private List<List<Long>> diagramDate(String sourceId,String startTime,String endTime,String format){
        try {
            long startTimel = sdf.parse(startTime).getTime();
            long endTimel = sdf.parse(endTime).getTime();
            if (format != null && !format.equals("auto")){
                switch (format){
                    case "minute":
                        return dateProcess(sourceId,startTimel,endTimel,900000,15,15,"minute");
                    case "hour":
                        return dateProcess(sourceId,startTimel,endTimel,3600000,1,1,"hour");
                    case "day":
                        return dateProcess(sourceId,startTimel,endTimel,86400000,1,1,"day");
                    case "month":
                        return dateProcess(sourceId,startTimel,endTimel,2592000000L,1,1,"month");
                    case "year":
                        return dateProcess(sourceId,startTimel,endTimel,31536000000L,1,1,"year");
                }
            }else if (format != null && format.equals("auto")){
                long minutes = (endTimel - startTimel) / (1000 * 60);
                long hours = minutes / 60;
                if (hours > 12){
                    long days = hours / 24;
                    if (days > 3){
                        long months = days / 31;
                        if (months > 2){
                            return dateProcess(sourceId,startTimel,endTimel,86400000,1,1,"day");
                        }else {
                            return dateProcess(sourceId,startTimel,endTimel,2592000000L,1,1,"month");
                        }
                    }else {
                        return dateProcess(sourceId,startTimel,endTimel,3600000,1,1,"hour");
                    }
                }else {
                    return dateProcess(sourceId,startTimel,endTimel,900000,15,15,"minute");
                }
            }
        } catch (ParseException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

}
