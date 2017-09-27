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
import org.onap.usecaseui.server.service.PerformanceHeaderService;
import org.onap.usecaseui.server.service.PerformanceInformationService;
import org.onap.usecaseui.server.util.CSVUtils;
import org.onap.usecaseui.server.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Controller
@Configuration
@EnableAspectJAutoProxy
public class PerformanceController {

    @Resource(name = "PerformanceHeaderService")
    private PerformanceHeaderService performanceHeaderService;

    @Resource(name = "PerformanceInformationService")
    private PerformanceInformationService performanceInformationService;

    private Logger logger = LoggerFactory.getLogger(PerformanceController.class);

    @RequestMapping(value = {"/usecaseui-server/performance"},method = RequestMethod.GET, produces = "application/json")
    public String getPerformanceData(HttpServletRequest request, @RequestBody PerformanceBo performanceBo) throws JsonProcessingException {
        List<PerformanceHeader> performanceHeaders = performanceHeaderService.queryPerformanceHeader(performanceBo.getPerformanceHeader(),performanceBo.getCurrentPage(),performanceBo.getPageSize()).getList();
        List<PerformanceInformation> performanceInformations = performanceInformationService.queryPerformanceInformation(performanceBo.getPerformanceInformation(),performanceBo.getCurrentPage(),performanceBo.getPageSize()).getList();
        Map<String,Object> maps = new HashMap<>();
        if (null != performanceHeaders && performanceHeaders.size() > 0)
            maps.put("performance_header",performanceHeaders);
        if (null != performanceInformations && performanceInformations.size() > 0)
            maps.put("performance_information",performanceInformations);
        return new ObjectMapper().writeValueAsString(maps);
    }

    @RequestMapping(value = {"/usecaseui-server/performance/genCsv/{eventId}"}, method = RequestMethod.GET, produces = "application/json")
    public String generateCsvFile(HttpServletResponse response, @PathVariable String[] eventId){
        String csvFile = "vnf_performance_"+new SimpleDateFormat("yy-MM-ddHH:mm:ss").format(new Date())+".csv";
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/csv");
        response.setHeader("Content-Disposition","attachment;filename="+csvFile);
        String[] headers = new String[]{"version",
                "eventName","domain","eventId","eventType","nfcNamingCode",
                "nfNamingCode","sourceId","sourceName","reportingEntityId",
                "reportingEntityName","priority","startEpochMicrosec","lastEpochMicroSec",
                "sequence","measurementsForVfScalingVersion","measurementInterval",
                "createTime","updateTime","value","name"};
        List<PerformanceHeader> performanceHeaders = performanceHeaderService.queryId(eventId);
        List<String[]> csvData = new ArrayList<>();
        performanceHeaders.forEach(s ->{
            List<PerformanceInformation> information = performanceInformationService.queryPerformanceInformation(new PerformanceInformation(s.getEventId()),1,100).getList();
            String names ="";
            String values ="";
            for (PerformanceInformation p : information){
                names += p.getName()+",";
                values += p.getValue()+",";
            }
            names = names.substring(0,names.lastIndexOf(','));
            values = values.substring(0,values.lastIndexOf(','));
            csvData.add(new String[]{
                s.getVersion(),s.getEventName(),s.getDomain(),s.getEventId(),s.getEventType(),s.getNfcNamingCode(),s.getNfNamingCode(),
                    s.getSourceId(),s.getSourceName(),s.getReportingEntityId(),s.getReportingEntityName(),s.getPriority(),
                    s.getStartEpochMicrosec(),s.getLastEpochMicroSec(),s.getSequence(),s.getMeasurementsForVfScalingVersion(),
                    s.getMeasurementInterval(),DateUtils.dateToString(s.getCreateTime()),DateUtils.dateToString(s.getUpdateTime()),
                    names,values
            });

        });
        CSVUtils.writeCsv(headers,csvData,csvFile);
        try(InputStream is = new FileInputStream(csvFile);
            OutputStream os = response.getOutputStream()){
            byte[] b = new byte[2048];
            int length;
            while ((length = is.read(b)) > 0) {
                os.write(b, 0, length);
            }
            return "{'result':'success'}";
        }catch (IOException e){
            logger.error("download csv File error :"+e.getMessage());
            return "{'result':'failed'}";
        }
    }

    @RequestMapping(value = {"/usecaseui-server/performance/genDiaCsv"}, method = RequestMethod.POST, produces = "application/json")
    public String generateDiaCsvFile(HttpServletResponse response,@RequestBody Map<String,String> p) throws ParseException {
        String csvFileName = "csvFiles/"+p.get("name")+"_"+new SimpleDateFormat("yy-MM-ddHH:mm:ss").format(new Date())+".csv";
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/csv");
        String[] headers = new String[]{"eventId","name","value","createTime","updateTime"};
        List<String[]> csvDatas = new ArrayList<>();
        if (null != p){
            for (int i =0;i<p.size();i++){
                csvDatas.add(new String[]{p.get("eventID"),p.get("name"),p.get("value"),p.get("createTime"),p.get("updateTime")});
            }
        }
        CSVUtils.writeCsv(headers,csvDatas,csvFileName);
        response.setHeader("Content-Disposition","attachment;filename="+csvFileName+"");
        try(InputStream is = new FileInputStream(csvFileName);
            OutputStream os = response.getOutputStream()){
            byte[] b = new byte[2048];
            int length;
            while ((length = is.read(b)) > 0) {
                os.write(b, 0, length);
            }
            return "{'result':'success'}";
        }catch (IOException e){
            logger.error("download csv File error :"+e.getMessage());
            return "{'result':'failed'}";
        }
    }

    @ResponseBody
    @RequestMapping(value = {"/usecaseui-server/performance/{unit}/{eventId}"}, method = RequestMethod.GET, produces = "application/json")
    public String generateDiagram(HttpServletRequest request,@PathVariable String unit,@PathVariable String eventId) throws ParseException, JsonProcessingException {
        String revDate = "";
        switch (unit){
            case "hour":
                revDate = DateUtils.initDate(new Date(),1,1,1,-1,0,0);
                break;
            case "day":
                revDate = DateUtils.initDate(new Date(),1,1,-1,0,0,0);
                break;
            case "month":
                revDate = DateUtils.initDate(new Date(),1,-1,0,0,0,0);
                break;
            case "year":
                revDate = DateUtils.initDate(new Date(),-1,0,0,0,0,0);
                break;
        }
        PerformanceInformation performanceInformation = new PerformanceInformation();
        performanceInformation.setEventId(eventId);
        performanceInformation.setCreateTime(new SimpleDateFormat().parse(revDate));
        List<PerformanceInformation> informations = performanceInformationService.queryPerformanceInformation(performanceInformation,1,100).getList();
        return new ObjectMapper().writeValueAsString(informations);
    }


}
