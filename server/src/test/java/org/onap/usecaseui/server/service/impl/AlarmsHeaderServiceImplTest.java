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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

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
	@Before
		public void before() throws Exception { 
	}

	@After
	public void after() throws Exception { 
	}

	AlarmsHeaderServiceImpl alarmsHeaderServiceImpl = new AlarmsHeaderServiceImpl();
	private Session session;
	private Transaction transaction;
	private Query query;

	@Test
	public void testSaveAlarmsHeader() throws Exception { 
		MockUp<Session> mockedSession = new MockUp<Session>() {
			@Mock
			public Transaction beginTransaction() {
				return transaction;
			}
			@Mock
			public void save(AlarmsHeader alarmsHeader) {
			}
			@Mock
			public void flush() {
			}
		};
		new MockUp<Transaction>() {
			@Mock
			public void commit() {
			}
		};
		new MockUp<AlarmsHeaderServiceImpl>() {
			@Mock
			private Session getSession() {
				return mockedSession.getMockInstance();
			}
		};
		AlarmsHeader alarmsHeader = null;
		alarmsHeaderServiceImpl.saveAlarmsHeader(alarmsHeader);
	}

	@Test(expected = Exception.class)
	public void testSaveAlarmsHeaderError(){
		alarmsHeaderServiceImpl.saveAlarmsHeader(new AlarmsHeader());
	}


} 
