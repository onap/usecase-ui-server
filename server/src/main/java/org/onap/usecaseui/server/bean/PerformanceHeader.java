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
@Table(name="performance_commoneventheader")
public class PerformanceHeader implements Serializable {

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
	
	@Column(name = "sequence")
	private String sequence;
	
	@Column(name = "measurements_for_vf_scaling_version")
	private String measurementsForVfScalingVersion;
	
	@Column(name = "measurement_interval")
	private String measurementInterval;

	/** */
	public static class PerformanceHeaderBuilder {
		private String sourceId;
		private String version;
		private String eventName;
		private String domain;
		private String eventId;
		private String eventType;
		private String nfcNamingCode;
		private String nfNamingCode;
		private String sourceName;
		private String reportingEntityId;
		private String reportingEntityName;
		private String priority;
		private String startEpochMicrosec;
		private String lastEpochMicroSec;
		private String sequence;
		private String measurementsForVfScalingVersion;
		private String measurementInterval;
		private Date createTime;
		private Date updateTime;

		public PerformanceHeaderBuilder setSourceId(String sourceId) {
			this.sourceId = sourceId;
			return this;
		}

		public PerformanceHeaderBuilder setVersion(String version) {
			this.version = version;
			return this;
		}

		public PerformanceHeaderBuilder setEventName(String eventName) {
			this.eventName = eventName;
			return this;
		}

		public PerformanceHeaderBuilder setDomain(String domain) {
			this.domain = domain;
			return this;
		}

		public PerformanceHeaderBuilder setEventId(String eventId) {
			this.eventId = eventId;
			return this;
		}

		public PerformanceHeaderBuilder setEventType(String eventType) {
			this.eventType = eventType;
			return this;
		}

		public PerformanceHeaderBuilder setNfcNamingCode(String nfcNamingCode) {
			this.nfcNamingCode = nfcNamingCode;
			return this;
		}

		public PerformanceHeaderBuilder setNfNamingCode(String nfNamingCode) {
			this.nfNamingCode = nfNamingCode;
			return this;
		}

		public PerformanceHeaderBuilder setSourceName(String sourceName) {
			this.sourceName = sourceName;
			return this;
		}

		public PerformanceHeaderBuilder setReportingEntityId(String reportingEntityId) {
			this.reportingEntityId = reportingEntityId;
			return this;
		}

		public PerformanceHeaderBuilder setReportingEntityName(String reportingEntityName) {
			this.reportingEntityName = reportingEntityName;
			return this;
		}

		public PerformanceHeaderBuilder setPriority(String priority) {
			this.priority = priority;
			return this;
		}

		public PerformanceHeaderBuilder setStartEpochMicrosec(String startEpochMicrosec) {
			this.startEpochMicrosec = startEpochMicrosec;
			return this;
		}

		public PerformanceHeaderBuilder setLastEpochMicroSec(String lastEpochMicroSec) {
			this.lastEpochMicroSec = lastEpochMicroSec;
			return this;
		}

		public PerformanceHeaderBuilder setSequence(String sequence) {
			this.sequence = sequence;
			return this;
		}

		public PerformanceHeaderBuilder setMeasurementsForVfScalingVersion(String measurementsForVfScalingVersion) {
			this.measurementsForVfScalingVersion = measurementsForVfScalingVersion;
			return this;
		}

		public PerformanceHeaderBuilder setMeasurementInterval(String measurementInterval) {
			this.measurementInterval = measurementInterval;
			return this;
		}

		public PerformanceHeaderBuilder setCreateTime(Date createTime) {
			this.createTime = createTime;
			return this;
		}

		public PerformanceHeaderBuilder setUpdateTime(Date updateTime) {
			this.updateTime = updateTime;
			return this;
		}

		public PerformanceHeader createPerformanceHeader() {
			return new PerformanceHeader(this);
		}
	}
	
	public PerformanceHeader() {
	}

	public PerformanceHeader(String sourceId) {
		this.sourceId = sourceId;
	}

	public PerformanceHeader(PerformanceHeaderBuilder builder) {
		this.version = builder.version;
		this.eventName = builder.eventName;
		this.domain = builder.domain;
		this.eventId = builder.eventId;
		this.eventType = builder.eventType;
		this.nfcNamingCode = builder.nfcNamingCode;
		this.nfNamingCode = builder.nfNamingCode;
		this.sourceId = builder.sourceId;
		this.sourceName = builder.sourceName;
		this.reportingEntityId = builder.reportingEntityId;
		this.reportingEntityName = builder.reportingEntityName;
		this.priority = builder.priority;
		this.startEpochMicrosec = builder.startEpochMicrosec;
		this.lastEpochMicroSec = builder.lastEpochMicroSec;
		this.sequence = builder.sequence;
		this.measurementsForVfScalingVersion = builder.measurementsForVfScalingVersion;
		this.measurementInterval = builder.measurementInterval;
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

	public String getMeasurementsForVfScalingVersion() {
		return measurementsForVfScalingVersion;
	}

	public void setMeasurementsForVfScalingVersion(String measurementsForVfScalingVersion) {
		this.measurementsForVfScalingVersion = measurementsForVfScalingVersion;
	}

	public String getMeasurementInterval() {
		return measurementInterval;
	}

	public void setMeasurementInterval(String measurementInterval) {
		this.measurementInterval = measurementInterval;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
