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

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class NetWorkResource {
	
	private String networkId;
	
	private List<Pnf> pnfs;
	
	private List<Pinterface> tps;
	
	private String aaiId;

	public NetWorkResource(String networkId, List<Pnf> pnfs, List<Pinterface> tps,String aaiId) {
		this.networkId = networkId;
		this.pnfs = pnfs;
		this.tps = tps;
		this.aaiId = aaiId;
	}

	public NetWorkResource() {
	}

	public String getNetworkId() {
		return networkId;
	}

	public void setNetworkId(String networkId) {
		this.networkId = networkId;
	}

	public List<Pnf> getPnfs() {
		return pnfs;
	}

	public void setPnfs(List<Pnf> pnfs) {
		this.pnfs = pnfs;
	}

	public List<Pinterface> getTps() {
		return tps;
	}

	public void setTps(List<Pinterface> tps) {
		this.tps = tps;
	}

	public String getAaiId() {
		return aaiId;
	}

	public void setAaiId(String aaiId) {
		this.aaiId = aaiId;
	}
	
}
