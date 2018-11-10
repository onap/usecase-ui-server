/**
 * Copyright 2016-2017 ZTE Corporation.
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
package org.onap.usecaseui.server.service.lcm.domain.so.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class OperationProgress {
	
	private String serviceId;
	
    private String operationId;
    
    private String serviceName;

    private String operation;

    private String result;

    private String reason;

    private String userId;

    private String operationContent;

    private int progress;

    private String operateAt;

    private String finishedAt;

    
    
    public OperationProgress(String serviceId, String operationId, String serviceName, String operation, String result,
			String reason, String userId, String operationContent, int progress, String operateAt, String finishedAt) {
		this.serviceId = serviceId;
		this.operationId = operationId;
		this.serviceName = serviceName;
		this.operation = operation;
		this.result = result;
		this.reason = reason;
		this.userId = userId;
		this.operationContent = operationContent;
		this.progress = progress;
		this.operateAt = operateAt;
		this.finishedAt = finishedAt;
	}
    
    public OperationProgress() {
	}

	public String getOperationId() {
        return operationId;
    }

	public void setOperationId(String operationId) {
        this.operationId = operationId;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOperationContent() {
        return operationContent;
    }

    public void setOperationContent(String operationContent) {
        this.operationContent = operationContent;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String getOperateAt() {
        return operateAt;
    }

    public void setOperateAt(String operateAt) {
        this.operateAt = operateAt;
    }

    public String getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(String finishedAt) {
        this.finishedAt = finishedAt;
    }

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
    
}
