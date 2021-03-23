/*
 * Copyright (C) 2021 CMCC, Inc. and others. All rights reserved.
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
package org.onap.usecaseui.server.service.slicingdomain.aai.bean.connection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.onap.usecaseui.server.service.slicingdomain.aai.bean.connection.RelationshipList;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class NetworkInfo {

    @JsonProperty("service-instance-id")
    private String serviceInstanceId;
    @JsonProperty("service-instance-name")
    private String serviceInstanceName;
    @JsonProperty("service-type")
    private String serviceType;
    @JsonProperty("service-role")
    private String serviceRole;
    @JsonProperty("environment-context")
    private String environmentContext;
    @JsonProperty("model-invariant-id")
    private String modelInvariantId;
    @JsonProperty("model-version-id")
    private String modelVersionId;
    @JsonProperty("resource-version")
    private String resourceVersion;
    @JsonProperty("orchestration-status")
    private String orchestrationStatus;
    @JsonProperty("relationship-list")
    private RelationshipList relationshipList;
}
