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
package org.onap.usecaseui.server.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class SiteResource implements Serializable {

    @JsonProperty("site-resource-id")
    private String siteResourceId;

    @JsonProperty("site-resource-name")
    private String resourceName;

    @JsonProperty("description")
    private String description;

    @JsonProperty("type")
    private String type;

    @JsonProperty("role")
    private String role;

    @JsonIgnore
    private String generatedSiteId;

    @JsonProperty("selflink")
    private String selflink;

    @JsonProperty("operational-status")
    private String opStatus;

    @JsonProperty("model-customization-id")
    private String modelCustId;

    @JsonProperty("model-invariant-id")
    private String modelInvId;

    @JsonProperty("model-version-id")
    private String modelVersionId;

    @JsonProperty("resource-version")
    private String resourceVersion;

    public SiteResource() {
    }

    public SiteResource(String siteResourceId, String resourceName, String description, String type, String role, String generatedSiteId, String selflink, String opStatus, String modelCustId, String modelInvId, String modelVersionId, String resourceVersion) {
        this.siteResourceId = siteResourceId;
        this.resourceName = resourceName;
        this.description = description;
        this.type = type;
        this.role = role;
        this.generatedSiteId = generatedSiteId;
        this.selflink = selflink;
        this.opStatus = opStatus;
        this.modelCustId = modelCustId;
        this.modelInvId = modelInvId;
        this.modelVersionId = modelVersionId;
        this.resourceVersion = resourceVersion;
    }

    public String getSiteResourceId() {
        return siteResourceId;
    }

    public void setSiteResourceId(String siteResourceId) {
        this.siteResourceId = siteResourceId;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getSelflink() {
        return selflink;
    }

    public void setSelflink(String selflink) {
        this.selflink = selflink;
    }

    public String getOpStatus() {
        return opStatus;
    }

    public void setOpStatus(String opStatus) {
        this.opStatus = opStatus;
    }

    public String getModelCustId() {
        return modelCustId;
    }

    public void setModelCustId(String modelCustId) {
        this.modelCustId = modelCustId;
    }

    public String getModelInvId() {
        return modelInvId;
    }

    public void setModelInvId(String modelInvId) {
        this.modelInvId = modelInvId;
    }

    public String getModelVersionId() {
        return modelVersionId;
    }

    public void setModelVersionId(String modelVersionId) {
        this.modelVersionId = modelVersionId;
    }

    public String getResourceVersion() {
        return resourceVersion;
    }

    public void setResourceVersion(String resourceVersion) {
        this.resourceVersion = resourceVersion;
    }

    public String getGeneratedSiteId() {
        return generatedSiteId;
    }

    public void setGeneratedSiteId(String generatedSiteId) {
        this.generatedSiteId = generatedSiteId;
    }

    @Override
    public String toString() {
        return "SiteResource{" +
                "siteResourceId='" + siteResourceId + '\'' +
                ", resourceName='" + resourceName + '\'' +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", role='" + role + '\'' +
                ", generatedSiteId='" + generatedSiteId + '\'' +
                ", selflink='" + selflink + '\'' +
                ", opStatus='" + opStatus + '\'' +
                ", modelCustId='" + modelCustId + '\'' +
                ", modelInvId='" + modelInvId + '\'' +
                ", modelVersionId='" + modelVersionId + '\'' +
                ", resourceVersion='" + resourceVersion + '\'' +
                '}';
    }
}
