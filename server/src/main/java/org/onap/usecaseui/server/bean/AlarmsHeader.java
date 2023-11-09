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
package org.onap.usecaseui.server.bean;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name="alarms_commoneventheader")
public class AlarmsHeader implements Serializable{

	@Id
	@Column(name = "id")
	private String id;

	@Column(name = "version")
	private String version;
	
	@Column(name = "event_name")
	private String eventName;
	
	@Column(name = "domain")
	private String domain;
	
	
	@Column(name = "event_id")
	private String eventId;
	
	@Column(name = "event_type")
	private String eventType;
	
	@Column(name = "nfc_naming_code", nullable=false)
	private String nfcNamingCode;
	
	@Column(name = "nf_naming_code", nullable=false)
	private String nfNamingCode;
	
	@Column(name = "source_id")
	private String sourceId;
	
	@Column(name = "source_name")
	private String sourceName;
	
	@Column(name = "reporting_entity_id")
	private String reportingEntityId;
	
	@Column(name = "reporting_entity_name")
	private String reportingEntityName;
	
	@Column(name = "priority")
	private String priority;
	
	@Column(name = "start_epoch_microsec")
	private String startEpochMicrosec;
	
	@Column(name = "last_epoch_microsec")
	private String lastEpochMicroSec;

	@Column(name = "start_epoch_microsec_cleared")
	private String startEpochMicrosecCleared;

	@Column(name = "last_epoch_microsec_cleared")
	private String lastEpochMicroSecCleared;

	
	@Column(name = "sequence")
	private String sequence;
	
	@Column(name = "fault_fields_version")
	private String faultFieldsVersion;
	
	@Column(name = "event_servrity")
	private String eventServrity;
	
	@Column(name = "event_source_type")
	private String eventSourceType;
	
	@Column(name = "event_category")
	private String eventCategory;
	
	@Column(name = "alarm_condition")
	private String alarmCondition;
	
	@Column(name = "specific_problem")
	private String specificProblem;
	
	@Column(name = "vf_status")
	private String vfStatus;
	
	@Column(name = "alarm_interfacea")
	private String alarmInterfaceA;
	
	@Column(name = "status")
	private String status;

	public AlarmsHeader() {
	}

	public AlarmsHeader(String sourceId) {
		this.sourceId = sourceId;
	}



	public AlarmsHeader(String version, String eventName, String domain, String eventId, String eventType, String nfcNamingCode, String nfNamingCode, String sourceId, String sourceName, String reportingEntityId, String reportingEntityName, String priority, String startEpochMicrosec, String lastEpochMicroSec, String startEpochMicrosecCleared, String lastEpochMicroSecCleared, String sequence, String faultFieldsVersion, String eventServrity, String eventSourceType, String eventCategory, String alarmCondition, String specificProblem, String vfStatus, String alarmInterfaceA, String status, Date createTime, Date updateTime) {
		this.version = version;
		this.eventName = eventName;
		this.domain = domain;
		this.eventId = eventId;
		this.eventType = eventType;
		this.nfcNamingCode = nfcNamingCode;
		this.nfNamingCode = nfNamingCode;
		this.sourceId = sourceId;
		this.sourceName = sourceName;
		this.reportingEntityId = reportingEntityId;
		this.reportingEntityName = reportingEntityName;
		this.priority = priority;
		this.startEpochMicrosec = startEpochMicrosec;
		this.lastEpochMicroSec = lastEpochMicroSec;
		this.startEpochMicrosecCleared = startEpochMicrosecCleared;
		this.lastEpochMicroSecCleared = lastEpochMicroSecCleared;
		this.sequence = sequence;
		this.faultFieldsVersion = faultFieldsVersion;
		this.eventServrity = eventServrity;
		this.eventSourceType = eventSourceType;
		this.eventCategory = eventCategory;
		this.alarmCondition = alarmCondition;
		this.specificProblem = specificProblem;
		this.vfStatus = vfStatus;
		this.alarmInterfaceA = alarmInterfaceA;
		this.status = status;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public String getNfcNamingCode() {
		return nfcNamingCode;
	}

	public void setNfcNamingCode(String nfcNamingCode) {
		this.nfcNamingCode = nfcNamingCode;
	}

	public String getNfNamingCode() {
		return nfNamingCode;
	}

	public void setNfNamingCode(String nfNamingCode) {
		this.nfNamingCode = nfNamingCode;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public String getReportingEntityId() {
		return reportingEntityId;
	}

	public void setReportingEntityId(String reportingEntityId) {
		this.reportingEntityId = reportingEntityId;
	}

	public String getReportingEntityName() {
		return reportingEntityName;
	}

	public void setReportingEntityName(String reportingEntityName) {
		this.reportingEntityName = reportingEntityName;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getStartEpochMicrosec() {
		return startEpochMicrosec;
	}

	public void setStartEpochMicrosec(String startEpochMicrosec) {
		this.startEpochMicrosec = startEpochMicrosec;
	}

	public String getStartEpochMicrosecCleared() {
		return startEpochMicrosecCleared;
	}

	public void setStartEpochMicrosecCleared(String startEpochMicrosecCleared) {
		this.startEpochMicrosecCleared = startEpochMicrosecCleared;
	}

	public String getLastEpochMicroSecCleared() {
		return lastEpochMicroSecCleared;
	}

	public void setLastEpochMicroSecCleared(String lastEpochMicroSecCleared) {
		this.lastEpochMicroSecCleared = lastEpochMicroSecCleared;
	}

	public String getLastEpochMicroSec() {
		return lastEpochMicroSec;
	}

	public void setLastEpochMicroSec(String lastEpochMicroSec) {
		this.lastEpochMicroSec = lastEpochMicroSec;
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public String getFaultFieldsVersion() {
		return faultFieldsVersion;
	}

	public void setFaultFieldsVersion(String faultFieldsVersion) {
		this.faultFieldsVersion = faultFieldsVersion;
	}

	public String getEventServrity() {
		return eventServrity;
	}

	public void setEventServrity(String eventServrity) {
		this.eventServrity = eventServrity;
	}

	public String getEventSourceType() {
		return eventSourceType;
	}

	public void setEventSourceType(String eventSourceType) {
		this.eventSourceType = eventSourceType;
	}

	public String getEventCategory() {
		return eventCategory;
	}

	public void setEventCategory(String eventCategory) {
		this.eventCategory = eventCategory;
	}

	public String getAlarmCondition() {
		return alarmCondition;
	}

	public void setAlarmCondition(String alarmCondition) {
		this.alarmCondition = alarmCondition;
	}

	public String getSpecificProblem() {
		return specificProblem;
	}

	public void setSpecificProblem(String specificProblem) {
		this.specificProblem = specificProblem;
	}

	public String getVfStatus() {
		return vfStatus;
	}

	public void setVfStatus(String vfStatus) {
		this.vfStatus = vfStatus;
	}

	public String getAlarmInterfaceA() {
		return alarmInterfaceA;
	}

	public void setAlarmInterfaceA(String alarmInterfaceA) {
		this.alarmInterfaceA = alarmInterfaceA;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
