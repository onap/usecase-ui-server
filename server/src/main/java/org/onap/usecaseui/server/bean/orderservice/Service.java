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
        "name",
        "description",
        "serviceInvariantUuid",
        "serviceUuid",
        "globalSubscriberId",
        "serviceType",
        "parameters"
})
public class Service {
    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("serviceInvariantUuid")
    private String serviceInvariantUuid;
    @JsonProperty("serviceUuid")
    private String serviceUuid;
    @JsonProperty("globalSubscriberId")
    private String globalSubscriberId;
    @JsonProperty("serviceType")
    private String serviceType;
    @JsonProperty("parameters")
    private Parameters parameters;


    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("serviceInvariantUuid")
    public String getServiceInvariantUuid() {
        return serviceInvariantUuid;
    }

    @JsonProperty("serviceInvariantUuid")
    public void setServiceInvariantUuid(String serviceInvariantUuid) {
        this.serviceInvariantUuid = serviceInvariantUuid;
    }

    @JsonProperty("serviceUuid")
    public String getServiceUuid() {
        return serviceUuid;
    }

    @JsonProperty("serviceUuid")
    public void setServiceUuid(String serviceUuid) {
        this.serviceUuid = serviceUuid;
    }

    @JsonProperty("globalSubscriberId")
    public String getGlobalSubscriberId() {
        return globalSubscriberId;
    }

    @JsonProperty("globalSubscriberId")
    public void setGlobalSubscriberId(String globalSubscriberId) {
        this.globalSubscriberId = globalSubscriberId;
    }

    @JsonProperty("serviceType")
    public String getServiceType() {
        return serviceType;
    }

    @JsonProperty("serviceType")
    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    @JsonProperty("parameters")
    public Parameters getParameters() {
        return parameters;
    }

    @JsonProperty("parameters")
    public void setParameters(Parameters parameters) {
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        return "\"Service\":" + "{" +
                "\"name=\":\"" + name +
                "\", \"description=\":\"" + description +
                ", serviceInvariantUuid='" + serviceInvariantUuid + '\'' +
                ", serviceUuid='" + serviceUuid + '\'' +
                ", globalSubscriberId='" + globalSubscriberId + '\'' +
                ", serviceType='" + serviceType + '\'' +
                ", parameters=" + parameters +
                '}';
    }
}
