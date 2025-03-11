/**
 * Copyright 2019 HUAWEI Corporation.
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
package org.onap.usecaseui.server.service.slicingdomain.aai.bean;

import java.io.Serial;
import java.io.Serializable;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AAIService implements Serializable{
    /**
     * 
     */
    @Serial
    private static final long serialVersionUID = -2847142014162429886L;

	private String serviceInstanceId;

	private String serviceInstanceName;
	
	private String serviceType;
	
	private String serviceRole;
	
	private String environmentContext;
	
	private String description;
	
	private String modelInvariantId;
	
	private String modelVersionId;

	private String resourceVersion;

	private String serviceInstanceLocationId;
	
	private String orchestrationStatus;
	

	public String getServiceInstanceId() {
		return serviceInstanceId;
	}

	public void setServiceInstanceId(String serviceInstanceId) {
		this.serviceInstanceId = serviceInstanceId;
	}

	public String getServiceInstanceName() {
		return serviceInstanceName;
	}

	public void setServiceInstanceName(String serviceInstanceName) {
		this.serviceInstanceName = serviceInstanceName;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getServiceRole() {
		return serviceRole;
	}

	public void setServiceRole(String serviceRole) {
		this.serviceRole = serviceRole;
	}

	public String getEnvironmentContext() {
		return environmentContext;
	}

	public void setEnvironmentContext(String environmentContext) {
		this.environmentContext = environmentContext;
	}


	public String getModelInvariantId() {
		return modelInvariantId;
	}

	public void setModelInvariantId(String modelInvariantId) {
		this.modelInvariantId = modelInvariantId;
	}

	public String getModelVersionId() {
		return modelVersionId;
	}

	public void setModelVersionId(String modelVersionId) {
		this.modelVersionId = modelVersionId;
	}

	public String getResourceVersion() {
		return resourceVersion;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setResourceVersion(String resourceVersion) {
		this.resourceVersion = resourceVersion;
	}

	public String getServiceInstanceLocationId() {
		return serviceInstanceLocationId;
	}

	public void setServiceInstanceLocationId(String serviceInstanceLocationId) {
		this.serviceInstanceLocationId = serviceInstanceLocationId;
	}

	public String getOrchestrationStatus() {
		return orchestrationStatus;
	}

	public void setOrchestrationStatus(String orchestrationStatus) {
		this.orchestrationStatus = orchestrationStatus;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
