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
package org.onap.usecaseui.server.service.lcm.domain.sdc.bean;

import org.junit.Test; 
import org.junit.Before; 
import org.junit.After;

public class VnfTest {

	@Before
	public void before() throws Exception {
	}

	@After
	public void after() throws Exception {
	}

	@Test
	public void testVnf() throws Exception {
		Vnf vnf = new Vnf();
		vnf.setUuid("");
		vnf.setInvariantUUID("");
		vnf.setName("");
		vnf.setVersion("");
		vnf.equals(vnf);
		vnf.hashCode();
		vnf.getUuid();
		vnf.getInvariantUUID();
		vnf.getName();
		vnf.getVersion();
	}
}
