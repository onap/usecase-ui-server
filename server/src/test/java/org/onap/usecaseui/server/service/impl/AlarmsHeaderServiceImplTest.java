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

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.onap.usecaseui.server.bean.AlarmsHeader;

import mockit.Mock;
import mockit.MockUp;

/** 
* AlarmsHeaderServiceImpl Tester. 
* 
* @author <Authors name> 
* @since <pre>8, 2018</pre>
* @version 1.0 
*/
public class AlarmsHeaderServiceImplTest {
	AlarmsHeaderServiceImpl alarmsHeaderServiceImpl = null;
	private static final long serialVersionUID = 1L;

	@Before
	public void before() throws Exception {
		alarmsHeaderServiceImpl = new AlarmsHeaderServiceImpl();
	}

	@After
	public void after() throws Exception {
	}

	@Test
	public void testSaveAlarmsHeader() throws Exception {
		try {
			AlarmsHeader ah = null;
			alarmsHeaderServiceImpl.saveAlarmsHeader(ah);
			alarmsHeaderServiceImpl.saveAlarmsHeader(new AlarmsHeader());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testUpdateAlarmsHeader() throws Exception {
		try {
			AlarmsHeader ah = null;
			alarmsHeaderServiceImpl.updateAlarmsHeader(ah);
			alarmsHeaderServiceImpl.updateAlarmsHeader(new AlarmsHeader());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testGetAllCount() throws Exception {
		try {
			AlarmsHeader ah = new AlarmsHeader();
			ah.setVersion("va2");
			ah.setEventName("a");
			ah.setAlarmCondition("ea");
			ah.setDomain("asb");
			ah.setEventId("1119");
			ah.setNfcNamingCode("std");
			ah.setNfNamingCode("cout");
			ah.setSourceId("123");
			ah.setSourceName("eggs");
			ah.setReportingEntityId("112");
			ah.setReportingEntityName("asfs");
			ah.setPriority("cs");
			ah.setStartEpochMicrosec("wallet");
			ah.setLastEpochMicroSec("csa");
			ah.setSequence("cgg");
			ah.setFaultFieldsVersion("v1");
			ah.setEventServrity("s");
			ah.setEventType("q");
			ah.setEventCategory("s");
			ah.setSpecificProblem("especially");
			ah.setAlarmInterfaceA("cs");
			ah.setStatus("1");
			ah.setVfStatus("1");
			ah.setEventSourceType("q");
			alarmsHeaderServiceImpl.getAllCount(ah, 1, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testQueryAlarmsHeader() throws Exception {
		try {
			AlarmsHeader ah = new AlarmsHeader();
			ah.setVersion("va2");
			ah.setEventName("a");
			ah.setAlarmCondition("ea");
			ah.setDomain("asb");
			ah.setEventId("1119");
			ah.setNfcNamingCode("std");
			ah.setNfNamingCode("cout");
			ah.setSourceId("123");
			ah.setSourceName("eggs");
			ah.setReportingEntityId("112");
			ah.setReportingEntityName("asfs");
			ah.setPriority("cs");
			ah.setStartEpochMicrosec("wallet");
			ah.setLastEpochMicroSec("csa");
			ah.setSequence("cgg");
			ah.setFaultFieldsVersion("v1");
			ah.setEventServrity("s");
			ah.setEventType("q");
			ah.setEventCategory("s");
			ah.setSpecificProblem("especially");
			ah.setAlarmInterfaceA("cs");
			ah.setStatus("1");
			ah.setVfStatus("1");
			ah.setEventSourceType("q");
			alarmsHeaderServiceImpl.queryAlarmsHeader(ah, 1, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testQueryId() throws Exception {
		try {
			String[] id = {"1", "2", "3"};
			alarmsHeaderServiceImpl.queryId(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetAlarmsHeaderById() throws Exception {
		try {
			alarmsHeaderServiceImpl.getAlarmsHeaderById(null);
			alarmsHeaderServiceImpl.getAlarmsHeaderById("1e578e892ebf4bcdbdd3e71fbad2a202");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testQueryStatusCount() throws Exception {
		try {
			alarmsHeaderServiceImpl.queryStatusCount(null);
			alarmsHeaderServiceImpl.queryStatusCount("active");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testUpdateAlarmsHeader2018() throws Exception {
		try {
	        Long l = System.currentTimeMillis();

	        Timestamp date_get = new Timestamp(l);
			alarmsHeaderServiceImpl.updateAlarmsHeader2018(null, null, null, null, null, null, null);
			alarmsHeaderServiceImpl.updateAlarmsHeader2018("close",date_get,"1527145109000", "1527145109000", "eventName","reportingEntityName","specificProblem");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
