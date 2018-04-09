/**
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
import org.onap.usecaseui.server.bean.PerformanceHeaderPm;
import org.onap.usecaseui.server.bean.PerformanceInformationPm;
import org.onap.usecaseui.server.bo.PerformanceBo;
import org.onap.usecaseui.server.constant.Constant;
import org.onap.usecaseui.server.service.*;
import org.onap.usecaseui.server.util.DateUtils;
import org.onap.usecaseui.server.util.Page;

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
public class PerformancePmController {

    @Resource(name = "PerformanceHeaderPmService")
    private PerformanceHeaderPmService performanceHeaderPmService;

    @Resource(name = "PerformanceInformationPmService")
    private PerformanceInformationPmService performanceInformationPmService;



    @Resource(name = "PerformanceHeaderService")
    private PerformanceHeaderService performanceHeaderService;

    @Resource(name = "PerformanceInformationService")
    private PerformanceInformationService performanceInformationService;



    @Resource(name = "PerformanceHeaderVmService")
    private PerformanceHeaderVmService performanceHeaderVmService;

    @Resource(name = "PerformanceInformationVmService")
    private PerformanceInformationVmService performanceInformationVmService;


    public void setPerformanceHeaderPmService(PerformanceHeaderPmService performanceHeaderPmService) {
        this.performanceHeaderPmService = performanceHeaderPmService;
    }

    public void setPerformanceInformationPmService(PerformanceInformationPmService performanceInformationPmService) {
        this.performanceInformationPmService = performanceInformationPmService;
    }

    public void setPerformanceHeaderVmService(PerformanceHeaderVmService performanceHeaderVmService) {
        this.performanceHeaderVmService = performanceHeaderVmService;
    }

    public void setPerformanceInformationVmService(PerformanceInformationVmService performanceInformationVmService) {
        this.performanceInformationVmService = performanceInformationVmService;
    }

    public void setPerformanceHeaderService(PerformanceHeaderService performanceHeaderService) {
        this.performanceHeaderService = performanceHeaderService;
    }


    public void setPerformanceInformationService(PerformanceInformationService performanceInformationService) {
        this.performanceInformationService = performanceInformationService;
    }







    private Logger logger = LoggerFactory.getLogger(PerformanceController.class);

    private final String[] PerformanceCSVHeaders = {"version",
            "eventName", "domain", "eventId", "eventType", "nfcNamingCode",
            "nfNamingCode", "sourceId", "sourceName", "reportingEntityId",
            "reportingEntityName", "priority", "startEpochMicrosec", "lastEpochMicroSec",
            "sequence", "measurementsForVfScalingVersion", "measurementInterval",
            "createTime", "updateTime", "value", "name"};

    private ObjectMapper omPerformance = new ObjectMapper();

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");


    @RequestMapping("/performance/getAllByDatetimePm/{eventId}/{startTime}/{endTime}")
    public String getAllByDatetime(@PathVariable(required = false) String eventId,@PathVariable(required = false) String startTime, @PathVariable(required = false) String endTime) throws ParseException, JsonProcessingException {
        String startime_s = "2017-10-29";
        String endtime_s = "2017-12-24";
        eventId = "2017-11-15T06:30:00MmeFunction1101ZTHX1MMEGJM1W1";
        //String startime_s = startTime;
        //String endtime_s = endTime;
        String string ="";
        if(startime_s!=null && endtime_s!=null && !"".equals(startime_s) && !"".equals(endtime_s) ) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date startime = formatter.parse(startime_s);
            Date endtime = formatter.parse(endtime_s);
            DateUtils dateUtils = new DateUtils();
            List<Date> datelist = dateUtils.getBetweenDates(startime, endtime);
            StringBuffer dateB = new StringBuffer();
            StringBuffer allB = new StringBuffer();
            //StringBuffer activeB = new StringBuffer();
            //StringBuffer closeB = new StringBuffer();

            for (Date dates : datelist) {
                String date_s = formatter.format(dates);
                dateB.append(date_s).append(",");
                //int aa = performanceHeaderService.getAllByDatetime("active", eventId,  date_s);
                //activeB.append(aa + "").append(",");
                //int bb = performanceHeaderService.getAllByDatetime("close", eventId,  date_s);
                //closeB.append(bb + "").append(",");
                int cc = performanceHeaderPmService.getAllByDatetime(eventId,  date_s);
                allB.append(cc + "").append(",");

            }
            String dateBa = dateB.toString();
            String allBa = allB.toString();
            //String activeBa = activeB.toString();
            //String closeBa = closeB.toString();

            String[] dateArr = dateBa.substring(0, dateBa.length() - 1).split(",");
            //String[] activeArr = activeBa.substring(0, activeBa.length() - 1).split(",");
            //String[] closeArr = closeBa.substring(0, closeBa.length() - 1).split(",");
            String[] allArr = allBa.substring(0, allBa.length() - 1).split(",");

            Map map = new HashMap();
            map.put("dateArr", dateArr);
            //map.put("activeArr", activeArr);
            //map.put("closeArr", closeArr);
            map.put("allArr", allArr);
            string = omPerformance.writeValueAsString(map);
        }

        return string;
    }


    @RequestMapping("/performance/getPerformanceHeaderPmDetail/{id}")
    public String getPerformanceHeaderDetail(@PathVariable Integer id) throws JsonProcessingException {
        PerformanceHeaderPm performanceHeaderPm= performanceHeaderPmService.getPerformanceHeaderDetail(id);
        String eventId ="ab305d54-85b4-a31b-7db2-fb6b9e546015_s";
        if(null!=performanceHeaderPm){
            eventId = performanceHeaderPm.getSourceId();
        }
        //List<PerformanceInformation> list =getAllPerformanceInformationByeventId(eventId);
        List<PerformanceInformationPm> list =new ArrayList<>();
        if(!"ab305d54-85b4-a31b-7db2-fb6b9e546015_s".equals(eventId)){
            list = performanceInformationPmService.getAllPerformanceInformationByeventId(eventId);
        }else{
            PerformanceInformationPm performanceInformation_s = new PerformanceInformationPm();
            performanceInformation_s.setId(4);
        }

        Map map = new HashMap();
        map.put("performanceHeaderPm",performanceHeaderPm);
        map.put("list",list);

        String string =omPerformance.writeValueAsString(map);
        return string;
    }








    @RequestMapping(value = {"/performance/getPerformanceDataByPm/{eventName}/{sourceName}/{reportingEntityName}/{createTime}/{endTime}"},method =RequestMethod.GET,produces = "application/json")
    public String getPerformanceDataByPm( @PathVariable(required = false) String eventName,@PathVariable(required = false) String sourceName,@PathVariable(required = false) String reportingEntityName,@PathVariable(required = false) String createTime,@PathVariable(required = false) String endTime) throws JsonProcessingException {
        Map map = new HashMap();
        Date createTime_s=null;
        Date endTime_s=null;

       /* Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);*/


        eventName="Mfvs_MMEEthernetPort";
        sourceName="1101ZTHX1EPO1NK7E0Z2";
        reportingEntityName="ZTE-CMBJ-BJ,SubNetwork=100001,ManagedElement=100040_40,EthernetPort=40_65535_1_2";
        createTime="2017-11-15 06:30:00";
        endTime="2017-12-15 06:30:00";

        try {
            createTime_s =(!"null".equals(createTime) ? new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(createTime) : null);
            endTime_s =(!"null".equals(endTime) ? new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(endTime) : null);
        } catch (ParseException e) {
            logger.error("Parse date error :" + e.getMessage());
        }
      /*  int countPm = performanceHeaderPmService.getAllCountByEventType();
        int countVm =  performanceHeaderVmService.getAllCountByEventType();
        int countBn =  performanceHeaderService.getAllCountByEventType();*/

        Set<String> eventNameList = new HashSet();
        Set<String> sourceNameList = new HashSet<>();
        Set<String> reportingEntityNameList = new HashSet<>();
        Set<String> sourceIdList = new HashSet<>();
        List<PerformanceHeaderPm> list = performanceHeaderPmService.getAllByEventType(eventName,sourceName,reportingEntityName,createTime_s,endTime_s);
        PerformanceHeaderPm performanceHeaderPm;
        for(int a=0;a<list.size();a++){
            performanceHeaderPm = list.get(a);
            eventNameList.add(performanceHeaderPm.getEventName());
            sourceNameList.add(performanceHeaderPm.getSourceName());
            reportingEntityNameList.add(performanceHeaderPm.getReportingEntityName());

            sourceIdList.add(performanceHeaderPm.getSourceId());

        }

        /*map.put("countPm",countPm);
        map.put("countVm",countVm);
        map.put("countBn",countBn);*/
        //map.put("list",list);
        map.put("eventNameList",eventNameList);
        map.put("sourceNameList",sourceNameList);
        map.put("reportingEntityNameList",reportingEntityNameList);

        map.put("sourceIdList",sourceIdList);


        String string =omPerformance.writeValueAsString(map);

        return string;
    }

    @RequestMapping(value = {"/performance/getPerformanceListByPm/{eventName}/{sourceName}/{reportingEntityName}/{createTime}/{endTime}"},method =RequestMethod.GET,produces = "application/json")
    public String getPerformanceListByPm( @PathVariable(required = false) String eventName,@PathVariable(required = false) String sourceName,@PathVariable(required = false) String reportingEntityName,@PathVariable(required = false) String createTime,@PathVariable(required = false) String endTime) throws JsonProcessingException {
        Map map = new HashMap();
        Date createTime_s=null;
        Date endTime_s=null;

       /* Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);*/


        eventName="Mfvs_MMEEthernetPort";
        sourceName="1101ZTHX1EPO1NK7E0Z2";
        reportingEntityName="ZTE-CMBJ-BJ,SubNetwork=100001,ManagedElement=100040_40,EthernetPort=40_65535_1_2";
        createTime="2017-11-15 06:30:00";
        endTime="2017-12-15 06:30:00";



        try {
            createTime_s =(!"null".equals(createTime) ? new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(createTime) : null);
            endTime_s =(!"null".equals(endTime) ? new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(endTime) : null);
        } catch (ParseException e) {
            logger.error("Parse date error :" + e.getMessage());
        }
        //int countClose = performanceHeaderService.getAllCountByStatus("close");
        //int countActive =  performanceHeaderService.getAllCountByStatus("active");
        //int countAll =countActive + countClose;
        //Set<String> eventNameList = new HashSet();
        //Set<String> sourceNameList = new HashSet<>();
        //Set<String> reportingEntityNameList = new HashSet<>();

        //Set<String> sourceIdList = new HashSet<>();


        List<PerformanceHeaderPm> list = performanceHeaderPmService.getAllByEventType(eventName,sourceName,reportingEntityName,createTime_s,endTime_s);
      /* PerformanceHeader performanceHeader;
       for(int a=0;a<list.size();a++){
           performanceHeader = list.get(a);
           eventNameList.add(performanceHeader.getEventName());
           sourceNameList.add(performanceHeader.getSourceName());
           reportingEntityNameList.add(performanceHeader.getReportingEntityName());

           sourceIdList.add(performanceHeader.getSourceId());

       }*/

        map.put("list",list);

        String string =omPerformance.writeValueAsString(map);

        return string;
    }








    @RequestMapping(value = {"/performancePm/{currentPage}/{pageSize}", "/performancePm/{currentPage}/{pageSize}/{sourceId}/{sourceName}/{priority}/{startTime}/{endTime}"}, method = RequestMethod.GET, produces = "application/json")
    public String getPerformancePmData(HttpServletResponse response, @PathVariable int currentPage,
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
            PerformanceHeaderPm performanceHeaderPm = new PerformanceHeaderPm();
            performanceHeaderPm.setSourceId(!"null".equals(sourceId) ? sourceId : null);
            performanceHeaderPm.setSourceName(!"null".equals(sourceName) ? sourceName : null);
            try {
                performanceHeaderPm.setCreateTime(!"null".equals(startTime) ? new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(startTime) : null);
                performanceHeaderPm.setUpdateTime(!"null".equals(endTime) ? new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(endTime) : null);
            } catch (ParseException e) {
                if (null != response)
                    response.setStatus(400);
                logger.error("ParseException[" + startTime + "]:" + e.getMessage());
                return "{'result':'error'}";
            }
            pa = performanceHeaderPmService.queryPerformanceHeaderPm(performanceHeaderPm, currentPage, pageSize);
            List<PerformanceHeaderPm> performanceHeaderPms = pa.getList();
            performanceHeaderPms.forEach(per -> {
                PerformanceBo pbo = new PerformanceBo();
                PerformanceInformationPm pe = new PerformanceInformationPm();
                pe.setEventId(per.getSourceId());
                List<PerformanceInformationPm> performanceInformationPms = performanceInformationPmService.queryPerformanceInformationPm(pe, 1, 100).getList();
                pbo.setPerformanceHeaderPm(per);
                performanceInformationPms.forEach(pi -> {
                    if (pi.getValue().equals("")) {
                        StringBuffer value1 = new StringBuffer();
                        performanceInformationPmService.queryPerformanceInformationPm(new PerformanceInformationPm(pi.getName()), 1, 100).getList()
                                .forEach(val -> value1.append(val.getValue()));
                        pi.setValue(value1.toString());
                    }
                });
                pbo.setPerformanceInformationPm(performanceInformationPms);
                list.add(pbo);
            });
        } else {
            pa = performanceHeaderPmService.queryPerformanceHeaderPm(null, currentPage, pageSize);
            List<PerformanceHeaderPm> p = pa != null ? pa.getList() : null;
            if (null != p && p.size() > 0)
                p.forEach(per -> {
                    PerformanceBo pbo = new PerformanceBo();
                    pbo.setPerformanceHeaderPm(per);
                    pbo.setPerformanceInformationPm(performanceInformationPmService.queryPerformanceInformationPm(new PerformanceInformationPm(per.getEventId()), 1, 100).getList());
                    list.add(pbo);
                });
        }
        Map<String, Object> map = new HashMap<>();
        map.put("performances", list);
        map.put("totalRecords", pa.getTotalRecords());
        omPerformance.setDateFormat(new SimpleDateFormat(Constant.DATE_FORMAT));
        return omPerformance.writeValueAsString(map);
    }

    /*@RequestMapping(value = {"/performance/genCsv/{eventId}"}, method = RequestMethod.GET, produces = "application/json")
    public String generateCsvFile(HttpServletResponse response, @PathVariable String[] eventId) throws JsonProcessingException {
        String csvFile = "csvFiles/vnf_performance_" + new SimpleDateFormat("yy-MM-ddHH:mm:ss").format(new Date()) + ".csv";
        List<PerformanceHeaderPm> performanceHeaderPms = performanceHeaderPmService.queryId(eventId);
        if (null == performanceHeaderPms || performanceHeaderPms.size() <= 0)
            return new ObjectMapper().writeValueAsString("selected eventId don't exist");
        List<String[]> csvData = new ArrayList<>();
        performanceHeaderPms.forEach(s -> {
            List<PerformanceInformationPm> information = performanceInformationPmService.queryPerformanceInformationPm(new PerformanceInformationPm(s.getEventId()), 1, 100).getList();
            String names = "";
            String values = "";
            if (0 < information.size() && null != information) {
                for (PerformanceInformationPm a : information) {
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

    @RequestMapping(value = {"/performancePm/diagram"}, method = RequestMethod.POST, produces = "application/json")
    public String generateDiagramPm(@RequestParam String sourceId, @RequestParam String startTime, @RequestParam String endTime, @RequestParam String nameParent, @RequestParam String format) {
        try {
            return omPerformance.writeValueAsString(diagramDate(sourceId, nameParent, startTime, endTime, format));
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(value = {"/performancePm/resourceIds"}, method = RequestMethod.GET)
    public String getSourceIdsPm() {
        List<String> sourceIds = new ArrayList<>();
        try {
            performanceHeaderPmService.queryAllSourceId().forEach(ph -> {
                if (!sourceIds.contains(ph))
                    sourceIds.add(ph);
            });
            return omPerformance.writeValueAsString(sourceIds);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @RequestMapping(value = {"/performancePm/names"}, method = RequestMethod.POST)
    public String getNamesPm(@RequestParam Object sourceId) {
        try {
            List<String> names = new ArrayList<>();
            performanceInformationPmService.queryDateBetween(sourceId.toString(), null, null, null).forEach(per -> {
                if (!names.contains(per.getName()) && per.getValue().matches("[0-9]*"))
                    if (Double.parseDouble(per.getValue()) > 0 && !per.getName().equals("Period"))
                        names.add(per.getName());

            });
            return omPerformance.writeValueAsString(names);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return "";
        }
    }

    private List<List<Long>> dateProcess(String sourceId, String name, long startTimeL, long endTimeL, long timeIteraPlusVal, long keyVal, long keyValIteraVal, String keyUnit) throws ParseException {
        List<List<Long>> dataList = new ArrayList<>();
        long tmpEndTimeL = startTimeL + timeIteraPlusVal;
        while (endTimeL >= tmpEndTimeL) {
            List<Map<String, String>> maps = performanceInformationPmService.queryMaxValueByBetweenDate(sourceId, name, sdf.format(new Date(startTimeL)), sdf.format(new Date(tmpEndTimeL)));
            maps.forEach(map -> {
                try {
                    List<Long> longList = new ArrayList<>();
                    if (map.get("Time") != null && !"".equals(map.get("Time")) && !"NULL".equals(map.get("Time"))) {
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

    private List<List<Long>> diagramDate(String sourceId, String name, String startTime, String endTime, String format) {
        try {
            long startTimel = sdf.parse(startTime).getTime();
            long endTimel = sdf.parse(endTime).getTime();
            if (format != null && !format.equals("auto")) {
                switch (format) {
                    case "minute":
                        return dateProcess(sourceId, name, startTimel, endTimel, 900000, 15, 15, "minute");
                    case "hour":
                        return dateProcess(sourceId, name, startTimel, endTimel, 3600000, 1, 1, "hour");
                    case "day":
                        return dateProcess(sourceId, name, startTimel, endTimel, 86400000, 1, 1, "day");
                    case "month":
                        return dateProcess(sourceId, name, startTimel, endTimel, 2592000000L, 1, 1, "month");
                    case "year":
                        return dateProcess(sourceId, name, startTimel, endTimel, 31536000000L, 1, 1, "year");
                }
            } else if (format != null && format.equals("auto")) {
                long minutes = (endTimel - startTimel) / (1000 * 60);
                long hours = minutes / 60;
                if (hours > 12) {
                    long days = hours / 24;
                    if (days > 3) {
                        long months = days / 31;
                        if (months > 2) {
                            return dateProcess(sourceId, name, startTimel, endTimel, 86400000, 1, 1, "day");
                        } else {
                            return dateProcess(sourceId, name, startTimel, endTimel, 2592000000L, 1, 1, "month");
                        }
                    } else {
                        return dateProcess(sourceId, name, startTimel, endTimel, 3600000, 1, 1, "hour");
                    }
                } else {
                    return dateProcess(sourceId, name, startTimel, endTimel, 900000, 15, 15, "minute");
                }
            }
        } catch (ParseException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

}
