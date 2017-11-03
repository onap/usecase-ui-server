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
package org.onap.usecaseui.server.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.glassfish.jersey.client.ClientConfig;
import org.onap.usecaseui.server.bean.AlarmsHeader;
import org.onap.usecaseui.server.bean.AlarmsInformation;
import org.onap.usecaseui.server.bean.PerformanceHeader;
import org.onap.usecaseui.server.bean.PerformanceInformation;
import org.onap.usecaseui.server.service.AlarmsHeaderService;
import org.onap.usecaseui.server.service.AlarmsInformationService;
import org.onap.usecaseui.server.service.PerformanceHeaderService;
import org.onap.usecaseui.server.service.PerformanceInformationService;
import org.onap.usecaseui.server.service.impl.AlarmsHeaderServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.io.*;
import java.util.*;

@org.springframework.context.annotation.Configuration
@EnableAspectJAutoProxy
public class DmaapSubscriber implements Runnable {

    private Logger logger = LoggerFactory.getLogger(DmaapSubscriber.class);

    private String url;
    private String topic;
    private String consumerGroup;
    private String consumer;
    private int timeout ;

    private boolean isActive = true;

    @Resource(name = "AlarmsHeaderService")
    private AlarmsHeaderService alarmsHeaderService;

    @Resource(name = "AlarmsInformationService")
    private AlarmsInformationService alarmsInformationService;

    @Resource(name = "PerformanceHeaderService")
    private PerformanceHeaderService performanceHeaderService;

    @Resource(name = "PerformanceInformationService")
    private PerformanceInformationService performanceInformationService;

    private void subscribe(){
        String response = "";
        try{
            response = getDMaaPData();
            logger.info(response);
            try{
                ObjectMapper objMapper = new ObjectMapper();
                Map<String,Map<String,Map<String,Object>>> maps = objMapper.readValue(response,Map.class);
                AlarmsHeader alarm_header = new AlarmsHeader();
                List<AlarmsInformation> alarm_informations = new ArrayList<>();
                PerformanceHeader performance_header = new PerformanceHeader();
                List<PerformanceInformation> performance_infomations = new ArrayList<>();
                maps.forEach( (k,v) -> {
                    maps.get(k).forEach( (k1,v1) ->{
                        if (maps.get(k).get(k1).containsValue("fault") || maps.get(k).containsKey("faultFields")){
                            if (k1.equals("commonEventHeader")){
                                maps.get(k).get(k1).forEach( (k2,v2 ) ->{
                                    if (k2.equals("version"))
                                        alarm_header.setVersion(v2.toString());
                                    if (k2.equals("eventName"))
                                        alarm_header.setEventName(v2.toString());
                                    if (k2.equals("domain"))
                                        alarm_header.setDomain(v2.toString());
                                    if (k2.equals("eventId"))
                                        alarm_header.setEventId(v2.toString());
                                    if (k2.equals("eventType"))
                                        alarm_header.setEventType(v2.toString());
                                    if (k2.equals("nfcNamingCode"))
                                        alarm_header.setNfcNamingCode(v2.toString());
                                    if (k2.equals("nfNamingCode"))
                                        alarm_header.setNfNamingCode(v2.toString());
                                    if (k2.equals("sourceId"))
                                        alarm_header.setSourceId(v2.toString());
                                    if (k2.equals("sourceName"))
                                        alarm_header.setSourceName(v2.toString());
                                    if (k2.equals("reportingEntityId"))
                                        alarm_header.setReportingEntityId(v2.toString());
                                    if (k2.equals("reportingEntityName"))
                                        alarm_header.setReportingEntityName(v2.toString());
                                    if (k2.equals("priority"))
                                        alarm_header.setPriority(v2.toString());
                                    if (k2.equals("startEpochMicrosec"))
                                        alarm_header.setStartEpochMicrosec(v2.toString());
                                    if (k2.equals("lastEpochMicrosec"))
                                        alarm_header.setLastEpochMicroSec(v2.toString());
                                    if (k2.equals("sequence"))
                                        alarm_header.setSequence(v2.toString());
                                } );
                            }
                            else if (k1.equals("faultFields")) {
                                maps.get(k).get(k1).forEach((k3, v3) -> {
                                    if (k3.equals("faultFieldsVersion"))
                                        alarm_header.setFaultFieldsVersion(v3.toString());
                                    if (k3.equals("eventSeverity"))
                                        alarm_header.setEventServrity(v3.toString());
                                    if (k3.equals("eventSourceType"))
                                        alarm_header.setEventSourceType(v3.toString());
                                    if (k3.equals("eventCategory"))
                                        alarm_header.setEventCategory(v3.toString());
                                    if (k3.equals("alarmCondition"))
                                        alarm_header.setAlarmCondition(v3.toString());
                                    if (k3.equals("specificProblem"))
                                        alarm_header.setSpecificProblem(v3.toString());
                                    if (k3.equals("vfStatus"))
                                        alarm_header.setVfStatus(v3.toString());
                                    if (k3.equals("alarmInterfaceA"))
                                        alarm_header.setAlarmInterfaceA(v3.toString());
                                    if (k3.equals("alarmAdditionalInformation")) {
                                        try {
                                            List<Map<String,Object>> m = (List<Map<String, Object>>) v3;
                                            m.forEach( i -> {
                                                i.forEach( (k4,v4) -> {
                                                    alarm_informations.add(new AlarmsInformation(k4,v4.toString(),alarm_header.getEventName(),new Date(),new Date()));
                                                });
                                            } );
                                            alarm_header.setCreateTime(new Date());
                                            if (alarm_header.getEventName().contains("Cleared")){
                                                alarm_header.setStatus("3");
                                                alarmsHeaderService.saveAlarmsHeader(alarm_header);
                                                alarm_informations.forEach( information ->
                                                        alarmsInformationService.saveAlarmsInformation(information));
                                                AlarmsHeader header1 = new AlarmsHeader();
                                                header1.setEventName(alarm_header.getEventName().substring(0,alarm_header.getEventName().indexOf("Cleared")));
                                                List<AlarmsHeader> alarmsHeaders = alarmsHeaderService.queryAlarmsHeader(header1,1,10).getList();
                                                alarmsHeaders.forEach( alarms -> {
                                                    alarms.setStatus("2");
                                                    alarmsHeaderService.updateAlarmsHeader(alarms);
                                                } );
                                            }else{
                                                alarm_header.setStatus("1");
                                                logger.info(alarm_header.toString() +"");
                                                alarmsHeaderService.saveAlarmsHeader(alarm_header);
                                                alarm_informations.forEach( information ->
                                                        alarmsInformationService.saveAlarmsInformation(information));
                                            }
                                        } catch (Exception e) {
                                            logger.error("convert alarmAdditionalInformation error："+e.getMessage());
                                        }
                                    }
                                });

                            }
                        }else if(maps.get(k).get(k1).containsValue("measurementsForVfScaling") || maps.get(k).containsKey("measurementsForVfScalingFields")){
                            if (k1.equals("commonEventHeader"))
                                maps.get(k).get(k1).forEach( (k2,v2) ->{
                                    if (k2.equals("version"))
                                        performance_header.setVersion(v2.toString());
                                    if (k2.equals("eventName"))
                                        performance_header.setEventName(v2.toString());
                                    if (k2.equals("domain"))
                                        performance_header.setDomain(v2.toString());
                                    if (k2.equals("eventId"))
                                        performance_header.setEventId(v2.toString());
                                    if (k2.equals("eventType"))
                                        performance_header.setEventType(v2.toString());
                                    if (k2.equals("nfcNamingCode"))
                                        performance_header.setNfcNamingCode(v2.toString());
                                    if (k2.equals("nfNamingCode"))
                                        performance_header.setNfNamingCode(v2.toString());
                                    if (k2.equals("sourceId"))
                                        performance_header.setSourceId(v2.toString());
                                    if (k2.equals("sourceName"))
                                        performance_header.setSourceName(v2.toString());
                                    if (k2.equals("reportingEntityId"))
                                        performance_header.setReportingEntityId(v2.toString());
                                    if (k2.equals("reportingEntityName"))
                                        performance_header.setReportingEntityName(v2.toString());
                                    if (k2.equals("priority"))
                                        performance_header.setPriority(v2.toString());
                                    if (k2.equals("startEpochMicrosec"))
                                        performance_header.setStartEpochMicrosec(v2.toString());
                                    if (k2.equals("lastEpochMicrosec"))
                                        performance_header.setLastEpochMicroSec(v2.toString());
                                    if (k2.equals("sequence"))
                                        performance_header.setSequence(v2.toString());
                                } );
                            else if(k1.equals("measurementsForVfScalingFields")) {
                                maps.get(k).get(k1).forEach((k3, v3) -> {
                                    if (k3.equals("measurementsForVfScalingVersion"))
                                        performance_header.setMeasurementsForVfScalingVersion(v3.toString());
                                    if (k3.equals("measurementInterval"))
                                        performance_header.setMeasurementInterval(v3.toString());
                                    if (k3.equals("additionalMeasurements")){
                                        try {
                                            List<Map<String,Object>> m = (List<Map<String, Object>>) v3;
                                            m.forEach( i -> {
                                                i.forEach( (k4,v4) -> {
                                                    performance_infomations.add(new PerformanceInformation(k4,v4.toString(),performance_header.getEventName(),new Date(),new Date()));
                                                });
                                            } );
                                        } catch (Exception e) {
                                            logger.error("convert additionalMeasurements error："+e.getMessage());
                                        }

                                    }
                                });
                                performance_header.setCreateTime(new Date());
                                performance_header.setUpdateTime(new Date());
                                performanceHeaderService.savePerformanceHeader(performance_header);
                                performance_infomations.forEach( information ->
                                        performanceInformationService.savePerformanceInformation(information));
                            }
                        }

                    });
                });
            }catch (Exception e){
                e.printStackTrace();
                logger.error("dispose of Data failed:"+e.getMessage());
            }
        }catch (Exception e){
            logger.error("getDMaaP Information failed :"+e.getMessage());
        }
    }

    private String getDMaaPData(){
        Client client = ClientBuilder.newClient(new ClientConfig());
        WebTarget webTarget = client.target(url +"/" + topic +"/"+ consumerGroup +"/"+ consumer);
        Response response = webTarget.queryParam("timeout",timeout).request().get();
        return response.readEntity(String.class);
    }

    private void initConfig(){
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("dmaap.properties");
        Properties p = new Properties();
        try {
            p.load(inputStream);
            this.url = p.getProperty("dmaap.url");
            this.topic = p.getProperty("dmaap.topic");
            this.consumerGroup = p.getProperty("dmaap.consumerGroup");
            this.consumer = p.getProperty("dmaap.consumer");
            this.timeout = Integer.parseInt(p.getProperty("dmaap.timeout"));
        } catch (IOException e1) {
            logger.error("get configuration file arise error :"+e1.getMessage());
        }

    }

    public void run() {
        try{
            initConfig();
            while(isActive){
               subscribe();
            }
        }catch (Exception e){
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            logger.error("subscribe raise error :"+e.getCause());
        }
    }

    public void stopTask(){
        if (isActive)
            isActive = false;
    }

}
