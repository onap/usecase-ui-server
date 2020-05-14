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
package org.onap.usecaseui.server.bean;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;
import org.junit.runner.RunWith;
import org.onap.usecaseui.server.UuiServerApplication;
import org.onap.usecaseui.server.bean.AlarmsHeader;
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

public class AlarmsHeaderTest {

	@Before
	public void before() throws Exception {
	}

	@After
	public void after() throws Exception {
	}

	@Test
	public void testGetAlarmsHeader() throws Exception {
		AlarmsHeader ah = new AlarmsHeader("version", "eventName", "domain", "eventId", "eventType", "nfcNamingCode",
										"nfNamingCode", "sourceId", "sourceName", "reportingEntityId", "reportingEntityName",
										"priority", "startEpochMicrosec", "lastEpochMicroSec","startEpochMicrosecCleared", "lastEpochMicroSecCleared", "sequence", "faultFieldsVersion",
										"eventServrity", "eventSourceType", "eventCategory", "alarmCondition", "specificProblem",
										"vfStatus", "alarmInterfaceA", "status", DateUtils.now(), DateUtils.now());
		ah.getVersion();
		ah.getEventName();
		ah.getDomain();
		ah.getEventId();
		ah.getEventType();
		ah.getNfcNamingCode();
		ah.getNfNamingCode();
		ah.getSourceId();
		ah.getSourceName();
		ah.getReportingEntityId();
		ah.getReportingEntityName();
		ah.getPriority();
		ah.getStartEpochMicrosec();
		ah.getLastEpochMicroSec();
		ah.getSequence();
		ah.getFaultFieldsVersion();
		ah.getEventServrity();
		ah.getEventSourceType();
		ah.getEventCategory();
		ah.getAlarmCondition();
		ah.getSpecificProblem();
		ah.getVfStatus();
		ah.getAlarmInterfaceA();
		ah.getStatus();
		ah.getId();
		Assert.assertNotNull(ah);
		Assert.assertNotNull(ah.getAlarmInterfaceA());
	}

	@Test
	public void testSetAlarmsHeader() throws Exception {
		AlarmsHeader ah = new AlarmsHeader("sourceId");
		ah.setVersion("");
		ah.setEventName("");
		ah.setDomain("");
		ah.setEventId("");
		ah.setEventType("");
		ah.setNfcNamingCode("");
		ah.setNfNamingCode("");
		ah.setSourceId("");
		ah.setSourceName("");
		ah.setReportingEntityId("");
		ah.setReportingEntityName("");
		ah.setPriority("");
		ah.setStartEpochMicrosec("");
		ah.setLastEpochMicroSec("");
		ah.setSequence("");
		ah.setFaultFieldsVersion("");
		ah.setEventServrity("");
		ah.setEventSourceType("");
		ah.setEventCategory("");
		ah.setAlarmCondition("");
		ah.setSpecificProblem("");
		ah.setVfStatus("");
		ah.setAlarmInterfaceA("");
		ah.setStatus("");
		ah.setStartEpochMicrosec(DateUtils.now().getTime()+"");
		ah.setLastEpochMicroSec(DateUtils.now().getTime()+"");
		ah.setId("");
		Assert.assertNotNull(ah);

	}
}
