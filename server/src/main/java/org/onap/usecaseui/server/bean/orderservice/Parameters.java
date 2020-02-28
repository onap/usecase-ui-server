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

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "locationConstraints",
        "resources",
        "requestInputs"
})

public class Parameters {
    @JsonProperty("locationConstraints")
    private List<Object> locationConstraints = null;
    @JsonProperty("resources")
    private List<Resource> resources = null;
    @JsonProperty("requestInputs")
    private RequestInputs requestInputs;


    @JsonProperty("locationConstraints")
    public List<Object> getLocationConstraints() {
        return locationConstraints;
    }

    @JsonProperty("locationConstraints")
    public void setLocationConstraints(List<Object> locationConstraints) {
        this.locationConstraints = locationConstraints;
    }

    @JsonProperty("resources")
    public List<Resource> getResources() {
        return resources;
    }

    @JsonProperty("resources")
    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

    @JsonProperty("requestInputs")
    public RequestInputs getRequestInputs() {
        return requestInputs;
    }

    @JsonProperty("requestInputs")
    public void setRequestInputs(RequestInputs requestInputs) {
        this.requestInputs = requestInputs;
    }

}
