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

import com.google.common.base.Throwables;
import org.onap.usecaseui.server.bean.AlarmsHeader;
import org.onap.usecaseui.server.bean.AlarmsInformation;
import org.onap.usecaseui.server.bean.SortMaster;
import org.onap.usecaseui.server.constant.CommonConstant;
import org.onap.usecaseui.server.service.AlarmsHeaderService;
import org.onap.usecaseui.server.service.AlarmsInformationService;
import org.onap.usecaseui.server.util.DateProcessDataObject;
import org.onap.usecaseui.server.util.DateUtils;
import org.onap.usecaseui.server.util.Page;
import org.onap.usecaseui.server.util.UuiCommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
	 * @throws ParseException 
	 */
    @GetMapping(value = {"/alarm/{currentPage}/{pageSize}"},
            produces = "application/json")
    public String getAlarmData(@RequestParam(required = false) String sourceName,
            @RequestParam(required = false) String priority, @RequestParam(required = false) String startTime,
            @RequestParam(required = false) String endTime, @RequestParam(required = false) String vfStatus,
            @PathVariable String currentPage, @PathVariable String pageSize)
            throws JsonProcessingException, ParseException {
        AlarmsHeader alarm = new AlarmsHeader();
        alarm.setSourceName(sourceName);
        alarm.setStatus(vfStatus);
        alarm.setStartEpochMicrosec(!UuiCommonUtil.isNotNullOrEmpty(startTime) ? null
                : new SimpleDateFormat(CommonConstant.DATE_FORMAT).parse(startTime).getTime() + "");
        alarm.setLastEpochMicroSec(!UuiCommonUtil.isNotNullOrEmpty(endTime) ? null
                : new SimpleDateFormat(CommonConstant.DATE_FORMAT).parse(endTime).getTime() + "");
        Page pa =
                alarmsHeaderService.queryAlarmsHeader(alarm, Integer.parseInt(currentPage), Integer.parseInt(pageSize));
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("alarms", pa.getList());
            map.put("totalRecords", pa.getTotalRecords());
            omAlarm.setDateFormat(new SimpleDateFormat(CommonConstant.DATE_FORMAT));
            return omAlarm.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            logger.debug("JsonProcessingException :" + e.getMessage());
            return omAlarm.writeValueAsString("failed");
        }
    }

    @GetMapping(value = {"/alarm/sourceId"})
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
    
    @GetMapping(value = {"/alarm/getSourceNames"},produces = "application/json")
    public String getSourceNames() throws JsonProcessingException{
       Set<String> sourceNames = new HashSet<>();
       Page<AlarmsHeader> page  = alarmsHeaderService.queryAlarmsHeader(new AlarmsHeader(), 1, Integer.MAX_VALUE);
        AlarmsHeader alarmsHeader;
        if(!UuiCommonUtil.isNotNullOrEmpty(page)){
            page = new Page<>();
            List<AlarmsHeader> list = new ArrayList<>();
            alarmsHeader = new AlarmsHeader();
            list.add(alarmsHeader);
            page.setList(list);

        }
        for(int a=0;a<page.getList().size();a++){
            alarmsHeader  = (AlarmsHeader)page.getList().get(a);
            String sourceName = alarmsHeader.getSourceName();
            sourceNames.add(sourceName);
        }
        return omAlarm.writeValueAsString(sourceNames);
    }
    
    @GetMapping(value = {"/alarm/diagram"},produces = "application/json")
    public String diagram(@RequestParam String sourceName, @RequestParam(required = false) String startTime,
            @RequestParam(required = false) String endTime, @RequestParam String format) {
        long timeInterval = 0;
        try {
            if ("month".equals(format)) {// alarm day month year
                formatDate = "yyyy-MM";
                int maxDay = DateUtils.MonthOfDay(startTime, formatDate);
                timeInterval = 86400000L * maxDay;
            } else {
                formatDate = "yyyy-MM-dd";
                timeInterval = 86400000;
            }
            sdf = new SimpleDateFormat(formatDate);
            long startTimel = sdf.parse(startTime).getTime();
            long endTimel = sdf.parse(endTime).getTime();
            return getDiagram(sourceName, startTimel, endTimel + timeInterval, timeInterval, 1, 1, format);
        } catch (Exception e) {
            logger.error("alarmController diagram occured exception:" + e.getMessage());
            logger.error("alarmController diagram occured exception:"+ Throwables.getStackTraceAsString(e));
        }
        return null;
    }
    
    @SuppressWarnings("rawtypes")
    private String getDiagram(String sourceName, long startTimeL, long endTimeL, long timeIteraPlusVal, long keyVal,
            long keyValIteraVal, String format) throws JsonProcessingException {
        Map<String, List> result = new HashMap<String, List>();

        Map<String, List> allMaps = dateProcess(new DateProcessDataObject().setSourceName(sourceName)
                .setStartTimeL(startTimeL).setEndTimeL(endTimeL).setTimeIteraPlusVal(timeIteraPlusVal).setKeyVal(keyVal)
                .setKeyValIteraVal(keyValIteraVal).setFormat(format).setLevel(""));

        Map<String, List> closedMaps = dateProcess(new DateProcessDataObject().setSourceName(sourceName)
                .setStartTimeL(startTimeL).setEndTimeL(endTimeL).setTimeIteraPlusVal(timeIteraPlusVal).setKeyVal(1)
                .setKeyValIteraVal(1).setFormat(format).setLevel("closed"));

        Map<String, List> activeMaps = dateProcess(new DateProcessDataObject().setSourceName(sourceName)
                .setStartTimeL(startTimeL).setEndTimeL(endTimeL).setTimeIteraPlusVal(timeIteraPlusVal).setKeyVal(1)
                .setKeyValIteraVal(1).setFormat(format).setLevel("active"));

        result.put("dateList", allMaps.get("dateTime"));
        result.put("allList", allMaps.get("dataList"));
        result.put("closedList", closedMaps.get("dataList"));
        result.put("ActiveList", activeMaps.get("dataList"));
        return omAlarm.writeValueAsString(result);
    }

    private Map<String, List> dateProcess(DateProcessDataObject dataObject) {
        Map<String, List> result = new HashMap<String, List>();
        List<String> dateList = new ArrayList<String>();
        List<Integer> numList = new ArrayList<Integer>();
        long tmpEndTimeL = dataObject.getStartTimeL() + dataObject.getTimeIteraPlusVal();
        while (dataObject.getEndTimeL() >= tmpEndTimeL) {
            int maxDay2 = 1;
            int maxDay = 1;
            int num = alarmsInformationService.queryDateBetween(dataObject.getSourceName(),
                    dataObject.getStartTimeL() + "", tmpEndTimeL + "", dataObject.getLevel());
            dateList.add(DateUtils.getResultDate(dataObject.getStartTimeL(), dataObject.getFormat()));
            if ("month".equals(dataObject.getFormat())) {
                maxDay2 = DateUtils.MonthOfDay(sdf.format(new Date(tmpEndTimeL)), formatDate);
                maxDay = DateUtils.MonthOfDay(sdf.format(new Date(dataObject.getStartTimeL())), formatDate);
            }
            numList.add(num);
            long startTimeL1 = dataObject.getStartTimeL();
            startTimeL1 += 86400000L * maxDay;
            tmpEndTimeL += 86400000L * maxDay2;
            long keyVal1 = dataObject.getKeyVal();
            long keyValIteraVal1 = dataObject.getKeyValIteraVal();
            keyVal1 += keyValIteraVal1;
        }
        result.put("dateTime", dateList);
        result.put("dataList", numList);
        return result;
    }
    
    @GetMapping(value = "/alarm/statusCount", produces = "application/json")
    public String getStatusCount() throws JsonProcessingException {
        List<String> statusCount = new ArrayList<>();

        	statusCount.add(alarmsHeaderService.queryStatusCount("0"));
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
    
    @GetMapping(value="/listSortMasters",produces = "application/json;charset=utf8")
    public String getSortMasterInfo() throws JsonProcessingException{
    	Map<String,List<SortMaster>> map = new HashMap<>();
    	List<SortMaster> operationTypes = alarmsHeaderService.listSortMasters("operationType");
    	List<SortMaster> operationResults = alarmsHeaderService.listSortMasters("operationResult");
    	map.put("operationTypes", operationTypes);
    	map.put("operationResults", operationResults);
    	return omAlarm.writeValueAsString(map);
    }
}
