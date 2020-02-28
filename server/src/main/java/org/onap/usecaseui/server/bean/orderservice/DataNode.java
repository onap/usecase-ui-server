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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "name",
        "description",
        "globalSubscriberId",
        "serviceType",
        "role",
        "publicIP",
        "ipMode",
        "portType",
        "portNumber",
        "transportNetworkName",
        "version",
        "type",
        "class",
        "systemIp"
})
public class DataNode {
    @JsonIgnore
    private String name;
    @JsonIgnore
    private String description;
    @JsonIgnore
    private String globalSubscriberId;
    @JsonIgnore
    private String serviceType;
    @JsonIgnore
    private String role;
    @JsonIgnore
    private String publicIP;
    @JsonIgnore
    private String ipMode;
    @JsonIgnore
    private String portType;
    @JsonIgnore
    private String portNumber;
    @JsonIgnore
    private String transportNetworkName;
    @JsonIgnore
    private String version;
    @JsonIgnore
    private String type;
    @JsonIgnore
    private String _class;
    @JsonIgnore
    private String systemIp;

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

    @JsonProperty("role")
    public String getRole() {
        return role;
    }

    @JsonProperty("role")
    public void setRole(String role) {
        this.role = role;
    }

    @JsonProperty("publicIP")
    public String getPublicIP() {
        return publicIP;
    }

    @JsonProperty("publicIP")
    public void setPublicIP(String publicIP) {
        this.publicIP = publicIP;
    }

    @JsonProperty("ipMode")
    public String getIpMode() {
        return ipMode;
    }

    @JsonProperty("ipMode")
    public void setIpMode(String ipMode) {
        this.ipMode = ipMode;
    }

    @JsonProperty("portType")
    public String getPortType() {
        return portType;
    }

    @JsonProperty("portType")
    public void setPortType(String portType) {
        this.portType = portType;
    }

    @JsonProperty("portNumber")
    public String getPortNumber() {
        return portNumber;
    }

    @JsonProperty("portNumber")
    public void setPortNumber(String portNumber) {
        this.portNumber = portNumber;
    }

    @JsonProperty("transportNetworkName")
    public String getTransportNetworkName() {
        return transportNetworkName;
    }

    @JsonProperty("transportNetworkName")
    public void setTransportNetworkName(String transportNetworkName) {
        this.transportNetworkName = transportNetworkName;
    }

    @JsonProperty("version")
    public String getVersion() {
        return version;
    }

    @JsonProperty("version")
    public void setVersion(String version) {
        this.version = version;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("class")
    public String getClass_() {
        return _class;
    }

    @JsonProperty("class")
    public void setClass_(String _class) {
        this._class = _class;
    }

    @JsonProperty("systemIp")
    public String getSystemIp() {
        return systemIp;
    }

    @JsonProperty("systemIp")
    public void setSystemIp(String systemIp) {
        this.systemIp = systemIp;
    }

    @Override
    public String toString() {
        return "{" +
                "\"name\":\"" + name + '\"' +
                ", \"description\":\"" + description + '\"' +
                ", \"globalSubscriberId\":\"" + globalSubscriberId + '\"' +
                ", \"serviceType\":\"" + serviceType + '\"' +
                ", \"role\":\"" + role + '\"' +
                ", \"publicIP\":\"" + publicIP + '\"' +
                ", \"ipMode\":\"" + ipMode + '\"' +
                ", \"portType\":\"" + portType + '\"' +
                ", \"portNumber\":\"" + portNumber + '\"' +
                ", \"transportNetworkName\":\"" + transportNetworkName + '\"' +
                ", \"version\":\"" + version + '\"' +
                ", \"type\":\"" + type + '\"' +
                ", \"class\":\"" + _class + '\"' +
                ", \"systemIp\":\"" + systemIp + '\"' +
                '}';
    }
}
