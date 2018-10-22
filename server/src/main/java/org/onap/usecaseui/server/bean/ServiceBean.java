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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="service")
public class ServiceBean {
	
	@Id
	@Column(name = "id")
	private String id;
	
	@Column(name = "serviceInstanceId")
	private String serviceInstanceId;
	
	@Column(name = "customerId")
	private String customerId;
	
	@Column(name = "serviceType")
	private String serviceType;
	
	@Column(name = "serviceDomain")
	private String serviceDomain;
	
	@Column(name = "operationId")
	private String operationId;
	
	@Column(name = "parentServiceInstanceId")
	private String parentServiceInstanceId;
	
	@Column(name = "status")
	private String status;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getServiceInstanceId() {
		return serviceInstanceId;
	}

	public void setServiceInstanceId(String serviceInstanceId) {
		this.serviceInstanceId = serviceInstanceId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getServiceDomain() {
		return serviceDomain;
	}

	public void setServiceDomain(String serviceDomain) {
		this.serviceDomain = serviceDomain;
	}

	public String getOperationId() {
		return operationId;
	}

	public void setOperationId(String operationId) {
		this.operationId = operationId;
	}

	public String getParentServiceInstanceId() {
		return parentServiceInstanceId;
	}

	public void setParentServiceInstanceId(String parentServiceInstanceId) {
		this.parentServiceInstanceId = parentServiceInstanceId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ServiceBean() {
	}

	public ServiceBean(String id, String serviceInstanceId, String customerId, String serviceType, String serviceDomain,
			String operationId, String parentServiceInstanceId, String status) {
		this.id = id;
		this.serviceInstanceId = serviceInstanceId;
		this.customerId = customerId;
		this.serviceType = serviceType;
		this.serviceDomain = serviceDomain;
		this.operationId = operationId;
		this.parentServiceInstanceId = parentServiceInstanceId;
		this.status = status;
	}

	@Override
	public String toString() {
		return "ServiceBean [id=" + id + ", serviceInstanceId=" + serviceInstanceId + ", customerId=" + customerId
				+ ", serviceType=" + serviceType + ", serviceDomain=" + serviceDomain + ", operationId=" + operationId
				+ ", parentServiceInstanceId=" + parentServiceInstanceId + ", status=" + status + "]";
	}
	
}
