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
package org.onap.usecaseui.server.controller; 

import org.junit.Test; 
import org.junit.Before; 
import org.junit.After;
import org.onap.usecaseui.server.bean.AlarmsHeader;
import org.onap.usecaseui.server.service.AlarmsHeaderService;

import javax.servlet.http.HttpServletRequest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.mockito.Mockito.*;

/** 
* AlarmController Tester. 
* 
* @author <Authors name> 
* @since <pre>���� 28, 2018</pre> 
* @version 1.0 
*/ 
public class AlarmControllerTest { 

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: setAlarmsHeaderService(AlarmsHeaderService alarmsHeaderService) 
* 
*/ 
@Test
public void testSetAlarmsHeaderService() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: setAlarmsInformationService(AlarmsInformationService alarmsInformationService) 
* 
*/ 
@Test
public void testSetAlarmsInformationService() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getAllByDatetime(@PathVariable(required = false) String eventId, @PathVariable(required = false) String eventServrity, @PathVariable(required = false) String startTime, @PathVariable(required = false) String endTime) 
* 
*/ 
@Test
public void testGetAllByDatetime() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getAlarmsHeaderDetail(@PathVariable Integer id) 
* 
*/ 
@Test
public void testGetAlarmsHeaderDetail() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getAlarmDataByStatus(@PathVariable String status, @PathVariable(required = false) String eventName, @PathVariable(required = false) String sourceName, @PathVariable(required = false) String eventServerity, @PathVariable(required = false) String reportingEntityName, @PathVariable(required = false) String createTime, @PathVariable(required = false) String endTime) 
* 
*/ 
@Test
public void testGetAlarmDataByStatus() throws Exception { 
//TODO: Test goes here...

    AlarmController controller = new AlarmController();
    AlarmsHeaderService service = mock(AlarmsHeaderService.class);
    controller.setAlarmsHeaderService(service);
    HttpServletRequest request = mock(HttpServletRequest.class);
       /* String id ="id";
        when(request.getParameter(id)).thenReturn(id);
        String type="type";
        when(request.getParameter(type)).thenReturn(type);*/

    String status ="active";
    String eventName ="Fault_MultiCloud_VMFailureCleared";
    String sourceName ="shentao-test-2001";
    String eventServerity ="CRITICAL";
    String reportingEntityName ="Multi-Cloud";
    String createTime_s="2018-01-24 17:00:25";
    String endTime_s="2018-03-15 00:00:00";
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    Date createTime=formatter.parse(createTime_s);
    Date endTime=formatter.parse(endTime_s);


    controller.getAlarmDataByStatus(status,eventName,sourceName,eventServerity,reportingEntityName,createTime_s,endTime_s);
    //verify(customerService, times(1)).listCustomer();
    verify(service,times(1)).getAllByStatus(status,eventName,sourceName,eventServerity,reportingEntityName,createTime,endTime);




} 

/** 
* 
* Method: getAlarmData(@PathVariable(required = false) String sourceId, @PathVariable(required = false) String sourceName, @PathVariable(required = false) String priority, @PathVariable(required = false) String startTime, @PathVariable(required = false) String endTime, @PathVariable(required = false) String vfStatus, @PathVariable int currentPage, @PathVariable int pageSize) 
* 
*/ 
@Test
public void testGetAlarmData() throws Exception { 
//TODO: Test goes here...
    AlarmController controller = new AlarmController();
    AlarmsHeaderService service = mock(AlarmsHeaderService.class);
    controller.setAlarmsHeaderService(service);
    AlarmsHeader header = new AlarmsHeader();
   // {currentPage}/{pageSize}/{sourceId}/{sourceName}/{priority}/{startTime}/{endTime}/{vfStatus}

    int  currentPage=1;
    int  pageSize=12;
    //String  sourceId="shentao-test-1003";
    //String  sourceName="shentao-test-1003";
    //String  priority="High";
    //String  startTime="2017-10-31 09:51";
    //String  endTime="2018-03-15 00:00";
    //String  vfStatus="Active";
    /*String  sourceId="11694_113";
    String  sourceName="11694_113";
    String  priority="High";
    String  startTime="2017-10-31 09:52:15";
    String  endTime="2017-11-15 15:27:16";
    String  vfStatus="Medium";*/
    String  sourceId=null;
    String  sourceName=null;
    String  priority=null;
    String  startTime=null;
    String  endTime=null;
    String  vfStatus=null;
    header.setPriority(priority);
    header.setStatus(vfStatus);
    header.setSourceId(sourceId);
    header.setSourceName(sourceName);



   // controller.getAlarmData(currentPage,pageSize,sourceId,sourceName,priority,startTime,endTime,vfStatus);
    //controller.getAlarmData(currentPage,pageSize,sourceId,sourceName,priority,startTime,endTime,vfStatus);

   // header.setCreateTime(!"null".equals(startTime) ? new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(startTime) : null);
   // header.setUpdateTime(!"null".equals(endTime) ? new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(endTime) : null);
   // verify(service,times(1)).queryAlarmsHeader(header,currentPage,pageSize);

} 

/** 
* 
* Method: getStatusCount() 
* 
*/ 
@Test
public void testGetStatusCount() throws Exception { 
//TODO: Test goes here...
    AlarmController controller = new AlarmController();
    AlarmsHeaderService service = mock(AlarmsHeaderService.class);
    controller.setAlarmsHeaderService(service);

    controller.getStatusCount();
    verify(service,times(1)).queryStatusCount("0");
    verify(service,times(1)).queryStatusCount("active");
    verify(service,times(1)).queryStatusCount("close");





} 

/** 
* 
* Method: getTopologyData(@PathVariable String serviceName) 
* 
*/ 
@Test
public void testGetTopologyData() throws Exception { 
//TODO: Test goes here...
    AlarmController controller = new AlarmController();
    AlarmsHeaderService service = mock(AlarmsHeaderService.class);
    controller.setAlarmsHeaderService(service);

    String serviceName="Fault_MultiCloud_VMFailureCleared";
    //controller.getTopologyData(serviceName);
    //controller.getAllVNFS().get("networkServices");
    //controller.getAllVNFS().get("VNFS");



} 

/** 
* 
* Method: getTopologyServices() 
* 
*/ 
@Test
public void testGetTopologyServices() throws Exception { 
//TODO: Test goes here...
    AlarmController controller = new AlarmController();
    AlarmsHeaderService service = mock(AlarmsHeaderService.class);
    controller.setAlarmsHeaderService(service);

    AlarmsHeader header = new AlarmsHeader();
    header.setSourceId("shentao-test-2001");
    //controller.getAllVNFS().get("services");

    //controller.getTopologyServices();

    //verify(service,times(1)).queryAlarmsHeader(header,1,10).getList();
} 

/** 
* 
* Method: getAllVNFS() 
* 
*/ 
/*@Test
public void testGetAllVNFS() throws Exception { 
//TODO: Test goes here... 
}*/

/** 
* 
* Method: getSourceId() 
* 
*/ 
@Test
public void testGetSourceId() throws Exception { 
//TODO: Test goes here...
    AlarmController controller = new AlarmController();
    AlarmsHeaderService service = mock(AlarmsHeaderService.class);
    controller.setAlarmsHeaderService(service);
    AlarmsHeader header = new AlarmsHeader();
   // controller.getSourceId();

    //verify(service,times(1)).queryAlarmsHeader(header,1,10);
} 

/** 
* 
* Method: genDiagram(@RequestParam String sourceId, @RequestParam String startTime, @RequestParam String endTime, @RequestParam String showMode) 
* 
*/ 
@Test
public void testGenDiagram() throws Exception { 
//TODO: Test goes here...
    AlarmController controller = new AlarmController();
    AlarmsHeaderService service = mock(AlarmsHeaderService.class);
    controller.setAlarmsHeaderService(service);
    String sourceId="";
    String startTime="";
    String endTime="";
    String showMode="";
    controller.genDiagram(sourceId,startTime,endTime,showMode);
} 


/** 
* 
* Method: getTopologyData() 
* 
*/ 

/*@Test
public void testDateProcess() throws Exception { 
//TODO: Test goes here... 

} */

/** 
* 
* Method: diagramDate(String sourceId, String startTime, String endTime, String format) 
* 
*/ 
/*@Test
public void testDiagramDate() throws Exception { 
//TODO: Test goes here... 

} */

} 
