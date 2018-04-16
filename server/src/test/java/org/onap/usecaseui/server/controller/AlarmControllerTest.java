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
import org.onap.usecaseui.server.bean.AlarmsHeader;
import org.onap.usecaseui.server.bean.AlarmsInformation;
import org.onap.usecaseui.server.service.AlarmsHeaderService;
import org.onap.usecaseui.server.service.AlarmsInformationService;
import org.onap.usecaseui.server.util.Page;

import java.util.*;

import mockit.Mock;
import mockit.MockUp;

public class AlarmControllerTest {

	AlarmController controller = null;

	@Before
	public void before() throws Exception {
		controller = new AlarmController();

		new MockUp<AlarmsHeaderService>() {
			@Mock
			public Page<AlarmsHeader> queryAlarmsHeader(AlarmsHeader alarmsHeader, int currentPage, int pageSize) {
				return new Page<AlarmsHeader>();
			}
			@Mock
			public List<AlarmsHeader> queryId(String[] id) {
				AlarmsHeader ah = new AlarmsHeader();
				return Arrays.asList(ah);
			}
		};
		new MockUp<AlarmsInformationService>() {
			@Mock
			public Page<AlarmsInformation> queryAlarmsInformation(AlarmsInformation alarmsInformation, int currentPage, int pageSize) {
				return new Page<AlarmsInformation>();
			}
			@Mock
			public List<Map<String,String>> queryDateBetween(String sourceId, String startTime, String endTime) {
				Map<String,String> map = new HashMap<String,String>();
				return Arrays.asList(map);
			}
		};
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
		controller.getAlarmData("sourceId", "sourceName", "priority", "startTime", "endTime", "vfStatus", 1, 10);
		controller.getAlarmData(null, null, null, null, null, null, 1, 10);
	}

	@Test
	public void testGetSourceId() throws Exception {
		controller.getSourceId();
	}

	@Test
	public void testGenDiagram() throws Exception {
		controller.genDiagram("sourceId", "startTime", "endTime");
	}
}
