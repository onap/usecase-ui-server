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
package org.onap.usecaseui.server.service.impl; 

import org.junit.Test; 
import org.junit.Before; 
import org.junit.After;
import org.junit.runner.RunWith;
import org.onap.usecaseui.server.UsecaseuiServerApplication;
import org.onap.usecaseui.server.bean.AlarmsHeader;
import org.onap.usecaseui.server.service.AlarmsHeaderService;
import org.onap.usecaseui.server.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/** 
* AlarmsHeaderServiceImpl Tester. 
* 
* @author <Authors name> 
* @since <pre>8, 2018</pre>
* @version 1.0 
*/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = UsecaseuiServerApplication.class)
@WebAppConfiguration
public class AlarmsHeaderServiceImplTest {
    @Autowired
    private AlarmsHeaderService alarmsHeaderService;

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: saveAlarmsHeader(AlarmsHeader alarmsHeader) 
* 
*/ 
@Test
public void testSaveAlarmsHeader() throws Exception { 
//TODO: Test goes here...
    AlarmsHeader a = new AlarmsHeader();
    a.setEventName("a");
    a.setStatus("1");
    a.setVfStatus("1");
    a.setEventId("1119");
    a.setDomain("asb");
    a.setEventCategory("s");
    a.setAlarmCondition("ea");
    a.setAlarmInterfaceA("cs");
    a.setCreateTime(DateUtils.now());
    a.setEventServrity("s");
    a.setEventSourceType("q");
    a.setEventType("q");
    a.setFaultFieldsVersion("v1");
    a.setLastEpochMicroSec("csa");
    a.setNfcNamingCode("std");
    a.setNfNamingCode("cout");
    a.setPriority("cs");
    a.setReportingEntityId("112");
    a.setReportingEntityName("asfs");
    a.setSequence("cgg");
    a.setSourceId("123");
    a.setSourceName("eggs");
    a.setSpecificProblem("especially");
    a.setStartEpochMicrosec("wallet");
    a.setUpdateTime(DateUtils.now());
    a.setVersion("va2");
    System.out.println(alarmsHeaderService.saveAlarmsHeader(a));
} 

/** 
* 
* Method: updateAlarmsHeader2018(String status, String date, String eventNameCleared, String eventName, String reportingEntityName, String specificProblem) 
* 
*/ 
@Test
public void testUpdateAlarmsHeader2018() throws Exception { 
//TODO: Test goes here...
    //Date date = new Date();
   // Date  date = new Date("2018-02-28 15:25:39");
    //Date  date = new Date();
    //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
   // String date_gets = dateFormat.format( new Date() );
    //Date date_get = new Date();
    //Date date_get = dateFormat.parse(date_gets);
    Long l = System.currentTimeMillis();

    Timestamp date_get = new Timestamp(l); //2013-01-14 22:45:36.484
   alarmsHeaderService.updateAlarmsHeader2018("active",date_get,"112","112","Fault_MultiCloud_VMFailureCleared","Multi-Cloud","Fault_MultiCloud_VMFailure");


}

/**
* 
* Method: getStatusBySourceName(String sourceName) 
* 
*/ 
@Test
public void testGetStatusBySourceName() throws Exception { 
//TODO: Test goes here...
 Boolean  bl =   alarmsHeaderService.getStatusBySourceName("vnf_a_3");
 System.out.println("boolean="+bl);
} 

/** 
* 
* Method: getIdByStatusSourceName(String sourceName) 
* 
*/ 
@Test
public void testGetIdByStatusSourceName() throws Exception { 
//TODO: Test goes here...
    alarmsHeaderService.getIdByStatusSourceName("vnf_a_3");
} 

/** 
* 
* Method: updateAlarmsHeader(AlarmsHeader alarmsHeader) 
* 
*/ 
@Test
public void testUpdateAlarmsHeader() throws Exception { 
//TODO: Test goes here...
    AlarmsHeader a = new AlarmsHeader();
    a.setEventName("a1");
    a.setStatus("2");
    a.setVfStatus("3");
    a.setEventId("1101");
    a.setDomain("asb");
    a.setEventCategory("s");
    a.setAlarmCondition("ea");
    a.setAlarmInterfaceA("cs");
    a.setCreateTime(DateUtils.now());
    a.setEventServrity("s");
    a.setEventSourceType("q");
    a.setEventType("q");
    a.setFaultFieldsVersion("v1");
    a.setLastEpochMicroSec("csa");
    a.setNfcNamingCode("std");
    a.setNfNamingCode("cout");
    a.setPriority("cs");
    a.setReportingEntityId("112");
    a.setReportingEntityName("asfs");
    a.setSequence("cgg");
    a.setSourceId("123");
    a.setSourceName("eggs");
    a.setSpecificProblem("especially");
    a.setStartEpochMicrosec("wallet");
    a.setUpdateTime(DateUtils.now());
    a.setVersion("va2");
    System.out.println(alarmsHeaderService.updateAlarmsHeader(a));
} 

/** 
* 
* Method: getAllCount(AlarmsHeader alarmsHeader, int currentPage, int pageSize) 
* 
*/ 
@Test
public void testGetAllCount() throws Exception { 
//TODO: Test goes here...
    AlarmsHeader alarmsHeader = new AlarmsHeader();
    alarmsHeader.setSourceName("vnf_a_3");
    alarmsHeader.setEventName("Fault_MultiCloud_VMFailureCleared");
    alarmsHeader.setEventId("ab305d54-85b4-a31b-7db2-fb6b9e546015");
    alarmsHeader.setSourceId("shentao-test-3004");
    alarmsHeader.setLastEpochMicroSec("1516784364860");
    alarmsHeader.setStartEpochMicrosec("1516784364860");
    alarmsHeader.setEventType("");
    alarmsHeader.setStatus("active");
    /*Date dateC = new Date("2018-01-25 15:00:40");
    Date dateE = new Date("2018-01-26 16:59:24");
    alarmsHeader.setCreateTime(dateC);
    alarmsHeader.setUpdateTime(dateE);*/


    alarmsHeaderService.getAllCount(alarmsHeader,0,12);
} 

/** 
* 
* Method: queryAlarmsHeader(AlarmsHeader alarmsHeader, int currentPage, int pageSize) 
* 
*/ 
@Test
public void testQueryAlarmsHeader() throws Exception { 
//TODO: Test goes here...

    AlarmsHeader alarmsHeader=new AlarmsHeader();
    alarmsHeader.setEventId("110");
    alarmsHeader.setEventName("asdasds");
    alarmsHeader.setSourceName("vnf_a_3");
    alarmsHeader.setEventName("Fault_MultiCloud_VMFailureCleared");
    alarmsHeader.setEventId("ab305d54-85b4-a31b-7db2-fb6b9e546015");
    alarmsHeader.setSourceId("shentao-test-3004");
    alarmsHeader.setLastEpochMicroSec("1516784364860");
    alarmsHeader.setStartEpochMicrosec("1516784364860");
    alarmsHeader.setEventType("");
    alarmsHeader.setStatus("active");
   /* Date dateC = new Date("2018-01-25 15:00:40");
    Date dateE = new Date("2018-01-26 16:59:24");
    alarmsHeader.setCreateTime(dateC);
    alarmsHeader.setUpdateTime(dateE);*/
    //System.out.println(alarmsHeaderService.queryAlarmsHeader(alarmsHeader,1,100).getList().size());
    alarmsHeaderService.queryAlarmsHeader(alarmsHeader,1,100).getList().forEach( as->System.out.println(as.toString()));

} 

/** 
* 
* Method: queryId(String[] id) 
* 
*/ 
@Test
public void testQueryId() throws Exception { 
//TODO: Test goes here...
    alarmsHeaderService.queryId(new String[]{"1101"}).forEach( a -> System.out.println(a));
} 

/** 
* 
* Method: queryStatusCount(String status) 
* 
*/ 
@Test
public void testQueryStatusCount() throws Exception { 
//TODO: Test goes here...
    String str =alarmsHeaderService.queryStatusCount("close");
    System.out.println("str ="+str);
} 


} 
