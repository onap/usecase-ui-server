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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.ParseException;

import jakarta.servlet.http.HttpServletResponse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.onap.usecaseui.server.bean.PerformanceHeader;
import org.onap.usecaseui.server.service.PerformanceHeaderService;
import org.onap.usecaseui.server.service.PerformanceInformationService;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.onap.usecaseui.server.util.Page;

 public class PerformanceControllerTest {

	PerformanceController  controller;
	PerformanceHeaderService phs;
	PerformanceInformationService pihs;
	@Before
	public void before() throws Exception {
		controller = new PerformanceController();
		phs=mock(PerformanceHeaderService.class);
		pihs =mock(PerformanceInformationService.class);
		controller.setPerformanceHeaderService(phs);
		controller.setPerformanceInformationService(pihs);
	}

	@After
	public void after() throws Exception {
	}

	@Test
	public void testGetPerformanceData() throws JsonProcessingException, ParseException {
	    HttpServletResponse response = mock(HttpServletResponse.class);
	    int currentPage = 1;
	    int pageSize=12;
	    String  sourceName="2b8957a6-46d4-4e91-8d50-17c29e8583ac";
	    PerformanceHeader header = new PerformanceHeader.PerformanceHeaderBuilder().createPerformanceHeader();

        when(phs.queryPerformanceHeader(any(),eq(currentPage),eq(pageSize))).thenReturn(new Page<>());
	    controller.getPerformanceData(currentPage+"",pageSize+"",sourceName,null,null);
	    //controller.getPerformanceData(currentPage+"",pageSize+"",null,null,null);
	    verify(phs,times(1)).queryPerformanceHeader(any(),eq(currentPage),eq(pageSize));

	}
	
	@Test
	public void testGetPerformanceSourceNames() throws JsonProcessingException{
		String currentPage="1";
		String pageSize="10";
		String sourceName="";
		controller.getPerformanceSourceNames(currentPage, pageSize, sourceName);
	}
	
	@Test
	public void testGetSourceIds(){
		controller.getSourceIds();
	}
	
	@Test
	public void testGetPerformanceHeaderDetail() {
		try {
			controller.getPerformanceHeaderDetail("0a573f09d50f46adaae0c10e741fea4d");
			verify(phs,times(1)).getPerformanceHeaderById("0a573f09d50f46adaae0c10e741fea4d");
			verify(pihs,times(0)).getAllPerformanceInformationByHeaderId("0a573f09d50f46adaae0c10e741fea4d");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}