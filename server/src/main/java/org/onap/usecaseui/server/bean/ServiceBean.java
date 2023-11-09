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

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="service_instances")
public class ServiceBean {
	
	@Id
	@Column(name = "id")
	private String id;
	
	@Column(name = "service_instance_id")
	private String serviceInstanceId;
	
	@Column(name = "customer_id")
	private String customerId;
	
	@Column(name = "service_type")
	private String serviceType;
	
	@Column(name = "usecase_type")
	private String serviceDomain;
	
	@Column(name = "uuid")
	private String uuid;
	
	@Column(name = "invariant_uuid")
	private String invariantUuuid;

	public ServiceBean() {
	}

	public ServiceBean(String id, String serviceInstanceId, String customerId, String serviceType, String serviceDomain,
			String uuid, String invariantUuuid) {
		this.id = id;
		this.serviceInstanceId = serviceInstanceId;
		this.customerId = customerId;
		this.serviceType = serviceType;
		this.serviceDomain = serviceDomain;
		this.uuid = uuid;
		this.invariantUuuid = invariantUuuid;
	}

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

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getInvariantUuuid() {
		return invariantUuuid;
	}

	public void setInvariantUuuid(String invariantUuuid) {
		this.invariantUuuid = invariantUuuid;
	}

	
}
