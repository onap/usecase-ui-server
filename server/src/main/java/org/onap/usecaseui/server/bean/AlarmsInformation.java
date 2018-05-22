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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author xuekui
 *
 */
@Entity
@Table(name="alarms_additionalinformation")
public class AlarmsInformation implements Serializable {
	
	@Id
	@Column(name = "id")
	private int id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "value")
	private String value;
	
	@Column(name = "sourceId")
	private String sourceId;
	
	@Column(name = "startEpochMicroSec")
	private String  startEpochMicroSec;
	
	@Column(name = "lastEpochMicroSec")
	private String lastEpochMicroSec;
	
	@Column(name = "headerId")
	private String headerId;
	
	@Override
	public String toString() {
		return "AlarmsInformation{" +
				"id=" + id +
				", name='" + name + '\'' +
				", value='" + value + '\'' +
				", sourceId='" + sourceId + '\'' +
				", createTime=" + startEpochMicroSec +
				", updateTime=" + lastEpochMicroSec +
				", headerId=" + headerId +
				'}';
	}

	public AlarmsInformation() {
	}

	public AlarmsInformation(String sourceId) {
		this.sourceId = sourceId;
	}

	public AlarmsInformation(String name, String value, String sourceId, String startEpochMicroSec, String lastEpochMicroSec,String headerId) {
		this.name = name;
		this.value = value;
		this.sourceId = sourceId;
		this.startEpochMicroSec = startEpochMicroSec;
		this.lastEpochMicroSec = lastEpochMicroSec;
		this.headerId = headerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public String getStartEpochMicroSec() {
		return startEpochMicroSec;
	}

	public void setStartEpochMicroSec(String startEpochMicroSec) {
		this.startEpochMicroSec = startEpochMicroSec;
	}

	public String getLastEpochMicroSec() {
		return lastEpochMicroSec;
	}

	public void setLastEpochMicroSec(String lastEpochMicroSec) {
		this.lastEpochMicroSec = lastEpochMicroSec;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getHeaderId() {
		return headerId;
	}

	public void setHeaderId(String headerId) {
		this.headerId = headerId;
	}
	
}
