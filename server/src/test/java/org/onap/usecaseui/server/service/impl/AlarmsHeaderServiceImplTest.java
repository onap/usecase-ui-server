package org.onap.usecaseui.server.service.impl; 

import org.junit.Test; 
import org.junit.Before; 
import org.junit.After;
import org.junit.runner.RunWith;
import org.onap.usecaseui.server.UsecaseuiServerApplication;
import org.onap.usecaseui.server.bean.AlarmsHeader;
import org.onap.usecaseui.server.service.impl.AlarmsHeaderServiceImpl;
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
    private AlarmsHeaderServiceImpl alarmsHeaderServiceImpl;

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
    System.out.println(alarmsHeaderServiceImpl.saveAlarmsHeader(a));
} 


} 
