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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.onap.usecaseui.server.bean.AlarmsHeader;
import org.onap.usecaseui.server.bean.AlarmsInformation;
import org.onap.usecaseui.server.service.AlarmsHeaderService;
import org.onap.usecaseui.server.service.AlarmsInformationService;
import org.onap.usecaseui.server.util.CSVUtils;
import org.onap.usecaseui.server.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlarmControllerTest {

    @Autowired
    AlarmController alarmController;

    @Resource(name = "AlarmsHeaderService")
    AlarmsHeaderService alarmsHeaderService;

    @Resource(name = "AlarmsInformationService")
    AlarmsInformationService alarmsInformationService;

    @Test
    public void getDataNotParam(){
       System.out.println(alarmController.getAlarmData(null,null,null,null,null,1,1100));
    }

    @Test
    public void getDataCarryParam(){
        System.out.println(alarmController.getAlarmData("110","a","drop","down","1506331166000",1,1100));
    }

    @Test
    public void genCsvFile(){
        String[] eventId = new String[]{"110"};
        String csvFile = "csvFiles/vnf_alarm_"+new SimpleDateFormat("yy-MM-ddHH:mm:ss").format(new Date())+".csv";
        String[] headers = new String[]{"version",
                "eventName","domain","eventId","eventType","nfcNamingCode",
                "nfNamingCode","sourceId","sourceName","reportingEntityId",
                "reportingEntityName","priority","startEpochMicrosec","lastEpochMicroSec",
                "sequence","faultFieldsVersion","eventServrity","eventSourceType",
                "eventCategory","alarmCondition","specificProblem","vfStatus",
                "alarmInterfaceA","status",
                "createTime","updateTime","name","value"};
        List<AlarmsHeader> alarmsHeaders = alarmsHeaderService.queryId(eventId);
        List<String[]> csvData = new ArrayList<>();
        alarmsHeaders.forEach(ala ->{
            List<AlarmsInformation> information = alarmsInformationService.queryAlarmsInformation(new AlarmsInformation(ala.getEventId()),1,100).getList();
            String names = new String();
            String values = new String();
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
                    ala.getVfStatus(),ala.getAlarmInterfaceA(),ala.getStatus(), DateUtils.dateToString(ala.getCreateTime()),
                    DateUtils.dateToString(ala.getUpdateTime()),names,values
            });
        });
        CSVUtils.writeCsv(headers,csvData,csvFile);
    }


}
