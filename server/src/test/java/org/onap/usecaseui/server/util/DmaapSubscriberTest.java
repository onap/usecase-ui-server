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
package org.onap.usecaseui.server.util; 

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 
* DmaapSubscriber Tester. 
* 
* @author <Authors name> 
* @since <pre>���� 4, 2018</pre> 
* @version 1.0 
*/ 
public class DmaapSubscriberTest {
    DmaapSubscriber dm =null;
    String topic = "alarmTopic";

@Before
public void before() throws Exception {
    dm = new DmaapSubscriber();
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: subscribe(String topic) 
* 
*/ 
@Test
public void testSubscribe() throws Exception { 
//TODO: Test goes here...
    String topic = "alarmTopic";
    try {
        Method method = dm.getClass().getDeclaredMethod("subscribe",String.class);
        method.setAccessible(true);
        method.invoke(dm,topic);
    } catch(NoSuchMethodException e) {
    } catch(IllegalAccessException e) {
    } catch(InvocationTargetException e) {
    }

}

    @Test
    public void testSubscribe_a() throws Exception {
//TODO: Test goes here...
        String topic = "alarmTopic";
       // Assert.assertNotNull(dm.subscribe(topic));
        try {
            Method method = dm.getClass().getDeclaredMethod("subscribe",String.class);
            method.setAccessible(true);
            method.invoke(dm,topic);
        } catch(NoSuchMethodException e) {
        } catch(IllegalAccessException e) {
        } catch(InvocationTargetException e) {
        }

    }

    /**
* 
* Method: run() 
* 
*/ 
/*@Test
public void testRun() throws Exception { 
//TODO: Test goes here...
    try {
        Method method = dm.getClass().getDeclaredMethod("run");
        method.setAccessible(false);
        method.invoke(dm,null);
    } catch(NoSuchMethodException e) {
    } catch(IllegalAccessException e) {
    } catch(InvocationTargetException e) {
    }

} */


/** 
* 
* Method: getDMaaPData(String topic) 
* 
*/ 
@Test
public void testGetDMaaPData() throws Exception { 
//TODO: Test goes here... 
String topic="alarmTopic";
    String url="http://172.30.3.45:3904";
    String alarmTopic="events/unauthenticated.SEC_FAULT_OUTPUT";
    String performanceTopic="events/unauthenticated.SEC_MEASUREMENT_OUTPUT";
    String consumerGroup="mingzhan";
    String consumer="mingzhan";
    int timeout=1000;
try { 
   Method method = dm.getClass().getDeclaredMethod("getDMaaPData", String.class);
   method.setAccessible(true); 
   method.invoke(dm, topic);
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) {
} 

} 

/** 
* 
* Method: initConfig() 
* 
*/ 
@Test
public void testInitConfig() throws Exception { 
//TODO: Test goes here... 

try { 
   Method method = dm.getClass().getDeclaredMethod("initConfig");
   method.setAccessible(true); 
   method.invoke(dm, null);
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 

} 

/** 
* 
* Method: alarmProcess(Map<String, Object> eventMap) 
* 
*/ 
@Test
public void testAlarmProcess() throws Exception { 
//TODO: Test goes here...
    List<Map<String,Object>> lista = new ArrayList<>();
    Map mapa = new HashMap();
    mapa.put("name","name_s");
    mapa.put("value","value_s");
    mapa.put("createTime","2017-11-15 06:30:00");
    lista.add(mapa);


    Map<String, Object> kv3f = new HashMap<>();
    kv3f.put("faultFieldsVersion","faultFieldsVersion");
    kv3f.put("eventSeverity","eventSeverity");
    kv3f.put("eventSourceType","eventSourceType");
    kv3f.put("eventCategory","eventCategory");
    kv3f.put("alarmCondition","alarmCondition");
    kv3f.put("specificProblem","specificProblem");
    kv3f.put("vfStatus","vfStatus");
    kv3f.put("alarmInterfaceA","alarmInterfaceA");
    kv3f.put("alarmAdditionalInformation",lista);




    Map<String, Object> kv3c = new HashMap<>();
    kv3c.put("version","version");
    kv3c.put("eventName","active");//还有
    kv3c.put("domain","domain");
    kv3c.put("eventId","eventId");
    kv3c.put("eventType","eventType");
    kv3c.put("nfcNamingCode","nfcNamingCode");
    kv3c.put("nfNamingCode","nfNamingCode");
    kv3c.put("sourceId","sourceId");
    kv3c.put("sourceName","sourceName");
    kv3c.put("reportingEntityId","reportingEntityId");

    kv3c.put("reportingEntityName","reportingEntityName");
    kv3c.put("priority","priority");
    kv3c.put("startEpochMicrosec","startEpochMicrosec");
    kv3c.put("lastEpochMicrosec","lastEpochMicrosec");
    kv3c.put("sequence","sequence");
















    Map<String, Object> eventMap = new HashMap<>();
    eventMap.put("commonEventHeader",kv3c);
    eventMap.put("faultFields",kv3f);

try { 
   Method method = dm.getClass().getDeclaredMethod("alarmProcess", Map.class);//Map<String,.class
   method.setAccessible(true); 
   method.invoke(dm, eventMap);
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 

}



    @Test
    public void testAlarmProcess_Cleared() throws Exception {
//TODO: Test goes here...
        List<Map<String,Object>> lista = new ArrayList<>();
        Map mapa = new HashMap();
        mapa.put("name","name_s");
        mapa.put("value","value_s");
        mapa.put("createTime","2017-11-15 06:30:00");
        lista.add(mapa);


        Map<String, Object> kv3f = new HashMap<>();
        kv3f.put("faultFieldsVersion","faultFieldsVersion");
        kv3f.put("eventSeverity","eventSeverity");
        kv3f.put("eventSourceType","eventSourceType");
        kv3f.put("eventCategory","eventCategory");
        kv3f.put("alarmCondition","alarmCondition");
        kv3f.put("specificProblem","specificProblem");
        kv3f.put("vfStatus","vfStatus");
        kv3f.put("alarmInterfaceA","alarmInterfaceA");
        kv3f.put("alarmAdditionalInformation",lista);




        Map<String, Object> kv3c = new HashMap<>();
        kv3c.put("version","version");
        kv3c.put("eventName","Cleared");//还有 active
        kv3c.put("domain","domain");
        kv3c.put("eventId","eventId");
        kv3c.put("eventType","eventType");
        kv3c.put("nfcNamingCode","nfcNamingCode");
        kv3c.put("nfNamingCode","nfNamingCode");
        kv3c.put("sourceId","sourceId");
        kv3c.put("sourceName","sourceName");
        kv3c.put("reportingEntityId","reportingEntityId");

        kv3c.put("reportingEntityName","reportingEntityName");
        kv3c.put("priority","priority");
        kv3c.put("startEpochMicrosec","startEpochMicrosec");
        kv3c.put("lastEpochMicrosec","lastEpochMicrosec");
        kv3c.put("sequence","sequence");
















        Map<String, Object> eventMap = new HashMap<>();
        eventMap.put("commonEventHeader",kv3c);
        eventMap.put("faultFields",kv3f);

        try {
            Method method = dm.getClass().getDeclaredMethod("alarmProcess", Map.class);//Map<String,.class
            method.setAccessible(true);
            method.invoke(dm, eventMap);
        } catch(NoSuchMethodException e) {
        } catch(IllegalAccessException e) {
        } catch(InvocationTargetException e) {
        }

    }

    /**
* 
* Method: performanceProcess(Map<String, Object> eventMap) 
* 
*/ 
@Test
public void testPerformanceProcess() throws Exception { 
//TODO: Test goes here... 
   // Map<String, Object> eventMap = new HashMap<>();
 /*   eventMap.put("eventType","guestOS");
    eventMap.put("eventType","hostOS");
    eventMap.put("eventType","applicationVnf");*/
  List list  = new ArrayList();
    Map<String,String> kv34 = new HashMap<>();
    kv34.put("name","StartTime");
    kv34.put("value","2017-11-15 06:30:00");
    kv34.put("createTime","2017-11-15 06:30:00");
    list.add(kv34);


    List lists  = new ArrayList();
    Map<String,Object> kv33 = new HashMap<>();
    kv33.put("arrayOfFields",list);
    kv33.put("arrayOfFields_a","dddd");
    lists.add(kv33);


    Map<String, Object> kv3f = new HashMap<>();
    kv3f.put("measurementsForVfScalingVersion","measurementsForVfScalingVersion");
    kv3f.put("measurementInterval","measurementInterval");
    kv3f.put("additionalMeasurements", lists);





    Map<String, Object> kv3c = new HashMap<>();
    kv3c.put("version","version");
    kv3c.put("eventName","eventName");
    kv3c.put("domain","domain");
    kv3c.put("eventId","eventId");
    kv3c.put("eventType","applicationVnf");
    kv3c.put("nfcNamingCode","nfcNamingCode");
    kv3c.put("nfNamingCode","nfNamingCode");
    kv3c.put("sourceId","sourceId");
    kv3c.put("sourceName","sourceName");
    kv3c.put("reportingEntityId","reportingEntityId");

    kv3c.put("reportingEntityName","reportingEntityName");
    kv3c.put("priority","priority");
    kv3c.put("startEpochMicrosec","startEpochMicrosec");
    kv3c.put("lastEpochMicrosec","lastEpochMicrosec");
    kv3c.put("sequence","sequence");
















    Map<String, Object> eventMap = new HashMap<>();
    eventMap.put("commonEventHeader",kv3c);
    eventMap.put("measurementsForVfScalingFields",kv3f);

try { 
   Method method = dm.getClass().getDeclaredMethod("performanceProcess", Map.class);
   method.setAccessible(true); 
   method.invoke(dm, eventMap);
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 

}




    @Test
    public void testPerformanceProcess_guestOS() throws Exception {
//TODO: Test goes here...
        // Map<String, Object> eventMap = new HashMap<>();
 /*   eventMap.put("eventType","guestOS");
    eventMap.put("eventType","hostOS");
    eventMap.put("eventType","applicationVnf");*/
        List list  = new ArrayList();
        Map<String,String> kv34 = new HashMap<>();
        kv34.put("name","StartTime");
        kv34.put("value","2017-11-15 06:30:00");
        kv34.put("createTime","2017-11-15 06:30:00");
        list.add(kv34);


        List lists  = new ArrayList();
        Map<String,Object> kv33 = new HashMap<>();
        kv33.put("arrayOfFields",list);
        kv33.put("arrayOfFields_a","dddd");
        lists.add(kv33);


        Map<String, Object> kv3f = new HashMap<>();
        kv3f.put("measurementsForVfScalingVersion","measurementsForVfScalingVersion");
        kv3f.put("measurementInterval","measurementInterval");
        kv3f.put("additionalMeasurements", lists);





        Map<String, Object> kv3c = new HashMap<>();
        kv3c.put("version","version");
        kv3c.put("eventName","eventName");
        kv3c.put("domain","domain");
        kv3c.put("eventId","eventId");
        kv3c.put("eventType","guestOS");
        kv3c.put("nfcNamingCode","nfcNamingCode");
        kv3c.put("nfNamingCode","nfNamingCode");
        kv3c.put("sourceId","sourceId");
        kv3c.put("sourceName","sourceName");
        kv3c.put("reportingEntityId","reportingEntityId");

        kv3c.put("reportingEntityName","reportingEntityName");
        kv3c.put("priority","priority");
        kv3c.put("startEpochMicrosec","startEpochMicrosec");
        kv3c.put("lastEpochMicrosec","lastEpochMicrosec");
        kv3c.put("sequence","sequence");
















        Map<String, Object> eventMap = new HashMap<>();
        eventMap.put("commonEventHeader",kv3c);
        eventMap.put("measurementsForVfScalingFields",kv3f);

        try {
            Method method = dm.getClass().getDeclaredMethod("performanceProcess", Map.class);
            method.setAccessible(true);
            method.invoke(dm, eventMap);
        } catch(NoSuchMethodException e) {
        } catch(IllegalAccessException e) {
        } catch(InvocationTargetException e) {
        }

    }






    @Test
    public void testPerformanceProcess_hostOS() throws Exception {
//TODO: Test goes here...
        // Map<String, Object> eventMap = new HashMap<>();
 /*   eventMap.put("eventType","guestOS");
    eventMap.put("eventType","hostOS");
    eventMap.put("eventType","applicationVnf");*/
        List list  = new ArrayList();
        Map<String,String> kv34 = new HashMap<>();
        kv34.put("name","StartTime");
        kv34.put("value","2017-11-15 06:30:00");
        kv34.put("createTime","2017-11-15 06:30:00");
        list.add(kv34);


        List lists  = new ArrayList();
        Map<String,Object> kv33 = new HashMap<>();
        kv33.put("arrayOfFields",list);
        kv33.put("arrayOfFields_a","dddd");
        lists.add(kv33);


        Map<String, Object> kv3f = new HashMap<>();
        kv3f.put("measurementsForVfScalingVersion","measurementsForVfScalingVersion");
        kv3f.put("measurementInterval","measurementInterval");
        kv3f.put("additionalMeasurements", lists);





        Map<String, Object> kv3c = new HashMap<>();
        kv3c.put("version","version");
        kv3c.put("eventName","eventName");
        kv3c.put("domain","domain");
        kv3c.put("eventId","eventId");
        kv3c.put("eventType","hostOS");
        kv3c.put("nfcNamingCode","nfcNamingCode");
        kv3c.put("nfNamingCode","nfNamingCode");
        kv3c.put("sourceId","sourceId");
        kv3c.put("sourceName","sourceName");
        kv3c.put("reportingEntityId","reportingEntityId");

        kv3c.put("reportingEntityName","reportingEntityName");
        kv3c.put("priority","priority");
        kv3c.put("startEpochMicrosec","startEpochMicrosec");
        kv3c.put("lastEpochMicrosec","lastEpochMicrosec");
        kv3c.put("sequence","sequence");
















        Map<String, Object> eventMap = new HashMap<>();
        eventMap.put("commonEventHeader",kv3c);
        eventMap.put("measurementsForVfScalingFields",kv3f);

        try {
            Method method = dm.getClass().getDeclaredMethod("performanceProcess", Map.class);
            method.setAccessible(true);
            method.invoke(dm, eventMap);
        } catch(NoSuchMethodException e) {
        } catch(IllegalAccessException e) {
        } catch(InvocationTargetException e) {
        }

    }

} 
