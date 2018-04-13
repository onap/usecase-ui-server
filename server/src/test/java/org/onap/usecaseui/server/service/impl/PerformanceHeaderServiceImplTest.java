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
package org.onap.usecaseui.server.service.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test; 
import org.junit.Before; 
import org.junit.After;
import org.junit.runner.RunWith;
import org.onap.usecaseui.server.UsecaseuiServerApplication;
import org.onap.usecaseui.server.bean.PerformanceHeader;
import org.onap.usecaseui.server.service.PerformanceHeaderServiceImpl;
import org.onap.usecaseui.server.util.DateUtils;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;

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
import static org.mockito.Mockito.mock;

/** 
* PerformanceHeaderServiceImpl Tester. 
* 
* @author <Authors name> 
* @since <pre> 8, 2018</pre>
* @version 1.0 
*/
public class PerformanceHeaderServiceImplTest {

	PerformanceHeaderServiceImpl performanceHeaderServiceImpl = null;
	private static final long serialVersionUID = 1L;

	@Before
	public void before() throws Exception {
		performanceHeaderServiceImpl = new PerformanceHeaderServiceImpl();

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
			public List<PerformanceHeader> list() {
				PerformanceHeader ph = new PerformanceHeader();
				return Arrays.asList(ph);
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
		new MockUp<PerformanceHeaderServiceImpl>() {
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
	public void testSavePerformanceHeader() throws Exception {
		PerformanceHeader ph = null;
		performanceHeaderServiceImpl.savePerformanceHeader(ph);
	}

	@Test
	public void testUpdatePerformanceHeader() throws Exception {
		PerformanceHeader ph = null;
		performanceHeaderServiceImpl.updatePerformanceHeader(ph);
	}

	@Test
	public void testGetAllCountByEventType() throws Exception {
		performanceHeaderServiceImpl.getAllCountByEventType();
	}

	@Test
	public void testGetAllByEventType() throws Exception {
		new MockUp<Query>() {
			@Mock
			public List<PerformanceHeader> list() {
				PerformanceHeader ph = new PerformanceHeader();
				return Arrays.asList(ph);
			}
		};
		performanceHeaderServiceImpl.getAllByEventType("eventName", "sourceName", "reportingEntityName", DateUtils.now(), DateUtils.now());
	}

	@Test
	public void testGetPerformanceHeaderDetail() throws Exception {
		performanceHeaderServiceImpl.getPerformanceHeaderDetail(1);
	}

	@Test
	public void testGetAllByDatetime() throws Exception {
		performanceHeaderServiceImpl.getAllByDatetime("eventId", "createTime");
	}

	@Test
	public void testGetAllCount() throws Exception {
		PerformanceHeader ph = new PerformanceHeader();
		ph.setVersion("version");
		ph.setEventName("eventName");
		ph.setDomain("domain");
		ph.setEventId("eventId");
		ph.setNfcNamingCode("nfcNamingCode");
		ph.setNfNamingCode("nfNamingCode");
		ph.setSourceId("sourceId");
		ph.setSourceName("sourceName");
		ph.setReportingEntityId("reportingEntityId");
		ph.setReportingEntityName("reportingEntityName");
		ph.setPriority("priority");
		ph.setStartEpochMicrosec("startEpochMicrosec");
		ph.setLastEpochMicroSec("lastEpochMicroSec");
		ph.setSequence("sequence");
		ph.setMeasurementsForVfScalingVersion("measurementsForVfScalingVersion");
		ph.setMeasurementInterval("measurementInterval");
		ph.setEventType("eventType");
		ph.setCreateTime(DateUtils.now());
		ph.setUpdateTime(DateUtils.now());
		performanceHeaderServiceImpl.getAllCount(ph, 1, 10);
	}

	@Test
	public void testQueryPerformanceHeader() throws Exception {
		new MockUp<PerformanceHeaderServiceImpl>() {
			@Mock
			private int getAllCount(PerformanceHeader performanceHeader, int currentPage, int pageSize) {
				return 10;
			}
		};
		PerformanceHeader ph = new PerformanceHeader();
		ph.setVersion("version");
		ph.setEventName("eventName");
		ph.setDomain("domain");
		ph.setEventId("eventId");
		ph.setNfcNamingCode("nfcNamingCode");
		ph.setNfNamingCode("nfNamingCode");
		ph.setSourceId("sourceId");
		ph.setSourceName("sourceName");
		ph.setReportingEntityId("reportingEntityId");
		ph.setReportingEntityName("reportingEntityName");
		ph.setPriority("priority");
		ph.setStartEpochMicrosec("startEpochMicrosec");
		ph.setLastEpochMicroSec("lastEpochMicroSec");
		ph.setSequence("sequence");
		ph.setMeasurementsForVfScalingVersion("measurementsForVfScalingVersion");
		ph.setMeasurementInterval("measurementInterval");
		ph.setEventType("eventType");
		ph.setCreateTime(DateUtils.now());
		ph.setUpdateTime(DateUtils.now());
		performanceHeaderServiceImpl.queryPerformanceHeader(ph, 1, 10);
	}

	@Test
	public void testQueryId() throws Exception {
		String[] id = {};
		performanceHeaderServiceImpl.queryId(id);
	}

	@Test
	public void testQueryAllSourceId() throws Exception {
		String[] id = {};
		performanceHeaderServiceImpl.queryAllSourceId();
	}

	@Test(expected = Exception.class)
	public void testSavePerformanceHeader() throws Exception {
		new MockUp<PerformanceHeaderServiceImpl>() {
			@Mock
			private Session getSession() throws Exception {
				throw new Exception();
			}
		};
		PerformanceHeader ph = null;
		performanceHeaderServiceImpl.savePerformanceHeader(ph);
	}

	@Test(expected = Exception.class)
	public void testUpdatePerformanceHeader() throws Exception {
		new MockUp<PerformanceHeaderServiceImpl>() {
			@Mock
			private Session getSession() throws Exception {
				throw new Exception();
			}
		};
		PerformanceHeader ph = null;
		performanceHeaderServiceImpl.updatePerformanceHeader(ph);
	}

	@Test(expected = Exception.class)
	public void testGetAllCountByEventType() throws Exception {
		new MockUp<PerformanceHeaderServiceImpl>() {
			@Mock
			private Session getSession() throws Exception {
				throw new Exception();
			}
		};
		performanceHeaderServiceImpl.getAllCountByEventType();
	}

	@Test(expected = Exception.class)
	public void testGetAllByEventType() throws Exception {
		new MockUp<PerformanceHeaderServiceImpl>() {
			@Mock
			private Session getSession() throws Exception {
				throw new Exception();
			}
		};
		performanceHeaderServiceImpl.getAllByEventType("eventName", "sourceName", "reportingEntityName", DateUtils.now(), DateUtils.now());
	}

	@Test(expected = Exception.class)
	public void testGetPerformanceHeaderDetail() throws Exception {
		new MockUp<PerformanceHeaderServiceImpl>() {
			@Mock
			private Session getSession() throws Exception {
				throw new Exception();
			}
		};
		performanceHeaderServiceImpl.getPerformanceHeaderDetail(1);
	}

	@Test(expected = Exception.class)
	public void testGetAllByDatetime() throws Exception {
		new MockUp<PerformanceHeaderServiceImpl>() {
			@Mock
			private Session getSession() throws Exception {
				throw new Exception();
			}
		};
		performanceHeaderServiceImpl.getAllByDatetime("eventId", "createTime");
	}

	@Test(expected = Exception.class)
	public void testGetAllCount() throws Exception {
		new MockUp<PerformanceHeaderServiceImpl>() {
			@Mock
			private Session getSession() throws Exception {
				throw new Exception();
			}
		};
		PerformanceHeader ph = new PerformanceHeader();
		performanceHeaderServiceImpl.getAllCount(ph, 1, 10);
	}

	@Test(expected = Exception.class)
	public void testQueryPerformanceHeader() throws Exception {
		new MockUp<PerformanceHeaderServiceImpl>() {
			@Mock
			private Session getSession() throws Exception {
				throw new Exception();
			}
		};
		PerformanceHeader ph = new PerformanceHeader();
		performanceHeaderServiceImpl.queryPerformanceHeader(ph, 1, 10);
	}

	@Test(expected = Exception.class)
	public void testQueryId() throws Exception {
		new MockUp<PerformanceHeaderServiceImpl>() {
			@Mock
			private Session getSession() throws Exception {
				throw new Exception();
			}
		};
		String[] id = {};
		performanceHeaderServiceImpl.queryId(id);
	}

	@Test(expected = Exception.class)
	public void testQueryAllSourceId() throws Exception {
		new MockUp<PerformanceHeaderServiceImpl>() {
			@Mock
			private Session getSession() throws Exception {
				throw new Exception();
			}
		};
		String[] id = {};
		performanceHeaderServiceImpl.queryAllSourceId();
	}
}