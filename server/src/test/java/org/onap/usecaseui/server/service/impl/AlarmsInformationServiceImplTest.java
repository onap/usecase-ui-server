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

import org.junit.Test; 
import org.junit.Before; 
import org.junit.After;
import org.junit.runner.RunWith;
import org.onap.usecaseui.server.UsecaseuiServerApplication;
import org.onap.usecaseui.server.bean.AlarmsInformation;
import org.onap.usecaseui.server.service.impl.AlarmsInformationServiceImpl;
import org.onap.usecaseui.server.util.DateUtils;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
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

/** 
* AlarmsInformationServiceImpl Tester. 
* 
* @author <Authors name> 
* @since <pre> 8, 2018</pre>
* @version 1.0 
*/
public class AlarmsInformationServiceImplTest {

	AlarmsInformationServiceImpl alarmsInformationServiceImpl = null;
	private static final long serialVersionUID = 1L;

	@Before
	public void before() throws Exception {
		alarmsInformationServiceImpl = new AlarmsInformationServiceImpl();

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
			public List<AlarmsInformation> list() {
				AlarmsInformation ai = new AlarmsInformation();
				return Arrays.asList(ai);
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
		new MockUp<AlarmsInformationServiceImpl>() {
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
	public void testSaveAlarmsInformation() throws Exception {
		AlarmsInformation ai = null;
		alarmsInformationServiceImpl.saveAlarmsInformation(ai);
	}

	@Test
	public void testUpdateAlarmsInformation() throws Exception {
		AlarmsInformation ai = null;
		alarmsInformationServiceImpl.updateAlarmsInformation(ai);
	}

	@Test
	public void testGetAllCount() throws Exception {
		AlarmsInformation ai = new AlarmsInformation();
		ai.setName("name");
		ai.setValue("value");
		ai.setEventId("eventId");
		ai.setCreateTime(DateUtils.now());
		ai.setUpdateTime(DateUtils.now());
		alarmsInformationServiceImpl.getAllCount(ai, 1, 10);
	}

	@Test
	public void testQueryAlarmsInformation() throws Exception {
		new MockUp<AlarmsInformationServiceImpl>() {
			@Mock
			private int getAllCount(AlarmsInformation alarmsInformation, int currentPage, int pageSize) {
				return 10;
			}
		};
		AlarmsInformation ai = new AlarmsInformation();
		ai.setName("name");
		ai.setValue("value");
		ai.setEventId("eventId");
		ai.setCreateTime(DateUtils.now());
		ai.setUpdateTime(DateUtils.now());
		alarmsInformationServiceImpl.queryAlarmsInformation(ai, 1, 10);
	}

	@Test
	public void testQueryId() throws Exception {
		String[] id = {};
		alarmsInformationServiceImpl.queryId(id);
	}

	@Test
	public void testQueryDateBetween() throws Exception {
		new MockUp<List>() {
			@Mock
			private Iterator iterator() {
				String[] strlist1 = {"time1", "count1"};
				String[] strlist2 = {"time2", "count2"};
				List<String[]> list = new ArrayList<String[]>();
				list.add(strlist1);
				list.add(strlist2);
				return list.iterator();
			}
		};
		alarmsInformationServiceImpl.queryDateBetween("sourceId", "startTime", "endTime");
	}

	@Test
	public void testGetAllAlarmsInformationByeventId() throws Exception {
		alarmsInformationServiceImpl.getAllAlarmsInformationByeventId("eventId");
	}

	@Test(expected = Exception.class)
	public void testSaveAlarmsInformationException() throws Exception {
		new MockUp<AlarmsInformationServiceImpl>() {
			@Mock
			private Session getSession() throws Exception {
				throw new Exception();
			}
		};
		AlarmsInformation ai = null;
		alarmsInformationServiceImpl.saveAlarmsInformation(ai);
	}

	@Test(expected = Exception.class)
	public void testUpdateAlarmsInformationException() throws Exception {
		new MockUp<AlarmsInformationServiceImpl>() {
			@Mock
			private Session getSession() throws Exception {
				throw new Exception();
			}
		};
		AlarmsInformation ai = null;
		alarmsInformationServiceImpl.updateAlarmsInformation(ai);
	}

	@Test(expected = Exception.class)
	public void testGetAllCountException() throws Exception {
		new MockUp<AlarmsInformationServiceImpl>() {
			@Mock
			private Session getSession() throws Exception {
				throw new Exception();
			}
		};
		AlarmsInformation ai = null;
		alarmsInformationServiceImpl.getAllCount(ai, 1, 10);
	}

	@Test(expected = Exception.class)
	public void testQueryAlarmsInformationException() throws Exception {
		new MockUp<AlarmsInformationServiceImpl>() {
			@Mock
			private Session getSession() throws Exception {
				throw new Exception();
			}
		};
		AlarmsInformation ai = null;
		alarmsInformationServiceImpl.queryAlarmsInformation(ai, 1, 10);
	}

	@Test(expected = Exception.class)
	public void testQueryIdException() throws Exception {
		new MockUp<AlarmsInformationServiceImpl>() {
			@Mock
			private Session getSession() throws Exception {
				throw new Exception();
			}
		};
		String[] id = {};
		alarmsInformationServiceImpl.queryId(id);
	}

	@Test(expected = Exception.class)
	public void testQueryDateBetweenException() throws Exception {
		new MockUp<AlarmsInformationServiceImpl>() {
			@Mock
			private Session getSession() throws Exception {
				throw new Exception();
			}
		};
		alarmsInformationServiceImpl.queryDateBetween("sourceId", "startTime", "endTime");
	}

	@Test(expected = Exception.class)
	public void testGetAllAlarmsInformationByeventIdException() throws Exception {
		new MockUp<AlarmsInformationServiceImpl>() {
			@Mock
			private Session getSession() throws Exception {
				throw new Exception();
			}
		};
		alarmsInformationServiceImpl.getAllAlarmsInformationByeventId("eventId");
	}

}
