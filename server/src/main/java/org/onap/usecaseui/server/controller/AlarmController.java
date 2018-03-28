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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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

   /* public AlarmsHeaderService getAlarmsHeaderService() {
        return alarmsHeaderService;
    }*/

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


    @RequestMapping("/alarm/getAllByDatetime/{eventId}/{eventServrity}/{startTime}/{endTime}")
    public String getAllByDatetime(@PathVariable(required = false) String eventId,@PathVariable(required = false) String eventServrity,@PathVariable(required = false) String startTime, @PathVariable(required = false) String endTime) throws ParseException, JsonProcessingException {
       //String startime_s = "2017-10-29";
        //String endtime_s = "2017-12-24";
        String startime_s = startTime;
        String endtime_s = endTime;
        String string ="";
        if(startime_s!=null && endtime_s!=null && !"".equals(startime_s) && !"".equals(endtime_s) ) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date startime = formatter.parse(startime_s);
            Date endtime = formatter.parse(endtime_s);
            DateUtils dateUtils = new DateUtils();
            List<Date> datelist = dateUtils.getBetweenDates(startime, endtime);
            StringBuffer dateB = new StringBuffer();
            StringBuffer allB = new StringBuffer();
            StringBuffer activeB = new StringBuffer();
            StringBuffer closeB = new StringBuffer();

            for (Date dates : datelist) {
                String date_s = formatter.format(dates);
                dateB.append(date_s).append(",");
                int aa = alarmsHeaderService.getAllByDatetime("active", eventId, eventServrity, date_s);
                activeB.append(aa + "").append(",");
                int bb = alarmsHeaderService.getAllByDatetime("close", eventId, eventServrity, date_s);
                closeB.append(bb + "").append(",");
                int cc = alarmsHeaderService.getAllByDatetime("0", eventId, eventServrity, date_s);
                allB.append(cc + "").append(",");

            }
            String dateBa = dateB.toString();
            String allBa = allB.toString();
            String activeBa = activeB.toString();
            String closeBa = closeB.toString();

            String[] dateArr = dateBa.substring(0, dateBa.length() - 1).split(",");
            String[] activeArr = activeBa.substring(0, activeBa.length() - 1).split(",");
            String[] closeArr = closeBa.substring(0, closeBa.length() - 1).split(",");
            String[] allArr = allBa.substring(0, activeBa.length() - 1).split(",");

            Map map = new HashMap();
            map.put("dateArr", dateArr);
            map.put("activeArr", activeArr);
            map.put("closeArr", closeArr);
            map.put("allArr", allArr);
            string = omAlarm.writeValueAsString(map);
        }

        return string;
    }


    @RequestMapping("/alarm/getAlarmsHeaderDetail/{id}")
    public String getAlarmsHeaderDetail(@PathVariable Integer id) throws JsonProcessingException {
        AlarmsHeader alarmsHeader= alarmsHeaderService.getAlarmsHeaderDetail(id);
        String eventId = alarmsHeader.getEventId();
        List<AlarmsInformation> list = alarmsInformationService.getAllAlarmsInformationByeventId(eventId);
        Map map = new HashMap();
        map.put("alarmsHeader",alarmsHeader);
        map.put("list",list);

        String string =omAlarm.writeValueAsString(map);
        return string;
    }


    @RequestMapping(value = {"/alarm/getAlarmDataByStatus/{status}","/alarm/getAlarmDataByStatus/{status}/{eventName}/{sourceName}/{eventServerity}/{reportingEntityName}/{createTime}/{endTime}"},method =RequestMethod.GET,produces = "application/json")
    public String getAlarmDataByStatus(@PathVariable String status, @PathVariable(required = false) String eventName,@PathVariable(required = false) String sourceName,@PathVariable(required = false) String eventServerity,@PathVariable(required = false) String reportingEntityName,@PathVariable(required = false) String createTime,@PathVariable(required = false) String endTime) throws JsonProcessingException {
        Map map = new HashMap();
        Date createTime_s=null;
        Date endTime_s=null;

       /* Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);*/



        try {
           createTime_s =(!"null".equals(createTime) ? new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(createTime) : null);
          endTime_s =(!"null".equals(endTime) ? new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(endTime) : null);
        } catch (ParseException e) {
            logger.error("Parse date error :" + e.getMessage());
        }
       int countClose = alarmsHeaderService.getAllCountByStatus("close");
       int countActive =  alarmsHeaderService.getAllCountByStatus("active");
       int countAll =countActive + countClose;
       Set<String> eventNameList = new HashSet();
       Set<String> sourceNameList = new HashSet<>();
        Set<String> reportingEntityNameList = new HashSet<>();
        Set<String> eventServerityList = new HashSet<>();
        Set<String> sourceIdList = new HashSet<>();


       List<AlarmsHeader> list = alarmsHeaderService.getAllByStatus(status,eventName,sourceName,eventServerity,reportingEntityName,createTime_s,endTime_s);
       AlarmsHeader alarmsHeader;
       for(int a=0;a<list.size();a++){
           alarmsHeader = list.get(a);
           eventNameList.add(alarmsHeader.getEventName());
           sourceNameList.add(alarmsHeader.getSourceName());
           reportingEntityNameList.add(alarmsHeader.getReportingEntityName());
           eventServerityList.add(alarmsHeader.getEventServrity());
           sourceIdList.add(alarmsHeader.getSourceId());

       }

       map.put("countClose",countClose);
        map.put("countActive",countActive);
        map.put("countAll",countAll);
        map.put("list",list);
        map.put("eventNameList",eventNameList);
        map.put("sourceNameList",sourceNameList);
        map.put("reportingEntityNameList",reportingEntityNameList);
        map.put("eventServerityList",eventServerityList);
        map.put("sourceIdList",sourceIdList);


        String string =omAlarm.writeValueAsString(map);

        return string;
    }



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
        List<AlarmsHeader> alarmsHeaders = null;
        List<AlarmBo> list = new ArrayList<>();
        Page pa = new Page();
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

            alarmsHeaders = pa.getList();
            if (null != alarmsHeaders && alarmsHeaders.size() > 0) {

            //if (null != pa) {
                alarmsHeaders = pa.getList();
                alarmsHeaders.forEach(a -> {
                    logger.info(a.toString());
                    AlarmBo abo = new AlarmBo();
                    if (!a.getStatus().equals("3")) {
                        abo.setAlarmsHeader(a);
                        AlarmsInformation information = new AlarmsInformation();
                        information.setEventId(a.getSourceId());
                        List<AlarmsInformation> informationList = alarmsInformationService.queryAlarmsInformation(information, 1, 100).getList();
                        abo.setAlarmsInformation(informationList);
                        list.add(abo);
                    }
                });
            }
        } else {
            pa = alarmsHeaderService.queryAlarmsHeader(null, currentPage, pageSize);
            alarmsHeaders = pa.getList();
            if (null != alarmsHeaders && alarmsHeaders.size() > 0) {
                alarmsHeaders.forEach(a -> {
                    AlarmBo abo = new AlarmBo();
                    if (!a.getStatus().equals("3")) {
                        abo.setAlarmsHeader(a);
                        abo.setAlarmsInformation(alarmsInformationService.queryAlarmsInformation(new AlarmsInformation(a.getEventId()), currentPage, pageSize).getList());
                        list.add(abo);
                    }

                });
            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("alarms", list);
        map.put("totalRecords", pa.getTotalRecords());
        omAlarm.setDateFormat(new SimpleDateFormat(Constant.DATE_FORMAT));
        return omAlarm.writeValueAsString(map);
    }

    /*public String getAlarmData(@PathVariable(required = false) String sourceId, @PathVariable(required = false) String sourceName,
                               @PathVariable(required = false) String priority, @PathVariable(required = false) String startTime,
                               @PathVariable(required = false) String endTime, @PathVariable(required = false) String vfStatus,
                               @PathVariable int currentPage, @PathVariable int pageSize) throws JsonProcessingException {
        logger.info("transfer getAlarmData Apis, " +
                        "Parameter all follows : [currentPage : {} , pageSize : {} , sourceId : {} , " +
                        "sourceName : {} , priority : {} , startTime :{} , endTime : {}  , vfStatus : {}]"
                , currentPage, pageSize, sourceId, sourceName, priority, startTime, endTime, vfStatus);
        List<AlarmsHeader> alarmsHeaders = null;
        List<AlarmBo> list = new ArrayList<>();
        Page pa = null;
        if (null != sourceId || null != sourceName || null != priority || null != startTime || null != endTime
                || null != vfStatus) {
            AlarmsHeader alarm = new AlarmsHeader();
            alarm.setSourceId(!"null".equals(sourceId) ? sourceId : null);
            alarm.setSourceName(!"null".equals(sourceName) ? sourceName : null);
            alarm.setStatus(!"null".equals(vfStatus) ? vfStatus : null);
            try {
                alarm.setCreateTime(!"null".equals(startTime) ? new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(startTime) : null);
                alarm.setUpdateTime(!"null".equals(endTime) ? new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(endTime) : null);
            } catch (ParseException e) {
                logger.error("Parse date error :" + e.getMessage());
            }
            pa = alarmsHeaderService.queryAlarmsHeader(alarm, currentPage, pageSize);

            alarmsHeaders = pa.getList();
            if (null != alarmsHeaders && alarmsHeaders.size() > 0) {
                alarmsHeaders.forEach(a -> {
                    logger.info(a.toString());
                    AlarmBo abo = new AlarmBo();
                    if (!a.getStatus().equals("close")) {
                        abo.setAlarmsHeader(a);
                        AlarmsInformation information = new AlarmsInformation();
                        information.setEventId(a.getSourceId());
                        List<AlarmsInformation> informationList = alarmsInformationService.queryAlarmsInformation(information, 1, 100).getList();
                        abo.setAlarmsInformation(informationList);
                        list.add(abo);
                    }
                });
            }
        } else {
            pa = alarmsHeaderService.queryAlarmsHeader(null, currentPage, pageSize);
            alarmsHeaders = pa.getList();
            if (null != alarmsHeaders && alarmsHeaders.size() > 0) {
                alarmsHeaders.forEach(a -> {
                    AlarmBo abo = new AlarmBo();
                    if (!a.getStatus().equals("close")) {
                        abo.setAlarmsHeader(a);
                        abo.setAlarmsInformation(alarmsInformationService.queryAlarmsInformation(new AlarmsInformation(a.getEventId()), currentPage, pageSize).getList());
                        list.add(abo);
                    }

                });
            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("alarms", list);
        map.put("totalRecords", pa.getTotalRecords());
        omAlarm.setDateFormat(new SimpleDateFormat(Constant.DATE_FORMAT));
        return omAlarm.writeValueAsString(map);
    }*/

    @RequestMapping(value = "/alarm/statusCount", method = RequestMethod.GET, produces = "application/json")
    public String getStatusCount() {
        List<String> statusCount = new ArrayList<>();
        try {
            statusCount.add(alarmsHeaderService.queryStatusCount("0"));
            statusCount.add(alarmsHeaderService.queryStatusCount("active"));
            statusCount.add(alarmsHeaderService.queryStatusCount("close"));
            return omAlarm.writeValueAsString(statusCount);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return null;
    }

    @RequestMapping(value = {"/topology/{serviceName}"}, method = RequestMethod.GET)
    public String getTopologyData(@PathVariable String serviceName){
        Map<String,Object> topologyMap = new HashMap<>();
        try {
            topologyMap.put("name",serviceName);
            /*List<Map<String,Object>> services = (List<Map<String, Object>>) getTopologyData().get("services");
            services.forEach( i -> {
                i.forEach((kk,vv) -> {
                  if (kk.equals("ServiceName"))
                      if (vv.equals(serviceName))
                          topologyMap.put("isAlarm",i.get("isAlarm"));
                });
            } );*/
            //List<Map<String,Object>> networkServices = (List<Map<String, Object>>) getTopologyData().get("networkServices");
            //List<Map<String,Object>> VNFS = (List<Map<String, Object>>) getTopologyData().get("VNFS");

            List<Map<String,Object>> networkServices = (List<Map<String, Object>>) getAllVNFS().get("networkServices");
            List<Map<String,Object>> VNFS = (List<Map<String, Object>>) getAllVNFS().get("VNFS");

            List<Map<String,Object>> children = new ArrayList<>();
            networkServices.forEach( i -> {
                Map<String,Object> childrenMap = new HashMap<>();
                i.forEach( (k,v) ->{
                    if (k.equals("parentService"))
                        if (v.equals(serviceName)){
                            childrenMap.put("name",i.get("nsName"));
                            List<Map<String,Object>> childrenList = new ArrayList<>();
                            VNFS.forEach( j -> {
                                Map<String,Object> childrenJMap = new HashMap<>();
                                j.forEach( (k1,v2) -> {
                                    if (k1.equals("parentNS"))
                                        if (v2.equals(i.get("nsName"))){

                                            childrenJMap.put("name",j.get("vnfName"));
                                            childrenJMap.put("isAlarm",j.get("isAlarm"));
                                        }
                                } );
                                if (childrenJMap.size() > 0 )
                                    childrenList.add(childrenJMap);
                            } );
                            if (childrenList.size() > 0){
                                childrenMap.put("children",childrenList);
                            }
                        }
                } );
                if (childrenMap.size() > 0){
                    children.add(childrenMap);
                }
            } );
            if (children.size() > 0){
                topologyMap.put("children",children);
            }
            return omAlarm.writeValueAsString(topologyMap);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(value = {"/topology/services"}, method = RequestMethod.GET)
    public String getTopologyServices(){
        try {
            //List<Map<String,Object>> services = (List<Map<String, Object>>) getTopologyData().get("services");
            List<Map<String,Object>> services = (List<Map<String, Object>>) getAllVNFS().get("services");
            services.forEach( i -> {
               i.forEach( (k,v) -> {
                   if (k.equals("ServiceName")){
                       AlarmsHeader alarmsHeader = new AlarmsHeader();
                       alarmsHeader.setSourceId(v.toString());
                       List<AlarmsHeader> alarmsHeaderList = alarmsHeaderService.queryAlarmsHeader(alarmsHeader,1,10).getList();
                       alarmsHeaderList.forEach(alarmsHeader1 -> {
                           if (alarmsHeader1.getStatus().equals("1")){
                               i.replace("isAlarm","true");
                           }
                       });
                   }
               } );
            } );
            return omAlarm.writeValueAsString(services);
        }catch (Exception e){
            logger.error(e.getMessage());
            return null;
        }
    }

    private Map<String,Object> getTopologyData() throws IOException {
        String data = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader("/home/uui/resources/topologyD_data.json"));
            String tmpStr = "";
            while ((tmpStr=br.readLine()) != null){
                data += tmpStr;
            }
            br.close();
            //System.out.println(data);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<String,Object> map = omAlarm.readValue(data, Map.class);
        return map;
    }

    public Map<String,Object>   getAllVNFS() throws IOException {

        String data="";
        try {
            String str=null;
            BufferedReader  br = new BufferedReader(new FileReader("/home/uui/resources/topologyD_data.json"));
            while ((str=br.readLine())!=null) {
                data += str;
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
            JSONObject jsonObject = (JSONObject) JSON.parseObject(data);
            JSONArray jsonArray = jsonObject.getJSONArray("VNFS");

            for(int a=0;a<jsonArray.size();a++){
                JSONObject jsonObject1 = jsonArray.getJSONObject(a);
                String vnfName = jsonObject1.getString("vnfName");

                Boolean name = false;
                name =   alarmsHeaderService.getStatusBySourceName(vnfName);

                jsonObject1.put("isAlarm",name);

                System.out.print("vnfName===="+vnfName+"name====="+name);
            }

        String jsonS = jsonObject.toJSONString();
        System.out.print("toJSONString===="+jsonS);

        Map<String,Object> map = omAlarm.readValue(jsonS, Map.class);
        return map;
    }

    @RequestMapping(value = {"/alarm/sourceId"}, method = RequestMethod.GET)
    public String getSourceId() throws JsonProcessingException {
        List<String> sourceIds = new ArrayList<>();
        alarmsHeaderService.queryAlarmsHeader(new AlarmsHeader(), 1, Integer.MAX_VALUE).getList().forEach(al -> {
            if (!al.getStatus().equals("3") &&
                    !sourceIds.contains(al.getSourceId()))
                sourceIds.add(al.getSourceId());
        });
        return omAlarm.writeValueAsString(sourceIds);
    }

    @RequestMapping(value = {"/alarm/diagram"}, method = RequestMethod.POST)
    public String genDiagram(@RequestParam String sourceId, @RequestParam String startTime, @RequestParam String endTime, @RequestParam String showMode) {
        try {
            return omAlarm.writeValueAsString(diagramDate(sourceId, startTime, endTime, showMode));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }
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
