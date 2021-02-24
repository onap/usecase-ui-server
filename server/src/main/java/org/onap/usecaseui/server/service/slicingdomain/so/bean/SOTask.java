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
package org.onap.usecaseui.server.service.slicingdomain.so.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SOTask {

	private String taskId;

	private String requestId;

	private String name;

	private String createdTime;

	private String status;

	private String isManual;

	private SliceTaskParams sliceTaskParams;


	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getTaskId() {
		return taskId;
	}

	public String getRequestId() {
		return requestId;
	}

	public String getName() {
		return name;
	}


	public String getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

	public String getIsManual() {
		return isManual;
	}

	public void setIsManual(String isManual) {
		this.isManual = isManual;
	}

	public SliceTaskParams getSliceTaskParams() {
		return sliceTaskParams;
	}

	public void setSliceTaskParams(SliceTaskParams sliceTaskParams) {
		this.sliceTaskParams = sliceTaskParams;
	}
}
