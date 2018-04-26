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

import java.util.Arrays;
import java.util.List;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.onap.usecaseui.server.bean.PerformanceHeader;
import org.onap.usecaseui.server.bean.PerformanceInformation;
import org.onap.usecaseui.server.service.PerformanceHeaderService;
import org.onap.usecaseui.server.service.PerformanceInformationService;
import org.onap.usecaseui.server.util.Page;

import mockit.Mock;
import mockit.MockUp;

public class PerformanceControllerTest {

	PerformanceController controller = null;

	@Before
	public void before() throws Exception {
		controller = new PerformanceController();

		new MockUp<PerformanceHeaderService>() {
			@Mock
			public Page<PerformanceHeader> queryPerformanceHeader(PerformanceHeader performanceHeder, int currentPage, int pageSize) {
				return new Page<PerformanceHeader>();
			}
			@Mock
			public List<PerformanceHeader> queryId(String[] id) {
				PerformanceHeader ph = new PerformanceHeader();
				return Arrays.asList(ph);
			}
			@Mock
			public List<String> queryAllSourceId() {
				String str = "abc";
				return Arrays.asList(str);
			}
		};
		new MockUp<PerformanceInformationService>() {
			@Mock
			public Page<PerformanceInformation> queryPerformanceInformation(PerformanceInformation performanceInformation, int currentPage, int pageSize) {
				return new Page<PerformanceInformation>();
			}
			@Mock
			public int queryDataBetweenSum(String eventId, String name, Date startDate, Date endDate) {
				return 1;
			}
			@Mock
			public List<PerformanceInformation> queryDateBetween(String resourceId, String name, String startTime, String endTime) {
				PerformanceInformation pi = new PerformanceInformation();
				return Arrays.asList(pi);
			}
		};
	}

	@After
	public void after() throws Exception {
	}

	@Test
	public void testGenerateDiagram() {
		try {
			controller.generateDiagram("hour", "eventId");
			controller.generateDiagram("day", "eventId");
			controller.generateDiagram("month", "eventId");
			controller.generateDiagram("year", "eventId");
			controller.generateDiagram("sourceId", "startTime", "endTime", "nameParent", "nameChild");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testGetSourceIds() {
		try {
			controller.getSourceIds();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testGetNames() {
		try {
			controller.getNames("sourceId");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}