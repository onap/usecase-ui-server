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

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConnectionLink {
    @JsonProperty("link-name")
    private String linkName;
    @JsonProperty("in-maint")
    private String inMaint;
    @JsonProperty("link-type")
    private String linkType;
    @JsonProperty("resource-version")
    private String resourceVersion;
    @JsonProperty("link-name2")
    private String linkName2;
    @JsonProperty("link-id")
    private String linkId;
    @JsonProperty("service-function")
    private String serviceFunction;
    @JsonProperty("relationship-list")
    private RelationshipList relationshipList;


}
