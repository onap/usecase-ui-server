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
package org.onap.usecaseui.server.bean.lcm;

import org.junit.Test; 
import org.junit.Before; 
import org.junit.After;

import java.util.*;

public class ServiceTemplateInputTest {

	@Before
	public void before() throws Exception {
	}

	@After
	public void after() throws Exception {
	}

	@Test
	public void testGetServiceTemplateInput() throws Exception {
		List<TemplateInput> inputs = new ArrayList<>();
		ServiceTemplateInput sti = new ServiceTemplateInput("invariantUUID", "uuid", "name", "type", "version", "description", "category", "subcategory","", inputs);
		sti.getInvariantUUID();
		sti.getUuid();
		sti.getName();
		sti.getType();
		sti.getVersion();
		sti.getDescription();
		sti.getCategory();
		sti.getSubcategory();
		sti.getInputs();
		sti.getNestedTemplates();
		sti.toString();
		sti.hashCode();
	}

	@Test
	public void testSetServiceTemplateInput() throws Exception {
		List<TemplateInput> tis = new ArrayList<>();
		TemplateInput ti = new TemplateInput("name", "type", "description", "isRequired", "defaultValue");
		ServiceTemplateInput sti = new ServiceTemplateInput("invariantUUID", "uuid", "name", "type", "version", "description", "category", "subcategory","", tis);
		sti.addNestedTemplate(sti);
		sti.addInputs(tis);
		sti.addInput(ti);
		sti.setType("type");
		sti.equals(sti);
	}
}
