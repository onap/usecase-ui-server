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

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import jakarta.annotation.Resource;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Configuration;
import jakarta.ws.rs.core.Response;

import com.google.common.base.Throwables;
import org.glassfish.jersey.client.ClientConfig;
import org.onap.usecaseui.server.bean.*;
import org.onap.usecaseui.server.constant.CommonConstant;
import org.onap.usecaseui.server.service.AlarmsHeaderService;
import org.onap.usecaseui.server.service.AlarmsInformationService;
import org.onap.usecaseui.server.service.PerformanceHeaderService;
import org.onap.usecaseui.server.service.PerformanceInformationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class DmaapSubscriber implements Runnable {

    private Logger logger = LoggerFactory.getLogger(DmaapSubscriber.class);

    private String url;
    private String alarmTopic;
    private String performanceTopic;
    private String consumerGroup;
    private String consumer;
    private int timeout;

    private boolean isActive = true;

    @Resource(name = "AlarmsHeaderService")
    private AlarmsHeaderService alarmsHeaderService;

    @Resource(name = "AlarmsInformationService")
    private AlarmsInformationService alarmsInformationService;

    @Resource(name = "PerformanceHeaderService")
    private PerformanceHeaderService performanceHeaderService;

    @Resource(name = "PerformanceInformationService")
    private PerformanceInformationService performanceInformationService;

    public void subscribe(String topic) {
        try {
            List<String> respList = getDMaaPData(topic);
            if (!UuiCommonUtil.isNotNullOrEmpty(respList)) {
                return;
            }
            ObjectMapper objMapper = new ObjectMapper();
            objMapper.setDateFormat(new SimpleDateFormat(CommonConstant.DATE_FORMAT));
            respList.forEach(rl -> {
                try {
                    Map<String, Object> eventMaps =
                            (Map<String, Object>) objMapper.readValue(rl, Map.class).get("event");
                    if (eventMaps.containsKey("measurementsForVfScalingFields")) {
                        performanceProcess(eventMaps);
                    } else if (eventMaps.containsKey("faultFields")) {
                        alarmProcess(eventMaps);
                    }
                } catch (IOException e) {
                    logger.error(
                            "exception occurred while performing DmaapSubcriber performanceProcess or alarmProcess. Details:{}",
                            Throwables.getStackTraceAsString(e));
                    logger.error(
                            "exception occurred while performing DmaapSubcriber performanceProcess or alarmProcess. Details:{}",
                            e.getMessage());
                    logger.error("exception from content:{}", rl);
                    logger.error("response content is :{}", respList);
                }
            });

        } catch (Exception e) {
            logger.error("getDMaaP Information failed :{}", Throwables.getStackTraceAsString(e));
            logger.error("getDMaaP Information failed :{}", e.getMessage());
        }
    }

    public List<String> getDMaaPData(String topic) {
        Client client = ClientBuilder.newClient((Configuration) new ClientConfig());
        WebTarget webTarget = client.target(url + "/" + topic + "/" + consumerGroup + "/" + consumer);
        Response response = webTarget.queryParam("timeout", timeout).request().get();
        return response.readEntity(List.class);
    }

    public void initConfig() {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("dmaap.properties");
        Properties p = new Properties();
        try {
            p.load(inputStream);
            this.url = p.getProperty("dmaap.url") + System.getenv("MR_ADDR");
            this.alarmTopic = p.getProperty("dmaap.alarmTopic");
            this.performanceTopic = p.getProperty("dmaap.performanceTopic");
            this.consumerGroup = p.getProperty("dmaap.consumerGroup");
            this.consumer = p.getProperty("dmaap.consumer");
            this.timeout = Integer.parseInt(p.getProperty("dmaap.timeout"));
        } catch (IOException e1) {
            logger.error("get configuration file arise error :{}",e1.getMessage());
        }
    }

    public void run() {
        try {
            initConfig();
            while (isActive) {
                Thread.sleep(1000);
                /*
                subscribe(alarmTopic);
                subscribe(performanceTopic);
            */}
        } catch (Exception e) {
            logger.error("subscribe raise error :{}",e.getCause());
        }
    }

    public void alarmProcess(Map<String, Object> eventMap) {
        AlarmsHeader alarm_header = new AlarmsHeader();
        alarm_header.setId(UuiCommonUtil.getUUID());
        List<AlarmsInformation> alarm_informations = new ArrayList<>();
        eventMap.forEach((ek1, ev1) -> {
            if (ek1.equals("commonEventHeader")) {
                ((Map<String, Object>) ev1).forEach((k2, v2) -> {
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
                        alarm_header.setStartEpochMicrosec(this.getTime(v2.toString()));
                    if (k2.equals("lastEpochMicrosec"))
                        alarm_header.setLastEpochMicroSec(this.getTime(v2.toString()));
                    if (k2.equals("sequence"))
                        alarm_header.setSequence(v2.toString());
                });
            } else if (ek1.equals("faultFields")) {
                ((Map<String, Object>) ev1).forEach((k3, v3) -> {
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
                            List<Map<String, Object>> m = (List<Map<String, Object>>) v3;
                            m.forEach(i -> {
                                alarm_informations
                                        .add(new AlarmsInformation(i.get("name").toString(), i.get("value").toString(),
                                                alarm_header.getSourceId(), alarm_header.getStartEpochMicrosec(),
                                                alarm_header.getLastEpochMicroSec(), alarm_header.getId()));
                            });
                        } catch (Exception e) {
                            logger.error("convert alarmAdditionalInformation error：{}",Throwables.getStackTraceAsString(e));
                            logger.error("convert alarmAdditionalInformation error：{}",e.getMessage());
                        }
                    }
                });
            }
        });
        if (alarm_header.getEventName() != null&&(alarm_header.getStartEpochMicrosec().length()>=13||alarm_header.getLastEpochMicroSec().length()>=13)){
        Long l = System.currentTimeMillis();

        Timestamp date_get = new Timestamp(l);
            if (alarm_header.getEventName().contains("Cleared")) {
                alarm_header.setStatus("close");
                alarmsHeaderService.updateAlarmsHeader2018("close", date_get, alarm_header.getStartEpochMicrosec(),
                        alarm_header.getLastEpochMicroSec(), alarm_header.getEventName().replace("Cleared", ""),
                        alarm_header.getReportingEntityName(), alarm_header.getSpecificProblem());
                alarm_informations.forEach(information -> alarmsInformationService.saveAlarmsInformation(information));

            } else {
            alarm_header.setStatus("active");
            alarmsHeaderService.saveAlarmsHeader(alarm_header);
            if(alarm_informations.isEmpty()) {
            alarm_informations.forEach(information ->
                    alarmsInformationService.saveAlarmsInformation(information));
            }
        }
    }
    }

    public void performanceProcess(Map<String, Object> maps) {
        PerformanceHeader performance_header = new PerformanceHeader.PerformanceHeaderBuilder().createPerformanceHeader();
        performance_header.setId(UuiCommonUtil.getUUID());
        List<PerformanceInformation> performance_informations = new ArrayList<>();
        maps.forEach((ek1, ev1) -> {
                    if (ek1.equals("commonEventHeader")) {
                        ((Map<String, Object>) ev1).forEach((k2, v2) -> {
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
                                performance_header.setStartEpochMicrosec(this.getTime(v2.toString()));
                            if (k2.equals("lastEpochMicrosec"))
                                performance_header.setLastEpochMicroSec(this.getTime(v2.toString()));
                            if (k2.equals("sequence"))
                                performance_header.setSequence(v2.toString());
                        });
                    } else if (ek1.equals("measurementsForVfScalingFields")) {
                        ((Map<String, Object>) ev1).forEach((k3, v3) -> {
                            if (k3.equals("measurementsForVfScalingVersion"))
                                performance_header.setMeasurementsForVfScalingVersion(v3.toString());
                            if (k3.equals("measurementInterval"))
                                performance_header.setMeasurementInterval(v3.toString());
                            if (k3.equals("additionalMeasurements")) {
                                try {
                                    List<Map<String, Object>> m = (List<Map<String, Object>>) v3;
                                    m.forEach(i -> {
                                        i.forEach( (k,v) -> {
                                    if (k.equals("arrayOfFields")) {
                                        List<Map<String, String>> arrayOfFields = (List<Map<String, String>>) v;
                                        arrayOfFields.forEach(fields -> {
                                            performance_informations.add(new PerformanceInformation(fields.get("name"),
                                                    fields.get("value"), performance_header.getSourceId(),
                                                    performance_header.getStartEpochMicrosec(),
                                                    performance_header.getLastEpochMicroSec(),
                                                    performance_header.getId()));
                                        });
                                    }
                                        });
                                    });
                                } catch (Exception e) {
                                    logger.error("convert performanceAdditionalInformation error：{}",Throwables.getStackTraceAsString(e));
                                    logger.error("convert performanceAdditionalInformation error：{}",e.getMessage());
                                }
                            }
                        });
                        if ((performance_header.getStartEpochMicrosec().length()>=13||performance_header.getLastEpochMicroSec().length()>=13)){//时间有效才会进行存储
                        	performanceHeaderService.savePerformanceHeader(performance_header);
                        	performance_informations.forEach(ai -> {
                        		performanceInformationService.savePerformanceInformation(ai);
                        	});
                        }
                    }
                });
    }
    
    public String getTime(String time){
    	String result=time;
    	if(time.length()>=13){
    		result=time.substring(0, 13);
    	}
    	return result;
    }
}