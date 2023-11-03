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
import org.onap.usecaseui.server.bean.PerformanceHeader;
import java.util.*;
import java.io.*;
import org.hibernate.query.Query;
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
	}

	@After
	public void after() throws Exception {
	}

	@Test
	public void testSavePerformanceHeader() throws Exception {
		try {
			PerformanceHeader ph = null;
			PerformanceHeader phNew = new PerformanceHeader();
			performanceHeaderServiceImpl.savePerformanceHeader(ph);
			performanceHeaderServiceImpl.savePerformanceHeader(phNew);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testUpdatePerformanceHeader() throws Exception {
		try {
			PerformanceHeader ph = null;
			PerformanceHeader phNew = new PerformanceHeader();
			performanceHeaderServiceImpl.updatePerformanceHeader(ph);
			performanceHeaderServiceImpl.updatePerformanceHeader(phNew);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testGetAllCount() throws Exception {
		try {
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
			ph.setStartEpochMicrosec("");;
			ph.setLastEpochMicroSec("");;
			performanceHeaderServiceImpl.getAllCount(ph, 1, 1);
			
			PerformanceHeader phNew = new PerformanceHeader();
			performanceHeaderServiceImpl.getAllCount(phNew, 1, 1);
			performanceHeaderServiceImpl.getAllCount(null, 1, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testQueryPerformanceHeader() throws Exception {
		try {
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
			ph.setStartEpochMicrosec("");;
			ph.setLastEpochMicroSec("");;
			performanceHeaderServiceImpl.queryPerformanceHeader(ph, 1, 10);
			
			PerformanceHeader phNew = new PerformanceHeader();
			performanceHeaderServiceImpl.queryPerformanceHeader(phNew, 1, 1);
			performanceHeaderServiceImpl.queryPerformanceHeader(null, 1, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testQueryId() throws Exception {
		try {
			String[] id = {"1", "2", "3"};
			performanceHeaderServiceImpl.queryId(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void queryAllSourceNames() throws Exception {
		try {
			performanceHeaderServiceImpl.queryAllSourceNames();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetPerformanceHeaderById() throws Exception {
		try {
			performanceHeaderServiceImpl.getPerformanceHeaderById("0a573f09d50f46adaae0c10e741fea4d");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
