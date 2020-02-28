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
package org.onap.usecaseui.server.bean.activateEdge;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "description",
        "resource-version",
        "type",
        "allotted-resource-name",
        "relationship-list"
})
public class AllotedResource {
    @JsonProperty("id")
    private String id;
    @JsonProperty("description")
    private String description;
    @JsonProperty("resource-version")
    private String resourceVersion;
    @JsonProperty("type")
    private String type;
    @JsonProperty("allotted-resource-name")
    private String allottedResourceName;
    @JsonProperty("relationship-list")
    private RelationshipList relationshipList;

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("resource-version")
    public String getResourceVersion() {
        return resourceVersion;
    }

    @JsonProperty("resource-version")
    public void setResourceVersion(String resourceVersion) {
        this.resourceVersion = resourceVersion;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("allotted-resource-name")
    public String getAllottedResourceName() {
        return allottedResourceName;
    }

    @JsonProperty("allotted-resource-name")
    public void setAllottedResourceName(String allottedResourceName) {
        this.allottedResourceName = allottedResourceName;
    }

    @JsonProperty("relationship-list")
    public RelationshipList getRelationshipList() {
        return relationshipList;
    }

    @JsonProperty("relationship-list")
    public void setRelationshipList(RelationshipList relationshipList) {
        this.relationshipList = relationshipList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).append("description", description).append("resourceVersion", resourceVersion).append("type", type).append("allottedResourceName", allottedResourceName).append("relationshipList", relationshipList).toString();
    }
}
