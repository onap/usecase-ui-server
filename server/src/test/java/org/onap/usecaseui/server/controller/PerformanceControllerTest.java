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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.mockito.Mockito.*;

/** 
* PerformanceController Tester. 
* 
* @author <Authors name> 
* @since <pre>���� 28, 2018</pre> 
* @version 1.0 
*/ 
public class PerformanceControllerTest {
    PerformanceController controller_s;
    PerformanceController controller = new PerformanceController();
    PerformanceHeaderService service;
    PerformanceInformationService perservece;


    @Before
public void before() throws Exception {
    service = mock(PerformanceHeaderService.class);
        perservece = mock(PerformanceInformationService.class);
    controller.setPerformanceHeaderService(service);
    controller.setPerformanceInformationService(perservece);
        controller_s = new PerformanceController();
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



    @Test
    public void testGetPerformanceData_2() throws Exception {
//TODO: Test goes here...
        HttpServletResponse response = mock(HttpServletResponse.class);
        int currentPage = 1;
        int pageSize=12;
        String sourceId=null;
        String  sourceName=null;
        String  priority=null;
        String  startTime=null;
        String  endTime=null;
        PerformanceHeader header = new PerformanceHeader();


        controller.getPerformanceData(response,currentPage,pageSize,sourceId,sourceName,priority,startTime,endTime);
        verify(service,times(1)).queryPerformanceHeader(header,currentPage,pageSize);

    }



    @Test
    public void testGetPerformanceData_3() throws Exception {
//TODO: Test goes here...
        HttpServletResponse response = mock(HttpServletResponse.class);
        int currentPage = 1;
        int pageSize=12;
        String sourceId="1101ZTHX1MNE1NK7E0";
        String  sourceName="1101ZTHX1MNE1NK7E0";
        String  priority="Normal";
        String  startTime="2017-11-15";
        String  endTime="2017-11-15";
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


    @Test
    public void testGenerateDiagram_a() throws Exception {
//TODO: Test goes here...

        String sourceId="";
        String startTime="2017-10-15";
        String endTime="2017-11-15";
        String nameParent="";
        String format="";
        controller.generateDiagram(sourceId,startTime,endTime,nameParent,"0000");
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
Object sourceId="1101ZTHX1MMEGJM1W1";
    controller.getNames(sourceId);
    verify(perservece,times(1)).queryDateBetween(sourceId.toString(),null,null,null);
}


/** 
* 
* Method: dateProcess(String sourceId, String name, long startTimeL, long endTimeL, long timeIteraPlusVal, long keyVal, long keyValIteraVal, String keyUnit) 
* 
*/ 
@Test
public void testDateProcess() throws Exception { 
//TODO: Test goes here... 
    PerformanceController per = new PerformanceController();
    String sourceId="1101ZTHX1MMEGJM1W1";
    String name="HO.SuccOutInterMme";
    String time_star="2017-11-15 06:30:00";
    //String time_itera="2017-11-15 06:31:00";
    String time_end="2017-12-15 06:30:00";
    SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date date_star = format.parse(time_star);
    //Date date_itera = format.parse(time_itera);
    Date date_end = format.parse(time_end);

    //System.out.println(date.getTime());  //将时间转化为毫秒数例如: 1473297300000

    long startTimeL =date_star.getTime();
    long endTimeL = date_end.getTime();
    //long timeIteraPlusVal =date_itera.getTime();
    long timeIteraPlusVal =2592000000L;
    long keyVal=1l;
    long keyValIteraVal=1l;
    String keyUnit="month";
try {
   Method method = per.getClass().getDeclaredMethod("dateProcess", String.class, String.class, long.class, long.class, long.class, long.class, long.class, String.class);
   method.setAccessible(true);
   method.invoke(per,sourceId,name, startTimeL,endTimeL,timeIteraPlusVal,keyVal,keyValIteraVal,keyUnit);
} catch(NoSuchMethodException e) {
} catch(IllegalAccessException e) {
} catch(InvocationTargetException e) {
}

} 

/** 
* 
* Method: diagramDate(String sourceId, String name, String startTime, String endTime, String format) 
* 
*/ 
@Test
public void testDiagramDate_year() throws Exception {
//TODO: Test goes here...
    PerformanceController per = new PerformanceController();
    String sourceId="1101ZTHX1MMEGJM1W1";
    String name="DNS.AttDnsQuery";
    String startTime="2017-11-15 06:30:00";
    String endTime="2017-11-16 06:30:00";
    String format="year";
try { 
   Method method = per.getClass().getDeclaredMethod("diagramDate", String.class, String.class, String.class, String.class, String.class);
   method.setAccessible(true); 
   method.invoke(per, sourceId,name,startTime,endTime,format);
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 

}




    @Test
    public void testDiagramDate_month() throws Exception {
//TODO: Test goes here...
        PerformanceController per = new PerformanceController();
        String sourceId="1101ZTHX1MMEGJM1W1";
        String name="DNS.AttDnsQuery";
        String startTime="2017-11-15 06:30:00";
        String endTime="2017-11-16 06:30:00";
        String format="month";
        try {
            Method method = per.getClass().getDeclaredMethod("diagramDate", String.class, String.class, String.class, String.class, String.class);
            method.setAccessible(true);
            method.invoke(per, sourceId,name,startTime,endTime,format);
        } catch(NoSuchMethodException e) {
        } catch(IllegalAccessException e) {
        } catch(InvocationTargetException e) {
        }

    }




    @Test
    public void testDiagramDate_day() throws Exception {
//TODO: Test goes here...
        PerformanceController per = new PerformanceController();
        String sourceId="1101ZTHX1E6M2QI4FZVTOXU";
        String name="Dn";
        String startTime="2017-11-15 06:30:00";
        String endTime="2017-09-16 06:30:00";
        String format="day";
        try {
            Method method = per.getClass().getDeclaredMethod("diagramDate", String.class, String.class, String.class, String.class, String.class);
            method.setAccessible(true);
            method.invoke(per, sourceId,name,startTime,endTime,format);
        } catch(NoSuchMethodException e) {
        } catch(IllegalAccessException e) {
        } catch(InvocationTargetException e) {
        }

    }




    @Test
    public void testDiagramDate_hour() throws Exception {
//TODO: Test goes here...
        PerformanceController per = new PerformanceController();
        String sourceId="1101ZTHX1E6M2QI4FZVTOXU";
        String name="rmUID";
        String startTime="2017-11-15 06:10:00";
        String endTime="2017-11-17 07:40:20";
        String format="hour";
        try {
            Method method = per.getClass().getDeclaredMethod("diagramDate", String.class, String.class, String.class, String.class, String.class);
            method.setAccessible(true);
            method.invoke(per, sourceId,name,startTime,endTime,format);
        } catch(NoSuchMethodException e) {
        } catch(IllegalAccessException e) {
        } catch(InvocationTargetException e) {
        }

    }




    @Test
    public void testDiagramDate_minute() throws Exception {
//TODO: Test goes here...
        PerformanceController per = new PerformanceController();
        String sourceId="1101ZTHX1MMEGJM1W1";
        String name="HO.AttOutInterMme";
        String startTime="2017-11-15 06:30:00";
        String endTime="2017-11-16 08:20:15";
        String format="minute";
        try {
            Method method = per.getClass().getDeclaredMethod("diagramDate", String.class, String.class, String.class, String.class, String.class);
            method.setAccessible(true);
            method.invoke(per, sourceId,name,startTime,endTime,format);
        } catch(NoSuchMethodException e) {
        } catch(IllegalAccessException e) {
        } catch(InvocationTargetException e) {
        }

    }




    @Test
    public void testDiagramDate_auto_hours() throws Exception {
//TODO: Test goes here...
        PerformanceController per = new PerformanceController();
        String sourceId="1101ZTHX1MMEGJM1W1";
        String name="IRATHO.AttIncFromGeran";
        String startTime="2017-11-19 06:10:00";
        String endTime="2017-11-19 09:59:59";
        String format="auto";
        try {
            Method method = per.getClass().getDeclaredMethod("diagramDate", String.class, String.class, String.class, String.class, String.class);
            method.setAccessible(true);
            method.invoke(per, sourceId,name,startTime,endTime,format);
        } catch(NoSuchMethodException e) {
        } catch(IllegalAccessException e) {
        } catch(InvocationTargetException e) {
        }

    }




    @Test
    public void testDiagramDate_auto_month2() throws Exception {
//TODO: Test goes here...
        PerformanceController per = new PerformanceController();
        String sourceId="1101ZTHX1MMEGJM1W1";
        String name="IRATHO.SuccOutToUtran";
        String startTime="2017-02-15 06:10:00";
        String endTime="2017-11-28 09:59:59";
        String format="auto";
        try {
            Method method = per.getClass().getDeclaredMethod("diagramDate", String.class, String.class, String.class, String.class, String.class);
            method.setAccessible(true);
            method.invoke(per, sourceId,name,startTime,endTime,format);
        } catch(NoSuchMethodException e) {
        } catch(IllegalAccessException e) {
        } catch(InvocationTargetException e) {
        }

    }




    @Test
    public void testDiagramDate_auto_month1() throws Exception {
//TODO: Test goes here...
        PerformanceController per = new PerformanceController();
        String sourceId="1101ZTHX1MMEGJM1W1";
        String name="MM.AttEpsAttach";
        String startTime="2017-09-10 06:10:00";
        String endTime="2017-11-19 07:59:59";
        String format="auto";
        try {
            Method method = per.getClass().getDeclaredMethod("diagramDate", String.class, String.class, String.class, String.class, String.class);
            method.setAccessible(true);
            method.invoke(per, sourceId,name,startTime,endTime,format);
        } catch(NoSuchMethodException e) {
        } catch(IllegalAccessException e) {
        } catch(InvocationTargetException e) {
        }

    }



    @Test
    public void testDiagramDate_auto_day1() throws Exception {
//TODO: Test goes here...
        PerformanceController per = new PerformanceController();
        String sourceId="1101ZTHX1MMEGJM1W1";
        String name="IRATHO.SuccIncFromUtran";
        String startTime="2017-10-17 06:10:00";
        String endTime="2017-10-19 07:59:59";
        String format="auto";
        try {
            Method method = per.getClass().getDeclaredMethod("diagramDate", String.class, String.class, String.class, String.class, String.class);
            method.setAccessible(true);
            method.invoke(per, sourceId,name,startTime,endTime,format);
        } catch(NoSuchMethodException e) {
        } catch(IllegalAccessException e) {
        } catch(InvocationTargetException e) {
        }

    }

} 
