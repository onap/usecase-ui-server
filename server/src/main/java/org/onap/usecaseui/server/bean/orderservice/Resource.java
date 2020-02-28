/*
 * Copyright (C) 2017 CMCC, Inc. and others. All rights reserved.
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
package org.onap.usecaseui.server.bean.orderservice;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "resourceName",
        "resourceInvariantUuid",
        "resourceUuid",
        "resourceCustomizationUuid",
        "parameters"
})

public class Resource {

    @JsonProperty("resourceName")
    private String resourceName;
    @JsonProperty("resourceInvariantUuid")
    private String resourceInvariantUuid;
    @JsonProperty("resourceUuid")
    private String resourceUuid;
    @JsonProperty("resourceCustomizationUuid")
    private String resourceCustomizationUuid;
    @JsonProperty("parameters")
    private Parameters_ parameters_;


    @JsonProperty("resourceName")
    public String getResourceName() {
        return resourceName;
    }

    @JsonProperty("resourceName")
    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    @JsonProperty("resourceInvariantUuid")
    public String getResourceInvariantUuid() {
        return resourceInvariantUuid;
    }

    @JsonProperty("resourceInvariantUuid")
    public void setResourceInvariantUuid(String resourceInvariantUuid) {
        this.resourceInvariantUuid = resourceInvariantUuid;
    }

    @JsonProperty("resourceUuid")
    public String getResourceUuid() {
        return resourceUuid;
    }

    @JsonProperty("resourceUuid")
    public void setResourceUuid(String resourceUuid) {
        this.resourceUuid = resourceUuid;
    }

    @JsonProperty("resourceCustomizationUuid")
    public String getResourceCustomizationUuid() {
        return resourceCustomizationUuid;
    }

    @JsonProperty("resourceCustomizationUuid")
    public void setResourceCustomizationUuid(String resourceCustomizationUuid) {
        this.resourceCustomizationUuid = resourceCustomizationUuid;
    }

    public Parameters_ getParameters_() {
        return parameters_;
    }

    public void setParameters_(Parameters_ parameters_) {
        this.parameters_ = parameters_;
    }
}
