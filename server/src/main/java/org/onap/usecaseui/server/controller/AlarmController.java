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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.onap.usecaseui.server.bean.AlarmsHeader;
import org.onap.usecaseui.server.bean.AlarmsInformation;
import org.onap.usecaseui.server.bo.AlarmBo;
import org.onap.usecaseui.server.constant.Constant;
import org.onap.usecaseui.server.service.AlarmsHeaderService;
import org.onap.usecaseui.server.service.AlarmsInformationService;
import org.onap.usecaseui.server.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.bind.annotation.*;


import java.io.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.*;


@RestController
@org.springframework.context.annotation.Configuration
@EnableAspectJAutoProxy
public class AlarmController {


    private final Logger logger = LoggerFactory.getLogger(AlarmController.class);

    @Resource(name = "AlarmsHeaderService")
    private AlarmsHeaderService alarmsHeaderService;


    public void setAlarmsHeaderService(AlarmsHeaderService alarmsHeaderService) {
        this.alarmsHeaderService = alarmsHeaderService;
    }


    public void setAlarmsInformationService(AlarmsInformationService alarmsInformationService) {
        this.alarmsInformationService = alarmsInformationService;
    }

    @Resource(name = "AlarmsInformationService")
    private AlarmsInformationService alarmsInformationService;

    private ObjectMapper omAlarm = new ObjectMapper();

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    @RequestMapping(value = {"/alarm/{currentPage}/{pageSize}",
            "/alarm/{currentPage}/{pageSize}/{sourceId}/{sourceName}/{priority}/{startTime}/{endTime}/{vfStatus}"},
            method = RequestMethod.GET, produces = "application/json")
    public String getAlarmData(@PathVariable int currentPage, @PathVariable int pageSize,
                               @PathVariable(required = false) String sourceId, @PathVariable(required = false) String sourceName,
                               @PathVariable(required = false) String priority, @PathVariable(required = false) String startTime,
                               @PathVariable(required = false) String endTime, @PathVariable(required = false) String vfStatus
                               ) throws JsonProcessingException, ParseException {
        logger.info("transfer getAlarmData Apis, " +
                        "Parameter all follows : [currentPage : {} , pageSize : {} , sourceId : {} , " +
                        "sourceName : {} , priority : {} , startTime :{} , endTime : {}  , vfStatus : {}]"
                , currentPage, pageSize, sourceId, sourceName, priority, startTime, endTime, vfStatus);
        List<AlarmsHeader> alarmsHeaders = new ArrayList<AlarmsHeader>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<AlarmBo> list = new ArrayList<>();
        Page pa =null;
        if (null != sourceId || null != sourceName || null != priority || null != startTime || null != endTime
                || null != vfStatus) {
            AlarmsHeader alarm = new AlarmsHeader();
            alarm.setSourceId(!"null".equals(sourceId) ? sourceId : null);
            alarm.setSourceName(!"null".equals(sourceName) ? sourceName : null);
            alarm.setStatus(!"null".equals(vfStatus) ? vfStatus : null);
            alarm.setPriority(!"null".equals(priority) ? vfStatus : null);

            try {
                alarm.setCreateTime(!"null".equals(startTime) ? new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(startTime) : null);
                alarm.setUpdateTime(!"null".equals(endTime) ? new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(endTime) : null);
            } catch (ParseException e) {
                logger.error("Parse date error :" + e.getMessage());
            }
            pa = alarmsHeaderService.queryAlarmsHeader(alarm, currentPage, pageSize);

            if (null == pa) {
                AlarmsHeader alarm_s = new AlarmsHeader();
                //SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String  startTime_s="2017-10-31 09:52:15";
                String  endTime_s="2017-11-15 15:27:16";
                alarm_s.setSourceId("11694_113");
                alarm_s.setSourceName("11694_113");
                alarm_s.setPriority("High");
                alarm_s.setVfStatus("Medium");
                alarm_s.setCreateTime(formatter.parse(startTime_s));
                alarm_s.setUpdateTime(formatter.parse(endTime_s));
                alarm_s.setEventId("ab305d54-85b4-a31b-7db2-fb6b9e546015");
                alarm_s.setEventName("Fault_MultiCloud_VMFailureCleared");
                alarm_s.setId(5);
                alarm_s.setStatus("close");
                alarmsHeaders.add(alarm_s);
            }else {
                alarmsHeaders = pa.getList();
            }


                if (null != alarmsHeaders && alarmsHeaders.size() > 0) {

                    //if (null != pa) {
                    //alarmsHeaders = pa.getList();
                    //alarmsHeaders.forEach(a -> {
                    AlarmsHeader a;
                    for(int c=0;c<alarmsHeaders.size();c++){
                        a = alarmsHeaders.get(c);

                        logger.info(a.toString());
                        AlarmBo abo = new AlarmBo();
                        if (!a.getStatus().equals("active")) {
                            abo.setAlarmsHeader(a);
                            AlarmsInformation information = new AlarmsInformation();
                            information.setEventId(a.getSourceId());
                            List<AlarmsInformation> informationList=new ArrayList<AlarmsInformation>();
                           if("11694_113".equals(a.getSourceId())){
                               AlarmsInformation al = new AlarmsInformation();
                               al.setName("neType");
                               al.setValue("IMSSBC");
                               al.setEventId("11694_113");
                               al.setCreateTime(formatter.parse("2017-10-31 09:51:15"));
                               al.setUpdateTime(formatter.parse("2017-11-15 15:27:15"));
                               informationList.add(al);
                           }else {
                               informationList =  alarmsInformationService.queryAlarmsInformation(information, 1, 100).getList();

                           }
                            abo.setAlarmsInformation(informationList);
                            list.add(abo);
                        }
                    }
                   // });
                }

        } else {
            pa = alarmsHeaderService.queryAlarmsHeader(null, currentPage, pageSize);
            if (null == pa) {
                AlarmsHeader alarm_s = new AlarmsHeader();
                //SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String  startTime_s="2017-10-31 09:52:15";
                String  endTime_s="2017-11-15 15:27:16";
                alarm_s.setSourceId("11694_113");
                alarm_s.setSourceName("11694_113");
                alarm_s.setPriority("High");
                alarm_s.setVfStatus("Medium");
                alarm_s.setCreateTime(formatter.parse(startTime_s));
                alarm_s.setUpdateTime(formatter.parse(endTime_s));
                alarm_s.setEventId("ab305d54-85b4-a31b-7db2-fb6b9e546015");
                alarm_s.setEventName("Fault_MultiCloud_VMFailureCleared");
                alarm_s.setId(5);
                alarm_s.setStatus("close");
                alarmsHeaders.add(alarm_s);
            }else{
                alarmsHeaders = pa.getList();
            }

                if (null != alarmsHeaders && alarmsHeaders.size() > 0) {
                    alarmsHeaders.forEach(a -> {
                        AlarmBo abo = new AlarmBo();
                        if (!a.getStatus().equals("active")) {
                            abo.setAlarmsHeader(a);
                            abo.setAlarmsInformation(alarmsInformationService.queryAlarmsInformation(new AlarmsInformation(a.getEventId()), currentPage, pageSize).getList());
                            list.add(abo);
                        }

                    });
                }

        }
        Map<String, Object> map = new HashMap<>();
        map.put("alarms", list);
        map.put("totalRecords", pa==null?0:pa.getTotalRecords());
        omAlarm.setDateFormat(new SimpleDateFormat(Constant.DATE_FORMAT));
        return omAlarm.writeValueAsString(map);
    }


    @RequestMapping(value = "/alarm/statusCount", method = RequestMethod.GET, produces = "application/json")
    public String getStatusCount() throws JsonProcessingException {
        List<String> statusCount = new ArrayList<>();

            statusCount.add(alarmsHeaderService.queryStatusCount("0"));
            statusCount.add(alarmsHeaderService.queryStatusCount("active"));
            statusCount.add(alarmsHeaderService.queryStatusCount("close"));
            return omAlarm.writeValueAsString(statusCount);

    }

    @RequestMapping(value = {"/alarm/sourceId"}, method = RequestMethod.GET)
    public String getSourceId() throws JsonProcessingException {
        List<String> sourceIds = new ArrayList<>();
        Page page  = alarmsHeaderService.queryAlarmsHeader(new AlarmsHeader(), 1, Integer.MAX_VALUE);
        AlarmsHeader alarmsHeader;

        if(page==null){
            page = new Page();
            List list = new ArrayList();
            alarmsHeader = new AlarmsHeader();
            alarmsHeader.setId(1);
            alarmsHeader.setSourceId("aaaa");
            list.add(alarmsHeader);
            page.setPageNo(1);
            page.setPageSize(12);
            page.setTotalRecords(1);
            page.setList(list);

        }
        for(int a=0;a<page.getList().size();a++){

            alarmsHeader  = (AlarmsHeader)page.getList().get(a);
            String sourceid = alarmsHeader.getSourceId();
            if (!sourceIds.contains(sourceid)) {
                sourceIds.add(sourceid);
            }
        }
        return omAlarm.writeValueAsString(sourceIds);
    }

    @RequestMapping(value = {"/alarm/diagram"}, method = RequestMethod.POST)
    public String genDiagram(@RequestParam String sourceId, @RequestParam String startTime, @RequestParam String endTime, @RequestParam String showMode) throws JsonProcessingException {

            return omAlarm.writeValueAsString(diagramDate(sourceId, startTime, endTime, showMode));

    }

    private List<List<Long>> dateProcess(String sourceId, long startTimeL, long endTimeL, long timeIteraPlusVal, long keyVal, long keyValIteraVal, String keyUnit) throws ParseException {
        List<List<Long>> dataList = new ArrayList<>();
        long tmpEndTimeL = startTimeL + timeIteraPlusVal;
        while (endTimeL >= tmpEndTimeL) {
            List<Map<String, String>> maps = alarmsInformationService.queryDateBetween(sourceId, sdf.format(new Date(startTimeL)), sdf.format(new Date(tmpEndTimeL)));
            maps.forEach(map -> {
                try {
                    List<Long> longList = new ArrayList<>();
                    if (map.get("Time") != null && !"".equals(map.get("Time")) && !"NULL".equals(map.get("Time"))) {
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
            });
            startTimeL += timeIteraPlusVal;
            tmpEndTimeL += timeIteraPlusVal;
            keyVal += keyValIteraVal;
        }
        return dataList;
    }

    private List<List<Long>> diagramDate(String sourceId, String startTime, String endTime, String format) {
        try {
            long startTimel = sdf.parse(startTime).getTime();
            long endTimel = sdf.parse(endTime).getTime();
            if (format != null && !format.equals("auto")) {
                switch (format) {
                    case "minute":
                        return dateProcess(sourceId, startTimel, endTimel, 900000, 15, 15, "minute");
                    case "hour":
                        return dateProcess(sourceId, startTimel, endTimel, 3600000, 1, 1, "hour");
                    case "day":
                        return dateProcess(sourceId, startTimel, endTimel, 86400000, 1, 1, "day");
                    case "month":
                        return dateProcess(sourceId, startTimel, endTimel, 2592000000L, 1, 1, "month");
                    case "year":
                        return dateProcess(sourceId, startTimel, endTimel, 31536000000L, 1, 1, "year");
                }
            } else if (format != null && format.equals("auto")) {
                long minutes = (endTimel - startTimel) / (1000 * 60);
                long hours = minutes / 60;
                if (hours > 12) {
                    long days = hours / 24;
                    if (days > 3) {
                        long months = days / 31;
                        if (months > 2) {
                            return dateProcess(sourceId, startTimel, endTimel, 86400000, 1, 1, "day");
                        } else {
                            return dateProcess(sourceId, startTimel, endTimel, 2592000000L, 1, 1, "month");
                        }
                    } else {
                        return dateProcess(sourceId, startTimel, endTimel, 3600000, 1, 1, "hour");
                    }
                } else {
                    return dateProcess(sourceId, startTimel, endTimel, 900000, 15, 15, "minute");
                }
            }
        } catch (ParseException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
