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
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class OperationProgressInformation {
	
    private OperationProgress operationStatus;
	
    public OperationProgressInformation(@JsonProperty("operation")OperationProgress operationStatus) {
		this.operationStatus = operationStatus;
	}
    
	public OperationProgressInformation() {
	}

	public OperationProgress getOperationStatus() {
        return operationStatus;
    }

    public void setOperationStatus(OperationProgress operationStatus) {
        this.operationStatus = operationStatus;
    }
}
