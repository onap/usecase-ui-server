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
package org.onap.usecaseui.server.service.lcm.domain.so.bean;

import org.junit.Test; 
import org.junit.Before; 
import org.junit.After;

import static org.junit.Assert.assertEquals;

public class OperationProgressTest {

	@Before
	public void before() throws Exception {
	}

	@After
	public void after() throws Exception {
	}

	@Test
	public void testOperationProgress() throws Exception {
		OperationProgress op = new OperationProgress();
		op.setUserId("test");
		op.setOperationId("test");
		op.setOperation("test");
		op.setResult("test");
		op.setReason("test");
		op.setOperationContent("test");
		op.setProgress(123);
		op.setOperateAt("test");
		op.setFinishedAt("test");
		op.setServiceId("123");
		op.setServiceName("test");
		op.getOperationId();
		op.getOperation();
		op.getResult();
		op.getReason();
		op.getUserId();
		op.getOperationContent();
		op.getProgress();
		op.getOperateAt();
		assertEquals("123",op.getServiceId());
		assertEquals("test",op.getOperationId());
		assertEquals("test",op.getServiceName());
		assertEquals("test",op.getOperation());
		assertEquals("test",op.getResult());
		assertEquals("test",op.getReason());
		assertEquals("test",op.getUserId());
		assertEquals("test",op.getFinishedAt());
		assertEquals("test",op.getOperateAt());
		assertEquals("test",op.getOperationContent());
		assertEquals(123,op.getProgress());

	}
	@Test
	public void testConstructor(){
		OperationProgress op = new OperationProgress("123","test","test","test","test","test","test","test",123,"test","test");
		assertEquals("123",op.getServiceId());
		assertEquals("test",op.getOperationId());
		assertEquals("test",op.getServiceName());
		assertEquals("test",op.getOperation());
		assertEquals("test",op.getResult());
		assertEquals("test",op.getReason());
		assertEquals("test",op.getUserId());
		assertEquals("test",op.getFinishedAt());
		assertEquals("test",op.getOperateAt());
		assertEquals("test",op.getOperationContent());
		assertEquals(123,op.getProgress());

	}
}
