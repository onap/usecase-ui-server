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
import java.util.Date;

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
    @Resource(name = "PerformanceInformationService")
    PerformanceInformationService performanceInformationService;
@Before
public void before() throws Exception { 
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
    System.out.println(performanceInformationService.savePerformanceInformation(a));

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
    System.out.println(performanceInformationService.updatePerformanceInformation(a));
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


    performanceInformationService.getAllCount(performanceInformation,0,12);

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
    performanceInformationService.queryPerformanceInformation(a, 1, 100)
            .getList().forEach(al -> System.out.println(al.getValue()));
} 

/** 
* 
* Method: queryId(String[] id) 
* 
*/ 
@Test
public void testQueryId() throws Exception { 
//TODO: Test goes here...
    performanceInformationService.queryId(new String[]{"2202"})
            .forEach(ai -> System.out.println(ai.getCreateTime()));
} 

/** 
* 
* Method: queryDateBetween(String eventId, Date startDate, Date endDate) 
* 
*/ 
@Test
public void testQueryDateBetweenForEventIdStartDateEndDate() throws Exception { 
//TODO: Test goes here...
    performanceInformationService.queryDateBetween("1101ZTHX1MMEGJM1W1",new Date("2017-11-15 06:30:00"),new Date("2017-11-15 14:45:10"));
} 

/** 
* 
* Method: queryDateBetween(String resourceId, String name, String startTime, String endTime) 
* 
*/ 
@Test
public void testQueryDateBetweenForResourceIdNameStartTimeEndTime() throws Exception { 
//TODO: Test goes here...
    performanceInformationService.queryDateBetween("1101ZTHX1MMEGJM1W1",new Date("2017-11-15 06:30:00"),new Date("2017-11-15 14:45:10"));

} 

/** 
* 
* Method: queryMaxValueByBetweenDate(String sourceId, String name, String startTime, String endTime) 
* 
*/ 
@Test
public void testQueryMaxValueByBetweenDate() throws Exception { 
//TODO: Test goes here...
    performanceInformationService.queryDateBetween("2202", DateUtils.stringToDate("2017-10-15 01:00:00"), DateUtils.stringToDate("2017-10-15 02:00:00")).forEach(p -> System.out.println(p));

} 


} 
