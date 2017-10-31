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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.onap.usecaseui.server.bean.AlarmsHeader;
import org.onap.usecaseui.server.bean.AlarmsInformation;
import org.onap.usecaseui.server.bo.AlarmBo;
import org.onap.usecaseui.server.constant.Constant;
import org.onap.usecaseui.server.service.AlarmsHeaderService;
import org.onap.usecaseui.server.service.AlarmsInformationService;
import org.onap.usecaseui.server.util.CSVUtils;
import org.onap.usecaseui.server.util.DateUtils;
import org.onap.usecaseui.server.util.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


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

    private  String[] AlarmCSVHeaders = new String[]{"version",
            "eventName","domain","eventId","eventType","nfcNamingCode",
            "nfNamingCode","sourceId","sourceName","reportingEntityId",
            "reportingEntityName","priority","startEpochMicrosec","lastEpochMicroSec",
            "sequence","faultFieldsVersion","eventServrity","eventSourceType",
            "eventCategory","alarmCondition","specificProblem","vfStatus",
            "alarmInterfaceA","status",
            "createTime","updateTime","name","value"};

    private ObjectMapper omAlarm = new ObjectMapper();

    private List<String> last_eventId = new ArrayList<>();

    private List<String> last_status = new ArrayList<>();

    private List<String> last_vfStatus = new ArrayList<>();

    @RequestMapping(value = {"/usecase-ui"}, method = RequestMethod.GET)
    public ModelAndView index(){
        return new ModelAndView("index");
    }

    @RequestMapping(value = {"/alarm/{eventId}/{eventName}/{name}/{value}/{createTime}/{status}/{vfStatus}"})
    public String getAlarmDateTotal(@PathVariable String eventId,@PathVariable String eventName,
                                    @PathVariable String name,@PathVariable String value,
                                    @PathVariable String createTime,@PathVariable String status,
                                    @PathVariable String vfStatus) throws ParseException {

        return "";
    }

    @RequestMapping(value = {"/alarm/{currentPage}/{pageSize}",
            "/alarm/{currentPage}/{pageSize}/{eventId}/{eventName}/{name}/{value}/{createTime}/{status}/{vfStatus}"},
            method = RequestMethod.GET , produces = "application/json")
    public String getAlarmData(@PathVariable(required = false) String eventId,@PathVariable(required = false) String eventName,
                               @PathVariable(required = false) String name,@PathVariable(required = false) String value,
                               @PathVariable(required = false) String createTime,@PathVariable(required = false) String status,
                               @PathVariable(required = false) String vfStatus,
                               @PathVariable int currentPage, @PathVariable int pageSize) throws JsonProcessingException {
        List<AlarmsHeader> alarmsHeaders = null;
        List<AlarmBo> list = new ArrayList<>();
        Page pa = null;
        if (null != eventId || null != eventName || null != name || null != value || null != createTime
                || null != status || null != vfStatus  ){
            AlarmsHeader alarm = new AlarmsHeader();
            alarm.setEventId(!"null".equals(eventId)?eventId:null);
            alarm.setEventName(!"null".equals(eventName)?eventName:null);
            alarm.setStatus(!"null".equals(status)?status:null);
            alarm.setVfStatus(!"null".equals(vfStatus)?vfStatus:null);
            try {
                alarm.setCreateTime(!"null".equals(createTime)?DateUtils.stringToDate(createTime):null);
            } catch (ParseException e) {
                logger.error("Parse date error :"+e.getMessage());
            }
            pa = alarmsHeaderService.queryAlarmsHeader(alarm,currentPage,pageSize);
            alarmsHeaders = pa.getList();
            if (null != alarmsHeaders && alarmsHeaders.size() > 0) {
                alarmsHeaders.forEach(a ->{
                    AlarmBo abo = new AlarmBo();
                    abo.setAlarmsHeader(a);
                    AlarmsInformation information = new AlarmsInformation();
                    information.setName(!"null".equals(name)?name:null);
                    information.setValue(!"null".equals(value)?value:null);
                    information.setEventId(a.getEventId());
                    abo.setAlarmsInformation(alarmsInformationService.queryAlarmsInformation(information,1,100).getList());
                    list.add(abo);
                });
            }
        }else {
            pa = alarmsHeaderService.queryAlarmsHeader(null, currentPage, pageSize);
            alarmsHeaders = pa.getList();
            if (null != alarmsHeaders && alarmsHeaders.size() > 0) {
                alarmsHeaders.forEach(a -> {
                    AlarmBo abo = new AlarmBo();
                    abo.setAlarmsHeader(a);
                    abo.setAlarmsInformation(alarmsInformationService.queryAlarmsInformation(new AlarmsInformation(a.getEventId()),currentPage,pageSize).getList());
                    list.add(abo);
                });
            }
        }
        try {
            Map<String,Object> map = new HashMap<>();
            map.put("alarms",list);
            map.put("totalRecords",pa.getTotalRecords());
            omAlarm.setDateFormat(new SimpleDateFormat(Constant.DATE_FORMAT));
            return omAlarm.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            logger.debug("JsonProcessingException :"+e.getMessage());
            return omAlarm.writeValueAsString("failed");
        }
    }

    @RequestMapping(value = { "/alarm/genCsv/{eventId}" } , method = RequestMethod.GET , produces = "application/json")
    public String generateCsvFile(HttpServletResponse response, @PathVariable String[] eventId) throws JsonProcessingException {
        String csvFile = "csvFiles/vnf_alarm_"+new SimpleDateFormat("yy-MM-ddHH:mm:ss").format(new Date())+".csv";
        List<AlarmsHeader> alarmsHeaders = alarmsHeaderService.queryId(eventId);
        List<String[]> csvData = new ArrayList<>();
        try{
            alarmsHeaders.forEach(ala ->{
                List<AlarmsInformation> information = alarmsInformationService.queryAlarmsInformation(new AlarmsInformation(ala.getEventId()),1,100).getList();
                String names = "";
                String values = "";
                if (0 < information.size() && null != information){
                    for (AlarmsInformation a : information){
                        names += a.getName()+",";
                        values += a.getValue()+",";
                    }
                    names = names.substring(0,names.lastIndexOf(','));
                    values = values.substring(0,values.lastIndexOf(','));
                }
                csvData.add(new String[]{
                        ala.getVersion(),ala.getEventName(),ala.getDomain(),ala.getEventId(),ala.getEventType(),
                        ala.getNfcNamingCode(),ala.getNfNamingCode(),ala.getSourceId(),ala.getSourceName(),
                        ala.getReportingEntityId(),ala.getReportingEntityName(),ala.getPriority(),ala.getStartEpochMicrosec(),
                        ala.getLastEpochMicroSec(),ala.getSequence(),ala.getFaultFieldsVersion(),ala.getEventServrity(),
                        ala.getEventSourceType(),ala.getEventCategory(),ala.getAlarmCondition(),ala.getSpecificProblem(),
                        ala.getVfStatus(),ala.getAlarmInterfaceA(),ala.getStatus(),DateUtils.dateToString(ala.getCreateTime()),
                        DateUtils.dateToString(ala.getUpdateTime()),names,values
                });
            });
            CSVUtils.writeCsv(AlarmCSVHeaders,csvData,csvFile);
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        if (null != response){
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/csv");
            response.setHeader("Content-Disposition","attachment;filename="+csvFile);
            try(InputStream is = new FileInputStream(csvFile);
                OutputStream os = response.getOutputStream()){
                byte[] b = new byte[2048];
                int length;
                while ((length = is.read(b)) > 0) {
                    os.write(b, 0, length);
                }
                return omAlarm.writeValueAsString("success");
            }catch (IOException e){
                logger.error("download csv File error :"+e.getMessage());
                return omAlarm.writeValueAsString("failed");
            }
        }else
            return omAlarm.writeValueAsString("csvFile generate success,response is null,don't download to local");
    }


    @RequestMapping(value = { "/alarm/{eventId}/{status}/{type}" } , method = RequestMethod.PUT )
    public String updateStatus(HttpServletResponse response,@PathVariable String[] eventId,@PathVariable String[] status,@PathVariable String type) throws JsonProcessingException {
        List<AlarmsHeader> alarmsHeaders = alarmsHeaderService.queryId(eventId);
        last_eventId.clear();
        last_vfStatus.clear();
        last_status.clear();
        for (AlarmsHeader ala: alarmsHeaders) {
            last_eventId.add(ala.getEventId());
            last_status.add(ala.getStatus());
            last_vfStatus.add(ala.getVfStatus());
            if ("vf".equals(type))
                ala.setVfStatus(status[0]);
            else if ("many".equals(type)){
                ala.setStatus(status[0]);
                ala.setVfStatus(status[1]);
            }else {
                ala.setStatus(status[0]);
            }
            if ("0".equals(alarmsHeaderService.updateAlarmsHeader(ala))) {
                if (null != response)
                    response.setStatus(400);
                return omAlarm.writeValueAsString("failed");
            }
        }
        return omAlarm.writeValueAsString("success");
    }

    @RequestMapping(value = {"/alarm/revoke"} , method = RequestMethod.GET)
    public String revoke() throws JsonProcessingException {
        String result ;
        if (last_eventId.size() > 0 && last_vfStatus.size() > 0 && last_status.size() > 0){
            String[] eids = new String[last_eventId.size()];
            last_eventId.toArray(eids);
            List<AlarmsHeader> alarmsHeaders  = alarmsHeaderService.queryId(eids);
            for (int i = 0;i<alarmsHeaders.size();i++){
                AlarmsHeader ala =  alarmsHeaders.get(i);
                ala.setStatus(last_status.get(i));
                ala.setVfStatus(last_vfStatus.get(i));
                alarmsHeaderService.updateAlarmsHeader(ala);
            }
            last_eventId.clear();
            last_status.clear();
            last_vfStatus.clear();
            result = "revoke success";
        } else {
            result = "nothing can revoke";
        }
        return omAlarm.writeValueAsString(result);
    }


}
