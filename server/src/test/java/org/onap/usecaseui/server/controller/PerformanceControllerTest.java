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
import org.junit.Before; 
import org.junit.After;
import org.onap.usecaseui.server.bean.PerformanceHeader;
import org.onap.usecaseui.server.bean.PerformanceInformation;
import org.onap.usecaseui.server.service.PerformanceHeaderService;
import org.onap.usecaseui.server.service.PerformanceInformationService;

import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.*;

/** 
* PerformanceController Tester. 
* 
* @author <Authors name> 
* @since <pre>���� 28, 2018</pre> 
* @version 1.0 
*/ 
public class PerformanceControllerTest {
    PerformanceController controller = new PerformanceController();
    PerformanceHeaderService service;
    PerformanceInformationService perservece;


    @Before
public void before() throws Exception {
    service = mock(PerformanceHeaderService.class);
    controller.setPerformanceHeaderService(service);
    controller.setPerformanceInformationService(perservece);
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: getPerformanceData(HttpServletResponse response, @PathVariable int currentPage, @PathVariable int pageSize, @PathVariable(required = false) String sourceId, @PathVariable(required = false) String sourceName, @PathVariable(required = false) String priority, @PathVariable(required = false) String startTime, @PathVariable(required = false) String endTime) 
* 
*/ 
@Test
public void testGetPerformanceData() throws Exception {
//TODO: Test goes here...
    HttpServletResponse response = mock(HttpServletResponse.class);
    int currentPage = 1;
    int pageSize=12;
    String sourceId="1101ZTHX1MNE1NK7E0";
    String  sourceName="1101ZTHX1MNE1NK7E0";
    String  priority="Normal";
    String  startTime="2017-11-15 06:30:00";
    String  endTime="2017-11-15 14:45:26";
    PerformanceHeader header = new PerformanceHeader();


    controller.getPerformanceData(response,currentPage,pageSize,sourceId,sourceName,priority,startTime,endTime);
    verify(service,times(1)).queryPerformanceHeader(header,currentPage,pageSize);

}

/** 
* 
* Method: generateDiagram(@RequestParam String sourceId, @RequestParam String startTime, @RequestParam String endTime, @RequestParam String nameParent, @RequestParam String format) 
* 
*/ 
@Test
public void testGenerateDiagram() throws Exception { 
//TODO: Test goes here...

    String sourceId="";
    String startTime="";
    String endTime="";
    String nameParent="";
    String format="";
    controller.generateDiagram(sourceId,startTime,endTime,nameParent,format);
} 

/** 
* 
* Method: getSourceIds() 
* 
*/ 
@Test
public void testGetSourceIds() throws Exception { 
//TODO: Test goes here...
    controller.getSourceIds();
    verify(service,times(1)).queryAllSourceId();
} 

/** 
* 
* Method: getNames(@RequestParam Object sourceId) 
* 
*/ 
@Test
public void testGetNames() throws Exception { 
//TODO: Test goes here...
Object sourceId="1101ZTHX1MNE1NK7E0";
    controller.getNames(sourceId);
    //verify(perservece,times(1)).queryDateBetween(sourceId.toString(),null,null,null);
} 


/** 
* 
* Method: dateProcess(String sourceId, String name, long startTimeL, long endTimeL, long timeIteraPlusVal, long keyVal, long keyValIteraVal, String keyUnit) 
* 
*/ 
@Test
public void testDateProcess() throws Exception { 
//TODO: Test goes here... 
/*
try {
   Method method = PerformanceController.getClass().getMethod("dateProcess", String.class, String.class, long.class, long.class, long.class, long.class, long.class, String.class);
   method.setAccessible(true);
   method.invoke(<Object>, <Parameters>);
} catch(NoSuchMethodException e) {
} catch(IllegalAccessException e) {
} catch(InvocationTargetException e) {
}
*/
} 

/** 
* 
* Method: diagramDate(String sourceId, String name, String startTime, String endTime, String format) 
* 
*/ 
@Test
public void testDiagramDate() throws Exception { 
//TODO: Test goes here... 
/* 
try { 
   Method method = PerformanceController.getClass().getMethod("diagramDate", String.class, String.class, String.class, String.class, String.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/ 
} 

} 
