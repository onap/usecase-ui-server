/*
 * Copyright (C) 2019 CMCC, Inc. and others. All rights reserved.
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
package org.onap.usecaseui.server.bean.nsmf.task;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SlicingTaskInfo {

    @JsonProperty("task_id")
    private String taskId;

    @JsonProperty("task_name")
    private String name;

    @JsonProperty("service_snssai")
    private String serviceSnssai;

    @JsonProperty("service_type")
    private String serviceType;

    @JsonProperty("create_time")
    private String createTime;

    @JsonProperty("processing_status")
    private String status;
}
