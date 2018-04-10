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
import org.junit.runner.RunWith;
import org.onap.usecaseui.server.UsecaseuiServerApplication;
import org.onap.usecaseui.server.bean.AlarmsHeader;
import org.onap.usecaseui.server.service.impl.AlarmsHeaderServiceImpl;
import org.onap.usecaseui.server.util.DateUtils;
import org.onap.usecaseui.server.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

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

		MockUp<Transaction> mockUpTransaction = new MockUp<Transaction>() {
			@Mock
			public void commit() {
			}
		};
		MockUp<Query> mockUpQuery = new MockUp<Query>() {
		};
		new MockUp<Query>() {
			@Mock
			public Query setString(String name, String value) {
				return mockUpQuery.getMockInstance();
			}
			@Mock
			public Query setDate(String name, Date value) {
				return mockUpQuery.getMockInstance();
			}
			@Mock
			public Query setInteger(String name, int value) {
				return mockUpQuery.getMockInstance();
			}
			@Mock
			public int executeUpdate() {
				return 0;
			}
			@Mock
			public Query setMaxResults(int value) {
				return mockUpQuery.getMockInstance();
			}
			@Mock
			public Query setFirstResult(int firstResult) {
				return mockUpQuery.getMockInstance();
			}
			@Mock
			public Query setParameterList(String name, Object[] values) {
				return mockUpQuery.getMockInstance();
			}
			@Mock
			public List<AlarmsHeader> list() {
				AlarmsHeader ah = new AlarmsHeader();
				return Arrays.asList(ah);
			}
			@Mock
			public Object uniqueResult() {
				return "0";
			}
		};
		MockUp<Session> mockedSession = new MockUp<Session>() {
			@Mock
			public Query createQuery(String sql) {
				return mockUpQuery.getMockInstance();
			}
			@Mock
			public Transaction beginTransaction() {
				return mockUpTransaction.getMockInstance();
			}
			@Mock
			public Transaction getTransaction() {
				return mockUpTransaction.getMockInstance();
			}
			@Mock
			public Serializable save(Object object) {
				return (Serializable) serialVersionUID;
			}
			@Mock
			public void flush() {
			}
			@Mock
			public void update(Object object) {
			}
		};
		new MockUp<SessionFactory>() {
			@Mock
			public Session openSession() {
				return mockedSession.getMockInstance();
			}
		};
		new MockUp<AlarmsHeaderServiceImpl>() {
			@Mock
			private Session getSession() {
				return mockedSession.getMockInstance();
			}
		};
	}

	@After
	public void after() throws Exception {
	}

	@Test
	public void testSaveAlarmsHeader() throws Exception {
		AlarmsHeader ah = null;
		alarmsHeaderServiceImpl.saveAlarmsHeader(ah);
	}

	@Test
	public void testUpdateAlarmsHeader2018() throws Exception {
		alarmsHeaderServiceImpl.updateAlarmsHeader2018("status", new Timestamp(System.currentTimeMillis()), "startEpochMicrosecCleared", "lastEpochMicroSecCleared", "eventName", "reportingEntityName", "specificProblem");
	}

	@Test
	public void testGetStatusBySourceName() throws Exception {
		alarmsHeaderServiceImpl.getStatusBySourceName("sourceName");
	}

	@Test
	public void testGetIdByStatusSourceName() throws Exception {
		alarmsHeaderServiceImpl.getIdByStatusSourceName("sourceName");
	}

	@Test
	public void testUpdateAlarmsHeader() throws Exception {
		AlarmsHeader ah = null;
		alarmsHeaderServiceImpl.updateAlarmsHeader(ah);
	}

	@Test
	public void testGetAllCountByStatus() throws Exception {
		alarmsHeaderServiceImpl.getAllCountByStatus("status");
	}

	@Test
	public void testGetAllByStatus() throws Exception {
		alarmsHeaderServiceImpl.getAllByStatus("status", "eventName", "sourceName", "eventServrity", "reportingEntityName", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()));
	}

	@Test
	public void testGetAlarmsHeaderDetail() throws Exception {
		alarmsHeaderServiceImpl.getAlarmsHeaderDetail(1);
	}

	@Test
	public void testGetAllByDatetime() throws Exception {
		alarmsHeaderServiceImpl.getAllCountByStatus("status");
		alarmsHeaderServiceImpl.getAllByDatetime("status", "eventId", "eventServrity", "createTime");
	}

	@Test
	public void testGetAllCount() throws Exception {
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
		ah.setCreateTime(DateUtils.now());
		ah.setUpdateTime(DateUtils.now());
		ah.setVfStatus("1");
		ah.setEventSourceType("q");
		alarmsHeaderServiceImpl.getAllCount(ah, 1, 1);
	}

	@Test
	public void testQueryAlarmsHeader() throws Exception {
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
		ah.setCreateTime(DateUtils.now());
		ah.setUpdateTime(DateUtils.now());
		ah.setVfStatus("1");
		ah.setEventSourceType("q");
		alarmsHeaderServiceImpl.queryAlarmsHeader(ah, 1, 1);
	}

	@Test
	public void testQueryId() throws Exception {
		String[] id = {"1", "2", "3"};
		alarmsHeaderServiceImpl.queryId(id);
	}

	@Test
	public void testQueryStatusCount() throws Exception {
		alarmsHeaderServiceImpl.queryStatusCount("status");
	}

	@Test(expected = Exception.class)
	public void testSaveAlarmsHeaderException() {
		new MockUp<AlarmsHeaderServiceImpl>() {
			@Mock
			private Session getSession() throws Exception {
				throw new Exception();
			}
		};
		AlarmsHeader ah = new AlarmsHeader();
		alarmsHeaderServiceImpl.saveAlarmsHeader(ah);
	}

	@Test(expected = Exception.class)
	public void testUpdateAlarmsHeader2018Exception() {
		new MockUp<AlarmsHeaderServiceImpl>() {
			@Mock
			private Session getSession() throws Exception {
				throw new Exception();
			}
		};
		alarmsHeaderServiceImpl.updateAlarmsHeader2018("status", new Timestamp(System.currentTimeMillis()), "startEpochMicrosecCleared", "lastEpochMicroSecCleared", "eventName", "reportingEntityName", "specificProblem");
	}

	@Test(expected = Exception.class)
	public void testGetStatusBySourceNameException() {
		new MockUp<AlarmsHeaderServiceImpl>() {
			@Mock
			private Session getSession() throws Exception {
				throw new Exception();
			}
		};
		alarmsHeaderServiceImpl.getStatusBySourceName("sourceName");
	}

	@Test(expected = Exception.class)
	public void testGetIdByStatusSourceNameException() {
		new MockUp<AlarmsHeaderServiceImpl>() {
			@Mock
			private Session getSession() throws Exception {
				throw new Exception();
			}
		};
		alarmsHeaderServiceImpl.getIdByStatusSourceName("sourceName");
	}

	@Test(expected = Exception.class)
	public void testUpdateAlarmsHeaderException() {
		new MockUp<AlarmsHeaderServiceImpl>() {
			@Mock
			private Session getSession() throws Exception {
				throw new Exception();
			}
		};
		AlarmsHeader ah = new AlarmsHeader();
		alarmsHeaderServiceImpl.updateAlarmsHeader(ah);
	}

	@Test(expected = Exception.class)
	public void testGetAllCountByStatusException() {
		new MockUp<AlarmsHeaderServiceImpl>() {
			@Mock
			private Session getSession() throws Exception {
				throw new Exception();
			}
		};
		alarmsHeaderServiceImpl.getAllCountByStatus("status");
	}

	@Test(expected = Exception.class)
	public void testGetAllByStatusException() {
		new MockUp<AlarmsHeaderServiceImpl>() {
			@Mock
			private Session getSession() throws Exception {
				throw new Exception();
			}
		};
		alarmsHeaderServiceImpl.getAllByStatus("status", "eventName", "sourceName", "eventServrity", "reportingEntityName", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()));
	}

	@Test(expected = Exception.class)
	public void testGetAlarmsHeaderDetailException() {
		new MockUp<AlarmsHeaderServiceImpl>() {
			@Mock
			private Session getSession() throws Exception {
				throw new Exception();
			}
		};
		alarmsHeaderServiceImpl.getAlarmsHeaderDetail(1);
	}

	@Test(expected = Exception.class)
	public void testGetAllByDatetimeException() {
		new MockUp<AlarmsHeaderServiceImpl>() {
			@Mock
			private Session getSession() throws Exception {
				throw new Exception();
			}
		};
		alarmsHeaderServiceImpl.getAllByDatetime("status", "eventId", "eventServrity", "createTime");
	}

	@Test(expected = Exception.class)
	public void testGetAllCountException() {
		new MockUp<AlarmsHeaderServiceImpl>() {
			@Mock
			private Session getSession() throws Exception {
				throw new Exception();
			}
		};
		AlarmsHeader ah = new AlarmsHeader();
		alarmsHeaderServiceImpl.getAllCount(ah, 1, 1);
	}

	@Test(expected = Exception.class)
	public void testQueryAlarmsHeaderException() {
		new MockUp<AlarmsHeaderServiceImpl>() {
			@Mock
			private Session getSession() throws Exception {
				throw new Exception();
			}
		};
		AlarmsHeader ah = new AlarmsHeader();
		alarmsHeaderServiceImpl.queryAlarmsHeader(ah, 1, 1);
	}

	@Test(expected = Exception.class)
	public void testQueryIdException() {
		new MockUp<AlarmsHeaderServiceImpl>() {
			@Mock
			private Session getSession() throws Exception {
				throw new Exception();
			}
		};
		String[] id = {"1", "2", "3"};
		alarmsHeaderServiceImpl.queryId(id);
	}

	@Test(expected = Exception.class)
	public void testQueryStatusCountException() {
		new MockUp<AlarmsHeaderServiceImpl>() {
			@Mock
			private Session getSession() throws Exception {
				throw new Exception();
			}
		};
		alarmsHeaderServiceImpl.queryStatusCount("status");
	}
}
