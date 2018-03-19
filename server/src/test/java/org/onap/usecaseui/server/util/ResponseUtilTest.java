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
