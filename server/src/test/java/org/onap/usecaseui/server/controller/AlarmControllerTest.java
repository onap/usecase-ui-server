package org.onap.usecaseui.server.controller; 

import org.junit.Test; 
import org.junit.Before; 
import org.junit.After;
import org.onap.usecaseui.server.bean.AlarmsHeader;
import org.onap.usecaseui.server.service.AlarmsHeaderService;
import org.onap.usecaseui.server.service.AlarmsInformationService;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.mockito.Mockito.*;

/** 
* AlarmController Tester. 
* 
* @author <Authors name> 
* @since <pre>���� 6, 2018</pre> 
* @version 1.0 
*/ 
public class AlarmControllerTest {
    AlarmController controller = new AlarmController();
    AlarmsHeaderService service;
    AlarmsInformationService alService;
@Before
public void before() throws Exception {
    service = mock(AlarmsHeaderService.class);
    alService = mock(AlarmsInformationService.class);
    controller.setAlarmsHeaderService(service);
    controller.setAlarmsInformationService(alService);
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
* Method: getAlarmData(@PathVariable int currentPage, @PathVariable int pageSize, @PathVariable(required = false) String sourceId, @PathVariable(required = false) String sourceName, @PathVariable(required = false) String priority, @PathVariable(required = false) String startTime, @PathVariable(required = false) String endTime, @PathVariable(required = false) String vfStatus) 
* 
*/ 
@Test
public void testGetAlarmData() throws Exception { 
//TODO: Test goes here...
    AlarmController controller = new AlarmController();
    AlarmsHeaderService service = mock(AlarmsHeaderService.class);
    controller.setAlarmsHeaderService(service);
    AlarmsHeader header = new AlarmsHeader();
    int  currentPage=1;
    int  pageSize=10;
    //String  sourceId="shentao-test-1003";
    //String  sourceName="shentao-test-1003";
    //String  priority="High";
    //String  startTime="2017-10-31 09:51";
    //String  endTime="2018-03-15 00:00";
    //String  vfStatus="Active";
    String  sourceId="11694_113";
    String  sourceName="11694_113";
    String  priority="High";
    String  startTime="2017-10-31 09:52:15";
    String  endTime="2017-11-15 15:27:16";
    String  vfStatus="Medium";
    /*String  sourceId=null;
    String  sourceName=null;
    String  priority=null;
    String  startTime=null;
    String  endTime=null;
    String  vfStatus=null;*/

    header.setPriority(priority);
    header.setStatus(vfStatus);
    header.setSourceId(sourceId);
    header.setSourceName(sourceName);



    controller.getAlarmData(currentPage,pageSize,sourceId,sourceName,priority,startTime,endTime,vfStatus);
    verify(service,times(1)).queryAlarmsHeader(header,currentPage,pageSize);
}

    @Test
    public void testGetAlarmData_a() throws Exception {
//TODO: Test goes here...

        AlarmController controller = new AlarmController();
        AlarmsHeaderService service = mock(AlarmsHeaderService.class);
        controller.setAlarmsHeaderService(service);
        AlarmsHeader header = new AlarmsHeader();
        int  currentPage=1;
        int  pageSize=10;

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



        controller.getAlarmData(currentPage,pageSize,sourceId,sourceName,priority,startTime,endTime,vfStatus);
        verify(service,times(1)).queryAlarmsHeader(header,currentPage,pageSize);
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
* Method: getSourceId() 
* 
*/ 
@Test
public void testGetSourceId() throws Exception { 
//TODO: Test goes here...
    controller.getSourceId();
    AlarmsHeader alarmsHeader = new AlarmsHeader();
    verify(service,times(1)).queryAlarmsHeader(alarmsHeader,1,12);
} 

/** 
* 
* Method: genDiagram(@RequestParam String sourceId, @RequestParam String startTime, @RequestParam String endTime, @RequestParam String showMode) 
* 
*/ 
@Test
public void testGenDiagram() throws Exception { 
//TODO: Test goes here...
    String sourceId="";
    String startTime="";
    String endTime="";
    String nameParent="";
    String format="";
    controller.genDiagram(sourceId,startTime,endTime,format);
}


    @Test
    public void testGenDiagram_a() throws Exception {
//TODO: Test goes here...
        String sourceId="";
        String startTime="2017-10-15";
        String endTime="2017-11-15";
        String nameParent="";
        String format="";
        controller.genDiagram(sourceId,startTime,endTime,"0000");
    }


    /**
* 
* Method: dateProcess(String sourceId, long startTimeL, long endTimeL, long timeIteraPlusVal, long keyVal, long keyValIteraVal, String keyUnit) 
* 
*/ 
@Test
public void testDateProcess() throws Exception { 
//TODO: Test goes here... 
    AlarmController alarmController = new AlarmController();
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
   Method method = alarmController.getClass().getDeclaredMethod("dateProcess", String.class, long.class, long.class, long.class, long.class, long.class, String.class);
   method.setAccessible(true); 
   method.invoke(alarmController,sourceId,name, startTimeL,endTimeL,timeIteraPlusVal,keyVal,keyValIteraVal,keyUnit);
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 

} 

/** 
* 
* Method: diagramDate(String sourceId, String startTime, String endTime, String format) 
* 
*/ 
@Test
public void testDiagramDate_year() throws Exception {
//TODO: Test goes here... 
    AlarmController alarmController = new AlarmController();
    String sourceId="1101ZTHX1MMEGJM1W1";
    String name="DNS.AttDnsQuery";
    String startTime="2017-11-15 06:30:00";
    String endTime="2017-11-16 06:30:00";
    String format="year";

   // String sourceId, long startTimeL, long endTimeL, long timeIteraPlusVal, long keyVal, long keyValIteraVal, String keyUnit
try { 
   Method method = alarmController.getClass().getDeclaredMethod("diagramDate", String.class, String.class, String.class, String.class);
   method.setAccessible(true); 
   method.invoke(alarmController, sourceId,startTime,endTime,format);
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) {
} 

}




    @Test
    public void testDiagramDate_month() throws Exception {
//TODO: Test goes here...
        AlarmController alarmController = new AlarmController();
        String sourceId="1101ZTHX1MMEGJM1W1";
        String name="DNS.AttDnsQuery";
        String startTime="2017-11-15 06:30:00";
        String endTime="2017-11-16 06:30:00";
        String format="month";

        // String sourceId, long startTimeL, long endTimeL, long timeIteraPlusVal, long keyVal, long keyValIteraVal, String keyUnit
        try {
            Method method = alarmController.getClass().getDeclaredMethod("diagramDate", String.class, String.class, String.class, String.class);
            method.setAccessible(true);
            method.invoke(alarmController, sourceId,startTime,endTime,format);
        } catch(NoSuchMethodException e) {
        } catch(IllegalAccessException e) {
        } catch(InvocationTargetException e) {
        }

    }





    @Test
    public void testDiagramDate_day() throws Exception {
//TODO: Test goes here...
        AlarmController alarmController = new AlarmController();
        String sourceId="1101ZTHX1E6M2QI4FZVTOXU";
        String name="Dn";
        String startTime="2017-11-15 06:30:00";
        String endTime="2017-09-16 06:30:00";
        String format="day";

        // String sourceId, long startTimeL, long endTimeL, long timeIteraPlusVal, long keyVal, long keyValIteraVal, String keyUnit
        try {
            Method method = alarmController.getClass().getDeclaredMethod("diagramDate", String.class, String.class, String.class, String.class);
            method.setAccessible(true);
            method.invoke(alarmController, sourceId,startTime,endTime,format);
        } catch(NoSuchMethodException e) {
        } catch(IllegalAccessException e) {
        } catch(InvocationTargetException e) {
        }

    }




    @Test
    public void testDiagramDate_hour() throws Exception {
//TODO: Test goes here...
        AlarmController alarmController = new AlarmController();
        String sourceId="1101ZTHX1E6M2QI4FZVTOXU";
        String name="rmUID";
        String startTime="2017-11-15 06:10:00";
        String endTime="2017-11-17 07:40:20";
        String format="hour";

        // String sourceId, long startTimeL, long endTimeL, long timeIteraPlusVal, long keyVal, long keyValIteraVal, String keyUnit
        try {
            Method method = alarmController.getClass().getDeclaredMethod("diagramDate", String.class, String.class, String.class, String.class);
            method.setAccessible(true);
            method.invoke(alarmController, sourceId,startTime,endTime,format);
        } catch(NoSuchMethodException e) {
        } catch(IllegalAccessException e) {
        } catch(InvocationTargetException e) {
        }

    }





    @Test
    public void testDiagramDate_minute() throws Exception {
//TODO: Test goes here...
        AlarmController alarmController = new AlarmController();
        String sourceId="1101ZTHX1MMEGJM1W1";
        String name="HO.AttOutInterMme";
        String startTime="2017-11-15 06:30:00";
        String endTime="2017-11-16 08:20:15";
        String format="minute";

        // String sourceId, long startTimeL, long endTimeL, long timeIteraPlusVal, long keyVal, long keyValIteraVal, String keyUnit
        try {
            Method method = alarmController.getClass().getDeclaredMethod("diagramDate", String.class, String.class, String.class, String.class);
            method.setAccessible(true);
            method.invoke(alarmController, sourceId,startTime,endTime,format);
        } catch(NoSuchMethodException e) {
        } catch(IllegalAccessException e) {
        } catch(InvocationTargetException e) {
        }

    }





    @Test
    public void testDiagramDate_auto_hours() throws Exception {
//TODO: Test goes here...
        AlarmController alarmController = new AlarmController();
        String sourceId="1101ZTHX1MMEGJM1W1";
        String name="IRATHO.AttIncFromGeran";
        String startTime="2017-11-19 06:10:00";
        String endTime="2017-11-19 09:59:59";
        String format="auto";

        // String sourceId, long startTimeL, long endTimeL, long timeIteraPlusVal, long keyVal, long keyValIteraVal, String keyUnit
        try {
            Method method = alarmController.getClass().getDeclaredMethod("diagramDate", String.class, String.class, String.class, String.class);
            method.setAccessible(true);
            method.invoke(alarmController, sourceId,startTime,endTime,format);
        } catch(NoSuchMethodException e) {
        } catch(IllegalAccessException e) {
        } catch(InvocationTargetException e) {
        }

    }




    @Test
    public void testDiagramDate_auto_month2() throws Exception {
//TODO: Test goes here...
        AlarmController alarmController = new AlarmController();
        String sourceId="1101ZTHX1MMEGJM1W1";
        String name="IRATHO.SuccOutToUtran";
        String startTime="2017-02-15 06:10:00";
        String endTime="2017-11-28 09:59:59";
        String format="auto";

        // String sourceId, long startTimeL, long endTimeL, long timeIteraPlusVal, long keyVal, long keyValIteraVal, String keyUnit
        try {
            Method method = alarmController.getClass().getDeclaredMethod("diagramDate", String.class, String.class, String.class, String.class);
            method.setAccessible(true);
            method.invoke(alarmController, sourceId,startTime,endTime,format);
        } catch(NoSuchMethodException e) {
        } catch(IllegalAccessException e) {
        } catch(InvocationTargetException e) {
        }

    }





    @Test
    public void testDiagramDate_auto_month1() throws Exception {
//TODO: Test goes here...
        AlarmController alarmController = new AlarmController();
        String sourceId="1101ZTHX1MMEGJM1W1";
        String name="MM.AttEpsAttach";
        String startTime="2017-09-10 06:10:00";
        String endTime="2017-11-19 07:59:59";
        String format="auto";

        // String sourceId, long startTimeL, long endTimeL, long timeIteraPlusVal, long keyVal, long keyValIteraVal, String keyUnit
        try {
            Method method = alarmController.getClass().getDeclaredMethod("diagramDate", String.class, String.class, String.class, String.class);
            method.setAccessible(true);
            method.invoke(alarmController, sourceId,startTime,endTime,format);
        } catch(NoSuchMethodException e) {
        } catch(IllegalAccessException e) {
        } catch(InvocationTargetException e) {
        }

    }




    @Test
    public void testDiagramDate_auto_day1() throws Exception {
//TODO: Test goes here...
        AlarmController alarmController = new AlarmController();
        String sourceId="1101ZTHX1MMEGJM1W1";
        String name="IRATHO.SuccIncFromUtran";
        String startTime="2017-10-17 06:10:00";
        String endTime="2017-10-19 07:59:59";
        String format="auto";

        // String sourceId, long startTimeL, long endTimeL, long timeIteraPlusVal, long keyVal, long keyValIteraVal, String keyUnit
        try {
            Method method = alarmController.getClass().getDeclaredMethod("diagramDate", String.class, String.class, String.class, String.class);
            method.setAccessible(true);
            method.invoke(alarmController, sourceId,startTime,endTime,format);
        } catch(NoSuchMethodException e) {
        } catch(IllegalAccessException e) {
        } catch(InvocationTargetException e) {
        }

    }

} 
