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
package org.onap.usecaseui.server.bean;

import java.io.Serial;
import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="service_instance_operations")
public class ServiceInstanceOperations implements Serializable{

    /**
     * 
     */
    @Serial
    private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "service_instance_id")
	private String serviceInstanceId;
	
	@Id
	@Column(name = "operation_id")
	private String operationId;
	
	@Column(name = "operation_type")
	private String operationType;
	
	@Column(name = "operation_progress")
	private String operationProgress;
	
	@Column(name = "operation_result")
	private String operationResult;
	
	@Column(name = "start_time")
	private String startTime;
	
	@Column(name = "end_time")
	private String endTime;

	public ServiceInstanceOperations() {
	}

	public ServiceInstanceOperations(String serviceInstanceId, String operationId, String operationType,
			String operationProgress, String operationResult, String startTime, String endTime) {
		super();
		this.serviceInstanceId = serviceInstanceId;
		this.operationId = operationId;
		this.operationType = operationType;
		this.operationProgress = operationProgress;
		this.operationResult = operationResult;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public String getServiceInstanceId() {	
		return serviceInstanceId;
	}

	public void setServiceInstanceId(String serviceInstanceId) {
		this.serviceInstanceId = serviceInstanceId;
	}

	public String getOperationId() {
		return operationId;
	}

	public void setOperationId(String operationId) {
		this.operationId = operationId;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public String getOperationProgress() {
		return operationProgress;
	}

	public void setOperationProgress(String operationProgress) {
		this.operationProgress = operationProgress;
	}

	public String getOperationResult() {
		return operationResult;
	}

	public void setOperationResult(String operationResult) {
		this.operationResult = operationResult;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((operationId == null) ? 0 : operationId.hashCode());
		result = prime * result + ((serviceInstanceId == null) ? 0 : serviceInstanceId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ServiceInstanceOperations other = (ServiceInstanceOperations) obj;
		if (operationId == null) {
			if (other.operationId != null)
				return false;
		} else if (!operationId.equals(other.operationId))
			return false;
		if (serviceInstanceId == null) {
			if (other.serviceInstanceId != null)
				return false;
		} else if (!serviceInstanceId.equals(other.serviceInstanceId))
			return false;
		return true;
	}
}
