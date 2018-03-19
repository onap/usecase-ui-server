package org.onap.usecaseui.server.util; 

import org.junit.Test; 
import org.junit.Before; 
import org.junit.After;

import javax.servlet.http.HttpServletResponse;

/** 
* ResponseUtil Tester. 
* 
* @author <Authors name> 
* @since <pre>���� 14, 2018</pre> 
* @version 1.0 
*/ 
public class ResponseUtilTest { 

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: responseDownload(String filePath, HttpServletResponse response) 
* 
*/ 
@Test
public void testResponseDownload() throws Exception { 
//TODO: Test goes here...
    HttpServletResponse httpServletResponse = null;
    ResponseUtil.responseDownload("D:/topology.csv", httpServletResponse);
} 


} 
