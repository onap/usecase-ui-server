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
import org.onap.usecaseui.server.bean.AlarmsInformation;
import org.onap.usecaseui.server.service.impl.AlarmsInformationServiceImpl;
import org.onap.usecaseui.server.util.DateUtils;

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
* @since <pre>8, 2018</pre>
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
		new MockUp<Query>() {
			@Mock
			public Object uniqueResult() {
				return "1";
			}
		};
		try {
			AlarmsInformation ai = new AlarmsInformation();
			ai.setName("");
			ai.setValue("");
			ai.setEventId("");
			ai.setCreateTime(DateUtils.now());
			ai.setUpdateTime(DateUtils.now());
			alarmsInformationServiceImpl.getAllCount(ai, 1, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testQueryAlarmsInformation() throws Exception {
		try {
			AlarmsInformation ai = new AlarmsInformation();
			ai.setName("");
			ai.setValue("");
			ai.setEventId("");
			ai.setCreateTime(DateUtils.now());
			ai.setUpdateTime(DateUtils.now());
			alarmsInformationServiceImpl.queryAlarmsInformation(ai, 1, 1);
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
			alarmsInformationServiceImpl.queryDateBetween("sourceId", "startTime", "endTime");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
