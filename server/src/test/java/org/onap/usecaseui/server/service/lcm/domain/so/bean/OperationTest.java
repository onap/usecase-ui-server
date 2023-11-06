/**
 * Copyright (C) 2017 CMCC, Inc. and others. All rights reserved.
 *
 * ================================================================================
 * Modifications Copyright (C) 2020 IBM.
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
import static org.junit.Assert.assertEquals;

public class OperationTest {

	@Before
	public void before() throws Exception {
	}

	@After
	public void after() throws Exception {
	}

	@Test
	public void testOperation() throws Exception {
		Operation o = new Operation();
		o.setServiceId("123");
		o.setOperationId("123");
		assertEquals("123",o.getServiceId());
		assertEquals("123",o.getOperationId());
	}
}
