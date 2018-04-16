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
import org.onap.usecaseui.server.bean.PerformanceHeader;
import org.onap.usecaseui.server.service.impl.PerformanceHeaderServiceImpl;
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
* PerformanceHeaderServiceImpl Tester. 
* 
* @author <Authors name> 
* @since <pre>8, 2018</pre>
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
	public void testGetAllCount() throws Exception {
		new MockUp<Query>() {
			@Mock
			public Object uniqueResult() {
				return "1";
			}
		};
		PerformanceHeader ph = new PerformanceHeader();
		ph.setVersion("");
		ph.setEventName("");
		ph.setDomain("");
		ph.setEventId("");
		ph.setNfcNamingCode("");
		ph.setNfNamingCode("");
		ph.setSourceId("");
		ph.setSourceName("");
		ph.setReportingEntityId("");
		ph.setReportingEntityName("");
		ph.setPriority("");
		ph.setStartEpochMicrosec("");
		ph.setLastEpochMicroSec("");
		ph.setSequence("");
		ph.setMeasurementsForVfScalingVersion("");
		ph.setMeasurementInterval("");
		ph.setEventType("");
		ph.setCreateTime(DateUtils.now());
		ph.setUpdateTime(DateUtils.now());
		performanceHeaderServiceImpl.getAllCount(ph, 1, 1);
	}

	@Test
	public void testQueryPerformanceHeader() throws Exception {
		PerformanceHeader ph = new PerformanceHeader();
		ph.setVersion("");
		ph.setEventName("");
		ph.setDomain("");
		ph.setEventId("");
		ph.setNfcNamingCode("");
		ph.setNfNamingCode("");
		ph.setSourceId("");
		ph.setSourceName("");
		ph.setReportingEntityId("");
		ph.setReportingEntityName("");
		ph.setPriority("");
		ph.setStartEpochMicrosec("");
		ph.setLastEpochMicroSec("");
		ph.setSequence("");
		ph.setMeasurementsForVfScalingVersion("");
		ph.setMeasurementInterval("");
		ph.setEventType("");
		ph.setCreateTime(DateUtils.now());
		ph.setUpdateTime(DateUtils.now());
		performanceHeaderServiceImpl.queryPerformanceHeader(ph, 1, 1);
	}

	@Test
	public void testQueryId() throws Exception {
		String[] id = {"1", "2", "3"};
		performanceHeaderServiceImpl.queryId(id);
	}

	@Test
	public void testQueryAllSourceId() throws Exception {
		performanceHeaderServiceImpl.queryAllSourceId();
	}

	@Test(expected = Exception.class)
	public void testSavePerformanceHeaderException() throws Exception {
		new MockUp<PerformanceHeaderServiceImpl>() {
			@Mock
			private Session getSession() throws Exception {
				throw new Exception();
			}
		};
		PerformanceHeader ph = new PerformanceHeader();
		performanceHeaderServiceImpl.savePerformanceHeader(ph);
	}

	@Test(expected = Exception.class)
	public void testUpdatePerformanceHeaderException() throws Exception {
		new MockUp<PerformanceHeaderServiceImpl>() {
			@Mock
			private Session getSession() throws Exception {
				throw new Exception();
			}
		};
		PerformanceHeader ph = new PerformanceHeader();
		performanceHeaderServiceImpl.updatePerformanceHeader(ph);
	}

	@Test(expected = Exception.class)
	public void testGetAllCountException() throws Exception {
		new MockUp<PerformanceHeaderServiceImpl>() {
			@Mock
			private Session getSession() throws Exception {
				throw new Exception();
			}
		};
		PerformanceHeader ph = new PerformanceHeader();
		performanceHeaderServiceImpl.getAllCount(ph, 1, 1);
	}

	@Test(expected = Exception.class)
	public void testQueryPerformanceHeaderException() throws Exception {
		new MockUp<PerformanceHeaderServiceImpl>() {
			@Mock
			private Session getSession() throws Exception {
				throw new Exception();
			}
		};
		PerformanceHeader ph = new PerformanceHeader();
		performanceHeaderServiceImpl.queryPerformanceHeader(ph, 1, 1);
	}

	@Test(expected = Exception.class)
	public void testQueryIdException() throws Exception {
		new MockUp<PerformanceHeaderServiceImpl>() {
			@Mock
			private Session getSession() throws Exception {
				throw new Exception();
			}
		};
		String[] id = {"1", "2", "3"};
		performanceHeaderServiceImpl.queryId(id);
	}

	@Test(expected = Exception.class)
	public void testQueryAllSourceIdException() throws Exception {
		new MockUp<PerformanceHeaderServiceImpl>() {
			@Mock
			private Session getSession() throws Exception {
				throw new Exception();
			}
		};
		performanceHeaderServiceImpl.queryAllSourceId();
	}
}
