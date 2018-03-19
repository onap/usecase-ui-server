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
import org.onap.usecaseui.server.bean.AlarmsInformation;
import org.onap.usecaseui.server.service.AlarmsInformationService;
import org.onap.usecaseui.server.util.DateUtils;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;

/** 
* AlarmsInformationServiceImpl Tester. 
* 
* @author <Authors name> 
* @since <pre> 8, 2018</pre>
* @version 1.0 
*/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = UsecaseuiServerApplication.class)
@WebAppConfiguration
public class AlarmsInformationServiceImplTest {

    @Resource(name = "AlarmsInformationService")
    AlarmsInformationService alarmsInformationService;
@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: saveAlarmsInformation(AlarmsInformation alarmsInformation) 
* 
*/ 
@Test
public void testSaveAlarmsInformation() throws Exception { 
//TODO: Test goes here...
    AlarmsInformation a = new AlarmsInformation();
    a.setEventId("1119");
    a.setName("efw");
    a.setValue("fre");
    a.setCreateTime(DateUtils.now());
    a.setUpdateTime(DateUtils.now());
    System.out.println(alarmsInformationService.saveAlarmsInformation(a));
} 

/** 
* 
* Method: updateAlarmsInformation(AlarmsInformation alarmsInformation) 
* 
*/ 
@Test
public void testUpdateAlarmsInformation() throws Exception { 
//TODO: Test goes here...
    AlarmsInformation a = new AlarmsInformation();
    a.setEventId("110");
    a.setName("1");
    a.setValue("fko");
    a.setUpdateTime(DateUtils.now());
    a.setCreateTime(DateUtils.now());
    System.out.println(alarmsInformationService.updateAlarmsInformation(a));
} 

/** 
* 
* Method: getAllCount(AlarmsInformation alarmsInformation, int currentPage, int pageSize) 
* 
*/ 
@Test
public void testGetAllCount() throws Exception { 
//TODO: Test goes here...
    AlarmsInformation larmsInformation = new AlarmsInformation();
    larmsInformation.setName("vnf_a_3");


    alarmsInformationService.getAllCount(larmsInformation,0,12);
} 

/** 
* 
* Method: queryAlarmsInformation(AlarmsInformation alarmsInformation, int currentPage, int pageSize) 
* 
*/ 
@Test
public void testQueryAlarmsInformation() throws Exception { 
//TODO: Test goes here...
    AlarmsInformation a = new AlarmsInformation();
    a.setEventId("110");
    alarmsInformationService.queryAlarmsInformation(a,1,100)
            .getList().forEach( al -> System.out.println(al.getEventId()));
} 

/** 
* 
* Method: queryId(String[] id) 
* 
*/ 
@Test
public void testQueryId() throws Exception { 
//TODO: Test goes here...
    alarmsInformationService.queryId(new String[]{"110"}).forEach(ai -> System.out.println(ai));
} 

/** 
* 
* Method: queryDateBetween(String sourceId, String startTime, String endTime) 
* 
*/ 
@Test
public void testQueryDateBetween() throws Exception { 
//TODO: Test goes here...
    alarmsInformationService.queryDateBetween("MME40","","").forEach( in -> {
       // System.out.println(in);
    });
} 


} 
