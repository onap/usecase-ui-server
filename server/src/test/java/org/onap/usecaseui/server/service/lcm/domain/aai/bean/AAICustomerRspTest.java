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
package org.onap.usecaseui.server.service.lcm.domain.aai.bean;

import org.junit.Test; 
import org.junit.Before; 
import org.junit.After;
import java.util.*;

import static org.junit.Assert.assertNotNull;

public class AAICustomerRspTest {

	@Before
	public void before() throws Exception {
	}

	@After
	public void after() throws Exception {
	}

	@Test
	public void testAAICustomerRsp() throws Exception {
		AAICustomer ac = new AAICustomer("globalCustomerId", "subscriberName", "subscriberType","resourceVersion");
		List acList=new ArrayList<AAICustomer>();
		acList.add(ac);
		AAICustomerRsp acr = new AAICustomerRsp();
		acr.setCustomer(acList);
		assertNotNull(acr.getCustomer());
	}
}
