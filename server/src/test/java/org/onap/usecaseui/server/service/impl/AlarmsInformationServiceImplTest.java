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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.onap.usecaseui.server.bean.AlarmsInformation;

/** 
* AlarmsInformationServiceImpl Tester. 
* 
* @author <Authors name> 
* @since <pre>8, 2018</pre>
* @version 1.0 
*/
public class AlarmsInformationServiceImplTest {
	AlarmsInformationServiceImpl alarmsInformationServiceImpl = null;
	private static final long serialVersionUID = 1L;

	@Before
	public void before() throws Exception {
		alarmsInformationServiceImpl = new AlarmsInformationServiceImpl();
	}

	@After
	public void after() throws Exception {
	}

	@Test
	public void testSaveAlarmsInformation() throws Exception {
		try {
			AlarmsInformation ai = null;
			alarmsInformationServiceImpl.saveAlarmsInformation(ai);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testUpdateAlarmsInformation() throws Exception {
		try {
			AlarmsInformation ai = null;
			alarmsInformationServiceImpl.updateAlarmsInformation(ai);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testGetAllCount() throws Exception {
		try {
			AlarmsInformation ai = new AlarmsInformation();
			ai.setName("");
			ai.setValue("");
			ai.setSourceId("");
			ai.setStartEpochMicroSec("");;
			ai.setLastEpochMicroSec("");;
			alarmsInformationServiceImpl.getAllCount(ai, 1, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testQueryId() throws Exception {
		try {
			String[] id = {"1", "2", "3"};
			alarmsInformationServiceImpl.queryId(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testQueryDateBetween() throws Exception {
		try {
			alarmsInformationServiceImpl.queryDateBetween("sourceId", "startTime", "endTime", "level");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetAllAlarmsInformationByHeaderId() throws Exception {
		try {
			alarmsInformationServiceImpl.getAllAlarmsInformationByHeaderId(null);
			alarmsInformationServiceImpl.getAllAlarmsInformationByHeaderId("headerId");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
