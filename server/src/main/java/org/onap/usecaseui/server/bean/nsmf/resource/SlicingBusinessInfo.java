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
package org.onap.usecaseui.server.bean.nsmf.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SlicingBusinessInfo {

    @JsonProperty("service_instance_id")
    private String serviceInstanceId;

    @JsonProperty("service_instance_name")
    private String serviceInstanceName;

    @JsonProperty("service_type")
    private String serviceType;

    @JsonProperty("service_snssai")
    private String environmentContext;

    @JsonProperty("orchestration_status")
    private String orchestrationStatus;

    @JsonProperty("last_operation_type")
    private String lastOperationType;

    @JsonProperty("last_operation_progress")
    private String lastOperationProgress;

}
