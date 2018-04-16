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
package org.onap.usecaseui.server.bean;

import org.junit.Test; 
import org.junit.Before; 
import org.junit.After;
import org.junit.runner.RunWith;
import org.onap.usecaseui.server.UsecaseuiServerApplication;
import org.onap.usecaseui.server.bean.PerformanceHeader;
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

public class PerformanceHeaderTest implements Serializable {

	@Before
	public void before() throws Exception {
	}

	@After
	public void after() throws Exception {
	}

	@Test
	public void testGetPerformanceHeader() throws Exception {
		PerformanceHeader ph = new PerformanceHeader("version", "eventName", "domain", "eventId", "eventType", "nfcNamingCode", "nfNamingCode",
													"sourceId", "sourceName", "reportingEntityId", "reportingEntityName", "priority",
													"startEpochMicrosec", "lastEpochMicroSec", "sequence", "measurementsForVfScalingVersion",
													"measurementInterval", DateUtils.now(), DateUtils.now());
		ph.getVersion();
		ph.getEventName();
		ph.getDomain();
		ph.getEventId();
		ph.getEventType();
		ph.getNfcNamingCode();
		ph.getNfNamingCode();
		ph.getSourceId();
		ph.getSourceName();
		ph.getReportingEntityId();
		ph.getReportingEntityName();
		ph.getPriority();
		ph.getStartEpochMicrosec();
		ph.getLastEpochMicroSec();
		ph.getSequence();
		ph.getMeasurementsForVfScalingVersion();
		ph.getMeasurementInterval();
		ph.getCreateTime();
		ph.getUpdateTime();
	}

	@Test
	public void testSetPerformanceHeader() throws Exception {
		PerformanceHeader ph = new PerformanceHeader("sourceId");
		ph.setVersion("");
		ph.setEventName("");
		ph.setDomain("");
		ph.setEventId("");
		ph.setEventType("");
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
		ph.setCreateTime(DateUtils.now());
		ph.setUpdateTime(DateUtils.now());
	}
}
