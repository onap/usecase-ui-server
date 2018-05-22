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
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.ResponseBody;
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

    private final String[] PerformanceCSVHeaders = {"version",
            "eventName","domain","eventId","eventType","nfcNamingCode",
            "nfNamingCode","sourceId","sourceName","reportingEntityId",
            "reportingEntityName","priority","startEpochMicrosec","lastEpochMicroSec",
            "sequence","measurementsForVfScalingVersion","measurementInterval",
            "createTime","updateTime","value","name"};

    private ObjectMapper omPerformance = new ObjectMapper();

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
                performanceHeader.setCreateTime(!"null".equals(startTime)?new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(startTime):null);
                performanceHeader.setUpdateTime(!"null".equals(endTime)?new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(endTime):null);
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

    @ResponseBody
    @RequestMapping(value = {"/performance/diagram/{unit}/{eventId}"}, method = RequestMethod.GET, produces = "application/json")
    public String generateDiagram(@PathVariable String unit,@PathVariable String eventId) throws ParseException, JsonProcessingException {
        Map<String,List<Integer>> diagramSource = new HashMap<>();
        String[] names = {"cpu","network","disk","memory"};
        switch (unit){
            case "hour":
                for(int i = 0 ; i < 4 ; i++){
                    Date startDateHour = DateUtils.stringToDate(DateUtils.initDate(new Date(),1,1,1,-1,0,0));
                    Date endDateHour = DateUtils.stringToDate(DateUtils.initDate(new Date(),1,1,1,-1,0,0));
                    endDateHour = DateUtils.stringToDate(DateUtils.addDate(endDateHour,"minute",15));
                    List<Integer> values = new ArrayList<>();
                    for (int j = 0 ; j < 4 ; j++){
                        logger.info(DateUtils.dateToString(startDateHour));
                        logger.info(DateUtils.dateToString(endDateHour));
                        values.add(performanceInformationService.queryDataBetweenSum(eventId,names[i],startDateHour,endDateHour));
                        startDateHour = DateUtils.stringToDate(DateUtils.addDate(startDateHour,"minute",15));
                        endDateHour = DateUtils.stringToDate(DateUtils.addDate(endDateHour,"minute",15));
                    }
                    diagramSource.put(names[i],values);
                }
                break;
            case "day":
                for(int i = 0 ; i < 4 ; i++){
                    Date startDateDay = DateUtils.stringToDate(DateUtils.initDate(new Date(),1,1,-1,0,0,0));
                    Date endDateDay = DateUtils.stringToDate(DateUtils.initDate(new Date(),1,1,-1,0,0,0));
                    endDateDay = DateUtils.stringToDate(DateUtils.addDate(endDateDay,"hour",1));
                    List<Integer> values = new ArrayList<>();
                    for (int j = 0 ; j < 24 ; j++){
                        values.add(performanceInformationService.queryDataBetweenSum(eventId,names[i],startDateDay,endDateDay));
                        startDateDay = DateUtils.stringToDate(DateUtils.addDate(startDateDay,"hour",1));
                        endDateDay = DateUtils.stringToDate(DateUtils.addDate(endDateDay,"hour",1));
                    }
                    diagramSource.put(names[i],values);
                }
                break;
            case "month":
               for(int i = 0 ; i < 4 ; i++){
                   Date startDateMonth = DateUtils.stringToDate(DateUtils.initDate(new Date(),1,-1,0,0,0,0));
                   Date endDateMonth = DateUtils.stringToDate(DateUtils.initDate(new Date(),1,-1,0,0,0,0));
                   endDateMonth = DateUtils.stringToDate(DateUtils.addDate(endDateMonth,"day",1));
                   List<Integer> values = new ArrayList<>();
                    for (int j = 0 ; j < 31 ; j++){
                        values.add(performanceInformationService.queryDataBetweenSum(eventId,names[i],startDateMonth,endDateMonth));
                        startDateMonth = DateUtils.stringToDate(DateUtils.addDate(startDateMonth,"day",1));
                        endDateMonth = DateUtils.stringToDate(DateUtils.addDate(endDateMonth,"day",1));
                    }
                    diagramSource.put(names[i],values);
                }
                break;
            case "year":
                for(int i = 0 ; i < 4 ; i++){
                    Date startDateYear = DateUtils.stringToDate(DateUtils.initDate(new Date(),-1,0,0,0,0,0));
                    Date endDateYear = DateUtils.stringToDate(DateUtils.initDate(new Date(),-1,0,0,0,0,0));
                    endDateYear = DateUtils.stringToDate(DateUtils.addDate(endDateYear,"month",1));
                    List<Integer> values = new ArrayList<>();
                    for (int j = 0 ; j < 12 ; j++){
                        values.add(performanceInformationService.queryDataBetweenSum(eventId,names[i],startDateYear,endDateYear));
                        startDateYear = DateUtils.stringToDate(DateUtils.addDate(startDateYear,"month",1));
                        endDateYear = DateUtils.stringToDate(DateUtils.addDate(endDateYear,"month",1));
                    }
                    diagramSource.put(names[i],values);
                }
                break;
        }
        omPerformance.setDateFormat(new SimpleDateFormat(Constant.DATE_FORMAT));
        return omPerformance.writeValueAsString(diagramSource);
    }

    @RequestMapping(value = {"/performance/diagram"}, method = RequestMethod.POST, produces = "application/json")
    public String generateDiagram(@RequestParam String sourceId,@RequestParam String startTime,@RequestParam String endTime,@RequestParam String nameParent,@RequestParam(required = false) String nameChild)  {
        List<String> diagramSource = new ArrayList<>();
        try {
            logger.info(sourceId+":"+startTime+":"+endTime+":"+nameParent+":"+nameChild);
            if (performanceHeaderService.queryPerformanceHeader(new PerformanceHeader(sourceId),1,10).getList() != null){
                if (nameChild != null && !"".equals(nameChild)){
                    sourceId = nameParent;
                    nameParent = nameChild;
                }
                performanceInformationService.queryDateBetween(sourceId,nameParent,startTime,endTime)
                        .forEach( per -> {
                            diagramSource.add(per.getValue());
                        });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            return omPerformance.writeValueAsString(diagramSource);
        } catch (JsonProcessingException e) {
            logger.error("JsonProcessingException:"+e.getMessage());
            return "";
        }
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
            List<String> names = new ArrayList<>();
            performanceInformationService.queryDateBetween(sourceId.toString(),null,null,null).forEach( per ->{
                if (!names.contains(per.getName()))
                    names.add(per.getName());
            } );
            return omPerformance.writeValueAsString(names);
        } catch (JsonProcessingException e) {
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
