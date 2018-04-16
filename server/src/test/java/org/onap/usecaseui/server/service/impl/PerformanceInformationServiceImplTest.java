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
import org.onap.usecaseui.server.bean.PerformanceInformation;
import org.onap.usecaseui.server.service.impl.PerformanceInformationServiceImpl;
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
			public List<PerformanceInformation> list() {
				PerformanceInformation pi = new PerformanceInformation();
				return Arrays.asList(pi);
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
		new MockUp<PerformanceInformationServiceImpl>() {
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
	public void testSavePerformanceInformation() throws Exception {
		PerformanceInformation pi = null;
		performanceInformationServiceImpl.savePerformanceInformation(pi);
	}

	@Test
	public void testUpdatePerformanceInformation() throws Exception {
		PerformanceInformation pi = null;
		performanceInformationServiceImpl.updatePerformanceInformation(pi);
	}

	@Test
	public void testGetAllCount() throws Exception {
		new MockUp<Query>() {
			@Mock
			public Object uniqueResult() {
				return "1";
			}
		};
		PerformanceInformation pi = new PerformanceInformation();
		pi.setName("");
		pi.setValue("");
		pi.setEventId("");
		pi.setCreateTime(DateUtils.now());
		pi.setUpdateTime(DateUtils.now());
		performanceInformationServiceImpl.getAllCount(pi, 1, 1);
	}

	@Test
	public void testQueryPerformanceInformation() throws Exception {
		PerformanceInformation pi = new PerformanceInformation();
		pi.setName("");
		pi.setValue("");
		pi.setEventId("");
		pi.setCreateTime(DateUtils.now());
		pi.setUpdateTime(DateUtils.now());
		performanceInformationServiceImpl.queryPerformanceInformation(pi, 1, 1);
	}

	@Test
	public void testQueryId() throws Exception {
		String[] id = {"1", "2", "3"};
		performanceInformationServiceImpl.queryId(id);
	}

	@Test
	public void testQueryDateBetween() throws Exception {
		performanceInformationServiceImpl.queryDateBetween("eventId", DateUtils.now(), DateUtils.now());
		performanceInformationServiceImpl.queryDateBetween("resourceId", "name", "startTime", "endTime");
	}

	@Test
	public void testQueryDataBetweenSum() throws Exception {
		performanceInformationServiceImpl.queryDataBetweenSum("eventId", "name", DateUtils.now(), DateUtils.now());
	}

	@Test(expected = Exception.class)
	public void testSavePerformanceInformationException() throws Exception {
		new MockUp<PerformanceInformationServiceImpl>() {
			@Mock
			private Session getSession() throws Exception {
				throw new Exception();
			}
		};
		PerformanceInformation pi = new PerformanceInformation();
		performanceInformationServiceImpl.savePerformanceInformation(pi);
	}

	@Test(expected = Exception.class)
	public void testUpdatePerformanceInformationException() throws Exception {
		new MockUp<PerformanceInformationServiceImpl>() {
			@Mock
			private Session getSession() throws Exception {
				throw new Exception();
			}
		};
		PerformanceInformation pi = new PerformanceInformation();
		performanceInformationServiceImpl.updatePerformanceInformation(pi);
	}

	@Test(expected = Exception.class)
	public void testGetAllCountException() throws Exception {
		new MockUp<PerformanceInformationServiceImpl>() {
			@Mock
			private Session getSession() throws Exception {
				throw new Exception();
			}
		};
		PerformanceInformation pi = new PerformanceInformation();
		performanceInformationServiceImpl.getAllCount(pi, 1, 1);
	}

	@Test(expected = Exception.class)
	public void testQueryPerformanceInformationException() throws Exception {
		new MockUp<PerformanceInformationServiceImpl>() {
			@Mock
			private Session getSession() throws Exception {
				throw new Exception();
			}
		};
		PerformanceInformation pi = new PerformanceInformation();
		performanceInformationServiceImpl.queryPerformanceInformation(pi, 1, 1);
	}

	@Test(expected = Exception.class)
	public void testQueryIdException() throws Exception {
		new MockUp<PerformanceInformationServiceImpl>() {
			@Mock
			private Session getSession() throws Exception {
				throw new Exception();
			}
		};
		String[] id = {"1", "2", "3"};
		performanceInformationServiceImpl.queryId(id);
	}

	@Test(expected = Exception.class)
	public void testQueryDateBetweenException() throws Exception {
		new MockUp<PerformanceInformationServiceImpl>() {
			@Mock
			private Session getSession() throws Exception {
				throw new Exception();
			}
		};
		performanceInformationServiceImpl.queryDateBetween("eventId", DateUtils.now(), DateUtils.now());
		performanceInformationServiceImpl.queryDateBetween("resourceId", "name", "startTime", "endTime");
	}

	@Test(expected = Exception.class)
	public void testQueryDataBetweenSumException() throws Exception {
		new MockUp<PerformanceInformationServiceImpl>() {
			@Mock
			private Session getSession() throws Exception {
				throw new Exception();
			}
		};
		performanceInformationServiceImpl.queryDataBetweenSum("eventId", "name", DateUtils.now(), DateUtils.now());
	}
}
