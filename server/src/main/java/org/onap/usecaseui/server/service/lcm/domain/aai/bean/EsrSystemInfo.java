/**
 * Copyright (C) 2019 Verizon. All Rights Reserved.
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

package org.onap.usecaseui.server.service.lcm.domain.aai.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EsrSystemInfo {

    @JsonProperty("esr-system-info-id")
    private String esrSystemInfoId;

    @JsonProperty("system-name")
    private String systemName;

    @JsonProperty("vendor")
    private String vendor;

    @JsonProperty("version")
    private String version;

    @JsonProperty("service-url")
    private String serviceUrl;

    @JsonProperty("user-name")
    private String userName;

    @JsonProperty("password")
    private String password;

    @JsonProperty("system-type")
    private String systemType;

    @JsonProperty("resource-version")
    private String resourceVersion;

    public String getEsrSystemInfoId() {
        return esrSystemInfoId;
    }

    public void setEsrSystemInfoId(String esrSystemInfoId) {
        this.esrSystemInfoId = esrSystemInfoId;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getServiceUrl() {
        return serviceUrl;
    }

    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSystemType() {
        return systemType;
    }

    public void setSystemType(String systemType) {
        this.systemType = systemType;
    }

    public String getResourceVersion() {
        return resourceVersion;
    }

    public void setResourceVersion(String resourceVersion) {
        this.resourceVersion = resourceVersion;
    }

}
