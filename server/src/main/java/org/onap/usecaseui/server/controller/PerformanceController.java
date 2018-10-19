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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.onap.usecaseui.server.bean.PerformanceHeader;
import org.onap.usecaseui.server.bean.PerformanceInformation;
import org.onap.usecaseui.server.bo.PerformanceBo;
import org.onap.usecaseui.server.constant.Constant;
import org.onap.usecaseui.server.service.PerformanceHeaderService;
import org.onap.usecaseui.server.service.PerformanceInformationService;
import org.onap.usecaseui.server.util.DateUtils;
import org.onap.usecaseui.server.util.Page;
import org.onap.usecaseui.server.util.UuiCommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@RestController
@Configuration
@EnableAspectJAutoProxy
public class PerformanceController {

    @Resource(name = "PerformanceHeaderService")
    private PerformanceHeaderService performanceHeaderService;

    @Resource(name = "PerformanceInformationService")
    private PerformanceInformationService performanceInformationService;

    private Logger logger = LoggerFactory.getLogger(PerformanceController.class);
    
    public void setPerformanceHeaderService(PerformanceHeaderService performanceHeaderService) {
        this.performanceHeaderService = performanceHeaderService;
    }


    public void setPerformanceInformationService(PerformanceInformationService performanceInformationService) {
        this.performanceInformationService = performanceInformationService;
    }
    
    private final String[] PerformanceCSVHeaders = {"version",
            "eventName","domain","eventId","eventType","nfcNamingCode",
            "nfNamingCode","sourceId","sourceName","reportingEntityId",
            "reportingEntityName","priority","startEpochMicrosec","lastEpochMicroSec",
            "sequence","measurementsForVfScalingVersion","measurementInterval",
            "createTime","updateTime","value","name"};

    private ObjectMapper omPerformance = new ObjectMapper();
    
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
    private String formatDate = "yyyy-MM-dd";
    
    @RequestMapping(value = {"/performance/{currentPage}/{pageSize}","/performance/{currentPage}/{pageSize}/{sourceId}/{sourceName}/{priority}/{startTime}/{endTime}"},method = RequestMethod.GET, produces = "application/json")
    public String getPerformanceData(HttpServletResponse response,@PathVariable int currentPage,
                                     @PathVariable int pageSize,@PathVariable(required = false) String sourceId,
                                     @PathVariable(required = false) String sourceName,@PathVariable(required = false) String priority,
                                     @PathVariable(required = false) String startTime,@PathVariable(required = false) String endTime) throws JsonProcessingException {
        logger.info("API Parameter: [currentPage:{}, pageSize:{}, sourceId:{}, sourceName:{}, priority:{}, startTime:{}, endTime:{}]" ,currentPage, pageSize, sourceId, sourceName, priority, startTime, endTime);
        List<Object> list = new ArrayList<>();
        Page<PerformanceHeader> pa = new Page<PerformanceHeader>();
        if (null != sourceId || null != sourceName || null != priority || null != startTime || null != endTime){
            PerformanceHeader performanceHeader = new PerformanceHeader();
            performanceHeader.setSourceId(!"null".equals(sourceId)?sourceId:null);
            performanceHeader.setSourceName(!"null".equals(sourceName)?sourceName:null);
            try {
                performanceHeader.setStartEpochMicrosec(!"null".equals(startTime)?new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(startTime).getTime()+"":null);
                performanceHeader.setLastEpochMicroSec(!"null".equals(endTime)?new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(endTime).getTime()+"":null);
            } catch (ParseException e) {
                 if (null != response)
                    response.setStatus(400);
                logger.error("ParseException["+startTime+"]:"+e.getMessage());
                return "{'result':'error'}";
            }
            pa = performanceHeaderService.queryPerformanceHeader(performanceHeader,currentPage,pageSize);
            List<PerformanceHeader> performanceHeaders = pa.getList();
            performanceHeaders.forEach( per ->{
                PerformanceBo pbo = new PerformanceBo();
                PerformanceInformation pe = new PerformanceInformation();
                pe.setSourceId(per.getSourceId());
                List<PerformanceInformation> performanceInformations = performanceInformationService.queryPerformanceInformation(pe,1,100).getList();
                pbo.setPerformanceHeader(per);
                performanceInformations.forEach( pi ->{
                    if (pi.getValue().equals("")){
                        StringBuffer value1 = new StringBuffer();
                        performanceInformationService.queryPerformanceInformation(new PerformanceInformation(pi.getName()),1,100).getList()
                                .forEach( val -> value1.append(val.getValue()));
                        pi.setValue(value1.toString());
                    }
                } );
                pbo.setPerformanceInformation(performanceInformations);
                list.add(pbo);
            });
        }else{
            pa = performanceHeaderService.queryPerformanceHeader(null,currentPage,pageSize);
            List<PerformanceHeader> p = pa!=null?pa.getList():null;
            if (null != p && p.size() > 0)
                p.forEach( per ->{
                    PerformanceBo pbo = new PerformanceBo();
                    pbo.setPerformanceHeader(per);
                    pbo.setPerformanceInformation(performanceInformationService.queryPerformanceInformation(new PerformanceInformation(per.getEventId()),1,100).getList());
                    list.add(pbo);
                });
        }
        try {
            Map<String,Object> map = new HashMap<>();
            map.put("performances",list);
            map.put("totalRecords",pa.getTotalRecords());
            omPerformance.setDateFormat(new SimpleDateFormat(Constant.DATE_FORMAT));
            return omPerformance.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            logger.error("JsonProcessingException"+e.getMessage());
            return omPerformance.writeValueAsString("failed");
        }
    }

    @RequestMapping(value = {"/performance/diagram"}, method = RequestMethod.POST, produces = "application/json")
    public String generateDiagram(@RequestParam String sourceId,@RequestParam String startTime,@RequestParam String endTime,@RequestParam String nameParent,@RequestParam String format)  {
        long timeInterval = 0;
    	try {
        	if("minute".equals(format)){//performance 时间级别  hour day month
        		formatDate="yyyy-MM-dd HH:mm";
        		timeInterval =5*60000;
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
            return getDiagram(sourceId, startTimel, endTimel+timeInterval, timeInterval,format,nameParent);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    
    private  String getDiagram(String sourceId, long startTimeL, long endTimeL, long timeIteraPlusVal,String format,String nameParent) throws JsonProcessingException{
    	Map<String,List> result = new HashMap<String,List>();
    	
    	Map<String,List> allMaps = dateProcess(sourceId, startTimeL, endTimeL, timeIteraPlusVal,format,nameParent);
    	result.put("dateList", allMaps.get("dateTime"));
    	result.put("valueList", allMaps.get("dataList"));
    	return omPerformance.writeValueAsString(result);
    }
    private Map<String,List> dateProcess(String sourceId, long startTimeL, long endTimeL, long timeIteraPlusVal,String format,String nameParent) {
    	Map<String,List> result = new HashMap<String,List>();
        List<String> dateList = new ArrayList<String>();
        List<String> numList = new ArrayList<String>();
        long tmpEndTimeL = startTimeL + timeIteraPlusVal;
        while (endTimeL >= tmpEndTimeL) {
           String num = performanceInformationService.queryMaxValueByBetweenDate(sourceId, nameParent, startTimeL+"", tmpEndTimeL+"");
            dateList.add(DateUtils.getResultDate(startTimeL, format));
            numList.add(num);
            startTimeL += timeIteraPlusVal;
            tmpEndTimeL += timeIteraPlusVal;
        }
        result.put("dateTime", dateList);
        result.put("dataList", numList);
        return result;
    }
    
    @RequestMapping(value = {"/performance/resourceIds"},method = RequestMethod.GET)
    public String getSourceIds(){
        try {
            return omPerformance.writeValueAsString(performanceHeaderService.queryAllSourceId());
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
            return "";
        }
    }

    @RequestMapping(value = {"/performance/names"},method = RequestMethod.POST)
    public String getNames(@RequestParam Object sourceId){
        try {
            Set<String> names = new HashSet<>();
            List<PerformanceInformation> list =performanceInformationService.queryDateBetween(sourceId.toString(), null, null, null);
            PerformanceInformation per;
            for(int a=0;a<list.size();a++) {
                per = (PerformanceInformation)list.get(a);
                if (UuiCommonUtil.checkNumber(per.getValue(),"^(-?\\d+)(\\.\\d+)?$"))
                    if (Double.parseDouble(per.getValue()) > 0)
                        names.add(per.getName());

            }
            return omPerformance.writeValueAsString(names);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return "";
        }
    }
    
    @RequestMapping("/performance/getPerformanceHeaderDetail/{id}")
    public String getPerformanceHeaderDetail(@PathVariable String id) throws JsonProcessingException {
        PerformanceHeader performanceHeader= performanceHeaderService.getPerformanceHeaderById(id);
        List<PerformanceInformation> list =new ArrayList<>();
        if(UuiCommonUtil.isNotNullOrEmpty(performanceHeader)){
        	String headerId = performanceHeader.getId();
        	list = performanceInformationService.getAllPerformanceInformationByHeaderId(headerId);
        }

        Map map = new HashMap();
        map.put("performanceHeader",performanceHeader);
        map.put("list",list);

        String string =omPerformance.writeValueAsString(map);
        return string;
    }


}
