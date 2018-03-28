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
import org.onap.usecaseui.server.bean.PerformanceInformation;
import org.onap.usecaseui.server.service.PerformanceInformationService;
import org.onap.usecaseui.server.util.DateUtils;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.mockito.Mockito.mock;

/** 
* PerformanceInformationServiceImpl Tester. 
* 
* @author <Authors name> 
* @since <pre> 8, 2018</pre>
* @version 1.0 
*/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = UsecaseuiServerApplication.class)
@WebAppConfiguration
public class PerformanceInformationServiceImplTest {
    /*@Resource(name = "PerformanceInformationService")
    PerformanceInformationService performanceInformationService;*/
    PerformanceInformationServiceImpl service;
@Before
public void before() throws Exception {
    service = mock(PerformanceInformationServiceImpl.class);
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: savePerformanceInformation(PerformanceInformation performanceInformation) 
* 
*/ 
@Test
public void testSavePerformanceInformation() throws Exception { 
//TODO: Test goes here...

    PerformanceInformation a = new PerformanceInformation();

    a.setEventId("123");
    a.setName("SGS.UeUnreachable");
    a.setValue("40");
    a.setCreateTime(DateUtils.now());
    a.setUpdateTime(DateUtils.now());
    service.savePerformanceInformation(a);

} 

/** 
* 
* Method: updatePerformanceInformation(PerformanceInformation performanceInformation) 
* 
*/ 
@Test
public void testUpdatePerformanceInformation() throws Exception { 
//TODO: Test goes here...

    PerformanceInformation a = new PerformanceInformation();
    a.setEventId("110");
    a.setName("efw");
    a.setValue("fko11");
    a.setUpdateTime(DateUtils.now());
    a.setCreateTime(DateUtils.now());
    service.updatePerformanceInformation(a);
} 

/** 
* 
* Method: getAllCount(PerformanceInformation performanceInformation, int currentPage, int pageSize) 
* 
*/ 
@Test
public void testGetAllCount() throws Exception { 
//TODO: Test goes here...

    PerformanceInformation performanceInformation = new PerformanceInformation();
    performanceInformation.setName("vnf_a_3");


    service.getAllCount(performanceInformation,0,12);

} 

/** 
* 
* Method: queryPerformanceInformation(PerformanceInformation performanceInformation, int currentPage, int pageSize) 
* 
*/ 
@Test
public void testQueryPerformanceInformation() throws Exception { 
//TODO: Test goes here...
    PerformanceInformation a = new PerformanceInformation();
    // a.setEventId("2202");
    service.queryPerformanceInformation(a, 1, 100);
           // .getList().forEach(al -> System.out.println(al.getValue()));
} 

/** 
* 
* Method: queryId(String[] id) 
* 
*/ 
@Test
public void testQueryId() throws Exception { 
//TODO: Test goes here...
    service.queryId(new String[]{"2202"});
          //  .forEach(ai -> System.out.println(ai.getCreateTime()));
} 

/** 
* 
* Method: queryDateBetween(String eventId, Date startDate, Date endDate) 
* 
*/ 
@Test
public void testQueryDateBetweenForEventIdStartDateEndDate() throws Exception { 
//TODO: Test goes here...
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    String star = "2017-11-15 06:30:00";
    String end="2017-11-15 14:45:10";
    Date stard = sdf.parse(star);
    Date endd = sdf.parse(end);
    service.queryDateBetween("1101ZTHX1MMEGJM1W1",stard,endd);
} 

/** 
* 
* Method: queryDateBetween(String resourceId, String name, String startTime, String endTime) 
* 
*/ 
@Test
public void testQueryDateBetweenForResourceIdNameStartTimeEndTime() throws Exception { 
//TODO: Test goes here...
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    String star = "2017-11-15 06:30:00";
    String end="2017-11-15 14:45:10";
    Date stard = sdf.parse(star);
    Date endd = sdf.parse(end);
    service.queryDateBetween("1101ZTHX1MMEGJM1W1",stard,endd);

} 

/** 
* 
* Method: queryMaxValueByBetweenDate(String sourceId, String name, String startTime, String endTime) 
* 
*/ 
@Test
public void testQueryMaxValueByBetweenDate() throws Exception { 
//TODO: Test goes here...
    service.queryDateBetween("2202", DateUtils.stringToDate("2017-10-15 01:00:00"), DateUtils.stringToDate("2017-10-15 02:00:00")).forEach(p -> System.out.println(p));

} 


} 
