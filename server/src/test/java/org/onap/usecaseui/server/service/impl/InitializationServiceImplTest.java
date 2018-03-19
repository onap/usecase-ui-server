package org.onap.usecaseui.server.service.impl; 

import org.junit.Test; 
import org.junit.Before; 
import org.junit.After;
import org.junit.runner.RunWith;
import org.onap.usecaseui.server.UsecaseuiServerApplication;
import org.onap.usecaseui.server.service.InitializationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/** 
* InitializationServiceImpl Tester. 
* 
* @author <Authors name> 
* @since <pre>���� 15, 2018</pre> 
* @version 1.0 
*/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = UsecaseuiServerApplication.class)
@WebAppConfiguration
public class InitializationServiceImplTest { 

    @Autowired
    private InitializationService initializationService;
@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: initialize() 
* 
*/ 
@Test
public void testInitialize() throws Exception { 
//TODO: Test goes here...
    initializationService.initialize();
} 


} 
