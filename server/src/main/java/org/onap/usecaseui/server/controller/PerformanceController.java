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
import org.onap.usecaseui.server.bean.PerformanceHeaderPm;
import org.onap.usecaseui.server.bean.PerformanceHeaderVm;
import org.onap.usecaseui.server.bean.PerformanceInformation;
import org.onap.usecaseui.server.bo.PerformanceBo;
import org.onap.usecaseui.server.constant.Constant;
import org.onap.usecaseui.server.service.*;
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


    @Resource(name = "PerformanceHeaderPmService")
    private PerformanceHeaderPmService performanceHeaderPmService;

    @Resource(name = "PerformanceInformationPmService")
    private PerformanceInformationPmService performanceInformationPmService;

    @Resource(name = "PerformanceHeaderVmService")
    private PerformanceHeaderVmService performanceHeaderVmService;

    @Resource(name = "PerformanceInformationVmService")
    private PerformanceInformationVmService performanceInformationVmService;


   /* public void setPerformanceHeaderPmService(PerformanceHeaderPmService performanceHeaderPmService) {
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
    }*/

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


  /*  @RequestMapping("/performance/getAllByDatetimeBn/{eventId}/{startTime}/{endTime}")
    public String getAllByDatetimeBn(@PathVariable(required = false) String eventId,@PathVariable(required = false) String startTime, @PathVariable(required = false) String endTime) throws ParseException, JsonProcessingException {
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
           // StringBuffer closeB = new StringBuffer();

            for (Date dates : datelist) {
                String date_s = formatter.format(dates);
                dateB.append(date_s).append(",");
               // int aa = performanceHeaderService.getAllByDatetime("active", eventId,  date_s);
               // activeB.append(aa + "").append(",");
                //int bb = performanceHeaderService.getAllByDatetime("close", eventId,  date_s);
                //closeB.append(bb + "").append(",");
                int cc = performanceHeaderService.getAllByDatetime( eventId,  date_s);
                allB.append(cc + "").append(",");

            }
            List<PerformanceInformation> listAllEventId = performanceInformationService.getAllEventId();
            int listAllEventIdSize = listAllEventId.size();
            //Set<String> eventIdAll = new HashSet<>();
           // Set<String> envntServrity = new HashSet<>();
            //List list = new ArrayList();
           // Map<String,String> map = new HashMap<>();
            //PerformanceInformation perB;
            //String eId="";
            //String evendId="";

            String dateBa = dateB.toString();
            String allBa = allB.toString();
            //String activeBa = activeB.toString();
           // String closeBa = closeB.toString();

            String[] dateArr = dateBa.substring(0, dateBa.length() - 1).split(",");
            //String[] activeArr = activeBa.substring(0, activeBa.length() - 1).split(",");
            //String[] closeArr = closeBa.substring(0, closeBa.length() - 1).split(",");
            String[] allArr = allBa.substring(0, allBa.length() - 1).split(",");

            Map map = new HashMap();
            map.put("dateArr", dateArr);
            //map.put("eventIdAll", eventIdAll);
             //map.put("envntServrity", envntServrity);
            map.put("allArr", allArr);
            string = omPerformance.writeValueAsString(map);
        }

        return string;
    }

*/


 /*   @RequestMapping("/performance/getAllByDatetimeBnSearch/{eventId}")
    public String getAllByDatetimeBnSearch(@PathVariable(required = false) String eventId,@PathVariable(required = false) String startTime, @PathVariable(required = false) String endTime) throws ParseException, JsonProcessingException {
        String startime_s = "2017-10-29";
        String endtime_s = "2017-12-24";
        //eventId = "2017-11-15T06:30:00MmeFunction1101ZTHX1MMEGJM1W1";
        //String startime_s = startTime;
        //String endtime_s = endTime;
        String string ="";

            List<PerformanceInformation> listAllEventId = performanceInformationService.getAllEventId();
            int listAllEventIdSize = listAllEventId.size();
            //Set<String> eventIdAll = new HashSet<>();
            Set<String> envntServrity = new HashSet<>();
           // List list = new ArrayList();
            // Map<String,String> mapa = new HashMap<>();
            PerformanceInformation perB;
            String eId="";
            for (int a=0;a<listAllEventIdSize;a++){
                perB = listAllEventId.get(a);
                eId =perB.getEventId();
                //if(!"".equals(eId) && null!=eId){
                if(eventId.equals(eId)){
                   // mapa = new HashMap<>();
                    //eventIdAll.add(eId);
                    envntServrity.add(perB.getValue());
                    //mapa.put(eId+"_"+a,perB.getValue());
                   // list.add(mapa);
                }
            }



           // String dateBa = dateB.toString();
           // String allBa = allB.toString();

           // String[] dateArr = dateBa.substring(0, dateBa.length() - 1).split(",");

           // String[] allArr = allBa.substring(0, allBa.length() - 1).split(",");

            Map map = new HashMap();
          //  map.put("dateArr", dateArr);

           // map.put("eventIdAll", eventIdAll);
            map.put("envntServrity", envntServrity);
            //map.put("envntServrity", list);
         //   map.put("allArr", allArr);
            string = omPerformance.writeValueAsString(map);
       // }

        return string;
    }

*/




   /* @RequestMapping("/performance/getPerformanceHeaderBnDetail/{id}")
    public String getPerformanceHeaderDetail(@PathVariable Integer id) throws JsonProcessingException {
        PerformanceHeader performanceHeader= performanceHeaderService.getPerformanceHeaderDetail(id);
        String sourceId ="2017-11-15T06:30:00MmeFunction1101ZTHX1MMEGJM1W1_s";
        //String sourceId;
        if(null!=performanceHeader){
           sourceId = performanceHeader.getSourceId();
        }
        //List<PerformanceInformation> list =getAllPerformanceInformationByeventId(eventId);
        List<PerformanceInformation> list =new ArrayList<>();
        if(!"2017-11-15T06:30:00MmeFunction1101ZTHX1MMEGJM1W1_s".equals(sourceId)){
            list = performanceInformationService.getAllPerformanceInformationByeventId(sourceId);
        }else{
            PerformanceInformation performanceInformation_s = new PerformanceInformation();
            performanceInformation_s.setId(4);
        }

        Map map = new HashMap();
        map.put("performanceHeader",performanceHeader);
        map.put("list",list);

        String string =omPerformance.writeValueAsString(map);
        return string;
    }
*/
   /* @RequestMapping(value = {"/performance/getCounVmPmBn"})
    public String getCounVmPmBn() throws JsonProcessingException {

        int countPm = performanceHeaderPmService.getAllCountByEventType();
        int countVm =  performanceHeaderVmService.getAllCountByEventType();
        int countBn =  performanceHeaderService.getAllCountByEventType();
Map<String,Integer> map = new HashMap<String, Integer>();
        map.put("countPm",countPm);
        map.put("countVm",countVm);
        map.put("countBn",countBn);

        String string =omPerformance.writeValueAsString(map);

        return string;

    }
*/

/*

    @RequestMapping(value = {"/performance/getPerformanceDataByBn/{eventName}/{sourceName}/{reportingEntityName}/{createTime}/{endTime}"},method =RequestMethod.GET,produces = "application/json")
    public String getPerformanceDataByBn( @PathVariable(required = false) String eventName,@PathVariable(required = false) String sourceName,@PathVariable(required = false) String reportingEntityName,@PathVariable(required = false) String createTime,@PathVariable(required = false) String endTime) throws JsonProcessingException {
        Map map = new HashMap();
        Date createTime_s=null;
        Date endTime_s=null;




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


        Set<String> eventNameList = new HashSet();
        Set<String> sourceNameList = new HashSet<>();
        Set<String> reportingEntityNameList = new HashSet<>();

        List<PerformanceHeader> list = performanceHeaderService.getAllByEventType(eventName,sourceName,reportingEntityName,createTime_s,endTime_s);
        PerformanceHeader performanceHeader;
        for(int a=0;a<list.size();a++){
            performanceHeader = list.get(a);
            eventNameList.add(performanceHeader.getEventName());
            sourceNameList.add(performanceHeader.getSourceName());
            reportingEntityNameList.add(performanceHeader.getReportingEntityName());



        }


        //map.put("list",list);
        map.put("eventNameList",eventNameList);
        map.put("sourceNameList",sourceNameList);
        map.put("reportingEntityNameList",reportingEntityNameList);

       // map.put("sourceIdList",sourceIdList);


        String string =omPerformance.writeValueAsString(map);

        return string;
    }

    @RequestMapping(value = {"/performance/getPerformanceListByBn/","/performance/getPerformanceListByBn/{eventName}/{sourceName}/{reportingEntityName}/{createTime}/{endTime}"},method =RequestMethod.GET,produces = "application/json")
    public String getPerformanceListByBn( @PathVariable(required = false) String eventName,@PathVariable(required = false) String sourceName,@PathVariable(required = false) String reportingEntityName,@PathVariable(required = false) String createTime,@PathVariable(required = false) String endTime) throws JsonProcessingException {
        Map map = new HashMap();
        Date createTime_s=null;
        Date endTime_s=null;




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
        Set<String> eventNameList = new HashSet();
        Set<String> sourceNameList = new HashSet<>();
        Set<String> reportingEntityNameList = new HashSet<>();

        Set<String> sourceIdList = new HashSet<>();


        List<PerformanceHeader> list = performanceHeaderService.getAllByEventType(eventName,sourceName,reportingEntityName,createTime_s,endTime_s);


        map.put("list",list);

        String string =omPerformance.writeValueAsString(map);

        return string;
    }



*/



















    @RequestMapping(value = {"/performance/{currentPage}/{pageSize}", "/performance/{currentPage}/{pageSize}/{sourceId}/{sourceName}/{priority}/{startTime}/{endTime}"}, method = RequestMethod.GET, produces = "application/json")
    public String getPerformanceData(HttpServletResponse response, @PathVariable int currentPage,
                                     @PathVariable int pageSize, @PathVariable(required = false) String sourceId,
                                     @PathVariable(required = false) String sourceName, @PathVariable(required = false) String priority,
                                     @PathVariable(required = false) String startTime, @PathVariable(required = false) String endTime) throws JsonProcessingException, ParseException {
        logger.info("transfer getPerformanceData Apis, " +
                        "Parameter all follows : [currentPage : {} , pageSize : {} , sourceId : {} , " +
                        "sourceName : {} , priority : {} , startTime :{} , endTime : {} ]"
                , currentPage, pageSize, sourceId, sourceName, priority, startTime, endTime);
        List<PerformanceBo> list = new ArrayList<>();
        List<PerformanceHeader> performanceHeaderList = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
            if(pa==null) {

                PerformanceHeader performanceHeader_s = new PerformanceHeader();
                //SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String createtime="2017-11-15 06:30:00";
                String upatetime="2017-11-15 14:46:09";
                performanceHeader_s.setSourceName("101ZTHX1EPO1NK7E0Z2");
                performanceHeader_s.setSourceId("1101ZTHX1EPO1NK7E0Z21");
                performanceHeader_s.setEventName("Mfvs_MMEEthernetPort");
                performanceHeader_s.setEventId("2017-11-15T06:30:00EthernetPort1101ZTHX1EPO1NK7E0Z2");
                performanceHeader_s.setPriority("Normal");
                performanceHeader_s.setCreateTime(formatter.parse(createtime));
                performanceHeader_s.setUpdateTime(formatter.parse(upatetime));
                performanceHeader_s.setId(5);
                performanceHeaderList.add(performanceHeader_s);

            }else{
                performanceHeaderList = pa.getList();
            }


            if (null != performanceHeaderList && performanceHeaderList.size() > 0) {
                PerformanceHeader per;
                for(int c=0;c<performanceHeaderList.size();c++){
                    per = performanceHeaderList.get(c);

                //performanceHeaderList.forEach(per -> {
                    PerformanceBo pbo = new PerformanceBo();
                    PerformanceInformation pe = new PerformanceInformation();
                    pe.setEventId(per.getSourceId());
                    List<PerformanceInformation> performanceInformations =new ArrayList<>();
                    if("1101ZTHX1EPO1NK7E0Z21".equals(per.getSourceId())){
                        PerformanceInformation pera = new PerformanceInformation();
                        pera.setValue("0");
                        pera.setId(6);
                        pera.setName("HO.AttOutInterMme");
                        pera.setEventId("1101ZTHX1MMEGJM1W1");
                        String createtime="2017-11-15 06:30:00";
                        String updatetime="2017-11-15 14:45:10";
                        pera.setCreateTime(formatter.parse(createtime));
                        pera.setUpdateTime(formatter.parse(updatetime));
                        performanceInformations.add(pera);



                    }else{
                      performanceInformations = performanceInformationService.queryPerformanceInformation(pe, 1, 100).getList();

                    }
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

                }
            }

        } else {
            pa = performanceHeaderService.queryPerformanceHeader(null, currentPage, pageSize);
            if (pa == null) {
                //List lists = new ArrayList();
                PerformanceHeader performanceHeader = new PerformanceHeader();
                //SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String createtime="2017-11-15 06:30:00";
                String upatetime="2017-11-15 14:46:09";
                performanceHeader.setSourceName("101ZTHX1EPO1NK7E0Z2");
                performanceHeader.setSourceId("1101ZTHX1EPO1NK7E0Z2");
                performanceHeader.setEventName("Mfvs_MMEEthernetPort");
                performanceHeader.setEventId("2017-11-15T06:30:00EthernetPort1101ZTHX1EPO1NK7E0Z2");
                performanceHeader.setPriority("Normal");
                performanceHeader.setCreateTime(formatter.parse(createtime));
                performanceHeader.setUpdateTime(formatter.parse(upatetime));
                performanceHeader.setId(5);
                performanceHeaderList.add(performanceHeader);
                //lists.add(performanceHeader_s);
               // pa.setList(lists);
                pa = new Page();
                pa.setPageNo(1);
                pa.setPageSize(12);
                pa.setTotalRecords(1);
                pa.setList(performanceHeaderList);



            }

            //performanceHeaders = pa.getList();
            //if (null != performanceHeaders && performanceHeaders.size() > 0) {
            //list = pa.getList();

                List<PerformanceHeader> p = pa != null ? pa.getList() : null;
                if (null != p && p.size() > 0)
                    p.forEach(per -> {
                        PerformanceBo pbo = new PerformanceBo();
                        pbo.setPerformanceHeader(per);
                        pbo.setPerformanceInformation(performanceInformationService.queryPerformanceInformation(new PerformanceInformation(per.getEventId()), 1, 100).getList());
                        list.add(pbo);
                    });

        }
        Map<String, Object> map = new HashMap<>();
        map.put("performances", list);
        map.put("totalRecords", pa==null?0:pa.getTotalRecords());
        omPerformance.setDateFormat(new SimpleDateFormat(Constant.DATE_FORMAT));
        return omPerformance.writeValueAsString(map);
    }



    @RequestMapping(value = {"/performance/diagram"}, method = RequestMethod.POST, produces = "application/json")
    public String generateDiagram(@RequestParam String sourceId, @RequestParam String startTime, @RequestParam String endTime, @RequestParam String nameParent, @RequestParam String format) throws JsonProcessingException {
        //try {
            List<List<Long>> list =null;
            //if(sourceId!="") {
              list = diagramDate(sourceId, nameParent, startTime, endTime, format);
                return omPerformance.writeValueAsString(list);
           // }


            //return omPerformance.writeValueAsString(diagramDate(sourceId, nameParent, startTime, endTime, format));
      /*  } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return null;
        }*/
       // return null;
    }

    @RequestMapping(value = {"/performance/resourceIds"}, method = RequestMethod.GET)
    public String getSourceIds() throws JsonProcessingException {
        List<String> sourceIds = new ArrayList<>();

            /*performanceHeaderService.queryAllSourceId().forEach(ph -> {
                   if (!sourceIds.contains(ph))
                    sourceIds.add(ph);
            });*/
          List list=  performanceHeaderService.queryAllSourceId();
            PerformanceHeader performanceHeader;
            if(list.size()==0){
                performanceHeader = new PerformanceHeader();
                performanceHeader.setId(1);
                performanceHeader.setSourceId("aaaa");
                list.add(performanceHeader);

            }
          for(int a=0;a<list.size();a++){

              performanceHeader  = (PerformanceHeader)list.get(a);
              String sourceid = performanceHeader.getSourceId();
              if (!sourceIds.contains(sourceid)) {
                  sourceIds.add(sourceid);
              }
          }

            return omPerformance.writeValueAsString(sourceIds);

    }

    @RequestMapping(value = {"/performance/names"}, method = RequestMethod.POST)
    public String getNames(@RequestParam Object sourceId) {
        try {
            List<String> names = new ArrayList<>();
            //String sourceId_s = sourceId.toString();

           //performanceInformationService.queryDateBetween(sourceId.toString(), null, null, null).forEach(per -> {
            List<PerformanceInformation> list =performanceInformationService.queryDateBetween(sourceId.toString(), null, null, null);
            PerformanceInformation per;
            if(list.size()==0){
                PerformanceInformation perf= new PerformanceInformation();
                perf.setEventId("1101ZTHX1MMEGJM1W1");
                perf.setValue("0");
                perf.setName("DNS.AttDnsQuery");
                perf.setId(1);
                list.add(perf);

            }
            for(int a=0;a<list.size();a++) {
                per = (PerformanceInformation)list.get(a);
                if (null == per) {
                    per.setName("MM.AttEpsAttach");
                    per.setValue("0");
                }
                if (!names.contains(per.getName()) && per.getValue().matches("[0-9]*"))
                    if (Double.parseDouble(per.getValue()) > 0 && !per.getName().equals("Period"))
                        names.add(per.getName());

            }
           // });
            return omPerformance.writeValueAsString(names);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return "";
        }
    }

    private List<List<Long>> dateProcess(String sourceId, String name, long startTimeL, long endTimeL, long timeIteraPlusVal, long keyVal, long keyValIteraVal, String keyUnit) throws ParseException {
        List<List<Long>> dataList = new ArrayList<>();
        long tmpEndTimeL = startTimeL + timeIteraPlusVal;
        Date datea_a = new Date(startTimeL);
        Date dateb_a = new Date(tmpEndTimeL);
        String datea = sdf.format(datea_a);
        String dateb = sdf.format(dateb_a);
        while (endTimeL >= tmpEndTimeL) {
            List<Map<String, String>> maps = performanceInformationService.queryMaxValueByBetweenDate(sourceId, name, datea, dateb);

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
