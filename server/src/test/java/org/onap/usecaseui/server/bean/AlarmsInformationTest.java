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

public class AlarmsInformationTest implements Serializable {

	@Before
	public void before() throws Exception {
	}

	@After
	public void after() throws Exception {
	}

	@Test
	public void testGetAlarmsInformation() throws Exception {
		AlarmsInformation ai = new AlarmsInformation("name", "value", "eventId","","","headerId");
		ai.getName();
		ai.getValue();
		ai.getSourceId();
		ai.getStartEpochMicroSec();
		ai.getLastEpochMicroSec();
		ai.getId();
		ai.setHeaderId("");
		ai.toString();
	}

	@Test
	public void testSetAlarmsInformation() throws Exception {
		AlarmsInformation ai = new AlarmsInformation("eventId");
		ai.setName("");
		ai.setValue("");
		ai.setSourceId("");
		ai.setStartEpochMicroSec("");;
		ai.setLastEpochMicroSec("");;
		ai.setId(1);
		ai.setHeaderId("");
	}
}
