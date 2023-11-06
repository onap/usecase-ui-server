/**
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
package org.onap.usecaseui.server.service.impl;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.onap.usecaseui.server.bean.PerformanceInformation;
import org.onap.usecaseui.server.util.DateUtils;

/** 
* PerformanceInformationServiceImpl Tester. 
* 
* @author <Authors name> 
* @since <pre>8, 2018</pre>
* @version 1.0 
*/
public class PerformanceInformationServiceImplTest {
	PerformanceInformationServiceImpl performanceInformationServiceImpl = null;
	private static final long serialVersionUID = 1L;

	@Before
	public void before() throws Exception {
		performanceInformationServiceImpl = new PerformanceInformationServiceImpl();
	}

	@After
	public void after() throws Exception {
	}

	@Test
	public void testSavePerformanceInformation() throws Exception {
		try {
			PerformanceInformation pi = null;
			performanceInformationServiceImpl.savePerformanceInformation(pi);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testUpdatePerformanceInformation() throws Exception {
		try {
			PerformanceInformation pi = null;
			performanceInformationServiceImpl.updatePerformanceInformation(pi);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testQueryId() throws Exception {
		try {
			String[] id = {"1", "2", "3"};
			performanceInformationServiceImpl.queryId(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testQueryDateBetween() throws Exception {
		try {
			performanceInformationServiceImpl.queryDateBetween("eventId", DateUtils.now(), DateUtils.now());
			performanceInformationServiceImpl.queryDateBetween("resourceId", "name", "startTime", "endTime");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testQueryDataBetweenSum() throws Exception {
		try {
			performanceInformationServiceImpl.queryDataBetweenSum("eventId", "name", DateUtils.now(), DateUtils.now());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testQueryMaxValueByBetweenDate() throws Exception {
		try {
			performanceInformationServiceImpl.queryMaxValueByBetweenDate("", "","","");
			performanceInformationServiceImpl.queryMaxValueByBetweenDate("eventId", "name","1527151520000","1527151620000");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetAllPerformanceInformationByHeaderId() throws Exception {
		try {
			performanceInformationServiceImpl.getAllPerformanceInformationByHeaderId("0a573f09d50f46adaae0c10e741fea4d");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
