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

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.onap.usecaseui.server.bean.AlarmsHeader;
import org.onap.usecaseui.server.bean.AlarmsInformation;
import org.onap.usecaseui.server.bo.AlarmBo;
import org.onap.usecaseui.server.service.AlarmsHeaderService;
import org.onap.usecaseui.server.service.AlarmsInformationService;
import org.onap.usecaseui.server.wrapper.AlarmWrapper;
import org.powermock.api.support.membermodification.MemberModifier;

public class AlarmControllerTest {

	AlarmController controller = null;
    AlarmsHeaderService service;
    AlarmsInformationService alService;

	@Before
	public void before() throws Exception {
		controller = new AlarmController();
	    service = mock(AlarmsHeaderService.class);
	    alService = mock(AlarmsInformationService.class);
	    controller.setAlarmsHeaderService(service);
	    controller.setAlarmsInformationService(alService);

	}

	@After
	public void after() throws Exception {
	}

	@Test
	public void testIndex() throws Exception {
		controller.index();
	}

	@Test
	public void testGetAlarmData() throws Exception {
		try {
			controller.getAlarmData("sourceName", "priority", "startTime", "endTime", "vfStatus", "1", "10");
			controller.getAlarmData(null, null, null, null, null, "1", "10");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testGetSourceId() throws Exception {
		try {
			controller.getSourceId();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testGenDiagram() throws Exception {
		try {
			controller.diagram("sourceId", "2018-5-24 14:58:29", "2018-5-25 14:58:29", "day");
			controller.diagram("sourceId", "2018-5-24 14:58:29", "2018-5-25 14:58:29", "hour");
			controller.diagram("sourceId", "2018-5-24 14:58:29", "2018-5-25 14:58:29","month");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testStatusCount() throws Exception {
		try {
			controller.getStatusCount();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testGetAlarmsHeaderDetail() throws Exception {
		try {
			controller.getAlarmsHeaderDetail("33a8353381bb4e8fabbc739f9f7e02bf");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testAlarmWrapper(){
		AlarmWrapper  alarmWrapper = new AlarmWrapper();
		AlarmsHeader alarmsHeader =new AlarmsHeader();
		AlarmsInformation alarmsInformation = new AlarmsInformation();
		int currentPage  = 1;
		int pageSize  = 100;
		alarmWrapper.setAlarmsHeader(alarmsHeader);
		alarmWrapper.setAlarmsInformation(alarmsInformation);
		alarmWrapper.setCurrentPage(currentPage);
		alarmWrapper.setPageSize(pageSize);
		alarmWrapper.getAlarmsHeader();
		alarmWrapper.getAlarmsInformation();
		alarmWrapper.getCurrentPage();
		alarmWrapper.getPageSize();
	}
	
	@Test
	public void testAlarmBo (){
		AlarmsHeader alarmsHeader =new AlarmsHeader();
		List<AlarmsInformation> alarmsInformation = new ArrayList<>();
		AlarmBo alarmBo2 = new AlarmBo(alarmsHeader,alarmsInformation);
		AlarmBo alarmBo = new AlarmBo();
		alarmBo.setAlarmsHeader(alarmsHeader);
		alarmBo.setAlarmsInformation(alarmsInformation);
		alarmBo.getAlarmsHeader();
		alarmBo.getAlarmsInformation();
	}
}
