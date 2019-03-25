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

import java.io.Serializable;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.onap.usecaseui.server.util.DateUtils;

public class PerformanceHeaderTest implements Serializable {

	@Before
	public void before() throws Exception {
	}

	@After
	public void after() throws Exception {
	}

	@Test
	public void testGetPerformanceHeader() throws Exception {
		PerformanceHeader ph = new PerformanceHeader.PerformanceHeaderBuilder().setVersion("version").setEventName("eventName").setDomain("domain").setEventId("eventId").setEventType("eventType").setNfcNamingCode("nfcNamingCode").setNfNamingCode("nfNamingCode").setSourceId("sourceId").setSourceName("sourceName").setReportingEntityId("reportingEntityId").setReportingEntityName("reportingEntityName").setPriority("priority").setStartEpochMicrosec("startEpochMicrosec").setLastEpochMicroSec("lastEpochMicroSec").setSequence("sequence").setMeasurementsForVfScalingVersion("measurementsForVfScalingVersion").setMeasurementInterval("measurementInterval").setCreateTime(DateUtils.now()).setUpdateTime(DateUtils.now()).createPerformanceHeader();
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
		ph.getId();
	}

	@Test
	public void testSetPerformanceHeader() throws Exception {
		PerformanceHeader ph = new PerformanceHeader.PerformanceHeaderBuilder().setSourceId("sourceId").createPerformanceHeader();
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
		ph.setId("");
	}
}
