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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/** 
* RestfulServices Tester. 
* 
* @author <Authors name> 
* @since <pre>���� 14, 2018</pre> 
* @version 1.0 
*/ 
public class RestfulServicesTest { 

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: create(String baseUrl, Class<T> clazz) 
* 
*/ 
@Test
public void testCreateForBaseUrlClazz() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: create(Class<T> clazz) 
* 
*/ 
@Test
public void testCreateClazz() throws Exception { 
//TODO: Test goes here...
    RestfulServices.create(RestfulServicesTest.class);
} 

/** 
* 
* Method: getMsbAddress() 
* 
*/ 
@Test
public void testGetMsbAddress() throws Exception { 
//TODO: Test goes here...
    RestfulServices.getMsbAddress();
} 

/** 
* 
* Method: extractBody(HttpServletRequest request) 
* 
*/ 
@Test
public void testExtractBody() throws Exception { 
//TODO: Test goes here...
    HttpServletRequest request = null;
    RestfulServices.extractBody(request);
} 


} 
