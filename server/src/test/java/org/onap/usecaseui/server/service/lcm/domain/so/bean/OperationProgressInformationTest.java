/**
 * Copyright (C) 2017 CMCC, Inc. and others. All rights reserved.
 *
 * ================================================================================
 *  Modifications Copyright (C) 2020 IBM.
 * ================================================================================
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
package org.onap.usecaseui.server.service.lcm.domain.so.bean;

import org.junit.Test; 
import org.junit.Before; 
import org.junit.After;
import org.junit.runner.RunWith;
import org.onap.usecaseui.server.UuiServerApplication;
import org.onap.usecaseui.server.service.lcm.domain.so.bean.*;
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

import static org.junit.Assert.assertEquals;

public class OperationProgressInformationTest {

	@Before
	public void before() throws Exception {
	}

	@After
	public void after() throws Exception {
	}

	@Test
	public void testGet() throws Exception {
		OperationProgressInformation opi = new OperationProgressInformation();
		OperationProgress op = new OperationProgress("123","test","test","test","test","test","test","test",123,"test","test");
		opi.setOperationStatus(op);
		assertEquals(op,opi.getOperationStatus());
	}
	@Test
	public void testConstructor() throws Exception {
		OperationProgress op = new OperationProgress("123","test","test","test","test","test","test","test",123,"test","test");
		OperationProgressInformation opi = new OperationProgressInformation(op);
		assertEquals(op,opi.getOperationStatus());
	}
}
