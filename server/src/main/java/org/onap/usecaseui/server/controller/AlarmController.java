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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.onap.usecaseui.server.bean.AlarmsHeader;
import org.onap.usecaseui.server.bean.AlarmsInformation;
import org.onap.usecaseui.server.bo.AlarmBo;
import org.onap.usecaseui.server.constant.Constant;
import org.onap.usecaseui.server.service.AlarmsHeaderService;
import org.onap.usecaseui.server.service.AlarmsInformationService;
import org.onap.usecaseui.server.util.DateUtils;
import org.onap.usecaseui.server.util.Page;
import org.onap.usecaseui.server.util.UuiCommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


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
    
    public void setAlarmsHeaderService(AlarmsHeaderService alarmsHeaderService) {
        this.alarmsHeaderService = alarmsHeaderService;
    }


    public void setAlarmsInformationService(AlarmsInformationService alarmsInformationService) {
        this.alarmsInformationService = alarmsInformationService;
    }
    
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
    private String formatDate = "yyyy-MM-dd";
    
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

		
	/**
	 * test commit
	 */
    @RequestMapping(value = {"/alarm/{currentPage}/{pageSize}",
            "/alarm/{currentPage}/{pageSize}/{sourceName}/{priority}/{startTime}/{endTime}/{vfStatus}"},
            method = RequestMethod.GET , produces = "application/json")
    public String getAlarmData(@PathVariable(required = false) String sourceName,
                               @PathVariable(required = false) String priority,@PathVariable(required = false) String startTime,
                               @PathVariable(required = false) String endTime,@PathVariable(required = false) String vfStatus,
                               @PathVariable int currentPage, @PathVariable int pageSize) throws JsonProcessingException {
            AlarmsHeader alarm = new AlarmsHeader();
            alarm.setSourceName(!"null".equals(sourceName)?sourceName:null);
            alarm.setStatus(!"null".equals(vfStatus)?vfStatus:null);
            try {
                alarm.setStartEpochMicrosec(!"null".equals(startTime)?new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(startTime).getTime()+"":null);
                alarm.setLastEpochMicroSec(!"null".equals(endTime)?new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(endTime).getTime()+"":null);
            } catch (ParseException e) {
                logger.error("Parse date error :"+e.getMessage());
            }
            Page  pa = alarmsHeaderService.queryAlarmsHeader(alarm,currentPage,pageSize);
        try {
            Map<String,Object> map = new HashMap<>();
            map.put("alarms",pa.getList());
            map.put("totalRecords",pa.getTotalRecords());
            omAlarm.setDateFormat(new SimpleDateFormat(Constant.DATE_FORMAT));
            return omAlarm.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            logger.debug("JsonProcessingException :"+e.getMessage());
            return omAlarm.writeValueAsString("failed");
        }
    }

    @RequestMapping(value = {"/alarm/sourceId"},method = RequestMethod.GET)
    public String getSourceId() throws JsonProcessingException{
        List<String> sourceIds = new ArrayList<>();
       Page page  = alarmsHeaderService.queryAlarmsHeader(new AlarmsHeader(), 1, Integer.MAX_VALUE);
        AlarmsHeader alarmsHeader;
        if(page==null){
            page = new Page();
            List list = new ArrayList();
            alarmsHeader = new AlarmsHeader();
            list.add(alarmsHeader);
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
    
    @RequestMapping(value = {"/alarm/getSourceNames"},method = RequestMethod.GET,produces = "application/json")
    public String getSourceNames() throws JsonProcessingException{
       Set<String> sourceNames = new HashSet<>();
       Page<AlarmsHeader> page  = alarmsHeaderService.queryAlarmsHeader(new AlarmsHeader(), 1, Integer.MAX_VALUE);
        AlarmsHeader alarmsHeader;
        if(UuiCommonUtil.isNotNullOrEmpty(page)){
            page = new Page<>();
            List<AlarmsHeader> list = new ArrayList<>();
            alarmsHeader = new AlarmsHeader();
            list.add(alarmsHeader);
            page.setList(list);

        }
        for(int a=0;a<page.getList().size();a++){
            alarmsHeader  = (AlarmsHeader)page.getList().get(a);
            String sourceName = alarmsHeader.getSourceId();
            sourceNames.add(sourceName);
        }
        return omAlarm.writeValueAsString(sourceNames);
    }
    
    @RequestMapping(value = {"/alarm/diagram"},method = RequestMethod.POST,produces = "application/json")
    public String diagram(@RequestParam String sourceId, @RequestParam String startTime, @RequestParam String endTime, @RequestParam String format) {
        long timeInterval = 0;
    	try {
        	if("month".equals(format)){//alarm 时间级别  day month year
        		formatDate="yyyy-MM";
        		int maxDay= DateUtils.MonthOfDay(startTime, formatDate);
        		timeInterval =86400000L*maxDay;
        	}else if("hour".equals(format)){
        		formatDate="yyyy-MM-dd HH";
        		timeInterval = 3600000;
        	}else{
        		formatDate="yyyy-MM-dd";
        		timeInterval =86400000;
        	}
        	sdf = new SimpleDateFormat(formatDate);
            long startTimel = sdf.parse(startTime).getTime();
            long endTimel = sdf.parse(endTime).getTime();
            return getDiagram(sourceId, startTimel, endTimel+timeInterval, timeInterval, 1, 1,format);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    
    private  String getDiagram(String sourceId, long startTimeL, long endTimeL, long timeIteraPlusVal, long keyVal, long keyValIteraVal,String format) throws JsonProcessingException{
    	Map<String,List> result = new HashMap<String,List>();
    	
    	Map<String,List> allMaps = dateProcess(sourceId, startTimeL, endTimeL, timeIteraPlusVal, keyVal, keyValIteraVal,format,"");
    	Map<String,List> criticalMaps = dateProcess(sourceId, startTimeL, endTimeL, timeIteraPlusVal, 1, 1,format,"CRITICAL");
    	Map<String,List> majorMaps = dateProcess(sourceId, startTimeL, endTimeL, timeIteraPlusVal, 1, 1,format,"MAJOR");
    	Map<String,List> minorMaps = dateProcess(sourceId, startTimeL, endTimeL, timeIteraPlusVal, 1, 1,format,"MINOR");
    	Map<String,List> warningMaps = dateProcess(sourceId, startTimeL, endTimeL, timeIteraPlusVal, 1, 1,format,"WARNING");
    	Map<String,List> normalMaps = dateProcess(sourceId, startTimeL, endTimeL, timeIteraPlusVal, 1, 1,format,"NORMAL");
    	result.put("dateList", allMaps.get("dateTime"));
    	result.put("allList", allMaps.get("dataList"));
    	result.put("criticalList",criticalMaps.get("dataList"));
    	result.put("majorList",majorMaps.get("dataList"));
    	result.put("minorList",minorMaps.get("dataList"));
    	result.put("warningList",warningMaps.get("dataList"));
    	result.put("normalList",normalMaps.get("dataList"));
    	return omAlarm.writeValueAsString(result);
    }
    private Map<String,List> dateProcess(String sourceId, long startTimeL, long endTimeL, long timeIteraPlusVal, long keyVal, long keyValIteraVal,String format,String level) {
    	Map<String,List> result = new HashMap<String,List>();
        List<String> dateList = new ArrayList<String>();
        List<Integer> numList = new ArrayList<Integer>();
        long tmpEndTimeL = startTimeL + timeIteraPlusVal;
        while (endTimeL >= tmpEndTimeL) {
            int num = alarmsInformationService.queryDateBetween(sourceId,startTimeL+"",tmpEndTimeL+"",level);
            dateList.add(DateUtils.getResultDate(startTimeL, format));
            int maxDay2 = DateUtils.MonthOfDay(sdf.format(new Date(tmpEndTimeL)), formatDate);
            int maxDay = DateUtils.MonthOfDay(sdf.format(new Date(startTimeL)), formatDate);
            numList.add(num);
            startTimeL += 86400000L*maxDay;
            tmpEndTimeL += 86400000L*maxDay2;
            keyVal += keyValIteraVal;
        }
        result.put("dateTime", dateList);
        result.put("dataList", numList);
        return result;
    }
    
    @RequestMapping(value = "/alarm/statusCount", method = RequestMethod.GET, produces = "application/json")
    public String getStatusCount() throws JsonProcessingException {
        List<String> statusCount = new ArrayList<>();

            statusCount.add(alarmsHeaderService.queryStatusCount("active"));
            statusCount.add(alarmsHeaderService.queryStatusCount("close"));
            return omAlarm.writeValueAsString(statusCount);
    }
    
    @RequestMapping("/alarm/getAlarmsHeaderDetail/{id}")
    public String getAlarmsHeaderDetail(@PathVariable String id) throws JsonProcessingException {
        AlarmsHeader alarmsHeader= alarmsHeaderService.getAlarmsHeaderById(id);
        List<AlarmsInformation> list =new ArrayList<>();
        if(UuiCommonUtil.isNotNullOrEmpty(alarmsHeader)){
        	String headerId = alarmsHeader.getId();
        	list = alarmsInformationService.getAllAlarmsInformationByHeaderId(headerId);
        }

        Map map = new HashMap();
        map.put("alarmsHeader",alarmsHeader);
        map.put("list",list);

        String string =omAlarm.writeValueAsString(map);
        return string;
    }
}
