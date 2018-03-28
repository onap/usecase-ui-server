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
import org.onap.usecaseui.server.bean.PerformanceHeader;
import org.onap.usecaseui.server.service.PerformanceHeaderService;
import org.onap.usecaseui.server.util.DateUtils;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;

import static org.mockito.Mockito.mock;

/** 
* PerformanceHeaderServiceImpl Tester. 
* 
* @author <Authors name> 
* @since <pre> 8, 2018</pre>
* @version 1.0 
*/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = UsecaseuiServerApplication.class)
@WebAppConfiguration
public class PerformanceHeaderServiceImplTest {
  /*  @Resource(name = "PerformanceHeaderService")
    PerformanceHeaderService performanceHeaderService;*/
  PerformanceHeaderServiceImpl service;
@Before
public void before() throws Exception {
    service = mock(PerformanceHeaderServiceImpl.class);
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: savePerformanceHeader(PerformanceHeader performanceHeder) 
* 
*/ 
@Test
public void testSavePerformanceHeader() throws Exception { 
//TODO: Test goes here...
    PerformanceHeader p = new PerformanceHeader();
    p.setCreateTime(DateUtils.now());
    p.setEventId("2202");
    p.setEventName("fxc");
    p.setDomain("asb");
    p.setCreateTime(DateUtils.now());
    p.setEventType("q");
    p.setLastEpochMicroSec("csa");
    p.setNfcNamingCode("std");
    p.setNfNamingCode("cout");
    p.setPriority("cs");
    p.setReportingEntityId("112");
    p.setReportingEntityName("asfs");
    p.setSequence("cgg");
    p.setSourceId("123");
    p.setSourceName("eggs");
    p.setStartEpochMicrosec("wallet");
    p.setUpdateTime(DateUtils.now());
    p.setVersion("va2");
    p.setMeasurementInterval("12");
    p.setMeasurementsForVfScalingVersion("12");

    service.savePerformanceHeader(p);
} 

/** 
* 
* Method: updatePerformanceHeader(PerformanceHeader performanceHeder) 
* 
*/ 
@Test
public void testUpdatePerformanceHeader() throws Exception { 
//TODO: Test goes here...
    PerformanceHeader p = new PerformanceHeader();
    p.setCreateTime(DateUtils.now());
    p.setEventId("110");
    p.setEventName("fxc");
    p.setDomain("asb");
    p.setCreateTime(DateUtils.now());
    p.setEventType("q");
    p.setLastEpochMicroSec("csa");
    p.setNfcNamingCode("std");
    p.setNfNamingCode("cout");
    p.setPriority("cs");
    p.setReportingEntityId("112");
    p.setReportingEntityName("asfs");
    p.setSequence("cgg");
    p.setSourceId("123");
    p.setSourceName("eggs");
    p.setStartEpochMicrosec("wallet");
    p.setUpdateTime(DateUtils.now());
    p.setVersion("va2");
    p.setMeasurementInterval("12");
    p.setMeasurementsForVfScalingVersion("12");
    service.updatePerformanceHeader(p);
} 

/** 
* 
* Method: getAllCount(PerformanceHeader performanceHeder, int currentPage, int pageSize) 
* 
*/ 
@Test
public void testGetAllCount() throws Exception { 
//TODO: Test goes here...

    PerformanceHeader performanceHeader = new PerformanceHeader();
    performanceHeader.setSourceName("vnf_a_3");


    service.getAllCount(performanceHeader,0,12);

} 

/** 
* 
* Method: queryPerformanceHeader(PerformanceHeader performanceHeder, int currentPage, int pageSize) 
* 
*/ 
@Test
public void testQueryPerformanceHeader() throws Exception { 
//TODO: Test goes here...
    PerformanceHeader p = new PerformanceHeader();
    p.setEventId("110");
    service.queryPerformanceHeader(p,1,100);
          //  .getList().forEach(per -> System.out.println(per));
} 

/** 
* 
* Method: queryId(String[] id) 
* 
*/ 
@Test
public void testQueryId() throws Exception { 
//TODO: Test goes here...
    service.queryId(new String[]{"110"});
           // .forEach(pe -> System.out.println(pe.getCreateTime()));
} 

/** 
* 
* Method: queryAllSourceId() 
* 
*/ 
@Test
public void testQueryAllSourceId() throws Exception { 
//TODO: Test goes here...
    PerformanceHeader p = new PerformanceHeader();
    p.setSourceId("123");
    service.queryPerformanceHeader(p,1,100);
            //.getList().forEach(per -> System.out.println(per));
} 


} 
