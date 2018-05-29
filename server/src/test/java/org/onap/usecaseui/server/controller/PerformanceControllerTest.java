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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import javax.servlet.http.HttpServletResponse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.onap.usecaseui.server.bean.PerformanceHeader;
import org.onap.usecaseui.server.service.PerformanceHeaderService;
import org.onap.usecaseui.server.service.PerformanceInformationService;

import com.fasterxml.jackson.core.JsonProcessingException;

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
	public void testGenerateDiagram() {
		try {
			controller.generateDiagram("sourceId", "2018-5-24 14:58:29", "2018-5-25 14:58:29", "nameParent", "day");
			controller.generateDiagram("sourceId", "2018-5-24 14:58:29", "2018-5-25 14:58:29", "nameParent", "hour");
			controller.generateDiagram("sourceId", "2018-5-24 14:58:29", "2018-5-25 14:58:29", "nameParent", "minute");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testGetSourceIds() {
		try {
			controller.getSourceIds();
			verify(phs,times(1)).queryAllSourceId();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testGetPerformanceData() throws JsonProcessingException {
	    HttpServletResponse response = mock(HttpServletResponse.class);
	    int currentPage = 1;
	    int pageSize=12;
	    String sourceId="uui_test_vm5";
	    String  sourceName="2b8957a6-46d4-4e91-8d50-17c29e8583ac";
	    String  priority="Normal";
	    String  startTime="1521678529000";
	    String  endTime="1521680329000";
	    PerformanceHeader header = new PerformanceHeader();


	    controller.getPerformanceData(response,currentPage,pageSize,sourceId,sourceName,priority,startTime,endTime);
	    controller.getPerformanceData(response,currentPage,pageSize,null,null,null,null,null);
	    verify(phs,times(1)).queryPerformanceHeader(header,currentPage,pageSize);

}

	@Test
	public void testGetNames() {
		try {
			controller.getNames("vnf_test_3");
			verify(pihs,times(1)).queryDateBetween("vnf_test_3",null,null,null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testGetPerformanceHeaderDetail() {
		try {
			controller.getPerformanceHeaderDetail("0a573f09d50f46adaae0c10e741fea4d");
			verify(phs,times(1)).getPerformanceHeaderById("0a573f09d50f46adaae0c10e741fea4d");
			verify(pihs,times(1)).getAllPerformanceInformationByHeaderId("0a573f09d50f46adaae0c10e741fea4d");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}