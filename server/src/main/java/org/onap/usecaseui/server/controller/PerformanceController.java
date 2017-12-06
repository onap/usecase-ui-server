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
import org.onap.usecaseui.server.bo.PerformanceBo;
import org.onap.usecaseui.server.constant.Constant;
import org.onap.usecaseui.server.service.PerformanceHeaderService;
import org.onap.usecaseui.server.service.PerformanceInformationService;
import org.onap.usecaseui.server.util.CSVUtils;
import org.onap.usecaseui.server.util.DateUtils;
import org.onap.usecaseui.server.util.Page;
import org.onap.usecaseui.server.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


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
            "eventName", "domain", "eventId", "eventType", "nfcNamingCode",
            "nfNamingCode", "sourceId", "sourceName", "reportingEntityId",
            "reportingEntityName", "priority", "startEpochMicrosec", "lastEpochMicroSec",
            "sequence", "measurementsForVfScalingVersion", "measurementInterval",
            "createTime", "updateTime", "value", "name"};

    private ObjectMapper omPerformance = new ObjectMapper();

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    @RequestMapping(value = {"/performance/{currentPage}/{pageSize}", "/performance/{currentPage}/{pageSize}/{sourceId}/{sourceName}/{priority}/{startTime}/{endTime}"}, method = RequestMethod.GET, produces = "application/json")
    public String getPerformanceData(HttpServletResponse response, @PathVariable int currentPage,
                                     @PathVariable int pageSize, @PathVariable(required = false) String sourceId,
                                     @PathVariable(required = false) String sourceName, @PathVariable(required = false) String priority,
                                     @PathVariable(required = false) String startTime, @PathVariable(required = false) String endTime) throws JsonProcessingException {
        logger.info("transfer getAlarmData Apis, " +
                        "Parameter all follows : [currentPage : {} , pageSize : {} , sourceId : {} , " +
                        "sourceName : {} , priority : {} , startTime :{} , endTime : {} ]"
                , currentPage, pageSize, sourceId, sourceName, priority, startTime, endTime);
        List<Object> list = new ArrayList<>();
        Page pa = null;
        if (null != sourceId || null != sourceName || null != priority || null != startTime || null != endTime) {
            PerformanceHeader performanceHeader = new PerformanceHeader();
            performanceHeader.setSourceId(!"null".equals(sourceId) ? sourceId : null);
            performanceHeader.setSourceName(!"null".equals(sourceName) ? sourceName : null);
            try {
                performanceHeader.setCreateTime(!"null".equals(startTime) ? new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(startTime) : null);
                performanceHeader.setUpdateTime(!"null".equals(endTime) ? new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(endTime) : null);
            } catch (ParseException e) {
                if (null != response)
                    response.setStatus(400);
                logger.error("ParseException[" + startTime + "]:" + e.getMessage());
                return "{'result':'error'}";
            }
            pa = performanceHeaderService.queryPerformanceHeader(performanceHeader, currentPage, pageSize);
            List<PerformanceHeader> performanceHeaders = pa.getList();
            performanceHeaders.forEach(per -> {
                PerformanceBo pbo = new PerformanceBo();
                PerformanceInformation pe = new PerformanceInformation();
                pe.setEventId(per.getSourceId());
                List<PerformanceInformation> performanceInformations = performanceInformationService.queryPerformanceInformation(pe, 1, 100).getList();
                pbo.setPerformanceHeader(per);
                performanceInformations.forEach(pi -> {
                    if (pi.getValue().equals("")) {
                        StringBuffer value1 = new StringBuffer();
                        performanceInformationService.queryPerformanceInformation(new PerformanceInformation(pi.getName()), 1, 100).getList()
                                .forEach(val -> value1.append(val.getValue()));
                        pi.setValue(value1.toString());
                    }
                });
                pbo.setPerformanceInformation(performanceInformations);
                list.add(pbo);
            });
        } else {
            pa = performanceHeaderService.queryPerformanceHeader(null, currentPage, pageSize);
            List<PerformanceHeader> p = pa != null ? pa.getList() : null;
            if (null != p && p.size() > 0)
                p.forEach(per -> {
                    PerformanceBo pbo = new PerformanceBo();
                    pbo.setPerformanceHeader(per);
                    pbo.setPerformanceInformation(performanceInformationService.queryPerformanceInformation(new PerformanceInformation(per.getEventId()), 1, 100).getList());
                    list.add(pbo);
                });
        }
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("performances", list);
            map.put("totalRecords", pa.getTotalRecords());
            omPerformance.setDateFormat(new SimpleDateFormat(Constant.DATE_FORMAT));
            return omPerformance.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            logger.error("JsonProcessingException" + e.getMessage());
            return omPerformance.writeValueAsString("failed");
        }
    }

    /*@RequestMapping(value = {"/performance/genCsv/{eventId}"}, method = RequestMethod.GET, produces = "application/json")
    public String generateCsvFile(HttpServletResponse response, @PathVariable String[] eventId) throws JsonProcessingException {
        String csvFile = "csvFiles/vnf_performance_" + new SimpleDateFormat("yy-MM-ddHH:mm:ss").format(new Date()) + ".csv";
        List<PerformanceHeader> performanceHeaders = performanceHeaderService.queryId(eventId);
        if (null == performanceHeaders || performanceHeaders.size() <= 0)
            return new ObjectMapper().writeValueAsString("selected eventId don't exist");
        List<String[]> csvData = new ArrayList<>();
        performanceHeaders.forEach(s -> {
            List<PerformanceInformation> information = performanceInformationService.queryPerformanceInformation(new PerformanceInformation(s.getEventId()), 1, 100).getList();
            String names = "";
            String values = "";
            if (0 < information.size() && null != information) {
                for (PerformanceInformation a : information) {
                    names += a.getName() + ",";
                    values += a.getValue() + ",";
                }
                names = names.substring(0, names.lastIndexOf(','));
                values = values.substring(0, values.lastIndexOf(','));
            }
            csvData.add(new String[]{
                    s.getVersion(), s.getEventName(), s.getDomain(), s.getEventId(), s.getEventType(), s.getNfcNamingCode(), s.getNfNamingCode(),
                    s.getSourceId(), s.getSourceName(), s.getReportingEntityId(), s.getReportingEntityName(), s.getPriority(),
                    s.getStartEpochMicrosec(), s.getLastEpochMicroSec(), s.getSequence(), s.getMeasurementsForVfScalingVersion(),
                    s.getMeasurementInterval(), DateUtils.dateToString(s.getCreateTime()), DateUtils.dateToString(s.getUpdateTime()),
                    names, values
            });
        });
        CSVUtils.writeCsv(PerformanceCSVHeaders, csvData, csvFile);
        if (ResponseUtil.responseDownload(csvFile, response)) {
            return omPerformance.writeValueAsString("success");
        } else {
            return omPerformance.writeValueAsString("failed");
        }
    }*/

    /*@RequestMapping(value = {"/performance/genDiaCsv/{dataJson}"}, method = RequestMethod.GET, produces = "application/json")
    public String generateDiaCsvFile(HttpServletResponse response, @PathVariable String dataJson) throws IOException {
        List<Map<String, Object>> dataList = omPerformance.readValue(dataJson, List.class);
        String csvFileName = "csvFiles/" + dataList.get(0).get("name") + "_" + new SimpleDateFormat("yy-MM-ddHH:mm:ss").format(new Date()) + ".csv";
        try {
            String[] headers = new String[]{"eventId", "name", "dateUnit", "value"};
            List<String[]> csvDatas = new ArrayList<>();
            if (null != dataList) {
                dataList.forEach((l) -> {
                    StringBuffer fileData = new StringBuffer();
                    l.forEach((k, v) -> {
                        logger.info(v.toString());
                        fileData.append(v.toString() + ",");
                    });
                    csvDatas.add(fileData.toString().split(","));
                });
            }
            CSVUtils.writeCsv(headers, csvDatas, csvFileName);
        } catch (Exception pe) {
            logger.error(pe.getMessage());
        }
        if (ResponseUtil.responseDownload(csvFileName, response)) {
            return omPerformance.writeValueAsString("success");
        } else {
            return omPerformance.writeValueAsString("failed");
        }
    }*/

    @RequestMapping(value = {"/performance/diagram"}, method = RequestMethod.POST, produces = "application/json")
    public String generateDiagram(@RequestParam String sourceId, @RequestParam String startTime, @RequestParam String endTime, @RequestParam String nameParent, @RequestParam String format) {
        try {
            return omPerformance.writeValueAsString(diagramDate(sourceId,nameParent,startTime,endTime,format));
        } catch (JsonProcessingException e) {
            logger.error("JsonProcessingException:" + e.getMessage());
            return null;
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(value = {"/performance/resourceIds"}, method = RequestMethod.GET)
    public String getSourceIds() {
        List<String> sourceIds = new ArrayList<>();
        try {
            performanceHeaderService.queryAllSourceId().forEach( ph -> {
                if (!sourceIds.contains(ph))
                    sourceIds.add(ph);
            } );
            return omPerformance.writeValueAsString(sourceIds);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
            return "";
        }
    }

    @RequestMapping(value = {"/performance/names"}, method = RequestMethod.POST)
    public String getNames(@RequestParam Object sourceId) {
        try {
            List<String> names = new ArrayList<>();
            performanceInformationService.queryDateBetween(sourceId.toString(), null, null, null).forEach(per -> {
                if (!names.contains(per.getName()) && per.getValue().matches("[0-9]*"))
                    if (Double.parseDouble(per.getValue()) > 0 && !per.getName().equals("Period"))
                        names.add(per.getName());

            });
            return omPerformance.writeValueAsString(names);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
            return "";
        }
    }

    private List<List<Long>> dateProcess(String sourceId,String name,long startTimeL,long endTimeL,long timeIteraPlusVal,long keyVal,long keyValIteraVal,String keyUnit) throws ParseException {
        List<List<Long>> dataList = new ArrayList<>();
        long tmpEndTimeL = startTimeL + timeIteraPlusVal;
        while (endTimeL >= tmpEndTimeL){
            List<Map<String,String>> maps = performanceInformationService.queryMaxValueByBetweenDate(sourceId,name,sdf.format(new Date(startTimeL)),sdf.format(new Date(tmpEndTimeL)));
            maps.forEach( map -> {
                try {
                    List<Long> longList = new ArrayList<>();
                    if (map.get("Time") != null && !"".equals(map.get("Time")) && !"NULL".equals(map.get("Time")) ){
                        longList.add(sdf.parse(map.get("Time")).getTime());
                        if (map.get("Max") != null && !"".equals(map.get("Max")))
                            longList.add(Long.parseLong(map.get("Max")));
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

    private List<List<Long>> diagramDate(String sourceId,String name,String startTime,String endTime,String format){
        try {
            long startTimel = sdf.parse(startTime).getTime();
            long endTimel = sdf.parse(endTime).getTime();
            if (format != null && !format.equals("auto")){
                switch (format){
                    case "minute":
                        return dateProcess(sourceId,name,startTimel,endTimel,900000,15,15,"minute");
                    case "hour":
                        return dateProcess(sourceId,name,startTimel,endTimel,3600000,1,1,"hour");
                    case "day":
                        return dateProcess(sourceId,name,startTimel,endTimel,86400000,1,1,"day");
                    case "month":
                        return dateProcess(sourceId,name,startTimel,endTimel,2592000000L,1,1,"month");
                    case "year":
                        return dateProcess(sourceId,name,startTimel,endTimel,31536000000L,1,1,"year");
                }
            }else if (format != null && format.equals("auto")){
                long minutes = (endTimel - startTimel) / (1000 * 60);
                long hours = minutes / 60;
                if (hours > 12){
                    long days = hours / 24;
                    if (days > 3){
                        long months = days / 31;
                        if (months > 2){
                            return dateProcess(sourceId,name,startTimel,endTimel,86400000,1,1,"day");
                        }else {
                            return dateProcess(sourceId,name,startTimel,endTimel,2592000000L,1,1,"month");
                        }
                    }else {
                        return dateProcess(sourceId,name,startTimel,endTimel,3600000,1,1,"hour");
                    }
                }else {
                    return dateProcess(sourceId,name,startTimel,endTimel,900000,15,15,"minute");
                }
            }
        } catch (ParseException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

}
