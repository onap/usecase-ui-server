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
import java.util.Date;

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
	@Column(name = "name")
	private String name;
	
	@Column(name = "value")
	private String value;
	
	@Id
	@Column(name = "eventId")
	private String eventId;
	
	@Column(name = "createTime")
	private Date  createTime;
	
	@Column(name = "updateTime")
	private Date updateTime;

	public AlarmsInformation() {
	}

	public AlarmsInformation(String eventId) {
		this.eventId = eventId;
	}

	public AlarmsInformation(String name, String value, String eventId, Date createTime, Date updateTime) {
		this.name = name;
		this.value = value;
		this.eventId = eventId;
		this.createTime = createTime;
		this.updateTime = updateTime;
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

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	
}
