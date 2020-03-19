/*
 * Copyright (C) 2018 CMCC, Inc. and others. All rights reserved.
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
package org.onap.usecaseui.server.bean.sotn;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Pinterface {
	
	private String interfaceName;
	
	public Pinterface() {
	}

	@JsonCreator
	public Pinterface(@JsonProperty("interface-name") String interfaceName) {
		this.interfaceName = interfaceName;
	}
	
	@JsonProperty("interface-name")
	public String getInterfaceName() {
		return interfaceName;
	}
	
	@JsonProperty("interface-name")
	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}
	
}